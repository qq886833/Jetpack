package com.bsoft.libcommon.utils.proxsp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.bsoft.libbasic.thirdpart.fastsharedpreferences.FastSharedPreferences;
import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 应用程序代理类
 * Created by rae on 2020-02-20.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class AppConfigHandler {

    private AppConfigHandler() {
    }

    /**
     * 创建程序配置代理类
     *
     * @param cls 类的Class
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(Context context, Class<T> cls) {
        Config config = cls.getAnnotation(Config.class);
        if (config == null) {
            throw new RuntimeException("请在配置类标注@Config()");
        }
        if (!cls.isInterface()) {
            throw new RuntimeException("配置类必须是接口");
        }
        String configName = config.value();
        if (TextUtils.isEmpty(configName)) {
            configName = cls.getName();
        }
     //   SharedPreferences preferences = context.getSharedPreferences(configName, Context.MODE_PRIVATE);
        FastSharedPreferences preferences = FastSharedPreferences.get(configName);
        // 创建动态代理
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new ConfigProxy(preferences));
    }

    private static class ConfigProxy implements InvocationHandler {

        private final FastSharedPreferences mPreference;
        private final Gson mGson = new Gson();

        private ConfigProxy(FastSharedPreferences preference) {
            this.mPreference = preference;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            String methodName = method.getName().toUpperCase();
            // 清除配置文件
            if (methodName.equalsIgnoreCase("clear")) {
                mPreference.edit().clear().apply();
            }
            // 移除配置项处理
            else if (methodName.equalsIgnoreCase("remove") && args != null) {
                String key = args[0].toString().toUpperCase();
                mPreference.edit().remove(key).apply();
            }
            // Get方法处理
            else if (methodName.startsWith("SET")) {
                setValue(methodName.replace("SET", ""), method, args);
            }
            // Set方法处理
            else if (methodName.startsWith("GET")) {
                return getValue(methodName.replace("GET", ""), method, args);
            }
            // Is方法处理，比如：isLogin()、isVip()，这类的布尔值
            else if (methodName.startsWith("IS")) {
                boolean value = mPreference.getBoolean(methodName.replace("IS", ""), false);
                return value;
            }
            return null;
        }

        /**
         * 设置配置值
         */
        private void setValue(String name, Method method, Object[] args) {
            if (args.length != 1) throw new IllegalArgumentException("set方法的方法参数只允许一个");
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> parameterType = parameterTypes[0];
            Object arg = args[0];
            SharedPreferences.Editor editor = mPreference.edit();
            if (parameterType == String.class) {
                editor.putString(name, (String) arg);
            } else if (parameterType == int.class) {
                editor.putInt(name, (int) arg);
            } else if (parameterType == boolean.class) {
                editor.putBoolean(name, (boolean) arg);
            } else if (parameterType == float.class) {
                editor.putFloat(name, (float) arg);
            } else if (parameterType == long.class) {
                editor.putLong(name, (long) arg);
            } else {
                // 其他值默认使用Json字符串
                String json = mGson.toJson(arg);
                editor.putString(name, json);
            }
            editor.apply();
        }

        /**
         * 获取配置值
         */
        private Object getValue(String name, Method method, Object[] args) {
            Class<?> type = method.getReturnType();
            Object defaultValue = args == null ? null : args[0];
            if (type == String.class) {
                return mPreference.getString(name, (String) defaultValue);
            } else if (type == int.class) {
                return mPreference.getInt(name, defaultValue == null ? 0 : (int) defaultValue);
            } else if (type == boolean.class) {
                return mPreference.getBoolean(name, defaultValue != null && (boolean) defaultValue);
            } else if (type == float.class) {
                return mPreference.getFloat(name, defaultValue == null ? 0 : (float) defaultValue);
            } else if (type == long.class) {
                return mPreference.getLong(name, defaultValue == null ? 0 : (long) defaultValue);
            } else {
                // 其他值默认使用Json字符串
                String json = mPreference.getString(name, null);
                return mGson.fromJson(json, type);
            }
        }
    }
}

