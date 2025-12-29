package com.shop.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 通用状态码
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // 参数错误 4xx
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 用户模块 5001-5099
    USER_NOT_FOUND(5001, "用户不存在"),
    USER_EXISTS(5002, "用户已存在"),
    PASSWORD_ERROR(5003, "密码错误"),
    USER_DISABLED(5004, "用户已被禁用"),
    TOKEN_EXPIRED(5005, "登录已过期，请重新登录"),
    TOKEN_INVALID(5006, "无效的Token"),
    USERNAME_OR_PASSWORD_ERROR(5007, "用户名或密码错误"),
    PHONE_EXISTS(5008, "手机号已被注册"),
    EMAIL_EXISTS(5009, "邮箱已被注册"),

    // 商品模块 5101-5199
    PRODUCT_NOT_FOUND(5101, "商品不存在"),
    PRODUCT_OFF_SALE(5102, "商品已下架"),
    PRODUCT_STOCK_NOT_ENOUGH(5103, "商品库存不足"),
    CATEGORY_NOT_FOUND(5104, "分类不存在"),
    CATEGORY_HAS_CHILDREN(5105, "该分类下存在子分类，无法删除"),
    CATEGORY_HAS_PRODUCTS(5106, "该分类下存在商品，无法删除"),

    // 购物车模块 5201-5299
    CART_ITEM_NOT_FOUND(5201, "购物车商品不存在"),
    CART_EMPTY(5202, "购物车为空"),

    // 订单模块 5301-5399
    ORDER_NOT_FOUND(5301, "订单不存在"),
    ORDER_STATUS_ERROR(5302, "订单状态异常"),
    ORDER_ALREADY_PAID(5303, "订单已支付"),
    ORDER_ALREADY_CANCELED(5304, "订单已取消"),
    ORDER_CANNOT_CANCEL(5305, "订单无法取消"),
    ORDER_CANNOT_DELETE(5306, "订单无法删除"),

    // 支付模块 5401-5499
    PAY_ERROR(5401, "支付失败"),
    PAY_AMOUNT_ERROR(5402, "支付金额异常"),
    REFUND_ERROR(5403, "退款失败"),

    // 地址模块 5501-5599
    ADDRESS_NOT_FOUND(5501, "收货地址不存在"),
    ADDRESS_LIMIT_EXCEEDED(5502, "收货地址数量已达上限");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;
}
