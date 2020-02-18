package com.bsoft.libcommon.commonaop.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.bsoft.libcommon.commonaop.permission.aop.IPermission;
import com.bsoft.libcommon.commonaop.permission.util.PermissionUtil;


/**
 * 专门用来请求权限的 Activity
 */
public class PermissionRequestActivity extends Activity {


    private static final String PERMISSION = "permission";
    private static final String REQUEST_CODE = "request_code";
    private static IPermission mIPermission;

    public static void startPermissionRequest(Context context, String[] permissions, int requestCode,
                                              IPermission iPermission) {

        // 获取 IPermission 接口的对象
        mIPermission = iPermission;

        // 传入参数，并且无感知的启动 PermissionRequestActivity
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION, permissions);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            // 屏蔽进入退出 Activity 的动画
            // (这个app不加也没事，可能是需要做了Activity进入退出动画的app才需要添加)
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] permissions = null;
        int requestCode = 0;

        // 获取permissions和requestCode
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            permissions = bundle.getStringArray(PERMISSION);
            requestCode = bundle.getInt(REQUEST_CODE);
        }

        // 如果没有申请权限  或者 mIPermission接口为null，直接返回
        if (permissions == null || permissions.length <= 0 || mIPermission == null) {
            Log.e("leo", "onCreate: 条件不满足，直接返回");
            finish();
            return; // 如果不要这个 return，finish 后还会走下面吗？会往下走，并直接出错
        }

        requestPermission(permissions, requestCode);
    }

    /**
     * 申请权限
     */
    private void requestPermission(String[] permissions, int requestCode) {

        // 是否已经有了这些权限
        if (PermissionUtil.hasSelfPermissions(this, permissions)) {
            // 所有权限都已经有了（granted:已经许可）
            mIPermission.onPermissionGranted();
            Log.e("leo", "Activity，requestPermission: 所有权限都已经有了");
            finish();
        } else {
            // 开始请求权限
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //检查是否都赋予了权限
        if (PermissionUtil.verifyPermissions(grantResults)) {
            // 所有权限都已经有了（granted:已近许可）
            mIPermission.onPermissionGranted();
        } else {
            // 显示提示
            if (PermissionUtil.shouldShowRequestPermissionRationale(this, permissions)) {
                // 取消权限
                mIPermission.onPermissionCanceled(requestCode);
            } else {
                // 权限被拒绝
                mIPermission.onPermissionDenied(requestCode);
            }
        }
        finish();
        // 屏蔽进入退出 Activity 的动画
        // 暂时测试出来是唯一一个真正需要调用这个方法的地方
        overridePendingTransition(0, 0);
    }
}
