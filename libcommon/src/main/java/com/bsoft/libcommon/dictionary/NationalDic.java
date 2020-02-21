package com.bsoft.libcommon.dictionary;

import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.model.ChoiceItem;

import java.util.ArrayList;


public class NationalDic {
    public static final String CHINA = "01";
    public static final String HONGKONG = "02";
    public static final String TAIWAN = "03";
    public static final String OVERSEAS = "04";


    public static String getIdcardText(String type) {
        if (TextUtils.equals(CHINA, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_china);
        } else if (TextUtils.equals(HONGKONG, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_hongkong);
        } else if (TextUtils.equals(TAIWAN, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_taiwan);
        } else if (TextUtils.equals(OVERSEAS, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_overseas);
        } else {
            return null;
        }
    }

    public static ArrayList<ChoiceItem> getChoice() {
        ArrayList<ChoiceItem> choiceItems = new ArrayList<>();

        choiceItems.add(new ChoiceItem(CHINA, getIdcardText(CHINA)));
        choiceItems.add(new ChoiceItem(HONGKONG, getIdcardText(HONGKONG)));
        choiceItems.add(new ChoiceItem(TAIWAN, getIdcardText(TAIWAN)));
        choiceItems.add(new ChoiceItem(OVERSEAS, getIdcardText(OVERSEAS)));

        return choiceItems;
    }

}
