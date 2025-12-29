package com.shop.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应VO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "登录响应")
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "用户信息")
    private UserVO userInfo;
}
