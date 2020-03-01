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


/**
 * Description:
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadingCallback extends Callback {


    private @DrawableRes int resourceId;
    private String msg;


    private LoadingCallback(Builder builder) {
        this.resourceId = builder.resourceId;
        this.msg = builder.msg;
        setSuccessVisible(builder.aboveable);
    }

    @Override
    protected int onCreateView() {
        return R.layout.basic_layout_loading;
    }



    @Override
    protected void onViewCreate(Context context, View view) {

        ImageView ivLoading = view.findViewById(R.id.ivLoading);
        TextView tvMsg = view.findViewById(R.id.tvMsg);
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }else {
            tvMsg.setText(ContextProvider.get().getContext().getResources().getString(R.string.basic_loading));
        }
        if (resourceId != -1) {
            Glide.with(context).load(resourceId).into(ivLoading);
        } else {
            Glide.with(context).load(R.drawable.icon_loading).into(ivLoading);
        }
    }

    public static class Builder {

        private @DrawableRes int resourceId = -1;
        private String msg;
        private boolean aboveable;

        public LoadingCallback.Builder setImageView(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            return this;
        }
        public LoadingCallback.Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public LoadingCallback build() {
            return new LoadingCallback(this);
        }
        public LoadingCallback.Builder setAboveSuccess(boolean aboveable) {
            this.aboveable = aboveable;
            return this;
        }
    }

}
