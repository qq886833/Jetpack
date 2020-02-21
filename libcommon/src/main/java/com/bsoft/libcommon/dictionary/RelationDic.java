package com.bsoft.libcommon.dictionary;

import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.model.ChoiceItem;

import java.util.ArrayList;


public class RelationDic {
    public static final String OWN = "0";
    public static final String SPOUSE = "1";
    public static final String SON = "2";
    public static final String DAOGHTER = "3";
    public static final String GRANDSON = "4";
    public static final String PARENT = "5";
    public static final String ANCESTOR = "6";
    public static final String BROTHER = "7";
    public static final String OTHER = "8";


    public static String getIdcardText(String type) {
        if (TextUtils.equals(OWN, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_own);
        } else if (TextUtils.equals(SPOUSE, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_spouse);
        } else if (TextUtils.equals(SON, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_son);
        } else if (TextUtils.equals(DAOGHTER, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_daoghter);
        } else if (TextUtils.equals(GRANDSON, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_grandson);
        } else if (TextUtils.equals(PARENT, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_parent);
        } else if (TextUtils.equals(ANCESTOR, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_ancestor);
        } else if (TextUtils.equals(BROTHER, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_brother);
        } else if (TextUtils.equals(OTHER, type)) {
            return ContextProvider.get().getApplication().getString(R.string.wise_common_other_relation);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param existSelf 是否包含本人
     * @return
     */
    public static ArrayList<ChoiceItem> getChoice(boolean existSelf) {
        ArrayList<ChoiceItem> choiceItems = new ArrayList<>();

        if(existSelf)
            choiceItems.add(new ChoiceItem(OWN, getIdcardText(OWN)));
        choiceItems.add(new ChoiceItem(SPOUSE, getIdcardText(SPOUSE)));
        choiceItems.add(new ChoiceItem(SON, getIdcardText(SON)));
        choiceItems.add(new ChoiceItem(DAOGHTER, getIdcardText(DAOGHTER)));
        choiceItems.add(new ChoiceItem(GRANDSON, getIdcardText(GRANDSON)));
        choiceItems.add(new ChoiceItem(PARENT, getIdcardText(PARENT)));
        choiceItems.add(new ChoiceItem(ANCESTOR, getIdcardText(ANCESTOR)));
        choiceItems.add(new ChoiceItem(BROTHER, getIdcardText(BROTHER)));
        choiceItems.add(new ChoiceItem(OTHER, getIdcardText(OTHER)));

        return choiceItems;
    }

    public static ArrayList<ChoiceItem> getChoice(){
        return getChoice(true);
    }

}
