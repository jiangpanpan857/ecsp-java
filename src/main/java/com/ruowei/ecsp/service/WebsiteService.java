package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.ruowei.ecsp.domain.QWebsite;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.web.rest.qm.WebsiteListQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class WebsiteService {
    private final WebsiteRepository websiteRepository;

    private QWebsite qWebsite = QWebsite.website;

    public WebsiteService(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    public void createWebsite(Website site) {
        assertWebsite(site);
        site.setAddTime(ZonedDateTime.now());
        websiteRepository.save(site);
    }

    public void updateWebsite(Website site) {
        assertWebsite(site);
        websiteRepository.save(site);
    }

    public ResponseEntity<List<Website>> getAllWebsites(WebsiteListQM qm,
                                                        Pageable pageable) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qWebsite.domain::contains, qm.getDomain())
            .notEmptyAnd(qWebsite.name::contains, qm.getName())
            .notEmptyAnd(qWebsite.organizationName::contains, qm.getOrganizationName())
            .build();
        Page<Website> page = websiteRepository.findAll(builder, pageable);
        return PageUtil.pageReturn(page);
    }

    private void assertWebsite(Website site) {
        if (site.getId() == null) {
            AssertUtil.thenThrow(websiteRepository.existsByDomain(site.getDomain()), "新增网站失败！",  "该域名已被占用");
        } else {
            AssertUtil.thenThrow(websiteRepository.existsByDomainAndIdNot(site.getDomain(), site.getId()), "修改网站失败！",  "该域名已被占用");
        }
    }
}
