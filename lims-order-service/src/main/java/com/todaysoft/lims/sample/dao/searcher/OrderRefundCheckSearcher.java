package com.todaysoft.lims.sample.dao.searcher;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.sample.entity.payment.OrderRefund;
@Builder
@AllArgsConstructor
public class OrderRefundCheckSearcher
{
    
    private OrderRefundSearcher refundId;
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private Integer replyAmount; //'实际允许退款金额',
    
    private String checkerId;// '审核人ID',
    
    private String checkerName;// '审核人姓名',
    
    private Date checkerTime;//'审核时间',
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    public OrderRefundCheckSearcher(){
        
    }

    public OrderRefundSearcher getRefundId()
    {
        return refundId;
    }

    public void setRefundId(OrderRefundSearcher refundId)
    {
        this.refundId = refundId;
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
    
}
