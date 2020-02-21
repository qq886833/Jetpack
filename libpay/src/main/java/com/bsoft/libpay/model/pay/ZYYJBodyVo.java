package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * 住院金预缴
 */

public class ZYYJBodyVo extends PayBodyVo {
    public String patientCode;
    public String inHospitalRecordNumber;

    public ZYYJBodyVo() {
        super(BusType.ZYYJ);
    }

}
