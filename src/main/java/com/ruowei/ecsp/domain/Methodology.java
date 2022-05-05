package com.ruowei.ecsp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 方法学
 */
@Schema(description = "方法学")
@Entity
@Table(name = "eco_methodology")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Methodology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 类型
     */
    @Schema(description = "类型", required = true)
    @NotNull
    @Column(name = "type", nullable = false, unique = true)
    private String type;

    /**
     * 名称
     */
    @Schema(description = "名称", required = true)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 简介
     */
    @Schema(description = "简介", required = true)
    @NotNull
    @Size(max = 1000)
    @Column(name = "introduction", length = 1000, nullable = false)
    private String introduction;

    /**
     * 图片
     */
    @Schema(description = "图片", required = true)
    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    /**
     * 方法学文件
     */
    @Schema(description = "方法学文件", required = true)
    @NotNull
    @Column(name = "attachment", nullable = false)
    private String attachment;

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

    public Methodology id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Methodology type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Methodology name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public Methodology introduction(String introduction) {
        this.setIntroduction(introduction);
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImage() {
        return this.image;
    }

    public Methodology image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public Methodology attachment(String attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public ZonedDateTime getAddTime() {
        return this.addTime;
    }

    public Methodology addTime(ZonedDateTime addTime) {
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
        if (!(o instanceof Methodology)) {
            return false;
        }
        return id != null && id.equals(((Methodology) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Methodology{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            ", image='" + getImage() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", addTime='" + getAddTime() + "'" +
            "}";
    }
}
