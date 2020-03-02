package com.bsoft.libwebview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bsoft.libwebview.utils.WebConstants;

import java.util.HashMap;

public class WebviewActivity extends AppCompatActivity {
    private String title;
    private String url;
    private Boolean havaTopBar;
    WebviewFragment webviewFragment;

    public static void startCommonWeb(Context context, String title, String url,boolean havaTopBar) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(WebConstants.INTENT_TAG_TITLE, title);
        intent.putExtra(WebConstants.INTENT_TAG_URL, url);
        intent.putExtra(WebConstants.INTENT_TAG_TOPBAR, havaTopBar);//true  显示标题
        if (context instanceof Service) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity_common_web);
        title = getIntent().getStringExtra(WebConstants.INTENT_TAG_TITLE);
        url = getIntent().getStringExtra(WebConstants.INTENT_TAG_URL);
        havaTopBar = getIntent().getBooleanExtra(WebConstants.INTENT_TAG_TOPBAR,true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        webviewFragment = null;
        webviewFragment = WebviewFragment.newInstance(title,url,havaTopBar,(HashMap<String, String>) getIntent().getExtras().getSerializable(WebConstants.INTENT_TAG_HEADERS), true);
        transaction.replace(R.id.web_view_fragment, webviewFragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webviewFragment != null && webviewFragment instanceof WebviewFragment) {
            boolean flag = webviewFragment.onKeyDown(keyCode, event);
            if (flag) {
                return flag;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
