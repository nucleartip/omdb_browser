package techfist.dev.omdbbrowser.api.service;


import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.SortType;
import techfist.dev.omdbbrowser.api.response.Movie;

/**
 * interface defining structure of key api's to communicate with tmdb backend, callbacks are wrapped in Rx
 * using RxAdapterFactory
 *
 * More info over API provide https://www.themoviedb.org/
 */

public interface MoviesApi {

    String ENDPOINT = "https://api.themoviedb.org/3/";
    String API_KEY = "5f4c38a48c054b3e4ef248cd4c6ee801";

    /**
     * loads the most popular movies in supplied sorted order
     *
     * @param apiKey
     * @param sort
     * @param page
     * @return
     */
    @GET("discover/movie")
    Single<DiscoverResponse> lookUpPopularMovies(@Query("api_key") @NonNull String apiKey,
                                                 @Query("sort_by") @NonNull @SortType String sort,
                                                 @Query("page") int page);

    /**
     * loads details of a particular movie
     *
     * @param apiKey api key
     * @param id     movie id to look for
     * @return {@link Single<Movie>}
     */
    @GET("movie/{movie_id}")
    Single<Movie> getMovieDetails(@Path("movie_id") long id, @Query("api_key") @NonNull String apiKey);

}
