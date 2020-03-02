package com.bsoft.libcommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bsoft.libcommon.R;


/**
 * Created by diwang on 2018/2/2.
 */

public class LEditTextItemView extends RelativeLayout {


    private LayoutInflater mMInflater;
    private RelativeLayout contentView;

    private RelativeLayout relayout;
    private ImageView leftImg;
    private ImageView rightImg;
    private TextView leftText;
    private TextView rightText;


    private Context context;
    private String leftTitle = null;
    private String rightTitle = null;
    private int leftImgIds;
    private int rightImgIds;
    private float imgLpadding,tvLpaddingTop;
    private float imgRpadding,tvLpaddingBottom;
    private float imgLmargin;
    private float imgRmargin;
    private float leftTvSize;
    private float rightTvSize;
    private int leftTitleColor;
    private int rightTitleColor;
    MenuOptionsClickListener menuOptionsClickListener;
    private boolean mRightTitleHint;

    public LEditTextItemView(Context context) {
        super(context);
        this.context = context;
    }

    public LEditTextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public LEditTextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }




    /**
     * 初始化界面
     *
     * @param context
     */
    void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LEditTextItemView);
        leftTitle = typedArray.getString(R.styleable.LEditTextItemView_title_left);
        rightTitle = typedArray.getString(R.styleable.LEditTextItemView_title_right);
        mRightTitleHint = typedArray.getBoolean(R.styleable.LEditTextItemView_title_right_hint,false);
        leftTitleColor = typedArray.getColor(R.styleable.LEditTextItemView_title_left_color, Color.parseColor("#323232"));
        rightTitleColor = typedArray.getColor(R.styleable.LEditTextItemView_title_right_color, Color.parseColor("#999999"));
        leftImgIds = typedArray.getResourceId(R.styleable.LEditTextItemView_img_left_src,-1);
        rightImgIds = typedArray.getResourceId(R.styleable.LEditTextItemView_img_right_src,-1);

        imgLpadding = typedArray.getDimension(R.styleable.LEditTextItemView_imgLpadding,0);
        imgRpadding = typedArray.getDimension(R.styleable.LEditTextItemView_imgRpadding,0);
        imgLmargin = typedArray.getDimension(R.styleable.LEditTextItemView_imgLmargin,0);
        imgRmargin = typedArray.getDimension(R.styleable.LEditTextItemView_imgRmargin,0);

        tvLpaddingTop = typedArray.getDimension(R.styleable.LEditTextItemView_tvLpaddingTop,0);
        tvLpaddingBottom = typedArray.getDimension(R.styleable.LEditTextItemView_tvLpaddingBottom,0);



        leftTvSize = typedArray.getDimension(R.styleable.LEditTextItemView_leftTvSize,0);
        rightTvSize = typedArray.getDimension(R.styleable.LEditTextItemView_rightTvSize,0);
        typedArray.recycle();

        init(context);

    }

    private void init(Context context) {
        mMInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = (RelativeLayout) mMInflater.inflate(R.layout.common_layout_menu_options, null);
        addView(contentView);
        leftImg = (ImageView) contentView.findViewById(R.id.iv_left);
        rightImg = (ImageView) contentView.findViewById(R.id.iv_right);
        leftText = (TextView) contentView.findViewById(R.id.tv_title_left);
        rightText = (TextView) contentView.findViewById(R.id.tv_title_right);
        relayout = (RelativeLayout) contentView.findViewById(R.id.relayout);

        tvStatChange();

        if(leftImgIds!=-1) {
            leftImg.setImageResource(leftImgIds);
        }
        if(rightImgIds!=-1) {
            rightImg.setImageResource(rightImgIds);
            Drawable drawable=ContextCompat.getDrawable(context, R.drawable.icon_arrow_right);
                    drawable.setTint(ContextCompat.getColor(getContext(), R.color.color_white));
            rightImg.setImageDrawable(drawable);
          ;
        }
        leftText.setText(leftTitle);
        leftText.setPadding(0,(int)tvLpaddingTop,0,(int)tvLpaddingBottom);
        if(mRightTitleHint) {
            rightText.setHint(rightTitle);
        }else{
            rightText.setText(rightTitle);
        }
        rightText.setPadding(0,(int)tvLpaddingTop,0,(int)tvLpaddingBottom);
        leftText.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTvSize+1);
        leftText.setTextColor(leftTitleColor);
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightTvSize+1);
        rightText.setTextColor(rightTitleColor);
        leftImg.setPadding((int)imgLpadding,(int)imgLmargin,(int)imgLmargin,(int)imgLmargin);
        rightImg.setPadding((int)imgRmargin ,(int)imgRmargin,(int)imgRpadding,(int)imgRmargin);

        relayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOptionsClickListener != null) {
                    menuOptionsClickListener.relayoutClick();
                }
            }
        });
    }


    /**
     *  动态设置3个TextView的显示和隐藏
     */
    public void tvStatChange(){

        if ((leftTitle == null)||(leftTitle.equals(""))){
            leftText.setVisibility(GONE);
        }else {
            leftText.setVisibility(VISIBLE);
        }

        if ((rightTitle == null)||(rightTitle.equals(""))){
            rightText.setVisibility(GONE);
        }else {
            rightText.setVisibility(VISIBLE);
        }

    }

    public void setLeftText(String str){
        leftTitle = str;
        leftText.setText(leftTitle);
        tvStatChange();
    }

    public void setRightText(String str){
        rightTitle = str;
        rightText.setText(rightTitle);
        tvStatChange();
    }
    public void setLeftColor(int color){
        leftText.setTextColor(color);
    }
    public void setRightColor(int color){
        rightText.setTextColor(color);
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public String getRightTitle() {
        return rightText.getText().toString().trim();
    }



    public void setLeftImgSrc(int Ids){
        leftImg.setImageResource(Ids);
    }

    public void setRightImgSrc(int Ids){
        rightImg.setImageResource(Ids);
    }

    public interface MenuOptionsClickListener{
        void relayoutClick();
    }

    public void setMenuOptionsClickListener(MenuOptionsClickListener menuOptionsClickListener) {
        this.menuOptionsClickListener = menuOptionsClickListener;
    }
}
