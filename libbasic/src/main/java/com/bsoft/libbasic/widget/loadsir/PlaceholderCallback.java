package com.bsoft.libbasic.widget.loadsir;

import android.content.Context;
import android.view.View;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.thirdpart.loadsir.callback.Callback;


/**
 * Description:
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.basic_layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
