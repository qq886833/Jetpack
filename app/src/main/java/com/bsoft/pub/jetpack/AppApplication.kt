package com.bsoft.pub.jetpack

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.bsoft.libbasic.application.AppConfig
import com.bsoft.libbasic.application.BaseApp
import com.bsoft.libbasic.constant.HttpConstants
import com.bsoft.libbasic.init.BaseAppInit
import com.bsoft.libbasic.init.BaseInitConfig
import com.bsoft.libcommon.localdata.AccountSharpref
import com.bsoft.libnet.utils.NetEnvironmentUtil
import com.bsoft.libnet.utils.log.LogUtil


class AppApplication : BaseApp() {
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

        initModuleApp(this)
        initModuleData(this)
        initComponentList()


        BaseAppInit.getInstance().init(this, getConfig())

        NetEnvironmentUtil.initConstans(this)
        LogUtil.init()
        JPushInterface.setDebugMode(HttpConstants.isDebug)
        JPushInterface.init(this)
        InitializeService.start(applicationContext)

    }
    override fun initModuleApp(application: Application?) {

    }
    override fun initModuleData(application: Application?) {

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

    //初始化组件
//通过反射初始化
    private fun initComponentList() {
        for (moduleApp in AppConfig.moduleApps) {
            try {
                val clazz = Class.forName(moduleApp)
                val baseApp = clazz.newInstance() as BaseApp
                baseApp.initModuleApp(this)
                baseApp.initModuleData(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun logout() {
        AccountSharpref.getInstance().loginState = false
    //    AccountSharpref.getInstance().setAccessToken(null)
    //    LocalDataUtil.getInstance().saveIndexVo(null)
        //互联网医院退出
    //    InquiryDocLogin.logout()
    }
}