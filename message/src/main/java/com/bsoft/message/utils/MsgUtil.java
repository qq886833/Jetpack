package com.bsoft.message.utils;

import android.content.Context;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.message.R;
import com.bsoft.message.model.MsgDetailVo;


/**
 * Created by Administrator on 2017/9/13.
 */

public class MsgUtil {

    //*************************** 通知类别 ***************************
    //在线问诊
    public static final String INQUIRY_ONLINE = "0601";
    //药品订单
    public static final String MEDICINE_ORDER = "0602";

    //*************************** 通知 ***************************
    //************ 在线问诊 *******
    //问诊订单可支付时间剩余5分钟
    public static final String INQUIRY_ONLINE_06060101 = "06060101";
    //问诊订单超时未支付，系统自动取消
    public static final String INQUIRY_ONLINE_06060102 = "06060102";
    //医生在视频问诊中开启了视频
    public static final String INQUIRY_ONLINE_06060104 = "06060104";
    //视频问诊就诊前5分钟
    public static final String INQUIRY_ONLINE_06060105 = "06060105";
    //问诊订单医生超时未接诊，系统自动取消
    public static final String INQUIRY_ONLINE_06060111 = "06060111";
    //医生退诊
    public static final String INQUIRY_ONLINE_06060113 = "06060113";
    //************* 药品订单 *******
    //处方审核通过
    public static final String INQUIRY_MEDICINE_06060116 = "06060116";
    //订单可支付时间剩余5分钟
    public static final String INQUIRY_MEDICINE_06060201 = "06060201";
    //订单超时未支付，系统自动取消
    public static final String INQUIRY_MEDICINE_06060202 = "06060202";
    //平台收到药品订单发货信息
    public static final String INQUIRY_MEDICINE_06060203 = "06060203";
    //提交处方给对接方返回失败
    public static final String INQUIRY_MEDICINE_06060204 = "06060204";

    private static ArrayMap<String, Integer> iconMap = new ArrayMap<>();
    private static ArrayMap<String, String> clickList = new ArrayMap<>();

    static {
        //通知列表
     //   iconMap.put(INQUIRY_ONLINE, R.mipmap.wisemsg_icon_news_consult);
    //    iconMap.put(MEDICINE_ORDER, R.mipmap.wisemsg_icon_news_medicine);

        //通知详情跳转
        clickList.put(INQUIRY_ONLINE_06060101, "");
        clickList.put(INQUIRY_ONLINE_06060102, "");
        clickList.put(INQUIRY_ONLINE_06060104, "");
        clickList.put(INQUIRY_ONLINE_06060105, "");
        clickList.put(INQUIRY_ONLINE_06060111, "");
        clickList.put(INQUIRY_ONLINE_06060113, "");
        clickList.put(INQUIRY_MEDICINE_06060116, "");
        clickList.put(INQUIRY_MEDICINE_06060201, "");
        clickList.put(INQUIRY_MEDICINE_06060202, "");
    }

    public static int getIcon(String businessType) {
        Integer icon = iconMap.get(businessType);
        if (icon == null) {
           // icon = R.mipmap.wisemsg_icon_news_system;
        }
        return icon;
    }

    public static boolean isInClick(String msgType) {
        if (TextUtils.isEmpty(msgType)) {
            return false;
        }
        return clickList.keySet().contains(msgType);
    }

    public static void msgRouter(Context context, MsgDetailVo msgDetailVo) {
        if (msgDetailVo == null) {
            return;
        }

        if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060101)) {
       //     InquiryRouter.gotoPay(context, msgDetailVo.extras);
            return;
        } else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060102)) {
         //   InquiryRouter.gotoNoPay(context, msgDetailVo.extras);
            return;
        } else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060104)) {
         //   InquiryRouter.gotoVideo(context, msgDetailVo.extras);
            return;
        } else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060105)) {
         //   InquiryRouter.gotoVideo(context, msgDetailVo.extras);
            return;
        } else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060111)) {
         //   InquiryRouter.gotoNoPay(context, msgDetailVo.extras);
            return;
        } else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_ONLINE_06060113)) {
         //   InquiryRouter.gotoNoPay(context, msgDetailVo.extras);
            return;
        }else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_MEDICINE_06060116)) {
        //    InquiryRouter.gotoRevisit(context, msgDetailVo.extras);
            return;
        }else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_MEDICINE_06060201)) {
         //   InquiryRouter.gotoMedicalOrderDetail(msgDetailVo.extras);
            return;
        }else if (TextUtils.equals(msgDetailVo.notificationCode, INQUIRY_MEDICINE_06060202)) {
         //   InquiryRouter.gotoMedicalOrderDetail(msgDetailVo.extras);
            return;
        }

        String path = clickList.get(msgDetailVo.notificationCode);
        if (!TextUtils.isEmpty(path)) {
            CommonArouterGroup.getArouter(path)
                    .withString(CommonArouterGroup.PARAM, msgDetailVo.extras)
                    .navigation();
        } else {
            ToastUtil.toast(R.string.msg_router_empty);
        }
    }
}
