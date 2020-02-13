package com.bsoft.pub.jetpack

import cn.jpush.android.api.JPushInterface
import com.bsoft.libbasic.BaseApplication
import com.bsoft.libbasic.constant.HttpConstants
import com.bsoft.libbasic.init.ArouterInit
import com.bsoft.libbasic.init.BaseAppInit
import com.bsoft.libbasic.init.BaseInitConfig
import com.bsoft.libcommon.InitializeService
import com.bsoft.libcommon.localdata.AccountSharpref
import com.bsoft.libnet.utils.NetEnvironmentUtil
import com.bsoft.libnet.utils.log.LogUtil



class AppApplication : BaseApplication() {

        init{
            instance = this
        }
    companion object{
        lateinit var instance : AppApplication
    }




    override fun onCreate() {
        super.onCreate()




        BaseAppInit.getInstance().init(this, getConfig())
        ArouterInit.init(this)
        NetEnvironmentUtil.initConstans(this)
        LogUtil.init()
        JPushInterface.setDebugMode(HttpConstants.isDebug)
        JPushInterface.init(this)
        InitializeService.start(applicationContext)

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