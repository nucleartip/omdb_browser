package techfist.dev.omdbbrowser.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.data.dao.MoviesDao;
import techfist.dev.omdbbrowser.data.dao.MoviesDatabase;
import techfist.dev.omdbbrowser.utils.NetworkConnection;

/**
 * you can read more about dependency injection here, for this app am using
 * one of most popular solution Dagger https://google.github.io/dagger/users-guide
 * <p>
 * provides app-wide dependencies.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(@Nullable Context context) {
        this.context = context;
    }

    @AppComponent.AppScope
    @Provides
    ResourceProvider provideResourceProvider() {
        return new ResourceProvider(context);
    }

    @Provides
    @AppComponent.AppScope
    MoviesDao provideMoviesDao() {
        return Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "movie_database")
                .build()
                .moviesDao();
    }

    @Provides
    @AppComponent.AppScope
    NetworkConnection provideNetworkConnection() {
        return new NetworkConnection(context);
    }

}
