package com.bsoft.libcommon.localdata;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.bsoft.libbasic.shapref.BaseSharpref;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.model.LoginUserVo;
import org.jetbrains.annotations.NotNull;

public class AccountSharpref extends BaseSharpref {
    private static final String  SHARED_ACCOUNT = "share_account";
    private static final String LOGIN_STATE = "login_state";
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

    private MutableLiveData<LoginUserVo> userLiveData = new MutableLiveData<>();
   public LiveData<LoginUserVo> login(Context context) {
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
       CommonArouterGroup.gotoActivity(CommonArouterGroup.TEST_ACTIVITY);
        return userLiveData;
    }
}
