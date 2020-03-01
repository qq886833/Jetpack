package com.bsoft.libcommon.widget.timeselect;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;


/**
 * Created by dongrong.fu on 2019/9/11
 * 同时选择2个数字的控件，使用时传入数字数组
 */
public class SelectDoubleNumberDialog extends Dialog {
    private TextView mTvDialogCompetitionTitle;
    private TextView mTvDialogCancel;
    private TextView mTvDialogEnsure;
    private SelectNumberView mSVDialogSelectNumber1;
    private SelectNumberView mSVDialogSelectNumber2;
    private SelectNumberListener mListener;
    private Context mContext;

    public SelectDoubleNumberDialog(Context context) {
        super(context);
//        super(context, R.style.MyDialog2);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_select_double_number, null);
//        mContext = context;
//        mTvDialogCompetitionTitle = view.findViewById(R.id.tv_dialog_competition_title);
//        mTvDialogCancel = view.findViewById(R.id.tv_dialog_cancel);
//        mTvDialogEnsure = view.findViewById(R.id.tv_dialog_ensure);
//        mSVDialogSelectNumber1 = view.findViewById(R.id.snv_dialog_select_number1);
//        mSVDialogSelectNumber2 = view.findViewById(R.id.snv_dialog_select_number2);
//        mTvDialogCancel.setOnClickListener(v -> dismiss());
//        mTvDialogEnsure.setOnClickListener(v -> {
//            dismiss();
//            if(mListener != null){
//                mListener.getValue(mSVDialogSelectNumber1.getNumber(),mSVDialogSelectNumber2.getNumber());
//            }
//        });
//        addContentView(view, new ViewGroup.LayoutParams(ScreenUtils.dp2px(context, 280), ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public SelectDoubleNumberDialog setTitle(String title){
        mTvDialogCompetitionTitle.setText(title);
        return this;
    }

    public SelectDoubleNumberDialog setSelectArr(String[] selectArr1, int defaultPosition1, String[] selectArr2, int defaultPosition2){
        mSVDialogSelectNumber1.setSelectArr(selectArr1,defaultPosition1);
        mSVDialogSelectNumber2.setSelectArr(selectArr2,defaultPosition2);
        return this;
    }

    public void show(){
        mSVDialogSelectNumber1.init();
        mSVDialogSelectNumber2.init();
        super.show();
    }

    public SelectDoubleNumberDialog setSelectNumberListenr(SelectNumberListener selectNumberListenr){
        this.mListener = selectNumberListenr;
        return this;
    }

    public interface SelectNumberListener{
        void getValue(String value1, String value2);
    }

}

