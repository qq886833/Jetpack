package com.bsoft.libnet.api;

import android.annotation.SuppressLint;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libnet.apiservice.GetService;
import com.bsoft.libnet.base.NetworkApi;
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
import java.util.Map;


public class NetGetApi extends NetworkApi {



    private static volatile NetGetApi sInstance;

    public static NetGetApi getInstance() {
        if (sInstance == null) {
            synchronized (NetGetApi.class) {
                if (sInstance == null) {
                    sInstance = new NetGetApi();
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


    //********************************  get return null **********************************

    /**
     * baseUrl+url 或者 url
     * get 请求
     *
     * @param heads
     * @param body
     * @param observer
     */
    @SuppressLint("CheckResult")
    public <T> void get(String url, Map<String, String> heads, Map<String, Object> body,
                    BaseObserver<NullResponse> observer) {
        Observable observable  = getService(GetService.class)
                .get(url, heads, body)
                .map(new Function<String, NullResponse>() {
                    @Override
                    public NullResponse apply(String s) {
                        NullResponse response = new NullResponse();
                        response.setCode(BaseResponse.SUCCESS);
                        return response;
                    }
                }) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ApiErrorHandler<NullResponse>());
                observable.onErrorResumeNext(new HttpErrorHandler<T>());
                 observable.subscribe(observer);
    }

    //********************************  get **********************************

    /**
     * baseUrl+url 或者 url
     * get 请求
     *
     * @param heads
     * @param body
     * @param observer
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void get( String url, Map<String, String> heads, Map<String, Object> body,
                        final Class<T> clazz, BaseObserver<T> observer) {
        Observable observable  = getService(GetService.class)
                .get(url, heads, body)
                .map(new Function<String, BaseResponse<T>>() {
                    @Override
                    public BaseResponse<T> apply(String s) throws Exception {
                        return convert(s, clazz);
                    }
                }).compose(applySchedulers(observer));
    }

    //********************************  get 2**********************************

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
    public <T extends BaseResponse> void get(String url, Map<String, String> heads, Map<String, Object> body,
                                             final Class<T> clazz, BaseObserver2<T> observer) {

        Observable observable = getService(GetService.class)
                .get(url, heads, body)
                .map(new Function<String, T>() {
                    @Override
                    public T apply(String s) {
                        return JSON.parseObject(s, clazz);
                    }
                }).compose(applySchedulers(observer));
    }

    //******************************* getList *********************************************

    /**
     * baseUrl+url 或者 url
     * get list 请求
     *
     * @param heads
     * @param body
     * @param observer
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public <T> void getList( String url, Map<String, String> heads, Map<String, Object> body,
                            final Class<T> clazz, BaseObserver<ArrayList<T>> observer) {
        Observable observable = getService(GetService.class)
                .get(url, heads, body)
                .map(new Function<String, BaseResponse<ArrayList<T>>>() {
                    @Override
                    public BaseResponse<ArrayList<T>> apply(String s) throws Exception {
                        return convertList(s, clazz);
                    }
                }).compose(applySchedulers(observer));
    }


    private <T> BaseResponse<T> convert(String s, Class<T> clazz) {
        BaseResponse<T> baseGetResponse = JSON.parseObject(s, new TypeReference<BaseResponse<T>>() {
        });

        String detail = JSON.toJSONString(baseGetResponse.getData());
        baseGetResponse.setData(JSON.parseObject(detail, clazz));

        return baseGetResponse;
    }

    private <T> BaseResponse<ArrayList<T>> convertList(String s, Class<T> clazz) {
        BaseResponse<ArrayList<T>> baseGetResponse = JSON.parseObject(s, new TypeReference<BaseResponse<ArrayList<T>>>() {
        });

        String detail = JSON.toJSONString(baseGetResponse.getData());
        ArrayList<T> arrayList = new ArrayList<>();
        List<T> list = JSON.parseArray(detail, clazz);
        if (list != null) {
            arrayList.addAll(list);
        }

        baseGetResponse.setData(arrayList);

        return baseGetResponse;
    }


}
