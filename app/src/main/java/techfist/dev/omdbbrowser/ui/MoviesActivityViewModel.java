package techfist.dev.omdbbrowser.ui;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.data.SearchManager;
import techfist.dev.omdbbrowser.utils.LifecycleObserver;
import techfist.dev.omdbbrowser.utils.RxObservableField;
import techfist.dev.omdbbrowser.utils.RxUtils;

import static techfist.dev.omdbbrowser.ApplicationConstant.EMPTY;


public class MoviesActivityViewModel extends RecyclerView.OnScrollListener implements LifecycleObserver {

    private static String TAG = MoviesActivityViewModel.class.getSimpleName();

    private final SearchManager searchManager;
    private final ResourceProvider resourceProvider;


    /**
     * binds to ui publishing result
     */
    public final MoviesAdapter adapter;

    // available for action
    /**
     * Publishes error if any
     */
    RxObservableField<String> errorText = new RxObservableField<>(EMPTY);
    /**
     * publishes loading messages if any
     */
    RxObservableField<String> loadingText = new RxObservableField<>(EMPTY);

    /**
     * publishes if loading more text need to be shown or not
     */
    RxObservableField<Boolean> showLoadingMore = new RxObservableField<>(false);

    @Nullable private Disposable loader;

    public MoviesActivityViewModel(@NonNull SearchManager searchManager,
                                   @NonNull ResourceProvider resourceProvider,
                                   @NonNull MoviesAdapter moviesAdapter) {
        this.searchManager = searchManager;
        this.resourceProvider = resourceProvider;
        this.adapter = moviesAdapter;
    }

    @Override
    public void onScreenInit() {
        loader = searchManager.loadMovies()
                .flatMap(this::transformResponse)
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler())
                .doOnSubscribe(disposable -> loadingText.set(resourceProvider.getString(R.string.loading_looking_up_result)))
                .doOnEvent((s, throwable) -> loadingText.set(EMPTY))
                .subscribe(adapter::appendList, (throwable) -> {
                    errorText.set(resourceProvider.getString(R.string.error));
                    Log.d(TAG, throwable.getMessage(), throwable);
                });

    }

    @Override
    public void onScreenVisible() {

    }

    @Override
    public void onScreenGone() {

    }

    @Override
    public void onScreenDestroyed() {
        RxUtils.unSubscribeSubscription(loader);
    }


    /**
     * transforms a list of @class {@link DiscoverResponse} into list of @class
     * {@link MovieModel}
     * <p>
     * this API does not assumes trip will have return ticket as well, this is just to make it more generic,
     * as same api can be used to display one way ticket
     *
     * @param response result from search api
     * @return Single producing required model list
     */

    @VisibleForTesting
    Single<List<MovieModel>> transformResponse(@NonNull final DiscoverResponse response) {

        if (response.getMoviesList() == null || response.getMoviesList()
                .isEmpty()) {
            return Single.just(Collections.emptyList());
        }

        return Observable.fromIterable(response.getMoviesList())
                .map((movie -> new MovieModel(resourceProvider, movie.getTitle(), movie.getReleaseDate(), movie.getPosterUrl())))
                .collect(ArrayList<MovieModel>::new, List::add);
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 4) {

            // its already running
            if (RxUtils.isSubscriptionRunning(loader)) {
                return;
            }

            loader = searchManager.loadMoreMovies()
                    .flatMap(this::transformResponse)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe((ignored) -> showLoadingMore.set(true))
                    .doOnEvent((movieModelList, throwable) -> showLoadingMore.set(false))
                    .subscribe(adapter::appendList, (throwable) -> {
                        errorText.set(resourceProvider.getString(R.string.loading_more_error));
                        Log.d(TAG, throwable.getMessage(), throwable);
                    });

        }
    }


    @VisibleForTesting
    Scheduler getIoScheduler() {
        return Schedulers.io();
    }

    @VisibleForTesting
    Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
