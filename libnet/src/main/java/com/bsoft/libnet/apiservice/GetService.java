package com.bsoft.libnet.apiservice;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import java.util.Map;

/**
 * Created by chenkai on 2017/5/10.
 */


public interface GetService {
    @GET
    Observable<String> get(@Url String url, @HeaderMap Map<String, String> headers,
                           @QueryMap Map<String, Object> params);
}
