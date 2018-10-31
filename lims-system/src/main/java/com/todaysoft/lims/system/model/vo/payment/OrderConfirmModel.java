package com.todaysoft.lims.system.model.vo.payment;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class OrderConfirmModel
{
    
    private String orderCode;
    
    private String payType;
    
    private String posNo;
    
    private String receiptRolls;
    
    private String transferMember;//转账人
    
    private String transferNo;//转账卡号
    
    private String transferDate;//转账日期
    
    private String transRemark;
    
    private String realPrice;
    
    private String reduceApply;
    
    private String reduceStatus;
    
    private String applyPrice;
    
    private String checkPrice;
    
    private String paymenter;
    
    private String paymentTime;
    
    private String incommingAmoun;
    
    private String remark;
    
    @ExcelField(title = "*订单编号", align = 2, sort = 10)
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @ExcelField(title = "*支付方式", align = 2, sort = 20)
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    @ExcelField(title = "（交易）参考号", align = 2, sort = 30)
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    @ExcelField(title = "收据单号", align = 2, sort = 40)
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    @ExcelField(title = "*转账人", align = 2, sort = 50)
    public String getTransferMember()
    {
        return transferMember;
    }
    
    public void setTransferMember(String transferMember)
    {
        this.transferMember = transferMember;
    }
    
    @ExcelField(title = "*转账日期", align = 2, sort = 60)
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }
    
    @ExcelField(title = "转账卡号", align = 2, sort = 70)
    public String getTransferDate()
    {
        return transferDate;
    }
    
    public void setTransferDate(String transferDate)
    {
        this.transferDate = transferDate;
    }
    
    @ExcelField(title = "支付备注", align = 2, sort = 80)
    public String getTransRemark()
    {
        return transRemark;
    }
    
    public void setTransRemark(String transRemark)
    {
        this.transRemark = transRemark;
    }
    
    @ExcelField(title = "*应付款", align = 2, sort = 90)
    public String getRealPrice()
    {
        return realPrice;
    }
    
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }
    
    @ExcelField(title = "减免申请", align = 2, sort = 100)
    public String getReduceApply()
    {
        return reduceApply;
    }
    
    public void setReduceApply(String reduceApply)
    {
        this.reduceApply = reduceApply;
    }
    
    @ExcelField(title = "减免状态", align = 2, sort = 110)
    public String getReduceStatus()
    {
        return reduceStatus;
    }
    
    public void setReduceStatus(String reduceStatus)
    {
        this.reduceStatus = reduceStatus;
    }
    
    @ExcelField(title = "申请金额", align = 2, sort = 120)
    public String getApplyPrice()
    {
        return applyPrice;
    }
    
    public void setApplyPrice(String applyPrice)
    {
        this.applyPrice = applyPrice;
    }
    
    @ExcelField(title = "审批金额", align = 2, sort = 130)
    public String getCheckPrice()
    {
        return checkPrice;
    }
    
    public void setCheckPrice(String checkPrice)
    {
        this.checkPrice = checkPrice;
    }
    
    @ExcelField(title = "付款人", align = 2, sort = 140)
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    @ExcelField(title = "*缴费日期", align = 2, sort = 150)
    public String getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(String paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    @ExcelField(title = "*确认到账金额", align = 2, sort = 160)
    public String getIncommingAmoun()
    {
        return incommingAmoun;
    }
    
    public void setIncommingAmoun(String incommingAmoun)
    {
        this.incommingAmoun = incommingAmoun;
    }
    
    @ExcelField(title = "备注", align = 2, sort = 170)
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
