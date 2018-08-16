package techfist.dev.omdbbrowser.di;


import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.api.service.MoviesApi;
import techfist.dev.omdbbrowser.api.service.MoviesService;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.data.MoviesRepository;
import techfist.dev.omdbbrowser.ui.landing.MoviesActivity;
import techfist.dev.omdbbrowser.ui.landing.MoviesActivityViewModel;
import techfist.dev.omdbbrowser.ui.landing.MoviesAdapter;


@AppComponent.ScreenScope
@Subcomponent(modules = {MoviesActivityComponent.MoviesActivityModule.class})
public interface MoviesActivityComponent extends AndroidInjector<MoviesActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MoviesActivity> {}

    @Module
    class MoviesActivityModule {

        @AppComponent.ScreenScope
        @Provides
        static MoviesRepository provideSearchManager(MoviesService moviesService) {
            return new MoviesRepository(moviesService, MoviesApi.API_KEY);
        }

        @AppComponent.ScreenScope
        @Provides
        static MoviesAdapter provideMoviesAdapter() {
            return new MoviesAdapter();
        }

        @AppComponent.ScreenScope
        @Provides
        static MoviesActivityViewModel provideMoviesActivityViewModel(MoviesAdapter moviesAdapter,
                                                                      ResourceProvider resourceProvider,
                                                                      MoviesRepository moviesRepository) {
            return new MoviesActivityViewModel(moviesRepository, resourceProvider, moviesAdapter);
        }

    }
}
