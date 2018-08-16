package techfist.dev.omdbbrowser;




import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import techfist.dev.omdbbrowser.di.AppModule;
import techfist.dev.omdbbrowser.di.NetModule;
import techfist.dev.omdbbrowser.di.UIBuilderModule;

/**
 * Created by Nucleartip on 5/3/18.
 */

@AppComponent.AppScope
@Component(modules = {AppModule.class, NetModule.class, UIBuilderModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MoviesBrowserApplication> {
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface AppScope {
    }

    @Scope
    @interface ScreenScope {
    }
}
