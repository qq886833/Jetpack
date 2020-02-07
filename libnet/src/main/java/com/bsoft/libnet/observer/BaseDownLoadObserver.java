package com.bsoft.libnet.observer;

import com.bsoft.libnet.model.DownloadingVo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseDownLoadObserver implements Observer<DownloadingVo> {
    protected abstract void onHandlePrePare(Disposable var1);

    protected abstract void onHandleDownloading(DownloadingVo var1);

    protected abstract void onHandleSuccess(DownloadingVo var1);

    protected abstract void onHandleError(Throwable e);

    protected abstract void onHandleComplete();

    protected BaseDownLoadObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.onHandlePrePare(d);
    }



    @Override
    public void onNext(DownloadingVo value) {
        if(value.isDone()){
            onHandleSuccess(value);
        }else{
            onHandleDownloading(value);
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
