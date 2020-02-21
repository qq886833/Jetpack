package com.bsoft.libpay.model;

import java.io.Serializable;

public class PayResult implements Serializable {
    private String code;
    private String msg;
    private String extra;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
