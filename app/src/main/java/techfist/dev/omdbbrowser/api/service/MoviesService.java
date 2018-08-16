package techfist.dev.omdbbrowser.api.service;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.requests.MovieDetailRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;

public interface MoviesService {
    /**
     * discovers movies according to provided request
     * @param resultsRequest
     * @return {{@link Single<DiscoverResponse>}}
     */
    Single<DiscoverResponse> discoverPopularMovies(@NonNull DiscoverMoviesRequest resultsRequest);

    /**
     * gets requested movie detail
     * @param movieDetailRequest
     * @return
     */
    Single<Movie> getMovieDetail(@NonNull MovieDetailRequest movieDetailRequest);
}
