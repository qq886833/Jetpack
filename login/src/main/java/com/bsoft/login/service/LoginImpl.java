package com.bsoft.login.service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.baseservices.ILoginListener;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.model.UserInfoVo;

import java.util.ArrayList;
import java.util.List;

@Route(path = AppRouterService.APP_LOGIN_SERVICE)
public class LoginImpl implements ILoginService {

    private List<ILoginListener> mLoginListeners = new ArrayList<>();

    @Override
    public boolean checkLogin(boolean needLogin) {
        return false;
    }

    @Override
    public boolean isLogin() {
        return false;
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
        CommonArouterGroup.getArouter(CommonArouterGroup.USER_INFO_ACTIVITY).navigation();
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
