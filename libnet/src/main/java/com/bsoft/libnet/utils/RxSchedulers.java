package com.bsoft.libnet.utils;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> observableIO2Main(final Context context) {
        return upstream -> {
            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            return observable;
           // return composeContext(context, observable);
        };
    }

 /*   public static <T> ObservableTransformer<T, T> observableIO2Main(final RxFragment fragment) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).compose(fragment.<T>bindToLifecycle());
    }

    private static <T> ObservableSource<T> composeContext(Context context, Observable<T> observable) {
        if(context instanceof RxActivity) {
            return observable.compose(((RxActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else if(context instanceof RxFragmentActivity){
            return observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        }else if(context instanceof RxAppCompatActivity){
            return observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        }else {
            return observable;
        }
    }*/

}
