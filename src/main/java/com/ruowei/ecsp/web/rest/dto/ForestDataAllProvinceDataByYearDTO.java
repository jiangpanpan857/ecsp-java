package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public interface ForestDataAllProvinceDataByYearDTO {
    @ApiModelProperty(value = "省份id")
    String getId();

    @ApiModelProperty(value = "省份名称")
    String getName();

    @ApiModelProperty(value = "数值")
    BigDecimal getValue();
}
