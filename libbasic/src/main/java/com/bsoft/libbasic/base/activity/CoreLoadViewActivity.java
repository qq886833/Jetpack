package com.bsoft.libbasic.base.activity;

import android.view.View;
import com.bsoft.libbasic.base.IBaseView;
import com.bsoft.libbasic.thirdpart.loadsir.callback.Callback;
import com.bsoft.libbasic.thirdpart.loadsir.core.LoadService;
import com.bsoft.libbasic.thirdpart.loadsir.core.LoadSir;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libbasic.widget.loadsir.EmptyCallback;
import com.bsoft.libbasic.widget.loadsir.ErrorCallback;
import com.bsoft.libbasic.widget.loadsir.LoadingCallback;
import com.bsoft.libbasic.widget.loadsir.PlaceholderCallback;


public abstract class CoreLoadViewActivity extends CoreActivity implements IBaseView {


    private LoadService mLoadService;
    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }
    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }else {
            ToastUtil.showLong( message);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
    @Override
    public void showPlaceholder() {
        if (mLoadService != null) {
            mLoadService.showCallback(PlaceholderCallback.class);
        }
    }
    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    protected abstract void onRetryBtnClick();
}
