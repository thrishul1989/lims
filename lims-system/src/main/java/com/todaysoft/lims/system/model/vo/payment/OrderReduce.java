package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.system.model.vo.order.Order;

@Builder
@AllArgsConstructor
public class OrderReduce
{
    
    private String id;
    
    private Order orderId; //订单ID,
    
    private String applyReason;//'申请退款原因',
    
    private Integer applyAmount; //'申请减免退款金额',
    
    private Integer checkAmount; //'申请减免退款金额',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date updateTime;// '更新时间',
    
    private List<OrderReduceCheck> orderReduceCheckList;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Date applyEndTime;//'额外字段',
    
    private String examineeName;//额外字段
    
    private String orderCode;//额外字段
    
    private String orderType;//额外字段
    
    private String ownerId;//额外字段
    
    public OrderReduce()
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
    
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    public Integer getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(Integer applyAmount)
    {
        this.applyAmount = applyAmount;
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
    
    public List<OrderReduceCheck> getOrderReduceCheckList()
    {
        return orderReduceCheckList;
    }
    
    public void setOrderReduceCheckList(List<OrderReduceCheck> orderReduceCheckList)
    {
        this.orderReduceCheckList = orderReduceCheckList;
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
    
    public Integer getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(Integer checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
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
