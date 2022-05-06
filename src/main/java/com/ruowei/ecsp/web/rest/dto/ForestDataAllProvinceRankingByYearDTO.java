package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForestDataAllProvinceRankingByYearDTO {

    @ApiModelProperty(value = "排名")
    private Integer rank;

    @ApiModelProperty(value = "省份id")
    private String id;

    @ApiModelProperty(value = "省份名称")
    private String name;

    @ApiModelProperty(value = "数值")
    private BigDecimal value;
}
