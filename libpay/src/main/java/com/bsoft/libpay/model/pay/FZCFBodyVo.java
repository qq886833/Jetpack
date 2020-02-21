package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/11/1
 * Description:图文咨询
 */
/*
"""paymentOrderId": "",
		"identificationNumbers": ["1-5083"],
		"invoiceNumber": "1c4f2ee4e613429eae4cfb89bcd28490"//发票号码
	},


*/

public class FZCFBodyVo extends PayBodyVo {
    public String paymentOrderId;
    public String identificationNumbers;
    public String invoiceNumber;

    public FZCFBodyVo() {
        super(BusType.FZCF);
    }
}
