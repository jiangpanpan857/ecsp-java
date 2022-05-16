package com.ruowei.ecsp.web.rest.dto;

import com.ruowei.ecsp.domain.Website;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteDetailDTO {
    private Long id;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称", required = true)
    private String name;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名", required = true)
    private String domain;

    /**
     * 碳天枰账号ID
     */
    @Schema(description = "碳天枰账号ID", required = true)
    private String carbonLibraAccount;

    /**
     * 碳天枰账号ID
     */
    @Schema(description = "碳天枰账号ID")
    private String carbonLibraAccountName;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称", required = true)
    private String organizationName;

    /**
     * 城市ID
     */
    @Schema(description = "城市ID", required = true)
    private String cityId;

    /**
     * 城市名称
     */
    @Schema(description = "城市名称", required = true)
    private String cityName;

    /**
     * 网站联系人
     */
    @Schema(description = "网站联系人", required = true)
    private String websiteContact;

    /**
     * 网站联系人电话
     */
    @Schema(description = "网站联系人电话", required = true)
    private String websiteContactNumber;

    /**
     * 网站logo
     */
    @Schema(description = "网站logo", required = true)
    private String logo;

    /**
     * 顶部背景图
     */
    @Schema(description = "顶部背景图", required = true)
    private String headerImg;

    /**
     * 业务咨询电话
     */
    @Schema(description = "业务咨询电话", required = true)
    private String businessNumber;

    /**
     * 机构地址
     */
    @Schema(description = "机构地址", required = true)
    private String address;

    /**
     * 可展示方法学（按顺序）
     */
    @Schema(description = "可展示方法学（按顺序）", required = true)
    private String methodologyIds;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间", required = true)
    private ZonedDateTime addTime;

    /**
     * 方法学名称【,拼接】
     */
    @Schema(description = "方法学名称【,拼接】")
    private String methodologyNames;

    public WebsiteDetailDTO(Website website) {
        this.id = website.getId();
        this.name = website.getName();
        this.domain = website.getDomain();
        this.carbonLibraAccount = website.getCarbonLibraAccount();
        this.organizationName = website.getOrganizationName();
        this.cityId = website.getCityId();
        this.cityName = website.getCityName();
        this.websiteContact = website.getWebsiteContact();
        this.websiteContactNumber = website.getWebsiteContactNumber();
        this.logo = website.getLogo();
        this.headerImg = website.getHeaderImg();
        this.businessNumber = website.getBusinessNumber();
        this.address = website.getAddress();
        this.methodologyIds = website.getMethodologyIds();
        this.addTime = website.getAddTime();
    }

}
