package techfist.dev.omdbbrowser.api.service;


import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.SortType;

/**
 * interface defining structure of key api's to communicate with tmdb backend, callbacks are wrapped in Rx
 * using RxAdapterFactory
 */

public interface MoviesApi {

    String ENDPOINT = "https://api.themoviedb.org/3/";
    String API_KEY = "5f4c38a48c054b3e4ef248cd4c6ee801";

    @GET("discover/movie")
    Single<DiscoverResponse> lookUpPopularMovies(@Query("api_key") @NonNull String apiKey,
                                                 @Query("sort_by") @NonNull @SortType String sort,
                                                 @Query("page") int page);

}
