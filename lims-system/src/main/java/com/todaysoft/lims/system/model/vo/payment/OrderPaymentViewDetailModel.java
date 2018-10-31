package com.todaysoft.lims.system.model.vo.payment;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.lims.utils.StringUtils;

/**
 * 款动查看详情
 * @description:
 * @author ww
 * @date 2017年4月25日 下午4:20:05
 */
public class OrderPaymentViewDetailModel
{
    private String paymentType;
    
    private String payType;
    
    private String posNo;
    
    private String receiptRolls;//收据单号
    
    private String transferMember;//转账人
    
    private String transferNo;//转账卡号
    
    private Date transferDate;//转账日期
    
    private String paymenter;//缴费人或退款人 
    
    private Date paymentTime;//缴费日期或退款时间
    
    private Integer paymentAmount;
    
    private String paymentRealAmount; //真是价格 【元】 (变动金额)
    
    private String instrumentImg;//凭证
    
    private String instrumentImgShow; //用于前台显示凭证信息
    
    private String zremark;//支付备注
    
    private String remark;//确认备注或退款备注
    
    private String checkName;//操作人
    
    private Date checkTime;//操作时间
    
    private String tradeNo;//支付宝交易号
    
    /***********new***************/
    
    private String merNum;
    
    private String termId;
    
    private String tranDate;
    
    private String referNo;
    
    private String batchNo;
    
    private String serialNo;
    
    private String tranType;
    
    private String wangPayType;
    
    private String orderCode;
    
    public String getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    public String getTransferMember()
    {
        return transferMember;
    }
    
    public void setTransferMember(String transferMember)
    {
        this.transferMember = transferMember;
    }
    
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }
    
    public Date getTransferDate()
    {
        return transferDate;
    }
    
    public void setTransferDate(Date transferDate)
    {
        this.transferDate = transferDate;
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public Integer getPaymentAmount()
    {
        return paymentAmount;
    }
    
    public void setPaymentAmount(Integer paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }
    
    public BigDecimal getPaymentRealAmount()
    {
        
        if (StringUtils.isNotEmpty(paymentAmount))
        {
            return BigDecimal.valueOf(paymentAmount).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setPaymentRealAmount(String paymentRealAmount)
    {
        this.paymentRealAmount = paymentRealAmount;
    }
    
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }
    
    public String getInstrumentImgShow()
    {
        return instrumentImgShow;
    }
    
    public void setInstrumentImgShow(String instrumentImgShow)
    {
        this.instrumentImgShow = instrumentImgShow;
    }
    
    public String getZremark()
    {
        return zremark;
    }
    
    public void setZremark(String zremark)
    {
        this.zremark = zremark;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public String getMerNum()
    {
        return merNum;
    }
    
    public void setMerNum(String merNum)
    {
        this.merNum = merNum;
    }
    
    public String getTermId()
    {
        return termId;
    }
    
    public void setTermId(String termId)
    {
        this.termId = termId;
    }
    
    public String getTranDate()
    {
        return tranDate;
    }
    
    public void setTranDate(String tranDate)
    {
        this.tranDate = tranDate;
    }
    
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public String getSerialNo()
    {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    
    public String getTranType()
    {
        return tranType;
    }
    
    public void setTranType(String tranType)
    {
        this.tranType = tranType;
    }
    
    public String getWangPayType()
    {
        return wangPayType;
    }
    
    public void setWangPayType(String wangPayType)
    {
        this.wangPayType = wangPayType;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getTradeNo()
    {
        return tradeNo;
    }
    
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }
    
}
