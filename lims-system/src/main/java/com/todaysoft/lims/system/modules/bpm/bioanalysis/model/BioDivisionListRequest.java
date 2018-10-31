package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;


import java.util.Date;

public class BioDivisionListRequest {

    private String sequencingCode;

    private Date createStartDate;

    private Date createEndDate;

    private Integer status;

    private Integer pageNo;

    private Integer pageSize;

    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
