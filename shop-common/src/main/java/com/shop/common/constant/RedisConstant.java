package com.shop.common.constant;

/**
 * Redis Key 常量
 *
 * @author shop-demo
 * @since 1.0.0
 */
public class RedisConstant {

    private RedisConstant() {
    }

    /**
     * 项目前缀
     */
    public static final String PROJECT_PREFIX = "shop:";

    /**
     * 用户Token
     */
    public static final String USER_TOKEN = PROJECT_PREFIX + "user:token:";

    /**
     * 用户信息缓存
     */
    public static final String USER_INFO = PROJECT_PREFIX + "user:info:";

    /**
     * 商品信息缓存
     */
    public static final String PRODUCT_INFO = PROJECT_PREFIX + "product:info:";

    /**
     * 商品库存
     */
    public static final String PRODUCT_STOCK = PROJECT_PREFIX + "product:stock:";

    /**
     * 购物车
     */
    public static final String CART = PROJECT_PREFIX + "cart:";

    /**
     * 验证码
     */
    public static final String CAPTCHA = PROJECT_PREFIX + "captcha:";

    /**
     * 短信验证码
     */
    public static final String SMS_CODE = PROJECT_PREFIX + "sms:code:";

    /**
     * Token过期时间（秒）：7天
     */
    public static final long TOKEN_EXPIRE = 7 * 24 * 60 * 60;

    /**
     * 验证码过期时间（秒）：5分钟
     */
    public static final long CAPTCHA_EXPIRE = 5 * 60;

    /**
     * 短信验证码过期时间（秒）：5分钟
     */
    public static final long SMS_CODE_EXPIRE = 5 * 60;
}
