package com.bsoft.libnet.apiservice;


import androidx.collection.ArrayMap;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 *
 */


public interface PostService {
    @POST
    Observable<String> post(@Url String url, @HeaderMap ArrayMap<String, String> headers,
                            @Body Object request);
}
