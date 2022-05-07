package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.EcoUserService;
import com.ruowei.ecsp.web.rest.dto.EcoUserDTO;
import com.ruowei.ecsp.web.rest.qm.EcoUserQM;
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
@RequestMapping("/api/eco-user")
@Transactional
@Api(tags = "生态系统网站用户管理")
public class EcoUserResource {

    private final EcoUserRepository ecoUserRepository;
    private final WebsiteRepository websiteRepository;

    private final EcoUserService ecoUserService;

    public EcoUserResource(EcoUserRepository ecoUserRepository, WebsiteRepository websiteRepository, EcoUserService ecoUserService) {
        this.ecoUserRepository = ecoUserRepository;
        this.websiteRepository = websiteRepository;
        this.ecoUserService = ecoUserService;
    }


    @PostMapping("")
    @ApiOperation(value = "新增生态系统网站管理员", notes = "author: czz")
    public ResponseEntity<String> createEcoUser(@RequestBody EcoUser ecoUser) {
        ecoUserService.createEcoUser(ecoUser);
        return ResponseEntity.ok().body("success add");
    }

    @PutMapping("")
    @ApiOperation(value = "编辑修改生态系统网站管理员", notes = "author: czz")
    public ResponseEntity<String> editEcoUser(@RequestBody EcoUser ecoUser) {
        ecoUserService.updateEcoUser(ecoUser);
        return ResponseEntity.ok().body("success edit");
    }

    @GetMapping("/permit")
    @ApiOperation(value = "获取用户列表", notes = "author: czz")
    public ResponseEntity<List<EcoUserDTO>> getAll(EcoUserQM qm,
                                                   @PageableDefault(sort = "addTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ecoUserService.getEcoUsers(qm, pageable);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除生态系统网站管理员", notes = "author: czz")
    public ResponseEntity<String> deleteEcoUser(@PathVariable Long id) {
        ecoUserRepository.deleteById(id);
        return ResponseEntity.ok().body("success delete");
    }
}
