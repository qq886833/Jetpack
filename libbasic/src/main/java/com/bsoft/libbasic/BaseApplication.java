
package com.bsoft.libbasic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.bsoft.libbasic.thirdpart.fastsharedpreferences.FastSharedPreferences;
import com.bsoft.libbasic.utils.ActivityManager;
import com.bsoft.libbasic.utils.ScreenAutoAdapter;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;


public class BaseApplication extends MultiDexApplication {


    private static BaseApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
        ScreenAutoAdapter.setup(this);
        FastSharedPreferences.init(this);
        QMUISwipeBackActivityManager.init(this);//必须使用   QMUI  AppTheme

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }
    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull BaseApplication application) {
        sInstance = application;
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManager.getInstance().attach(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityManager.getInstance().detach(activity);
            }
        });
    }
    /**
     * 获得当前app运行的Application
     */
    public static BaseApplication getInstance()
    {
        if (sInstance == null)
        {
            throw new NullPointerException(
                    "please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }
}

