package com.bsoft.libnet.utils;


import com.bsoft.libnet.errorhandler.ApiErrorHandler;
import com.bsoft.libnet.errorhandler.HttpErrorHandler;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/10/29
 * Description:
 */
public class RxUtil {

    /**
     * 线程调度器
     */

    public static <T> ObservableTransformer<T, T> observableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applySchedules() {
        return upstream -> {
            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.map(new ApiErrorHandler<T>());
            observable.onErrorResumeNext(new HttpErrorHandler<T>());
            return observable;
        };

    }

}
