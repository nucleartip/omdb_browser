package techfist.dev.omdbbrowser.api.service;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;

public interface MoviesService {
    Single<DiscoverResponse> discoverPopularMovies(@NonNull DiscoverMoviesRequest resultsRequest);
}
