package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.Methodology;
import com.ruowei.ecsp.repository.MethodologyRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.MethodologyService;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.web.rest.dto.MethodologyPermitDTO;
import com.ruowei.ecsp.web.rest.qm.MethodologyQM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/methodology")
@Transactional
@Tag(name = "方法学管理")
public class MethodologyResource {
    private final MethodologyService methodologyService;

    private final MethodologyRepository methodologyRepository;
    private final WebsiteRepository websiteRepository;


    public MethodologyResource(MethodologyService methodologyService, MethodologyRepository methodologyRepository, WebsiteRepository websiteRepository) {
        this.methodologyService = methodologyService;
        this.methodologyRepository = methodologyRepository;
        this.websiteRepository = websiteRepository;
    }

    @PostMapping("")
    @Operation(summary = "新增方法学", description = "author: czz")
    public ResponseEntity<String> create(@RequestBody Methodology methodology) {
        methodologyService.createMethodology(methodology);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("")
    @Operation(summary = "编辑指定方法学", description = "author: czz")
    public ResponseEntity<String> update(@RequestBody Methodology methodology) {
        methodologyService.updateMethodology(methodology);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取指定id方法学详情", description = "author: czz")
    public ResponseEntity<Methodology> get(@PathVariable Long id) {
        Optional<Methodology> optional = methodologyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optional);
    }

    @GetMapping("")
    @Operation(summary = "条件查询方法学(系统管理员用)", description = "author: czz")
    public ResponseEntity<List<Methodology>> getAll(MethodologyQM qm,
                                                    @PageableDefault(sort = {"addTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return methodologyService.searchAllMethodology(qm, pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "域名查询方法学(访客用)", description = "author: czz")
    public ResponseEntity<List<MethodologyPermitDTO>> getAllPermit(String domain) {
        return methodologyService.allMethodologyPermitByDomain(domain);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除指定方法学", description = "author: czz")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String idStr = StringUtil.castAppendComma(id);   // get id str
        AssertUtil.thenThrow(websiteRepository.existsByMethodologyIdsContains(idStr), "删除失败", "该方法学已被使用!");
        methodologyRepository.deleteById(id);
        return ResponseEntity.ok("OK");
    }
}

