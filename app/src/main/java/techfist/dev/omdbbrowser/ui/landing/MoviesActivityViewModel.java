package techfist.dev.omdbbrowser.ui.landing;


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
import techfist.dev.omdbbrowser.data.MoviesRepository;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.utils.LifecycleObserver;
import techfist.dev.omdbbrowser.utils.RxObservableField;
import techfist.dev.omdbbrowser.utils.RxUtils;

import static techfist.dev.omdbbrowser.ApplicationConstant.EMPTY;

/**
 * this code has been unit tested, and can be found under test directory under similar
 * package structure
 *
 * this view model associates with {@link MoviesActivity}
 * it provides various data binding fields, which are self explanatory
 *
 * holds business logic of fetching and providing movies list for display
 *
 */
public class MoviesActivityViewModel extends RecyclerView.OnScrollListener implements LifecycleObserver {

    private static String TAG = MoviesActivityViewModel.class.getSimpleName();

    private final MoviesRepository moviesRepository;
    private final ResourceProvider resourceProvider;


    /**
     * binds to ui publishing result
     */
    public final MoviesAdapter adapter;

    // available for action
    /**
     * Publishes error if any
     */
    public final RxObservableField<String> errorText;
    /**
     * publishes loading messages if any
     */
    public final RxObservableField<String> loadingText;

    /**
     * publishes if loading more text need to be shown or not
     */
    public final RxObservableField<Boolean> showLoadingMore;

    @Nullable
    @VisibleForTesting Disposable loader;

    public MoviesActivityViewModel(@NonNull MoviesRepository moviesRepository,
                                   @NonNull ResourceProvider resourceProvider,
                                   @NonNull MoviesAdapter moviesAdapter) {
        this.moviesRepository = moviesRepository;
        this.resourceProvider = resourceProvider;
        this.adapter = moviesAdapter;
        this.errorText = new RxObservableField<>(EMPTY);
        this.loadingText = new RxObservableField<>(EMPTY);
        this.showLoadingMore  = new RxObservableField<>(false);

    }

    @Override
    public void onScreenInit() {
        loader = loadMovies()
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler())
                .doOnSubscribe(ignored -> loadingText.set(resourceProvider.getString(R.string.loading_looking_up_result)))
                .doOnEvent((s, throwable) -> loadingText.set(EMPTY))
                .subscribe(adapter::appendList, (throwable) -> {
                    errorText.set(resourceProvider.getString(R.string.error));
                    Log.d(TAG, throwable.getMessage(), throwable);
                });
    }

    @VisibleForTesting
    Single<List<MovieModel>> loadMovies(){
        return moviesRepository.loadMovies()
                .flatMap(this::transformResponse);
    }

    @VisibleForTesting
    Single<List<MovieModel>> loadMoreMovies(){
        return moviesRepository.loadMoreMovies()
                .flatMap(this::transformResponse);
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

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 4) {

            // its already running
            if (RxUtils.isSubscriptionRunning(loader)) {
                return;
            }

            loader = loadMoreMovies()
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

    /**
     * transforms a list of @class {@link DiscoverResponse} into list of @class
     * {@link MovieModel}
     * <p>
     * @param response result from discover api
     * @return Single producing required model list
     */

    private Single<List<MovieModel>> transformResponse(@NonNull final DiscoverResponse response) {

        if (response.getMoviesList() == null || response.getMoviesList()
                .isEmpty()) {
            return Single.just(Collections.emptyList());
        }

        return Observable.fromIterable(response.getMoviesList())
                .map((movie -> new MovieModel(resourceProvider, movie.getId(), movie.getTitle(), movie.getReleaseDate(), movie
                        .getPosterUrl(), movie.getAverageRatings())))
                .collect(ArrayList<MovieModel>::new, List::add);
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
