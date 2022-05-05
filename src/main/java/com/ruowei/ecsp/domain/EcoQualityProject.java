package com.ruowei.ecsp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 生态系统碳汇优质项目
 */
@Schema(description = "生态系统碳汇优质项目")
@Entity
@Table(name = "eco_quality_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcoQualityProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 所属网站ID
     */
    @Schema(description = "所属网站ID", required = true)
    @NotNull
    @Column(name = "website_id", nullable = false)
    private Long websiteId;

    /**
     * 项目名称
     */
    @Schema(description = "项目名称", required = true)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 方法学：CCER | 碳惠普
     */
    @Schema(description = "方法学：CCER | 碳惠普", required = true)
    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    /**
     * 项目类型
     */
    @Schema(description = "项目类型", required = true)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 省份名
     */
    @Schema(description = "省份名", required = true)
    @NotNull
    @Column(name = "province_name", nullable = false)
    private String provinceName;

    /**
     * 省份ID
     */
    @Schema(description = "省份ID", required = true)
    @NotNull
    @Column(name = "province_id", nullable = false)
    private Long provinceId;

    /**
     * 预计排放量
     */
    @Schema(description = "预计排放量", required = true)
    @NotNull
    @Column(name = "pre_sink", precision = 21, scale = 2, nullable = false)
    private BigDecimal preSink;

    /**
     * 已备案排放量
     */
    @Schema(description = "已备案排放量", required = true)
    @NotNull
    @Column(name = "record_sink", precision = 21, scale = 2, nullable = false)
    private BigDecimal recordSink;

    /**
     * 项目图片地址
     */
    @Schema(description = "项目图片地址", required = true)
    @NotNull
    @Column(name = "pic_url", nullable = false)
    private String picUrl;

    /**
     * 项目简介
     */
    @Schema(description = "项目简介", required = true)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 交易电话
     */
    @Schema(description = "交易电话", required = true)
    @NotNull
    @Column(name = "trade_phone", nullable = false)
    private String tradePhone;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", required = true)
    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期", required = true)
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    /**
     * 状态: 已发布 | 未发布
     */
    @Schema(description = "状态: 已发布 | 未发布", required = true)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EcoQualityProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebsiteId() {
        return this.websiteId;
    }

    public EcoQualityProject websiteId(Long websiteId) {
        this.setWebsiteId(websiteId);
        return this;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getName() {
        return this.name;
    }

    public EcoQualityProject name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return this.method;
    }

    public EcoQualityProject method(String method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return this.type;
    }

    public EcoQualityProject type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public EcoQualityProject provinceName(String provinceName) {
        this.setProvinceName(provinceName);
        return this;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getProvinceId() {
        return this.provinceId;
    }

    public EcoQualityProject provinceId(Long provinceId) {
        this.setProvinceId(provinceId);
        return this;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public BigDecimal getPreSink() {
        return this.preSink;
    }

    public EcoQualityProject preSink(BigDecimal preSink) {
        this.setPreSink(preSink);
        return this;
    }

    public void setPreSink(BigDecimal preSink) {
        this.preSink = preSink;
    }

    public BigDecimal getRecordSink() {
        return this.recordSink;
    }

    public EcoQualityProject recordSink(BigDecimal recordSink) {
        this.setRecordSink(recordSink);
        return this;
    }

    public void setRecordSink(BigDecimal recordSink) {
        this.recordSink = recordSink;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public EcoQualityProject picUrl(String picUrl) {
        this.setPicUrl(picUrl);
        return this;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public EcoQualityProject description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTradePhone() {
        return this.tradePhone;
    }

    public EcoQualityProject tradePhone(String tradePhone) {
        this.setTradePhone(tradePhone);
        return this;
    }

    public void setTradePhone(String tradePhone) {
        this.tradePhone = tradePhone;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public EcoQualityProject createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public EcoQualityProject createDate(LocalDate createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return this.status;
    }

    public EcoQualityProject status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcoQualityProject)) {
            return false;
        }
        return id != null && id.equals(((EcoQualityProject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcoQualityProject{" +
            "id=" + getId() +
            ", websiteId=" + getWebsiteId() +
            ", name='" + getName() + "'" +
            ", method='" + getMethod() + "'" +
            ", type='" + getType() + "'" +
            ", provinceName='" + getProvinceName() + "'" +
            ", provinceId=" + getProvinceId() +
            ", preSink=" + getPreSink() +
            ", recordSink=" + getRecordSink() +
            ", picUrl='" + getPicUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", tradePhone='" + getTradePhone() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
