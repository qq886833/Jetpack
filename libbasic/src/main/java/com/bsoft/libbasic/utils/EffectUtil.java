package com.bsoft.libbasic.utils;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/12/13.
 */
public class EffectUtil {

    /**
     * 添加点击效果
     *
     * @param v 需要触发的View
     * @return view自身
     */
    public static View addClickEffect(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        ObjectAnimator obj = ObjectAnimator.ofFloat(v, "alpha", 0.3f, 1.0f);
                        obj.start();
                        if (!ExitUtil.canClick(v)) {//多次点击拦截事件传递

                            return true;
                        }

                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                        ObjectAnimator.ofFloat(v, "alpha", 0.3f, 1.0f).start();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.3f).start();
                        break;
                }
                return false;
            }
        });
        return v;
    }
}
