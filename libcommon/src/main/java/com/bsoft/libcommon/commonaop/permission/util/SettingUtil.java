package com.bsoft.libcommon.commonaop.permission.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.bsoft.libcommon.commonaop.permission.setting.Default;
import com.bsoft.libcommon.commonaop.permission.setting.HuaWei;
import com.bsoft.libcommon.commonaop.permission.setting.ISetting;
import com.bsoft.libcommon.commonaop.permission.setting.ViVo;


public class SettingUtil {

    public static final String MANUFACTURER_HUAWEI = "huawei";//华为
    public static final String MANUFACTURER_MEIZU = "meizu";//魅族
    public static final String MANUFACTURER_XIAOMI = "xiaomi";//小米
    public static final String MANUFACTURER_SONY = "sony";//索尼
    public static final String MANUFACTURER_OPPO = "oppo";
    public static final String MANUFACTURER_LG = "lg";
    public static final String MANUFACTURER_VIVO = "vivo";
    public static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    public static final String MANUFACTURER_LETV = "letv";//乐视
    public static final String MANUFACTURER_ZTE = "zte";//中兴
    public static final String MANUFACTURER_YULONG = "yulong";//酷派
    public static final String MANUFACTURER_LENOVO = "lenovo";//联想

    /**
     * 跳到设置界面
     */
    public static void go2Setting(Context context) {
        ISetting iSetting = null;

        Log.e("leo", "go2Setting: " + Build.MANUFACTURER);

        // 产品/硬件的制造商
        switch (Build.MANUFACTURER.toLowerCase()) {
            case MANUFACTURER_VIVO:
                iSetting = new ViVo();
                break;
            case MANUFACTURER_HUAWEI:
                iSetting = new HuaWei();
                break;
            default:
                iSetting = new Default();
                break;
        }
        context.startActivity(iSetting.getSetting(context));
    }

}
