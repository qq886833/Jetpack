package com.bsoft.libcommon.arouter;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;


/**
 * Created by chenkai on 2018/6/28.
 */

public class CommonArouterGroup {
    public static final String PATH = "path";
    public static final String PARAM = "param";
    public static final String CALLBACK = "callback";
    public static String AROUTER_NAME = ContextProvider.get().getApplication().getString(R.string.common_arouter_name);
    //TODO 根据项目修改
    public static final int PRIORITY = 111;
    private static final String PROJECT = "/wise_";


    public static final String MAIN_TAB_ACTIVITY = PROJECT + "libmain/mainTabActivity";
    public static final String LOGIN_ACTIVITY = PROJECT + "login/LoginActivity";
    public static final String TEST_ACTIVITY = PROJECT + "app/HttpActivity";
    //******************** wiseChangeNet **********************
    public static final String CHANGE_NET_ACTIVITY = PROJECT + "wiseChangeNet/changenet/ChangeNetActivity";

    //******************** commonlib **********************
    public static final String COMMON_WEB_ACTIVITY = PROJECT + "commonlib/web/CommonWebActivity";
    public static final String COMMON_WEB_FRAGMENT = PROJECT + "commonlib/web/CommonWebFragment";
    public static final String CHANGE_PERSION_ACTIVITY = PROJECT + "commonlib/ChangePersonActivity";

    //******************** businessGuide *******************
    public static final String PATH_GUIDE_ACTIVITY = PROJECT + "businessGuide/GuideActivity";

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
        CommonArouterGroup.gotoActivity(CommonArouterGroup.MAIN_TAB_ACTIVITY);
    }
}
