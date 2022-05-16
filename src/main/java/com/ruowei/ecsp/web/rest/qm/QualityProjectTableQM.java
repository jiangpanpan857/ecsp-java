package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QualityProjectTableQM {

    /**
     * 项目名称
     */
    @Schema(description = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String name;

    /**
     * 方法学: CCER | VCS | 碳惠普
     */
    @Schema(description = "方法学: CCER | VCS | 碳惠普")
    @ApiModelProperty(value = "方法学: CCER | VCS | 碳惠普")
    private String method;

    /**
     * 项目类型
     */
    @Schema(description = "项目类型")
    @ApiModelProperty(value = "项目类型")
    private List<String> typeList;

    /**
     * 省份名
     */
    @Schema(description = "省份名")
    @ApiModelProperty(value = "省份名")
    private List<String> provinceNameList;

    /**
     * 状态: 已发布 | 未发布
     */
    @Schema(description = "状态: 已发布 | 未发布")
    @ApiModelProperty(value = "状态: 已发布 | 未发布")
    private String status;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名")
    @ApiModelProperty("网站域名")
    private String domain;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @Schema(description = "结束日期")
    private LocalDate endDate;
}
