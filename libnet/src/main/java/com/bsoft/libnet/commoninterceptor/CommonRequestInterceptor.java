package com.bsoft.libnet.commoninterceptor;


import com.bsoft.libnet.base.INetworkInit;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class CommonRequestInterceptor implements Interceptor {
    private INetworkInit requiredInfo;
    public CommonRequestInterceptor(INetworkInit requiredInfo){
        this.requiredInfo = requiredInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion",this.requiredInfo.getAppVersionCode());

        return chain.proceed(builder.build());
    }
}
