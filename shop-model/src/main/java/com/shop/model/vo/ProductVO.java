package com.shop.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品VO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "商品信息")
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private Long id;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "副标题")
    private String subtitle;

    @Schema(description = "主图")
    private String mainImage;

    @Schema(description = "副图")
    private String subImages;

    @Schema(description = "商品详情")
    private String detail;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "状态：0-下架，1-上架")
    private Integer status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
