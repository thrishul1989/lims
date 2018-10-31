package com.todaysoft.lims.system.model.vo.order;

public class OrderPhenotypePointRequest extends OrderFormRequest
{
    private String familyGroupId;
    
    public String getFamilyGroupId()
    {
        return familyGroupId;
    }
    
    public void setFamilyGroupId(String familyGroupId)
    {
        this.familyGroupId = familyGroupId;
    }
    
}
