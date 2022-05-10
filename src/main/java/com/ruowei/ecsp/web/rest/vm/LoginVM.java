package com.ruowei.ecsp.web.rest.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginVM {

    @NotNull
    @Schema(description = "用户名", required = true)
    private String username;

    @NotNull
    @Schema(description = "密码", required = true)
    private String password;

    @Schema(description = "记住我", required = true)
    private boolean rememberMe;

    @Schema(description = "登录域名", required = true)
    private String domain;
}
