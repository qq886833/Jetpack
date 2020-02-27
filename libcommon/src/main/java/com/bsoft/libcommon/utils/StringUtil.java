package com.bsoft.libcommon.utils;

import android.content.Context;
import android.text.TextUtils;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.R;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static String PATTERN_LEFT = ".*([";
    private static String PATTERN_RIGHT = "])";
    private static String PATTERN_FINAL = ".*";

    // private static String[] phones = { "130", "131", "132", "133", "134",
    // "135", "136", "137", "138", "139", "150", "151", "152", "153",
    // "154", "155", "156", "157", "158", "159", "180", "181", "182",
    // "183", "184", "185", "186", "187", "188", "189" };

    // 隐藏手机号中间4位
    public static String getPhoneStr(String phoneNo) {
        if (phoneNo.length() >= 11) {
            String str1 = phoneNo.substring(0, 3);
            String str2 = phoneNo.substring(phoneNo.length() - 4);
            return str1 + "****" + str2;
        }
        return phoneNo;
    }

    // 隐藏后4位
    public static String getIdCardStr(String idcard) {
        if (idcard == null)
            return "";
        if (idcard.length() > 6) {
            String s = idcard.substring(0, idcard.length() - 4);
            return s + "****";
        }
        return idcard;
    }
    // 只显示后4位
    public static String getCardStr(String card) {
        if (card == null)
            return "";
        if (card.length() > 6) {
            String s = card.substring(card.length() - 4);
            String a = card.substring(0, card.length()-4);
            for (int i = 0;i < a.length() ; i++){
                a = a.replace(String.valueOf(a.charAt(i)),"*");
            }
            return a+s;
        }
        return card;
    }

    public static String hideStr(String txt, int hideLength, int hideIndex){
        return "";
    }

    public static String replaceAll(String s, String find, String replace) {
        StringBuffer buffer = new StringBuffer(s);
        int pos = buffer.toString().indexOf(find);
        int len = find.length();
        while (pos > -1) {
            buffer.replace(pos, pos + len, replace);
            pos = buffer.toString().indexOf(find);
        }
        return buffer.toString();
    }

    public static String getTextLimit(String s, int len) {
        if (isEmpty(s)) {
            return "";
        }
        if (s.length() <= len) {
            return s;
        } else {
            return s.substring(0, len - 1) + "...";
        }

    }

    public static String getTextViewText(String s) {
        if (null == s) {
            return "";
        }
        return s;
    }

    /**
     * 判断字符串是否为null或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }

        return false;
    }

    public static String notNull(String str) {
        if (str == null || str.length() <= 0) {
            return "";
        }

        return str;
    }
    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }
    /**
     * @param str
     * @param def 默认值
     * @return
     */
    public static String notNull(String str, String def) {
        if (str == null || str.length() <= 0) {
            return def;
        }

        return str;
    }

    public static boolean isMobilPhoneNumber(String mobilPhoneNumber) {
        // if (mobilPhoneNumber.trim().length() != 11) {
        // return false;
        // }
        //
        // String phone = mobilPhoneNumber.substring(0, 3);
        // for (String str : phones) {
        // if (phone.equals(str)) {
        // return true;
        // }
        // }
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//		Matcher m = p.matcher(mobilPhoneNumber);
//		return m.matches();

        // return false;
        return mobilPhoneNumber.length() == 11;
    }

    /**
     * 判断字符串前后截掉空字符后,是否为null或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isTrimEmpty(String str) {
        if (str == null) {
            return true;
        }

        str = str.trim();
        return isEmpty(str);
    }

    /**
     * 判断是否是中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否含有中文字符
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (str == null) return false;

        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否符合复杂密码要求
     *
     * @param str
     * @return
     */
    public static boolean isPassWord(String str) {
        char[] pw = str.toCharArray();
        boolean isPassWord = false;
        for (int i = 1; i < pw.length; i++) {
            if (Math.abs(pw[i] - pw[i - 1]) > 1) {
                isPassWord = true;
                break;
            }
        }
        return isPassWord;
    }

    /**
     * 获取搜索字符的正则表达式
     *
     * @param searchText
     * @return
     */
    public static String getPatterString(String searchText) {
        if (searchText == null)
            return "";
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？] ";
        final int len = searchText.length();
        if (len > 0) {
            String pattern = "";
            for (int i = 0; i < len; ++i) {
                String subStr = searchText.substring(i, i + 1);
                if (regEx.contains(subStr)) {
                    pattern += PATTERN_LEFT + "\\" + subStr + PATTERN_RIGHT;
                } else {
                    pattern += PATTERN_LEFT + subStr + PATTERN_RIGHT;
                }
            }
            return pattern + PATTERN_FINAL;
        } else {
            return "";
        }
    }


    /**
     * 读取raw文件夹下的文件
     *
     * @param resourceId raw文件夹下的文件资源ID
     * @return 文件内容
     */
    public static String readFileFromRaw(Context context, int resourceId) {
        if (null == context || resourceId < 0) {
            return null;
        }

        String result = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            // 获取文件的字节数
            int length = inputStream.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            inputStream.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String text(String str) {
        if (str == null) return "";
        return str;
    }

    /**
     * @param value
     * @param num       小数位数
     * @param isPercent 是否是百分数
     * @return
     */
    public static String numberFormat(double value, int num, boolean isPercent) {
        if (isPercent) {
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2
            nt.setMaximumFractionDigits(num);
            return nt.format(value);
        } else {
            NumberFormat nf = NumberFormat.getNumberInstance();
            //double精度2
            nf.setMaximumFractionDigits(num);
            return nf.format(value);
        }
    }

    public static boolean isEmojiCharacter(CharSequence input) {
        if (TextUtils.isEmpty(input)) return false;
        for (int i = 0; i < input.length(); i++) {
            if (isEmojiCharacter(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    //是否是Emoji表情
    public static boolean isEmojiCharacter(char codePoint) {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        return !isScopeOf;
    }

    //去除字符串中的Emoji表情
    public static String removeEmoji(CharSequence source) {
        if (TextUtils.isEmpty(source)) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (isEmojiCharacter(c)) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 匹配获取所有匹配结果位置
     *
     * @param content
     * @param key
     * @param isCase  忽略大小写
     * @return
     */
    public static ArrayList<Integer> matchIndexs(String content, String key, boolean isCase) {
        ArrayList<Integer> list = new ArrayList<>();
        if (content == null || key == null) return list;
        String wordReg = isCase ? "(?i)" + key : key;//用(?i)来忽略大小写
        Matcher matcher = Pattern.compile(wordReg).matcher(content);
        while (matcher.find()) {
            list.add(matcher.start());
        }
        return list;
    }

    /**
     * str 为空返回"空"
     *
     * @param str
     * @return
     */
    public static String getStrNoEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return ContextProvider.get().getApplication().getString(R.string.common_empty);
        } else {
            return str;
        }
    }

    /**
     * 把单个英文字母或者字符串转换成数字ASCII码
     *
     * @param input
     * @return
     */
    public static int character2ASCII(String input) {
        if (TextUtils.isEmpty(input)) {
            return -1;
        }
        char[] temp = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char each : temp) {
            builder.append((int) each);
        }
        String result = builder.toString();
        return Integer.parseInt(result);
    }
}
