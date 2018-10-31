package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.order.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class OrderDelay
{
    private String id;
    
    private Order orderId; //订单ID,
    
    private String delayReason;//'申请延迟原因',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date updateTime;//'更新时间',
    
    private Date planPayTime;//'计划延迟时间',
    
    private Date replyPayTime;//'审批延迟时间',
    
    private List<OrderDelayCheck> orderDelayCheckList;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Date applyEndTime;//'额外字段',
    
    private String examineeName;//额外字段
    
    private String orderCode;//额外字段
    
    private String orderType;//额外字段
    
    private String ownerId;//额外字段
    
    public OrderDelay()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Order getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(Order orderId)
    {
        this.orderId = orderId;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public Date getPlanPayTime()
    {
        return planPayTime;
    }
    
    public void setPlanPayTime(Date planPayTime)
    {
        this.planPayTime = planPayTime;
    }
    
    public Date getReplyPayTime()
    {
        return replyPayTime;
    }
    
    public void setReplyPayTime(Date replyPayTime)
    {
        this.replyPayTime = replyPayTime;
    }
    
    public List<OrderDelayCheck> getOrderDelayCheckList()
    {
        return orderDelayCheckList;
    }
    
    public void setOrderDelayCheckList(List<OrderDelayCheck> orderDelayCheckList)
    {
        this.orderDelayCheckList = orderDelayCheckList;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getDelayReason()
    {
        return delayReason;
    }
    
    public void setDelayReason(String delayReason)
    {
        this.delayReason = delayReason;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
}
