package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;







import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;



@Builder
@AllArgsConstructor
@Entity
@Table(name = "LIMS_ORDER_DELAY_CHECK")
public class OrderDelayCheck extends UuidKeyEntity
{
 private static final long serialVersionUID = 1L;
    
    private OrderDelay delayId;
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private Date replyPayTime;//'审核时间'
    
    private String checkerId;// '审核人ID',
    
    private String checkerName;// '审核人姓名',
    
    private Date checkerTime;//'审核时间',
    
    
    public OrderDelayCheck(){
        
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLY_ID")
    @JsonIgnore
    public OrderDelay getDelayId()
    {
        return delayId;
    }


    public void setDelayId(OrderDelay delayId)
    {
        this.delayId = delayId;
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




    @Column(name = "CHECKER_ID")
    public String getCheckerId()
    {
        return checkerId;
    }


    public void setCheckerId(String checkerId)
    {
        this.checkerId = checkerId;
    }


    @Column(name = "CHECKER_NAME")
    public String getCheckerName()
    {
        return checkerName;
    }


    public void setCheckerName(String checkerName)
    {
        this.checkerName = checkerName;
    }


    @Column(name = "CHECKER_TIME")
    public Date getCheckerTime()
    {
        return checkerTime;
    }


    public void setCheckerTime(Date checkerTime)
    {
        this.checkerTime = checkerTime;
    }


    @Column(name = "REPLY_PAY_TIME")
    public Date getReplyPayTime()
    {
        return replyPayTime;
    }


    public void setReplyPayTime(Date replyPayTime)
    {
        this.replyPayTime = replyPayTime;
    }
}
