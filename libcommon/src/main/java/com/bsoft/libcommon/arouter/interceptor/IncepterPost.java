package com.bsoft.libcommon.arouter.interceptor;

import com.alibaba.android.arouter.facade.Postcard;

import java.io.Serializable;


public class IncepterPost  implements Serializable {
    public static final int USER_INFO_ERROR = 1;
    public static final int NEED_LOGIN = 2;

    private Postcard postcard;
    private int Mode;

    public Postcard getPostcard() {
        return postcard;
    }

    public void setPostcard(Postcard postcard) {
        this.postcard = postcard;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }
}
