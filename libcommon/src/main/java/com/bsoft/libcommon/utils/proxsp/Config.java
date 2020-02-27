package com.bsoft.libcommon.utils.proxsp;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 应用程序配置注解
 * Created by rae on 2020-02-20.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Retention(RUNTIME)
public @interface Config {

    /**
     * 程序配置名称
     */
    String value();

}


