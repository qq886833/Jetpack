package com.bsoft.libcommon.model;

import android.text.TextUtils;
import com.bsoft.libcommon.utils.DateUtil;

import java.util.Date;


/**
 * Created by Administrator on 2017/4/19.
 */

public class FamilyVo extends UserInfoVo {
    /**与用户关系*/
    public String relation;
    public boolean registered;//是否注册
    public boolean isSelected;

    public boolean isAdd;

    /**
     * 是否本人
     * @return
     */
    public boolean isSelf() {
        return TextUtils.equals("0", relation);
    }


    /**
     * 是否配偶
     * @return
     */
    public boolean ifSpouse() {
        return TextUtils.equals("1", relation);
    }

    /**
     * 是否显示报告查询
     * @return
     */
    public boolean isShowReport() {
        Date dateTime = DateUtil.getDateTime("yyyy-MM-dd", dob);
        int age = 0;
        if(dateTime != null) {
            age = DateUtil.getAge(dateTime.getTime());
        }
        return age < 15;
    }

    public boolean ifRegister(){
        return registered;
    }
}
