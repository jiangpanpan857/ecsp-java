package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.QSysCompany;
import com.ruowei.ecsp.domain.QUser;
import com.ruowei.ecsp.domain.QWebsite;
import com.ruowei.ecsp.domain.enumeration.CompanyStatusType;
import com.ruowei.ecsp.domain.enumeration.UserStatusType;
import com.ruowei.ecsp.repository.UserRepository;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class CooperateService {
    private final UserRepository userRepository;

    private final JPAQueryFactory queryFactory;
    private QUser qU = QUser.user;
    private QSysCompany qSC = QSysCompany.sysCompany;
    private QWebsite qW = QWebsite.website;


    public CooperateService(UserRepository userRepository, JPAQueryFactory queryFactory) {
        this.userRepository = userRepository;
        this.queryFactory = queryFactory;
    }

    public List<SinUserDTO> getAllCooperateUsers(SinUserQM qm) {
        // remove already connected users
        BooleanBuilder builder = new BooleanBuilder()
            .and(qU.status.eq(UserStatusType.NORMAL))
            .and(qU.companyId.isNotNull())
            .and(qW.id.isNull())  // 如果是网站用户已绑定的，则不能是合作用户侯选
            .and(qSC.status.eq(CompanyStatusType.NORMAL));
        BooleanBuilder predicate = new OptionalBooleanBuilder()
            .notEmptyAnd(qSC.companyAddress::contains, qm.getCityName())
            .notEmptyAnd(qSC.companyName::contains, qm.getOrganizationName())
            .notEmptyAnd(qU.login::contains, qm.getLogin())
            .build();
        JPAQuery<SinUserDTO> query = queryFactory
            .select(Projections.bean(SinUserDTO.class, qU.id, qU.login))
            .from(qU)
            .leftJoin(qSC).on(qU.companyId.eq(qSC.id))
            .leftJoin(qW).on(qU.login.eq(qW.carbonLibraAccount))
            .where(builder.and(predicate));
        return query.fetch();
    }
}
