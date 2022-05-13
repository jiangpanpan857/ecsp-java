package com.ruowei.ecsp.domain;

import com.ruowei.ecsp.web.rest.imp.SequenceIMP;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 生态资源
 */
@Schema(description = "生态资源")
@Entity
@Table(name = "eco_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EcoResource implements Serializable, SequenceIMP {

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
     * 轮播顺序
     */
    @Schema(description = "轮播顺序", required = true)
    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    /**
     * 标题
     */
    @Schema(description = "标题", required = true)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 图片
     */
    @Schema(description = "图片", required = true)
    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    /**
     * 简介
     */
    @Schema(description = "简介")
    @Size(max = 1000)
    @Column(name = "introduction", length = 1000)
    private String introduction;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间", required = true)
    @NotNull
    @Column(name = "add_time", nullable = false)
    private ZonedDateTime addTime;

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

    public EcoResource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebsiteId() {
        return this.websiteId;
    }

    public EcoResource websiteId(Long websiteId) {
        this.setWebsiteId(websiteId);
        return this;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public EcoResource sequence(Integer sequence) {
        this.setSequence(sequence);
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return this.title;
    }

    public EcoResource title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public EcoResource image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public EcoResource introduction(String introduction) {
        this.setIntroduction(introduction);
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ZonedDateTime getAddTime() {
        return this.addTime;
    }

    public EcoResource addTime(ZonedDateTime addTime) {
        this.setAddTime(addTime);
        return this;
    }

    public void setAddTime(ZonedDateTime addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return this.status;
    }

    public EcoResource status(String status) {
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
        if (!(o instanceof EcoResource)) {
            return false;
        }
        return id != null && id.equals(((EcoResource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcoResource{" +
            "id=" + getId() +
            ", websiteId=" + getWebsiteId() +
            ", sequence=" + getSequence() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            ", addTime='" + getAddTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
