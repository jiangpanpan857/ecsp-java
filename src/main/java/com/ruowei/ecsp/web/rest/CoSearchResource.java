package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.CoSearchService;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.qm.CarbonTradeTableQM;
import com.ruowei.ecsp.web.rest.qm.SinProjectQM;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Transactional
@Tag(name = "请求转发接口管理")
public class CoSearchResource {

    private final CoSearchService coSearchService;
    private final WebsiteRepository websiteRepository;

    public CoSearchResource(CoSearchService coSearchService, WebsiteRepository websiteRepository) {
        this.coSearchService = coSearchService;
        this.websiteRepository = websiteRepository;
    }

    @GetMapping("/permit/forest-datas/allCityDataByProvinceAndYear")
    @Operation(summary = "森林数据统计信息-指定省份和年份的各城市数据")
    public ResponseEntity<Object> getAllCityDataByProvinceAndYear(
        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
        @ApiParam("省份") @RequestParam String provinceId,
        @ApiParam("起始年份") @RequestParam Integer startYear,
        @ApiParam("截止年份") @RequestParam Integer endYear
    ) {
        String url = "forest-datas/allCityDataByProvinceAndYear";
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("provinceId", provinceId);
        map.put("startYear", startYear);
        map.put("endYear", endYear);
        return coSearchService.redirectGetWithoutSinToken(url, map, null);
    }

    @GetMapping("/permit/forest-datas/allCityRankingByProvinceAndYear")
    @Operation(summary = "森林数据统计信息-指定年份和省份的各城市排名")
    public ResponseEntity<Object> getAllCityRankingByProvinceAndYear(
        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
        @ApiParam("省份") @RequestParam String provinceId,
        @ApiParam("起始年份") @RequestParam Integer startYear,
        @ApiParam("截止年份") @RequestParam Integer endYear
    ) throws Exception {
        String url = "forest-datas/allCityRankingByProvinceAndYear";
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("provinceId", provinceId);
        map.put("startYear", startYear);
        map.put("endYear", endYear);
        return coSearchService.redirectGetWithoutSinToken(url, map, null);
    }

    @GetMapping("/permit/forest-datas/allAreaDataByCityAndYear")
    @Operation(summary = "森林数据统计信息-指定城市和年份的各区域数据")
    public ResponseEntity<Object> getAllAreaDataByCityAndYear(
        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
        @ApiParam("市") @RequestParam String cityId,
        @ApiParam("起始年份") @RequestParam Integer startYear,
        @ApiParam("截止年份") @RequestParam Integer endYear
    ) {
        String url = "permit/forest-datas/allAreaDataByCityAndYear";
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("cityId", cityId);
        map.put("startYear", startYear);
        map.put("endYear", endYear);
        return coSearchService.redirectGetWithoutSinToken(url, map, null);
    }

    @GetMapping("/permit/forest-datas/allAreaRankingByCityAndYear")
    @Operation(summary = "森林数据统计信息-指定年份和城市下各区域排名")
    public ResponseEntity<Object> getAllAreaRankingByCityAndYear(
        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
        @ApiParam("城市") @RequestParam String cityId,
        @ApiParam("起始年份") @RequestParam Integer startYear,
        @ApiParam("截止年份") @RequestParam Integer endYear
    ) {
        String url = "permit/forest-datas/allAreaRankingByCityAndYear";
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("cityId", cityId);
        map.put("startYear", startYear);
        map.put("endYear", endYear);
        return coSearchService.redirectGetWithoutSinToken(url, map, null);
    }

    @GetMapping("/permit/forest-datas/allYearDataByArea")
    @Operation(summary = "森林数据统计信息-指定区域的各年份数据")
    public ResponseEntity<Object> getAllYearDataByArea(
        @ApiParam("数据类型：0森林蓄积量｜1新增造林面积") @RequestParam String type,
        @ApiParam("区域") @RequestParam String areaId
    ) {
        String url = "permit/forest-datas/allYearDataByArea";
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("areaId", areaId);
        return coSearchService.redirectGetWithoutSinToken(url, map, null);
    }

    @GetMapping("/permit/carbon-trades/statistic/ccer")
    @Operation(summary = "碳交易数据统计数据", description = "author: czz")
    public ResponseEntity<Object> getCcerStatistics(CarbonTradeTableQM qm) {
        String url = "permit/carbon-trades/statistic/ccer";
        return coSearchService.redirectGetWithoutSinToken(url, qm, null);
    }

    @GetMapping("/eco-cooperate/users")
    @Operation(summary = "条件查询网站关联碳天秤账号候选项(系统管理员)", description = "author: czz")
    public ResponseEntity<Object> getAllCooperateUsers(SinUserQM qm) {
        List<SinUserDTO> sinUserDTOS = coSearchService.getCarbonLibraAccount(qm);
        List<String> accountIdStrs = StreamUtil.collectV(sinUserDTOS, sinUserDTO -> String.valueOf(sinUserDTO.getId()));
        List<Website> websites = websiteRepository.findAllByCarbonLibraAccountIn(accountIdStrs);
        for (Website website : websites) {
            sinUserDTOS.remove(sinUserDTOS.stream().filter(sinUserDTO -> String.valueOf(sinUserDTO.getId()).equals(website.getCarbonLibraAccount())).findFirst().get());
        }
        return ResponseEntity.ok().body(sinUserDTOS);
    }

