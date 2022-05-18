package com.ruowei.ecsp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 森林数据表
 */
@ApiModel(description = "森林数据表")
@Entity
@Table(name = "forest_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ForestData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 省份名
     */
    @ApiModelProperty(value = "省份名")
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份ID")
    @Column(name = "province_id")
    private String provinceId;

    /**
     * 城市名
     */
    @ApiModelProperty(value = "城市名")
    @Column(name = "city_name")
    private String cityName;

    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID")
    @Column(name = "city_id")
    private String cityId;

    /**
     * 区县名
     */
    @ApiModelProperty(value = "区县名")
    @Column(name = "area_name")
    private String areaName;

    /**
     * 区县ID
     */
    @ApiModelProperty("区县ID")
    @Column(name = "area_id")
    private String areaId;

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    @Column(name = "year")
    private String year;

    /**
     * 新增造林面积
     */
    @ApiModelProperty(value = "新增造林面积")
    @Column(name = "area_increment", precision = 21, scale = 2)
    private BigDecimal areaIncrement;

    /**
     * 森林蓄积量
     */
    @ApiModelProperty(value = "森林蓄积量")
    @Column(name = "storage", precision = 21, scale = 2)
    private BigDecimal storage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ForestData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public ForestData provinceName(String provinceName) {
        this.setProvinceName(provinceName);
        return this;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return this.provinceId;
    }

    public ForestData provinceId(String provinceId) {
        this.setProvinceId(provinceId);
        return this;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public ForestData cityName(String cityName) {
        this.setCityName(cityName);
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return this.cityId;
    }

    public ForestData cityId(String cityId) {
        this.setCityId(cityId);
        return this;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public ForestData areaName(String areaName) {
        this.setAreaName(areaName);
        return this;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public ForestData areaId(String areaId) {
        this.setAreaId(areaId);
        return this;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getYear() {
        return this.year;
    }

    public ForestData year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getAreaIncrement() {
        return this.areaIncrement;
    }

    public ForestData areaIncrement(BigDecimal areaIncrement) {
        this.setAreaIncrement(areaIncrement);
        return this;
    }

    public void setAreaIncrement(BigDecimal areaIncrement) {
        this.areaIncrement = areaIncrement;
    }

    public BigDecimal getStorage() {
        return this.storage;
    }

    public ForestData storage(BigDecimal storage) {
        this.setStorage(storage);
        return this;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForestData)) {
            return false;
        }
        return id != null && id.equals(((ForestData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ForestData{" +
            "id=" + getId() +
            ", provinceName='" + getProvinceName() + "'" +
            ", provinceId='" + getProvinceId() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", areaName='" + getAreaName() + "'" +
            ", areaId='" + getAreaId() + "'" +
            ", year='" + getYear() + "'" +
            ", areaIncrement=" + getAreaIncrement() +
            ", storage=" + getStorage() +
            "}";
    }
}
