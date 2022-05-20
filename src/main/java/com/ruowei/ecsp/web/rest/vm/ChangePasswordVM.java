package com.ruowei.ecsp.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordVM {

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码")
    private String oldPwd;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
