package com.ruowei.ecsp.web.rest.dto;

import com.ruowei.ecsp.util.DateUtil;
import com.ruowei.ecsp.util.StreamUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RealTradeStatisticDTO implements Serializable {

    /**
     * name : 上海
     * data : [["2019-12-01",320],["2020-12-01",222],["2020-11-01",111],["2020-10-01",888],["2020-09-01",123],["2020-08-01",200]]
     * type : line
     * smooth : true
     * connectNulls : true
     * amountSum: 32.00
     * totalPriceSum: 3232.00
     */

    private String name;
    private String type;
    private Boolean smooth;
    private Boolean connectNulls;
    private BigDecimal amountSum;
    private BigDecimal totalPriceSum;
    private List<List<String>> data;

    public RealTradeStatisticDTO(CarbonTradeStatisticDTO dto) {
        this.setName(dto.getProvinceName());
        this.setType("line");
        this.setSmooth(true);
        this.setConnectNulls(true);
        this.setAmountSum(dto.getAmountSum());
        this.setTotalPriceSum(dto.getTotalPriceSum());
        List<List<String>> temp = StreamUtil.collectV(
            dto.getDateTrades(),
            trade -> Arrays.asList(DateUtil.defaultFormat(trade.getTradeDate()), trade.getAvgPrice().toString())
        );
        this.setData(temp);
    }
}
