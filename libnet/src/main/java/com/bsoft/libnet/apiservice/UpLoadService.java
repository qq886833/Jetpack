package com.bsoft.libnet.apiservice;

import androidx.collection.ArrayMap;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;


public interface UpLoadService {
    /**
     * Upload
     *
     * @return
     */
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @HeaderMap ArrayMap<String, String> headers,
                              @PartMap ArrayMap<String, RequestBody> params, @PartMap ArrayMap<String, RequestBody> files);
}
