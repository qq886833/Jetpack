package com.bsoft.libcommon.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/21
 * describe :
 */
public class SpannableUtil {

    /**
     * 返回通用金额，如：¥89.50
     * "¥"   字体大小为：smallSize
     * "89." 字体大小为：bigSize
     * "50"  字体大小为：smallSize
     */
    public static Spannable getRMBSpannable(double cost, int smallSize, int bigSize) {
        String costStr = formatToRMBStr(cost);
        Spannable accountSpan = new SpannableStringBuilder(costStr);
        accountSpan.setSpan(new AbsoluteSizeSpan(smallSize, true), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        accountSpan.setSpan(new AbsoluteSizeSpan(bigSize, true), 1, costStr.indexOf("."),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        accountSpan.setSpan(new AbsoluteSizeSpan(smallSize, true), costStr.indexOf(".") + 1,
                costStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return accountSpan;
    }

    /**
     * 将字符串str的第start到end之间的字符修改为color的颜色
     */
    public static SpannableStringBuilder getColorSpannableStr(String str, int start, int end, int color) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }

    /**
     * 将字符串str的第start到end之间的字符修改为color的颜色
     */
    public static SpannableStringBuilder getColorAndSizeSpannableStr(String str, int start, int end, int color, int size) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new AbsoluteSizeSpan(size, true), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }


    /**
     * 89.5返回：+89.50
     * -89.5返回：-89.50
     * "+89." 字体大小为：bigSize
     * "50"  字体大小为：smallSize
     */
    public static Spannable getPlusMinusSpannable(double account, int smallSize, int bigSize) {
        String accountStr = getFormatDecimalStr(account);
        if (account >= 0) {
            accountStr = "+" + accountStr;
        }
        Spannable accountSpan = new SpannableStringBuilder(accountStr);
        accountSpan.setSpan(new AbsoluteSizeSpan(bigSize, true), 0, accountStr.indexOf("."),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        accountSpan.setSpan(new AbsoluteSizeSpan(smallSize, true), accountStr.indexOf(".") + 1,
                accountStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return accountSpan;
    }

    /**
     * 将入参d转为保留2位小数的str
     */
    public static String getFormatDecimalStr(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(d);
    }

    /**
     * 将入参cost转为保留两位小数的人民币符号开头的str
     * 如入参3.2，返回¥3.20
     */
    public static String formatToRMBStr(double cost) {
        return "¥ " + getFormatDecimalStr(cost);
    }

}
