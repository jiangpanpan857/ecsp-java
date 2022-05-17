package com.ruowei.ecsp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.security.UserModel;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.JsonUtil;
import com.ruowei.ecsp.util.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

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
        try {
            UserModel userModel = ecoUserService.getUserModel();
            Website website = websiteRepository.getById(userModel.getWebsiteId());
            return website.getSinkToken();
        } catch (Exception e) {
            log.error("getCurrentSiteToken error", e);
            AssertUtil.toThrow("操作失败", "无法获取所属网站的token");
        }
        return null;
    }

    public ResponseEntity<Object> redirectUrl(String url, Object paramObject, Pageable pageable) {
        String preUrl = "http://localhost:5156/api/";
        StringBuilder urlBuilder = new StringBuilder(preUrl).append(url).append("?");
        if (pageable != null) {
            urlBuilder.append("page=").append(pageable.getPageNumber()).append("&size=").append(pageable.getPageSize()).append("&");
        }
        if (paramObject != null) {
            JsonNode jsonNode = JsonUtil.toJsonNode(paramObject);
            Iterator<Map.Entry<String, JsonNode>> jsonNodes = jsonNode.fields();
            while (jsonNodes.hasNext()) {
                Map.Entry<String, JsonNode> node = jsonNodes.next();
                String value = node.getValue().asText();
                if (!value.equals("null")) {
                    urlBuilder.append(node.getKey()).append("=").append(value).append("&");
                }
            }
        }
        url = urlBuilder.substring(0, urlBuilder.length() - 1);
        log.info("redirectUrl url:{}", url);
        String token = getCurrentSiteToken();
        log.info("redirectUrl token:{}", token);
        ResponseEntity<Object> result = RestTemplateUtil.getExchangeObject(url, token);
        log.info("redirectUrl result:{}", result);
        return result;
    }


}