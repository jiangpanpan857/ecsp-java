//package com.ruowei.ecsp.web.rest;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.querydsl.core.BooleanBuilder;
//import com.ruowei.ecsp.domain.ForestData;
//import com.ruowei.ecsp.domain.QForestData;
//import com.ruowei.ecsp.service.OptionalBooleanBuilder;
//import com.ruowei.ecsp.util.DESUtil;
//import com.ruowei.ecsp.web.rest.dto.*;
//import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
//import com.ruowei.ecsp.web.rest.qm.ForestDataQM;
//import io.swagger.annotations.ApiParam;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import tech.jhipster.web.util.PaginationUtil;
//import tech.jhipster.web.util.ResponseUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@RestController
//@RequestMapping("/api/cor")
//@Transactional
//@Tag(name = "公用数据-森林数据查询模块")
//public class EcoCorResource {
//
//    private final QForestData qForestData = QForestData.forestData;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final List<String> fixedYears = List.of("2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019");
//
//
//    @GetMapping("/permit/forest-datas")
//    @Operation(summary = "森林数据列表")
//    public ResponseEntity<List<ForestData>> getAllForestData(
//        ForestDataQM qm,
//        @PageableDefault(sort = {"cityId", "year"}, direction = Sort.Direction.ASC) Pageable pageable
//    ) {
//        BooleanBuilder predicate = new OptionalBooleanBuilder()
//            .notEmptyAnd(qForestData.provinceId::eq, qm.getProvinceId())
//            .notEmptyAnd(qForestData.cityId::eq, qm.getCityId())
//            .notEmptyAnd(qForestData.areaId::eq, qm.getAreaId())
//            .notEmptyAnd(qForestData.year::eq, qm.getYear())
//            .build();
//        Page<ForestData> page = forestDataRepository.findAll(predicate, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }
//
//    @GetMapping("/permit/forest-datas/{id}")
//    @Operation(summary = "森林数据详情")
//    public ResponseEntity<ForestData> getForestData(@PathVariable Long id) {
//        Optional<ForestData> forestData = forestDataRepository.findById(id);
//        return ResponseUtil.wrapOrNotFound(forestData);
//    }
//
//
//    @GetMapping("/permit/forest-datas/allProvinceDataByYear")
//    @Operation(summary = "森林数据统计信息-指定年份的各省份数据")
//    public ResponseEntity<ForestDataEncDTO> getAllProvinceDataByYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllProvinceDataByYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllProvinceStorageByYear(startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list = forestDataRepository.getAllProvinceAreaIncrementByYear(startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allCityDataByProvinceAndYear")
//    @Operation(summary = "森林数据统计信息-指定省份和年份的各城市数据")
//    public ResponseEntity<ForestDataEncDTO> getAllCityDataByProvinceAndYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("省份") @RequestParam String provinceId,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllCityDataByProvinceAndYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllCityStorageByProvinceAndYear(provinceId, startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list = forestDataRepository.getAllCityAreaIncrementByProvinceAndYear(provinceId, startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allAreaDataByCityAndYear")
//    @Operation(summary = "森林数据统计信息-指定城市和年份的各区域数据")
//    public ResponseEntity<ForestDataEncDTO> getAllAreaDataByCityAndYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("市") @RequestParam String cityId,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllAreaDataByCityAndYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllAreaStorageByCityAndYear(cityId, startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list = forestDataRepository.getAllAreaAreaIncrementByCityAndYear(cityId, startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allProvinceRankingByYear")
//    @Operation(summary = "森林数据统计信息-指定年份的各省份排名")
//    public ResponseEntity<ForestDataEncDTO> getAllProvinceRankingByYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllProvinceRankingByYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllProvinceStorageRankingByYear(startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list = forestDataRepository.getAllProvinceAreaIncrementRankingByYear(startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        IntStream.range(0, list.size()).forEach(i -> list.get(i).setRank(i + 1));
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allCityRankingByProvinceAndYear")
//    @Operation(summary = "森林数据统计信息-指定年份和省份的各城市排名")
//    public ResponseEntity<ForestDataEncDTO> getAllCityRankingByProvinceAndYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("省份") @RequestParam String provinceId,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllCityRankingByProvinceAndYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllCityStorageRankingByProvinceAndYear(provinceId, startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list =
//                forestDataRepository.getAllCityAreaIncrementRankingByProvinceAndYear(provinceId, startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        IntStream.range(0, list.size()).forEach(i -> list.get(i).setRank(i + 1));
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allAreaRankingByCityAndYear")
//    @Operation(summary = "森林数据统计信息-指定年份和城市下各区域排名")
//    public ResponseEntity<ForestDataEncDTO> getAllAreaRankingByCityAndYear(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("城市") @RequestParam String cityId,
//        @ApiParam("起始年份") @RequestParam Integer startYear,
//        @ApiParam("截止年份") @RequestParam Integer endYear
//    ) throws Exception {
//        List<ForestDataAllAreaRankingByCityAndYearDTO> list;
//        if (startYear > endYear) {
//            throw new BadRequestProblem("请求年份范围错误");
//        }
//        if ("0".equals(type)) {
//            list = forestDataRepository.getAllAreaStorageRankingByCityAndYear(cityId, startYear.toString(), endYear.toString());
//        } else if ("1".equals(type)) {
//            list = forestDataRepository.getAllAreaAreaIncrementRankingByCityAndYear(cityId, startYear.toString(), endYear.toString());
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        IntStream.range(0, list.size()).forEach(i -> list.get(i).setRank(i + 1));
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allYearDataByProvince")
//    @Operation(summary = "森林数据统计信息-指定省份的各年份数据")
//    public ResponseEntity<ForestDataEncDTO> getAllYearDataByProvince(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("省份") @RequestParam String provinceId
//    ) throws Exception {
//        List<ForestDataAllYearDataByProvinceDTO> list = new ArrayList<>(10);
//        if ("0".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(new ForestDataAllYearDataByProvinceDTO(year, forestDataRepository.getAllYearStorageByProvince(provinceId, year)));
//            }
//        } else if ("1".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(
//                    new ForestDataAllYearDataByProvinceDTO(year, forestDataRepository.getAllYearAreaIncrementByProvince(provinceId, year))
//                );
//            }
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allYearDataByCity")
//    @Operation(summary = "森林数据统计信息-指定城市的各年份数据")
//    public ResponseEntity<ForestDataEncDTO> getAllYearDataByCity(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("城市") @RequestParam String cityId
//    ) throws Exception {
//        List<ForestDataAllYearDataByCityDTO> list = new ArrayList<>(10);
//        if ("0".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(new ForestDataAllYearDataByCityDTO(year, forestDataRepository.getAllYearStorageByCity(cityId, year)));
//            }
//        } else if ("1".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(new ForestDataAllYearDataByCityDTO(year, forestDataRepository.getAllYearAreaIncrementByCity(cityId, year)));
//            }
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//    @GetMapping("/permit/forest-datas/allYearDataByArea")
//    @Operation(summary = "森林数据统计信息-指定区域的各年份数据")
//    public ResponseEntity<ForestDataEncDTO> getAllYearDataByArea(
//        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
//        @ApiParam("区域") @RequestParam String areaId
//    ) throws Exception {
//        List<ForestDataAllYearDataByCityDTO> list = new ArrayList<>(10);
//        if ("0".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(new ForestDataAllYearDataByCityDTO(year, forestDataRepository.getAllYearStorageByArea(areaId, year)));
//            }
//        } else if ("1".equals(type)) {
//            for (String year : fixedYears) {
//                list.add(new ForestDataAllYearDataByCityDTO(year, forestDataRepository.getAllYearAreaIncrementByArea(areaId, year)));
//            }
//        } else {
//            throw new BadRequestProblem("请求数据类型错误");
//        }
//        String json = objectMapper.writeValueAsString(list);
//        return ResponseEntity.ok().body(new ForestDataEncDTO(DESUtil.encrypt(json)));
//    }
//
//}
