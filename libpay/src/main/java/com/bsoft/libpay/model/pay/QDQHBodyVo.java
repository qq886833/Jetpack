package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/11/1
 * Description:图文咨询
 */
/*
"""hisOrderNumber": "295",//预约挂号标识(his返回的)，
		"outpatientType": "1",//1标识非云诊室预约(健康城市可不传，互联网医院必穿)，
        "paymentBudgetNumber": "1424",//预结算标识，
        " invoiceNumber ":"发票号码"
*/

public class QDQHBodyVo extends PayBodyVo {
    public String hisOrderNumber;
    public String outpatientType;
    public String paymentBudgetNumber;
    public String invoiceNumber;

    public QDQHBodyVo() {
        super(BusType.QDQH);
    }
}
