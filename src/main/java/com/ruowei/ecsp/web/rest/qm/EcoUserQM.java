package com.ruowei.ecsp.web.rest.qm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcoUserQM {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String login;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称")
    private String websiteName;

    /**
     * 用户实际姓名
     */
    @Schema(description = "用户实际姓名")
    private String realName;
}
