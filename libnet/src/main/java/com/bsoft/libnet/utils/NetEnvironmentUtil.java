package com.bsoft.libnet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.bsoft.libnet.base.INetworkInit;
import com.bsoft.libnet.constants.HttpConstants;
import com.bsoft.libnet.model.NetAddressVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by 83990 on 2018/2/8.
 */

public class NetEnvironmentUtil {
    private static String sCurrentNetworkEnvironment = "currentEnviroment";
    private static INetworkInit mINetworkInit;
    private static SharedPreferences sp ;
    /**
     * 初始化网络环境
     *
     * @param context
     * @return
     */
    public static boolean initConstans(Context context,INetworkInit iNetworkInit) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        sp = context.getSharedPreferences("netWork", Context.MODE_PRIVATE);
        mINetworkInit =iNetworkInit;
        NetAddressVo vo = getCurEnvironment(context);

        if (vo != null) {
            HttpConstants.versionCode =mINetworkInit.getAppVersionCode();
            HttpConstants.versionName =mINetworkInit.getAppVersionName();
            HttpConstants.environment =vo.getEnvironment();
            HttpConstants.httpApiUrl = vo.getHttpApiUrl();
            HttpConstants.httpDownloadUrl = vo.getHttpDownloadUrl();
            HttpConstants.httpImgUrl = vo.getHttpImgUrl();
            HttpConstants.isDebug = mINetworkInit.isDebug();
            return true;
        } else {
            return false;
        }
    }




    /**
     * 获取网络环境参数List
     *
     * @param context
     * @return
     */
    public static ArrayList<NetAddressVo> getNetEnvironments(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("netConfigs");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

            if (!TextUtils.isEmpty(stringBuffer)) {
                Gson gson = new Gson();
                ArrayList<NetAddressVo> netBeans = gson.fromJson(stringBuffer.toString()
                        , new TypeToken<ArrayList<NetAddressVo>>() {
                        }.getType());
                if (netBeans != null && !netBeans.isEmpty()) {

                    return netBeans;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前网络环境参数
     *
     * @param context
     * @return
     */
    public static NetAddressVo getCurEnvironment(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        if(!TextUtils.isEmpty(sp.getString(sCurrentNetworkEnvironment,""))){
            HttpConstants.environment = sp.getString(sCurrentNetworkEnvironment,"");
        }else {
            HttpConstants.environment = mINetworkInit.getEnvironment();
        }


        ArrayList<NetAddressVo> vos = getNetEnvironments(context);
        if (vos != null) {
            for (NetAddressVo vo : vos) {
                if (TextUtils.equals(vo.getEnvironment(), HttpConstants.environment)) {
                    return vo;
                }
            }
        }

        return null;
    }

    /**
     * 设置当前网络环境
     *
     * @param context
     * @param enviroment
     * @return
     */
    public static boolean setEnvironment(Context context, String enviroment) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }

        if (TextUtils.isEmpty(enviroment)) {
            return false;
        }

        ArrayList<NetAddressVo> vos = getNetEnvironments(context);
        if (vos != null) {
            for (NetAddressVo vo : vos) {
                if (TextUtils.equals(vo.getEnvironment(), enviroment)) {
                    //获取到edit对象
                    SharedPreferences.Editor edit = sp.edit();
                    //通过editor对象写入数据
                    edit.putString(sCurrentNetworkEnvironment,enviroment);
                    //提交数据存入到xml文件中
                    edit.commit();
                    return true;
                }
            }
        }

        return false;
    }



}
