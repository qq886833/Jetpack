
package com.bsoft.libbasic;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.bsoft.libbasic.thirdpart.fastsharedpreferences.FastSharedPreferences;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;


public class BaseApplication extends MultiDexApplication {



    @Override
    public void onCreate() {
        super.onCreate();

        FastSharedPreferences.init(this);
        QMUISwipeBackActivityManager.init(this);//必须使用   QMUI  AppTheme

      //  LiveEventBus.config().supportBroadcast(getApplicationContext()).lifecycleObserverAlwaysActive(true);
      //  CoreAppInit.getInstance().init(this)

//        LoadSir.beginBuilder()
//                .addCallback(new ErrorCallback.Builder().build())
//                .addCallback(new EmptyCallback.Builder().build())
//                .addCallback(new LoadingCallback.Builder().build())
//                .addCallback(new TimeoutCallback.Builder().build())
//                .addCallback(new CustomCallback())
//                .addCallback(new PlaceholderCallback())
//                .setDefaultCallback(LoadingCallback.class)
//                .commit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }
}
