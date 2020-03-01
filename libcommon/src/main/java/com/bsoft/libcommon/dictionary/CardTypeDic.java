package com.bsoft.libcommon.dictionary;

import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.dicchoice.model.ChoiceItem;

import java.util.ArrayList;

public class CardTypeDic {
    public static final String IDCARD = "01";
    public static final String HOUSE_CARD = "02";
    public static final String PASSPORT = "03";
    public static final String OFFICER_CARD = "04";
    public static final String DRIVING_CARD = "05";
    public static final String HONGKONG_CARD = "06";
    public static final String TAIWAN_CARD = "07";
    public static final String BIRTH_CARD = "11";
    public static final String OTHER_CARD = "99";


    public static String getIdcardText(String type) {
        if (TextUtils.equals(IDCARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_idcard);
        } else if (TextUtils.equals(HOUSE_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_house_card);
        } else if (TextUtils.equals(PASSPORT, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_passport);
        } else if (TextUtils.equals(OFFICER_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_officer_card);
        } else if (TextUtils.equals(DRIVING_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_driving_card);
        } else if (TextUtils.equals(HONGKONG_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_hongkong_card);
        } else if (TextUtils.equals(TAIWAN_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_taiwan_card);
        } else if (TextUtils.equals(BIRTH_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_birth_card);
        } else if (TextUtils.equals(OTHER_CARD, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_other_card);
        } else {
            return null;
        }
    }

    public static ArrayList<ChoiceItem> getChoice() {
        ArrayList<ChoiceItem> choiceItems = new ArrayList<>();

        ChoiceItem idcard = new ChoiceItem();
        idcard.setIndex(IDCARD);
        idcard.setItemName(getIdcardText(IDCARD));
        choiceItems.add(idcard);

        ChoiceItem birth = new ChoiceItem();
        birth.setIndex(BIRTH_CARD);
        birth.setItemName(getIdcardText(BIRTH_CARD));
        choiceItems.add(birth);

        ChoiceItem passport = new ChoiceItem();
        passport.setIndex(PASSPORT);
        passport.setItemName(getIdcardText(PASSPORT));
        choiceItems.add(passport);

        ChoiceItem taiwan = new ChoiceItem();
        taiwan.setIndex(TAIWAN_CARD);
        taiwan.setItemName(getIdcardText(TAIWAN_CARD));
        choiceItems.add(taiwan);

        ChoiceItem hongkong = new ChoiceItem();
        hongkong.setIndex(HONGKONG_CARD);
        hongkong.setItemName(getIdcardText(HONGKONG_CARD));
        choiceItems.add(hongkong);

        ChoiceItem officer = new ChoiceItem();
        officer.setIndex(OFFICER_CARD);
        officer.setItemName(getIdcardText(OFFICER_CARD));
        choiceItems.add(officer);

        return choiceItems;
    }

}
