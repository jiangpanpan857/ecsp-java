package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.WebsiteService;
import com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO;
import com.ruowei.ecsp.web.rest.qm.WebsiteListQM;
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
@RequestMapping("/api/website")
@Transactional
@Api(tags = "网站管理")
public class WebsiteResource {

    private final WebsiteService websiteService;
    private final WebsiteRepository websiteRepository;

    public WebsiteResource(WebsiteService websiteService, WebsiteRepository websiteRepository) {
        this.websiteService = websiteService;
        this.websiteRepository = websiteRepository;
    }

    @GetMapping("/down-list")
    @ApiOperation(value = "获取网站下拉列表（用户管理用）", notes = "author: czz")
    public ResponseEntity<List<WebsiteDownListDTO>> getWebsiteDownList() {
        List<WebsiteDownListDTO> websiteDownList = websiteRepository.getWebsiteDownList();
        return ResponseEntity.ok().body(websiteDownList);
    }

    @PostMapping("")
    @ApiOperation(value = "新增网站", notes = "author: czz")
    public ResponseEntity<String> createWebsite(Website site) {
        websiteService.createWebsite(site);
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("")
    @ApiOperation(value = "修改网站", notes = "author: czz")
    public ResponseEntity<String> updateWebsite(Website site) {
        websiteService.updateWebsite(site);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("")
    @ApiOperation(value = "获取网站", notes = "author: czz")
    public ResponseEntity<List<Website>> getAll(WebsiteListQM qm,
                                                @PageableDefault(sort = "addTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return websiteService.getAllWebsites(qm, pageable);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除网站", notes = "author: czz")
    public ResponseEntity<String> deleteWebsite(@PathVariable Long id) {
        websiteRepository.deleteById(id);
        return ResponseEntity.ok().body("success");
    }
}
