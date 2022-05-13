package com.ruowei.ecsp.web.rest.dto;

import com.ruowei.ecsp.domain.CarbonTrade;
import com.ruowei.ecsp.util.NumUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarbonTradeStatisticDTO {

    /**
     * 省份
     */
    @Schema(description = "省份")
    @ApiModelProperty("省份")
    private String provinceName;

    /**
     * 指定时域下的交易数量
     */
    @Schema(description = "指定时域下的交易数量")
    @ApiModelProperty("指定时域下的交易数量")
    private BigDecimal amountSum;

    /**
     * 指定时域下的交易金额
     */
    @Schema(description = "指定时域下的交易金额")
    @ApiModelProperty("指定时域下的交易金额")
    private BigDecimal totalPriceSum;

    /**
     * 该省份交易日数据的总数统计
     */
    @Schema(description = "该省份交易日数据的总数统计")
    @ApiModelProperty("该省份交易日数据的总数统计")
    private int dateTradeSize;

    /**
     * 指定省份下的交易数据【日期正序】
     */
    @Schema(description = "指定省份下的交易数据【日期正序】")
    @ApiModelProperty("指定省份下的交易数据【日期正序】")
    private List<DateTrade> dateTrades;

    public CarbonTradeStatisticDTO(String provinceName) {
        this.provinceName = provinceName;
        this.dateTrades = new ArrayList<>();
    }

    public CarbonTradeStatisticDTO(String provinceName, List<DateTrade> dateTrades) {
        this.provinceName = provinceName;
        this.dateTrades = dateTrades;
    }

    public void setExtra(BigDecimal totalPriceSum, BigDecimal amountSum, int dateTradeSize) {
        this.totalPriceSum = totalPriceSum;
        this.amountSum = amountSum;
        this.dateTradeSize = dateTradeSize;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateTrade {

        /**
         * 交易日期
         */
        @Schema(description = "交易日期")
        @ApiModelProperty("交易日期")
        private LocalDate tradeDate;

        /**
         * 交易数量
         */
        @Schema(description = "交易数量")
        @ApiModelProperty("交易数量")
        private BigDecimal amount;

        /**
         * 交易总金额
         */
        @Schema(description = "交易总金额")
        @ApiModelProperty("交易总金额")
        private BigDecimal totalPrice;

        /**
         * 交易均价
         */
        @Schema(description = "交易均价")
        @ApiModelProperty("交易均价")
        private BigDecimal avgPrice;

        public DateTrade(CarbonTrade carbonTrade) {
            this.tradeDate = carbonTrade.getTradeDate();
            this.amount = carbonTrade.getAmount();
            this.totalPrice = carbonTrade.getTotalPrice();
            this.avgPrice = carbonTrade.getAveragePrice();
        }

        public void add(DateTrade newDateTrade) {
            this.amount = this.amount.add(newDateTrade.getAmount());
            this.totalPrice = this.totalPrice.add(newDateTrade.getTotalPrice());
            this.avgPrice = NumUtil.div(this.totalPrice, this.amount, 2, RoundingMode.HALF_UP);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateTypeTrade {

        private LocalDate date;

        private BigDecimal amount;

        private BigDecimal totalPrice;

        private BigDecimal avgPrice;

        private String type;
    }
}
