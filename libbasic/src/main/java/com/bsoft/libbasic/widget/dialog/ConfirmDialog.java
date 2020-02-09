package com.bsoft.libbasic.widget.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.utils.EffectUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class ConfirmDialog extends BaseDialog {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";
    private static final String ARG_INFO = "info";
    private static final String ARG_COMFIRM_TEXT = "comfirm_text";
    private static final String ARG_CANCEL_TEXT = "cancel_text";

    private CommonDialogListener listener;

    private String title;
    private String content;
    private String info;
    private String confirmText;
    private String cancelText;
    public static ConfirmDialog newInstance(String content, String confirmText) {
        return newInstance(null, content, null, confirmText, null);
    }

    public static ConfirmDialog newInstance(String content, String confirmText, String cancelText) {
        return newInstance(null, content, null, confirmText, cancelText);
    }

    public static ConfirmDialog newInstance(String title, String content, String confirmText, String cancelText) {
        return newInstance(title, content, null, confirmText, cancelText);
    }

    public static ConfirmDialog newInstance2(String content, String info, String confirmText, String cancelText) {
        return newInstance(null, content, info, confirmText, cancelText);
    }
    public static ConfirmDialog newInstance(String title, String content, String info, String confirmText, String cancelText) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_INFO, info);
        args.putString(ARG_COMFIRM_TEXT, confirmText);
        args.putString(ARG_CANCEL_TEXT, cancelText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString(ARG_TITLE);
        content = bundle.getString(ARG_CONTENT);
        info = bundle.getString(ARG_INFO);
        confirmText = bundle.getString(ARG_COMFIRM_TEXT);
        cancelText =bundle.getString(ARG_CANCEL_TEXT);
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.base_dialog_confirm;
    }

    public ConfirmDialog setCommonDialogListener(CommonDialogListener listener) {
        this.listener = listener;
        return this;
    }
    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {



       TextView tvTitle= holder.getView(R.id.tvTitle);
        TextView tvContent= holder.getView(R.id.tvContent);
        TextView tvInfo= holder.getView(R.id.tvInfo);
        QMUIRoundButton btConfirm= holder.getView(R.id.btConfirm);
        QMUIRoundButton btCancel= holder.getView(R.id.btCancel);
         View divder= holder.getView(R.id.divder);
        tvContent.setText(content);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(info)) {
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText(info);
        } else {
            tvInfo.setVisibility(View.GONE);
        }

        btConfirm.setText(confirmText);
        EffectUtil.addClickEffect(btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onComplete(true, getTag());
                }
                dismiss();
            }
        });

        if (!TextUtils.isEmpty(cancelText)) {
            btCancel.setVisibility(View.VISIBLE);
            divder.setVisibility(View.VISIBLE);
            btCancel.setText(cancelText);
            EffectUtil.addClickEffect(btCancel);
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onComplete(false, getTag());
                    }
                    dismiss();
                }
            });
        } else {
            btCancel.setVisibility(View.GONE);
            divder.setVisibility(View.GONE);
        }


        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (listener != null) {
                        listener.onComplete(false, getTag());
                    }
                    dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });


    }


    public interface CommonDialogListener {
        void onComplete(boolean ok, String tag);
    }
}

