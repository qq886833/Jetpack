package com.bsoft.libcommon.arouter;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;

import java.util.Arrays;
import java.util.List;


/**
 * Created by chenkai on 2018/6/28.
 */

public class CommonArouterGroup {
    //单点登录
    public static final String EXTRA_IS_SSO = "isSSO";
    public static final String PATH = "path";
    public static final String PARAM = "param";
    public static final String CALLBACK = "callback";
    public static String AROUTER_NAME = ContextProvider.get().getApplication().getString(R.string.common_arouter_name);
    //TODO 根据项目修改
    public static final int PRIORITY = 111;
    private static final String PROJECT = "/wise_";

    //******************** GUIDE **********************
    public static final String PATH_SPLASH_ACTIVITY = PROJECT + "guide/SplashActivity";
    public static final String PATH_GUIDE_ACTIVITY = PROJECT + "guide/GuideActivity";
    public static final String PATH_LOADING_ACTIVITY = PROJECT + "guide/LoadingActivity";



    public static final String PATH_CHANGE_NET_ACTIVITY = PROJECT + "net/ChangeNetActivity";




    public static final String PATH_LOGIN_ACTIVITY = PROJECT + "login/AccountFragmentActivity";




    public static final String PATH_MAIN_TAB_ACTIVITY = PROJECT + "libmain/MainTabActivity";



    public static final String TEST_ACTIVITY = PROJECT + "app/HttpActivity";
    //******************** wiseChangeNet **********************


    //******************** commonlib **********************
    public static final String COMMON_WEB_ACTIVITY = PROJECT + "commonlib/web/CommonWebActivity";
    public static final String COMMON_WEB_FRAGMENT = PROJECT + "commonlib/web/CommonWebFragment";
    public static final String CHANGE_PERSION_ACTIVITY = PROJECT + "commonlib/ChangePersonActivity";

    //******************** businessGuide *******************


    //******************** businessAppoint *******************
    public static final String APPOINT_CONFIRM_ACTIVITY = PROJECT + "businessAppoint/AppointConfirmActivity";
    public static final String APPOINT_DEPT_ACTIVITY = PROJECT + "businessAppoint/AppointDeptActivity";
    public static final String APPOINT_ORDER_FRAGMENT_ACTIVITY = PROJECT + "businessAppoint/AppointOrderFragmentActivity";
    //******************** businessChineseBody *******************
    public static final String BODY_TEST_MAIN_ACTIVITY = PROJECT + "businessChineseBody/BodyTestMainActivity";
    //******************** businessHealthBody *******************
    public static final String HEALTH_BODY_CALCULATEACTIVITY_ACTIVITY = PROJECT + "businessHealthBody/CalculateActivity";
    //******************** businessMedicalRemind *******************
    public static final String MEDICATION_REMINDER_ACTIVITY = PROJECT + "businessMedicalRemind/MedicationReminderActivity";

    //******************** businessAccount *******************

    //******************** wiseMessage *******************
    public static final String MSG_LIST_ACTIVITY = PROJECT + "wiseMessage/MsgListActivity";

    //******************** wiseSettings *******************
    public static final String SETTINGS_ACTIVITY = PROJECT + "wiseSettings/SettingsActivity";

    //******************** wiseFamily *******************
    public static final String FAMILY_MANAGER_ACTIVITY = PROJECT + "wiseFamily/ChangePersonActivity";
    public static final String FAMILY_INFO_ACTIVITY = PROJECT + "wiseFamily/FamilyInfoActivity";
    public static final String FAMILY_ADD_ACTIVITY = PROJECT + "wiseFamily/FamilyAddActivity";
    public static final String USER_INFO_ACTIVITY = PROJECT + "wiseFamily/UserInfoActivity";
    public static final String CARD_MANAGER_ACTIVITY = PROJECT + "wiseFamily/CardManagerActivity";

    //******************** wiseHealth *******************
    public static final String HEALTH_ACTIVITY = PROJECT + "wiseHealth/HealthActivity";

    public static Postcard getArouter(String path) {
        Postcard postcard = ARouter.getInstance()
                .build(path);
        postcard.setName(AROUTER_NAME);
        return postcard;
    }

    /**
     * go to Activity
     */
    public static void gotoActivity(String path) {
        getArouter(path).navigation();
    }

    /**
     * go to Activity
     */
    public static void gotoActivity(String path, Bundle bundle) {
        Postcard postcard = getArouter(path)
                .with(bundle);
        postcard.navigation();
    }

    public static void goMainTabActivity() {
        CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_MAIN_TAB_ACTIVITY);
    }
    public static void goLoginActivity(String path, Bundle bundle, boolean isSSO){
        CommonArouterGroup.getArouter(CommonArouterGroup.PATH_LOGIN_ACTIVITY)
                .withString(PATH, path)
                .withBundle(PARAM, bundle)
                .withBoolean(EXTRA_IS_SSO, isSSO)
                .navigation();
    }


    public static final List<String> NEED_LOGIN_FILTER_LIST = Arrays.asList(
           // PATH_CHANGE_NET_ACTIVITY
//            APPOINT_NOTICE_ACTIVITY,//预约挂号
//            CLOUD_HOME_ACTIVITY,   //云诊室
//            REPORT_HOME_ACTIVITY,//门诊、住院报告
//            PREPAY_HOME_ACTIVITY,//住院金预缴
//            SIGN_HOME_ACTIVITY, //签到取号
//            QUEUE_TWO_TAB_ACTIVITY,//排队叫号
//            PAY_HOME_ACTIVITY,//诊间支付
//            MZFY_HOME_ACTIVITY,//门诊费用
//            FZPY_FZPY_ACTIVITY,//复诊配药
//            ZXWZ_ZXWZ_ACTIVITY,//在线问诊
//            FAMILY_MY_FAMILY_LIST_ACTIVITY, //就诊人管理
//            APPOINT_RECORD_ACTIVITY, //预约记录
//            RECHARGE_ACCOUNT_QUERY_ACTIVITY, //账户查询
//
//            ACCOUNT_SETTING_ACTIVITY, //账号设置
//            // FAMILY_COMPLETE_INFO_ACTIVITY//完善信息
//            APP_ZY_SERVICE_ACTIVITY,//住院服务
//            HEALTH_RECORD_ACTIVTY //健康记录

    );

    public static final List<String> NEED_COMPLINFO_FILTER_LIST = Arrays.asList(
           // PATH_CHANGE_NET_ACTIVITY
//            APPOINT_NOTICE_ACTIVITY,//预约挂号
//            CLOUD_HOME_ACTIVITY,   //云诊室
//            REPORT_HOME_ACTIVITY,//门诊、住院报告
//            PREPAY_HOME_ACTIVITY,//住院金预缴
//            SIGN_HOME_ACTIVITY, //签到取号
//            QUEUE_TWO_TAB_ACTIVITY,//排队叫号
//            PAY_HOME_ACTIVITY,//诊间支付
//            MZFY_HOME_ACTIVITY,//门诊费用
//            FZPY_FZPY_ACTIVITY,//复诊配药
//            ZXWZ_ZXWZ_ACTIVITY,//在线问诊
//            FAMILY_MY_FAMILY_LIST_ACTIVITY, //就诊人管理
//            APPOINT_RECORD_ACTIVITY, //预约记录
//            RECHARGE_ACCOUNT_QUERY_ACTIVITY,//账户查询;
//            APP_ZY_SERVICE_ACTIVITY,//住院服务
//            FAMILY_CERTIFICATE_ACTIVITY,//身份认证
//            HEALTH_RECORD_ACTIVTY //健康记录
    );
}
