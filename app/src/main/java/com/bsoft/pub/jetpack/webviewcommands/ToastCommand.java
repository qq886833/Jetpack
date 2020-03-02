package com.bsoft.pub.jetpack.webviewcommands;

import android.os.Handler;
import android.os.Looper;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libwebview.ICallbackFromMainToWeb;
import com.bsoft.libwebview.command.Command;
import com.bsoft.libwebview.mainprocess.CommandsManager;

import java.util.Map;

public class ToastCommand implements Command {
    @Override
    public String name() {
        return "showToast";
    }

    public static final void init() {
        CommandsManager.getsInstance().registerCommand(new ToastCommand());
    }
    @Override
    public void exec(final Map params, ICallbackFromMainToWeb resultBack) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(String.valueOf(params.get("message")));
            }
        });

    }
}
