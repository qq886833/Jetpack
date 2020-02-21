package com.bsoft.libcommon.commonaop;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * 处理子线程切换的 切面
 */
@Aspect
public class MainAspect {

    @Around("execution(@com.bsoft.libcommon.commonaop.Main void *(..))")
    public void zsdaasdas(final ProceedingJoinPoint joinPoint) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    //执行真实的方法
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }

}
