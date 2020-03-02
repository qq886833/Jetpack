package com.bsoft.pub.jetpack.webviewcommands;

import android.text.TextUtils;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginListener;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libwebview.ICallbackFromMainToWeb;
import com.bsoft.libwebview.command.Command;
import com.bsoft.libwebview.mainprocess.CommandsManager;
import com.bsoft.libwebview.utils.AidlError;
import com.bsoft.libwebview.utils.WebConstants;

import java.util.HashMap;
import java.util.Map;

public final class LoginCommands {

    private LoginCommands() {
    }

    public static final void init() {
        CommandsManager.getsInstance().registerCommand(appLoginCommand);
    }

    /**
     * 页面路由
     */
    private static final Command appLoginCommand = new Command() {
        Map params;
        ICallbackFromMainToWeb resultBack;
        private ILoginListener loginListener = new ILoginListener() {
            @Override
            public void onLogin() {
                try {
                    String callbackName = "";
                    if (params.get("type") == null) {
                        AidlError aidlError = new AidlError(WebConstants.ERRORCODE.ERROR_PARAM, WebConstants.ERRORMESSAGE.ERROR_PARAM);
                        resultBack.onResult(WebConstants.FAILED, name(), aidlError.toString());
                        return;
                    }
                    if (params.get(WebConstants.WEB2NATIVE_CALLBACk) != null) {
                        callbackName = params.get(WebConstants.WEB2NATIVE_CALLBACk).toString();
                    }
                    String type = params.get("type").toString();
                    HashMap map = new HashMap();
                    switch (type) {
                        case "account":
                          //  map.put("accountName", PreferencesUtil.getInstance().getString("UserName"));
                            break;
                    }
                    if (!TextUtils.isEmpty(callbackName)) {
                        map.put(WebConstants.NATIVE2WEB_CALLBACK, callbackName);
                    }
                    resultBack.onResult(WebConstants.SUCCESS, name(),  GsonUtils.toJson(map));
                } catch (Exception e) {
                    e.printStackTrace();
                    AidlError aidlError = new AidlError(WebConstants.ERRORCODE.ERROR_PARAM, WebConstants.ERRORMESSAGE.ERROR_PARAM);
                    //resultBack.onResult(WebConstants.FAILED, name(), aidlError.toString());
                }
            }

            @Override
            public void onLogout() {

            }

            @Override
            public void onRegister() {

            }


        };

        @Override
        public String name() {
            return "appLogin";
        }

        @Override
        public void exec(Map params, ICallbackFromMainToWeb resultBack) {
            ILoginService iLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);
            if (!iLoginService.isLogin()) {
                iLoginService.registerLoginListener(loginListener);
                iLoginService.login();
            }
            this.params = params;
            this.resultBack = resultBack;
        }
    };
}
