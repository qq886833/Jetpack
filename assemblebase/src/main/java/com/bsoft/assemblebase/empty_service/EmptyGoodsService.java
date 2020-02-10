package com.bsoft.assemblebase.empty_service;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.bsoft.assemblebase.service.IGoodsService;


public class EmptyGoodsService implements IGoodsService {


    @Override
    public Fragment newGoodsFragment(Bundle bundle) {
        return null;
    }
}
