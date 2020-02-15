package com.bsoft.libbasic.base.fragment;

import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.widget.dialog.LoadingDialog;
import com.qmuiteam.qmui.widget.QMUITopBar;

public class CoreFragment extends Fragment {

    private LoadingDialog loadingDialog;
    protected QMUITopBar mTopBar;






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


}
