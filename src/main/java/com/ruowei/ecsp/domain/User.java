package com.ruowei.ecsp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruowei.ecsp.domain.enumeration.UserStatusType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户
 */
@ApiModel(description = "用户")
@Entity
@Table(name = "sys_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "用户名", required = true)
    @Column(name = "login", nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @ApiModelProperty(value = "密码", required = true)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @NotNull
    @ApiModelProperty(value = "用户昵称", required = true)
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty(value = "企业ID")
    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "业主ID")
    @Column(name = "company_id")
    private Long companyId;

    @ApiModelProperty(value = "第三方机构ID")
    @Column(name = "third_party_id")
    private Long thirdPartyId;

    @NotNull
    @ApiModelProperty(value = "用户状态", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatusType status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User id(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User login(String login) {
        this.login = login;
        return this;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public User nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public User remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public User enterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
        return this;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public User companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public User thirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
        return this;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public UserStatusType getStatus() {
        return status;
    }

    public User status(UserStatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(UserStatusType status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", nickName='" + nickName + '\'' +
            ", remark='" + remark + '\'' +
            ", enterpriseId=" + enterpriseId +
            ", companyId=" + companyId +
            ", thirdPartyId=" + thirdPartyId +
            ", status=" + status +
            '}';
    }
}
