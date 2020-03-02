package com.bsoft.pub.jetpack.webviewcommands;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libwebview.BaseWebView;
import com.bsoft.libwebview.WebviewActivity;
import com.bsoft.pub.jetpack.R;

import java.util.HashMap;

@Route(path = CommonArouterGroup.TEST_WEB_ACTIVITY)
public class MainActivityWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web);
        findViewById(R.id.openWeb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "腾讯网", "https://xw.qq.com/?f=qqcom",true);
            }
        });

        findViewById(R.id.openWeb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "AIDL测试", BaseWebView.CONTENT_SCHEME + "aidl.html",true);
            }
        });

        findViewById(R.id.openWeb3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for account level
                HashMap<String, String> accountInfo = new HashMap<>();
                accountInfo.put("username", "TestAccount");
                accountInfo.put("access_token", "880fed4ca2aabd20ae9a5dd774711de2");
                accountInfo.put("phone", "+8613989898898");
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "百度", "http://www.baidu.com",true);
            }
        });
        findViewById(R.id.alert_issue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "Alert问题", BaseWebView.CONTENT_SCHEME + "alert_issue.html",true);
            }
        });

        findViewById(R.id.auto_zoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "自适应屏幕问题", "http://www.customs.gov.cn/customs/302249/302266/302267/2491213/index.html",true);
            }
        });

        findViewById(R.id.webview_pre_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "腾讯网", "https://xw.qq.com/?f=qqcom",true);
            }
        });


        findViewById(R.id.file_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startCommonWeb(MainActivityWeb.this, "文件上传", "file:///android_asset/www/index.html",true);
            }
        });

    }
}
