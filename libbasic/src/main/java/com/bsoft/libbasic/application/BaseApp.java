package com.bsoft.libbasic.application;

import android.app.Application;
import com.bsoft.libbasic.BaseApplication;

public abstract class BaseApp extends BaseApplication {
    /**
     * Application 初始化
     */
    public abstract void initModuleApp(Application application);

    /**
     * 所有 Application 初始化后的自定义操作
     */
    public abstract void initModuleData(Application application);
}
