package com.bsoft.libwebview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.bsoft.libwebview.command.CommandDispatcher;
import com.bsoft.libwebview.settings.WebviewDefaultSetting;
import com.bsoft.libwebview.utils.progressbar.IndicatorHandler;
import com.bsoft.libwebview.utils.progressbar.WebProgressBar;
import com.bsoft.libwebview.webchromeclient.ProgressWebChromeClient;
import com.bsoft.libwebview.webviewclient.BaseWebviewClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BaseWebView extends WebView implements BaseWebviewClient.WebviewTouch {
    private static final String TAG = "BaseWebView";
    public static final String CONTENT_SCHEME = "file:///android_asset/";
    protected Context context;
    private WebViewCallBack webViewCallBack;
    private Map<String, String> mHeaders;
    private BaseWebviewClient mXiangxueWebviewClient;

    private IndicatorHandler indicatorHandler;
    private WebProgressBar progressBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            indicatorHandler.progress(progress);
        }
    };

    @Override
    public Handler getHandler() {
        return handler;
    }

    public BaseWebView(Context context) {
        super(context);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public WebViewCallBack getWebViewCallBack() {
        return webViewCallBack;
    }

    public void registerdWebViewCallBack(WebViewCallBack webViewCallBack) {
        this.webViewCallBack = webViewCallBack;
        mXiangxueWebviewClient = new BaseWebviewClient(this, webViewCallBack, mHeaders, this);
        setWebViewClient(mXiangxueWebviewClient);
        setWebChromeClient(new ProgressWebChromeClient(handler, webViewCallBack));
    }

    public void setHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }

    protected void init(Context context) {
        this.context = context;
        WebviewDefaultSetting.getInstance().toSetting(this);
        addJavascriptInterface(this, "webview");
        progressBar = new WebProgressBar(context);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setVisibility(GONE);
        addView(progressBar);
        indicatorHandler = IndicatorHandler.getInstance().inJectProgressView(progressBar);
    }

    @JavascriptInterface
    public void post(final String cmd, final String param) {
        // For test only.
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("fc".equalsIgnoreCase(cmd)) {
                    String fcString = null;
                    fcString.length();
                }
            }
        }, 10);
        CommandDispatcher.getsInstance().exec(cmd, param, BaseWebView.this);
    }

    @Override
    public void loadUrl(String url) {
        if (mHeaders == null) {
            super.loadUrl(url);
        } else {
            super.loadUrl(url, mHeaders);
        }
        Log.e(TAG, "DWebView load url: " + url);
        resetAllStateInternal(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
        Log.e(TAG, "DWebView load url: " + url);
        resetAllStateInternal(url);
    }

    public void handleCallback(String response) {
        if (!TextUtils.isEmpty(response)) {
            String trigger = "javascript:" + "dj.callback" + "(" + response + ")";
            evaluateJavascript(trigger, null);
        }
    }

    public void dispatchEvent(String name) {
        Map<String, String> param = new HashMap<>(1);
        param.put("name", name);
        String trigger = "javascript:" + "dj.dispatchEvent" + "(" + new Gson().toJson(param) + ")";
        evaluateJavascript(trigger, null);
    }

    private boolean mTouchByUser;

    public boolean isTouchByUser() {
        return mTouchByUser;
    }

    private void resetAllStateInternal(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("javascript:")) {
            return;
        }
        resetAllState();
    }

    // 加载url时重置touch状态
    protected void resetAllState() {
        mTouchByUser = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchByUser = true;
                break;
        }
        return super.onTouchEvent(event);
    }
}