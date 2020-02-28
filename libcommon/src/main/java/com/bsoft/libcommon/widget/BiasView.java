package com.bsoft.libcommon.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.bsoft.libcommon.R;


/**
 * 斜线
 *
 *
 <com.bsoft.libcommon.widget.BiasView
 android:layout_width="@dimen/dp_200"
 android:layout_height="@dimen/dp_100"
 android:layout_marginLeft="@dimen/dp_40"
 android:layout_marginRight="@dimen/yjhealth_core_margin" />
 *
 *
 */
public class BiasView extends View {
    public BiasView(Context context) {
        super(context);
    }

    public BiasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BiasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setStrokeWidth(getResources().getDimension(R.dimen.dp_1));
        p.setColor(getResources().getColor(R.color.color_666));
        p.setAntiAlias(true);

        canvas.drawLine(getWidth(), 0, 0, getHeight(), p);
    }
}

