package com.ruowei.ecsp.web.rest.qm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinUserQM {

    /**
     * 城市
     */
    @Schema(description = "城市")
    private String cityName;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String organizationName;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String login;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID ")
    private Long id;


}
