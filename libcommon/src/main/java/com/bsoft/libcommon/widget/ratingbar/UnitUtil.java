package com.bsoft.libcommon.widget.ratingbar;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class UnitUtil {
    public UnitUtil() {
    }

    public static float dp2Px(Context context, float value) {
        if (context == null) {
            return 0.0F;
        } else {
            TypedValue typedValue = new TypedValue();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return TypedValue.applyDimension(1, value, metrics);
        }
    }

    public static float sp2Px(Context context, float value) {
        TypedValue typedValue = new TypedValue();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(2, value, metrics);
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(1, dp, resources.getDisplayMetrics());
        return (int)px;
    }
}
