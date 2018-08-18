package techfist.dev.omdbbrowser.utils;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Android data binding @class {@link android.databinding.ObservableField<T>} wrapped in Rx @class {@link io.reactivex.Observable}
 * has capability of both
 *
 * this am doing so that ObservableFields can be easily fit into Rx chains
 */

public class RxObservableField<T> extends ObservableField<T> {

    public RxObservableField(@NonNull T value) {
        super(value);
    }

    public Observable<T> asObservable() {
        return RxUtils.asObservable(this);
    }
}
