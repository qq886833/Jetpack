package com.bsoft.libcommon.widget.switchbutton;

import android.animation.*;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import com.bsoft.libcommon.R;


/**
 * Created by mundane on 2017/4/4
 *
 *
 <com.bsoft.libcommon.widget.switchbutton.SwitchButton
 android:id="@+id/sbNongSuo"
 android:layout_width="@dimen/dp_50"
 android:layout_height="@dimen/dp_25"
 android:layout_alignParentRight="true"
 android:layout_centerVertical="true" />
 *
 */

public class SwitchButton extends View implements View.OnClickListener {

    private float switchViewStrockWidth;
    private int switchViewCloseBgColor;
    private int switchViewOpenBgColor;
    private int switchViewBallColor;
    private Paint mBallPaint;
    private Paint mBgPaint;
    private int mViewHeight;
    private int mViewWidth;
    private int mStrokeRadius;
    private float mSolidRadius;
    private RectF mBgStrokeRectF;
    private int BALL_X_RIGHT;

    private Handler handler = new Handler();

    private State mCurrentState;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.commonSwitchView);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.commonSwitchView_switch_bg_color_close) {
                switchViewCloseBgColor = typedArray.getColor(attr, Color.GRAY);

            } else if (attr == R.styleable.commonSwitchView_switch_bg_color_open) {
                switchViewOpenBgColor = typedArray.getColor(attr, Color.GREEN);

            } else if (attr == R.styleable.commonSwitchView_switch_ball_color) {
                switchViewBallColor = typedArray.getColor(attr, Color.WHITE);

            }
        }
        typedArray.recycle();
        initData();
    }

    private int greyColor;
    private int greenColor;

    private void initData() {

        greyColor = switchViewCloseBgColor;
        greenColor = switchViewOpenBgColor;

        mBallPaint = createPaint(switchViewBallColor, 0, Paint.Style.FILL, 0);
        mBgPaint = createPaint(switchViewCloseBgColor, 0, Paint.Style.FILL, 0);
        mCurrentState = State.CLOSE;
        setOnClickListener(this);
    }

    private float mSwitchBallx;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewHeight = h;
        mViewWidth = w;

        //	默认描边宽度是控件宽度的1/30, 比如控件宽度是120dp, 描边宽度就是4dp
        switchViewStrockWidth = w * 1.0f / 30;

        mStrokeRadius = mViewHeight / 2;
        mSolidRadius = (mViewHeight - 2 * switchViewStrockWidth) / 2;
        BALL_X_RIGHT = mViewWidth - mStrokeRadius;


        mSwitchBallx = mStrokeRadius;
        mBgStrokeRectF = new RectF(0, 0, mViewWidth, mViewHeight);

    }

    private static final int DEF_H = 60;
    private static final int DEF_W = 120;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth;
        int measureHeight;

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST://wrap_content
                measureWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_W, getResources().getDisplayMetrics());
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.EXACTLY:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST://wrap_content
                measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_H, getResources().getDisplayMetrics());
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.EXACTLY:
                break;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSwitchBg(canvas);
        drawSwitchBall(canvas);
    }

    private void drawSwitchBall(Canvas canvas) {
        canvas.drawCircle(mSwitchBallx, mStrokeRadius, mSolidRadius, mBallPaint);
    }

    private void drawSwitchBg(Canvas canvas) {
        canvas.drawRoundRect(mBgStrokeRectF, mStrokeRadius, mStrokeRadius, mBgPaint);
    }

    private Paint createPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(paintColor);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);//设置防抖动
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private enum State {
        OPEN, CLOSE
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton buttonView, boolean isChecked);
    }

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    @Override
    public void onClick(View v) {
        mCurrentState = (mCurrentState == State.CLOSE ? State.OPEN : State.CLOSE);
        //绿色	#1AAC19
        //灰色	#999999
        if (mCurrentState == State.CLOSE) {
            animate(BALL_X_RIGHT, mStrokeRadius, greenColor, greyColor);
        } else {
            animate(mStrokeRadius, BALL_X_RIGHT, greyColor, greenColor);
        }
        if (mOnCheckedChangeListener != null) {
            if (mCurrentState == State.OPEN) {
                mOnCheckedChangeListener.onCheckedChanged(this, true);
            } else {
                mOnCheckedChangeListener.onCheckedChanged(this, false);
            }
        }
    }

    private Runnable checkedRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCurrentState == State.CLOSE) {
                animate(BALL_X_RIGHT, mStrokeRadius, greenColor, greyColor);
            } else {
                animate(mStrokeRadius, BALL_X_RIGHT, greyColor, greenColor);
            }

            if (mOnCheckedChangeListener != null) {
                if (mCurrentState == State.OPEN) {
                    mOnCheckedChangeListener.onCheckedChanged(SwitchButton.this, true);
                } else {
                    mOnCheckedChangeListener.onCheckedChanged(SwitchButton.this, false);
                }
            }
        }
    };

    public void setChecked(final boolean checked) {
        if ((checked && mCurrentState == State.OPEN)
                || (!checked && mCurrentState == State.CLOSE)) {
            return;
        }

        mCurrentState = (checked ? State.OPEN : State.CLOSE);
        handler.removeCallbacks(checkedRunnable);
        handler.postDelayed(checkedRunnable, 100);
    }

    private void animate(int from, int to, int startColor, int endColor) {
        ValueAnimator translate = ValueAnimator.ofFloat(from, to);
        translate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSwitchBallx = ((float) animation.getAnimatedValue());
                postInvalidate();
            }
        });

        ValueAnimator color = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        color.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                switchViewCloseBgColor = ((int) animation.getAnimatedValue());
                mBgPaint.setColor(switchViewCloseBgColor);
                postInvalidate();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translate, color);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setClickable(true);
            }
        });
        animatorSet.start();
    }
}
