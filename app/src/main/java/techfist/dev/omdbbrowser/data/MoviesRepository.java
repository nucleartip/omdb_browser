package techfist.dev.omdbbrowser.data;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.utils.NetworkConnection;

/**
 * Represents a repositories of movies, works with online and online repository.
 * switches to offline automatically whenever there remain no active network
 * connection.
 * <p>
 * it tries to sync online downloaded data to local db, but ignores error if any
 * we are trying to give seamless experience to user, so if data is downloaded online successfully
 * we try to display it irrespective of local sync operation with db
 *
 * EDGE case not handled:
 *
 * switching off of network while browsing list, however it will be fairly straight forward to implement,
 * but owing to time am skipping this.
 */
public class MoviesRepository implements Repository {

    private final NetworkConnection networkConnection;
    private final OnlineMoviesRepository onlineMoviesRepository;
    private final OfflineMovieRepository offlineMovieRepository;

    public MoviesRepository(@NonNull NetworkConnection networkConnection,
                            @NonNull OnlineMoviesRepository onlineMoviesRepository,
                            @NonNull OfflineMovieRepository offlineMovieRepository) {

        this.networkConnection = networkConnection;
        this.onlineMoviesRepository = onlineMoviesRepository;
        this.offlineMovieRepository = offlineMovieRepository;
    }

    @Override
    public Single<DiscoverResponse> loadMovies() {
        if (networkConnection.isNetworkAvailable()) {
            return onlineMoviesRepository.loadMovies()
                    .flatMap(offlineMovieRepository::clearAndInsertAll);
        }

        return offlineMovieRepository.loadMovies();
    }

    @Override
    public Single<DiscoverResponse> loadMoreMovies() {
        if (networkConnection.isNetworkAvailable()) {
            return onlineMoviesRepository.loadMoreMovies()
                    .flatMap(offlineMovieRepository::insertAll);
        }

        return offlineMovieRepository.loadMoreMovies();
    }

    @Override
    public Single<Movie> loadMovieDetail(long id) {
        if (networkConnection.isNetworkAvailable()) {
            return onlineMoviesRepository.loadMovieDetail(id)
                    .flatMap(offlineMovieRepository::insertMovie);
        }

        return offlineMovieRepository.loadMovieDetail(id);
    }
}
