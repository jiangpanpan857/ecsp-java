package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SinProjectQM {

    /**
     * 项目名称，模糊查询
     */
    @ApiModelProperty(value = "项目名称，模糊查询")
    private String projectName;

    /**
     * 项目类型编码
     */
    @ApiModelProperty(value = "项目类型编码")
    private String projectTypeCode;

    /**
     * 项目状态编码
     */
    @ApiModelProperty(value = "项目状态编码")
    private String projectStatus;

    /**
     * 项目地点所在省份编码
     */
    @ApiModelProperty(value = "项目地点所在省份编码")
    private String provinceCode;

    /**
     * 搁置状态
     */
    @ApiModelProperty(value = "搁置状态")
    private Boolean layAsideStatus;

    /**
     * 项目业主名称,模糊查询
     */
    @ApiModelProperty(value = "项目业主名称,模糊查询")
    private String companyName;

    /**
     * 业务员名称,模糊查询
     */
    @ApiModelProperty(value = "业务员名称,模糊查询")
    private String salesmanName;
}
