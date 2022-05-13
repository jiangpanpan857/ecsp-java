package com.ruowei.ecsp.web.rest.qm;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarbonTradeTableQM {

    /**
     * 交易类型: CCER, CEA, ...
     */
    @Schema(description = "交易类型: CCER, CEA, ...")
    @ApiModelProperty(value = "交易类型: CCER, CEA, ...")
    private String type;

    /**
     * 日期开始
     */
    @Schema(description = "日期开始")
    @ApiModelProperty("日期开始")
    private LocalDate tradeDateStart;

    /**
     * 日期结束
     */
    @Schema(description = "日期结束")
    @ApiModelProperty("日期结束")
    private LocalDate tradeDateEnd;
}
