package com.github.scipioutils.core;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Alan Scipio
 * create time: 2018/4/20
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断字符串是否不为空(空格不算)
     */
    public static boolean isNotNull(String s) {
        return (s != null) && (!"".equals(s));
    }

    /**
     * 判断字符串是否为空(空格不算)
     */
    public static boolean isNull(String s) {
        return (s == null) || ("".equals(s));
    }

    /**
     * 判断字符串是否不为空(空格不算)
     */
    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    /**
     * 判断字符串是否为空(空格不算)
     */
    public static boolean isBlank(String s) {
        int strLen;
        if (s == null || (strLen = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为整数
     */
    public static boolean isIntNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为英文字母
     */
    public static boolean isAlpha(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为英文字母或数字
     */
    public static boolean isAlphaNumeric(String str) {
        Pattern pattern = Pattern.compile("^[a-z0-9A-Z]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为http的url
     * <ul>
     *     <li>匹配诸如：http://regxlib.com | http://electronics.cnet.com/electronics/0-6342366-8-8994967-1.html</li>
     *     <li>不匹配：www.yahoo.com</li>
     * </ul>
     */
    public static boolean isHttpUrl(String s) {
        Pattern pattern = Pattern.compile("(http|ftp|https)://[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-.,@?^=%&:/~+#]*[\\w\\-@?^=%&/~+#])?");
        return pattern.matcher(s).matches();
    }

    /**
     * 去除首尾空格，兼容null入参
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 比较字符串是否相等，支持null入参
     */
    public static boolean equals(final String cs1, final String cs2) {
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equals(cs2);
    }

    /**
     * 把byte转为bit字符串
     */
    public static String byteToBitStr(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1)
                + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1)
                + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1)
                + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1)
                + (byte) ((b) & 0x1);
    }

    /**
     * 把bit字符串转为byte
     */
    public static byte bitStrToByte(String bit) {
        int re, len;
        if (null == bit)
            return 0;
        len = bit.length();
        if (len != 4 && len != 8)
            return 0;
        if (len == 8) // 8 bit处理
        {
            if (bit.charAt(0) == '0') // 正数
                re = Integer.parseInt(bit, 2);//输出2进制数在10进制下的结果
            else// 负数
                re = Integer.parseInt(bit, 2) - 256;
        } else //4 bit处理
        {
            re = Integer.parseInt(bit, 2);
        }
        return (byte) re;
    }

    /**
     * 字符串插入
     *
     * @param offset    插入位置的下标
     * @param oldStr    原来的字符串
     * @param insertStr 要插入的字符串
     * @return 插入过的字符串
     */
    public static String insert(int offset, String oldStr, String insertStr) {
        StringBuilder sb = new StringBuilder(oldStr);
        sb.insert(offset, insertStr);
        return sb.toString();
    }

    /**
     * 英文首字母转为大写
     */
    public String firstToUpper(String s) {
        if (isNull(s)) {
            return s;
        }
        char[] sArr = s.toCharArray();
        int ascii0 = sArr[0];
        if (ascii0 >= 97 && ascii0 <= 122) {
            sArr[0] -= 32;
        }
        return String.valueOf(sArr);
    }

    /**
     * 英文首字母转为小写
     */
    public String firstToLower(String s) {
        if (isNull(s)) {
            return s;
        }
        char[] sArr = s.toCharArray();
        int ascii0 = sArr[0];
        if (ascii0 >= 65 && ascii0 <= 90) {
            sArr[0] += 32;
        }
        return String.valueOf(sArr);
    }

}
