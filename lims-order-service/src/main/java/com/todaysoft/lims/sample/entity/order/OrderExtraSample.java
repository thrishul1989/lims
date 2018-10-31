package com.todaysoft.lims.sample.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-追加附属样本
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXTRA_SAMPLE")
public class OrderExtraSample extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String creatorId;//追加人ID
    
    private String creatorName;//追加人name
    
    private Date createTime; //追加时间
    
    private List<OrderExtraSampleDetail> extraSampleDetail = new ArrayList<OrderExtraSampleDetail>();
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @OneToMany(mappedBy = "extraSample", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<OrderExtraSampleDetail> getExtraSampleDetail()
    {
        return extraSampleDetail;
    }
    
    public void setExtraSampleDetail(List<OrderExtraSampleDetail> extraSampleDetail)
    {
        this.extraSampleDetail = extraSampleDetail;
    }
    
}
