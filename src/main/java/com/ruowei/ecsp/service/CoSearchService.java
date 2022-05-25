package com.ruowei.ecsp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.repository.WebsiteRepository;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.JsonUtil;
import com.ruowei.ecsp.util.RestTemplateUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.dto.SinUserDTO;
import com.ruowei.ecsp.web.rest.qm.SinUserQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CoSearchService {

    private final WebsiteRepository websiteRepository;
    private final EcoUserRepository ecoUserRepository;

    public CoSearchService(WebsiteRepository websiteRepository, EcoUserRepository ecoUserRepository) {
        this.websiteRepository = websiteRepository;
        this.ecoUserRepository = ecoUserRepository;
    }

    public String getCurrentSiteToken() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getUsername();
            EcoUser ecoUser = ecoUserRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("用户不存在"));
            if (!login.equals("admin")) {
                Website site = websiteRepository.getById(ecoUser.getWebsiteId());
                return site.getSinkToken();
            }
        } catch (Exception e) {
            log.error("getCurrentSiteToken error", e);
            AssertUtil.toThrow("操作失败", "无法获取所属网站的token");
        }
        return null;
    }

    public String getRedirectUrl(String url, Object paramObject, Pageable pageable) {
        String newUrl;
        String preUrl = "http://localhost:8080/api/";
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
        newUrl = urlBuilder.substring(0, urlBuilder.length() - 1);
        log.info("redirectUrl url:{}", newUrl);
        return newUrl;
    }

    public ResponseEntity<Object> redirectGetWithDefaultSinToken(String url, Object paramObject, Pageable pageable) {
        String token = getCurrentSiteToken();
        log.info("redirectUrl token:{}", token);
        ResponseEntity<Object> result = RestTemplateUtil.getExchangeObject(getRedirectUrl(url, paramObject, pageable), token);
        log.info("redirectUrl result:{}", result);
        return result;
    }

    public ResponseEntity<Object> redirectGetWithoutSinToken(String url, Object paramObject, Pageable pageable) {
        ResponseEntity<Object> result = RestTemplateUtil.getForObject(getRedirectUrl(url, paramObject, pageable));
        log.info("redirectUrl result:{}", result);
        return result;
    }

    public String getSiteRelatedSinToken(String url, Object paramObject, Pageable pageable) {
        String result = RestTemplateUtil.getForEntity(getRedirectUrl(url, paramObject, pageable));
        log.info("redirectUrl result:{}", result);
        return result;
    }

    public List<SinUserDTO> getCarbonLibraAccount(SinUserQM qm) {
        ResponseEntity<Object> response = redirectGetWithoutSinToken("eco-cooperate/permit/users", qm, null);
        if (response.getBody() == null) {
            return new ArrayList<>();
        }
        List<Object> objects = (List<Object>) response.getBody();
        return StreamUtil.collectV(objects, o -> JsonUtil.transfer(o, SinUserDTO.class, ""));
    }

}
