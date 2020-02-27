package com.bsoft.libbasic.base.activity;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.utils.StatusBar;
import com.bsoft.libbasic.widget.dialog.LoadingDialog;
import com.qmuiteam.qmui.widget.QMUITopBar;

public class CoreActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;
    protected QMUITopBar mTopBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBar.fitSystemBar(this);
        StatusBar.lightStatusBar(this, true);
        super.onCreate(savedInstanceState);
    }

    protected void initTopBar() {
        mTopBar = findViewById(R.id.topbar);
       // mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.app_color_theme_4));
        if(mTopBar!=null){
            mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finish();
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
        loadingDialog.show(getSupportFragmentManager(), "loading");
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }


}
