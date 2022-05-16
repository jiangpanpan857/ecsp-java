package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodologyQM {
    /**
     * 方法学类型：CCER | 碳普惠
     */
    @Schema(description = "方法学类型：CCER | 碳普惠")
    @ApiModelProperty("方法学类型：CCER | 碳普惠")
    private String type;

    /**
     * 方法学名称
     */
    @Schema(description = "方法学名称")
    @ApiModelProperty("方法学名称")
    private String name;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名")
    @ApiModelProperty("网站域名")
    private String domain;
}
