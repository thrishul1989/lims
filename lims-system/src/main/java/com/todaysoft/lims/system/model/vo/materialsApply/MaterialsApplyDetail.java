package com.todaysoft.lims.system.model.vo.materialsApply;

import com.todaysoft.lims.utils.StringUtils;

public class MaterialsApplyDetail
{
    private String id;
    
    private String applyId;//申请id
    
    private String materialsType;//物资类别
    
    private String materialsName;//物资名称
    
    private String remark;//备注
    
    private Integer materialsCount;//申请物资数量
    
    private Integer materialsSendCount;//寄送物资数量
    
    private Integer count;//页面显示
    
    private String materialsTypeName;//页面显示
    
    public String getMaterialsTypeName()
    {
        return materialsTypeName;
    }
    
    public void setMaterialsTypeName(String materialsTypeName)
    {
        this.materialsTypeName = materialsTypeName;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    public String getMaterialsType()
    {
        return materialsType;
    }
    
    public void setMaterialsType(String materialsType)
    {
        this.materialsType = materialsType;
    }
    
    public String getMaterialsName()
    {
        return materialsName;
    }
    
    public void setMaterialsName(String materialsName)
    {
        this.materialsName = materialsName;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getMaterialsCount()
    {
        return materialsCount;
    }
    
    public void setMaterialsCount(Integer materialsCount)
    {
        this.materialsCount = materialsCount;
    }
    
    public Integer getMaterialsSendCount()
    {
        if (!StringUtils.isNotEmpty(materialsSendCount))
        {
            return 0;
        }
        return materialsSendCount;
    }
    
    public void setMaterialsSendCount(Integer materialsSendCount)
    {
        this.materialsSendCount = materialsSendCount;
    }
    
}
