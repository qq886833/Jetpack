package com.bsoft.libcommon.utils.proxsp;

/**
 * 程序配置
 * Created by rae on 2020/2/22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Config("YourAppConfig")
public interface IAppConfig {

    void setUserName(String name);

    String getUserName(String defaultValue);

    void setVip(boolean isVip);

    boolean isVip();

    void setVersion(int version);

    int getVersion();

    void clear();

    void remove(String key);
}

