package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_RESEARCH_SAMPLE")
public class OrderResearchSample extends UuidKeyEntity
{
    private static final long serialVersionUID = -8357130694936031435L;
    
    public static final String RECEIVE_STATUS_UNDELIVERED = "0";
    
    public static final String RECEIVE_STATUS_DELIVERING = "1";
    
    public static final String RECEIVE_STATUS_ERROR = "3";
    
    public static final String ERROR_TYPE_DELIVERY = "0";
    
    public static final String ERROR_TYPE_TESTING = "1";
    
    public static final String ERROR_SOLVE_STATUS_UNSOLVED = "0";
    
    private String orderId;
    
    private String sampleCode;
    
    private String sampleTypeId;
    
    private String receiveStatus;
    
    private String errorType;
    
    private String errorSolveStatus;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @Column(name = "SAMPLE_PACKAGE_STATUS")
    public String getReceiveStatus()
    {
        return receiveStatus;
    }
    
    public void setReceiveStatus(String receiveStatus)
    {
        this.receiveStatus = receiveStatus;
    }
    
    @Column(name = "SAMPLE_ERROR_TYPE")
    public String getErrorType()
    {
        return errorType;
    }
    
    public void setErrorType(String errorType)
    {
        this.errorType = errorType;
    }
    
    @Column(name = "SAMPLE_ERROR_STATUS")
    public String getErrorSolveStatus()
    {
        return errorSolveStatus;
    }
    
    public void setErrorSolveStatus(String errorSolveStatus)
    {
        this.errorSolveStatus = errorSolveStatus;
    }
}
