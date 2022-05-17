package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.service.CoSearchService;
import com.ruowei.ecsp.web.rest.qm.SinProjectQM;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Transactional
@Tag(name = "请求转发接口管理")
public class CoSearchResource {

    private final CoSearchService coSearchService;

    public CoSearchResource(CoSearchService coSearchService) {
        this.coSearchService = coSearchService;
    }


    @GetMapping("/dicts/byCatagory")
    @Operation(summary = "按分类获取数据字典列表")
    public ResponseEntity<Object> getAllDictsByCatagory(@ApiParam(value = "分类编码", required = true) @RequestParam String catagory) {
        Map<String, Object> map = new HashMap<>();
        map.put("catagory", catagory);
        return coSearchService.redirectUrl("dicts/byCatagory", map, null);
    }

    @GetMapping("/district/only-province")
    @Operation(summary = "获取省下拉列表接口", description = "作者：czz")
    public ResponseEntity<Object> getProvinceDistricts() {
        String url = "district/only-province";
        return coSearchService.redirectUrl(url, null, null);
    }

    @GetMapping("/sink-projects")
    @Operation(summary = "获取碳汇项目列表接口", description = "作者：czz")
    public ResponseEntity<Object> getAllSinkProjects(
        SinProjectQM qm,
        Pageable pageable
    ) {
        String url = "sink-projects";
        return coSearchService.redirectUrl(url, qm, pageable);
    }

    @GetMapping("/sink-project/{id}")
    @Operation(summary = "获取碳汇项目信息接口", description = "作者: czz")
    public ResponseEntity<Object> getSinkProjectDetail(@PathVariable Long id) {
        String url = "sink-project/" + id;
        return coSearchService.redirectUrl(url, null, null);
    }

    @GetMapping("/evaluation-application/{id}")
    @Operation(summary = "获取碳汇项目申请内容接口", description = "author: czz</br>")
    public ResponseEntity<Object> evaluationApplication(@PathVariable Long id) {
        String url = "evaluation-application/" + id;
        return coSearchService.redirectUrl(url, null, null);
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
        return coSearchService.redirectUrl(url, map, null);
    }

    @GetMapping("/sink-project-red-check/detail/{id}")
    @Operation(summary = "获取碳汇项目减排量核查信息(详情调用)", description = "作者：czz")
    public ResponseEntity<Object> getSinkProjectRedCheckDetailInfo(@PathVariable Long id) {
        String url = "sink-project-red-check/detail/" + id;
        return coSearchService.redirectUrl(url, null, null);
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
        return coSearchService.redirectUrl(url, map, null);
    }

    @GetMapping("/sink-project/grass-detail/{id}")
    @Operation(summary = "草地设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> estimationSinkGrass(@PathVariable Long id) {
        String url = "sink-project/grass-detail/" + id;
        return coSearchService.redirectUrl(url, null, null);
    }

    @GetMapping("/sink-project/grass-monitor/detail")
    @Operation(summary = "获取草地监测信息", description = "author: czz</br>")
    public ResponseEntity<Object> sinGrassMonitor(@RequestParam Long projectId, @RequestParam Integer serialNum) {
        String url = "sink-project/grass-monitor/detail";
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("serialNum", serialNum);
        return coSearchService.redirectUrl(url, map, null);
    }

    @GetMapping("/sink-project/sun-monitor-detail/")
    @Operation(summary = "光伏监测信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> getSunDesignVM(@NotNull @RequestParam Long projectId, @NotNull @RequestParam Integer serialNum) {
        String url = "sink-project/sun-monitor-detail/";
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("serialNum", serialNum);
        return coSearchService.redirectUrl(url, map, null);
    }

    @GetMapping("/sink-project/sun-detail/{id}")
    @Operation(summary = "光伏设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> getSunDesignVM(@PathVariable Long id) {
        String url = "sink-project/sun-detail/" + id;
        return coSearchService.redirectUrl(url, null, null);
    }

    @GetMapping("/sink-project-monitor/detail/{id}")
    @Operation(summary = "获取碳汇项目监测信息(详情调用)", description = "作者：czz")
    public ResponseEntity<Object> getSinkProjectMonitorDetailInfo(@PathVariable Long id) {
        String url = "sink-project-monitor/detail/" + id;
        return coSearchService.redirectUrl(url, null, null);
    }

    @GetMapping("/sink-project/design-detail/{id}")
    @Operation(summary = "碳汇设计信息获取", description = "author: czz</br>")
    public ResponseEntity<Object> estimationSinkProject(@PathVariable Long id) {
        String url = "sink-project/design-detail/" + id;
        return coSearchService.redirectUrl(url, null, null);
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
        return coSearchService.redirectUrl(url, map, null);
    }

    @GetMapping("/sink-project-files/clear-reply")
    @Operation(summary = "查询澄清回复列表", description = "作者：czz")
    public ResponseEntity<Object> getClearReply(
        @ApiParam(value = "碳汇项目文件ID字符串(逗号拼接)", required = true) @RequestParam String ids
    ) {
        String url = "sink-project-files/clear-reply";
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return coSearchService.redirectUrl(url, map, null);
    }
}
