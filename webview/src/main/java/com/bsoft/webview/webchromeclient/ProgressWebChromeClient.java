package com.bsoft.webview.webchromeclient;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.webview.ProgressWebView;
import com.jeremyliao.liveeventbus.LiveEventBus;


public class ProgressWebChromeClient extends WebChromeClient {

    private Handler progressHandler;

    public ProgressWebChromeClient(Handler progressHandler) {
        this.progressHandler = progressHandler;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);

        if (view instanceof ProgressWebView) {
            if (!TextUtils.isEmpty(title)) {
                LiveEventBus.get(LiveEventBusKey.KEY_WEBVIEW_TITLE).post(title);
            }
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Message message = new Message();
        if (newProgress == 100) {
            message.obj = newProgress;
            progressHandler.sendMessageDelayed(message, 200);
        } else {
            if (newProgress < 10) {
                newProgress = 10;
            }
            message.obj = newProgress;
            progressHandler.sendMessage(message);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(final WebView view, String url, String message, JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //按钮事件
                                Toast.makeText(view.getContext(), view.getContext().getString(android.R.string.ok) + " clicked.", Toast.LENGTH_LONG).show();
                            }
                        }).show();
        //result.confirm();// 不加这行代码，会造成Alert劫持：Alert只会弹出一次，并且WebView会卡死
        return true;
    }
}
