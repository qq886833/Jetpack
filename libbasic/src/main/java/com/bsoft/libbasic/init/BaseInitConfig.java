package com.bsoft.libbasic.init;

public class BaseInitConfig {
    private String environment;
    private int versionCode;
    private String versionName;
    private boolean debug;

    private String httpApiUrl;
    private String httpDownloadUrl;
    private String httpImgUrl;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getHttpApiUrl() {
        return httpApiUrl;
    }

    public void setHttpApiUrl(String httpApiUrl) {
        this.httpApiUrl = httpApiUrl;
    }

    public String getHttpDownloadUrl() {
        return httpDownloadUrl;
    }

    public void setHttpDownloadUrl(String httpDownloadUrl) {
        this.httpDownloadUrl = httpDownloadUrl;
    }

    public String getHttpImgUrl() {
        return httpImgUrl;
    }

    public void setHttpImgUrl(String httpImgUrl) {
        this.httpImgUrl = httpImgUrl;
    }
}
