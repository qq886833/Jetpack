package com.bsoft.libnet.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserverx<T> implements Observer<T> {
    public abstract void onHandlePrePare(Disposable d);
    protected abstract void onHandleSuccess(T value);
    protected abstract void onHandleError(Throwable e);
    protected abstract void onHandleComplete();

    protected BaseObserverx() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onHandlePrePare(d);
    }

    @Override
    public void onNext(T value) {

        onHandleSuccess(value);

    }

    @Override
    public void onError(Throwable e) {
        onHandleError(e);
    }

    @Override
    public void onComplete() {
        onHandleComplete();
    }
}
