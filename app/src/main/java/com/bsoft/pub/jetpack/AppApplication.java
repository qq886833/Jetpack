package com.bsoft.pub.jetpack;

import cn.jpush.android.api.JPushInterface;
import com.bsoft.libbasic.BaseApplication;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.init.ArouterInit;
import com.bsoft.libbasic.init.BaseAppInit;
import com.bsoft.libbasic.init.BaseInitConfig;
import com.bsoft.libcommon.InitializeService;
import com.bsoft.libcommon.localdata.AccountSharpref;
import com.bsoft.libnavannotation.WXPayEntry;
import com.bsoft.libnet.utils.NetEnvironmentUtil;
import com.bsoft.libnet.utils.log.LogUtil;
import com.bsoft.libpay.weixin.BaseWXPayEntryActivity;
import com.bsoft.login.BuildConfig;

@WXPayEntry(packageName = "com.bsoft.mhealthp.jkcs", entryClass = BaseWXPayEntryActivity.class)
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppInit.getInstance().init(this, getConfig());
        ArouterInit.init(this);
        NetEnvironmentUtil.initConstans(this);
        LogUtil.init();
        JPushInterface.setDebugMode(HttpConstants.isDebug);
        JPushInterface.init(this);
        InitializeService.start(getApplicationContext());
    }

    private BaseInitConfig getConfig()  {
        BaseInitConfig config = new BaseInitConfig();
        config.setEnvironment("zhengshi");
        config.setVersionCode(com.bsoft.login.BuildConfig.VERSION_CODE);
        config.setVersionName(com.bsoft.login.BuildConfig.VERSION_NAME);
        config.setDebug(BuildConfig.DEBUG);
        config.setHttpApiUrl("http://hc.dev.atag.bsoft.com.cn/hcn-web/");
        config.setHttpDownloadUrl("https://timgsa.baidu.com/");
        config.setHttpImgUrl("http://image.bshcn.com.cn/file/upload/image/");
        return config;
    }

    private void logout() {
        AccountSharpref.getInstance().setLoginState(false);
        //    AccountSharpref.getInstance().setAccessToken(null)
        //    LocalDataUtil.getInstance().saveIndexVo(null)
        //互联网医院退出
        //    InquiryDocLogin.logout()
    }
}
