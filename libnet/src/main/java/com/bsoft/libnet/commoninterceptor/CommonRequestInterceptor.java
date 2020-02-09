package com.bsoft.libnet.commoninterceptor;


import com.bsoft.libbasic.constant.HttpConstants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class CommonRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", String.valueOf(HttpConstants.versionCode));

        return chain.proceed(builder.build());
    }
}
