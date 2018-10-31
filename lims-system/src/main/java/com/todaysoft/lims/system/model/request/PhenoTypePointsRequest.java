package com.todaysoft.lims.system.model.request;

import java.util.List;

public class PhenoTypePointsRequest
{
    
    //  private String dataCode;
    
    // private boolean ifSingleSample;
    
    private List<String> hpoCode;
    
    private String examineeSex; //受检人性别
    
    private String familyGroupId; //通过任务id 查询是否家系、实验样本号
    
    public List<String> getHpoCode()
    {
        return hpoCode;
    }
    
    public void setHpoCode(List<String> hpoCode)
    {
        this.hpoCode = hpoCode;
    }
    
    public String getExamineeSex()
    {
        return examineeSex;
    }
    
    public void setExamineeSex(String examineeSex)
    {
        this.examineeSex = examineeSex;
    }
    
    public String getFamilyGroupId()
    {
        return familyGroupId;
    }
    
    public void setFamilyGroupId(String familyGroupId)
    {
        this.familyGroupId = familyGroupId;
    }
}
