package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("方法学类型：CCER | 碳普惠")
    private String type;

    /**
     * 方法学名称
     */
    @ApiModelProperty("方法学名称")
    private String name;
}
