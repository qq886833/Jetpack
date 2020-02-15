package com.bsoft.libbasic.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


public class ErrorView extends LinearLayout {
    private ImageView icon;
    private TextView title;
    private QMUIRoundButton btRefresh;

    public ErrorView(@NonNull Context context) {
        this(context, null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int style) {
        super(context, attrs, defStyleAttr, style);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.basic_layout_error_view, this, true);

        icon = findViewById(R.id.iv_error);
        title = findViewById(R.id.tv_error);
        btRefresh = findViewById(R.id.bt_refresh);
    }


    public void setErrorIcon(@DrawableRes int iconRes) {
        icon.setImageResource(iconRes);
    }

    public void setContent(String text) {
        if (TextUtils.isEmpty(text)) {
            title.setVisibility(GONE);
        } else {
            title.setText(text);
            title.setVisibility(VISIBLE);
        }

    }

    public void setButton(String text, OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            btRefresh.setVisibility(GONE);
        } else {
            btRefresh.setText(text);
            btRefresh.setVisibility(VISIBLE);
            btRefresh.setOnClickListener(listener);
        }

    }
}
