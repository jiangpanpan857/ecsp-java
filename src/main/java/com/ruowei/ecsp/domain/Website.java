package com.ruowei.ecsp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 网站
 */
@Schema(description = "网站")
@Entity
@Table(name = "eco_website")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Website implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称", required = true)
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * 网站域名
     */
    @Schema(description = "网站域名", required = true)
    @NotNull
    @Column(name = "domain", nullable = false, unique = true)
    private String domain;

    /**
     * 碳天枰账号ID
     */
    @Schema(description = "碳天枰账号ID", required = true)
    @NotNull
    @Column(name = "carbon_libra_account", nullable = false)
    private String carbonLibraAccount;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称", required = true)
    @NotNull
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    /**
     * 城市ID
     */
    @Schema(description = "城市ID", required = true)
    @NotNull
    @Column(name = "city_id", nullable = false)
    private String cityId;

    /**
     * 城市名称
     */
    @Schema(description = "城市名称", required = true)
    @NotNull
    @Column(name = "city_name", nullable = false)
    private String cityName;

    /**
     * 网站联系人
     */
    @Schema(description = "网站联系人", required = true)
    @NotNull
    @Column(name = "website_contact", nullable = false)
    private String websiteContact;

    /**
     * 网站联系人电话
     */
    @Schema(description = "网站联系人电话", required = true)
    @NotNull
    @Column(name = "website_contact_number", nullable = false)
    private String websiteContactNumber;

    /**
     * 网站logo
     */
    @Schema(description = "网站logo", required = true)
    @NotNull
    @Column(name = "logo", nullable = false)
    private String logo;

    /**
     * 顶部背景图
     */
    @Schema(description = "顶部背景图", required = true)
    @NotNull
    @Column(name = "header_img", nullable = false)
    private String headerImg;

    /**
     * 业务咨询电话
     */
    @Schema(description = "业务咨询电话", required = true)
    @NotNull
    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    /**
     * 机构地址
     */
    @Schema(description = "机构地址", required = true)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 可展示方法学（按顺序）
     */
    @Schema(description = "可展示方法学（按顺序）", required = true)
    @NotNull
    @Column(name = "methodology_ids", nullable = false)
    private String methodologyIds;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间", required = true)
    @NotNull
    @Column(name = "add_time", nullable = false)
    private ZonedDateTime addTime;

    /**
     * 碳天秤接口访问token
     */
    @Schema(description = "碳天秤接口访问token", required = true)
    @NotNull
    @Column(name = "sink_token", nullable = false)
    private String sinkToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Website id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Website name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return this.domain;
    }

    public Website domain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCarbonLibraAccount() {
        return this.carbonLibraAccount;
    }

    public Website carbonLibraAccount(String carbonLibraAccount) {
        this.setCarbonLibraAccount(carbonLibraAccount);
        return this;
    }

    public void setCarbonLibraAccount(String carbonLibraAccount) {
        this.carbonLibraAccount = carbonLibraAccount;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Website organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCityId() {
        return this.cityId;
    }

    public Website cityId(String cityId) {
        this.setCityId(cityId);
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Website cityName(String cityName) {
        this.setCityName(cityName);
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWebsiteContact() {
        return this.websiteContact;
    }

    public Website websiteContact(String websiteContact) {
        this.setWebsiteContact(websiteContact);
        return this;
    }

    public void setWebsiteContact(String websiteContact) {
        this.websiteContact = websiteContact;
    }

    public String getWebsiteContactNumber() {
        return this.websiteContactNumber;
    }

    public Website websiteContactNumber(String websiteContactNumber) {
        this.setWebsiteContactNumber(websiteContactNumber);
        return this;
    }

    public void setWebsiteContactNumber(String websiteContactNumber) {
        this.websiteContactNumber = websiteContactNumber;
    }

    public String getLogo() {
        return this.logo;
    }

    public Website logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHeaderImg() {
        return this.headerImg;
    }

    public Website headerImg(String headerImg) {
        this.setHeaderImg(headerImg);
        return this;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getBusinessNumber() {
        return this.businessNumber;
    }

    public Website businessNumber(String businessNumber) {
        this.setBusinessNumber(businessNumber);
        return this;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public Website address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMethodologyIds() {
        return this.methodologyIds;
    }

    public Website methodologyIds(String methodologyIds) {
        this.setMethodologyIds(methodologyIds);
        return this;
    }

    public void setMethodologyIds(String methodologyIds) {
        this.methodologyIds = methodologyIds;
    }

    public ZonedDateTime getAddTime() {
        return this.addTime;
    }

    public Website addTime(ZonedDateTime addTime) {
        this.setAddTime(addTime);
        return this;
    }

    public void setAddTime(ZonedDateTime addTime) {
        this.addTime = addTime;
    }

    public String getSinkToken() {
        return sinkToken;
    }

    public Website sinkToken(String sinkToken) {
        this.sinkToken = sinkToken;
        return this;
    }
    public void setSinkToken(String sinkToken) {
        this.sinkToken = sinkToken;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Website)) {
            return false;
        }
        return id != null && id.equals(((Website) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Website{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", domain='" + getDomain() + "'" +
            ", carbonLibraAccount='" + getCarbonLibraAccount() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", websiteContact='" + getWebsiteContact() + "'" +
            ", websiteContactNumber='" + getWebsiteContactNumber() + "'" +
            ", logo='" + getLogo() + "'" +
            ", headerImg='" + getHeaderImg() + "'" +
            ", businessNumber='" + getBusinessNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", methodologyIds='" + getMethodologyIds() + "'" +
            ", addTime='" + getAddTime() + "'" +
            ", sinkToken='" + getSinkToken() + "'" +
            "}";
    }
}
