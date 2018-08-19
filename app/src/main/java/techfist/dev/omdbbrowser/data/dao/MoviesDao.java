package techfist.dev.omdbbrowser.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.response.Movie;

/**
 * DAO class, provides interfaces to interact with underlying  {@link Movie} table
 *
 * it uses(for query, atleast) RX conversions
 *
 * as of now Room does not support RX for insert, delete and update.
 */
@Dao
public interface MoviesDao {

    @Query("select count(*) from movie")
    Single<Integer> count();

    @Query("SELECT * FROM movie ORDER BY created ASC LIMIT :limit")
    @WorkerThread
    Single<List<Movie>> loadMovies(int limit);

    @Query("SELECT * FROM movie where created > :date ORDER BY created ASC LIMIT :limit")
    @WorkerThread
    Single<List<Movie>> loadMoviesByTimeStamp(long date, int limit);

    @Query("SELECT * FROM movie WHERE id = :id")
    @WorkerThread
    Single<Movie> loadMovieById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    long insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    long[] insertAll(List<Movie> movieList);

    @Delete
    int delete(Movie movie);

    @Delete
    int deleteAll(List<Movie> movieList);

    @Query("DELETE FROM movie")
    int clear();
}
