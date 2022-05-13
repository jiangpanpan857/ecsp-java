package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteListQM {

    /**
     * 网站名称
     */
    @Schema(description = "网站名称")
    @ApiModelProperty("网站名称")
    private String name;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名")
    @ApiModelProperty("网站域名")
    private String domain;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    @ApiModelProperty("机构名称")
    private String organizationName;
}
