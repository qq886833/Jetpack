package com.bsoft.libnet.base;

import android.app.Application;

/**
 *
 *
 */


public interface INetworkInit {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();
    Application getApplicationContext();

    String getHttpApiUrl();
    String getHttpDownloadUrl();
    String getHttpImgUrl();
    String getEnvironment();

}
