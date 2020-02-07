package com.bsoft.pub.jetpack.api;

import androidx.collection.ArrayMap;
import com.bsoft.pub.jetpack.model.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface NewsApiInterface {
    @POST
    Observable<LoginResponse> getNewsChannels(@Url String url,@HeaderMap ArrayMap<String, String> headers,
                                              @Body Object request);
}
