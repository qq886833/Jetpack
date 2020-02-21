package com.bsoft.guide.activity;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.guide.R;
import com.bsoft.guide.fragment.CommonFragment;
import com.bsoft.guide.widget.indicator.IndicatorView;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libbasic.utils.StatusBar;

import static com.bsoft.libcommon.arouter.CommonArouterGroup.PATH_GUIDE_ACTIVITY;
import static com.bsoft.libcommon.arouter.CommonArouterGroup.PATH_LOGIN_ACTIVITY;


/**
 * Created by Administrator on 2017/4/26.
 */

@Route(path = PATH_GUIDE_ACTIVITY)
public class GuideActivity extends AppCompatActivity {
    /*Default*/
    ViewPager2 viewPager2;


    //1 第一次进入  2 关于那里进入
    public int flag = 1;
    private IndicatorView indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBar.setColor(this,getResources().getColor(R.color.color_white),0);
        StatusBar.lightStatusBar(this, true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide_activity_guide);
        flag = getIntent().getIntExtra("flag", 1);

        viewPager2 = (ViewPager2) findViewById(R.id.pager2);
        indicator = (IndicatorView) findViewById(R.id.indicator);
        indicator.setIndicatorRadius(DensityUtil.dip2px(2))
                .setIndicatorSelectedRadius(DensityUtil.dip2px(2))
                .setIndicatorColor(Color.GRAY).setIndicatorSelectorColor(Color.GREEN);
        TypedArray guideArr = getResources().obtainTypedArray(R.array.guide_res);
        String[] guideTitleArr = getResources().getStringArray(R.array.guide_title);
        String[] guideSubTitleArr = getResources().getStringArray(R.array.guide_sub_title);
        final int length = guideArr.length();

        viewPager2.setOffscreenPageLimit(length);
        //限制页面预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //viewPager2默认只有一种类型的Adapter。FragmentStateAdapter
        //并且在页面切换的时候 不会调用子Fragment的setUserVisibleHint ，取而代之的是onPause(),onResume()、

        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                Fragment fragment = mFragmentMap.get(position);
//                if (fragment == null) {
//                    fragment = getTabFragment(position);
//                    mFragmentMap.put(position, fragment);
//                }
                //这里不需要自己保管了,FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                return CommonFragment.getInstance(guideArr.getResourceId(position, 0),
                        guideTitleArr[position], guideSubTitleArr[position], position == length - 1);
            }

            @Override
            public int getItemCount() {
                return length;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                indicator.onPageScrollStateChanged(state);
            }

        });

        indicator.initIndicatorCount(length);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void goIn() {
        if (flag == 1) {
          //  AppSharpref.getInstance().setShowGuide(false);
            ARouter.getInstance().build(PATH_LOGIN_ACTIVITY)
                    .greenChannel().navigation();
            finish();
        } else if (flag == 2) {

            finish();
        }
    }
}
