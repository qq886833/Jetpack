package com.bsoft.guide;

import com.bsoft.libbasic.BaseApplication;
import com.bsoft.libbasic.init.ArouterInit;
import com.bsoft.libbasic.init.BaseAppInit;
import com.bsoft.libbasic.init.BaseInitConfig;
import com.bsoft.libcommon.InitializeService;
import com.bsoft.libnet.utils.NetEnvironmentUtil;
import com.bsoft.libnet.utils.log.LogUtil;


public class GuideApp extends BaseApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppInit.getInstance().init(this, getConfig());
        ArouterInit.init(this);
        NetEnvironmentUtil.initConstans(this);
        LogUtil.init();
        InitializeService.start(getApplicationContext());
    }


    private BaseInitConfig getConfig()  {
        BaseInitConfig config = new BaseInitConfig();
        config.setEnvironment("zhenshi");
        config.setVersionCode(BuildConfig.VERSION_CODE);
        config.setVersionName(BuildConfig.VERSION_NAME);
        config.setDebug(BuildConfig.DEBUG);
        config.setHttpApiUrl("http://hc.dev.atag.bsoft.com.cn/hcn-web/");
        config.setHttpDownloadUrl("https://timgsa.baidu.com/");
        config.setHttpImgUrl("http://image.bshcn.com.cn/file/upload/image/");
        return config;
    }
}
