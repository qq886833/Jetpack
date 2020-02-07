package com.bsoft.libnet.api;

import android.annotation.SuppressLint;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bsoft.libnet.apiservice.PostService;
import com.bsoft.libnet.base.NetworkApi;
import com.bsoft.libnet.constants.HttpConstants;
import com.bsoft.libnet.errorhandler.ApiErrorHandler;
import com.bsoft.libnet.errorhandler.HttpErrorHandler;
import com.bsoft.libnet.model.BaseResponse;
import com.bsoft.libnet.model.NullResponse;
import com.bsoft.libnet.observer.BaseObserver;
import com.bsoft.libnet.observer.BaseObserver2;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NetPostApi extends NetworkApi {


    private static volatile NetPostApi sInstance;


    public static NetPostApi getInstance() {
        if (sInstance == null) {
            synchronized (NetPostApi.class) {
                if (sInstance == null) {
                    sInstance = new NetPostApi();
                }
            }
        }
        return sInstance;
    }

    public <T> T getService(Class<T> service) {
        return getInstance().getRetrofit().create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Access-Token", "57cebcb3-5870-40e3-acc1-5ca3edaa8ed5");
                return chain.proceed(builder.build());
            }
        };
    }


    @Override
    public String getCurrentUrl() {
        return HttpConstants.httpApiUrl;
    }

    /**
     * baseUrl+url 或者 url
     * post 请求
     *
     * @param heads
     * @param body
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void post(String url, ArrayMap<String, String> heads, Object body, BaseObserver<NullResponse> observer) {

        Observable observable = getService(PostService.class)
                .post(url, heads, body)

                .map(new Function<String, NullResponse>() {
                    @Override
                    public NullResponse apply(String s) {
                        NullResponse response = new NullResponse();
                        response.setCode(BaseResponse.SUCCESS);
                        return response;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ApiErrorHandler<NullResponse>());
        observable.onErrorResumeNext(new HttpErrorHandler<T>());
        observable.subscribe(observer);
    }

    /**
     * baseUrl+url 或者 url
     * post 请求
     *
     * @param heads
     * @param body
     * @param observer
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void post(String url, ArrayMap<String, String> heads, Object body,
                         final Class<T> clazz, BaseObserver<T> observer) {

        Observable observable = getService(PostService.class)
                .post(url, heads, body)
                .map(new Function<String, BaseResponse<T>>() {
                    @Override
                    public BaseResponse<T> apply(String s) throws Exception {

                        return convert(s, clazz);
                    }
                }).compose(applySchedulers(observer));


    }
//   .compose(RxSchedulers.observableIO2Main(this))
    //********************************  post 2**********************************

    /**
     * baseUrl+url 或者 url
     * post 请求
     *
     * @param heads
     * @param body
     * @param observer
     * @param <T>
     */
  //    T extends Number 上界限定
    @SuppressLint("CheckResult")
    public <T extends BaseResponse> void post(String url, ArrayMap<String, String> heads, Object body,
                                              final Class<T> clazz, BaseObserver2<T> observer) {

        Observable observable = getService(PostService.class)
                .post(url, heads, body)
                .map(new Function<String, T>() {
                    @Override
                    public T apply(String s) {
                        return JSON.parseObject(s, clazz);

                    }
                }).compose(applySchedulers(observer));

    }

    /**
     * baseUrl+url 或者 url
     * post list 请求
     *
     * @param heads
     * @param body
     * @param observer
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void postList(String url, ArrayMap<String, String> heads, Object body,
                             final Class<T> clazz, BaseObserver<ArrayList<T>> observer) {
        Observable observable = getService(PostService.class)
                .post(url, heads, body)
                .map(new Function<String, BaseResponse<ArrayList<T>>>() {
                    @Override
                    public BaseResponse<ArrayList<T>> apply(String s) throws Exception {
                        return convertList(s, clazz);
                    }
                }).compose(applySchedulers(observer));


    }


    private <T> BaseResponse<T> convert(String s, Class<T> clazz) {
        //String to JSON TypeReference 复杂类型转换
        BaseResponse<T> baseResponse = JSON.parseObject(s, new TypeReference<BaseResponse<T>>() {
        });
        //JSON to String
        String detail = JSON.toJSONString(baseResponse.getData());
        baseResponse.setData(JSON.parseObject(detail, clazz));
        return baseResponse;
    }

    private <T> BaseResponse<ArrayList<T>> convertList(String s, Class<T> clazz) {
        BaseResponse<ArrayList<T>> baseResponse = JSON.parseObject(s, new TypeReference<BaseResponse<ArrayList<T>>>(){}.getType());
        String detail = JSON.toJSONString(baseResponse.getData());
        ArrayList<T> arrayList = new ArrayList<>();
        List<T> list = JSON.parseArray(detail, clazz);
        if (list != null) {
            arrayList.addAll(list);
        }
        baseResponse.setData(arrayList);
        return baseResponse;

    }


}

