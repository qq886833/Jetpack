package com.bsoft.pub.jetpack.webviewcommands;

import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libwebview.ICallbackFromMainToWeb;
import com.bsoft.libwebview.command.Command;
import com.bsoft.libwebview.mainprocess.CommandsManager;

import java.util.Map;

public final class ArouterCommands {

    private ArouterCommands() {
    }

    public static final void init() {
        CommandsManager.getsInstance().registerCommand(pageRouterCommand);
    }

    /**
     * 页面路由
     */
    private final static Command pageRouterCommand = new Command() {

        @Override
        public String name() {
            return "start_activity";
        }

        @Override
        public void exec(Map params, ICallbackFromMainToWeb resultBack) {
            //ARouter.getInstance().build(params.get("arouter_navigation").toString()).navigation();
            CommonArouterGroup.goMainTabActivity();
        }
    };
}
