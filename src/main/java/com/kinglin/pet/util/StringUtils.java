package com.kinglin.pet.util;

/**
 * @author huangjl
 * @description 字符串工具类
 * @since 2024-01-08 16:46
 */
public class StringUtils {

    /**
     * 校验是否为null或者空（不包含空格）
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验是否不为null或者不为空（不包含空格）
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 校验是否为null（包含空格）
     */
    public static boolean isEmpty(final CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * 校验是否不为null（包含空格）
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

}
