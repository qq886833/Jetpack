package com.bsoft.libcommon.commonaop.permission;

import android.Manifest;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libbasic.utils.log.LogUtil;
import com.bsoft.libbasic.widget.dialog.ConfirmDialog;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;
import com.bsoft.libcommon.commonaop.permission.util.SettingUtil;


public class RequestPermissionUtil {
    FragmentActivity mActivity;
    public RequestPermissionUtil(FragmentActivity activity) {
        mActivity = activity;
    }


    @PermissionNeed(value = {Manifest.permission.CALL_PHONE}, requestCode = 31)
    public void requestSensors() {
        LogUtil.e("leo", "RequestPermissionUtil:请求传感器权限");
    }

    @PermissionCancel
    public void permissionCancel(int requestCode) {
        LogUtil.e("leo", "RequestPermissionUtil:请求传感器权限被取消" + requestCode);
    }

    @PermissionDenied
    public void permissionDenied(int requestCode) {
        Log.e("leo", "RequestPermissionUtil:请求传感器权限被禁止" + requestCode);
        switch (requestCode) {
            case 111:
                showDialog("定位权限被禁止，需要手动去开启");
                break;
            case 222:
                showDialog("文件存储权限可能被禁止，需要手动去开启");
            case 333:
                showDialog("电话权限可能被禁止，需要手动去开启");
            case 444:
              //  showDialog("电话权限可能被禁止，需要手动去开启");
                break;
        }
    }


    private void showDialog(String message) {


        ConfirmDialog
                .newInstance("提示",
                        message,
                        "去开启",
                        "取消")
                .setCommonDialogListener(new ConfirmDialog.CommonDialogListener() {
                    @Override
                    public void onComplete(boolean ok, String tag) {
                        if (ok) {
                            SettingUtil.go2Setting(mActivity);
                        } else {

                        }
                    }
                }).setOutCancel(false)
                .setMargin(DensityUtil.dip2px(25))
                .show(mActivity.getSupportFragmentManager());
    }

}
