package com.bsoft.libbasic.context

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*
* 使用
ContextProvider.get().getApplication()
ContextProvider.get().getContext()
* */
class ContextProvider private constructor(val context: Context) {
    /**
     * 获取上下文
     */

    val application: Application
        get() = context.applicationContext as Application

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ContextProvider? = null

        /**
         * 获取实例
         */
        @JvmStatic
        fun get(): ContextProvider? {
            if (instance == null) {
                synchronized(ContextProvider::class.java) {
                    if (instance == null) {
                        val context =
                            ApplicationContextProvider.mContext ?: throw IllegalStateException("context == null")
                        instance = ContextProvider(context)
                    }
                }
            }
            return instance
        }
    }

}