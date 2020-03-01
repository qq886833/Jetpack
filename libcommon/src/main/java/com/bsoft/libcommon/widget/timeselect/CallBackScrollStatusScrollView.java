package com.bsoft.libcommon.widget.timeselect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import com.bsoft.libbasic.utils.DensityUtil;

/**
 * Created by dongrong.fu on 2019/9/11
 * 默认的ScrollView需要在api 16以上才能监听滑动距离，并且控制实际的滑动距离
 */
public class CallBackScrollStatusScrollView extends ScrollView {
    private OnScrollListener listener;
    private float mDefaultY;
    private float mFirstY;
    private boolean mIsDefaultScroll = true;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public CallBackScrollStatusScrollView(Context context) {
        super(context);
    }

    public CallBackScrollStatusScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CallBackScrollStatusScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mFirstY = ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
//            case MotionEvent.ACTION_DOWN:
//                mFirstY = ev.getY();
//                break;
            case MotionEvent.ACTION_MOVE:
            break;
            case MotionEvent.ACTION_UP:
                float nowY = ev.getY();
                int finalScrollY = Math.round((mDefaultY - (nowY - mFirstY)) / DensityUtil.dip2px(getContext(),48)) * DensityUtil.dip2px(getContext(),48);
                if(finalScrollY < 0){
                    mDefaultY = 0;
                }else if(finalScrollY > getChildAt(0).getHeight() - getHeight()){
                    mDefaultY = getChildAt(0).getHeight() - getHeight();
                }else {
                    mDefaultY = finalScrollY;
                }
                smoothScrollTo(0,(int)mDefaultY);
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mIsDefaultScroll){
            mDefaultY = t;
            mIsDefaultScroll = false;
        }
        if(listener != null){
            listener.onScroll(t);
        }
    }

    public void setDefaultY(float defaultY){
        mDefaultY = defaultY;
    }
}
