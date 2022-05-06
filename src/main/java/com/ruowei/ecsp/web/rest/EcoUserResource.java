package com.ruowei.ecsp.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eco-user")
@Transactional
@Api(tags = "生态用户管理")
public class EcoUserResource {

    @PostMapping("")
    @ApiOperation(value = "新增生态系统网站管理员", notes = "author: czz")
    public ResponseEntity<String> createEcoUser() {
        return ResponseEntity.ok().body("success");
    }
}
