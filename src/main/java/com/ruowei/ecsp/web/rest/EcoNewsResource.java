package com.ruowei.ecsp.web.rest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.News;
import com.ruowei.ecsp.domain.QNews;
import com.ruowei.ecsp.repository.NewsRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
import com.ruowei.ecsp.service.EcoUserService;
import com.ruowei.ecsp.service.NewsProjectService;
import com.ruowei.ecsp.service.OptionalBooleanBuilder;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.NewsProjectDTO;
import com.ruowei.ecsp.web.rest.vm.NewsVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/eco-news")
@Transactional
@Tag(name = "生态政策资讯模块")
public class EcoNewsResource {
    private final NewsRepository newsRepository;
    private final WebsiteRepository websiteRepository;

    private final EcoUserService ecoUserService;
    private final NewsProjectService newsProjectService;

    private final JPAQueryFactory queryFactory;

    private QNews qN = QNews.news;

    public EcoNewsResource(NewsRepository newsRepository, WebsiteRepository websiteRepository, EcoUserService ecoUserService, NewsProjectService newsProjectService, JPAQueryFactory queryFactory) {
        this.newsRepository = newsRepository;
        this.websiteRepository = websiteRepository;
        this.ecoUserService = ecoUserService;
        this.newsProjectService = newsProjectService;
        this.queryFactory = queryFactory;
    }


    @PostMapping("")
    @Operation(summary = "新增政策资讯", description = "author: czz")
    public ResponseEntity<String> createNews(@RequestBody News news) {
        UserModel userModel = ecoUserService.getUserModel();
        AssertUtil.notNullThrow(news.getId(), "新增失败!", "新增时，id需为空!");
        AssertUtil.thenThrow(newsRepository.existsByTitleAndWebsiteId(news.getTitle(), userModel.getWebsiteId()), "新增失败!", "新增时，title不能重复!");
        Instant now = Instant.now();
        LocalDate nowDate = LocalDate.now();
        news.setStatus("未发布");
        news.setCreateDate(nowDate);
        news.setCreateTime(now);
        news.setWebsiteId(userModel.getWebsiteId());
        newsRepository.save(news);
        return ResponseEntity.ok().body("新增成功!");
    }

    @PostMapping("/publish/{id}")
    @Operation(summary = "发布或撤销指定id的政策资讯", description = "author: czz")
    public ResponseEntity<String> publishNews(@PathVariable Long id, String status) {
        AssertUtil.falseThrow("未发布".equals(status) || "已发布".equals(status), "修改发布状态失败!", "发布状态输入错误!");
        News news = StreamUtil.optionalValue(newsRepository.findById(id), "修改发布状态失败!", "不存在指定id下的政策资讯!");
        news.setStatus(status);
        newsRepository.save(news);
        return ResponseEntity.ok().body("修改发布状态成功!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改政策资讯", description = "author: czz")
    public ResponseEntity<String> updateNews(@PathVariable(value = "id") final Long id, @RequestBody News news) {
        AssertUtil.nullThrow(id, "修改失败!", "修改时，id不能为空!");
        News oldNews = StreamUtil.optionalValue(newsRepository.findById(id), "修改失败!", "不存在指定id下的政策资讯!");
        AssertUtil.thenThrow(
            newsRepository.existsByTitleAndWebsiteIdAndIdNot(news.getTitle(), oldNews.getWebsiteId(), news.getId()),
            "修改失败!",
            "修改时，title不能与其他政策资讯重复!"
        );
        news.setCreateTime(oldNews.getCreateTime());
        news.setCreateDate(oldNews.getCreateDate());
        news.setStatus(oldNews.getStatus());
        news.setWebsiteId(oldNews.getWebsiteId()); // 修改只能是固定的站点
        newsRepository.save(news);
        return ResponseEntity.ok().body("修改成功!");
    }

    @GetMapping("")
    @Operation(summary = "条件查政策资讯列表(网站管理员用)", description = "author: czz")
    public ResponseEntity<List<News>> getAllNews(NewsVM newsVM,
                                                 @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return pageAll(newsVM, pageable);
    }

    @GetMapping("/permit")
    @Operation(summary = "条件查政策资讯列表(访客用)", description = "author: czz")
    public ResponseEntity<List<News>> getAllPermitNews(NewsVM newsVM,
                                                       @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        newsVM.setStatus("已发布");  // 限制只能访问已发布的政策资讯
        return pageAll(newsVM, pageable);
    }

    @GetMapping("/permit/fuzzy")
    @Operation(summary = "模糊查政策资讯，项目(访客用)", description = "author: czz")
    public ResponseEntity<List<NewsProjectDTO>> searchBy(String title) {
        return newsProjectService.searchBy(title);
    }

    @GetMapping("/permit/{id}")
    @Operation(summary = "获取指定ID政策资讯详情", description = "author: czz")
    public ResponseEntity<News> getNews(@PathVariable Long id) {
        Optional<News> news = newsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(news);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除指定id的政策资讯", description = "author: czz")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        newsRepository.deleteById(id);
        return ResponseEntity.ok().body("删除成功!");
    }


    private BooleanBuilder vmPredicate(NewsVM newsVM) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qN.title::contains, newsVM.getTitle())
            .notEmptyAnd(qN.status::eq, newsVM.getStatus())
            .notEmptyAnd(qN.type::eq, newsVM.getType())
            .build();
        Long websiteId = ecoUserService.getWebsiteId(newsVM.getDomain());
        return builder.and(qN.websiteId.eq(websiteId));
    }

    private ResponseEntity<List<News>> pageAll(NewsVM newsVM, Pageable pageable) {
        BooleanBuilder builder = vmPredicate(newsVM);
        JPAQuery<News> query = queryFactory.selectFrom(qN).where(builder).orderBy(qN.createTime.desc());
        // totalCount
        long count = query.stream().count();
        List<News> newsList = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return PageUtil.pageReturn(newsList, pageable, count);
    }
}
