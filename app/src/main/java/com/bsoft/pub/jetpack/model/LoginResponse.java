package com.bsoft.pub.jetpack.model;

import androidx.collection.ArrayMap;
import com.bsoft.libnet.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse<LoginUserVo> {
    @SerializedName("properties")
    private ArrayMap<String, String> properties;

    public LoginResponse() {
    }

    public ArrayMap<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(ArrayMap<String, String> properties) {
        this.properties = properties;
    }
}
