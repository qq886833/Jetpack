package com.bsoft.libbasic.init;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.libbasic.constant.HttpConstants;

public class ArouterInit {
    public static void init(Application application) {
        if (HttpConstants.isDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(application);
    }
}
