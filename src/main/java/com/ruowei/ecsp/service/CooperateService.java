package com.ruowei.ecsp.service;

import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.EcoUserRepository;
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
    private final EcoUserRepository ecoUserRepository;
    private final WebsiteRepository websiteRepository;

    private final CoSearchService coSearchService;


    public CooperateService(EcoUserRepository ecoUserRepository, WebsiteRepository websiteRepository, CoSearchService coSearchService) {
        this.ecoUserRepository = ecoUserRepository;
        this.websiteRepository = websiteRepository;
        this.coSearchService = coSearchService;
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
        String url = "http://localhost:5156" +
            "/api/permit/token?sysUserId=" + Long.valueOf(sysUserIdStr);
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
            userModel.setNeeded(site, Long.valueOf(sysUserIdStr));
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

