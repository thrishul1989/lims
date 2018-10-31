package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 订单-追加附属样本待处理样本
 */

@Entity
@Table(name = "LIMS_ORDER_EXTRA_SAMPLE_HANDLE")
public class OrderExtraSampleHandle extends UuidKeyEntity
{

    private static final long serialVersionUID = 5524831398068252407L;

    private String sampleId;

    private Integer purpose;//1-验证  2-检测 3-对照

    private String orderId;
    
    private Integer status;//0-未处理  1已处理

    private Date createTime;

    @Column(name="SAMPLE_ID")
    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Column(name="PURPOSE")
    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    @Column(name="ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name="STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
