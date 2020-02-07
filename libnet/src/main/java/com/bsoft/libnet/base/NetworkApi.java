package com.bsoft.libnet.base;

import android.annotation.SuppressLint;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libnet.R;
import com.bsoft.libnet.cache.CacheBuilder;
import com.bsoft.libnet.cache.CacheInterceptor;
import com.bsoft.libnet.commoninterceptor.CommonRequestInterceptor;
import com.bsoft.libnet.commoninterceptor.CommonResponseInterceptor;
import com.bsoft.libnet.commoninterceptor.NetLogInterceptor;
import com.bsoft.libnet.constants.NetConfig;
import com.bsoft.libnet.environment.IEnvironment;
import com.bsoft.libnet.errorhandler.ApiErrorHandler;
import com.bsoft.libnet.errorhandler.HttpErrorHandler;
import com.bsoft.libnet.utils.SSLTools;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


public abstract class NetworkApi implements IEnvironment {
    private static INetworkInit mINetworkInit;
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;



    public NetworkApi() {
        mBaseUrl = getCurrentUrl();
    }

    public static void init(INetworkInit iNetworkInit) {
        mINetworkInit = iNetworkInit;

    }

    protected Retrofit getRetrofit() {
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//        if (getInterceptor() != null) {
//            okHttpClientBuilder.addInterceptor(getInterceptor());
//        }
//
//        okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(mINetworkInit));
//        okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
//        if (mINetworkInit != null &&(mINetworkInit.isDebug())) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
//            okHttpClientBuilder.addInterceptor(new NetLogInterceptor());
//
//        }
//        if (NetConfig.CACHE_ENABLE) {
//            Cache cache = new CacheBuilder().build(ContextProvider.get().getApplication());
//            okHttpClientBuilder.cache(cache)
//                    .addInterceptor(new CacheInterceptor(ContextProvider.get().getApplication()));
//        }



        Retrofit  retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(mBaseUrl)
                .build();
        return retrofit;
    }

    private  OkHttpClient getOkHttpClient() {
    //    if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.connectionPool(new ConnectionPool(8,
                    NetConfig.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS))
                    .retryOnConnectionFailure(false)
                    .connectTimeout(NetConfig.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(NetConfig.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(NetConfig.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
            if (NetConfig.CACHE_ENABLE) {
                Cache cache = new CacheBuilder().build(ContextProvider.get().getApplication());
                okHttpClientBuilder.cache(cache)
                        .addInterceptor(new CacheInterceptor(ContextProvider.get().getApplication()));
            }
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(mINetworkInit));
            okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
            okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(
                    (mINetworkInit != null && (mINetworkInit.isDebug())) ? HttpLoggingInterceptor.Level.BODY :
                            HttpLoggingInterceptor.Level.NONE
            ));
            if (mINetworkInit != null && (mINetworkInit.isDebug())) {
                okHttpClientBuilder.addInterceptor(new NetLogInterceptor());

            }

            //ssl
            InputStream[] certificates = null;
            if (ContextProvider.get().getApplication().getString(R.string.net_url_mp).equals(mBaseUrl)) {
                certificates = new InputStream[]{ContextProvider.get().getApplication().getResources().openRawResource(R.raw.network_mp)};
            }
            SSLTools.SSLParams sslParams = SSLTools.getSslSocketFactory(certificates, null, null);
            if (sslParams != null) {
                okHttpClientBuilder.sslSocketFactory(sslParams.sslSocketFactory, sslParams.x509TrustManager);
                okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                    @SuppressLint("BadHostnameVerifier")
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }
            mOkHttpClient = okHttpClientBuilder.build();
   //     }
        return mOkHttpClient;
    }


    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = ((Observable<T>) upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiErrorHandler<T>()))
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    protected abstract Interceptor getInterceptor();


}
