package com.bsoft.libmain.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libbasic.widget.dialog.ConfirmDialog;
import com.bsoft.libmain.activity.ProfileActivity;
import com.bsoft.libmain.databinding.MainFragmentHomeBinding;
import com.bsoft.libnavannotation.FragmentDestination;


@FragmentDestination(pageUrl = "main/tabs/home", asStart = true)
public class HomeFragment extends CoreFragment {


    private MainFragmentHomeBinding binding;

    @Override
    protected void lazyLoadData() {
        Log.e("lazy","main/tabs/home");
    }
    public static HomeFragment newInstance(String tabType) {
        Bundle args = new Bundle();
        args.putString(ProfileActivity.KEY_TAB_TYPE, tabType);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = MainFragmentHomeBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConfirmDialog.newInstance("你好jhsjahfjhfjhjsdfhj","确定","取消")
                .setCommonDialogListener(new ConfirmDialog.CommonDialogListener() {
                    @Override
                    public void onComplete(boolean ok, String tag) {
                        if (ok) {
                            ToastUtil.showLong(tag+"======="+ok);
                        }else{
                            ToastUtil.showLong(tag+"======="+ok);
                        }
                    }
                })


            .setMargin(60)
                .setOutCancel(true)
                .show(getFragmentManager());

    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
//        String tabType = childFragment.getArguments().getString(ProfileActivity.KEY_TAB_TYPE);
//        ToastUtil.showLong(tabType);
//       binding.tvContent.setText(tabType);
    }
}
