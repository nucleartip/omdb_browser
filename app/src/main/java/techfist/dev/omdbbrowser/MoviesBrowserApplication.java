package techfist.dev.omdbbrowser;

import android.app.Activity;
import android.app.Application;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import techfist.dev.omdbbrowser.di.AppModule;

/**
 * initializes application dependency graph,
 * most of the dependency related wiring is done automatically by Dagger
 * you can read more about it here https://google.github.io/dagger/users-guide
 */

public class MoviesBrowserApplication extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
