package com.bsoft.libcommon.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.bsoft.libbasic.context.ContextProvider;

import java.util.ArrayList;

public class AppForegroundUtil {

    private Application application;

    private ArrayList<ForegroundCallback> callbacks = new ArrayList<>();
    /*Flag*/
    private int activityAount = 0;
    private boolean isForeground = false;
    /*View*/

    public interface ForegroundCallback {
        void onForegroundChange(boolean isForeground);
    }

    private static class Holder {
        private static final AppForegroundUtil INSTANCE = new AppForegroundUtil(ContextProvider.get().getApplication());
    }

    public static AppForegroundUtil getInstance() {
        return Holder.INSTANCE;
    }

    public AppForegroundUtil(@NonNull Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void addCallback(ForegroundCallback callback) {
        if (callback != null) {
            callbacks.add(callback);
        }
    }

    public void removeCallback(ForegroundCallback callback) {
        if (callback != null) {
            callbacks.remove(callback);
        }
    }

    public boolean isForeground() {
        return isForeground;
    }

    private void onForgroundChange(boolean foreground) {
        isForeground = foreground;
        for (ForegroundCallback callback : callbacks) {
            callback.onForegroundChange(isForeground);
        }
    }

    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activityAount == 0) {
                //app回到前台
                onForgroundChange(true);
            }
            activityAount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityAount--;
            if (activityAount == 0) {
                onForgroundChange(false);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };
}
