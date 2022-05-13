package com.ruowei.ecsp.web.rest;

import com.querydsl.core.BooleanBuilder;
import com.ruowei.ecsp.domain.CarbonTrade;
import com.ruowei.ecsp.domain.QCarbonTrade;
import com.ruowei.ecsp.repository.CarbonTradeRepository;
import com.ruowei.ecsp.service.OptionalBooleanBuilder;
import com.ruowei.ecsp.util.JsonUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.CarbonTradeStatisticDTO;
import com.ruowei.ecsp.web.rest.dto.ForestDataEncDTO;
import com.ruowei.ecsp.web.rest.dto.RealTradeStatisticDTO;
import com.ruowei.ecsp.web.rest.qm.CarbonTradeTableQM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static com.ruowei.ecsp.config.Constants.ZERO;


/**
 * REST controller for managing {@link com.ruowei.ecsp.domain.CarbonTrade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@Tag(name = "碳价数据管理")
public class CarbonTradeResource {

    private final Logger log = LoggerFactory.getLogger(CarbonTradeResource.class);

    private final CarbonTradeRepository carbonTradeRepository;

    private QCarbonTrade qCT = QCarbonTrade.carbonTrade;

    public CarbonTradeResource(CarbonTradeRepository carbonTradeRepository) {
        this.carbonTradeRepository = carbonTradeRepository;
    }

    @GetMapping("/permit/carbon-trades/statistic")
    @Operation(summary = "碳交易数据统计数据", description = "author: czz")
    public ResponseEntity<ForestDataEncDTO> getStatistics(CarbonTradeTableQM qm) {
        BooleanBuilder defaultBuilder = getDefaultBuilder()
//            .and(qCT.type.contains("A"))
            .and(qCT.provinceName.ne("全国EA"));
        BooleanBuilder additionalBuilder = new OptionalBooleanBuilder()
            .notEmptyAnd(qCT.tradeDate::goe, qm.getTradeDateStart())
            .notEmptyAnd(qCT.tradeDate::loe, qm.getTradeDateEnd())
            .notEmptyAnd(qCT.type::eq, qm.getType())
            .build();
        Iterable<CarbonTrade> carbonTrades = carbonTradeRepository.findAll(
            defaultBuilder.and(additionalBuilder),
            Sort.by(Sort.Direction.ASC, "provinceName", "tradeDate")
        );
        // List
        List<CarbonTradeStatisticDTO> tempResult = StreamUtil.streamMapCollect(
            Stream.of("重庆", "上海", "北京", "湖北", "广州"),
            CarbonTradeStatisticDTO::new
        );
        Map<LocalDate, CarbonTradeStatisticDTO.DateTrade> mapTJ = new HashMap<>();
        Map<LocalDate, CarbonTradeStatisticDTO.DateTrade> mapSZ = new HashMap<>();
        List<CarbonTradeStatisticDTO> finalTempResult = tempResult;
        carbonTrades.forEach(ct -> {
            // 天津，深圳会有多个EA的type
            if (ct.getProvinceName().equals("天津")) {
                addDataToMap(mapTJ, ct);
            } else if (ct.getProvinceName().equals("深圳")) {
                addDataToMap(mapSZ, ct);
            } else {
                Optional<CarbonTradeStatisticDTO> optional = finalTempResult
                    .stream()
                    .filter(dto -> dto.getProvinceName().equals(ct.getProvinceName()))
                    .findFirst();
                if (optional.isPresent()) {
                    CarbonTradeStatisticDTO dto = optional.get();
                    CarbonTradeStatisticDTO.DateTrade dateTrade = new CarbonTradeStatisticDTO.DateTrade(ct);
                    dto.getDateTrades().add(dateTrade);
                }
            }
        });
        // 天津
        tempResult.add(getFromDataMap("天津", mapTJ));
        // 深圳
        tempResult.add(getFromDataMap("深圳", mapSZ));
        tempResult.forEach(dto -> {
            List<CarbonTradeStatisticDTO.DateTrade> dateTrades = dto.getDateTrades();
            BigDecimal totalPriceSum = StreamUtil.sumProperty(dateTrades, CarbonTradeStatisticDTO.DateTrade::getTotalPrice);
            BigDecimal amountSum = StreamUtil.sumProperty(dateTrades, CarbonTradeStatisticDTO.DateTrade::getAmount);
            dto.setExtra(totalPriceSum, amountSum, dateTrades.size());
        });
        // 省份数据按交易的日数据数量倒序
        tempResult = StreamUtil.sortedCollect(tempResult, Comparator.comparing(CarbonTradeStatisticDTO::getDateTradeSize).reversed());
        List<com.ruowei.ecsp.web.rest.dto.RealTradeStatisticDTO> result = StreamUtil.collectV(tempResult, RealTradeStatisticDTO::new);
        return ResponseEntity.ok().body(JsonUtil.getEncodedTransUnit(result, "加密碳交易统计数据失败！"));
    }

    @GetMapping("/permit/carbon-trades")
    @Operation(summary = "碳交易数据列表", description = "author: czz")
    public ResponseEntity<ForestDataEncDTO> getAllCarbonTrades(
        CarbonTradeTableQM qm,
        @PageableDefault(sort = {"tradeDate", "type"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BooleanBuilder predicate = new OptionalBooleanBuilder()
            .notEmptyAnd(qCT.type::eq, qm.getType())
            .notEmptyAnd(qCT.tradeDate::loe, qm.getTradeDateEnd())
            .notEmptyAnd(qCT.tradeDate::goe, qm.getTradeDateStart())
            .build();
        Page<CarbonTrade> page = carbonTradeRepository.findAll(predicate.and(getDefaultBuilder()), pageable);
        HttpHeaders headers = PageUtil.getPageHeader(page);
        return ResponseEntity.ok().headers(headers).body(JsonUtil.getEncodedTransUnit(page.getContent(), "加密碳交易数据失败！"));
    }

    @GetMapping("/carbon-trades/all-types")
    @Operation(summary = "获取所有碳交易数据的交易类型")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok().body(carbonTradeRepository.getAllType());
    }

    private BooleanBuilder getDefaultBuilder() {
        return new BooleanBuilder()
            .and(qCT.amount.gt(ZERO))
            .and(qCT.averagePrice.gt(ZERO))
            .and(qCT.totalPrice.gt(ZERO))
            .and(qCT.type.notIn("", "-"))
            .and(qCT.type.isNotNull());
    }

    private CarbonTradeStatisticDTO getFromDataMap(String provinceName, Map<LocalDate, CarbonTradeStatisticDTO.DateTrade> map) {
        List<CarbonTradeStatisticDTO.DateTrade> data = StreamUtil.sortedCollect(
            map.values(),
            Comparator.comparing(CarbonTradeStatisticDTO.DateTrade::getTradeDate)
        );
        return new CarbonTradeStatisticDTO(provinceName, data);
    }

    private void addDataToMap(Map<LocalDate, CarbonTradeStatisticDTO.DateTrade> map, CarbonTrade ct) {
        CarbonTradeStatisticDTO.DateTrade newDateTrade = new CarbonTradeStatisticDTO.DateTrade(ct);
        if (map.containsKey(ct.getTradeDate())) {
            CarbonTradeStatisticDTO.DateTrade dateTrade = map.get(ct.getTradeDate());
            dateTrade.add(newDateTrade);
        } else {
            map.put(ct.getTradeDate(), newDateTrade);
        }
    }
}
