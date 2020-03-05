package com.bsoft.libcommon.localdata;

import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.utils.proxsp.AppConfigHandler;
import com.bsoft.libcommon.utils.proxsp.UserListConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class LocalDataUtil {

    public static volatile LocalDataUtil localDataUtil;


    private LocalDataUtil() {
    }

    public static LocalDataUtil getInstance(){
        if(localDataUtil==null){
            synchronized (LocalDataUtil.class){
                if(localDataUtil==null){
                    localDataUtil= new LocalDataUtil();
                }
            }
        }
        return localDataUtil;
    }


    /*
    * 登录用户列表
    */
    public void saveUserList(String data) {
        UserListConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(),UserListConfig.class);
        ArrayList<String> userList = getUserList();
        if (userList == null) userList = new ArrayList<>();
        if (!userList.contains(data)) {
            userList.add(0, data);

       //     AccountSharpref.getInstance().setLoginUserList(new Gson().toJson(userList));
            config.setUserList(new Gson().toJson(userList));
        }
    }

    public void delUserList(String data) {
        UserListConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(),UserListConfig.class);
        ArrayList<String> userList = getUserList();
        if (userList == null) return;
        userList.remove(data);
        //AccountSharpref.getInstance().setLoginUserList(new Gson().toJson(userList));
        config.setUserList(new Gson().toJson(userList));
    }
    public ArrayList<String> getUserList() {
        UserListConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(), UserListConfig.class);
        ArrayList<String> list = new Gson().fromJson(config.getUserList(), new TypeToken<ArrayList<String>>() {
        }.getType());
        if (list != null) {
            return list;
        }
        return null;
    }






   /* public void saveLoadingAd(BannerVo data) {
        AccountSharpref.getInstance().setLoadingAd(new Gson().toJson(data));
    }

    public BannerVo getLoadingAd() {
        String data = AccountSharpref.getInstance().getLoadingAd();
        return new Gson().fromJson(data, BannerVo.class);
    }*/

/*
    public void setDocInfo(DocInfoVo userInfo) {
        getIndexVo();
        if (indexVo != null) {
            indexVo.doctor = userInfo;
        }

        saveIndexVo(indexVo);
    }

    public DocInfoVo getDocInfo() {
        getIndexVo();
        if (indexVo != null) {
            return indexVo.doctor;
        }
        return null;
    }


    *//**
     * 登录用户列表
     *//*
    public void saveUserList(String data) {
        ArrayList<String> userList = getUserList();
        if (userList == null) userList = new ArrayList<>();
        if (!userList.contains(data)) {
            userList.add(0, data);
//            saveDatatoLocal(USER_LIST, userList);
            AccountSharpref.getInstance().setLoginUserList(new Gson().toJson(userList));
        }
    }

    public void delUserList(String data) {
        ArrayList<String> userList = getUserList();
        if (userList == null) return;
        userList.remove(data);
        AccountSharpref.getInstance().setLoginUserList(new Gson().toJson(userList));
    }

    public ArrayList<String> getUserList() {
        ArrayList<String> list = new Gson().fromJson(AccountSharpref.getInstance().getLoginUserList(), new TypeToken<ArrayList<String>>() {
        }.getType());
        if (list != null) {
            return list;
        }
        return null;
    }

    public void saveUserPhone(String data) {
        AccountSharpref.getInstance().setUserPhone(data);
    }

    public String getUserPhone() {
        return AccountSharpref.getInstance().getUserPhone();
    }

    public void saveAccessToken(String data) {
        AccountSharpref.getInstance().setAccessToken(data);
    }

    public String getAccessToken() {
        return AccountSharpref.getInstance().getAccessToken();
    }

    public void saveLoginUser(LoginUser data) {
        loginUser = data;
        AccountSharpref.getInstance().setLoginUser(new Gson().toJson(data));
    }

    public LoginUser getLoginUser() {
        if (loginUser == null) {
            String data = AccountSharpref.getInstance().getLoginUser();
            if (!TextUtils.isEmpty(data))
                loginUser = new Gson().fromJson(data, LoginUser.class);
        }
        return loginUser;
    }

    public void saveIndexVo(IndexVo data) {
        indexVo = data;
        AccountSharpref.getInstance().setIndexVo(new Gson().toJson(data));
    }

    public IndexVo getIndexVo() {
        if (indexVo == null) {
            String data = AccountSharpref.getInstance().getIndexVo();
            if (!TextUtils.isEmpty(data))
                indexVo = new Gson().fromJson(data, IndexVo.class);
        }
        return indexVo;
    }



    public void saveBannerList(ArrayList<BannerVo> data) {
        bannerList = data;
        AccountSharpref.getInstance().setBanner(new Gson().toJson(data));
    }

    public ArrayList<BannerVo> getBannerList() {
        if (bannerList == null) {
            String data = AccountSharpref.getInstance().getBanner();
            if (!TextUtils.isEmpty(data))
                bannerList = new Gson().fromJson(data, new TypeToken<ArrayList<BannerVo>>() {
                }.getType());
        }
        return bannerList;
    }

    public void setLoginState(boolean b) {
        AccountSharpref.getInstance().setLoginState(b);
    }

    public boolean getLoginState() {
        return AccountSharpref.getInstance().getLoginState();
    }*/
}
