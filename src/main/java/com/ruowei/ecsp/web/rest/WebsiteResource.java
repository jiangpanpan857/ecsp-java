package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.WebsiteService;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.WebsiteDetailDTO;
import com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO;
import com.ruowei.ecsp.web.rest.dto.WebsiteVisDTO;
import com.ruowei.ecsp.web.rest.qm.WebsiteListQM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/website")
@Transactional
@Tag(name = "网站管理")
public class WebsiteResource {

    private final WebsiteService websiteService;
    private final WebsiteRepository websiteRepository;
    private final EcoUserRepository ecoUserRepository;

    public WebsiteResource(WebsiteService websiteService, WebsiteRepository websiteRepository, EcoUserRepository ecoUserRepository) {
        this.websiteService = websiteService;
        this.websiteRepository = websiteRepository;
        this.ecoUserRepository = ecoUserRepository;
    }

    @GetMapping("/down-list")
    @Operation(summary = "获取网站下拉列表（用户管理用）", description = "author: czz")
    public ResponseEntity<List<WebsiteDownListDTO>> getWebsiteDownList() {
        List<WebsiteDownListDTO> websiteDownList = websiteRepository.getWebsiteDownList();
        return ResponseEntity.ok().body(websiteDownList);
    }

    @PostMapping("")
    @Operation(summary = "新增网站", description = "author: czz")
    public ResponseEntity<String> createWebsite(@RequestBody Website site) {
        websiteService.createWebsite(site);
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("")
    @Operation(summary = "修改网站", description = "author: czz")
    public ResponseEntity<String> updateWebsite(@RequestBody Website site) {
        websiteService.updateWebsite(site);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取指定id网站详情(增加方法学名字段)", description = "author: czz")
    public ResponseEntity<WebsiteDetailDTO> getWebsiteDetail(@PathVariable Long id) {
        Website website = StreamUtil.optionalValue(websiteRepository.findById(id), "获取详情失败", "网站不存在");
        return ResponseEntity.ok().body(websiteService.toWebsiteDetailDTO(website));
    }

    @GetMapping("")
    @Operation(summary = "获取网站(系统管理员)", description = "author: czz")
    public ResponseEntity<List<WebsiteDetailDTO>> getAll(WebsiteListQM qm,
                                                         @PageableDefault(sort = "addTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return websiteService.getAllWebsiteDetailDTOS(qm, pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "获取网站(访客)", description = "author: czz")
    public ResponseEntity<WebsiteVisDTO> getPermitted(String domain) {
        WebsiteVisDTO dto = websiteRepository.getWebsiteVis(domain); // 只通过域名访问
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除网站", description = "author: czz")
    public ResponseEntity<String> deleteWebsite(@PathVariable Long id) {
        websiteRepository.deleteById(id);
        AssertUtil.thenThrow(ecoUserRepository.existsByWebsiteId(id), "删除失败", "网站下存在用户，不能删除");
        return ResponseEntity.ok().body("success");
    }
}
