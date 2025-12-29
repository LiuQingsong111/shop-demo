package com.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车DTO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "添加购物车请求")
public class CartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID", example = "1")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "商品数量", example = "1")
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;
}
