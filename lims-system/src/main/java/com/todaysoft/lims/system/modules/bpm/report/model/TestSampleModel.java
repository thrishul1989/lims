package com.todaysoft.lims.system.modules.bpm.report.model;

public class TestSampleModel
{
    private String id;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleTypeName;
    
    private String relation;
    
    private String scheduleId;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
    private boolean endFlag;//流程是否正常结束，已暂停、已取消不能再被上报
    
    private int isResampling;//是否重新送样  0不是、1是
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getRelation()
    {
        return relation;
    }
    
    public void setRelation(String relation)
    {
        this.relation = relation;
    }
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
    
    public boolean isEndFlag()
    {
        return endFlag;
    }
    
    public void setEndFlag(boolean endFlag)
    {
        this.endFlag = endFlag;
    }

    public int getIsResampling()
    {
        return isResampling;
    }

    public void setIsResampling(int isResampling)
    {
        this.isResampling = isResampling;
    }
    
}
