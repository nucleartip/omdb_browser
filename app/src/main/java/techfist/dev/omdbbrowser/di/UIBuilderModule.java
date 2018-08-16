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
 * maintains binding of various ui components
 * Created by Nucleartip on 5/3/18.
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
