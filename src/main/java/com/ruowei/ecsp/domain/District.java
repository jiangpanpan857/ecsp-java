package com.ruowei.ecsp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 省市区
 */
@ApiModel(description = "省市区")
@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级id
     */
    @ApiModelProperty(value = "上级id")
    @Column(name = "pid")
    private Long pid;

    /**
     * 层级深度[0:省,1:市,2:区,3:镇]
     */
    @ApiModelProperty(value = "层级深度[0:省,1:市,2:区,3:镇]")
    @Column(name = "deep")
    private Integer deep;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    @Column(name = "name")
    private String name;

    /**
     * 城市名称的全拼
     */
    @ApiModelProperty(value = "城市名称的全拼")
    @Column(name = "pinyin")
    private String pinyin;

    /**
     * 城市名称拼音第一个字母
     */
    @ApiModelProperty(value = "城市名称拼音第一个字母")
    @Column(name = "prefix")
    private String prefix;

    /**
     * 数据源原始编号，如果是添加的数据，此编号为0
     */
    @ApiModelProperty(value = "数据源原始编号，如果是添加的数据，此编号为0")
    @Column(name = "ext_id")
    private Long extId;

    /**
     * 数据源原始名称，为未精简的名称
     */
    @ApiModelProperty(value = "数据源原始名称，为未精简的名称")
    @Column(name = "ext_name")
    private String extName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public District id(Long id) {
        this.id = id;
        return this;
    }

    public Long getPid() {
        return this.pid;
    }

    public District pid(Long pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getDeep() {
        return this.deep;
    }

    public District deep(Integer deep) {
        this.deep = deep;
        return this;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getName() {
        return this.name;
    }

    public District name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public District pinyin(String pinyin) {
        this.pinyin = pinyin;
        return this;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public District prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getExtId() {
        return this.extId;
    }

    public District extId(Long extId) {
        this.extId = extId;
        return this;
    }

    public void setExtId(Long extId) {
        this.extId = extId;
    }

    public String getExtName() {
        return this.extName;
    }

    public District extName(String extName) {
        this.extName = extName;
        return this;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", pid=" + getPid() +
            ", deep=" + getDeep() +
            ", name='" + getName() + "'" +
            ", pinyin='" + getPinyin() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", extId=" + getExtId() +
            ", extName='" + getExtName() + "'" +
            "}";
    }
}
