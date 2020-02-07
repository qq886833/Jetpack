package com.bsoft.libbasic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CornerRelativeLayout extends RelativeLayout {

    public CornerRelativeLayout(Context context) {
        this(context, null);
    }

    public CornerRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CornerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes);
    }
}
