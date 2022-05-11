package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DistrictDTO {

    /**
     * 地区编码
     */
    @Schema(description = "地区编码")
    @ApiModelProperty(value = "地区编码")
    private String value;

    /**
     * 地区名称
     */
    @Schema(description = "地区名称")
    @ApiModelProperty(value = "地区名称")
    private String label;

    /**
     * 子地区信息
     */
    @Schema(description = "子地区信息")
    @ApiModelProperty(value = "子地区信息")
    private List<DistrictDTO> children;
}
