package com.kinglin.pet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class TokenUtil {

    @Autowired
    private RedisUtil redisUtil;

    public TokenUtil() {

    }

    /**
     * token生成的密钥
     */
    public static final String TOKEN_SECRET = "TOKEN_SECRET@2019010";

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 缓存标志定义
     */
    public interface USER_CACHE_FLAG {
        int USERINFO_EXPIRE_TIME = 3600;//秒，用户信息缓存时间1小时
        int USER_CHECK_CODE_EXPIRE_TIME = 600;//秒，验证码有效时间10分钟
        int CACHE_EXPIRE_TIME = 24 * 60 * 60;//秒，缓存有效时间24小时
        String TOKEN = "ETW_NET:TOKEN_";//TOKEN秘钥
    }

    /**
     * token有效期（秒） 12小时失效
     */
    public static final int TOKEN_EXPIRES_TIME = 60 * 60 * 12;

    /**
     * 获取Claims信息
     * @param token
     * @return
     */
    public static Claims getAllClaimsFromToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(getTokenSecret()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException eje) {
            claims = null;
            log.error("获取token信息异常，jwt已过期");
        } catch (Exception e) {
            claims = null;
            log.error("获取token信息失败", e);
        }
        return claims;
    }

    /**
     * 获取token密钥
     * @return
     */
    private static String getTokenSecret() {
        String tokenSecret = null;
        if (StringUtils.isEmpty(tokenSecret)) {
            tokenSecret = TOKEN_SECRET;
        }
        return tokenSecret;
    }

    /**
     * 更新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (null == claims) {
            return null;
        }
        claims.setIssuedAt(new Date());
        String refreshedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(null))
                .signWith(SIGNATURE_ALGORITHM, getTokenSecret()).compact();
        try {
            //CacheServiceFactory.getService().setex(claims.getIssuer(), token, getExpiredInSecond());
            redisUtil.setExValue(claims.getIssuer(), token, getExpiredInSecond());
        } catch (Exception e) {
            log.error("存储redis数据异常", e);
        }
        return refreshedToken;
    }

    /**
     * 生成全局token
     * @return
     */
    public String generateToken() {
        String uuid = USER_CACHE_FLAG.TOKEN + UUID.randomUUID().toString()
                .replace("-", "");
        String tokenSecret = getTokenSecret();
        String token = Jwts.builder().setId("").setIssuer(uuid).setSubject("")
                .setAudience("").setIssuedAt(new Date()).setExpiration(generateExpirationDate(null))
                .signWith(SIGNATURE_ALGORITHM, tokenSecret).compact();
        //缓存token信息
        try {
            redisUtil.setExValue(uuid, token, getExpiredInSecond());
        } catch (Exception e) {
            log.error("存储redis数据异常", e);
        }
        return token;
    }

    private static int getExpiredInSecond() {
        return TOKEN_EXPIRES_TIME;
    }

    private static Date generateExpirationDate(Integer codeExpireTime) {
        if (null == codeExpireTime) {
            codeExpireTime = getExpiredIn();
        }
        return new Date((new Date()).getTime() + codeExpireTime);
    }

    private static int getExpiredIn() {
        return TOKEN_EXPIRES_TIME * 1000;
    }

    /**
     * 删除token
     * @param token
     */
    public void deleteToken(String token) {
        String uuid;
        if (StringUtils.isNotEmpty(token)) {
            uuid = getIssuerFromToken(token);
            try {
                //CacheServiceFactory.getService().decr(uuid);
                redisUtil.delValue(uuid);
            } catch (Exception e) {
                log.error("存储redis数据异常", e);
            }
        }
    }

    public String getIdFromToken(String token) {
        String id = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            if (null  != claims) {
                id = claims.getId();
            }
        } catch (Exception e) {
            log.error("从token里获取不到USER_ID", e);
        }
        return id;
    }

    /**
     * 获取发行人，对应UUID
     * @param token
     * @return
     */
    public static String getIssuerFromToken(String token) {
        String issuer = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            if (null != claims) {
                issuer = claims.getIssuer();
            }
        } catch (Exception e) {
            issuer = null;
            log.error("从token里获取不到UUID", e);
        }
        return issuer;
    }

    /**
     * 获取token主题
     * @param token
     * @return
     */
    public static String getSubjectFromToken(String token) {
        String subject;
        try {
            Claims claims = getAllClaimsFromToken(token);
            subject = claims.getSubject();
        } catch (Exception e) {
            subject = null;
            log.error("从token里获取不到主题", e);
        }
        return subject;
    }

    /**
     * 获取开始时间
     * @param token
     * @return
     */
    public static Date getIssuedDateFromToken(String token) {
        Date issueAt;
        try {
            Claims claims = getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
            log.error("从token里获取不到开始时间", e);
        }
        return issueAt;
    }

    /**
     * 获取到期时间
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
            log.error("从token里获取不到到期时间", e);
        }
        return expiration;
    }

    /**
     * 获取接收人
     * @param token
     * @return
     */
    public static String getAudienceFromToken(String token) {
        String audience;
        try {
            Claims claims = getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
            log.error("从token里获取不到接收人", e);
        }
        return audience;
    }

    /**
     * 登录用户生成token
     * @param userId
     * @param accessToken
     * @return
     */
    public String generateLoginToken(String userId, String accessToken) {
        String uuid = null;
        //如果传入的accessToken不为空，获取accessToken中的issuer，使用issuer生成新的token串，起到更新token的作用
        if (StringUtils.isNotEmpty(accessToken)) {
            uuid = getIssuerFromToken(accessToken);
        }
        //如果传入的accessToken为空，新生成一个issuer
        if (StringUtils.isEmpty(uuid)) {
            uuid = USER_CACHE_FLAG.TOKEN + UUID.randomUUID().toString()
                    .replace("-", "");
        }
        String tokenSecret = getTokenSecret();
        String token = Jwts.builder().setId(userId).setIssuer(uuid).setSubject("")
                .setAudience("").setIssuedAt(new Date()).setExpiration(generateExpirationDate(null))
                .signWith(SIGNATURE_ALGORITHM, tokenSecret).compact();

        //缓存token信息，uuid为key，格式为FANTASY:TOKEN:UUID，token解析成功后，需要在redis中进行查询，查询失败需要禁止访问，重新登陆
        try {
            redisUtil.setExValue(uuid, token, getExpiredInSecond());
        } catch (Exception e) {
            log.error("存储redis数据异常", e);
        }
        return token;
    }

    /**
     * 在token里获取对应参数的值
     * @param param
     * @param token
     * @return
     */
    public static String getClaimFromToken(String token, String param) {
        Claims claims = getAllClaimsFromToken(token);
        if (null == claims) {
            return "";
        }
        if (claims.containsKey(param)) {
            return claims.get(param).toString();
        }
        return "";
    }

    /**
     * 校验传送来的token和缓存的token是否一致
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (null == claims) {
            return false;
        }
        String issuer = claims.getIssuer();
       /*System.out.println("当前请求的token == " + token);
        System.out.println("当前请求的issuer == " + issuer);
        System.out.println("当前请求的id == " + claims.getId());*/
        String cacheToken;
        try {
            cacheToken = String.valueOf(redisUtil.getValue(issuer));
            //System.out.println("issuer对应缓存的token == " + cacheToken);
            //校验通过后修改token超时时间
            redisUtil.setExpire(issuer, getExpiredInSecond());
        } catch (Exception e) {
            cacheToken = null;
            log.error("获取不到存储的token", e);
        }
        return Objects.equals(token, cacheToken);
    }

}
