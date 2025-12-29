package com.shop.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车VO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "购物车信息")
public class CartVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "购物车ID")
    private Long id;

    @Schema(description = "商品ID")
    private Long productId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品图片")
    private String productImage;

    @Schema(description = "商品单价")
    private BigDecimal productPrice;

    @Schema(description = "商品数量")
    private Integer quantity;

    @Schema(description = "商品总价")
    private BigDecimal totalPrice;

    @Schema(description = "是否选中：0-未选中，1-选中")
    private Integer checked;

    @Schema(description = "商品库存")
    private Integer stock;

    @Schema(description = "商品状态：0-下架，1-上架")
    private Integer productStatus;
}
