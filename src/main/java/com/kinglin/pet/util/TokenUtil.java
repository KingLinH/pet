package com.kinglin.pet.util;

import io.jsonwebtoken.Jwts;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author huangjl
 * @description MD5加密
 * @since 2024-01-08 16:42
 */
public class TokenUtil {

    private static final Object USER_CACHE_FLAG = null;
    private static final Object SIGNATURE_ALGORITHM = "";

    public String generateLoginToken(String id, String accessToken) {
        String uuid = null;
        // 如果传入的accessToken不为空，获取其中的issuer，使用issuer生产新的token串，起到更新token的作用
        if (StringUtils.isNotBlank(accessToken)) {
            uuid = getIssuerFromToken(accessToken);
        }
        if (StringUtils.isBlank(uuid)) {
            uuid = USER_CACHE_FLAG.TOKEN + UUID.randomUUID().toString().replace("-", "");
        }

        String tokenSecret = getTokenSecret();
        String token = Jwts.builder().setId(id).setIssuer(uuid).setSubject("")
                .setAudience("").setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(null))
                .signWith(SIGNATURE_ALGORITHM, tokenSecret).compact();
    }

    private Date generateExpirationDate(String codeExpireTime) {
        return new Date();
    }

    private String getTokenSecret() {
        return "";
    }

    private String getIssuerFromToken(String accessToken) {
        return "";
    }

    public boolean verifyToken(String token) {
        return false;
    }

    public String getIdFromToken() {
        return "";
    }
}
