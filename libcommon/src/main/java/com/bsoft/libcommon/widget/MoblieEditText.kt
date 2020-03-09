package com.bsoft.libcommon.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bsoft.libcommon.R
import com.bsoft.libcommon.utils.ColorUtils

class MoblieEditText  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context,attrs,defStyleAttr) ,TextWatcher{


    private var draw:Drawable ?=null
    private var drawShow:Drawable ?=null
    init {
        draw=ContextCompat.getDrawable(context,R.drawable.icon_editclear)
        draw!!.setTint(ColorUtils.getColor(R.color.color_999))
        val minimumWidth: Int =draw!!.minimumWidth
        val minimumHeight: Int =draw!!.minimumHeight
        draw!!.setBounds(0,0,minimumWidth,minimumHeight)
    }

    private fun isShow(isShow:Boolean){
        drawShow=if (isShow){
            draw
        }else{
           null
        }
        setCompoundDrawables(compoundDrawables[0],compoundDrawables[1],drawShow,compoundDrawables[3])
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if (TextUtils.isEmpty(text.toString())){
            isShow(false)
        }else{
            isShow(true)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action==MotionEvent.ACTION_DOWN){
            var isDelete : Boolean =event.x>(width-totalPaddingRight)&&event.x<(width-paddingRight)
                    &&event.y>0&&event.y<height
            if (isDelete){
                setText("")
            }
        }

        return super.onTouchEvent(event)
    }
}