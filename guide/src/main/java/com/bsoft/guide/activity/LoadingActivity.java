package com.bsoft.guide.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.guide.R;
import com.bsoft.guide.databinding.GuideActivityLoadingBinding;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libbasic.utils.StatusBar;
import com.bsoft.libbasic.widget.dialog.ConfirmDialog;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.localdata.AccountSharpref;
import com.bsoft.libcommon.localdata.AppSharpref;
import com.bsoft.libcommon.utils.CheckRoot;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

@Route(path = CommonArouterGroup.PATH_LOADING_ACTIVITY)
public class LoadingActivity extends AppCompatActivity {

    private Disposable mDisposable;
    private GuideActivityLoadingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //势必要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme);
        StatusBar.fitSystemBar(this);
        StatusBar.lightStatusBar(this, true);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.guide_activity_loading);

        if (CheckRoot.isDeviceRooted()) {
            if (!AppSharpref.getInstance().isRootTrust()) {
                ConfirmDialog
                        .newInstance(getString(R.string.guide_remind),
                                "您的设备可能已经root，是否继续运行",
                                getString(R.string.basic_confirm),
                                null)
                        .setCommonDialogListener(new ConfirmDialog.CommonDialogListener() {
                            @Override
                            public void onComplete(boolean ok, String tag) {
                                if (ok) {
                                    AppSharpref.getInstance().setRootTrust(true);
                                    loadingStart();
                                } else {
                                    finish();
                                }
                            }
                        }).setOutCancel(false)
                        .setMargin(DensityUtil.dip2px(30))
                        .show(getSupportFragmentManager());

            } else {
                loadingStart();
            }
        } else {
            loadingStart();
        }
    }

    private void loadingStart() {


        mDisposable = Observable.timer(1, TimeUnit.SECONDS)//2s后发射
          .subscribe(new Consumer<Long>() {
              @Override
              public void accept(Long aLong) throws Exception {
                  redirectTo();

              }
          });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        AppSharpref.getInstance().setShowGuide(true);

        if (AccountSharpref.getInstance().getLoginState()) {
            ARouter.getInstance().build(CommonArouterGroup.PATH_LOGIN_ACTIVITY).navigation();
        } else {
            if (!AppSharpref.getInstance().isShowGuide()) {
                ARouter.getInstance().build(CommonArouterGroup.PATH_GUIDE_ACTIVITY).navigation();
            }else {
              //  ARouter.getInstance().build(CommonArouterGroup.MAIN_TAB_ACTIVITY).navigation();
                //登录
          //     CommonArouterGroup.goLoginActivity("",new Bundle(),false);
               CommonArouterGroup.gotoActivity(CommonArouterGroup.TEST_ACTIVITY);
                //CommonArouterGroup.gotoActivity(CommonArouterGroup.PAY_ACTIVITY);
               // ARouter.getInstance().build(CommonArouterGroup.PATH_GUIDE_ACTIVITY).navigation();
            }

        }

        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDisposable!=null&& !mDisposable.isDisposed()){
            mDisposable.dispose();
        }

    }
}
