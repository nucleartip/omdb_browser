package techfist.dev.omdbbrowser.utils;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

/**
 * Created by Nucleartip on 6/3/18.
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
     * api checked for current status of desposable
     * @param disposables desposable need to be checked for there status
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
     * Takes an android data binding @class {@link android.databinding.ObservableField} and return asn equivalent
     * Rx version of same.
     *
     * @param observableField<T>
     * @param <T>
     * @return
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
                                emitter.onError(e);
                            }
                        }
                    };
            observableField.addOnPropertyChangedCallback(callback);
            emitter.setCancellable(new Cancellable() {
                @Override public void cancel() throws Exception {
                    observableField.removeOnPropertyChangedCallback(callback);
                }
            });


        });

    }

}
