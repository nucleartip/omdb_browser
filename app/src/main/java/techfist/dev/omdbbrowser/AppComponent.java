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
 * bind's all the modules together into a component
 */

@AppComponent.AppScope
@Component(modules = {AppModule.class, NetModule.class, UIBuilderModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MoviesBrowserApplication> {

    /**
     * Simple annotation to define if a dependency will remain available to app wide scope
     */
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface AppScope {
    }

    /**
     * Simple annotation to define if a dependency will remain available to only current screen scope
     */
    @Scope
    @interface ScreenScope {
    }
}
