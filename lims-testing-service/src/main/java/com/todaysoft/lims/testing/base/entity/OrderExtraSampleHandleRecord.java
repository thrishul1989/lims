package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单-追加附属样本处理记录
 */

@Entity
@Table(name = "LIMS_ORDER_EXTRA_SAMPLE_HANDLE_RECORD")
public class OrderExtraSampleHandleRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 5688254728338617988L;


    private OrderExtraSampleHandle orderExtraSampleHandle;

    private String creatorId;//处理人ID

    private String creatorName;//处理人name

    private Date createTime; //处理时间

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECORD_ID")
    public OrderExtraSampleHandle getOrderExtraSampleHandle() {
        return orderExtraSampleHandle;
    }

    public void setOrderExtraSampleHandle(OrderExtraSampleHandle orderExtraSampleHandle) {
        this.orderExtraSampleHandle = orderExtraSampleHandle;
    }

    @Column(name = "CREATOR_ID")
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Column(name = "CREATOR_NAME")
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
