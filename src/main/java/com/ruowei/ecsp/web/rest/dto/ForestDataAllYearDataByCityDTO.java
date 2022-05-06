package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForestDataAllYearDataByCityDTO {

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "数值")
    private BigDecimal value;
}
