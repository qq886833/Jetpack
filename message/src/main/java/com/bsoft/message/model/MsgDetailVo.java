package com.bsoft.message.model;


import com.bsoft.libcommon.model.base.BaseVo;

/**
 * Created by Administrator on 2016/12/30.
 */
public class MsgDetailVo extends BaseVo {

    public int notificationId;
    public String userId;
    public String roleId;
    public String title;
    public String content;
    public String extras;
    public String notificationType;
    public String businessType;//大类型
    public Long sendTime;
    public String readFlag;
    public String linkType;
    public String notificationCode;//小类型
    public String operationable;
    public String productCode;
    public String status;
    public String tenantId;
    public String sendStatus;

    @Override
    public boolean equals(Object obj) {
        MsgDetailVo vo = (MsgDetailVo) obj;
        return this.notificationId == vo.notificationId;
    }
}
