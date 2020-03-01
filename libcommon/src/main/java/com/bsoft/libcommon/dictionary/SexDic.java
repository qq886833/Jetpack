package com.bsoft.libcommon.dictionary;

import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.dicchoice.model.ChoiceItem;

import java.util.ArrayList;

public class SexDic {
    public static final String MAN = "1";
    public static final String WOMEN = "2";

    public static String getSexStr(String type) {
        if (TextUtils.equals(type, MAN)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_man);
        } else if (TextUtils.equals(type, WOMEN)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_woman);
        } else {
            return "";
        }
    }

    public static ArrayList<ChoiceItem> getChoice() {
        ArrayList<ChoiceItem> choiceItems = new ArrayList<>();

        ChoiceItem idcard = new ChoiceItem();
        idcard.setIndex(MAN);
        idcard.setItemName(getSexStr(MAN));
        choiceItems.add(idcard);

        ChoiceItem officer = new ChoiceItem();
        officer.setIndex(WOMEN);
        officer.setItemName(getSexStr(WOMEN));
        choiceItems.add(officer);

        return choiceItems;
    }
}
