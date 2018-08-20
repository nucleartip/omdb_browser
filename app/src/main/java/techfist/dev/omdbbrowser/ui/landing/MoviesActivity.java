package techfist.dev.omdbbrowser.ui.landing;

import android.app.ProgressDialog;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import techfist.dev.omdbbrowser.BR;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.utils.LifecycleActivity;
import techfist.dev.omdbbrowser.utils.LifecycleObserver;
import techfist.dev.omdbbrowser.utils.RxUtils;

/**
 * Movies list screen, displays list of popular movies from TMDB database
 * Lazy loads the movies, loads more upon further scroll.
 *
 * Works using Android data binding library.
 * Associated ViewModel class is {@link MoviesActivityViewModel}
 *
 * it subscribes to publicly available observables exposed via view model,
 * and performs actions such as displaying of progress bar or toast etc.
 *
 *
 * All UI related task are being performed here
 * All business logic is in corresponding ViewModel class
 *
 * Clean MVVM design pattern is followed here
 *
 */
public class MoviesActivity extends LifecycleActivity {
    private static String TAG = MoviesActivity.class.getSimpleName();

    @Inject MoviesActivityViewModel moviesActivityViewModel;

    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private Disposable actions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movies;
    }

    @Override
    public LifecycleObserver getLifecycleObserver() {
        return moviesActivityViewModel;
    }

    @Override
    public void onScreenInit(@Nullable Bundle savedInstanceState, @Nullable Bundle extras, @Nullable ViewDataBinding viewDataBinding) {
        if (viewDataBinding != null) {
            viewDataBinding.setVariable(BR.vm, moviesActivityViewModel);
            initActions();
            // could have used Butterknife for this, but seemed like overkill
            coordinatorLayout = findViewById(R.id.coordinatorLayout);
            return;
        }

        finish();
    }


    @VisibleForTesting
    void showProgress(String text) {

        hideProgress();
        if (progressDialog == null) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

        }

        progressDialog.setMessage(text);
        progressDialog.show();
    }

    @VisibleForTesting
    void hideProgress() {

        if (progressDialog == null || !progressDialog.isShowing()) {
            Log.d(TAG, "dialog is already dismissed");
            return;
        }

        Log.d(TAG, "dialog has been dismissed");
        progressDialog.dismiss();

    }


    /**
     * chains multiple Rx observable streams together
     */
    private void initActions() {
        actions = Observable.merge(observeProgressDialogAction(), observeError(), observeSnackBar())
                .subscribe(Functions.emptyConsumer(), (throwable -> Log.d(TAG, throwable.getMessage(), throwable)));
    }

    /**
     * observes if loading progress dialog need to be displayed
     * if source observable emits a non null string, progress dialog is made visible
     * otherwise its made invisible
     *
     * @return useless
     */
    @VisibleForTesting
    Observable<String> observeProgressDialogAction() {

        return moviesActivityViewModel.loadingText.asObservable()
                .doOnNext((text) -> {
                    if (TextUtils.isEmpty(text)) {
                        hideProgress();
                        return;
                    }
                    showProgress(text);
                });

    }

    /**
     * observes if error toast need to be shown
     * if source observable emits a non null string, error toast is shown
     *
     * @return useless
     */
    @VisibleForTesting
    Observable<String> observeError() {

        return moviesActivityViewModel.errorText.asObservable()
                .filter((text) -> !TextUtils.isEmpty(text))
                .doOnNext(this::showErrorToast);
    }

    /**
     * observes if snack bar needs to be display notifying further loading of content
     * if source observable true boolean snackbar is shown, otherwise its made invisible
     *
     * @return useless
     */
    @VisibleForTesting
    Observable<Boolean> observeSnackBar() {

        return moviesActivityViewModel.showLoadingMore.asObservable()
                .filter((display) -> {
                    if (!display && snackbar != null) {
                        snackbar.dismiss();
                    }
                    return display;
                })
                .doOnNext((display) -> {

                    if (snackbar == null) {
                        snackbar = Snackbar.make(coordinatorLayout, R.string.loading_loading_up_more, Snackbar.LENGTH_INDEFINITE);
                    }
                    snackbar.show();
                });
    }

    @Override
    protected void onDestroy() {
        RxUtils.unSubscribeSubscription(actions);
        hideProgress();
        super.onDestroy();
    }

    private void showErrorToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show();
    }
}
