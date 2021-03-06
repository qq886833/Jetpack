package com.bsoft.libmain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libbasic.utils.StatusBar;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.localdata.AccountSharpref;
import com.bsoft.libcommon.model.LoginUserVo;
import com.bsoft.libmain.model.Destination;
import com.bsoft.libmain.utils.MainConfig;
import com.bsoft.libmain.utils.NavGraphBuilder;
import com.bsoft.libmain.widget.AppBottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * App 主页 入口
 * <p>
 * 1.底部导航栏 使用AppBottomBar 承载
 * 2.内容区域 使用WindowInsetsNavHostFragment 承载
 * <p>
 * 3.底部导航栏 和 内容区域的 切换联动 使用NavController驱动
 * 4.底部导航栏 按钮个数和 内容区域destination个数。由注解处理器NavProcessor来收集,生成assetsdestination.json。而后我们解析它。
 */

@Route(path = CommonArouterGroup.PATH_MAIN_TAB_ACTIVITY)
public class MainTabActivity extends CoreActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static ILoginService mLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);

    private NavController navController;
    private AppBottomBar navView;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //由于 启动时设置了 R.style.launcher 的windowBackground属性
        //势必要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme);

        //启用沉浸式布局，白底黑字
        StatusBar.fitSystemBar(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_main_tab);
        navView = findViewById(R.id.nav_view);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = NavHostFragment.findNavController(fragment);
        NavGraphBuilder.build(this, navController, fragment.getId());

        navView.setOnNavigationItemSelectedListener(this);

        navView.setBadgeCount(1,13);

        //通过ARouter 获取其他组件提供的fragment
        //Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
      //  navView.setSelectedItemId(1264295461);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        HashMap<String, Destination> destConfig = MainConfig.getDestConfig();
        Iterator<Map.Entry<String, Destination>> iterator = destConfig.entrySet().iterator();
        //遍历 target destination 是否需要登录拦截
        while (iterator.hasNext()) {
            Map.Entry<String, Destination> entry = iterator.next();
            Destination value = entry.getValue();

            if (value != null && !mLoginService.isLogin() && value.needLogin && value.id == menuItem.getItemId()) {
                AccountSharpref.getInstance().login(this).observe(this, new Observer<LoginUserVo>() {
                    @Override
                    public void onChanged(LoginUserVo user) {
                        navView.setSelectedItemId(menuItem.getItemId());
                    }
                });
                return false;
            }
        }

        navController.navigate(menuItem.getItemId());
        return !TextUtils.isEmpty(menuItem.getTitle());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }




    @Override
    public void onBackPressed() {
//        boolean shouldIntercept = false;
//        int homeDestinationId = 0;
//
//        Fragment fragment = getSupportFragmentManager().getPrimaryNavigationFragment();
//        String tag = fragment.getTag();
//        int currentPageDestId = Integer.parseInt(tag);
//
//        HashMap<String, Destination> config = MainConfig.getDestConfig();
//        Iterator<Map.Entry<String, Destination>> iterator = config.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Destination> next = iterator.next();
//            Destination destination = next.getValue();
//            if (!destination.asStarter && destination.id == currentPageDestId) {
//                shouldIntercept = true;
//            }
//
//            if (destination.asStarter) {
//                homeDestinationId = destination.id;
//            }
//        }
//
//        if (shouldIntercept && homeDestinationId > 0) {
//            navView.setSelectedItemId(homeDestinationId);
//            return;
//        }
//        super.onBackPressed();

        //当前正在显示的页面destinationId
        int currentPageId = navController.getCurrentDestination().getId();

        //APP页面路导航结构图  首页的destinationId
        int homeDestId = navController.getGraph().getStartDestination();

        //如果当前正在显示的页面不是首页，而我们点击了返回键，则拦截。
        if (currentPageId != homeDestId) {
            navView.setSelectedItemId(homeDestId);
            return;
        }

        //否则 finish，此处不宜调用onBackPressed。因为navigation会操作回退栈,切换到之前显示的页面。
        finish();
    }
}
