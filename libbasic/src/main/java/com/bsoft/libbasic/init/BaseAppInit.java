package com.bsoft.libbasic.init;

import android.app.Application;
import com.bsoft.libbasic.constant.HttpConstants;

/**
 *
 */

public class BaseAppInit {

    private static class Holder {
        private static final BaseAppInit INSTANCE = new BaseAppInit();
    }

    public static BaseAppInit getInstance() {
        return BaseAppInit.Holder.INSTANCE;
    }

    public void init(Application app, BaseInitConfig config) {

        initConstants(config);

    }

    private void initConstants(BaseInitConfig config) {

        HttpConstants.isDebug = config.isDebug();
        HttpConstants.environment = config.getEnvironment();
        HttpConstants.httpApiUrl = config.getHttpApiUrl();
        HttpConstants.httpDownloadUrl = config.getHttpDownloadUrl();
        HttpConstants.httpImgUrl = config.getHttpImgUrl();
        HttpConstants.versionCode = config.getVersionCode();
        HttpConstants.versionName = config.getVersionName();

    }


}
