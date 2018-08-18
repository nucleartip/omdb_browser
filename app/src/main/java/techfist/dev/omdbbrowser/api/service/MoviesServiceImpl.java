package techfist.dev.omdbbrowser.api.service;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.requests.MovieDetailRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;

/**
 * Provides implementation of MoviesServices API's
 *
 * 1. API is abstracted, and is implementation provided to use by Retrofit network lib.
 * 2. API response is converted into Observable stream using RxJava adapters.
 * 3. JSON's as automatically parsed to respective fields marked with annotation @SerializedName
 * by Google gson library
 */
public class MoviesServiceImpl implements MoviesService {
    private final MoviesApi moviesApi;

    public MoviesServiceImpl(@NonNull MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    /**
     * queries popular movies from TMDB movie database
     * @param moviesRequest
     * @return
     */
    @Override
    public Single<DiscoverResponse> discoverPopularMovies(@NonNull DiscoverMoviesRequest moviesRequest) {
        return moviesApi.lookUpPopularMovies(moviesRequest.getApikey(), moviesRequest.getSortingType(), moviesRequest.getRequestedPage());
    }

    /**
     * Queries details of a movie from TMDB database
     * @param movieDetailRequest
     * @return
     */
    @Override
    public Single<Movie> getMovieDetail(@NonNull MovieDetailRequest movieDetailRequest) {
        return moviesApi.getMovieDetails(movieDetailRequest.getMovieId(), movieDetailRequest.getApikey());
    }
}