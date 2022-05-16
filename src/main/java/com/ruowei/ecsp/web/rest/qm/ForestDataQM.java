package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForestDataQM {

    /**
     * 省份ID
     */
    @Schema(description = "省份ID")
    @ApiModelProperty(value = "省份ID")
    private String provinceId;

    /**
     * 城市ID
     */
    @Schema(description = "城市ID")
    @ApiModelProperty(value = "城市ID")
    private String cityId;

    /**
     * 年份
     */
    @Schema(description = "年份")
    @ApiModelProperty(value = "年份")
    private String year;
}
