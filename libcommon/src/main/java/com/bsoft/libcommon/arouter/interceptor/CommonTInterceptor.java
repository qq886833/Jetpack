package com.bsoft.libcommon.arouter.interceptor;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bsoft.libbasic.init.BaseAppInit;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.localdata.AccountSharpref;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


@Interceptor(priority = CommonArouterGroup.PRIORITY)
public class CommonTInterceptor implements IInterceptor {
    public static final int GREEN_ALL = Integer.MAX_VALUE;//green channel
    public static final int GREEN_LOGIN = 1000;//登录
    public static final int GREEN_COMPLETE = 900;//完善信息

    private ObservableEmitter<IncepterPost> emitter;

    /**
     * The operation of this tollgate.
     *
     * @param postcard meta
     * @param callback cb
     */
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        //验证是否是本项目
        if (!TextUtils.equals(postcard.getName(), CommonArouterGroup.AROUTER_NAME)) {
            callback.onContinue(postcard);
            return;
        }

        //是否登录
        if (postcard.getExtra() >= GREEN_LOGIN) {// 如果已经登录不拦截
            callback.onContinue(postcard);
            return;
        }

        if (AccountSharpref.getInstance().getLoginState()) {


        }else {
            callback.onInterrupt(null);

            IncepterPost post = new IncepterPost();
            post.setMode(IncepterPost.NEED_LOGIN);
            post.setPostcard(postcard);
            emitter.onNext(post);
        }

       /* if (AccountSharpref.getInstance().getLoginState()) {
            //是否完善信息
            if (postcard.getExtra() >= GREEN_COMPLETE) {
                callback.onContinue(postcard);
            } else if (LocalDataUtil.getInstance().isUserInfoPerfect()) {
                callback.onContinue(postcard);
            } else {
                callback.onInterrupt(null);

                IncepterPost post = new IncepterPost();
                post.setMode(IncepterPost.USER_INFO_ERROR);
                post.setPostcard(postcard);
                emitter.onNext(post);
            }
        } else {
            callback.onInterrupt(null);

            IncepterPost post = new IncepterPost();
            post.setMode(IncepterPost.NEED_LOGIN);
            post.setPostcard(postcard);
            emitter.onNext(post);
        }*/
    }

    /**
     * Do your init work in this method, it well be call when processor has been load.
     *
     * @param context ctx
     */
    @Override
    public void init(Context context) {
        Observable
                .create(new ObservableOnSubscribe<IncepterPost>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<IncepterPost> e) throws Exception {
                        emitter = e;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IncepterPost>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull IncepterPost incepterPost) {
                        Postcard postcard = incepterPost.getPostcard();
                        if (incepterPost.getMode() == IncepterPost.NEED_LOGIN) {
                            if (BaseAppInit.getInstance().getListener() != null && postcard != null) {
                                BaseAppInit.getInstance().getListener().needLogin(postcard.getPath(), postcard.getExtras());
                            }
                        } else if (incepterPost.getMode() == IncepterPost.USER_INFO_ERROR) {
                            if (BaseAppInit.getInstance().getListener() != null && postcard != null) {
                                BaseAppInit.getInstance().getListener().userInfoError(postcard.getPath(), postcard.getExtras());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}

