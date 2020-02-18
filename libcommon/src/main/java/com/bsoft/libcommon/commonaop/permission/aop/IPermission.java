package com.bsoft.libcommon.commonaop.permission.aop;

public interface IPermission {

    // 同意权限
    void onPermissionGranted();

    // 拒绝权限并且选中不再提示
    void onPermissionDenied(int requestCode);

    //取消权限
    void onPermissionCanceled(int requestCode);
}
