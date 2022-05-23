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

import javax.validation.constraints.NotNull;
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

    public String getSiteCoAccountLogin(Website site) {
        SinUserQM qm = new SinUserQM();
        qm.setId(Long.valueOf(site.getCarbonLibraAccount()));
        String url = "eco-cooperate/users";
        List<SinUserDTO> dtos = (List<SinUserDTO>) coSearchService.redirectUrl(url, qm, null);
        return dtos.get(0).getLogin();
    }


    public void addSiteSinToken(Website site) {
        log.info("addSiteSinToken: {}", site.getId());
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
            log.error("addSiteSinToken error: {}", e.getMessage());
        }
    }

    public UserModel userModelByLogin(@NotNull String login) {
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

}

