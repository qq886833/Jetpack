package com.bsoft.pub.jetpack

import com.bsoft.libbasic.BaseApplication
import com.bsoft.libnet.base.NetworkApi
import com.bsoft.libnet.utils.NetEnvironmentUtil
import com.bsoft.libnet.utils.log.LogUtil
import com.jeremyliao.liveeventbus.LiveEventBus



class AppApplication : BaseApplication() {

        init{
            instance = this
        }
    companion object{
        lateinit var instance : AppApplication
    }

    override fun onCreate() {
        super.onCreate()

        NetEnvironmentUtil.initConstans(this,NetWorkInit(this))
        NetworkApi.init(NetWorkInit(this))
        LogUtil.init()
        LiveEventBus.config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
//        LogUtils.getConfig().run {
//            isLogSwitch = BuildConfig.DEBUG
//            setSingleTagSwitch(true)
//        }


    }
}