package techfist.dev.omdbbrowser.data.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import techfist.dev.omdbbrowser.api.response.Movie;

/**
 * creates a movie data base with table movie
 */
@Database(entities = {Movie.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
