package com.bsoft.libnet.observer;

import com.bsoft.libnet.model.BaseResponse;
import com.bsoft.libnet.model.UploadingVo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseUpLoadObserver<T extends BaseResponse>  implements Observer<Object> {
    protected abstract void onHandlePrePare(Disposable d);

    protected abstract void onHandleUploading(UploadingVo value);

    protected abstract void onHandleSuccess(T value);

    protected abstract void onHandleError(Throwable e);

    protected abstract void onHandleComplete();

    protected BaseUpLoadObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onHandlePrePare(d);
    }

    @Override
    public void onNext(Object value) {
        if(value instanceof UploadingVo){
            onHandleUploading((UploadingVo)value);
        }else{
            T t =(T) value;
            if(t.isSuccess()){
                onHandleSuccess(t);
            }
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
