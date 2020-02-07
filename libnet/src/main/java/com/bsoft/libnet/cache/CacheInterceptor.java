package com.bsoft.libnet.cache;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.bsoft.libnet.constants.NetConfig;
import com.bsoft.libnet.utils.NetStateUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class CacheInterceptor implements Interceptor {

    private Context context;
    private int maxAge = NetConfig.CACHE_DEFAULT_MAX_AGE;//有网络时，强制缓存的生命周期
    private int maxStale = NetConfig.CACHE_DEFAULT_MAX_STALE;//无网络时，允许缓存的生命周期

    @SuppressWarnings("ConstantConditions")
    public CacheInterceptor(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @SuppressWarnings("ConstantConditions")
    public CacheInterceptor(@NonNull Context context, @IntRange(from = 0) int maxAge, @IntRange(from = 0) int maxStale) {
        if (maxAge < 0) {
            throw new IllegalArgumentException("maxAge < 0");
        }
        if (maxStale < 0) {
            throw new IllegalArgumentException("maxStale < 0");
        }
        this.context = context.getApplicationContext();
        this.maxAge = maxAge;
        this.maxStale = maxStale;
    }

    /**
     * 设置有网络的缓存生命周期
     *
     * @param maxAge
     * @return
     */
    public CacheInterceptor setMaxAge(@IntRange(from = 0) int maxAge) {
        if (maxAge < 0) {
            throw new IllegalArgumentException("maxAge < 0");
        }
        this.maxAge = maxAge;
        return this;
    }

    /**
     * 设置无网络的缓存生命周期
     *
     * @param maxStale
     * @return
     */
    public CacheInterceptor setMaxStale(@IntRange(from = 0) int maxStale) {
        if (maxStale < 0) {
            throw new IllegalArgumentException("maxStale < 0");
        }
        this.maxStale = maxStale;
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetStateUtil.getCurrentNetState(context) == NetStateUtil.NetState.NET_NO) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetStateUtil.getCurrentNetState(context) == NetStateUtil.NetState.NET_NO) {
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        } else {
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
        return response;
    }
}
