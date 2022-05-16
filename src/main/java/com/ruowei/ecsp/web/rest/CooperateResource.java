package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.service.CoSearchService;
import com.ruowei.ecsp.service.CooperateService;
import com.ruowei.ecsp.util.RestTemplateUtil;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eco-cooperate")
@Transactional
@Tag(name = "碳天秤关联模块：碳汇用户")
public class CooperateResource {
    private final CooperateService cooperateService;
    private final CoSearchService coSearchService;

    public CooperateResource(CooperateService cooperateService, CoSearchService coSearchService) {
        this.cooperateService = cooperateService;
        this.coSearchService = coSearchService;
    }

    @GetMapping("/users")
    @Operation(summary = "条件查询网站关联碳天秤账号候选项(系统管理员)", description = "author: czz")
    public ResponseEntity<List<SinUserDTO>> getAllCooperateUsers(SinUserQM qm) {
        List<SinUserDTO> dtos = cooperateService.getAllCooperateUsers(qm);
        return ResponseEntity.ok().body(dtos);
    }


    @PostMapping("/sink-project-file/download")
    @Operation(summary = "下载碳汇项目文件接口", description = "作者：czz")
    public ResponseEntity<Resource> downloadSinkProjectFile(@RequestParam String url) {
        return cooperateService.downloadSinkProjectFile(url);
    }

    @GetMapping("/sink-project/search")
    @Operation(summary = "关联业主相关的get请求", description = "作者：czz")
    public ResponseEntity<Object> getAllCooperateUsers(String url) {
        String token = coSearchService.getCurrentSiteToken();
        return ResponseEntity.ok().body(RestTemplateUtil.getExchangeObject(url, token));
    }
}
