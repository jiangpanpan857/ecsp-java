package com.ruowei.ecsp.domain;

import com.ruowei.ecsp.domain.enumeration.CompanyStatusType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 业主信息表
 */
@ApiModel(description = "业主信息表")
@Entity
@Table(name = "sys_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 企业名称
     */
    @NotNull
    @ApiModelProperty(value = "企业名称", required = true)
    @Column(name = "company_name", nullable = false)
    private String companyName;

    /**
     * 统一社会信用代码
     */
    @NotNull
    @ApiModelProperty(value = "统一社会信用代码", required = true)
    @Column(name = "uni_credit_code", nullable = false)
    private String uniCreditCode;

    /**
     * 联系方式
     */
    @NotNull
    @ApiModelProperty(value = "联系方式", required = true)
    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    /**
     * 企业地址
     */
    @ApiModelProperty(value = "企业地址")
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 企业邮编
     */
    @ApiModelProperty(value = "企业邮编")
    @Column(name = "company_post_code")
    private String companyPostCode;

    /**
     * 企业法人
     */
    @ApiModelProperty(value = "企业法人")
    @Column(name = "legal_person")
    private String legalPerson;

    /**
     * 法人职位
     */
    @ApiModelProperty(value = "法人职位")
    @Column(name = "legal_person_position")
    private String legalPersonPosition;

    /**
     * 企业传真
     */
    @ApiModelProperty(value = "企业传真")
    @Column(name = "company_fax")
    private String companyFax;

    /**
     * 企业电话
     */
    @ApiModelProperty(value = "企业电话")
    @Column(name = "company_tel")
    private String companyTel;

    /**
     * 企业邮箱
     */
    @ApiModelProperty(value = "企业邮箱")
    @Column(name = "company_mail")
    private String companyMail;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompanyStatusType status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysCompany id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public SysCompany companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUniCreditCode() {
        return this.uniCreditCode;
    }

    public SysCompany uniCreditCode(String uniCreditCode) {
        this.uniCreditCode = uniCreditCode;
        return this;
    }

    public void setUniCreditCode(String uniCreditCode) {
        this.uniCreditCode = uniCreditCode;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public SysCompany contactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public SysCompany companyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPostCode() {
        return this.companyPostCode;
    }

    public SysCompany companyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
        return this;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
    }

    public String getLegalPerson() {
        return this.legalPerson;
    }

    public SysCompany legalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
        return this;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonPosition() {
        return this.legalPersonPosition;
    }

    public SysCompany legalPersonPosition(String legalPersonPosition) {
        this.legalPersonPosition = legalPersonPosition;
        return this;
    }

    public void setLegalPersonPosition(String legalPersonPosition) {
        this.legalPersonPosition = legalPersonPosition;
    }

    public String getCompanyFax() {
        return this.companyFax;
    }

    public SysCompany companyFax(String companyFax) {
        this.companyFax = companyFax;
        return this;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyTel() {
        return this.companyTel;
    }

    public SysCompany companyTel(String companyTel) {
        this.companyTel = companyTel;
        return this;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyMail() {
        return this.companyMail;
    }

    public SysCompany companyMail(String companyMail) {
        this.companyMail = companyMail;
        return this;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public CompanyStatusType getStatus() {
        return this.status;
    }

    public SysCompany status(CompanyStatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(CompanyStatusType status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysCompany)) {
            return false;
        }
        return id != null && id.equals(((SysCompany) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysCompany{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", uniCreditCode='" + getUniCreditCode() + "'" +
            ", contactPhone='" + getContactPhone() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", companyPostCode='" + getCompanyPostCode() + "'" +
            ", legalPerson='" + getLegalPerson() + "'" +
            ", legalPersonPosition='" + getLegalPersonPosition() + "'" +
            ", companyFax='" + getCompanyFax() + "'" +
            ", companyTel='" + getCompanyTel() + "'" +
            ", companyMail='" + getCompanyMail() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
