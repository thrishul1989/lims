package com.todaysoft.lims.system.modules.reportexport.entity;

import java.util.List;

public class MutationFamilySangerReportSample
{
    private String sampleName;
    
    private String sampleCode;
    
    private String combineCode;
    
    private String combineType;
    
    private List<MutationFamilySangerReportPicture> pictures;

    public String getSampleName()
    {
        return sampleName;
    }

    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getCombineCode()
    {
        return combineCode;
    }

    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }

    public String getCombineType()
    {
        return combineType;
    }

    public void setCombineType(String combineType)
    {
        this.combineType = combineType;
    }

    public List<MutationFamilySangerReportPicture> getPictures()
    {
        return pictures;
    }

    public void setPictures(List<MutationFamilySangerReportPicture> pictures)
    {
        this.pictures = pictures;
    }
    
    
}
