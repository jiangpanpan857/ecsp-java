package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForestDataQM {

    @ApiModelProperty(value = "省份ID")
    private String provinceId;

    @ApiModelProperty(value = "城市ID")
    private String cityId;

    @ApiModelProperty(value = "年份")
    private String year;
}
