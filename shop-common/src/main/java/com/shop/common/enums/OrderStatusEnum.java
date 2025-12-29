package com.shop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING_PAYMENT(0, "待支付"),
    PENDING_DELIVERY(1, "待发货"),
    PENDING_RECEIVE(2, "待收货"),
    COMPLETED(3, "已完成"),
    CANCELED(4, "已取消"),
    REFUNDING(5, "退款中"),
    REFUNDED(6, "已退款");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态描述
     */
    private final String desc;

    /**
     * 根据code获取枚举
     */
    public static OrderStatusEnum getByCode(Integer code) {
        for (OrderStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
