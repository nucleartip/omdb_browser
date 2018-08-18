package techfist.dev.omdbbrowser.ui.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
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
 * Details screen, displays requested movie in detail
 *
 * Works using Android data binding library.
 * Associated ViewModel class is {@link MovieDetailViewModel}
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
public class MovieDetailActivity extends LifecycleActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private static final String KEY_MOVIE_ID = "asdkajsdasmd";

    /**
     * static API to obtains a valid intent to interact with this activity
     * @param context value non null context
     * @param id id of movide for which details is requsted
     * @return valid intent, which can be used to invoke this activity
     */
    public static Intent newIntent(@NonNull Context context, long id) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(KEY_MOVIE_ID, id);
        return intent;
    }

    @Inject MovieDetailViewModel movieDetailViewModel;

    private ProgressDialog progressDialog;
    private Disposable actions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public LifecycleObserver getLifecycleObserver() {
        return movieDetailViewModel;
    }

    @Override
    public void onScreenInit(@Nullable Bundle savedInstanceState, @Nullable Bundle extras, @Nullable ViewDataBinding viewDataBinding) {
        if (viewDataBinding != null && extras != null && extras.containsKey(KEY_MOVIE_ID)) {
            styleActionBar();
            viewDataBinding.setVariable(BR.vm, movieDetailViewModel);
            initActions();
            movieDetailViewModel.movieId.set(extras.getLong(KEY_MOVIE_ID, -1));
            return;
        }
        finish();
    }

    void initActions() {
        actions = Observable.merge(observeProgressDialogAction(), observeError())
                .subscribe(Functions.emptyConsumer(), (throwable -> Log.d(TAG, throwable.getMessage(), throwable)));
    }

    @Override
    protected void onDestroy() {
        RxUtils.unSubscribeSubscription(actions);
        hideProgress();
        super.onDestroy();
    }

    @VisibleForTesting
    Observable<String> observeProgressDialogAction() {
        return movieDetailViewModel.loadingText.asObservable()
                .doOnNext((text) -> {
                    if (TextUtils.isEmpty(text)) {
                        hideProgress();
                        return;
                    }
                    showProgress(text);
                });

    }

    @VisibleForTesting
    Observable<String> observeError() {
        return movieDetailViewModel.errorText.asObservable()
                .filter((text) -> !TextUtils.isEmpty(text))
                .doOnNext(this::showErrorToast);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showErrorToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show();
        finish();
    }

    private void styleActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }
}