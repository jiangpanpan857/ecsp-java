package com.ruowei.ecsp.web.rest.dto;

import com.ruowei.ecsp.domain.EcoUser;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcoUserDTO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 网站ID
     */
    @Schema(description = "网站ID")
    @ApiModelProperty("网站ID")
    private Long websiteId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @ApiModelProperty("用户名")
    private String login;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @ApiModelProperty("密码")
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ApiModelProperty("姓名")
    private String realName;

    /**
     * 角色编码: ROLE_ADMIN | ROLE_MANAGER
     */
    @Schema(description = "角色编码: ROLE_ADMIN | ROLE_MANAGER")
    @ApiModelProperty("角色编码: ROLE_ADMIN | ROLE_MANAGER")
    private String roleCode;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间")
    @ApiModelProperty("添加时间")
    private ZonedDateTime addTime;

    /**
     * 网站名
     */
    @Schema(description = "网站名")
    @ApiModelProperty("网站名")
    private String websiteName;

    public EcoUserDTO(EcoUser ecoUser) {
        this.id = ecoUser.getId();
        this.websiteId = ecoUser.getWebsiteId();
        this.login = ecoUser.getLogin();
        this.password = ecoUser.getPassword();
        this.realName = ecoUser.getRealName();
        this.roleCode = ecoUser.getRoleCode();
        this.addTime = ecoUser.getAddTime();
    }
}
