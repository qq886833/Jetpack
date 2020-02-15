package com.bsoft.libcommon.livedatabus.event;

import android.os.Bundle;

public class LoginEvent {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public boolean isSSO() {
        return isSSO;
    }

    public void setSSO(boolean SSO) {
        isSSO = SSO;
    }

    private String path;
    private Bundle bundle;
    private boolean isSSO;
}
