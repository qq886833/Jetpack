package com.bsoft.libwebview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.thirdpart.loadsir.callback.Callback;
import com.bsoft.libbasic.thirdpart.loadsir.core.LoadService;
import com.bsoft.libbasic.thirdpart.loadsir.core.LoadSir;
import com.bsoft.libbasic.widget.loadsir.LoadingCallback;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.libwebview.command.CommandDispatcher;
import com.bsoft.libwebview.utils.WebConstants;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.HashMap;
import java.util.Map;

public class WebviewFragment extends CoreFragment implements WebViewCallBack {
    public static final String ACCOUNT_INFO_HEADERS = "account_header";
    protected BaseWebView webView;
    protected HashMap<String, String> accountInfoHeaders;
    public static final int REQUEST_CODE_LOLIPOP = 1;

    public String webUrl;
    private String title;
    private Boolean havaTopBar;
    LoadService loadService;

    public static WebviewFragment newInstance(@NonNull String title,@NonNull String keyUrl,@NonNull boolean havaTopBar, @NonNull HashMap<String, String> headers, boolean isSyncToCookie) {
        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(getBundle(title,keyUrl,havaTopBar, headers));
        if(isSyncToCookie && headers != null) {
            syncCookie(keyUrl, (headers));
        }
        return fragment;
    }

    public static Bundle getBundle(@NonNull String title,@NonNull String url,@NonNull boolean havaTopBar, @NonNull HashMap<String, String> headers) {
        Bundle bundle = new Bundle();
        bundle.putString(WebConstants.INTENT_TAG_TITLE, title);
        bundle.putString(WebConstants.INTENT_TAG_URL, url);
        bundle.putBoolean(WebConstants.INTENT_TAG_TOPBAR, havaTopBar);
        bundle.putSerializable(ACCOUNT_INFO_HEADERS, headers);
        return bundle;
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url, Map<String, String> map) {
        CookieManager cookieManager = CookieManager.getInstance();
        for(String key:map.keySet()){
            cookieManager.setCookie(url, key+"="+map.get(key));
        }
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            webUrl = bundle.getString(WebConstants.INTENT_TAG_URL);
            title = bundle.getString(WebConstants.INTENT_TAG_TITLE);
            havaTopBar = bundle.getBoolean(WebConstants.INTENT_TAG_TOPBAR);
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

        loadService = LoadSir.getDefault().register(webView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                // your retry logic
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.registerdWebViewCallBack(this);
        CommandDispatcher.getsInstance().initAidlConnect(getContext().getApplicationContext());
        loadUrl();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTopBar.setVisibility(havaTopBar?View.VISIBLE:View.GONE);
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
    public void pageStarted(String url) {
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void pageFinished(String url) {
        loadService.showSuccess();
    }

    @Override
    public void onError() {
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

    @Override
    public void onShowFileChooser(Intent cameraIntent, ValueCallback<Uri[]> filePathCallback) {
        //整个弹出框为:相机、相册、文件管理
        //如果安装了其他的相机、文件管理程序，也有可能会弹出
        //selectionIntent(相册、文件管理)
        //Intent selectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //selectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        //selectionIntent.setType("image/*");
        mFilePathCallback = filePathCallback;
        //------------------------------------
        //如果通过下面的方式，则弹出的选择框有:相机、相册(Android9.0,Android8.0)
        //如果是小米Android6.0系统上，依然是：相机、相册、文件管理
        //如果安装了其他的相机(百度魔拍)、文件管理程序(ES文件管理器)，也有可能会弹出
        Intent selectionIntent = new Intent(Intent.ACTION_PICK,null);
        selectionIntent.setType("image/*");
        //------------------------------------

        Intent[] intentArray;
        if (cameraIntent != null) {
            intentArray = new Intent[]{cameraIntent};
        } else {
            intentArray = new Intent[0];
        }

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.file_chooser));
        chooserIntent.putExtra(Intent.EXTRA_INTENT, selectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

        startActivityForResult(chooserIntent, REQUEST_CODE_LOLIPOP);

    }

    @Override
    public void onUpdateTitle(String title) {
        if(getActivity() != null) {
            getActivity().setTitle(title);
        }
    }

    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_LOLIPOP:
                Uri[] results = null;
                // Check that the response is a good one
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        // If there is not data, then we may have taken a photo
                        if (mCameraPhotoPath != null) {
                            Log.d("AppChooserFragment", mCameraPhotoPath);

                            results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        }
                    } else {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }

                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
                break;
        }
    }
    protected int getLayoutRes() {
        return R.layout.webview_fragment_common_web;
    }
}
