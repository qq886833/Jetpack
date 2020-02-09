package com.bsoft.libmain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.libbasic.utils.StatusBar;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.arouter.interceptor.LoginNavCallback;
import com.bsoft.libcommon.commonaop.CheckNet;
import com.bsoft.libcommon.commonaop.SingleClick;
import com.bsoft.libmain.databinding.MainFragmentMyBinding;
import com.bsoft.libnavannotation.FragmentDestination;


@FragmentDestination(pageUrl = "main/tabs/my", asStart = false)
public class MyFragment extends Fragment {
    int i=0;

    private MainFragmentMyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = MainFragmentMyBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvProfil.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @CheckNet
            @Override
            public void onClick(View view) {
            //    ProfileActivity.startProfileActivity(getContext(), ProfileActivity.TAB_TYPE_ALL);

                ToastUtil.showLong("点击"+i++);
                ARouter.getInstance()
                        .build(CommonArouterGroup.CHANGE_NET_ACTIVITY)
                        .navigation(getContext(), new LoginNavCallback());
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBar.lightStatusBar(getActivity(), false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBar.lightStatusBar(getActivity(), hidden);
    }



}
