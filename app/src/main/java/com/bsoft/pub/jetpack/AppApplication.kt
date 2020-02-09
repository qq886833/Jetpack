package com.bsoft.pub.jetpack

import cn.jpush.android.api.JPushInterface
import com.bsoft.libbasic.BaseApplication
import com.bsoft.libbasic.constant.HttpConstants
import com.bsoft.libbasic.init.BaseAppInit
import com.bsoft.libbasic.init.BaseInitConfig
import com.bsoft.libcommon.localdata.AccountSharpref
import com.bsoft.libnet.utils.NetEnvironmentUtil
import com.bsoft.libnet.utils.log.LogUtil
import com.jeremyliao.liveeventbus.LiveEventBus


class AppApplication : BaseApplication() {
    //token失效在后台时，标记需要显示dialog，当程序进入前台显示dialog
    private var tokenErrorDialogNeedShow = false
    private var toekenErrorMsg: String? = null
        init{
            instance = this
        }
    companion object{
        lateinit var instance : AppApplication
    }

    override fun onCreate() {
        super.onCreate()



        LiveEventBus.config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
//        LogUtils.getConfig().run {
//            isLogSwitch = BuildConfig.DEBUG
//            setSingleTagSwitch(true)
//        }

        //  LiveEventBus.config().supportBroadcast(getApplicationContext()).lifecycleObserverAlwaysActive(true);
//  CoreAppInit.getInstance().init(this)
//        LoadSir.beginBuilder()
//                .addCallback(new ErrorCallback.Builder().build())
//                .addCallback(new EmptyCallback.Builder().build())
//                .addCallback(new LoadingCallback.Builder().build())
//                .addCallback(new TimeoutCallback.Builder().build())
//                .addCallback(new CustomCallback())
//                .addCallback(new PlaceholderCallback())
//                .setDefaultCallback(LoadingCallback.class)
//                .commit();
        BaseAppInit.getInstance().init(this, getConfig())

        NetEnvironmentUtil.initConstans(this)
        LogUtil.init()
        JPushInterface.setDebugMode(HttpConstants.isDebug)
        JPushInterface.init(this)


    }


    private fun getConfig(): BaseInitConfig? {
        val config = BaseInitConfig()
        config.setEnvironment(BuildConfig.environment)
        config.setVersionCode(BuildConfig.VERSION_CODE)
        config.setVersionName(BuildConfig.VERSION_NAME)
        config.setDebug(BuildConfig.DEBUG)
        config.setHttpApiUrl("http://hc.dev.atag.bsoft.com.cn/hcn-web/")
        config.setHttpDownloadUrl("https://timgsa.baidu.com/")
        config.setHttpImgUrl("http://image.bshcn.com.cn/file/upload/image/")
        return config
    }


    private fun logout() {
        AccountSharpref.getInstance().loginState = false
    //    AccountSharpref.getInstance().setAccessToken(null)
    //    LocalDataUtil.getInstance().saveIndexVo(null)
        //互联网医院退出
    //    InquiryDocLogin.logout()
    }
}