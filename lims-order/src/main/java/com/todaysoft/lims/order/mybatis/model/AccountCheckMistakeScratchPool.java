package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccountCheckMistakeScratchPool {
    private String id;

    private Date createTime;

    private Date paymentTime;

    private String checkTaskId;

    private String orderNo;

    private String referNo;

    private BigDecimal checkAmount;

    private Integer payType;

    private String batchNo;

    private String billDate;

    private String merNum;

    private String termId;

    private String tranDate;

    private String tranType;

    private String oSerialno;

    private String oBatchno;

    private String serialNo;

    private String wangPayType;

    private String ext1;

    private String ext2;

    private String addVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCheckTaskId() {
        return checkTaskId;
    }

    public void setCheckTaskId(String checkTaskId) {
        this.checkTaskId = checkTaskId == null ? null : checkTaskId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo == null ? null : referNo.trim();
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate == null ? null : billDate.trim();
    }

    public String getMerNum() {
        return merNum;
    }

    public void setMerNum(String merNum) {
        this.merNum = merNum == null ? null : merNum.trim();
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId == null ? null : termId.trim();
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType == null ? null : tranType.trim();
    }

    public String getoSerialno() {
        return oSerialno;
    }

    public void setoSerialno(String oSerialno) {
        this.oSerialno = oSerialno == null ? null : oSerialno.trim();
    }

    public String getoBatchno() {
        return oBatchno;
    }

    public void setoBatchno(String oBatchno) {
        this.oBatchno = oBatchno == null ? null : oBatchno.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getWangPayType() {
        return wangPayType;
    }

    public void setWangPayType(String wangPayType) {
        this.wangPayType = wangPayType == null ? null : wangPayType.trim();
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getAddVal() {
        return addVal;
    }

    public void setAddVal(String addVal) {
        this.addVal = addVal == null ? null : addVal.trim();
    }
}