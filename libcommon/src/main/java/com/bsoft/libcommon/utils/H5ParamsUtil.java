package com.bsoft.libcommon.utils;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2019/5/24
 * describe :
 */
public class H5ParamsUtil {
//
//    /********* -----文案配置  begin ------*/
//    //用户注册协议
//    public static final String PROTOCOL_REGISTER = "/#/protocal?key=UserRegisterProtocal";
//    //用户服务协议
//    public static final String PROTOCOL_SERVICE = "/#/protocal?key=AboutusUserPrivacy";
//    //用户隐私保护协议
//    public static final String PROTOCOL_PRIVACY_PROTECTION = "/#/protocal?key=US_privacy_protect";
//    //住院指南
//    public static final String PROTOCOL_INHOSPITAL_GUIDE = "/#/protocal?key=HZ_guide";
//    //电子健康卡
//    public static final String PROTOCOL_HEALTH_CARD = "/#/protocal?key=EHC_protocol";
//    //如何获取医院预留手机号码或就诊卡号
//    public static final String PROTOCOL_PHONE_CARD_GETWAY = "/#/protocal?key=PM_au_obtain";
//    //云支付支付说明
//    public static final String PROTOCOL_CLOUD_PAY = "/#/protocal?key=CC_pay_describe";
//    //云诊室云候诊常见问题
//    public static final String PROTOCOL_CLOUD_WAIT = "/#/protocal?key=CC_wait_question";
//
//    /********* -----文案配置 end ------*/
//
//
//    //医院介绍
//    public static final String HOSP_PATH = "/#/about?fromapp=1";
//    //价格公示
//    public static final String PRICE_PATH = "/#/about/price";
//    //健康资讯
//    public static final String HEALTH_PATH = "/#/about/news";
//
//    public static String getCommonParams() {
//        try {
//            return "token=" + LocalData.getLoginUser().token
//                    + "&sn=" + URLEncoder.encode(LocalData.getLoginUser().sn, "utf-8")
//                    + "&userId=" + LocalData.getLoginUser().id
//                    + "&device=" + DeviceUtil.getDeviceId(ContextUtil.getContext())
//                    + "&hospitalCode=" + LocalData.getLoginUser().getHospitalCode();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//
//    /**
//     * 获取处方笺
//     *
//     * @param visitNo
//     * @param outpatientType
//     * @return
//     */
//    public static String getPrescriptionUrl(String visitNo, String outpatientType) {
//        return StringUtil.appendStr("/#/recipe?", getCommonParams(), "&visitNo=", visitNo, "&recipeId=", "&outpatientType=", outpatientType);
//    }
//
//    /**
//     * //复诊配药
//     *
//     * @return
//     */
//    public static String getFZPYPath() {
//        String familyStr = getH5FamilyVo();
//        try {
//            return "/#/queryOnline/index?" + getCommonParams() + "&busType=2&familyPerson="  + URLEncoder.encode(familyStr, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            ToastUtil.showLong("复诊配药出错");
//            return "";
//        }
//    }
//
//    /**
//     * //云诊室
//     *
//     * @return
//     */
//    public static String getYZSPath() {
//        String familyStr = getH5FamilyVo();
//        try {
//            return "/#/queryOnline/index?" + getCommonParams() + "&busType=5&familyPerson="  + URLEncoder.encode(familyStr, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            ToastUtil.showLong("复诊配药出错");
//            return "";
//        }
//    }
//
//
//    /**
//     * //在线问诊
//     *
//     * @return
//     */
//    public static String getZXZXPath() {
//        String familyStr = getH5FamilyVo();
//        try {
//            return "/#/queryOnline/index?" + getCommonParams()+ "&busType=1,3,4&familyPerson=" + URLEncoder.encode(familyStr, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            ToastUtil.showLong("复诊配药出错");
//            return "";
//        }
//    }
//
//    public static String getDocZXZXPath(String docId, String departmentId) {
//        String url = httpUrl + "/#/queryOnline/doctorDetail?" + H5ParamsUtil.getCommonParams() + "&docid=" + docId+"&departmentId="+departmentId;
//        return url;
//    }
//
//    /**
//     * /用药提醒
//     *
//     * @return
//     */
//    public static String getQuestionnairePath() {
//        return "/#/questionnaire?" + getCommonParams();
//    }
//
//    /**
//     * /用药提醒
//     *
//     * @return
//     */
//    public static String getYYTXPath() {
//        return "/#/drugReminder/index?" + getCommonParams();
//    }
//
//
//    /**
//     * 意见反馈
//     *
//     * @return
//     */
//    public static String getFeedbackPath(Activity activity) {
//        String operater = "";
//        try {
//            operater = URLEncoder.encode(LocalData.getLoginUser().realname, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "/#/setting/feedback?" + getCommonParams() + "&roleType=1"
//                + "&appVersion=" + AppInfoUtil.getVersionCode(activity)
//                + "&osVersion=Android" + Build.VERSION.RELEASE
//                + "&operater=" + operater;
//    }
//
//    private static String getH5FamilyVo() {
//        FamilyVo cachedFamilyVo = SPUtil.getInstance().getObject("SelectedFamilyVo", FamilyVo.class);
//        String familyStr = "";
//        if (cachedFamilyVo != null && cachedFamilyVo.patientMedicalCardNumber != null) {
//            H5CachedFamilyVo h5CachedFamilyVo = new H5CachedFamilyVo();
//            h5CachedFamilyVo.uid = cachedFamilyVo.uid;
//            h5CachedFamilyVo.id = cachedFamilyVo.id;
//            h5CachedFamilyVo.mobile = cachedFamilyVo.mobile;
//            h5CachedFamilyVo.realname = cachedFamilyVo.realname;
//            h5CachedFamilyVo.relation = cachedFamilyVo.relation;
//            h5CachedFamilyVo.sexcode = cachedFamilyVo.sexcode;
//            h5CachedFamilyVo.patientcode = cachedFamilyVo.patientcode;
//            h5CachedFamilyVo.hisBusCardList = cachedFamilyVo.hisBusCardList;
//            h5CachedFamilyVo.patientcodeNumber = cachedFamilyVo.patientMedicalCardNumber;
//            h5CachedFamilyVo.activated = cachedFamilyVo.activated;
//            h5CachedFamilyVo.birthdate = cachedFamilyVo.birthdate;
//            h5CachedFamilyVo.cardtype = cachedFamilyVo.cardtype;
//            h5CachedFamilyVo.idcard = cachedFamilyVo.idcard;
//            h5CachedFamilyVo.certificationValidityPeriod = cachedFamilyVo.certificationValidityPeriod;
//            familyStr = JSON.toJSONString(h5CachedFamilyVo);
//        }
//        return familyStr;
//    }
}
