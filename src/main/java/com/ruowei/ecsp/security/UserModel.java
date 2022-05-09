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

    // websiteId
    private Long websiteId;

    // 用户登录名
    private String login;

    // 用户真实姓名
    private String realName;

    // roleType
    private String roleCode;

    // 网站名
    private String websiteName;

    public UserModel(EcoUser ecoUser, Website site) {
        this.userId = ecoUser.getId();
        this.websiteId = site.getId();
        this.login = ecoUser.getLogin();
        this.realName = ecoUser.getRealName();
        this.roleCode = ecoUser.getRoleCode();
        this.websiteName = site.getName();
    }

}
