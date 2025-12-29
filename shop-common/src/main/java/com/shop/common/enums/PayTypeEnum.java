package com.shop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信支付"),
    BALANCE(3, "余额支付");

    /**
     * 类型码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据code获取枚举
     */
    public static PayTypeEnum getByCode(Integer code) {
        for (PayTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
