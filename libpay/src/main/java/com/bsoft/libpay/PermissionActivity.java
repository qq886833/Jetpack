package com.bsoft.libpay;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.commonaop.PermissionCancel;
import com.bsoft.libcommon.commonaop.PermissionDenied;
import com.bsoft.libcommon.commonaop.PermissionNeed;
import com.bsoft.libcommon.commonaop.PermissionService;
import com.bsoft.libcommon.utils.SettingUtil;

@Route(path = CommonArouterGroup.PAY_ACTIVITY)
public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rr_activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        // 开启服务请求权限
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(PermissionActivity.this, PermissionService.class));
            }
        });

        // 开启普通类请求权限
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   new RequestPermissionUtil().requestSensors();
            }
        });
    }

    /**
     * 申请单个权限
     */
    @PermissionNeed(value = {Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 1111)
    public void requestLocation() {
        Toast.makeText(this, "定位权限申请通过", Toast.LENGTH_SHORT).show();
    }

    /**
     * 申请多个权限
     */
    @PermissionNeed(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, requestCode = 1222)
    public void requestPermission() {
        Toast.makeText(this, "电话、相机权限申请通过", Toast.LENGTH_SHORT).show();
    }

    @PermissionCancel()
    public void permissionCancel(int requestCode) {
        Log.e("leo", "permissionCancel: " + requestCode);
    }

    @PermissionDenied()
    public void permissionDenied(int requestCode) {
        Log.e("leo", "permissionDenied: " + requestCode);
        switch (requestCode) {
            case 1111:
                showDialog("定位权限被禁止，需要手动去开启");
                break;
            case 1222:
                showDialog("电话和相机权限可能被禁止，需要手动去开启");
                break;
        }
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(PermissionActivity.this)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SettingUtil.go2Setting(PermissionActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
}