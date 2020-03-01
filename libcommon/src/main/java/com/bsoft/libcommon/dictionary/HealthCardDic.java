package com.bsoft.libcommon.dictionary;

import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.dicchoice.model.ChoiceItem;

import java.util.ArrayList;


public class HealthCardDic {
    //社保卡
    public static final String SOCIAL_CARD = "01";
    //就诊卡
    public static final String MEDICAL_CARD = "02";


    public static String getIdcardText(String type) {
        if (TextUtils.equals(SOCIAL_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_social_card);
        } else if (TextUtils.equals(MEDICAL_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_medical_card);
        } else {
            return null;
        }
    }

    public static ArrayList<ChoiceItem> getChoice() {
        ArrayList<ChoiceItem> choiceItems = new ArrayList<>();

        choiceItems.add(new ChoiceItem(SOCIAL_CARD, getIdcardText(SOCIAL_CARD)));
        choiceItems.add(new ChoiceItem(MEDICAL_CARD, getIdcardText(MEDICAL_CARD)));

        return choiceItems;
    }

}
