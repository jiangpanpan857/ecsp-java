package com.ruowei.ecsp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户
 */
@Schema(description = "用户")
@Entity
@Table(name = "eco_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcoUser implements Serializable {

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
     * 用户名
     */
    @Schema(description = "用户名", required = true)
    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    /**
     * 密码
     */
    @Schema(description = "密码", required = true)
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名", required = true)
    @NotNull
    @Column(name = "real_name", nullable = false)
    private String realName;

    /**
     * 角色编码: ROLE_ADMIN | ROLE_MANAGER
     */
    @Schema(description = "角色编码: ROLE_ADMIN | ROLE_MANAGER", required = true)
    @NotNull
    @Column(name = "role_code", nullable = false)
    private String roleCode;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间", required = true)
    @NotNull
    @Column(name = "add_time", nullable = false)
    private ZonedDateTime addTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EcoUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebsiteId() {
        return this.websiteId;
    }

    public EcoUser websiteId(Long websiteId) {
        this.setWebsiteId(websiteId);
        return this;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getLogin() {
        return this.login;
    }

    public EcoUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public EcoUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return this.realName;
    }

    public EcoUser realName(String realName) {
        this.setRealName(realName);
        return this;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public EcoUser roleCode(String roleCode) {
        this.setRoleCode(roleCode);
        return this;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public ZonedDateTime getAddTime() {
        return this.addTime;
    }

    public EcoUser addTime(ZonedDateTime addTime) {
        this.setAddTime(addTime);
        return this;
    }

    public void setAddTime(ZonedDateTime addTime) {
        this.addTime = addTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcoUser)) {
            return false;
        }
        return id != null && id.equals(((EcoUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcoUser{" +
            "id=" + getId() +
            ", websiteId=" + getWebsiteId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", realName='" + getRealName() + "'" +
            ", roleCode='" + getRoleCode() + "'" +
            ", addTime='" + getAddTime() + "'" +
            "}";
    }
}
