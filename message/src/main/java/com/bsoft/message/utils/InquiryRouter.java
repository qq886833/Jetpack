package com.bsoft.message.utils;

public class InquiryRouter {
/*

    public static void gotoPay(Context context, String extra) {
        InquiryVo vo = DataConverUtil.convertAtoB(extra, InquiryVo.class);
        if (vo != null) {
            if (TextUtils.equals(ConsultTypeDic.BUZID_PIC, vo.goodsCategory)) {
                ArouterGroup.webPayStart(vo.orderDetailId, ConsultTypeDic.BUZID_PIC);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_VIDEO, vo.goodsCategory)) {
                ArouterGroup.webPayStart(vo.orderDetailId, ConsultTypeDic.BUZID_VIDEO);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_PEIYAO, vo.goodsCategory)) {
                SubVisitWaitPayActivity.actStart(context,vo.revisitId);
            }
        }
    }

    public static void gotoNoPay(Context context, String extra) {
        InquiryVo vo = DataConverUtil.convertAtoB(extra, InquiryVo.class);
        if (vo != null) {
            if (TextUtils.equals(ConsultTypeDic.BUZID_PIC, vo.goodsCategory)) {
                ArouterGroup.webNoPayOrderDetailStart(vo.orderDetailId);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_VIDEO, vo.goodsCategory)) {
                ArouterGroup.webNoPayOrderDetailStart(vo.orderDetailId);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_PEIYAO, vo.goodsCategory)) {
                DosageActivity.start(context, vo.revisitId);
            }
        }
    }

    public static void gotoVideo(Context context, String extra) {
        OrderDetailVo vo = DataConverUtil.convertAtoB(extra, OrderDetailVo.class);
        VideoMessageActivity.start(context, vo.orderDetailId);
    }

    public static void gotoRevisit(Context context, String extra) {
        InquiryVo vo = DataConverUtil.convertAtoB(extra, InquiryVo.class);
        DosageActivity.start(context, vo.revisitId);
    }

    public static void gotoInquiryCancel(Context context, String extra) {
        InquiryVo vo = DataConverUtil.convertAtoB(extra, InquiryVo.class);
        if (vo != null) {
            if (TextUtils.equals(ConsultTypeDic.BUZID_PIC, vo.goodsCategory)) {
                MessageCancelActivity.start(context, vo.orderDetailId);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_VIDEO, vo.goodsCategory)) {
                MessageCancelActivity.start(context, vo.orderDetailId);
            } else if (TextUtils.equals(ConsultTypeDic.BUZID_PEIYAO, vo.goodsCategory)) {
                DosageActivity.start(context, vo.revisitId);
            }
        }
    }

    public static void gotoMedicalOrderDetail(String extra) {
        InquiryVo vo = DataConverUtil.convertAtoB(extra, InquiryVo.class);
        ArouterGroup.medicalOrderDetail(vo.orderNo);
    }
*/

}
