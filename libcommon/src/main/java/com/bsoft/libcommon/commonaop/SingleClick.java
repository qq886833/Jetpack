package com.bsoft.libcommon.commonaop;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复点击
 * Created by jerry on 2018/6/13.
 */
@Keep
@Retention(RetentionPolicy.RUNTIME) //注解保留至运行时
@Target(ElementType.METHOD) //声明注解作用在方法上面
public @interface SingleClick {
    /* 点击间隔时间 */
    long value() default 1000;
}
