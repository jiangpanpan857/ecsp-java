package com.ruowei.ecsp.web.rest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.HeadlineNews;
import com.ruowei.ecsp.domain.QHeadlineNews;
import com.ruowei.ecsp.repository.HeadlineNewsRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
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
@RequestMapping("/api/headline-news")
@Transactional
@Tag(name = "头条新闻管理")
public class HeadlineNewsResource {

    private final EcoUserService ecoUserService;
    private final SequenceService sequenceService;

    private final HeadlineNewsRepository headlineNewsRepository;
    private final WebsiteRepository websiteRepository;

    private final JPAQueryFactory queryFactory;
    private QHeadlineNews qHeadlineNews = QHeadlineNews.headlineNews;

    public HeadlineNewsResource(EcoUserService ecoUserService, SequenceService sequenceService, HeadlineNewsRepository headlineNewsRepository, WebsiteRepository websiteRepository, JPAQueryFactory queryFactory) {
        this.ecoUserService = ecoUserService;
        this.sequenceService = sequenceService;
        this.headlineNewsRepository = headlineNewsRepository;
        this.websiteRepository = websiteRepository;
        this.queryFactory = queryFactory;
    }


    @PostMapping("")
    @Operation(summary = "新增头条新闻", description = "author: czz")
    public ResponseEntity<String> createNews(@RequestBody HeadlineNews headlineNews) {
        // assert
        AssertUtil.notNullThrow(headlineNews.getId(), "新增失败!", "新增时，id需为空!");
        UserModel userModel = ecoUserService.getUserModel();
        AssertUtil.thenThrow(headlineNewsRepository.existsByTitleAndWebsiteId(headlineNews.getTitle(), userModel.getWebsiteId()), "新增失败!", "新增时，title不能重复!");
        // get set: sequence, websiteId, createTime, status
        Integer sequence = sequenceService.newSequence("headline_news");
        headlineNews.setStatus("未发布");
        headlineNews.setAddTime(ZonedDateTime.now());
        headlineNews.setWebsiteId(userModel.getWebsiteId());
        headlineNews.setSequence(sequence);
        headlineNewsRepository.save(headlineNews);
        return ResponseEntity.ok().body("新增成功!");
    }

    @PostMapping("/publish/{id}")
    @Operation(summary = "发布或撤销指定id的头条新闻", description = "author: czz")
    public ResponseEntity<String> publishNews(@PathVariable Long id, String status) {
        AssertUtil.falseThrow("未发布".equals(status) || "已发布".equals(status), "修改发布状态失败!", "发布状态输入错误!");
        HeadlineNews news = StreamUtil.optionalValue(headlineNewsRepository.findById(id), "修改发布状态失败!", "不存在指定id下的头条新闻!");
        news.setStatus(status);
        headlineNewsRepository.save(news);
        return ResponseEntity.ok().body("修改发布状态成功!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改头条新闻", description = "author: czz")
    public ResponseEntity<String> updateNews(@PathVariable(value = "id") final Long id, @RequestBody HeadlineNews headlineNews) {
        AssertUtil.nullThrow(id, "修改失败!", "修改时，id不能为空!");
        HeadlineNews oldNews = StreamUtil.optionalValue(headlineNewsRepository.findById(id), "修改失败!", "不存在指定id下的头条新闻!");
        AssertUtil.thenThrow(
            headlineNewsRepository.existsByTitleAndWebsiteIdAndIdNot(headlineNews.getTitle(), oldNews.getWebsiteId(), headlineNews.getId()),
            "修改失败!",
            "修改时，title不能与其他头条新闻重复!"
        );
        headlineNews.setAddTime(oldNews.getAddTime());
        headlineNews.setStatus(oldNews.getStatus());
        headlineNews.setWebsiteId(oldNews.getWebsiteId());
        headlineNewsRepository.save(headlineNews);
        return ResponseEntity.ok().body("修改成功!");
    }

    @GetMapping("")
    @Operation(summary = "条件查头条新闻列表", description = "author: czz")
    public ResponseEntity<List<HeadlineNews>> getAllNews(NewsVM newsVM,
                                                         @PageableDefault(sort = {"sequence"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return pageAll(vmPredicate(newsVM), pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "获取允许访客访问的头条新闻列表", description = "author: czz")
    public ResponseEntity<List<HeadlineNews>> getAllVisitorPermitNews(NewsVM newsVM,
                                                                      @PageableDefault(sort = {"sequence"}, direction = Sort.Direction.ASC) Pageable pageable) {
        newsVM.setStatus("已发布"); // 只查已发布的头条新闻
        return pageAll(vmPredicate(newsVM), pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取指定ID头条新闻详情", description = "author: czz")
    public ResponseEntity<HeadlineNews> getNews(@PathVariable Long id) {
        Optional<HeadlineNews> news = headlineNewsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(news);
    }

    @PostMapping("/sort")
    @Operation(summary = "排序头条新闻", description = "author: czz")
    public ResponseEntity<String> sortNews(@RequestBody List<Long> ids) {
        AssertUtil.nullThrow(ids, "排序失败!", "排序时，ids不能为空!");
        List<HeadlineNews> news = headlineNewsRepository.findAllByIdIn(ids);
        sequenceService.reSequence(ids, news);
        headlineNewsRepository.saveAll(news);
        return ResponseEntity.ok().body("排序成功!");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除指定id的头条新闻", description = "author: czz")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        headlineNewsRepository.deleteById(id);
        return ResponseEntity.ok().body("删除成功!");
    }

    private BooleanBuilder vmPredicate(NewsVM newsVM) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qHeadlineNews.title::contains, newsVM.getTitle())
            .notEmptyAnd(qHeadlineNews.status::eq, newsVM.getStatus())
            .build();
        Long websiteId = ecoUserService.getWebsiteId(newsVM.getDomain());
        return builder.and(qHeadlineNews.websiteId.eq(websiteId));
    }

    private ResponseEntity<List<HeadlineNews>> pageAll(BooleanBuilder builder, Pageable pageable) {
        Page<HeadlineNews> page = headlineNewsRepository.findAll(builder, pageable);
        return PageUtil.pageReturn(page);
    }
}
