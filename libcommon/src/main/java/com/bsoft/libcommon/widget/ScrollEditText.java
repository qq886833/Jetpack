package com.bsoft.libcommon.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * 解决与ScrollView滚动冲突的问题
 */
@SuppressLint("AppCompatCustomView")
public class ScrollEditText extends AppCompatEditText {

    public ScrollEditText(Context context) {
        super(context);
    }

    public ScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getLineCount() > 3)
            getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    public void setTextChangeListener(final Context context, final int maxNumber, final TextView numberTv) {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = getText().toString().trim().length();
                numberTv.setText(number + "/" + maxNumber);
                if (number == maxNumber) {
                    Toast.makeText(context, "字数不能多于" + maxNumber + "字", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
