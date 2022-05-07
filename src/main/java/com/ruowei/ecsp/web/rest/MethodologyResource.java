package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.Methodology;
import com.ruowei.ecsp.repository.MethodologyRepository;
import com.ruowei.ecsp.service.MethodologyService;
import com.ruowei.ecsp.web.rest.qm.MethodologyQM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/methodology")
@Transactional
@Api(tags = "方法学管理")
public class MethodologyResource {
    private final MethodologyService methodologyService;

    private final MethodologyRepository methodologyRepository;

    public MethodologyResource(MethodologyService methodologyService, MethodologyRepository methodologyRepository) {
        this.methodologyService = methodologyService;
        this.methodologyRepository = methodologyRepository;
    }

    @PostMapping("")
    @ApiOperation(value = "新增方法学", notes = "author: czz")
    public ResponseEntity<String> create(Methodology methodology) {
        methodologyService.createMethodology(methodology);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("")
    @ApiOperation(value = "编辑指定方法学", notes = "author: czz")
    public ResponseEntity<String> update(Methodology methodology) {
        methodologyService.updateMethodology(methodology);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/permit")
    @ApiOperation(value = "获取所有方法学(无需登录)", notes = "author: czz")
    public ResponseEntity<List<Methodology>> getAll(MethodologyQM qm,
                                                               @PageableDefault(sort = { "addTime" }, direction = Sort.Direction.DESC) Pageable  pageable) {
        return methodologyService.searchAll(qm, pageable);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除指定方法学", notes = "author: czz")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        methodologyRepository.deleteById(id);
        return ResponseEntity.ok("OK");
    }
}

