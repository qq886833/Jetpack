package com.bsoft.libcommon.arouter.interceptor;

import android.content.Context;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.localdata.AccountSharpref;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/5/5.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
@Interceptor(name = "login", priority = 3)
public class LoginInterceptor implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if ((CommonArouterGroup.NEED_LOGIN_FILTER_LIST.contains(postcard.getPath()) && !AccountSharpref.getInstance().getLoginState())
                || (CommonArouterGroup.NEED_COMPLINFO_FILTER_LIST.contains(postcard.getPath()))) {// && TextUtils.isEmpty(LocalData.getLoginUser().idcard) 完善信息
            callback.onInterrupt(null);
            return;
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
