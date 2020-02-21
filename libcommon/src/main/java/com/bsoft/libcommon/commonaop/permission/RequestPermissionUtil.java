package com.bsoft.libcommon.commonaop.permission;

import android.Manifest;
import android.util.Log;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;


public class RequestPermissionUtil {

    @PermissionNeed(value = {Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, requestCode = 31)
    public void requestSensors() {
        Log.e("leo", "RequestPermissionUtil:请求传感器权限");
    }

    @PermissionCancel
    public void permissionCancel(int requestCode) {
        Log.e("leo", "RequestPermissionUtil:请求传感器权限被取消" + requestCode);
    }

    @PermissionDenied
    public void permissionDenied(int requestCode) {
        Log.e("leo", "RequestPermissionUtil:请求传感器权限被禁止" + requestCode);
    }

}
