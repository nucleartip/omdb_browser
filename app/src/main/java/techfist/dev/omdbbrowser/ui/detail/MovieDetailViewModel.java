package techfist.dev.omdbbrowser.ui.detail;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.api.response.Movie;
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
 * this view model associates with {@link MovieDetailActivity}
 * it provides various data binding fields, which are self explanatory
 *
 * holds business logic of fetching and providing movie details for display
 *
 */
public class MovieDetailViewModel implements LifecycleObserver {
    private static String TAG = MovieDetailViewModel.class.getSimpleName();
    @VisibleForTesting final static String PROFILE_URL_FORMAT = "https://image.tmdb.org/t/p/w500/%1$s";

    private final MoviesRepository moviesRepository;
    private final ResourceProvider resourceProvider;


    /**
     * publishes loading messages if any
     */
    final RxObservableField<String> loadingText;

    /**
     * Publishes error if any
     */
    final RxObservableField<String> errorText;

    /**
     * looks for movie id
     */
    final RxObservableField<Long> movieId;


    // Data binding fields, all are binded to UI by android data binding library
    /**
     * sets released date
     */
    public final RxObservableField<String> releasedDate;
    /**
     * sets rating
     */
    public final RxObservableField<String> ratings;
    /**
     * sets overview
     */
    public final RxObservableField<String> overview;
    /**
     * sets produced by
     */
    public final RxObservableField<String> producedBy;
    /**
     * sets spoken languages
     */
    public final RxObservableField<String> spokenLanguages;
    /**
     * sets country of origin
     */
    public final RxObservableField<String> countryOfOrigin;
    /**
     * sets photo url
     */
    public final RxObservableField<String> posterUrl;

    /**
     * sets title
     */
    public final RxObservableField<String> title;

    @VisibleForTesting Disposable detailLoader;

    public MovieDetailViewModel(@NonNull MoviesRepository moviesRepository, @NonNull ResourceProvider resourceProvider) {
        this.moviesRepository = moviesRepository;
        this.resourceProvider = resourceProvider;
        this.releasedDate = new RxObservableField<>(EMPTY);
        this.ratings = new RxObservableField<>(EMPTY);
        this.overview = new RxObservableField<>(EMPTY);
        this.producedBy = new RxObservableField<>(EMPTY);
        this.spokenLanguages = new RxObservableField<>(EMPTY);
        this.countryOfOrigin = new RxObservableField<>(EMPTY);
        this.posterUrl = new RxObservableField<>(EMPTY);
        this.loadingText = new RxObservableField<>(EMPTY);
        this.errorText = new RxObservableField<>(EMPTY);
        this.title = new RxObservableField<>(EMPTY);
        this.movieId = new RxObservableField<>(-1L);
    }

    @Override
    public void onScreenInit() {
        detailLoader = movieId.asObservable()
                .filter(id -> id > 0)
                .switchMapSingle(moviesRepository::loadMovieDetail)
                .subscribeOn(getIoScheduler())
                .observeOn(getMainThreadScheduler())
                .doOnSubscribe((ignored) -> loadingText.set(resourceProvider.getString(R.string.loading_details)))
                .doOnEach(ignored -> loadingText.set(EMPTY))
                .subscribe(this::setupVariables, error -> {
                    Log.d(TAG, error.getMessage(), error);
                    errorText.set(resourceProvider.getString(R.string.error));
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
        RxUtils.unSubscribeSubscription(detailLoader);
    }

    @SuppressLint("DefaultLocale")
    private void setupVariables(@NonNull Movie movie) {
        title.set(movie.getOriginalTitle());
        releasedDate.set(resourceProvider.getString(R.string.released, movie.getReleaseDate()));
        overview.set(resourceProvider.getString(R.string.synopsis, movie.getOverView()));
        posterUrl.set(String.format(PROFILE_URL_FORMAT, movie.getPosterUrl()));
        ratings.set(resourceProvider.getString(R.string.rating_detail, String.format("%.2f", movie.getAverageRatings()), movie
                .getTotalVote()));

        if (movie.getProductionCompanyList() != null) {
            StringBuilder builder = new StringBuilder();
            int size = movie.getProductionCompanyList()
                    .size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(movie.getProductionCompanyList()
                        .get(i)
                        .getName());
            }
            producedBy.set(resourceProvider.getString(R.string.produce_by, builder.toString()));
        }

        if (movie.getProductionCountryList() != null) {
            StringBuilder builder = new StringBuilder();
            int size = movie.getProductionCountryList()
                    .size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(movie.getProductionCountryList()
                        .get(i)
                        .getName());
            }
            countryOfOrigin.set(resourceProvider.getString(R.string.origin_country, builder.toString()));
        }


        if (movie.getSpokenLanguageList() != null) {
            StringBuilder builder = new StringBuilder();
            int size = movie.getSpokenLanguageList()
                    .size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(movie.getSpokenLanguageList()
                        .get(i)
                        .getName());
            }
            spokenLanguages.set(resourceProvider.getString(R.string.spoken_languages, builder.toString()));
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
