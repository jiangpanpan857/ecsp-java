package com.ruowei.ecsp.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteVisDTO {

    private Long id;

    /**
     * 网站名称
     */
    private String name;

    /**
     * 网站域名
     */
    private String domain;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 网站联系人
     */
    private String websiteContact;

    /**
     * 网站联系人电话
     */
    private String websiteContactNumber;

    /**
     * 网站logo
     */
    private String logo;

    /**
     * 顶部背景图
     */
    private String headerImg;

    /**
     * 业务咨询电话
     */
    private String businessNumber;

    /**
     * 机构地址
     */
    private String address;

    /**
     * 可展示方法学（按顺序）
     */
    private String methodologyIds;
}
