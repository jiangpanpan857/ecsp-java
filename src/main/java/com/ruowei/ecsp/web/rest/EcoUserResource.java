package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.service.EcoUserService;
import com.ruowei.ecsp.web.rest.dto.EcoUserDTO;
import com.ruowei.ecsp.web.rest.qm.EcoUserQM;
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
@RequestMapping("/api/eco-user")
@Transactional
@Tag(name = "生态系统网站用户管理")
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
    @Operation(summary = "新增生态系统网站管理员", description = "author: czz")
    public ResponseEntity<String> createEcoUser(@RequestBody EcoUser ecoUser) {
        ecoUserService.createEcoUser(ecoUser);
        return ResponseEntity.ok().body("success add");
    }

    @PutMapping("")
    @Operation(summary = "编辑修改生态系统网站管理员", description = "author: czz")
    public ResponseEntity<String> editEcoUser(@RequestBody EcoUser ecoUser) {
        ecoUserService.updateEcoUser(ecoUser);
        return ResponseEntity.ok().body("success edit");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询生态系统网站管理员详情", description = "author: czz")
    public ResponseEntity<EcoUserDTO> getEcoUser(@PathVariable Long id) {
        return ecoUserService.getEcoUser(id);
    }

    @GetMapping("")
    @Operation(summary = "获取用户列表", description = "author: czz")
    public ResponseEntity<List<EcoUserDTO>> getAll(EcoUserQM qm,
                                                   @PageableDefault(sort = "addTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ecoUserService.getEcoUsers(qm, pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除生态系统网站管理员", description = "author: czz")
    public ResponseEntity<String> deleteEcoUser(@PathVariable Long id) {
        ecoUserRepository.deleteById(id);
        return ResponseEntity.ok().body("success delete");
    }

    @PostMapping("/change-password")
    @Operation(summary = "修改自己密码", description = "author: czz")
    public ResponseEntity<String> changePassword(@RequestParam String password) {
        ecoUserService.updatePassword(null, password);
        return ResponseEntity.ok().body("success change password");
    }

    @PostMapping("/change-password/{id}")
    @Operation(summary = "Admin修改其他人密码", description = "author: czz")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String password) {
        ecoUserService.updatePassword(id, password);
        return ResponseEntity.ok().body("success change password");
    }
}
