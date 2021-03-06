package com.bsoft.libcommon.model;


import androidx.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/8.
 */
public class LoginUserVo  extends BaseObservable implements Serializable {

/*    {
        "id": 32140,
            "userId": "e6f9b1db-5af0-4638-84d2-9121bd198a05",
            "password": "2524b8e3d3b65377a07a970965e5371f5addf75794ee3faaabe64451df89a3d3",
            "roleId": "patient",
            "tenantId": "hcn.zhongshan",
            "manageUnit": "hcn",
            "lastLoginTime": "2017-07-19 14:56:04",
            "lastIPAddress": "112.17.247.53",
            "lastUserAgent": "UNKNOWN,null,UNKNOWN",
            "displayName": "健康中山-创业软件股份有限公司-患者",
            "userName": "摸摸你",
            "roleName": "患者",
            "tenantName": "健康中山",
            "accessToken": "08f8e44e-48d3-4de7-a361-be4b9f2f7595"
    }*/


    public String id = "";
    public String loginName = "";
    public String userId = "";
    public String roleId = "";
    public String tenantId = "";
    public String manageUnit = "";
    public String userName = "";

    //0728
    public String password = "";
    public String lastLoginTime = "";
    public String lastIPAddress = "";
    public String lastUserAgent = "";
    public String displayName = "";
    public String roleName = "";
    public String tenantName = "";
    public String accessToken = "";


    private LoginUserVo(){
        throw new UnsupportedOperationException("Do not instantiate");
    }

    public static LoginUserVo getInstance(){
        return LoginUserHolder.INSTANCE;
    }

    private static class LoginUserHolder{
        private static  final LoginUserVo INSTANCE =  new LoginUserVo();
    }

}
