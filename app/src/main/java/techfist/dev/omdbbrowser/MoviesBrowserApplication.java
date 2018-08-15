package techfist.dev.omdbbrowser;

import android.app.Activity;
import android.app.Application;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import techfist.dev.omdbbrowser.di.AppModule;

/**
 * initializes dependency graph
 * Created by Nucleartip on 5/3/18.
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
