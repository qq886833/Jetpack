package com.bsoft.pub.jetpack.jpush;


import com.bsoft.libcommon.model.base.BaseVo;

public class CustomMessage extends BaseVo {
    private String badge;
    private String content;
    private String shock;
    private String sound;
    private String title;
    private String type;

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShock() {
        return shock;
    }

    public void setShock(String shock) {
        this.shock = shock;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
