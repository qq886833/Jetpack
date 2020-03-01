package com.bsoft.libbasic.thirdpart.loadsir.core;


import com.bsoft.libbasic.thirdpart.loadsir.callback.Callback;

/**
 * Description:
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Convertor<T> {
   Class<?extends Callback> map(T t);
}
