package com.todaysoft.lims.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT")
public class Contract extends UuidKeyEntity
{
    
    /**
     * 业务库-合同主表
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//合同编号
    
    private String name;//合同名称
    
    private Integer startMode;//启动方式1-系统，2-人工
    
    private String marketingCenter;
    
    @Column(name = "MARKETING_CENTER")
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "START_MODE")
    public Integer getStartMode()
    {
        return startMode;
    }
    
    public void setStartMode(Integer startMode)
    {
        this.startMode = startMode;
    }
}
