package com.ruowei.ecsp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 碳汇交易表
 */
@ApiModel(description = "碳汇交易表")
@Entity
@Table(name = "carbon_trade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarbonTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 交易类型: CCER, CEA, ...
     */
    @ApiModelProperty(value = "交易类型: CCER, CEA, ...")
    @Column(name = "type")
    private String type;

    /**
     * 省份名
     */
    @ApiModelProperty(value = "省份名")
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 成交量（吨）
     */
    @ApiModelProperty(value = "成交量（吨）")
    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    /**
     * 成交均价（元/吨）
     */
    @ApiModelProperty(value = "成交均价（元/吨）")
    @Column(name = "average_price", precision = 21, scale = 2)
    private BigDecimal averagePrice;

    /**
     * 成交额（元）
     */
    @ApiModelProperty(value = "成交额（元）")
    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @Column(name = "trade_date")
    private LocalDate tradeDate;

    /**
     * 额外JSON信息
     */
    @ApiModelProperty(value = "额外JSON信息")
    @Lob
    @Column(name = "extra_info")
    private String extraInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarbonTrade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public CarbonTrade type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public CarbonTrade provinceName(String provinceName) {
        this.setProvinceName(provinceName);
        return this;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public CarbonTrade amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAveragePrice() {
        return this.averagePrice;
    }

    public CarbonTrade averagePrice(BigDecimal averagePrice) {
        this.setAveragePrice(averagePrice);
        return this;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public CarbonTrade totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getTradeDate() {
        return this.tradeDate;
    }

    public CarbonTrade tradeDate(LocalDate tradeDate) {
        this.setTradeDate(tradeDate);
        return this;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public CarbonTrade extraInfo(String extraInfo) {
        this.setExtraInfo(extraInfo);
        return this;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarbonTrade)) {
            return false;
        }
        return id != null && id.equals(((CarbonTrade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarbonTrade{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", provinceName='" + getProvinceName() + "'" +
            ", amount=" + getAmount() +
            ", averagePrice=" + getAveragePrice() +
            ", totalPrice=" + getTotalPrice() +
            ", tradeDate='" + getTradeDate() + "'" +
            ", extraInfo='" + getExtraInfo() + "'" +
            "}";
    }
}
