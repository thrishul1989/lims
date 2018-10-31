package com.todaysoft.lims.system.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class Phenotype implements Serializable
{
    
    private String id;
    
    private String name;
    
    private String code;
    
    private String enName;
    
    private Date createTime;
    
    private Date deleteTime;
    
    private String description;
    
    private Double phneotypeScore;
    
    private String descriptionEn;
    
    private Integer isOverwrite;//页面展示字段
    
    public Integer getIsOverwrite()
    {
        return isOverwrite;
    }
    
    public void setIsOverwrite(Integer isOverwrite)
    {
        this.isOverwrite = isOverwrite;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public Double getPhneotypeScore()
    {
        return phneotypeScore;
    }
    
    public void setPhneotypeScore(Double phneotypeScore)
    {
        this.phneotypeScore = phneotypeScore;
    }
    
    private List<Disease> list;
    
    public List<Disease> getList()
    {
        return list;
    }
    
    public void setList(List<Disease> list)
    {
        this.list = list;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "HPO编号", align = 2, sort = 10)
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @ExcelField(title = "名称(中文)", align = 2, sort = 30)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @ExcelField(title = "名称(英文)", align = 2, sort = 20)
    public String getEnName()
    {
        return enName;
    }
    
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    
    @ExcelField(title = "定义(英文)", align = 2, sort = 40)
    public String getDescriptionEn()
    {
        return descriptionEn;
    }
    
    public void setDescriptionEn(String descriptionEn)
    {
        this.descriptionEn = descriptionEn;
    }
    
    @ExcelField(title = "定义(中文)", align = 2, sort = 50)
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        
        return "code:" + code + "," + "name:" + name + "," + "enName:" + enName + " " + "description:" + description + "descriptionEn:" + descriptionEn;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this.code.equals(((Phenotype)obj).getCode()))
            return true;
        else
        {
            return false;
        }
    }
}
