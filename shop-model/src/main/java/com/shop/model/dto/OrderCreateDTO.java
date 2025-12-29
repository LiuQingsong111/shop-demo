package com.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建订单DTO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "创建订单请求")
public class OrderCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "收货地址ID", example = "1")
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    @Schema(description = "购物车ID列表")
    @NotNull(message = "请选择要购买的商品")
    private List<Long> cartIds;

    @Schema(description = "订单备注")
    private String remark;
}
