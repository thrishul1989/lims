package com.todaysoft.lims.testing.extrasample.model;

import java.util.Date;

public class ExtraSampleShowProducts {

    private String productName;

    private Integer refundStatus;//产品退款状态，0:未申请；1：退款审核中；2：退款中；3：已退款；

    private Integer reportStatus;//报告状态：0-未出报告，1-待一审，2-待二审，3-待寄送，4-已寄送 5-不寄送

    private Integer productStatus;//产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成；

    private Date reportTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
