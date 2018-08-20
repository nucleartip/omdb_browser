package techfist.dev.omdbbrowser.data;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import techfist.dev.omdbbrowser.api.SortType;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.requests.MovieDetailRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.api.service.MoviesService;


/**
 * represents a online repository of movies
 * am only exposing API's which are required and defaulting rest of filters.
 * <p>
 * provides functionality such as loading list of movies and particular movies detail
 * it automatically maintain current page details being viewed by user, and provides API
 * to load next page until user reaches the limit.
 * <p>
 * this code has been unit tested, test impl can be found under corresponding test folder
 * in similar directory structure.
 */
public class OnlineMoviesRepository implements Repository{

    private static final String TAG = OnlineMoviesRepository.class.getSimpleName();

    private final MoviesService moviesService;
    private final String apiKey;
    @VisibleForTesting int pageNumber = 1;
    @VisibleForTesting int maxPages = 1;


    public OnlineMoviesRepository(@NonNull MoviesService moviesService, @NonNull String apiKey) {
        this.moviesService = moviesService;
        this.apiKey = apiKey;
    }

    /**
     * Discovers popular movies in sorted format
     *
     * @return {@link Single<DiscoverResponse>} observable notifying if complete error otherwise
     */
    public Single<DiscoverResponse> loadMovies() {
        DiscoverMoviesRequest request = new DiscoverMoviesRequest.Builder(apiKey).setRequestedPage(1)
                .setSortingType(SortType.POPULARITY_DESC)
                .build();
        return load(request);

    }

    /**
     * loads next page in available movie directory
     *
     * @return {@link Single<DiscoverResponse>} observable notifying if complete error otherwise
     */
    public Single<DiscoverResponse> loadMoreMovies() {

        if (pageNumber > maxPages) {
            return Single.error(new RuntimeException("end of list"));
        }

        DiscoverMoviesRequest request = new DiscoverMoviesRequest.Builder(apiKey).setRequestedPage(++pageNumber)
                .setSortingType(SortType.POPULARITY_DESC)
                .build();
        return load(request);
    }

    /**
     * loads requested movie detail
     * @param id id of movie for which details has to be loaded
     * @return {@link Single<Movie>} observable notifying retrieved value error otherwise
     */
    public Single<Movie> loadMovieDetail(long id) {
        if (id < 0) {
            return Single.error(new IllegalArgumentException("in valid id to load"));
        }

        MovieDetailRequest request = new MovieDetailRequest.Builder(apiKey).setMovieId(id)
                .build();
        return moviesService.getMovieDetail(request);
    }

    private Single<DiscoverResponse> load(DiscoverMoviesRequest request) {
        return moviesService.discoverPopularMovies(request)
                .doOnEvent((discoverResponse, throwable) -> {
                    if (throwable == null) {
                        maxPages = discoverResponse.getTotalPages();
                        pageNumber = discoverResponse.getPage();
                        return;
                    }
                    --pageNumber;
                    Log.d(TAG, throwable.getMessage(), throwable);
                });
    }

}
