package techfist.dev.omdbbrowser.di;


import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.api.service.MoviesApi;
import techfist.dev.omdbbrowser.api.service.MoviesService;
import techfist.dev.omdbbrowser.data.MoviesRepository;
import techfist.dev.omdbbrowser.data.OfflineMovieRepository;
import techfist.dev.omdbbrowser.data.OnlineMoviesRepository;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.data.dao.MoviesDao;
import techfist.dev.omdbbrowser.data.dao.MoviesDatabase;
import techfist.dev.omdbbrowser.ui.detail.MovieDetailActivity;
import techfist.dev.omdbbrowser.ui.detail.MovieDetailViewModel;
import techfist.dev.omdbbrowser.utils.NetworkConnection;

/**
 * you can read more about dependency injection here, for this app am using
 * one of most popular solution Dagger https://google.github.io/dagger/users-guide
 *
 * provides dependencies required during lifecycle of Activity MovieDetailActivity
 *
 */
@AppComponent.ScreenScope
@Subcomponent(modules = {MovieDetailActivityComponent.MovieDetailActivityModule.class})
public interface MovieDetailActivityComponent extends AndroidInjector<MovieDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MovieDetailActivity> {}

    @Module
    class MovieDetailActivityModule {

        @AppComponent.ScreenScope
        @Provides
        static OnlineMoviesRepository provideOnlineMoviesRepository(MoviesService moviesService) {
            return new OnlineMoviesRepository(moviesService, MoviesApi.API_KEY);
        }

        @AppComponent.ScreenScope
        @Provides
        static OfflineMovieRepository provideOfflineMovieRepository(MoviesDao moviesDao) {
            return new OfflineMovieRepository(moviesDao);
        }

        @AppComponent.ScreenScope
        @Provides
        static MoviesRepository provideMoviesRepository(NetworkConnection networkConnection, OnlineMoviesRepository
                onlineMoviesRepository, OfflineMovieRepository offlineMovieRepository){
            return new MoviesRepository(networkConnection, onlineMoviesRepository, offlineMovieRepository);
        }

        @AppComponent.ScreenScope
        @Provides
        static MovieDetailViewModel provideMoviesActivityViewModel(ResourceProvider resourceProvider, MoviesRepository moviesRepository) {
            return new MovieDetailViewModel(moviesRepository, resourceProvider);
        }

    }
}
