package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.order.Order;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "LIMS_ORDER_REFUND")
public class OrderRefund extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Order orderId; //订单ID,
    
    private String orderProductId;//'订单产品关联表IDS，采用主键id1,id2,...',
    
    private String applyReason;//'申请退款原因',
    
    private Integer replyAmount; //'实际允许退款金额',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date updateTime;// '更新时间',
    
    private List<OrderRefundCheck> orderRefundCheckList;
    
    private boolean handler;// 是否处理,
    
    private String bankType; //银行名称
    
    private String bankNo; //银行卡号
    
    private String bankOwnerName; //姓名
    
    public OrderRefund()
    {
        
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    public Order getOrderId()
    {
        
        return orderId;
    }
    
    public void setOrderId(Order orderId)
    {
        
        this.orderId = orderId;
    }
    
    @Column(name = "ORDER_PRODUCT_RELATION_IDS")
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    @Column(name = "APPLY_REASON")
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    @Column(name = "REPLY_AMOUNT")
    public Integer getReplyAmount()
    {
        return replyAmount;
    }
    
    public void setReplyAmount(Integer replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
    @Column(name = "APPLY_TIME")
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @OneToMany(mappedBy = "refundId", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(clause = "checkerTime asc nulls last")
    public List<OrderRefundCheck> getOrderRefundCheckList()
    {
        return orderRefundCheckList;
    }
    
    public void setOrderRefundCheckList(List<OrderRefundCheck> orderRefundCheckList)
    {
        this.orderRefundCheckList = orderRefundCheckList;
    }
    
    @Column(name = "HANDLER")
    public boolean isHandler()
    {
        return handler;
    }
    
    public void setHandler(boolean handler)
    {
        this.handler = handler;
    }
    
    @Column(name = "BANK_TYPE")
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    
    @Column(name = "BANK_NO")
    public String getBankNo()
    {
        return bankNo;
    }
    
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }
    
    @Column(name = "BANK_OWNER_NAME")
    public String getBankOwnerName()
    {
        return bankOwnerName;
    }
    
    public void setBankOwnerName(String bankOwnerName)
    {
        this.bankOwnerName = bankOwnerName;
    }
    
}
