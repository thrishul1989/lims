package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

public class OrderPaymentModel
{
    private Date paymentTime;
    
    private Date checkTime;
    
    private Date applyTime;
    
    private Date checkerTime;
    
    private String applyReason;

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    public Date getCheckTime()
    {
        return checkTime;
    }

    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }

    public Date getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public Date getCheckerTime()
    {
        return checkerTime;
    }

    public void setCheckerTime(Date checkerTime)
    {
        this.checkerTime = checkerTime;
    }

    public String getApplyReason()
    {
        return applyReason;
    }

    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    
}
