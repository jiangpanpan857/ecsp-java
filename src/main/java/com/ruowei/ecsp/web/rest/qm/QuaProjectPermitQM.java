package com.ruowei.ecsp.web.rest.qm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuaProjectPermitQM extends QualityProjectTableQM{

    /**
     * 预计减排量：起始值
     */
    @Schema(description = "预计减排量：起始值")
    private BigDecimal preSinkStart;

    /**
     * 预计减排量：结束值
     */
    @Schema(description = "预计减排量：结束值")
    private BigDecimal preSinkEnd;

    /**
     * 签约减排量：起始值
     */
    @Schema(description = "签约减排量：起始值")
    private BigDecimal recordSinkStart;

    /**
     * 签约减排量：结束值
     */
    @Schema(description = "签约减排量：结束值")
    private BigDecimal recordSinkEnd;
}
