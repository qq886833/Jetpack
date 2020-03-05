package com.bsoft.libcommon.utils.proxsp;

/**
 * 程序配置
 * Created by rae on 2020/2/22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Config("YourAppConfig")
public interface LoginConfig {



    void setLoginState(boolean login);

    boolean getLoginState();

    void setVersion(int version);

    int getVersion();

    void clear();

    void remove(String key);
}

