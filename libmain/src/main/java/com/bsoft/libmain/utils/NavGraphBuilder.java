package com.bsoft.libmain.utils;

import android.content.ComponentName;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.*;
import androidx.navigation.fragment.FragmentNavigator;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libbasic.widget.navigator.FixFragmentNavigator;
import com.bsoft.libmain.model.Destination;

import java.util.HashMap;
import java.util.Iterator;

public class NavGraphBuilder {
    public static void build(FragmentActivity activity, NavController controller, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();

        //NavGraphNavigator也是页面路由导航器的一种，只不过他比较特殊。
        //它只为默认的展示页提供导航服务,但真正的跳转还是交给对应的navigator来完成的
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        //fragment的导航此处使用我们定制的FixFragmentNavigator，底部Tab切换时 使用hide()/show(),而不是replace()
        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(activity, activity.getSupportFragmentManager(), containerId);
        provider.addNavigator(fragmentNavigator);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        HashMap<String, Destination> destConfig = MainConfig.getDestConfig();
        Iterator<Destination> iterator = destConfig.values().iterator();
        while (iterator.hasNext()) {
            Destination node = iterator.next();
            if (node.isFragment) {
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setId(node.id);
                destination.setClassName(node.className);
                destination.addDeepLink(node.pageUrl);
                navGraph.addDestination(destination);
            } else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(node.id);
                destination.setComponentName(new ComponentName(ContextProvider.get().getApplication().getPackageName(), node.className));
                destination.addDeepLink(node.pageUrl);
                navGraph.addDestination(destination);
            }

            //给APP页面导航结果图 设置一个默认的展示页的id
            if (node.asStart) {
                navGraph.setStartDestination(node.id);
            }
        }

        controller.setGraph(navGraph);
    }
}
