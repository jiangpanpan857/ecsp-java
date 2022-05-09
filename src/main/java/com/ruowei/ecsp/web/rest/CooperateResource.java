package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.service.CooperateService;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eco-cooperate")
@Transactional
@Api(tags = "碳天秤关联模块：碳汇用户")
public class CooperateResource {
    private final CooperateService cooperateService;

    public CooperateResource(CooperateService cooperateService) {
        this.cooperateService = cooperateService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "条件查询网站关联碳天秤账号候选项(系统管理员)", notes = "author: czz")
    public ResponseEntity<List<SinUserDTO>> getAllCooperateUsers(SinUserQM qm) {
        List<SinUserDTO> dtos = cooperateService.getAllCooperateUsers(qm);
        return ResponseEntity.ok().body(dtos);
    }
}
