package techfist.dev.omdbbrowser.di;

import android.app.Activity;



import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import techfist.dev.omdbbrowser.ui.detail.MovieDetailActivity;
import techfist.dev.omdbbrowser.ui.landing.MoviesActivity;

/**
 * you can read more about dependency injection here, for this app am using
 * one of most popular solution Dagger https://google.github.io/dagger/users-guide
 *
 * provides UI components
 */
@SuppressWarnings("unused")
@Module(subcomponents = {MoviesActivityComponent.class, MovieDetailActivityComponent.class})
public abstract class UIBuilderModule {

    @Binds
    @IntoMap
    @ActivityKey(MoviesActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> provideMainActivityBuilder(MoviesActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(MovieDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> provideMovieDetailActivityBuilder(MovieDetailActivityComponent.Builder builder);
}
