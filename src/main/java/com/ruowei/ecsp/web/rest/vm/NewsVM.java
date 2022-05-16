package com.ruowei.ecsp.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NewsVM {

    /**
     * 新闻标题
     */
    @Schema(description = "新闻标题")
    @ApiModelProperty("新闻标题")
    private String title;

    /**
     * 新闻类型
     */
    @Schema(description = "新闻类型")
    @ApiModelProperty("新闻类型")
    private String type;

    /**
     * 发布状态
     */
    @Schema(description = "发布状态")
    @ApiModelProperty("发布状态")
    private String status;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名")
    @ApiModelProperty("网站域名")
    private String domain;
}
