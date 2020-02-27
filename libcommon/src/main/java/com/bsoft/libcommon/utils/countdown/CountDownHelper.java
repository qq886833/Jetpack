package com.bsoft.libcommon.utils.countdown;


import android.widget.TextView;
import androidx.annotation.ColorInt;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/13.
 * 倒计时
 */
public class CountDownHelper {

    CompositeDisposable disposables;

    TextView tv;
    int count;
    
    String originStr;
    String targetStr;
    
    int originTxtColor;
    int targetTxtColor;
    
    int originBg;
    int targetBg;

    public CountDownHelper(TextView tv, int count, String originStr, String targetStr, @ColorInt int originTxtColor, @ColorInt int targetTxtColor, int originBg, int targetBg) {
        this.tv = tv;
        this.count = count;
        this.originStr = originStr;
        this.targetStr = targetStr;
        this.originTxtColor = originTxtColor;
        this.targetTxtColor = targetTxtColor;
        this.originBg = originBg;
        this.targetBg = targetBg;
    }

    public void start(){

        disposables = new CompositeDisposable();
        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
                .take(count)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        tv.setEnabled(false);
                        tv.setTextColor(targetTxtColor);
                        tv.setBackgroundResource(targetBg);
                    }
                })
                
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        );
        
    }
    
    private DisposableObserver<Long> getObserver(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {

                try {
                    tv.setText(targetStr + "(" + (count-aLong) + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                if(disposables.isDisposed()){
                    restore();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                restore();
            }

            @Override
            public void onComplete() {
                restore();
            }
        };
    }
    
    public void clear(){
        if(disposables != null){
            
            disposables.dispose();

        }
        restore();
    }

    private void restore() {
        try {
            tv.setText(originStr);
            tv.setEnabled(true);
            tv.setTextColor(originTxtColor);
            tv.setBackgroundResource(originBg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void countDown(LifecycleTransformer<Long> longLifecycleTransformer,
//                                 final TextView tv, final int count, 
//                                 final String originStr, final String targetStr, 
//                                 final int originTxtColor, final int targetTxtColor, 
//                                 final int originBg, final int targetBg) {
//        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .take(count)
//                .compose(longLifecycleTransformer)
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        RxView.enabled(tv).accept(false);
//                        RxTextView.color(tv).accept(targetTxtColor);
//                        tv.setBackgroundResource(targetBg);
//                    }
//                })
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        try {
//                            RxTextView.text(tv).accept(targetStr + "(" + (count-aLong) + ")");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        try {
//                            RxTextView.text(tv).accept(originStr);
//                            RxView.enabled(tv).accept(true);
//                            RxTextView.color(tv).accept(originTxtColor);
//                            tv.setBackgroundResource(originBg);
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        try {
//                            RxTextView.text(tv).accept(originStr);
//                            RxView.enabled(tv).accept(true);
//                            RxTextView.color(tv).accept(originTxtColor);
//                            tv.setBackgroundResource(originBg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//    }
        
}
