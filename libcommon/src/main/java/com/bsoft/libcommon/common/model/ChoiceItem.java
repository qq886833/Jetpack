package com.bsoft.libcommon.common.model;

import android.text.TextUtils;

import java.io.Serializable;

public class ChoiceItem implements Serializable {
    private String index;
    private String itemName;
    private String dicValue;
    private boolean isChoice;

    public ChoiceItem() {
    }

    public ChoiceItem(String index) {
        this.index = index;
    }

    public ChoiceItem(String index, String itemName) {
        this.index = index;
        this.itemName = itemName;
    }

    public ChoiceItem(String index, String itemName, String dicValue, boolean isChoice) {
        this.index = index;
        this.itemName = itemName;
        this.dicValue = dicValue;
        this.isChoice = isChoice;
    }

    @Override
    public boolean equals(Object o) {
        return TextUtils.equals(this.index, ((ChoiceItem) o).index);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }



}
