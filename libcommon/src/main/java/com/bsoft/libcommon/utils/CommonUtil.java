package com.bsoft.libcommon.utils;

import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 其他的一些方法
 * Created by Administrator on 2016/6/17.
 */
public class CommonUtil {

    // 隐藏手机号中间4位
    public static String getPhoneStr(String idcard) {
        if (idcard == null) {
            return "";
        }
        if (idcard.length() >= 11) {
            String str1 = idcard.substring(0, 3);
            String str2 = idcard.substring(idcard.length() - 4);
            return str1 + "****" + str2;
        }
        return idcard;
    }

    // 隐藏后4位
    public static String getCardStr(String idcard) {
        if (idcard == null) {
            return "";
        }
        if (idcard.length() > 6) {
            String s = idcard.substring(0, idcard.length() - 4);
            return s + "****";
        }
        return idcard;
    }

    public static String parseDouble2String(double num) {
        return String.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(num)));
    }


    /**
     * @param idcard
     * @return
     *
     */
    @Deprecated
    public static String getIdcardStr(String idcard) {
        if (idcard == null) return "";
        if (idcard.length() >= 12) {
            String str1 = idcard.substring(0, 4);
            String str2 = idcard.substring(4, 8);
            String str3 = idcard.substring(8, 12);
            String str4 = idcard.substring(12, idcard.length());
            return str1 + " " + str2 + " " + str3 + " " + str4;
        }
        return idcard;
    }

    


    public static String ellipsizeString(final String txt, final TextView txtView) {
        if (TextUtils.isEmpty(txt) || txtView == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT < 14)  // 据初步证实API VISION 14以上的会出现这种情况，这个判断也可以去掉
            return txt;

        final String ellip = "...";
        String str = txt;

        TextPaint txtpaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        txtpaint.setTextSize(txtView.getTextSize());
        float txtWidth = txtpaint.measureText(str);

        // float scale  = ServiceBindActivity.curContext.getResources().getDisplayMetrics().scaledDensity;
        float PLpix = txtView.getPaddingLeft();// * scale + 0.5f;
        float PRpix = txtView.getPaddingRight();// * scale + 0.5f;
        float MLpix = 0f;
        float MRpix = 0f;

        int lines = txtView.getMaxLines();

        float txtViewWidth = txtView.getResources()
                .getDisplayMetrics().widthPixels - (MLpix + MRpix) - (PLpix + PRpix);

        if (txtWidth <= txtViewWidth * lines)
            return str;

        float onetextwidth = txtWidth / str.length();
        BigDecimal bd = new BigDecimal((txtViewWidth - txtpaint.measureText(ellip)) / onetextwidth).setScale(0, BigDecimal.ROUND_HALF_UP);
        return str.substring(0, bd.intValue() * lines) + ellip;
    }

    /**
     * 解析二维码
     *
     * @param barcode
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T transformZcode(String barcode, Class<T> clazz) {
        String flag = "data=";
        if (barcode == null || !barcode.contains(flag)) return null;
        String data = barcode.substring(barcode.indexOf(flag) + flag.length(), barcode.length());
        if (TextUtils.isEmpty(data)) return null;
        String json = null;
        try {
            json = new String(Base64.decode(data.getBytes("UTF-8"), Base64.NO_WRAP), "UTF-8");
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(json)) return null;
        T vo = JSON.parseObject(json, clazz);
        if (vo == null) return null;

        return vo;
    }


    /**
     * 复制一个list，复制的list不会影响原来的list的引用
     * @param src
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        }catch (IOException e) {
            e.printStackTrace();
            return  null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 密码复杂性验证
     * @param pwd
     * @return
     */
    public static boolean validatePwd(String pwd){
        if(pwd == null) {
            return false;
        }
        if(pwd.matches("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$")){//6-20数字+字母
            return true;
        }
//        if(pwd.matches("^[a-zA-Z0-9]{6,20}$")){//6-20数字或字母
//            return true;
//        }
        
        return false;
    }

    /**
     * 
     * @param phone
     * @return
     */
    public static boolean validatePhone(String phone){
        if(phone == null || phone.length() != 11) {
            return false;
        }
        
        return true;
    }

}
