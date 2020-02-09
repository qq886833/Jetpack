package com.bsoft.libnet.api;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSON;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libnet.apiservice.UpLoadService;
import com.bsoft.libnet.base.NetworkApi;
import com.bsoft.libnet.model.BaseResponse;
import com.bsoft.libnet.model.ProgressRequestBody;
import com.bsoft.libnet.model.UploadingVo;
import com.bsoft.libnet.observer.BaseUpLoadObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class NetUpLoadApi extends NetworkApi {



    private static volatile NetUpLoadApi sInstance;

    public static NetUpLoadApi getInstance() {
        if (sInstance == null) {
            synchronized (NetUpLoadApi.class) {
                if (sInstance == null) {
                    sInstance = new NetUpLoadApi();
                }
            }
        }
        return sInstance;
    }

    public static  <T> T getService(Class<T> service) {
        return getInstance().getRetrofit().create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    public String getCurrentUrl() {
        return HttpConstants.httpApiUrl;
    }



    @SuppressLint("CheckResult")
    public <T extends BaseResponse> void upload(@NonNull String url,
                                                ArrayMap<String, String> heads, ArrayMap<String, String> params,
                                                ArrayList<String> filePaths, Class<T> clazz,
                                                BaseUpLoadObserver<T> observer) {
        Observable observable = upload( url, heads, params, filePaths, clazz);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @param
     * @param url
     * @param heads
     * @param params
     * @param filePaths
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressLint("CheckResult")
    private <T extends BaseResponse> Observable<Object> upload(String url,
                                                               ArrayMap<String, String> heads, ArrayMap<String, String> params, ArrayList<String> filePaths, final Class<T> clazz) {
        final Subject<Object> subject = PublishSubject.create();

        ArrayMap<String, RequestBody> requestBodys = new ArrayMap<>();
        if (filePaths != null && !filePaths.isEmpty()) {
            long totalSize = 0;
            UploadingVo uploading = new UploadingVo();

            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                try {
                    totalSize = totalSize + requestBody.contentLength();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, uploading, subject);
                requestBodys.put("file\"; filename=\"" + file.getName(), progressRequestBody);
            }

            uploading.setTotalSize(totalSize);
        }

        ArrayMap<String, RequestBody> map = new ArrayMap<>();
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    map.put(key, RequestBody.create(MediaType.parse("form-data"), params.get(key)));
                }
            }
        }

        Observable<T> observable = getService(UpLoadService.class)
                .upload(url, heads, map, requestBodys)
                .map(new Function<String, T>() {
                    @Override
                    public T apply(String s) {
                        return JSON.parseObject(s, clazz);
                    }
                })
                .subscribeOn(Schedulers.io());

        observable.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                subject.onSubscribe(d);
            }

            @Override
            public void onNext(T value) {
                subject.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                subject.onError(e);
            }

            @Override
            public void onComplete() {
                subject.onComplete();
            }
        });


        return subject;
    }

}
