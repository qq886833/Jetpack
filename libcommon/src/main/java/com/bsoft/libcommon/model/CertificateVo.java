package com.bsoft.libcommon.model;

import android.text.TextUtils;

import java.io.Serializable;


/**
 * Created by Administrator on 2016/12/7.
 */
public class CertificateVo implements Serializable {
    public String certificateNo;
    public String certificateType;
    public String source;

    public String certificateTypeText = ""; //证件类别名称
    public String sourceText = "";          //发证机关名称

    @Override
    public boolean equals(Object obj) {
        CertificateVo vo = (CertificateVo)obj;
        return TextUtils.equals(certificateNo, vo.certificateNo)
                && TextUtils.equals(certificateType, vo.certificateType)
                && TextUtils.equals(source, vo.source)
                ;
    }
}
