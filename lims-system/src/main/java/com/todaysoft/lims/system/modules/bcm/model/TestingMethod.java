package com.todaysoft.lims.system.modules.bcm.model;

public class TestingMethod
{
    public static final String SANGER = "SANGER";
    
    public static final String SANGERTEST = "SANGER-TEST";

    public static final String QPCR = "QPCR";

    public static final String DT = "DT";

    public static final String MLPA = "MLPA";

    public static final String FLUOTEST = "FLUO-TEST";

    public static final String PCRNGS = "PCR-NGS";

    public static final String TECHNICAL = "TECHNICAL";

    private String id;
    
    private String name;
    
    private String type;
    
    private boolean capture;
    
    private String semantic;
    
    public String getPlatForm()
    {
        return platForm;
    }
    
    public void setPlatForm(String platForm)
    {
        this.platForm = platForm;
    }
    
    private boolean analyse;
    
    private String description;
    
    private String testingProcess;
    
    private String analyseProcess;
    
    private String platForm;
    
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
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public boolean isCapture()
    {
        return capture;
    }
    
    public void setCapture(boolean capture)
    {
        this.capture = capture;
    }
    
    public boolean isAnalyse()
    {
        return analyse;
    }
    
    public void setAnalyse(boolean analyse)
    {
        this.analyse = analyse;
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
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
}
