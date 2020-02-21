package com.bsoft.libpay;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PayFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new PayLifeCycle());
    }

}
