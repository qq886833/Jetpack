package com.bsoft.libpay.widget;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bsoft.libpay.R;
import com.bsoft.libpay.model.PayTypeVo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 文 件 名: AnimationAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 15:33
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class PayTypeAdapter extends BaseQuickAdapter<PayTypeVo, BaseViewHolder> {
    public PayTypeAdapter(List<PayTypeVo> data) {
        super(R.layout.pay_item_pay_type_select,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PayTypeVo item) {

        ((ImageView) helper.getView(R.id.pay_type_iv)).setImageResource(item.payIcon);
        ((TextView) helper.getView(R.id.pay_type_tv)).setText(item.payTypeText);
        ((TextView) helper.getView(R.id.pay_explain_tv)).setText(item.payExplain);
        ((ImageView) helper.getView(R.id.select_iv)).setImageResource(item.isSelected ?
                R.drawable.pay_icon_type_selected : R.drawable.pay_icon_type_unselected);


    }


}
