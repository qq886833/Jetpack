package com.bsoft.assemblebase.service;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public interface IGoodsService {

    /**
     * 创建 GoodsFragment
     * @param bundle
     * @return
     */
    Fragment newGoodsFragment(Bundle bundle);
}
