package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_METHOD")
public class TestingMethod implements Serializable
{
    public static final String SANGER = "SANGER";
    
    public static final String QPCR = "QPCR";
    
    public static final String MLPA_TEST = "MLPA-TEST";
    
    public static final String MLPA = "MLPA";
    
    public static final String PCR_NGS = "PCR-NGS";
    
    public static final String SANGER_TEST = "SANGER-TEST";
    
    public static final String MULTI_PCR = "MPCR";
    
    public static final String LONG_PCR = "LPCR";

    public static final String FLUO_TEST = "FLUO-TEST";

    public static final String DT = "DT";
    
    private static final long serialVersionUID = 8541993095895887950L;
    
    private String id;
    
    private String name;
    
    private String platform;
    
    private String type;
    
    private String testingProcess;
    
    private String analyseProcess;
    
    private String semantic;
    
    private boolean enabled;
    
    private String inputSampleTypeId;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    @Column(name = "PLATFORM")
    public String getPlatform()
    {
        return platform;
    }
    
    public void setPlatform(String platform)
    {
        this.platform = platform;
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
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    @Column(name = "ENABLED")
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    @Column(name = "INPUT_SAMPLE_TYPE")
    public String getInputSampleTypeId()
    {
        return inputSampleTypeId;
    }
    
    public void setInputSampleTypeId(String inputSampleTypeId)
    {
        this.inputSampleTypeId = inputSampleTypeId;
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
}
