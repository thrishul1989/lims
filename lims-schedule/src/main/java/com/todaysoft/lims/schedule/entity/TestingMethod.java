package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_METHOD")
public class TestingMethod extends UuidKeyEntity
{
    private static final long serialVersionUID = 6472646337414123808L;
    
    private String name;
    
    private String testingProcess;
    
    private String analyseProcess;
    
    private Integer delFlag;
    
    private String semantic;
    
    private String type;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "TESTING_PROCESS")
    public String getTestingProcess()
    {
        return testingProcess;
    }
    
    public void setTestingProcess(String testingProcess)
    {
        this.testingProcess = testingProcess;
    }
    
    @Column(name = "ANALYSE_PROCESS")
    public String getAnalyseProcess()
    {
        return analyseProcess;
    }
    
    public void setAnalyseProcess(String analyseProcess)
    {
        this.analyseProcess = analyseProcess;
    }
    
    @Column(name = "DEL_FLAG")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    @Column(name = "TYPE")
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
