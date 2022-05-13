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
import com.ruowei.ecsp.web.rest.qm.WebsiteListQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class WebsiteService {
    private final WebsiteRepository websiteRepository;

    private final CooperateService cooperateService;
    private final MethodologyService methodologyService;

    private final WebsiteDetailDTOMapper websiteDetailDTOMapper;

    private QWebsite qW = QWebsite.website;

    public WebsiteService(WebsiteRepository websiteRepository, CooperateService cooperateService, MethodologyService methodologyService, WebsiteDetailDTOMapper websiteDetailDTOMapper) {
        this.websiteRepository = websiteRepository;
        this.cooperateService = cooperateService;
        this.methodologyService = methodologyService;
        this.websiteDetailDTOMapper = websiteDetailDTOMapper;
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
        } else {
            AssertUtil.thenThrow(websiteRepository.existsByDomainAndIdNot(site.getDomain(), site.getId()), "修改网站失败！", "该域名已被占用");
        }
        cooperateService.addSiteToken(site);
        websiteRepository.save(site);
    }

    public WebsiteDetailDTO toWebsiteDetailDTO(Website website) {
        WebsiteDetailDTO websiteDetailDTO = websiteDetailDTOMapper.toDto(website);
        websiteDetailDTO.setMethodologyNames(methodologyService.getSiteMethodNamesStr(website));
        websiteDetailDTO.setCarbonLibraAccountName(cooperateService.getCoAccountLogin(website));
        return websiteDetailDTO;
    }


}
