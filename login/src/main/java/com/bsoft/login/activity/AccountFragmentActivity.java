package com.bsoft.login.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.login.R;
import com.jeremyliao.liveeventbus.LiveEventBus;

import static com.bsoft.libcommon.arouter.CommonArouterGroup.*;


@Route(path = CommonArouterGroup.PATH_LOGIN_ACTIVITY)
public class AccountFragmentActivity extends CoreActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_navigation_account);


        //TODO: Navigation和控件绑定
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        NavController navController = navHostFragment.getNavController();

        LiveEventBus.get(LiveEventBusKey.KEY_LOGIN, Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean flag) {
                        //doLoginPath(loginEvent.getPath(),loginEvent.getBundle(),loginEvent.isSSO());
                        if (flag) {
                            doLoginPath();
                        }

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_fragment).navigateUp();
    }

    private void doLoginPath() {
        String actPath = null;
        Bundle bundle = null;
        boolean isSSO = false;
        Intent arguments = getIntent();
        if (arguments != null) {
            actPath = arguments.getStringExtra(PATH);
            bundle = arguments.getBundleExtra(PARAM);
            isSSO = arguments.getBooleanExtra(EXTRA_IS_SSO, false);
        }

        if (!TextUtils.isEmpty(actPath)) {
            CommonArouterGroup.gotoActivity(actPath, bundle);
        } else if (isSSO) {
            CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_MAIN_TAB_ACTIVITY);
        } else {
            CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_MAIN_TAB_ACTIVITY);
        }
        finish();

    }
}
