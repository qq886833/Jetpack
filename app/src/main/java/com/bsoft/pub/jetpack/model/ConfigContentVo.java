package com.bsoft.pub.jetpack.model;


import com.bsoft.libnet.model.BaseVo;

public class ConfigContentVo extends BaseVo {


    /**
     * id : 1
     * moduleId : 0101
     * moduleName : 预约挂号
     * copywriterCode : 0101-01
     * copywriterTitle : 预约挂号预约规则
     * level : 2
     * objectId : hcn.dongtai
     * objectName : 健康城市
     * content : 您可预约当天及未来一周的专家号源及普通号源
     * pictureId : 1
     * type : 2
     */

    private int id;
    private String moduleId;
    private String moduleName;
    private String copywriterCode;
    private String copywriterTitle;
    private String level;
    private String objectId;
    private String objectName;
    private String content;
    private int pictureId;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCopywriterCode() {
        return copywriterCode;
    }

    public void setCopywriterCode(String copywriterCode) {
        this.copywriterCode = copywriterCode;
    }

    public String getCopywriterTitle() {
        return copywriterTitle;
    }

    public void setCopywriterTitle(String copywriterTitle) {
        this.copywriterTitle = copywriterTitle;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
