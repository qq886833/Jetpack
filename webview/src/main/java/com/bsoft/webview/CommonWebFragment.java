package com.bsoft.webview;

import android.os.Bundle;
import com.bsoft.webview.constant.WebConstants;


public class CommonWebFragment extends BaseWebviewFragment {

    public String url;

    public static CommonWebFragment newInstance(String url,boolean needShow) {
        CommonWebFragment fragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        bundle.putSerializable("title", needShow);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.webview_fragment_common_web;
    }

    @Override
    public int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }


}
