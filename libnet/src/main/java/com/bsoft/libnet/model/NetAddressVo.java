package com.bsoft.libnet.model;


/**
 * Created by 83990 on 2018/2/7.
 */

public class NetAddressVo  extends BaseVo {
    private String environment;
    private String environmentText;
    private String HttpApiUrl;
    private String HttpDownloadUrl;
    private String HttpImgUrl;
    private String HttpHealthInfoUrl;

    public String getHttpHealthInfoUrl() {
        return HttpHealthInfoUrl;
    }

    public void setHttpHealthInfoUrl(String httpHealthInfoUrl) {
        HttpHealthInfoUrl = httpHealthInfoUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getHttpApiUrl() {
        return HttpApiUrl;
    }

    public String getHttpDownloadUrl() {
        return HttpDownloadUrl;
    }

    public String getHttpImgUrl() {
        return HttpImgUrl;
    }

    public String getEnvironmentText() {
        return environmentText;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setEnvironmentText(String environmentText) {
        this.environmentText = environmentText;
    }

    public void setHttpApiUrl(String httpApiUrl) {
        HttpApiUrl = httpApiUrl;
    }

    public void setHttpDownloadUrl(String httpDownloadUrl) {
        HttpDownloadUrl = httpDownloadUrl;
    }

    public void setHttpImgUrl(String httpImgUrl) {
        HttpImgUrl = httpImgUrl;
    }
}
