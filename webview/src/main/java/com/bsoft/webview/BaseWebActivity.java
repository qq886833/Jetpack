package com.bsoft.webview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bsoft.webview.constant.WebConstants;

public class BaseWebActivity extends AppCompatActivity {
    private String title;
    private String url;

    BaseWebviewFragment webviewFragment;
    private boolean needShow;

    public static void startCommonWeb(Context context, String title, String url,boolean needShowTitle) {
        Intent intent = new Intent(context, BaseWebActivity.class);
        intent.putExtra(WebConstants.INTENT_TAG_TITLE, title);
        intent.putExtra(WebConstants.INTENT_TAG_URL, url);
        intent.putExtra(WebConstants.INTENT_NEED_TITLE, needShowTitle);

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
        needShow = getIntent().getBooleanExtra(WebConstants.INTENT_NEED_TITLE,true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        webviewFragment = CommonWebFragment.newInstance(url,needShow);
        transaction.replace(R.id.web_view_fragment, webviewFragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webviewFragment != null && webviewFragment instanceof BaseWebviewFragment) {
            boolean flag = webviewFragment.onKeyDown(keyCode, event);
            if (flag) {
                return flag;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