    @GetMapping("/dicts/byCatagory")
    @Operation(summary = "按分类获取数据字典列表")
    public ResponseEntity<Object> getAllDictsByCatagory(@ApiParam(value = "分类编码", required = true) @RequestParam String catagory) {
        Map<String, Object> map = new HashMap<>();
        map.put("catagory", catagory);
        return coSearchService.redirectGetWithDefaultSinToken("dicts/byCatagory", map, null);
    }

    @GetMapping("/district/permit/only-province")
    @Operation(summary = "获取省下拉列表接口", description = "作者：czz")
    public ResponseEntity<Object> getProvinceDistricts() {
        String url = "district/only-province";
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-projects")
    @Operation(summary = "获取碳汇项目列表接口", description = "作者：czz")
    public ResponseEntity<Object> getAllSinkProjects(
        SinProjectQM qm,
        Pageable pageable
    ) {
        String url = "sink-projects";
        return coSearchService.redirectGetWithDefaultSinToken(url, qm, pageable);
    }

    @GetMapping("/sink-project/{id}")
    @Operation(summary = "获取碳汇项目信息接口", description = "作者: czz")
    public ResponseEntity<Object> getSinkProjectDetail(@PathVariable Long id) {
        String url = "sink-project/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/evaluation-application/{id}")
    @Operation(summary = "获取碳汇项目申请内容接口", description = "author: czz</br>")
    public ResponseEntity<Object> evaluationApplication(@PathVariable Long id) {
        String url = "evaluation-application/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-project-files")
    @Operation(summary = "查询碳汇项目文件列表", description = "作者：czz")
    public ResponseEntity<Object> getAllSinkProjectFiles(
        @ApiParam(value = "碳汇项目ID", required = true) @RequestParam String projectId,
        @ApiParam(value = "文件类别编码，多种时英文逗号分隔") @RequestParam(required = false) String fileCategoryCode
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("fileCategoryCode", fileCategoryCode);
        String url = "sink-project-files";
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }

    @GetMapping("/sink-project-red-check/detail/{id}")
    @Operation(summary = "获取碳汇项目减排量核查信息(详情调用)", description = "作者：czz")
    public ResponseEntity<Object> getSinkProjectRedCheckDetailInfo(@PathVariable Long id) {
        String url = "sink-project-red-check/detail/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-project-judgement/{id}")
    @Operation(summary = "获取碳汇项目审定信息", description = "作者：czz")
    public ResponseEntity<Object> getSinkProjectJudgementInfo(
        @PathVariable Long id,
        @ApiParam(value = "是否为申请时调用", required = true) @RequestParam Boolean isApplication
    ) {
        String url = "sink-project-judgement/" + id;
        Map<String, Object> map = new HashMap<>();
        map.put("isApplication", isApplication);
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }

    @GetMapping("/sink-project/grass-detail/{id}")
    @Operation(summary = "草地设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> estimationSinkGrass(@PathVariable Long id) {
        String url = "sink-project/grass-detail/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-project/grass-monitor/detail")
    @Operation(summary = "获取草地监测信息", description = "author: czz</br>")
    public ResponseEntity<Object> sinGrassMonitor(@RequestParam Long projectId, @RequestParam Integer serialNum) {
        String url = "sink-project/grass-monitor/detail";
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("serialNum", serialNum);
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }

    @GetMapping("/sink-project/sun-monitor-detail/")
    @Operation(summary = "光伏监测信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> getSunDesignVM(@NotNull @RequestParam Long projectId, @NotNull @RequestParam Integer serialNum) {
        String url = "sink-project/sun-monitor-detail/";
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("serialNum", serialNum);
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }

    @GetMapping("/sink-project/sun-detail/{id}")
    @Operation(summary = "光伏设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> getSunDesignVM(@PathVariable Long id) {
        String url = "sink-project/sun-detail/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-project-monitor/detail/{id}")
    @Operation(summary = "获取碳汇项目监测信息(详情调用)", description = "作者：czz")
    public ResponseEntity<Object> getSinkProjectMonitorDetailInfo(@PathVariable Long id) {
        String url = "sink-project-monitor/detail/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-project/design-detail/{id}")
    @Operation(summary = "碳汇设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> estimationSinkProject(@PathVariable Long id) {
        String url = "sink-project/design-detail/" + id;
        return coSearchService.redirectGetWithDefaultSinToken(url, null, null);
    }

    @GetMapping("/sink-knowledge-base/drop-down")
    @Operation(summary = "获取知识库下拉列表接口", description = "作者：czz")
    public ResponseEntity<Object> getDropDownOfSinkKnowledgeBase(
        @ApiParam(value = "类别编码", required = true) @RequestParam String categoryCode,
        @ApiParam(value = "知识库编码") @RequestParam(required = false) String baseCode
    ) {
        String url = "sink-knowledge-base/drop-down";
        Map<String, Object> map = new HashMap<>();
        map.put("categoryCode", categoryCode);
        map.put("baseCode", baseCode);
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }

    @GetMapping("/sink-project-files/clear-reply")
    @Operation(summary = "查询澄清回复列表", description = "作者：czz")
    public ResponseEntity<Object> getClearReply(
        @ApiParam(value = "碳汇项目文件ID字符串(逗号拼接)", required = true) @RequestParam String ids
    ) {
        String url = "sink-project-files/clear-reply";
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return coSearchService.redirectGetWithDefaultSinToken(url, map, null);
    }
}
