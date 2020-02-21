package com.bsoft.libpay.weixin;

import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;


public class WXPayBackUtil {
    public static boolean wxPayBackEnable = false;

    public static void onResp(BaseResp resp) {
        //微信支付
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX
                && wxPayBackEnable) {
            LiveEventBus.get(LiveEventBusKey.KEY_WEIXIN_CALLBACK).post(resp);
        }
    }
}
