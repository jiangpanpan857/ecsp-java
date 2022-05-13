package com.ruowei.ecsp.security;

import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.domain.Website;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    // 用户ID
    private Long userId;

    // 用户登录名
    private String login;

    // 用户真实姓名
    private String realName;

    // roleType
    private String roleCode;

    // websiteId
    private Long websiteId;

    // 网站名
    private String websiteName;

    private Long companyId;

    private Long sysUserId;

    private String sinkToken;

    public UserModel(EcoUser ecoUser) {
        this.userId = ecoUser.getId();
        this.login = ecoUser.getLogin();
        this.realName = ecoUser.getRealName();
        this.roleCode = ecoUser.getRoleCode();
    }

    public void setNeeded(Website website, Long companyId, Long sysUserId) {
        this.websiteId = website.getId();
        this.websiteName = website.getName();
        this.companyId = companyId;
        this.sysUserId = sysUserId;
    }

}
