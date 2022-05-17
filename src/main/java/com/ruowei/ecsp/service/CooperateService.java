package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ruowei.ecsp.domain.*;
import com.ruowei.ecsp.domain.enumeration.CompanyStatusType;
import com.ruowei.ecsp.domain.enumeration.UserStatusType;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.SysCompanyRepository;
import com.ruowei.ecsp.repository.UserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
import com.ruowei.ecsp.util.RestTemplateUtil;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CooperateService {
    private final UserRepository userRepository;
    private final SysCompanyRepository sysCompanyRepository;
    private final EcoUserRepository ecoUserRepository;
    private final WebsiteRepository websiteRepository;

    private final JPAQueryFactory queryFactory;
    private QUser qU = QUser.user;
    private QSysCompany qSC = QSysCompany.sysCompany;
    private QWebsite qW = QWebsite.website;


    public CooperateService(UserRepository userRepository, SysCompanyRepository sysCompanyRepository, EcoUserRepository ecoUserRepository, WebsiteRepository websiteRepository, JPAQueryFactory queryFactory) {
        this.userRepository = userRepository;
        this.sysCompanyRepository = sysCompanyRepository;
        this.ecoUserRepository = ecoUserRepository;
        this.websiteRepository = websiteRepository;
        this.queryFactory = queryFactory;
    }

    public String getCoAccountLogin(Website site) {
        return userRepository.getById(Long.valueOf(site.getCarbonLibraAccount())).getLogin();
    }

    public SinUserDTO getSiteSysUser(Website site) {
        User sysUser = userRepository.getById(Long.valueOf(site.getCarbonLibraAccount()));
        return new SinUserDTO(sysUser.getId(), sysUser.getLogin());
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

    public void addSiteToken(Website site) {
        log.info("addSiteToken: {}", site.getId());
        String sysUserIdStr = site.getCarbonLibraAccount();
        User sysUser = userRepository.findById(Long.valueOf(sysUserIdStr)).orElseThrow(() -> new RuntimeException("用户对应碳天秤用户不存在"));
        String url = "http://localhost:5156" +
            "/api/permit/token?sysUserId=" + sysUser.getId()
            + "&sysUserName=" + sysUser.getLogin() +
            "&companyId=" + sysUser.getCompanyId();
        log.info("url: {}", url);
        try {
            String sinkToken = RestTemplateUtil.getForEntity(url);
            site.setSinkToken(sinkToken);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addSiteToken error: {}", e.getMessage());
        }
    }

    public UserModel currentUser(String login) {
        EcoUser ecoUser = ecoUserRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("用户不存在"));
        UserModel userModel = new UserModel(ecoUser);
        if (!login.equals("admin")) {
            Website site = websiteRepository.getById(ecoUser.getWebsiteId());
            String sysUserIdStr = site.getCarbonLibraAccount();
            User sysUser = userRepository.findById(Long.valueOf(sysUserIdStr)).orElseThrow(() -> new RuntimeException("用户对应碳天秤用户不存在"));
            userModel.setNeeded(site, sysUser.getCompanyId(), sysUser.getId());
        }
        return userModel;
    }

    public ResponseEntity<Resource> downloadSinkProjectFile(String url) {
        try {
            Resource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            } else {
                throw new BadRequestProblem("下载文件失败", url);
            }
        } catch (MalformedURLException e) {
            log.info("下载文件失败：{}，异常信息：{}", url, e.getCause());
            throw new BadRequestProblem("下载文件失败", url);
        }
    }

//    public ResponseEntity<List<SinProjectFile>> getAllSinkProjectFiles(
//        String projectId,
//        String fileCategoryCode
//    ) {
//        OptionalBooleanBuilder builder = new OptionalBooleanBuilder()
//            .notEmptyAnd(qSinProjectFile.relId::eq, projectId)
//            .notEmptyAnd(qSinProjectFile.used::eq, true);
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(fileCategoryCode)) {
//            builder.notEmptyAnd(qSinProjectFile.fileCategoryCode::in, Arrays.asList(fileCategoryCode.split(",")));
//        }
//        List<SinProjectFile> sinProjectFileList = jpaQueryFactory.selectFrom(qSinProjectFile).where(builder.build()).fetch();
//        return ResponseEntity.ok().body(sinProjectFileList);
//    }
//
//    public ResponseEntity<List<SinProjectFile>> getClearPeply(String ids) {
//        List<SinProjectFile> sinProjectFileList = sinProjectFileRepository.findByIdIn(
//            Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList())
//        );
//        return ResponseEntity.ok().body(sinProjectFileList);
//    }

}

