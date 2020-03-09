package com.bsoft.login.fragment;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.utils.ColorUtils;
import com.bsoft.libcommon.utils.KeyboardStatusDetector;
import com.bsoft.libcommon.utils.SpanUtils;
import com.bsoft.login.R;
import com.bsoft.login.adapter.UserAdapter;
import com.bsoft.login.databinding.LoginFragmentAccountLoginTabBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class LoginTabFragment extends CoreFragment  {


    private LoginFragmentAccountLoginTabBinding mBinding;
    private EditText etUser;
    private String digitsTxt;
    private String digitsNumber;
    private UserAdapter userAdapter;
    private KeyboardStatusDetector keyboardStatusDetector;

    private static ILoginService mLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);
    private String[] tabs;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    ClickableSpan clickableService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {

            ToastUtil.showShort("服务协议");
        }

        @Override
        public void updateDrawState(TextPaint ds) {

        }
    };
    ClickableSpan clickablePolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {

            ToastUtil.showShort("隐私政策");
        }

        @Override
        public void updateDrawState(TextPaint ds) {

        }
    };

    public static LoginTabFragment newInstance(String actPath, Bundle dataBundle, boolean isSSO) {
        LoginTabFragment fragment = new LoginTabFragment();
        Bundle bundle = new Bundle();
       // bundle.putString(PATH, actPath);
      //  bundle.putBundle(PARAM, dataBundle);
      //  bundle.putBoolean(EXTRA_IS_SSO, isSSO);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoginFragmentAccountLoginTabBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTopBar();
        initTab();

        SpanUtils.with( mBinding.tvAgreeService).append("登录注册表示同意").append("《服务协议》").setForegroundColor(ColorUtils.getColor( R.color.main_green)).setUnderline()
                .setClickSpan(clickableService).append("和").append("《隐私政策》").setForegroundColor(ColorUtils.getColor( R.color.main_green)).setUnderline()
                .setClickSpan(clickablePolicy).create();

    }

    @Override
    protected void initTopBar() {
        super.initTopBar();
        mTopBar.setBackgroundColor(ColorUtils.getColor( R.color.transparent));
        mBinding.topbar.setBottomDividerAlpha(0);

        if(HttpConstants.isDebug) {
            mBinding.topbar.addRightTextButton("切换地址", QMUIViewHelper.generateViewId()).setOnClickListener(view1 ->
                    {
                        CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_CHANGE_NET_ACTIVITY);
                    }
            );
        }
    }

    private void initTab() {

        tabs = getResources().getStringArray(R.array.login_tabs);
        viewPager = mBinding.viewPager;
        tabLayout = mBinding.tabLayout;
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return getTabPositionByFragment(position);
            }

            private Fragment getTabPositionByFragment(int position) {
                switch (position) {
                    case 0:
                        return  LoginPwdFragment.newInstance();
                    case 1:
                        return LoginCodeFragment.newInstance();

                }
                return LoginPwdFragment.newInstance();
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //autoRefresh:当我们调用viewpager的adaper#notifychangged方法的时候，要不要主动的把tablayout的选项卡给移除掉重新配置
        //要在给viewpager设置adapter之后调用
        new TabLayoutMediator(tabLayout, viewPager, false, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
              //  tab.setText(tabs[position]);
                tab.setCustomView(makeTabView(position));

            }
        }).attach();



        viewPager.registerOnPageChangeCallback(mPageChangeCallback);
        //切换到默认选择项,那当然要等待初始化完成之后才有效
        //viewPager.post(() -> viewPager.setCurrentItem(0, false));
    }

    ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView customView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {

                    customView.setTextSize(12);
                    customView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    customView.setTextSize(10);
                    customView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };


    private View makeTabView(int position) {
        TextView tabView = new TextView(getContext());
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{ColorUtils.getColor( R.color.color_333),
                ColorUtils.getColor( R.color.color_666)};
        ColorStateList stateList = new ColorStateList(states, colors);
        tabView.setTextColor(stateList);
        tabView.setText(tabs[position]);
        tabView.setTextSize(10);
        return tabView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
