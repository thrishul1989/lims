package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce.OrderReduceBuilder;

@Builder
@AllArgsConstructor
public class OrderReduceCheck

{
    
    private String id;
    
    private OrderReduce reduceId;
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private Integer replyAmount; //'实际允许退款金额',
    
    private String checkerId;// '审核人ID',
    
    private String checkerName;// '审核人姓名',
    
    private Date checkerTime;//'审核时间',
    
    private String roleType;//审批人职位额外字段
    
    public OrderReduceCheck()
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
    
    public OrderReduce getReduceId()
    {
        return reduceId;
    }
    
    public void setReduceId(OrderReduce reduceId)
    {
        this.reduceId = reduceId;
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
    
    public Integer getReplyAmount()
    {
        return replyAmount;
    }
    
    public void setReplyAmount(Integer replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
    public String getCheckerId()
    {
        return checkerId;
    }
    
    public void setCheckerId(String checkerId)
    {
        this.checkerId = checkerId;
    }
    
    public String getCheckerName()
    {
        return checkerName;
    }
    
    public void setCheckerName(String checkerName)
    {
        this.checkerName = checkerName;
    }
    
    public Date getCheckerTime()
    {
        return checkerTime;
    }
    
    public void setCheckerTime(Date checkerTime)
    {
        this.checkerTime = checkerTime;
    }

    public String getRoleType()
    {
        return roleType;
    }

    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
}
