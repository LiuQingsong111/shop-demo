package com.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录DTO
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@Schema(description = "用户登录请求")
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Schema(description = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;
}
