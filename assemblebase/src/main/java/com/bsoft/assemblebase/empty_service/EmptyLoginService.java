package com.bsoft.assemblebase.empty_service;


import com.bsoft.assemblebase.service.ILoginService;

public class EmptyLoginService implements ILoginService {
    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getAccountId() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void setToken(String token) {

    }

}
