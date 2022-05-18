package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public interface ForestDataAllAreaDataByCityAndYearDTO {
    @ApiModelProperty(value = "城市id")
    String getId();

    @ApiModelProperty(value = "城市名称")
    String getName();

    @ApiModelProperty(value = "数值")
    BigDecimal getValue();
}
