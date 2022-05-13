package com.ruowei.ecsp.web.rest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.EcoResource;
import com.ruowei.ecsp.domain.QEcoResource;
import com.ruowei.ecsp.repository.EcoResourceRepository;
import com.ruowei.ecsp.service.EcoUserService;
import com.ruowei.ecsp.service.OptionalBooleanBuilder;
import com.ruowei.ecsp.service.SequenceService;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.vm.NewsVM;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eco-resource")
@Transactional
@Tag(name = "生态资源管理")
public class EcoReResource {

    private final EcoResourceRepository ecoResourceRepository;
    private final EcoUserService ecoUserService;
    private final SequenceService sequenceService;

    private final JPAQueryFactory queryFactory;

    private QEcoResource qER = QEcoResource.ecoResource;

    public EcoReResource(EcoResourceRepository ecoResourceRepository, EcoUserService ecoUserService, SequenceService sequenceService, JPAQueryFactory queryFactory) {
        this.ecoResourceRepository = ecoResourceRepository;
        this.ecoUserService = ecoUserService;
        this.sequenceService = sequenceService;
        this.queryFactory = queryFactory;
    }


    @PostMapping("")
    @Operation(summary = "新增生态资源", description = "author: czz")
    public ResponseEntity<String> createNews(@RequestBody EcoResource ecoResource) {
        Integer sequence = sequenceService.newSequence("eco_resource");
        Long websiteId = ecoUserService.getWebsiteId(null);
        AssertUtil.notNullThrow(ecoResource.getId(), "新增失败!", "新增时，id需为空!");
        AssertUtil.thenThrow(ecoResourceRepository.existsByTitleAndWebsiteId(ecoResource.getTitle(), websiteId), "新增失败!", "新增时，title不能重复!");
        ecoResource.setStatus("未发布");
        ecoResource.setAddTime(ZonedDateTime.now());
        ecoResource.setWebsiteId(websiteId);
        ecoResource.setSequence(sequence);
        ecoResourceRepository.save(ecoResource);
        return ResponseEntity.ok().body("新增成功!");
    }

    @PostMapping("/publish/{id}")
    @Operation(summary = "发布或撤销指定id的生态资源", description = "author: czz")
    public ResponseEntity<String> publishNews(@PathVariable Long id, String status) {
        AssertUtil.falseThrow("未发布".equals(status) || "已发布".equals(status), "修改发布状态失败!", "发布状态输入错误!");
        EcoResource ecoResource = StreamUtil.optionalValue(ecoResourceRepository.findById(id), "修改发布状态失败!", "不存在指定id下的生态资源!");
        ecoResource.setStatus(status);
        ecoResourceRepository.save(ecoResource);
        return ResponseEntity.ok().body("修改发布状态成功!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改生态资源", description = "author: czz")
    public ResponseEntity<String> updateNews(@PathVariable(value = "id") final Long id, @RequestBody EcoResource ecoResource) {
        AssertUtil.nullThrow(id, "修改失败!", "修改时，id不能为空!");
        EcoResource oldResource = StreamUtil.optionalValue(ecoResourceRepository.findById(id), "修改失败!", "不存在指定id下的生态资源!");
        AssertUtil.thenThrow(
            ecoResourceRepository.existsByTitleAndWebsiteIdAndIdNot(ecoResource.getTitle(), oldResource.getWebsiteId(), ecoResource.getId()),
            "修改失败!",
            "修改时，title不能与其他生态资源重复!"
        );
        ecoResource.setAddTime(oldResource.getAddTime());
        ecoResource.setStatus(oldResource.getStatus());
        ecoResource.setWebsiteId(oldResource.getWebsiteId());
        ecoResourceRepository.save(ecoResource);
        return ResponseEntity.ok().body("修改成功!");
    }

    @GetMapping("")
    @Operation(summary = "条件查生态资源列表(网站管理员)", description = "author: czz")
    public ResponseEntity<List<EcoResource>> getAllNews(NewsVM newsVM,
                                                        @PageableDefault(sort = {"sequence"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return pageAll(vmPredicate(newsVM), pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "条件查生态资源列表(访客)", description = "author: czz")
    public ResponseEntity<List<EcoResource>> getAllPermittedNews(NewsVM newsVM,
                                                                 @PageableDefault(sort = {"sequence"}, direction = Sort.Direction.ASC) Pageable pageable) {
        newsVM.setStatus("已发布"); // 只查已发布的
        return pageAll(vmPredicate(newsVM), pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取指定ID生态资源详情", description = "author: czz")
    public ResponseEntity<EcoResource> getNews(@PathVariable Long id) {
        Optional<EcoResource> optional = ecoResourceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optional);
    }

    @PostMapping("/sort")
    @Operation(summary = "排序生态资源", description = "author: czz")
    public ResponseEntity<String> sortNews(@RequestBody List<Long> ids) {
        AssertUtil.nullThrow(ids, "排序失败!", "排序时，ids不能为空!");
        List<EcoResource> ecoResources = ecoResourceRepository.findAllByIdIn(ids);
        EcoResource[] array = ecoResources.toArray(new EcoResource[0]);
        sequenceService.reSequence(ids, array);
        ecoResourceRepository.saveAll(ecoResources);
        return ResponseEntity.ok().body("排序成功!");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除指定id的生态资源", description = "author: czz")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        ecoResourceRepository.deleteById(id);
        return ResponseEntity.ok().body("删除成功!");
    }

    private BooleanBuilder vmPredicate(NewsVM newsVM) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qER.title::contains, newsVM.getTitle())
            .notEmptyAnd(qER.status::eq, newsVM.getStatus())
            .build();
        Long websiteId = ecoUserService.getWebsiteId(newsVM.getDomain());
        return builder.and(qER.websiteId.eq(websiteId));
    }

    private ResponseEntity<List<EcoResource>> pageAll(BooleanBuilder builder, Pageable pageable) {
        Page<EcoResource> page = ecoResourceRepository.findAll(builder, pageable);
        return PageUtil.pageReturn(page);
    }
}
