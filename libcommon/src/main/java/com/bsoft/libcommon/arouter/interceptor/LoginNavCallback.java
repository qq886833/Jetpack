package com.bsoft.libcommon.arouter.interceptor;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.localdata.AccountSharpref;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class LoginNavCallback implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        if (CommonArouterGroup.NEED_LOGIN_FILTER_LIST.contains(postcard.getPath()) && !AccountSharpref.getInstance().getLoginState()) {
//            ARouter.getInstance()
//                    .build(RouterPath.ACCOUNT_LOGIN_ACTIVITY)
//                    .withString(BaseConstant.AROUTER_PATH, postcard.getPath())
//                    .withBundle(BaseConstant.AROUTER_BUNDLE, postcard.getExtras())
//                    .navigation();

            ToastUtil.showLong("zcdl");
        } else if (CommonArouterGroup.NEED_COMPLINFO_FILTER_LIST.contains(postcard.getPath()) ) {//&& TextUtils.isEmpty(LocalData.getLoginUser().idcard)
//            ARouter.getInstance()
//                    .build(RouterPath.FAMILY_COMPLETE_INFO_ACTIVITY)
//                    .withBoolean("isCanModify", true)
//                    .withBundle(BaseConstant.AROUTER_BUNDLE, postcard.getExtras())
//                    .withString(BaseConstant.AROUTER_PATH, postcard.getPath())
//                    .navigation();

            ToastUtil.showLong("wsxx");
        }
    }
}
