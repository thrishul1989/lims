package com.todaysoft.lims.system.model.vo.disease;

import java.io.Serializable;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class SimpleDisease implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;
    
    private String name;
    
    private String id;
    
    private String nameEn; //疾病英文名称 DiseaseGene
    
    private String incidence; //发病率
    
    private String onsetAge; // '发病年龄',
    
    private String mutationType; // 突变类型
    
    private String diseaseType; // 疾病类型：字典-DISEASE_TYPE
    
    private String description; //疾病简介
    
    private String hereditaryType;//遗传方式
    
    public String getHereditaryType()
    {
        return hereditaryType;
    }
    
    public void setHereditaryType(String hereditaryType)
    {
        this.hereditaryType = hereditaryType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "疾病编号", align = 2, sort = 10)
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @ExcelField(title = "疾病名称", align = 2, sort = 20)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getNameEn()
    {
        return nameEn;
    }
    
    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }
    
    public String getIncidence()
    {
        return incidence;
    }
    
    public void setIncidence(String incidence)
    {
        this.incidence = incidence;
    }
    
    public String getOnsetAge()
    {
        return onsetAge;
    }
    
    public void setOnsetAge(String onsetAge)
    {
        this.onsetAge = onsetAge;
    }
    
    public String getMutationType()
    {
        return mutationType;
    }
    
    public void setMutationType(String mutationType)
    {
        this.mutationType = mutationType;
    }
    
    public String getDiseaseType()
    {
        return diseaseType;
    }
    
    public void setDiseaseType(String diseaseType)
    {
        this.diseaseType = diseaseType;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
