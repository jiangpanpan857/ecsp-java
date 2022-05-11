package com.ruowei.ecsp.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVM {

    /**
     * 路径
     */
    @Schema(description = "路径")
    @ApiModelProperty(value = "路径", required = true)
    private String path;
}
