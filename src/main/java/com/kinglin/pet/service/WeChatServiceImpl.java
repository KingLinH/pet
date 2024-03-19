package com.kinglin.pet.service;

import com.kinglin.pet.model.dto.WeChatLoginDTO;

import java.util.Map;

/**
 * @author huangjl
 * @description 微信相关业务逻辑
 * @since 2023-05-23 16:59
 */
public class WeChatServiceImpl {

    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String AUTH_CODE= "authorization_code";

    public Map<String, Object> getUserInfo(WeChatLoginDTO weChatLoginDTO) {
        return null;
    }

}
