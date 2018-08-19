package techfist.dev.omdbbrowser.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.data.dao.MoviesDao;


/**
 * represents a offline repository of movies
 * am only exposing API's which are required and defaulting rest of filters.
 * <p>
 * provides functionality such as loading list of movies and particular movies detail
 * it automatically maintain current page details being viewed by user, and provides API
 * to load next page until user reaches the limit.
 * <p>
 * <p>
 */
public class OfflineMovieRepository implements Repository {

    private final MoviesDao moviesDao;
    private final int PAGING = 10;
    @VisibleForTesting int pageNumber = 1;
    @VisibleForTesting int maxPages = 1;
    @VisibleForTesting long lastTimeStamp = 0;

    public OfflineMovieRepository(@NonNull MoviesDao moviesDao) {
        this.moviesDao = moviesDao;
    }

    @Override
    public Single<DiscoverResponse> loadMovies() {
        return moviesDao.count()
                .flatMap(total -> {
                    maxPages = Math.max(1, total / PAGING);
                    pageNumber = 1;
                    return moviesDao.loadMovies(PAGING)
                            .map(list -> {
                                DiscoverResponse response = new DiscoverResponse();
                                response.setMoviesList(list);
                                response.setPage(pageNumber);
                                response.setTotalPages(maxPages);
                                lastTimeStamp = list.size() == 0 ? 0 : list.get(list.size() - 1)
                                        .getCreated();
                                return response;
                            });
                });
    }

    @Override
    public Single<DiscoverResponse> loadMoreMovies() {
        if (pageNumber > maxPages) {
            return Single.error(new RuntimeException("end of list"));
        }

        return moviesDao.loadMoviesByTimeStamp(lastTimeStamp, PAGING)
                .map(list -> {
                    lastTimeStamp = list.size() == 0 ? lastTimeStamp : list.get(list.size() - 1)
                            .getCreated();
                    pageNumber++;
                    DiscoverResponse response = new DiscoverResponse();
                    response.setMoviesList(list);
                    response.setPage(pageNumber);
                    response.setTotalPages(maxPages);
                    return response;

                });
    }

    @Override
    public Single<Movie> loadMovieDetail(long id) {
        if (id < 0) {
            return Single.error(new IllegalArgumentException("in valid id to load"));
        }

        return moviesDao.loadMovieById(id);
    }

    /**
     * clears and then inserts items into db
     *
     * @param discoverResponse discover response
     * @return irrespective of error or not, returns original supplied value
     */
    public Single<DiscoverResponse> clearAndInsertAll(@NonNull DiscoverResponse discoverResponse) {
        if (discoverResponse.getMoviesList() == null || discoverResponse.getMoviesList()
                .size() == 0) {
            return Single.just(discoverResponse);
        }

        return Single.create((SingleOnSubscribe<DiscoverResponse>) emitter -> {
            moviesDao.clear();
            moviesDao.insertAll(discoverResponse.getMoviesList());
            if (emitter != null) {
                emitter.onSuccess(discoverResponse);
            }
        })
                .onErrorReturnItem(discoverResponse);

    }

    /**
     * inserts items into db
     *
     * @param discoverResponse discover response
     * @return irrespective of error or not, returns original supplied value
     */
    public Single<DiscoverResponse> insertAll(@NonNull DiscoverResponse discoverResponse) {
        if (discoverResponse.getMoviesList() == null || discoverResponse.getMoviesList()
                .size() == 0) {
            return Single.just(discoverResponse);
        }

        return Single.create((SingleOnSubscribe<DiscoverResponse>) emitter -> {
            moviesDao.insertAll(discoverResponse.getMoviesList());
            if (emitter != null) {
                emitter.onSuccess(discoverResponse);
            }

        })
                .onErrorReturnItem(discoverResponse);
    }

    /**
     * simple inserts or replaces a movie into database
     *
     * @param movie movie needed to inserted or replaces
     * @return irrespective of error or not, returns original supplied value
     */
    public Single<Movie> insertMovie(@NonNull Movie movie) {

        return Single.create((SingleOnSubscribe<Movie>) emitter -> {
            moviesDao.insert(movie);
            if (emitter != null) {
                emitter.onSuccess(movie);
            }
        })
                .onErrorReturnItem(movie);
    }
}
