package com.kinglin.pet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 15:54
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;
}
