package com.bsoft.libbasic.utils.log;


import androidx.annotation.NonNull;
import com.bsoft.libbasic.thirdpart.logger.Logger;

/**
 * Created by kai.chen on 2016/10/9.
 */

public class LogUtil {
    public static void init() {
        Logger.addLogAdapter(new LogAdapter());
    }

    //**********  I   *************
    public static void i(@NonNull String msg) {
        Logger.t(CoreLogTag.TAG).i(msg);
    }

    public static void i(@NonNull String tag, @NonNull String msg) {
        Logger.t(CoreLogTag.TAG + "-" + tag).i(msg);
    }

    //**********  D   ************
    public static void d(@NonNull String msg) {
        Logger.t(CoreLogTag.TAG).d(msg);
    }

    public static void d(@NonNull String tag, @NonNull String msg) {
        Logger.t(CoreLogTag.TAG + "-" + tag).d(msg);
    }

    //**********  E   ************
    public static void e(@NonNull String msg) {
        Logger.t(CoreLogTag.TAG).e(msg);
    }

    public static void e(@NonNull String tag, @NonNull String msg) {
        Logger.t(CoreLogTag.TAG + "-" + tag).e(msg);
    }

    //**********  E  Throwable ************
    public static void e(@NonNull String msg, Throwable throwable) {
        Logger.t(CoreLogTag.TAG).e(throwable, msg);
    }

    public static void e(@NonNull String tag, @NonNull String msg, Throwable throwable) {
        Logger.t(CoreLogTag.TAG + "-" + tag).e(throwable, msg);
    }

    //***************** Json ********************
    public static void json(@NonNull String msg) {
        Logger.t(CoreLogTag.TAG).json(msg);
    }

    public static void json(@NonNull String tag, @NonNull String msg) {
        Logger.t(tag).json(msg);
    }

    //****************** Xml *********************
    public static void xml(@NonNull String msg) {
        Logger.t(CoreLogTag.TAG).xml(msg);
    }

    public static void xml(@NonNull String tag, @NonNull String msg) {
        Logger.t(CoreLogTag.TAG + "-" + tag).xml(msg);
    }
}
