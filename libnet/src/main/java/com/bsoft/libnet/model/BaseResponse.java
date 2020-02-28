package com.bsoft.libnet.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.bsoft.libcommon.model.base.BaseVo;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 *
 *
 *
 */

public class BaseResponse <E>  extends BaseVo {

    public static final int SUCCESS = 200;
    @SerializedName(value ="code")
    @JSONField(name = "code")
    private int code;

    @SerializedName(value = "msg", alternate = {"message"})
    @JSONField(name = "msg")
    private String message;

    @SerializedName(value = "body", alternate = {"data"})
    @JSONField(name = "body")
    private E data;


    public boolean isSuccess() {
        return SUCCESS==code;
    }

    public boolean isEmpty() {
        if (data instanceof Collection) {
            return data == null || ((Collection) data).isEmpty();
        }
        return data == null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
