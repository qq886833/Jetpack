package com.bsoft.libbasic.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.StringRes;
import com.bsoft.libbasic.context.ContextProvider;


public class ToastUtil {
    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextProvider.get().getContext(), "", Toast.LENGTH_LONG);
        }
        if (!TextUtils.isEmpty(msg)) {
            mToast.setText(msg);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showShort(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextProvider.get().getContext(), "", Toast.LENGTH_SHORT);
        }
        if (!TextUtils.isEmpty(msg)) {
            mToast.setText(msg);
            mToast.show();
        }
    }

    public static void toast(@StringRes int resId) {
        Toast.makeText(ContextProvider.get().getContext(),ContextProvider.get().getContext().getText(resId),
                Toast.LENGTH_SHORT).show();
    }
}
