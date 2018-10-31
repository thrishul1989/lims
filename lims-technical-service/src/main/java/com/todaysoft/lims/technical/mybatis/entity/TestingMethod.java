package com.todaysoft.lims.technical.mybatis.entity;

public class TestingMethod
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
    
    private String id;
    
    private String name;
    
    private String platform;
    
    private Integer type;
    
    private Boolean isCapture;
    
    private Boolean isAnalyse;
    
    private String description;
    
    private String testingProcess;
    
    private String analyseProcess;
    
    private Boolean delFlag;
    
    private String semantic;
    
    private Boolean enabled;
    
    private String inputSampleType;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPlatform()
    {
        return platform;
    }
    
    public void setPlatform(String platform)
    {
        this.platform = platform;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public Boolean getIsCapture()
    {
        return isCapture;
    }
    
    public void setIsCapture(Boolean isCapture)
    {
        this.isCapture = isCapture;
    }
    
    public Boolean getIsAnalyse()
    {
        return isAnalyse;
    }
    
    public void setIsAnalyse(Boolean isAnalyse)
    {
        this.isAnalyse = isAnalyse;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTestingProcess()
    {
        return testingProcess;
    }
    
    public void setTestingProcess(String testingProcess)
    {
        this.testingProcess = testingProcess;
    }
    
    public String getAnalyseProcess()
    {
        return analyseProcess;
    }
    
    public void setAnalyseProcess(String analyseProcess)
    {
        this.analyseProcess = analyseProcess;
    }
    
    public Boolean getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public Boolean getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public String getInputSampleType()
    {
        return inputSampleType;
    }
    
    public void setInputSampleType(String inputSampleType)
    {
        this.inputSampleType = inputSampleType;
    }
    
}