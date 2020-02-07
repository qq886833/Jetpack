package com.bsoft.libnet.observer;


import com.bsoft.libnet.model.BaseResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    public abstract void onHandlePrePare(Disposable d);
    protected abstract void onHandleSuccess(T value);
    protected abstract void onHandleError(Throwable e);
    protected abstract void onHandleComplete();

    protected BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onHandlePrePare(d);
    }

    @Override
    public void onNext(BaseResponse<T> value) {
        if (value.isSuccess()) {
            onHandleSuccess(value.getData());
        }
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
