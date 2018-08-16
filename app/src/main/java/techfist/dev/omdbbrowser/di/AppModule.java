package techfist.dev.omdbbrowser.di;

import android.content.Context;
import android.support.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.data.ResourceProvider;

/**
 *
 * provide dependencies required over the life cycle of app
 * Created by Nucleartip on 5/3/18.
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
