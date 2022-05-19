package com.ruowei.ecsp.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.repository.EcoUserRepository;
import com.ruowei.ecsp.security.jwt.JWTFilter;
import com.ruowei.ecsp.security.jwt.TokenProvider;
import com.ruowei.ecsp.service.WebsiteService;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.web.rest.errors.DefaultProblem;
import com.ruowei.ecsp.web.rest.vm.LoginVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Status;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "系统管理")
public class UserJWTController {

    private final EcoUserRepository ecoUserRepository;

    private final WebsiteService websiteService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(EcoUserRepository ecoUserRepository, WebsiteService websiteService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.ecoUserRepository = ecoUserRepository;
        this.websiteService = websiteService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "登录")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );
        String jwt;
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        } catch (Exception e) {
            throw new DefaultProblem("认证失败", Status.NOT_ACCEPTABLE, "用户名或密码错误");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        EcoUser ecoUser = ecoUserRepository.findByLogin(loginVM.getUsername());
        JWTToken token = new JWTToken(jwt, ecoUser.getRoleCode());
        // 网站限制 TODO
        if (!loginVM.getUsername().equals("admin")) {
            Website website = websiteService.getWebsiteByDomain(loginVM.getDomain());
            AssertUtil.falseThrow(ecoUserRepository.existsByLoginAndWebsiteId(loginVM.getUsername(), website.getId()), "登录失败", "用户名或密码错误");
            token.setLogin(loginVM.getUsername());
            token.setWebsiteName(website.getName());
            token.setRealName(ecoUser.getRealName());
//            token.setSinkToken(website.getSinkToken());
            token.setLogo(website.getLogo());
        }
        token.setDomain(loginVM.getDomain());
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        private String roleCode;

        private String login;

        private String realName;

        private String domain;

        private String websiteName;

        private String logo;

        private String sinkToken;

        JWTToken(String idToken, String roleCode) {
            this.idToken = idToken;
            this.roleCode = roleCode;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("role_code")
        String getRoleCode() {
            return roleCode;
        }

        void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        @JsonProperty("login")
        String getLogin() {
            return login;
        }

        void setLogin(String login) {
            this.login = login;
        }

        @JsonProperty("real_name")
        String getRealName() {
            return realName;
        }

        void setRealName(String realName) {
            this.realName = realName;
        }

        @JsonProperty("domain")
        String getDomain() {
            return domain;
        }

        void setDomain(String domain) {
            this.domain = domain;
        }

        @JsonProperty("website_name")
        String getWebsiteName() {
            return websiteName;
        }

        void setWebsiteName(String websiteName) {
            this.websiteName = websiteName;
        }

        @JsonProperty("logo")
        String getLogo() {
            return logo;
        }

        void setLogo(String logo) {
            this.logo = logo;
        }

        @JsonProperty("sink_token")
        String getSinkToken() {
            return sinkToken;
        }

        void setSinkToken(String sinkToken) {
            this.sinkToken = sinkToken;
        }

    }
}
