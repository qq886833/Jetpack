package com.bsoft.libcommon.utils.location;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.bsoft.libbasic.utils.AppUtil;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libbasic.widget.dialog.BaseDialog;
import com.bsoft.libbasic.widget.dialog.CommonDialog;
import com.bsoft.libbasic.widget.dialog.ViewConvertListener;
import com.bsoft.libbasic.widget.dialog.ViewHolder;
import com.bsoft.libcommon.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LocationUtil {

    public static void showLocationDialog(final FragmentActivity activity, final LocationConfig config) {
        if (config == null) {
            return;
        }

        CommonDialog.newInstance()
                .setLayoutId(R.layout.common_dialog_location)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseDialog dialog) {
                        holder.setOnClickListener(R.id.llBaidu, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToBaiduMap(activity, config);
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.llBaidu, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToGaodeMap(activity, config);
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .show(activity.getSupportFragmentManager());

    }

    /**
     * 跳转百度地图
     */
    public static void goToBaiduMap(Context context, LocationConfig config) {
        if (config == null) {

            return;
        }
        if (!AppUtil.isInstalled("com.baidu.BaiduMap")) {
            ToastUtil.toast(R.string.common_no_baidu);
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("baidumap://map/marker?location="
                    + config.latitude + ","
                    + config.longitude
                    + "&title=" + URLEncoder.encode(config.name, "utf-8")
                    + "&coord_type=" + "gcj02"));
            context.startActivity(intent); // 启动调用
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转高德地图
     */
    public static void goToGaodeMap(Context context, LocationConfig config) {
        if (config == null) {

            return;
        }
        if (!AppUtil.isInstalled("com.autonavi.minimap")) {
            ToastUtil.toast(R.string.common_no_gaode);
            return;
        }
        try {
            StringBuffer stringBuffer = null;
            stringBuffer = new StringBuffer("androidamap://viewMap?sourceApplication=")
                    .append(URLEncoder.encode(AppUtil.getAppName(), "utf-8"));
            stringBuffer.append("&poiname=").append(URLEncoder.encode(config.name, "utf-8"))
                    .append("&lat=").append(config.latitude)
                    .append("&lon=").append(config.longitude)
                    .append("&dev=").append(0);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.autonavi.minimap");
            context.startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
