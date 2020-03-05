package com.bsoft.login.service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.baseservices.ILoginListener;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.model.LoginUserVo;
import com.bsoft.libcommon.model.UserInfoVo;
import com.bsoft.libcommon.utils.proxsp.AppConfigHandler;
import com.bsoft.libcommon.utils.proxsp.LoginConfig;

import java.util.ArrayList;
import java.util.List;

@Route(path = AppRouterService.APP_LOGIN_SERVICE, name = "登录服务")
public class LoginImpl implements ILoginService {

    private List<ILoginListener> mLoginListeners = new ArrayList<>();

    @Override
    public boolean checkLogin(boolean needLogin) {
        LoginConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(),LoginConfig.class);
        config.setLoginState(needLogin);
        return needLogin;
    }

    @Override
    public boolean isLogin() {
        LoginConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(),LoginConfig.class);
        return config.getLoginState();
    }

    @Override
    public void checkUserInfo() {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUUID() {
        return null;
    }

    @Override
    public void refreshUserDetailInfo() {
        for(ILoginListener loginListener:mLoginListeners){
            loginListener.onLogin();
        }
    }

    @Override
    public void login() {
        CommonArouterGroup.getArouter(CommonArouterGroup.PATH_LOGIN_ACTIVITY).navigation();
    }

    @Override
    public void login(boolean isMainAccountLogin) {

    }

    @Override
    public void logout() {

    }

    @Override
    public UserInfoVo getUserInfoVo() {
        return null;
    }

    @Override
    public LoginUserVo getLoginUserVo() {
        return LoginUserVo.getInstance();
    }


    @Override
    public void onLoginCancel() {

    }

    @Override
    public void unregisterListener(ILoginListener listener) {
        for(int i = 0; i < mLoginListeners.size(); i ++){
            if(mLoginListeners.get(i) == listener){
                mLoginListeners.remove(i);
            }
        }
    }

    @Override
    public void registerLoginListener(ILoginListener listener) {
        mLoginListeners.add(listener);
    }

    @Override
    public void thirdUnbind(ThirdAccountType accountType, Context context) {

    }

    @Override
    public void onTokenExpire() {

    }

    @Override
    public void onOpenAccountSuccess() {

    }

    @Override
    public OpenAccountType checkOpenAccount() {
        return null;
    }

    @Override
    public void startOpenAccount() {

    }

    @Override
    public void init(Context context) {

    }
}
