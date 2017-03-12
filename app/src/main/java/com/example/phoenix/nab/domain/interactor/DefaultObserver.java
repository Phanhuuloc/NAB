package com.example.phoenix.nab.domain.interactor;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Phoenix on 3/11/17.
 */

public class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {
        // no-op by default.
    }

    @Override
    public void onComplete() {
        // no-op by default.
    }

    @Override
    public void onError(Throwable exception) {
        // no-op by default.
    }
}
