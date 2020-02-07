package com.bsoft.pub.jetpack;

import android.app.Application;
import com.bsoft.libnet.base.INetworkInit;


public class NetWorkInit implements INetworkInit {
    private Application mApplication;
    public NetWorkInit(Application application){
        this.mApplication = application;
    }
    @Override
    public String getAppVersionName() {
        return  BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }

    @Override
    public String getHttpApiUrl() {
        return "http://hc.dev.atag.bsoft.com.cn/hcn-web/";
    }

    @Override
    public String getHttpDownloadUrl() {
        return "https://timgsa.baidu.com/";
    }

    @Override
    public String getHttpImgUrl() {
        return "http://image.bshcn.com.cn/file/upload/image/";
    }

    @Override
    public String getEnvironment() {
        return "zhengshi";
    }
}
