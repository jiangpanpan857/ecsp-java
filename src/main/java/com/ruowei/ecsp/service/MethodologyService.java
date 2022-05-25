package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.Methodology;
import com.ruowei.ecsp.domain.QMethodology;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.MethodologyRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.web.rest.dto.MethodologyPermitDTO;
import com.ruowei.ecsp.web.rest.qm.MethodologyQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MethodologyService {
    private final MethodologyRepository methodologyRepository;
    private final WebsiteRepository websiteRepository;


    private final JPAQueryFactory queryFactory;
    private QMethodology qM = QMethodology.methodology;

    public MethodologyService(MethodologyRepository methodologyRepository, WebsiteRepository websiteRepository, JPAQueryFactory queryFactory) {
        this.methodologyRepository = methodologyRepository;
        this.websiteRepository = websiteRepository;
        this.queryFactory = queryFactory;
    }


    public void createMethodology(Methodology methodology) {
        AssertUtil.notNullThrow(methodology.getId(), "新增失败", "新增时ID只能为空");
        methodology.setAddTime(ZonedDateTime.now());
        assertMethodologyThenSave(methodology);
    }

    public void updateMethodology(Methodology methodology) {
        AssertUtil.nullThrow(methodology.getId(), "修改失败", "修改时ID不能为空");
        assertMethodologyThenSave(methodology);
    }

    public ResponseEntity<List<Methodology>> searchAllMethodology(MethodologyQM qm, Pageable pageable) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qM.name::contains, qm.getName())
            .notEmptyAnd(qM.type::eq, qm.getType())
            .build();
        Page<Methodology> page = methodologyRepository.findAll(builder, pageable);
        return PageUtil.pageReturn(page);
    }

    public ResponseEntity<List<MethodologyPermitDTO>> allMethodologyPermitByDomain(String domain) {
        List<Long> methodologyIds = getSiteMethodologyIds(domain);
        List<MethodologyPermitDTO> list = new ArrayList<>(queryFactory
            .selectFrom(qM)
            .where(qM.id.in(methodologyIds))
            .orderBy(qM.type.asc())
            .transform(
                GroupBy.groupBy(qM.type).as(
                    Projections.bean(MethodologyPermitDTO.class,
                        qM.type,
                        GroupBy.list(
                            Projections.bean(MethodologyPermitDTO.MethodTemp.class,
                                qM.id, qM.type, qM.name, qM.introduction, qM.image, qM.attachment)
                        ).as("methodTempList")
                    )
                ))
            .values()
        );
        return ResponseEntity.ok(list);
    }

    public String methodNamesJoinedStrBySite(Website website) {
        List<Long> methodologyIds = getSiteMethodologyIds(website);
        List<Methodology> methodologies = methodologyRepository.findByIdIn(methodologyIds);
        List<String> names = StreamUtil.collectV(methodologies, Methodology::getName);
        return StringUtil.join(names);
    }

    private void assertMethodologyThenSave(Methodology methodology) {
        if (methodology.getId() == null) {
            AssertUtil.thenThrow(methodologyRepository.existsByName(methodology.getName()), "新增方法学失败！", "该方法论已存在");
        } else {
            AssertUtil.thenThrow(methodologyRepository.existsByNameAndIdNot(methodology.getName(), methodology.getId()), "修改方法学失败！", "该方法论已存在");
        }
        methodologyRepository.save(methodology);
    }

    private List<Long> getSiteMethodologyIds(String domain) {
        Website website = StreamUtil.optionalValue(websiteRepository.findByDomain(domain), "获取失败！", "该域名不存在");
        return StringUtil.getLongList(website.getMethodologyIds());
    }

    private List<Long> getSiteMethodologyIds(Website website) {
        return StringUtil.getLongList(website.getMethodologyIds());
    }

}
