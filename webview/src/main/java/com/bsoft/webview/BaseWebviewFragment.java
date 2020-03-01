package com.bsoft.webview;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.webview.callback.WebViewCallBack;
import com.bsoft.webview.constant.WebConstants;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.HashMap;

public abstract class BaseWebviewFragment extends CoreFragment implements WebViewCallBack {
    public static final String ACCOUNT_INFO_HEADERS = "account_header";
    protected BaseWebView webView;
    protected HashMap<String, String> accountInfoHeaders;

    public String webUrl;
    private boolean needShow;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            webUrl = bundle.getString(WebConstants.INTENT_TAG_URL);
            needShow = bundle.getBoolean(WebConstants.INTENT_NEED_TITLE);
            if(bundle.containsKey(ACCOUNT_INFO_HEADERS)){
                accountInfoHeaders = (HashMap<String, String>) bundle.getSerializable(ACCOUNT_INFO_HEADERS);
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        webView = view.findViewById(R.id.web_view);
        mTopBar = view.findViewById(R.id.topbar);
        if(accountInfoHeaders != null) {
            webView.setHeaders(accountInfoHeaders);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.registerdWebViewCallBack(this);
        loadUrl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTopBar.setVisibility(needShow?View.VISIBLE:View.GONE);
        LiveEventBus.get(LiveEventBusKey.KEY_WEBVIEW_TITLE, String.class)
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        mTopBar.setTitle(s);
                    }
                });
                        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!onBackHandle()){
                  getActivity().onBackPressed();
              }else{
                  if (webView.canGoBack()) {
                      webView.goBack();
                  }

              }

            }
        });


    }

    protected void loadUrl() {
        webView.loadUrl(webUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.dispatchEvent("pageResume");
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.dispatchEvent("pagePause");
        webView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        webView.dispatchEvent("pageStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.dispatchEvent("pageDestroy");
        clearWebView(webView);
    }


    @Override
    public int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }

    @Override
    public void pageStarted(String url) {

    }

    @Override
    public void pageFinished(String url) {

    }

    @Override
    public boolean overrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onError() {

    }

    @Override
    public void exec(Context context, String cmd, String params, WebView webView) {

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return onBackHandle();
        }
        return false;
    }

    protected boolean onBackHandle() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void clearWebView(WebView m) {
        if (m == null)
            return;
        if (Looper.myLooper() != Looper.getMainLooper())
            return;
        m.stopLoading();
        if (m.getHandler() != null) {
            m.getHandler().removeCallbacksAndMessages(null);
        }
        m.removeAllViews();
        ViewGroup mViewGroup = null;
        if ((mViewGroup = ((ViewGroup) m.getParent())) != null) {
            mViewGroup.removeView(m);
        }
        m.setWebChromeClient(null);
        m.setWebViewClient(null);
        m.setTag(null);
        m.clearHistory();
        m.destroy();
        m = null;
    }


    //********************** Js - Android ******************


}
