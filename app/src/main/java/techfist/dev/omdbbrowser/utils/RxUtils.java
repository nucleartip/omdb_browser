package techfist.dev.omdbbrowser.utils;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Utility class providing generic apis
 */

public class RxUtils {

    /**
     * unsubscribe from one or many subscriptions
     * @param disposables disposables need to be deposed
     */
    public static void unSubscribeSubscription(Disposable... disposables) {

        for (Disposable disposable : disposables) {
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    /**
     * api checkes for current status of desposable
     * @param disposables disposable need to be checked for there status
     * @return boolean to indicate
     */
    public static boolean isSubscriptionRunning(final Disposable... disposables) throws NullPointerException {

        if (disposables == null) {
            return false;
        }

        for (Disposable disposable : disposables) {
            if (disposable == null || disposable.isDisposed()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Takes an android data binding @class {@link android.databinding.ObservableField} and return ann equivalent
     * Rx version of same.
     *
     * @param observableField {@link ObservableField<T>}
     * @return {@link Observable<T>} rX
     */
    public static <T> Observable<T> asObservable(@NonNull final ObservableField<T> observableField) {


        return Observable.create(emitter -> {
            if (!emitter.isDisposed()) {
                emitter.onNext(observableField.get());
            }

            final android.databinding.Observable.OnPropertyChangedCallback callback =
                    new android.databinding.Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(android.databinding.Observable observable, int i) {

                            try {
                                if (!emitter.isDisposed()) {
                                    emitter.onNext(observableField.get());
                                }
                            } catch (final Exception e) {
                                emitter.tryOnError(e);
                            }
                        }
                    };
            observableField.addOnPropertyChangedCallback(callback);
            emitter.setCancellable(() -> observableField.removeOnPropertyChangedCallback(callback));
        });
    }
}