package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.domain.QEcoUser;
import com.ruowei.ecsp.domain.QWebsite;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.EcoUserDTO;
import com.ruowei.ecsp.web.rest.mapper.EcoUserDTOMapper;
import com.ruowei.ecsp.web.rest.qm.EcoUserQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class EcoUserService {

    private final PasswordEncoder passwordEncoder;

    private final EcoUserRepository ecoUserRepository;
    private final WebsiteRepository websiteRepository;

    private final WebsiteService websiteService;

    private final EcoUserDTOMapper ecoUserDTOMapper;


    private final JPAQueryFactory jpaQueryFactory;
    private QEcoUser qEcoUser = QEcoUser.ecoUser;
    private QWebsite qWebsite = QWebsite.website;


    public EcoUserService(PasswordEncoder passwordEncoder, EcoUserRepository ecoUserRepository, WebsiteRepository websiteRepository, WebsiteService websiteService, EcoUserDTOMapper ecoUserDTOMapper, JPAQueryFactory jpaQueryFactory) {
        this.passwordEncoder = passwordEncoder;
        this.ecoUserRepository = ecoUserRepository;
        this.websiteRepository = websiteRepository;
        this.websiteService = websiteService;
        this.ecoUserDTOMapper = ecoUserDTOMapper;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Long getWebsiteId(String domain) {
        Long websiteId;
        if (domain == null) {
            try {
                UserModel userModel = getUserModel();
                websiteId = userModel.getWebsiteId();
            } catch (Exception e) {
                throw new RuntimeException("未登录");
            }
        } else {
            Website website = websiteService.getWebsiteByDomain(domain);
            websiteId = website.getId();
        }
        return websiteId;

    }

    public UserModel getUserModel() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EcoUser ecoUser = ecoUserRepository.findOneByLogin(user.getUsername()).orElseThrow(() -> new RuntimeException("用户不存在"));
        Website site = websiteRepository.getById(ecoUser.getWebsiteId());
        return new UserModel(ecoUser, site);
    }

    public void createEcoUser(EcoUser ecoUser) {
        log.info("createEcoUser: {}", ecoUser);
        assertEcoUser(ecoUser);
        ecoUser.setPassword(passwordEncoder.encode(ecoUser.getPassword()));
        ecoUser.setAddTime(ZonedDateTime.now());
        ecoUserRepository.save(ecoUser);
    }

    public ResponseEntity<List<EcoUserDTO>> getEcoUsers(EcoUserQM qm, Pageable pageable) {
        log.info("getEcoUsers");
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qEcoUser.login::contains, qm.getLogin())
            .notEmptyAnd(qEcoUser.realName::contains, qm.getRealName())
            .notEmptyAnd(qWebsite.name::eq, qm.getWebsiteName())
            .build();
        JPAQuery<EcoUserDTO> query = jpaQueryFactory.select(Projections.bean(EcoUserDTO.class, qEcoUser.id, qWebsite.id.as("websiteId"), qEcoUser.login, qEcoUser.password, qEcoUser.realName, qEcoUser.roleCode, qEcoUser.addTime, qWebsite.name.as("websiteName")))
            .from(qEcoUser)
            .leftJoin(qWebsite).on(qEcoUser.websiteId.eq(qWebsite.id))
            .where(builder.and(qEcoUser.roleCode.ne("ROLE_ADMIN"))); // admin无网站
        long count = query.stream().count();
        List<EcoUserDTO> users = query
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(qEcoUser.addTime.desc())
            .fetch();
        return PageUtil.pageReturn(users, pageable, count);
    }

    public void updateEcoUser(EcoUser ecoUser) {
        log.info("editEcoUser: {}", ecoUser);
        assertEcoUser(ecoUser);
        EcoUser oldEcoUser = StreamUtil.optionalValue(ecoUserRepository.findById(ecoUser.getId()), "修改网站管理员失败", "用户不存在");
        // remain password not edited.
        ecoUser.setPassword(oldEcoUser.getPassword());
        ecoUserRepository.save(ecoUser);
    }

    private void assertEcoUser(EcoUser ecoUser) {
        AssertUtil.falseThrow(websiteRepository.existsById(ecoUser.getWebsiteId()), "新增网站管理员失败", "网站不存在");
        if (ecoUser.getId() != null) {
            AssertUtil.thenThrow(ecoUserRepository.existsByLoginAndIdNot(ecoUser.getLogin(), ecoUser.getId()), "修改网站管理员失败", "用户名已被占用");
        } else {
            AssertUtil.thenThrow(ecoUserRepository.findOneByLogin(ecoUser.getLogin()).isPresent(), "新增网站管理员失败", "用户名已存在");
        }
    }

    public ResponseEntity<EcoUserDTO> getEcoUser(Long id) {
        EcoUser ecoUser = ecoUserRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
        EcoUserDTO dto = ecoUserDTOMapper.toDto(ecoUser);
        dto.setWebsiteName(websiteRepository.getById(ecoUser.getWebsiteId()).getName());
        return ResponseEntity.ok(dto);
    }
}
