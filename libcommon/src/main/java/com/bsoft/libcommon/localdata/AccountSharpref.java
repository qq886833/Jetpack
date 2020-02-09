package com.bsoft.libcommon.localdata;

import com.bsoft.baselib.shapref.BaseSharpref;
import org.jetbrains.annotations.NotNull;

public class AccountSharpref extends BaseSharpref {
    public static final String  SHARED_ACCOUNT = "share_account";
    public static final String LOGIN_STATE = "login_state";
    public AccountSharpref(@NotNull String name) {
        super(name);
    }

    private volatile static AccountSharpref instance;

    public static AccountSharpref getInstance() {
        if (instance == null) {
            synchronized (AccountSharpref.class) {
                if (instance == null) {
                    instance = new AccountSharpref(SHARED_ACCOUNT);
                }
            }
        }
        return instance;
    }




    public boolean setLoginState(boolean flag) {
        synchronized (this) {
            return setBoolean(flag, LOGIN_STATE);
        }
    }

    public boolean getLoginState() {
        synchronized (this) {
            return getBoolean(LOGIN_STATE);
        }
    }
}
