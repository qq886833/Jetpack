package com.bsoft.libbasic.widget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.R;
import com.bumptech.glide.Glide;

public class LoadingDialog extends BaseDialog {

    private static final String ARG_RESOURCE_ID = "resourceId";
    private static final String ARG_MSG = "msg";

    private int resourceId;
    private String msg;

    public static LoadingDialog newInstance(@DrawableRes int resourceId, String msg) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_RESOURCE_ID, resourceId);
        bundle.putString(ARG_MSG, msg);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        resourceId = bundle.getInt(ARG_RESOURCE_ID);
        msg = bundle.getString(ARG_MSG);
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.base_dialog_loading;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {


        ImageView ivLoading = holder.getView(R.id.ivLoading);
       TextView tvMsg= holder.getView(R.id.tvMsg);

        if (resourceId != 0) {
            Glide.with(getActivity()).load(resourceId).into(ivLoading);
        } else {
            Glide.with(getActivity()).load(R.drawable.icon_loading).into(ivLoading);
        }

        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(msg);
        } else {
            tvMsg.setVisibility(View.GONE);
        }

    }
}

