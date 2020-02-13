package com.bsoft.guide.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.guide.R;
import com.bsoft.guide.databinding.GuideActivitySplashBinding;
import com.bsoft.guide.widget.CountDownTextView;
import com.bsoft.libbasic.utils.StatusBar;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

import static com.bsoft.libcommon.arouter.CommonArouterGroup.PATH_GUIDE_ACTIVITY;


@Route(path = CommonArouterGroup.PATH_SPLASH_ACTIVITY)
public class SplashActivity extends AppCompatActivity {

    private GuideActivitySplashBinding binding;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBar.lightStatusBar(this, true);
        super.onCreate(savedInstanceState);
        // 非栈顶的时候，点击首页图标不跳转到这里
        if(!isTaskRoot()){
            finish();
            return;
        }
        binding = DataBindingUtil.setContentView(this, R.layout.guide_activity_splash);

      //  countDown();
        binding.tvSkip.setOnFinishedListener(new CountDownTextView.OnFinishedListener() {
            @Override
            public void onFinished() {
                ARouter.getInstance().build(PATH_GUIDE_ACTIVITY)
                        .greenChannel().navigation();
                finish();
            }
        });
    }

    private void countDown() {


//范围0~4，间隔时间1s
        disposable = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .doOnNext(new Consumer<Long>() {
                      @Override
                      public void accept(Long aLong) throws Exception {
                          //splashTime.setText((4 - aLong) + "s  |  跳过");//更新UI
                      }
                  })
                  .doOnComplete(new Action() {
                      @Override
                      public void run() throws Exception {
                          //goMainActivity();//关闭当前页面，跳转主页面
                          ToastUtil.showLong("关闭当前页面，跳转主页面");
                          ARouter.getInstance().build(PATH_GUIDE_ACTIVITY)
                                  .greenChannel().navigation();
                      }
                  })
                  .subscribe();


//Splash页面延迟2秒，再跳转到主页面
//        disposable = Observable.timer(2,TimeUnit.SECONDS)//2s后发射
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                       // goMainActivity();//关闭当前页面，跳转主页面
//                    }
//                });

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatusBar.lightStatusBar(this, true);
       binding.tvSkip.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        binding.tvSkip.stop();
        binding.tvSkip.reset();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null)
        disposable.dispose();
    }
}
