package techfist.dev.omdbbrowser.utils;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Android data binding @class {@link android.databinding.ObservableBoolean} wrapped in Rx @class {@link io.reactivex.Observable}
 * has capability of both
 * <p>
 * Created by Nucleartip on 6/3/18.
 */

public class RxObservableField<T> extends ObservableField<T> {

    public RxObservableField(@NonNull T value) {
        super(value);
    }

    public Observable<T> asObservable() {
        return RxUtils.asObservable(this);
    }
}
