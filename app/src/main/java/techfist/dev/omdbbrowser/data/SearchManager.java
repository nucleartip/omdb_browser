package techfist.dev.omdbbrowser.data;

import android.util.Log;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import techfist.dev.omdbbrowser.api.SortType;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.service.MoviesService;


/**
 * creates an search session, and manages search query
 * am only exposing API's which are required and defaulting filters
 * <p>
 * Created by Nucleartip on 5/3/18.
 */
public class SearchManager {

    private static final String TAG = SearchManager.class.getSimpleName();

    private final MoviesService moviesService;
    private final String apiKey;
    private int pageNumber = 1;
    private int maxPages = Integer.MAX_VALUE;


    public SearchManager(@NonNull MoviesService skyScannerService, @NonNull String apiKey) {
        this.moviesService = skyScannerService;
        this.apiKey = apiKey;
    }

    public Single<DiscoverResponse> loadMovies() {
        DiscoverMoviesRequest request = new DiscoverMoviesRequest.Builder(apiKey).setRequestedPage(1)
                .setSortingType(SortType.POPULARITY_DESC)
                .build();
        return load(request);

    }

    public Single<DiscoverResponse> loadMoreMovies() {

        if (pageNumber == maxPages) {
            return Single.error(new RuntimeException("end of list"));
        }

        DiscoverMoviesRequest request = new DiscoverMoviesRequest.Builder(apiKey).setRequestedPage(++pageNumber)
                .setSortingType(SortType.POPULARITY_DESC)
                .build();
        return load(request);
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
