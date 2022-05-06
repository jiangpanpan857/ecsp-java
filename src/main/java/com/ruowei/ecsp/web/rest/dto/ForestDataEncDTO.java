package com.ruowei.ecsp.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForestDataEncDTO {

    @ApiModelProperty(value = "已加密数据")
    private String data;
}
