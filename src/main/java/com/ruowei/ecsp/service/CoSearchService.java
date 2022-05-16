package com.ruowei.ecsp.service;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoSearchService {
    private final CooperateService cooperateService;
    private final EcoUserService ecoUserService;

    private final WebsiteRepository websiteRepository;

    public CoSearchService(CooperateService cooperateService, EcoUserService ecoUserService, WebsiteRepository websiteRepository) {
        this.cooperateService = cooperateService;
        this.ecoUserService = ecoUserService;
        this.websiteRepository = websiteRepository;
    }

    public String getCurrentSiteToken() {
        UserModel userModel = ecoUserService.getUserModel();
        Website website = websiteRepository.getById(userModel.getWebsiteId());
        return website.getSinkToken();
    }
}
