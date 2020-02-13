package com.bsoft.guide.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.guide.R;
import com.bsoft.libcommon.utils.AppUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

import static com.bsoft.libcommon.arouter.CommonArouterGroup.MAIN_TAB_ACTIVITY;

public class AdActivity extends AppCompatActivity {

    private Disposable disposable;
    private QMUIRoundButton tvTime;
    boolean seeAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.guide_activity_ad);
        Bitmap bm = BitmapFactory.decodeFile(AppUtil.getStoreImageDir("ad") + "ad.png");
        ImageView ivAd = findViewById(R.id.ivAd);
      //  ivAd.setImageBitmap(bm);
        ObjectAnimator.ofFloat(ivAd, "alpha",0, 1).setDuration(300).start();

        ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BannerVo localAd = LocalDataUtil.getInstance().getLoadingAd();
//                if(localAd != null && !TextUtils.isEmpty(localAd.linkAddress)){
//                    seeAd = true;
//                    SimpleWebActivity.appStart(activity, localAd.linkAddress, "", ThemeConfigUtil.getTheme());
//                }
            }
        });

        tvTime = findViewById(R.id.tvTime);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectTo();
            }
        });

        //范围0~4，间隔时间1s
        disposable = Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //splashTime.setText((4 - aLong) + "s  |  跳过");//更新UI
                        tvTime.setText("跳过 " + (4 - aLong)+"s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        tvTime.setText("跳过 0");
                        redirectTo();
                    }
                })
                .subscribe();

    }
    private void redirectTo() {
        if(seeAd) return;
        ARouter.getInstance().build(MAIN_TAB_ACTIVITY)
                .greenChannel().navigation();

        disposable = Observable.timer(500,TimeUnit.MILLISECONDS)//2s后发射
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        finish();
                    }
                });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        seeAd = false;
        redirectTo();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
