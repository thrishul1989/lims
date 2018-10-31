package com.todaysoft.lims.testing.report.model;

public class ReportTestingPicture
{
    private String id;
    
    private String sampleCode;
    
    private String familyRaletion;
    
    private String methodName;
    
    private String pictureName;
    
    private String pictureUrl;

    private boolean sampleResampling;

    private int sort;//对应家系关系 排序用 1-本人 2-父亲 3-母亲
    
    private int isReSampling;
    
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
    
    public String getFamilyRaletion()
    {
        return familyRaletion;
    }
    
    public void setFamilyRaletion(String familyRaletion)
    {
        this.familyRaletion = familyRaletion;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getPictureName()
    {
        return pictureName;
    }
    
    public void setPictureName(String pictureName)
    {
        this.pictureName = pictureName;
    }
    
    public String getPictureUrl()
    {
        return pictureUrl;
    }
    
    public void setPictureUrl(String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    public boolean isSampleResampling() {
        return sampleResampling;
    }

    public void setSampleResampling(boolean sampleResampling) {
        this.sampleResampling = sampleResampling;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsReSampling()
    {
        return isReSampling;
    }

    public void setIsReSampling(int isReSampling)
    {
        this.isReSampling = isReSampling;
    }
    
}
