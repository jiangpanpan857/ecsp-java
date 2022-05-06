package com.ruowei.ecsp.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVM {

    @ApiModelProperty(value = "路径", required = true)
    private String path;
}
