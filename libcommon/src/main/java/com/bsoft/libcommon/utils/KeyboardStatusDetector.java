package com.bsoft.libcommon.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.fragment.app.Fragment;

public class KeyboardStatusDetector {

    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 200;
    private KeyboardVisibilityListener mVisibilityListener;

    boolean keyboardVisible = false;
    
    View rootView;
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    public KeyboardStatusDetector registerFragment(Fragment f) {
        return registerView(f.getView());
    }

    public KeyboardStatusDetector registerActivity(Activity a) {
        return registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public KeyboardStatusDetector registerView(final View v) {
        rootView = v;
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than 100 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(true);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false);
                        }
                    }
                }
            }
        };
        v.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        return this;
    }
    
    public void destroy(){
        if(rootView != null){
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public KeyboardStatusDetector setVisibilityListener(KeyboardVisibilityListener listener) {
        mVisibilityListener = listener;
        return this;
    }

    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean keyboardVisible);
    }
}
