package com.bsoft.family.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.bsoft.family.BR;

/**
 * Created by bao.liu on 2016/11/25.
 */

public class ShowMode extends BaseObservable {
    private boolean add;
    private boolean editable;
    private int step;

    public ShowMode() {
    }

    public ShowMode(boolean add) {
        this.add = add;
    }

    @Bindable
    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
        notifyPropertyChanged(BR.add);
    }

    @Bindable
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
        notifyPropertyChanged(BR.step);
    }
    @Bindable
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyPropertyChanged(BR.editable);
    }
}
