package techfist.dev.omdbbrowser.data;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;

/**
 * defines behaviour of a movie repository
 */
public interface Repository {

    Single<DiscoverResponse> loadMovies();
    Single<DiscoverResponse> loadMoreMovies();
    Single<Movie> loadMovieDetail(long id);
}
