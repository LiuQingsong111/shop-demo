package com.shop.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Slf4j
public class JwtUtils {

    private JwtUtils() {
    }

    /**
     * 密钥（生产环境应从配置文件读取）
     */
    private static final String SECRET = "shop-demo-secret-key-must-be-at-least-256-bits-long";

    /**
     * 过期时间（毫秒）：7天
     */
    private static final long EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 获取密钥
     */
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成Token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return token
     */
    public static String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        return generateToken(claims);
    }

    /**
     * 生成Token
     *
     * @param claims 自定义声明
     * @return token
     */
    public static String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 解析Token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.warn("Token解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("userId", Long.class);
    }

    /**
     * 获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("username", String.class);
    }

    /**
     * 验证Token是否有效
     *
     * @param token token
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return false;
        }
        return !claims.getExpiration().before(new Date());
    }
}
