package techfist.dev.omdbbrowser.api.service;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;


public class MoviesServiceImpl implements MoviesService {
    private final MoviesApi moviesApi;

    public MoviesServiceImpl(@NonNull MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    @Override
    public Single<DiscoverResponse> discoverPopularMovies(@NonNull DiscoverMoviesRequest moviesRequest) {
        return moviesApi.lookUpPopularMovies(moviesRequest.getApikey(), moviesRequest.getSortingType(), moviesRequest.getRequestedPage());
    }
}
