package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.ruowei.ecsp.domain.QWebsite;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.WebsiteDetailDTO;
import com.ruowei.ecsp.web.rest.mapper.WebsiteDetailDTOMapper;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import com.ruowei.ecsp.web.rest.qm.WebsiteListQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class WebsiteService {
    private final WebsiteRepository websiteRepository;

    private final CoSearchService coSearchService;
    private final MethodologyService methodologyService;


    private QWebsite qW = QWebsite.website;

    public WebsiteService(WebsiteRepository websiteRepository, MethodologyService methodologyService, WebsiteDetailDTOMapper websiteDetailDTOMapper, CoSearchService coSearchService) {
        this.websiteRepository = websiteRepository;
        this.methodologyService = methodologyService;
        this.coSearchService = coSearchService;
    }

    public void createWebsite(Website site) {
        site.setAddTime(ZonedDateTime.now());
        assertWebsiteThenSave(site);
    }

    public void updateWebsite(Website site) {
        assertWebsiteThenSave(site);
    }

    public ResponseEntity<List<WebsiteDetailDTO>> getAllWebsiteDetailDTOS(WebsiteListQM qm,
                                                                          Pageable pageable) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qW.domain::contains, qm.getDomain())
            .notEmptyAnd(qW.name::contains, qm.getName())
            .notEmptyAnd(qW.organizationName::contains, qm.getOrganizationName())
            .build();
        Page<Website> page = websiteRepository.findAll(builder, pageable);
        List<WebsiteDetailDTO> dtos = StreamUtil.collectV(
            page.getContent(),
            this::toWebsiteDetailDTO
        );
        return PageUtil.pageReturn(page, dtos);
    }

    public Website getWebsiteByDomain(@NotNull String domain) {
        return StreamUtil.optionalValue(websiteRepository.findByDomain(domain), "获取失败！", "该域名不存在");
    }

    private void assertWebsiteThenSave(Website site) {
        if (site.getId() == null) {
            AssertUtil.thenThrow(websiteRepository.existsByDomain(site.getDomain()), "新增网站失败！", "该域名已被占用");
            AssertUtil.thenThrow(websiteRepository.existsByName(site.getName()), "新增网站失败！", "该网站名已被占用");
        } else {
            AssertUtil.thenThrow(websiteRepository.existsByDomainAndIdNot(site.getDomain(), site.getId()), "修改网站失败！", "该域名已被占用");
            AssertUtil.thenThrow(websiteRepository.existsByNameAndIdNot(site.getName(), site.getId()), "修改网站失败！", "该网站名已被占用");
        }
        addSiteSinToken(site);
        AssertUtil.nullThrow(site.getSinkToken(), "网站关联业主失败", "请核查碳天秤服务是否运行正常");
        websiteRepository.save(site);
    }

    public WebsiteDetailDTO toWebsiteDetailDTO(Website website) {
        WebsiteDetailDTO websiteDetailDTO = new WebsiteDetailDTO(website);
        websiteDetailDTO.setMethodologyNames(methodologyService.methodNamesJoinedStrBySite(website));
        websiteDetailDTO.setCarbonLibraAccountName(getSiteCoAccountLogin(website));
        return websiteDetailDTO;
    }

    public String getSiteCoAccountLogin(Website site) {
        try {
            SinUserQM qm = new SinUserQM();
            qm.setId(Long.valueOf(site.getCarbonLibraAccount()));
            return coSearchService.getCarbonLibraAccount(qm).get(0).getLogin();
        } catch (Exception e) {
            return "";
        }
    }

    public void addSiteSinToken(Website site) {
        log.info("addSiteSinToken: {}", site.getId());
        String sysUserIdStr = site.getCarbonLibraAccount();
        String url = "permit/token";
        Map<String, Object> qm = new HashMap<>();
        qm.put("sysUserId", Long.valueOf(sysUserIdStr));
        String sinkToken = coSearchService.getSiteRelatedSinToken(url, qm, null);
        site.setSinkToken(sinkToken);
    }

}
