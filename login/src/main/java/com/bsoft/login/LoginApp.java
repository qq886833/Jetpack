package com.bsoft.login;

import android.app.Application;
import com.bsoft.assemblebase.ServiceFactory;
import com.bsoft.libbasic.application.BaseApp;
import com.bsoft.login.service.LoginService;


public class LoginApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application application) {
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }

    @Override
    public void initModuleData(Application application) {

    }
}
