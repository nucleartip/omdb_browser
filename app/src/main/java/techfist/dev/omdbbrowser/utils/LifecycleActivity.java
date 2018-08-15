package techfist.dev.omdbbrowser.utils;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * base activity which does some generic work, plus gets a lifecycle observer and notifies it regarding ongoing
 * lifecycle changes, as of now this API only support single observer, it can changed to return a list of observer
 * in that case entire list of observer will be notified.
 * <p>
 * Created by Nucleartip on 5/3/18.
 */

public abstract class LifecycleActivity extends DaggerAppCompatActivity {


    abstract public int getLayoutId();

    abstract public LifecycleObserver getLifecycleObserver();

    abstract public void onScreenInit(@Nullable Bundle savedInstanceState,
                                      @Nullable Bundle extras,
                                      @Nullable ViewDataBinding viewDataBinding);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutId());
        onScreenInit(savedInstanceState, getIntent().getExtras(), binding);
        if (getLifecycleObserver() != null) {
            getLifecycleObserver().onScreenInit();
        }
    }

    @Override
    protected void onResume() {
        if (getLifecycleObserver() != null) {
            getLifecycleObserver().onScreenVisible();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (getLifecycleObserver() != null) {
            getLifecycleObserver().onScreenGone();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (getLifecycleObserver() != null) {
            getLifecycleObserver().onScreenDestroyed();
        }
        super.onDestroy();
    }
}
