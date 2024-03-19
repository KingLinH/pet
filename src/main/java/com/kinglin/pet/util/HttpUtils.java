package com.kinglin.pet.util;

import cn.hutool.http.HttpUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author huangjl
 * @description http请求工具类
 * @since 2023-05-15 9:37
 */
public class HttpUtils {

    private static final int TIMEOUT = 1000;

    private static final int TIMEOUT_MSEC = 5 * 1000;

    public static String doPost(String url, Map<String, Object> params) {

        if (Objects.nonNull(url) && Objects.nonNull(params)) {
            return HttpUtil.post(url, params);
        }
        return null;

    }

    public static String doGet(String url, Map<String, Object> params) {
        if (Objects.nonNull(url) && Objects.nonNull(params)) {
            return HttpUtil.get(url, params);
        }
        return null;
    }

}
