package com.bsoft.libbasic.widget.loadsir;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libbasic.thirdpart.loadsir.callback.Callback;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


/**
 * Description:
 * Create Time:2017/9/2 16:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class TimeoutCallback extends Callback {


    private @DrawableRes
    int resourceId;
    private String msg;
    private String btText;

    private TimeoutCallback(TimeoutCallback.Builder builder) {
        this.resourceId = builder.resourceId;
        this.msg = builder.msg;
        this.btText = builder.btText;
    }

    @Override
    protected int onCreateView() {
        return R.layout.basic_layout_common_view;
    }



    @Override
    protected void onViewCreate(Context context, View view) {

        ImageView ivEmpty = view.findViewById(R.id.iv_icon);
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        QMUIRoundButton bt_refresh = view.findViewById(R.id.bt_refresh);

        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }else {
            tvMsg.setText(ContextProvider.get().getContext().getResources().getString(R.string.basic_time_out));
        }
        if (!TextUtils.isEmpty(btText)) {
            bt_refresh.setText(btText);
        }else {
            bt_refresh.setText(ContextProvider.get().getContext().getResources().getString(R.string.basic_refresh));
        }
        if (resourceId != -1) {
            Glide.with(context).load(resourceId).into(ivEmpty);
        } else {
            Glide.with(context).load(R.mipmap.icon_time_out).into(ivEmpty);
        }
    }

    public static class Builder {

        private @DrawableRes int resourceId = -1;
        private String msg;
        private String btText;

        public TimeoutCallback.Builder setImageView(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            return this;
        }
        public TimeoutCallback.Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public TimeoutCallback build() {
            return new TimeoutCallback(this);
        }

    }

}
