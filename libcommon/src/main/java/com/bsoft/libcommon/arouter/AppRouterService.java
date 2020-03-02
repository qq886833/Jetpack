package com.bsoft.libcommon.arouter;
public final class AppRouterService {
    private static final String APP = "/app/";

    private AppRouterService(){}

    /**
     * Common webview activity
     * url is necessary parameter.
     */
    public static final String APP_LOGIN_ACTIVITY = APP + "login_activity";

    public static final String APP_TESTA_ACTIVITY = APP + "testa_activity";
    public static final String APP_TESTB_ACTIVITY = APP + "testb_activity";
    public static final String APP_LOGIN_SERVICE = APP + "login_service";

}
