package com.bsoft.libcommon.localdata;

import com.bsoft.libbasic.shapref.BaseSharpref;
import org.jetbrains.annotations.NotNull;

public class AppSharpref extends BaseSharpref {

    private static final String SHARED_NAME = "share_app";

    private static final String SHOW_GUIDE = "show_guide";
    private static final String ROOT_TRUST_STATE = "root_trust_state";

    public AppSharpref(@NotNull String name) {
        super(name);
    }

    private volatile static AppSharpref instance;

    public static AppSharpref getInstance() {
        if (instance == null) {
            synchronized (AppSharpref.class) {
                if (instance == null) {
                    instance = new AppSharpref(SHARED_NAME);
                }
            }
        }
        return instance;
    }

    public boolean setShowGuide(boolean flag) {
        synchronized (this) {
            return setBoolean(flag, SHOW_GUIDE);
        }
    }

    public boolean isShowGuide() {
        synchronized (this) {
            return getBoolean(SHOW_GUIDE);
        }
    }
    public boolean setRootTrust(boolean flag) {
        synchronized (this) {
            return setBoolean(flag, ROOT_TRUST_STATE);
        }
    }

    public boolean isRootTrust() {
        synchronized (this) {
            return getBoolean(ROOT_TRUST_STATE);
        }
    }
}
