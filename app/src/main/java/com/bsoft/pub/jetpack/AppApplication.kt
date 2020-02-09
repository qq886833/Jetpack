package com.bsoft.pub.jetpack

import android.os.Bundle
import com.bsoft.libbasic.BaseApplication
import com.bsoft.libbasic.init.BaseAppInit
import com.bsoft.libbasic.init.BaseInitConfig
import com.bsoft.libbasic.init.InitListener
import com.bsoft.libbasic.utils.ToastUtil
import com.bsoft.libcommon.arouter.CommonArouterGroup
import com.bsoft.libcommon.arouter.CommonArouterGroup.PARAM
import com.bsoft.libcommon.arouter.CommonArouterGroup.PATH
import com.bsoft.libcommon.localdata.AccountSharpref
import com.bsoft.libcommon.utils.AppForegroundUtil
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

//        NetEnvironmentUtil.initConstans(this,NetWorkInit(this))
//        NetworkApi.init(NetWorkInit(this))
        LogUtil.init()
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
        BaseAppInit.getInstance().init(this, getConfig(), object : InitListener {
            override fun needLogin(path: String, param: Bundle) {
             //   AccountFragmentActivity.appStart(path, param, false)
                ToastUtil.showShort("登录")
               //startActivity(Intent(this, HttpActivity::class.java))
            }

            override fun tokenError(msg: String) {
                onTokenError(msg)
            }

            override fun userInfoError(path: String, param: Bundle) {
                ToastUtil.showShort("未完善信息，请完善信息")
                CommonArouterGroup.getArouter(CommonArouterGroup.USER_INFO_ACTIVITY)
                    .withString(PATH, path)
                    .withBundle(PARAM, param)
                    .navigation()
            }
        })

        NetEnvironmentUtil.initConstans(this)

        AppForegroundUtil.getInstance().addCallback(object : AppForegroundUtil.ForegroundCallback {
           override fun onForegroundChange(b: Boolean) {
                LogUtil.d("HospitalPatientApplication", "onForegroundChange=$b")
                if (b) {
                    if (AccountSharpref.getInstance().getLoginState()) {
                        //getIndex()
                    } else if (tokenErrorDialogNeedShow) {
                        tokenErrorDialogNeedShow = false
                       // TokenErrorActivity.appStart(this@HospitalPatientApplication, toekenErrorMsg)
                    }
                }
            }
        })

    }
    private fun onTokenError(msg: String) { //后台时，收到error消息，先退出登录，acitivty等先不finish
       // logout()
        toekenErrorMsg = msg
        if (AppForegroundUtil.getInstance().isForeground()) {
          //  TokenErrorActivity.appStart(this@HospitalPatientApplication, toekenErrorMsg)
        } else {
            tokenErrorDialogNeedShow = true
        }
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
}