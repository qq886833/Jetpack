package com.bsoft.libcommon.commonaop;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcDarren on 2017/8/27.
 * 标记切点 注解
 */
@Keep
@Target(ElementType.METHOD) // Target 放在哪个位置
@Retention(RetentionPolicy.RUNTIME)// RUNTIME 运行时 xUtils  CLASS 代表编译时期 ButterKnife   SOURCE 代表资源
public @interface CheckNet { // @interface 注解

}
