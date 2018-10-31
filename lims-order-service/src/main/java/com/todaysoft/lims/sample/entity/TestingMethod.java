package com.todaysoft.lims.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_METHOD")
public class TestingMethod extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 4436035396328618357L;
    
    private String name;
    
    private String type;
    
    private boolean capture;
    
    private boolean analyse;
    
    private String description;
    
    private String testingProcess;
    
    private String analyseProcess;
    
    private boolean deleted;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    @Column(name = "IS_CAPTURE")
    public boolean isCapture()
    {
        return capture;
    }
    
    public void setCapture(boolean capture)
    {
        this.capture = capture;
    }
    
    @Column(name = "IS_ANALYSE")
    public boolean isAnalyse()
    {
        return analyse;
    }
    
    public void setAnalyse(boolean analyse)
    {
        this.analyse = analyse;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
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
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
}
