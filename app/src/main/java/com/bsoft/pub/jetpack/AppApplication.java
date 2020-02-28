package com.bsoft.pub.jetpack;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
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
import com.bsoft.libbasic.utils.log.LogUtil;
import com.bsoft.libpay.weixin.BaseWXPayEntryActivity;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import java.util.Locale;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

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


        initBuglyAndThinker();







    }

    private void initBuglyAndThinker() {


        setStrictMode();
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;

        /**
         *  全量升级状态回调
         */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {

            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
               // Toast.makeText(getApplicationContext(), "最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean b) {
              //  Toast.makeText(getApplicationContext(), "onUpgrading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                Toast.makeText(getApplicationContext(), patchFileUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(),
                        "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Toast.makeText(getApplicationContext(), patchFilePath, Toast.LENGTH_SHORT).show();
//                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {
                Toast.makeText(getApplicationContext(), "onPatchRollback", Toast.LENGTH_SHORT).show();
            }
        };

        long start = System.currentTimeMillis();
        Bugly.setUserId(this, "falue");
        Bugly.setUserTag(this, 123456);
        Bugly.putUserData(this, "key1", "123");
        Bugly.setAppChannel(this, "bugly");


        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
      //  Bugly.init(this, "900029763", true);

        String channel = WalleChannelReader.getChannel(getApplication());
        Bugly.setAppChannel(getApplication(), channel);
// 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(getApplication(), "YOUR_APP_ID", true);

        long end = System.currentTimeMillis();
        Log.e("init time--->", end - start + "ms");
    }

    private BaseInitConfig getConfig()  {
        BaseInitConfig config = new BaseInitConfig();
        config.setEnvironment("zhengshi");
        config.setVersionCode(BuildConfig.VERSION_CODE);
        config.setVersionName(BuildConfig.VERSION_NAME);
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


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker
        Beta.installTinker();
    }
    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}
