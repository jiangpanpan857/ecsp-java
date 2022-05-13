package com.ruowei.ecsp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典
 */
@ApiModel(description = "数据字典")
@Entity
@Table(name = "sys_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类编码
     */
    @NotNull
    @ApiModelProperty(value = "分类编码", required = true)
    @Column(name = "catagory_code", nullable = false)
    private String catagoryCode;

    /**
     * 分类名称
     */
    @NotNull
    @ApiModelProperty(value = "分类名称", required = true)
    @Column(name = "catagory_name", nullable = false)
    private String catagoryName;

    /**
     * 字典编码
     */
    @NotNull
    @ApiModelProperty(value = "字典编码", required = true)
    @Column(name = "dict_code", nullable = false)
    private String dictCode;

    /**
     * 字典名称
     */
    @NotNull
    @ApiModelProperty(value = "字典名称", required = true)
    @Column(name = "dict_name", nullable = false)
    private String dictName;

    /**
     * 禁用状态
     */
    @NotNull
    @ApiModelProperty(value = "禁用状态", required = true)
    @Column(name = "disabled", nullable = false)
    private Boolean disabled;

    /**
     * 排序号
     */
    @NotNull
    @ApiModelProperty(value = "排序号", required = true)
    @Column(name = "sort_no", nullable = false)
    private Integer sortNo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dict id(Long id) {
        this.id = id;
        return this;
    }

    public String getCatagoryCode() {
        return this.catagoryCode;
    }

    public Dict catagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
        return this;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getCatagoryName() {
        return this.catagoryName;
    }

    public Dict catagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
        return this;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public String getDictCode() {
        return this.dictCode;
    }

    public Dict dictCode(String dictCode) {
        this.dictCode = dictCode;
        return this;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return this.dictName;
    }

    public Dict dictName(String dictName) {
        this.dictName = dictName;
        return this;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public Dict disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getSortNo() {
        return this.sortNo;
    }

    public Dict sortNo(Integer sortNo) {
        this.sortNo = sortNo;
        return this;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dict)) {
            return false;
        }
        return id != null && id.equals(((Dict) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dict{" +
            "id=" + getId() +
            ", catagoryCode='" + getCatagoryCode() + "'" +
            ", catagoryName='" + getCatagoryName() + "'" +
            ", dictCode='" + getDictCode() + "'" +
            ", dictName='" + getDictName() + "'" +
            ", disabled='" + getDisabled() + "'" +
            ", sortNo=" + getSortNo() +
            "}";
    }
}
