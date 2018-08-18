package techfist.dev.omdbbrowser.utils;

/**
 *
 * a lifecycle framework to work closely with android activity lifecycle,
 * am not using android Lifecycle arch, as last I check it was still in beta mode
 * otherwise we can leverage on that by using ViewModel, LiveData, LifecycleActivity etc.
 *
 */

public interface LifecycleObserver {

    /**
     * Similar to @class {@link android.app.Activity} onCreate
     */
    void onScreenInit();

    /**
     * Similar to @class {@link android.app.Activity} onResume
     */
    void onScreenVisible();

    /**
     * Similar to @class {@link android.app.Activity} onPause
     */
    void onScreenGone();

    /**
     * Similar to @class {@link android.app.Activity} onDestroyed
     */
    void onScreenDestroyed();

}
