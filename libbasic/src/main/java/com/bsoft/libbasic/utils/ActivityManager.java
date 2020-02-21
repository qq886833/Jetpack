package com.bsoft.libbasic.utils;

import android.app.Activity;

import java.util.Stack;

/**
 *
 */

public class ActivityManager {
    private static volatile ActivityManager mInstance;
    private Stack<Activity> mActivities;

    private ActivityManager(){
        mActivities = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加统一管理
     * @param activity
     */
    public void attach(Activity activity){
        mActivities.add(activity);
    }

    /**
     * 移除解绑 - 防止内存泄漏
     * @param detachActivity
     */
    public void detach(Activity detachActivity){
        int size = mActivities.size();
        for (int i = 0; i < size; i++) {
            Activity activity = mActivities.get(i);
            if (activity == detachActivity) {
                mActivities.remove(i);
                i--;
                size--;
            }
        }
    }

    /**
     * 关闭当前的 Activity
     * @param finishActivity
     */
    public void finish(Activity finishActivity){
        int size = mActivities.size();
        for (int i = 0; i < size; i++) {
            Activity activity = mActivities.get(i);
            if (activity == finishActivity) {
                mActivities.remove(i);
                activity.finish();
                i--;
                size--;
            }
        }
    }

    /**
     * 根据Activity的类名关闭 Activity
     */
    public void finish(Class<? extends Activity> activityClass){
        int size = mActivities.size();
        for (int i = 0; i < size; i++) {
            Activity activity = mActivities.get(i);
            if (activity.getClass().getCanonicalName().equals(activityClass.getCanonicalName())) {
                mActivities.remove(i);
                activity.finish();
                i--;
                size--;
            }
        }
    }

    /**
     * 结束某个Activity之外的所有Activity
     */
    public void finishAllActivityExcept(Class<?> clazz) {
        for (int i = mActivities.size() - 1; i >= 0; i--) {

            if (mActivities.get(i) != null && !mActivities.get(i).getClass().equals(clazz)) {
                finish(mActivities.get(i));
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        for (int i = mActivities.size() - 1; i >= 0; i--) {

            if (mActivities.get(i) != null) {
                finish(mActivities.get(i));
            }
        }
        mActivities.clear();
    }


    /**
     * 退出整个应用
     */
    public void exitApplication(){
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 获取当前的Activity（最前面）
     * @return
     */
    public Activity currentActivity(){
        return mActivities.lastElement();
    }



}
