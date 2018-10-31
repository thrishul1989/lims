package com.todaysoft.lims.sample.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CHECKMANAGEMENT_CHECK")
public class CheckManagementCheck extends UuidKeyEntity
{
    private static final long serialVersionUID = 1483063455889881796L;
    
    private CheckManagement checkManagement;// 检测管理
    
    private Integer checkId;// 检测ID
    
    private Integer checkOrder;// 检测顺序
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKMANAGEMENT_ID")
    @JsonIgnore
    public CheckManagement getCheckManagement()
    {
        return checkManagement;
    }
    
    public void setCheckManagement(CheckManagement checkManagement)
    {
        this.checkManagement = checkManagement;
    }
    
    @Column(name = "CHECK_ID")
    public Integer getCheckId()
    {
        return checkId;
    }
    
    public void setCheckId(Integer checkId)
    {
        this.checkId = checkId;
    }
    
    @Column(name = "CHECK_ORDER")
    public Integer getCheckOrder()
    {
        return checkOrder;
    }
    
    public void setCheckOrder(Integer checkOrder)
    {
        this.checkOrder = checkOrder;
    }
    
}
