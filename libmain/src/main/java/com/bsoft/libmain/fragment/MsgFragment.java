package com.bsoft.libmain.fragment;

import android.util.Log;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libnavannotation.FragmentDestination;


@FragmentDestination(pageUrl = "main/tabs/msg", asStart = false)
public class MsgFragment extends CoreFragment {


    @Override
    protected void lazyLoadData() {
        Log.e("lazy","main/tabs/msg");
    }
}
