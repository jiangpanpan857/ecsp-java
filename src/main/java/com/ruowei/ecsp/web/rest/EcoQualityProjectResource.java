package com.ruowei.ecsp.web.rest;

import com.querydsl.core.BooleanBuilder;
import com.ruowei.ecsp.domain.EcoQualityProject;
import com.ruowei.ecsp.domain.QEcoQualityProject;
import com.ruowei.ecsp.repository.EcoQualityProjectRepository;
import com.ruowei.ecsp.security.UserModel;
import com.ruowei.ecsp.service.EcoUserService;
import com.ruowei.ecsp.service.OptionalBooleanBuilder;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.qm.QuaProjectPermitQM;
import com.ruowei.ecsp.web.rest.qm.QualityProjectTableQM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eco-quality-project")
@Transactional
@Tag(name = "Eco优质项目管理")
public class EcoQualityProjectResource {

    private final EcoUserService ecoUserService;
    private final EcoQualityProjectRepository qualityProjectRepository;

    private QEcoQualityProject qEQP = QEcoQualityProject.ecoQualityProject;

    public EcoQualityProjectResource(EcoUserService ecoUserService, EcoQualityProjectRepository qualityProjectRepository) {
        this.ecoUserService = ecoUserService;
        this.qualityProjectRepository = qualityProjectRepository;
    }

    @PostMapping("")
    @Operation(summary = "新增优质项目", description = "author: czz")
    public ResponseEntity<String> createQualityProject(@RequestBody EcoQualityProject ecoQualityProject) {
        UserModel userModel = ecoUserService.currentUserModel();
        AssertUtil.notNullThrow(ecoQualityProject.getId(), "新增失败！", "新增时，id须为空！");
        AssertUtil.thenThrow(qualityProjectRepository.existsByNameAndWebsiteId(ecoQualityProject.getName(), userModel.getWebsiteId()), "新增失败!", "新增时，项目名称不能重复!");
        Instant now = Instant.now();
        LocalDate nowDate = LocalDate.now();
        ecoQualityProject.createDate(nowDate).createTime(now).status("未发布").websiteId(userModel.getWebsiteId());
        qualityProjectRepository.save(ecoQualityProject);
        return ResponseEntity.ok().body("新增成功");
    }

    @PostMapping("/publish/{id}")
    @Operation(summary = "发布或撤销指定id的优质项目", description = "author: czz")
    public ResponseEntity<String> publishQualityProject(@PathVariable Long id, String status) {
        AssertUtil.falseThrow("未发布".equals(status) || "已发布".equals(status), "修改发布状态失败!", "发布状态输入错误!");
        EcoQualityProject ecoQualityProject = StreamUtil.optionalValue(qualityProjectRepository.findById(id), "修改发布状态失败!", "不存在的优质项目!");
        ecoQualityProject.status(status);
        qualityProjectRepository.save(ecoQualityProject);
        return ResponseEntity.ok().body("修改发布状态成功!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改优质项目", description = "author: czz")
    public ResponseEntity<String> updateQualityProject(@PathVariable final Long id, @RequestBody EcoQualityProject ecoQualityProject) {
        UserModel userModel = ecoUserService.currentUserModel();
        AssertUtil.nullThrow(id, "修改失败!", "修改时，id不能为空!");
        EcoQualityProject qP_ = StreamUtil.optionalValue(qualityProjectRepository.findById(id), "修改失败!", "不存在的优质项目!");
        AssertUtil.thenThrow(
            qualityProjectRepository.existsByNameAndWebsiteIdAndIdNot(ecoQualityProject.getName(), userModel.getWebsiteId(), id),
            "修改失败!",
            "修改时，项目名称不能重复!"
        );
        ecoQualityProject.createDate(qP_.getCreateDate()).createTime(qP_.getCreateTime()).status(qP_.getStatus()).websiteId(userModel.getWebsiteId());
        qualityProjectRepository.save(ecoQualityProject);
        return ResponseEntity.ok().body("修改成功!");
    }

    @GetMapping("")
    @Operation(summary = "条件查询优质项目列表(网站管理员)", description = "author: czz")
    public ResponseEntity<List<EcoQualityProject>> getAllQualityProjects(
        QualityProjectTableQM qm,
        @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return pageAll(qmPredicate(qm), pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "条件查询优质项目列表(访客)", description = "author: czz")
    public ResponseEntity<List<EcoQualityProject>> getAllPermittedQualityProjects(
        QuaProjectPermitQM qm,
        @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 限制访客只能看到网站发布的项目
        qm.setStatus("已发布");
        BooleanBuilder predicate = qmPredicate(qm);
        BooleanBuilder permitPredicate = new OptionalBooleanBuilder()
            .notEmptyAnd(qEQP.preSink::goe, qm.getPreSinkStart())
            .notEmptyAnd(qEQP.preSink::loe, qm.getPreSinkEnd())
            .notEmptyAnd(qEQP.recordSink::goe, qm.getRecordSinkStart())
            .notEmptyAnd(qEQP.recordSink::loe, qm.getRecordSinkEnd())
            .notEmptyAnd(qEQP.createDate::goe, qm.getStartDate())
            .notEmptyAnd(qEQP.createDate::loe, qm.getEndDate())
            .build();
        return pageAll(predicate.and(permitPredicate), pageable);
    }

    @GetMapping("/permit/types")
    @Operation(summary = "获取此网站有的优质项目类型列表（访客）", description = "author: czz")
    public ResponseEntity<List<String>> getAllPermittedQualityProjectTypes(String domain) {
        Long websiteId = ecoUserService.getWebsiteIdByDomain(domain);
        List<String> types = qualityProjectRepository.getTypes(websiteId);
        return ResponseEntity.ok().body(types);
    }

    @GetMapping("/{id}")
    @Operation(summary = "优质项目详情", description = "author: czz")
    public ResponseEntity<EcoQualityProject> getQualityProject(@PathVariable Long id) {
        Optional<EcoQualityProject> qualityProject = qualityProjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qualityProject);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除指定优质项目", description = "author: czz")
    public ResponseEntity<Void> deleteQualityProject(@PathVariable Long id) {
        qualityProjectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private BooleanBuilder qmPredicate(QualityProjectTableQM qm) {
        BooleanBuilder predicate = new OptionalBooleanBuilder()
            .notEmptyAnd(qEQP.name::contains, qm.getName())
            .notEmptyAnd(qEQP.method::eq, qm.getMethod())
            .notEmptyAnd(qEQP.type::in, qm.getTypeList())
            .notEmptyAnd(qEQP.status::eq, qm.getStatus())
            .notEmptyAnd(qEQP.provinceName::in, qm.getProvinceNameList())
            .build();
        Long websiteId = ecoUserService.getWebsiteIdByDomain(qm.getDomain()); // 无论是否登录均可获取网站ID
        return predicate.and(qEQP.websiteId.eq(websiteId));
    }

    private ResponseEntity<List<EcoQualityProject>> pageAll(BooleanBuilder predicate, Pageable pageable) {
        Page<EcoQualityProject> page = qualityProjectRepository.findAll(predicate, pageable);
        return PageUtil.pageReturn(page);
    }
}
