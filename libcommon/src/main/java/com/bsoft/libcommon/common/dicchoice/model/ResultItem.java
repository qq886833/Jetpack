package com.bsoft.libcommon.common.dicchoice.model;

import android.text.TextUtils;
import com.bsoft.libcommon.model.base.BaseVo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/17.
 * 选择结果
 */
public class ResultItem extends BaseVo {
    private ArrayList<ChoiceItem> list = new ArrayList<>();

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    private String extraText;

    public void setResult(ChoiceItem item) {
        list.clear();
        list.add(item);
    }

    public void setResult(ArrayList<ChoiceItem> list) {
        if (list != null)
            this.list = list;
    }

    public ChoiceItem getItem() {
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

    public ArrayList<ChoiceItem> getList() {
        return list;
    }

    public String getValue() {
        if (list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ChoiceItem valueItem = list.get(i);
            sb.append(valueItem.getIndex()).append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    public String getDisplayValue() {
        if (list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ChoiceItem valueItem = list.get(i);
            sb.append(valueItem.getItemName()).append(",");
        }
        if (sb.length() > 0) {
            if (TextUtils.isEmpty(extraText)) {
                sb.deleteCharAt(sb.lastIndexOf(","));
            } else {
                sb.append(extraText);
            }
        }
        return sb.toString();
    }
}
