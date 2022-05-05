package com.ruowei.ecsp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 政策资讯
 */
@Schema(description = "政策资讯")
@Entity
@Table(name = "eco_news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News implements Serializable {

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
     * 标题
     */
    @Schema(description = "标题", required = true)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", required = true)
    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 添加日期
     */
    @Schema(description = "添加日期", required = true)
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    /**
     * 类型: 林草新闻 | 地方动态 | 政策法规
     */
    @Schema(description = "类型: 林草新闻 | 地方动态 | 政策法规", required = true)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 作者
     */
    @Schema(description = "作者")
    @Column(name = "author")
    private String author;

    /**
     * 来源: 自定义
     */
    @Schema(description = "来源: 自定义")
    @Column(name = "source")
    private String source;

    /**
     * 内容: 富文本
     */
    @Schema(description = "内容: 富文本", required = true)
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

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

    public News id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebsiteId() {
        return this.websiteId;
    }

    public News websiteId(Long websiteId) {
        this.setWebsiteId(websiteId);
        return this;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getTitle() {
        return this.title;
    }

    public News title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public News createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public News createDate(LocalDate createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return this.type;
    }

    public News type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return this.author;
    }

    public News author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return this.source;
    }

    public News source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return this.content;
    }

    public News content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public News status(String status) {
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
        if (!(o instanceof News)) {
            return false;
        }
        return id != null && id.equals(((News) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "News{" +
            "id=" + getId() +
            ", websiteId=" + getWebsiteId() +
            ", title='" + getTitle() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", type='" + getType() + "'" +
            ", author='" + getAuthor() + "'" +
            ", source='" + getSource() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
