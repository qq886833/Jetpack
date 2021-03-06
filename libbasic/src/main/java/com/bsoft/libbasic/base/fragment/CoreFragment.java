package com.bsoft.libbasic.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.widget.dialog.LoadingDialog;
import com.qmuiteam.qmui.widget.QMUITopBar;

public class CoreFragment extends LifeCycleFragment {

    protected LoadingDialog loadingDialog;
    protected QMUITopBar mTopBar;
    protected FragmentActivity activity;
    //是否第一次加载
    private boolean isFirst= true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onVisible();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }
    protected void initTopBar() {
        mTopBar = getView().findViewById(R.id.topbar);
        // mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.app_color_theme_4));
        if(mTopBar!=null){
            mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  getActivity().onBackPressed();
                    //Navigation.findNavController(getView()).navigateUp();
                }
            });

//            mTopBar.addRightTextButton("添加", QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
        //mTopBar.setTitle(mQDItemDescription.getName());
    }


    //************************************ loadingDialog *******************************************
    public void showLoadingDialog() {
        showLoadingDialog(0, null);
    }

    public void showLoadingDialog(@DrawableRes int resourceId, String msg) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance(resourceId, msg);
        }
        loadingDialog.show(getChildFragmentManager(), "loading");
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onVisible();
    }

    private void onVisible() {
        if (getLifecycle().getCurrentState() == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData();
            isFirst = false;
        }
    }
    protected void  lazyLoadData(){}



    @Override
    public void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();
    }



















}
