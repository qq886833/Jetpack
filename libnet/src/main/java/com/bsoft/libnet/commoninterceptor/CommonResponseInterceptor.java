package com.bsoft.libnet.commoninterceptor;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;


public class CommonResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.e(TAG, "requestTime="+ (System.currentTimeMillis() - requestTime)+"------------------------------------");
        return response;
    }
}
