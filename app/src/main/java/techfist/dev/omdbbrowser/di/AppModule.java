package techfist.dev.omdbbrowser.di;

import android.content.Context;
import android.support.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.data.ResourceProvider;

/**
 * you can read more about dependency injection here, for this app am using
 * one of most popular solution Dagger https://google.github.io/dagger/users-guide
 *
 * provides app-wide dependencies.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(@Nullable Context context){
        this.context = context;
    }

    @AppComponent.AppScope
    @Provides
    ResourceProvider provideResourceProvider(){
        return new ResourceProvider(context);
    }

}
