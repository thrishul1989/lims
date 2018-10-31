package com.todaysoft.lims.system.model.vo.disease;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class DiseaseFormRequest
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String nameEn; //疾病英文名称 
    
    private String incidence; //发病率
    
    private String onsetAge; // '发病年龄',
    
    private String mutationType; // 突变类型
    
    private String diseaseType; // 疾病类型：字典-DISEASE_TYPE
    
    private String description; //疾病简介
    
    private String genes; //前台gene JSON串
    
    private String phenotypes;//前台表型 json串
    
    private String aliasJson; //前台别名json串
    
    private String hereditaryType;//遗传方式
    
    private List<Gene> geneList = new ArrayList<Gene>();
    
    private List<Phenotype> phenotypeList = new ArrayList<Phenotype>();
    
    private List<DiseaseAlias> alias = new ArrayList<DiseaseAlias>();
    
    public List<Gene> getGeneList()
    {
        return geneList;
    }
    
    public void setGeneList(List<Gene> geneList)
    {
        this.geneList = geneList;
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
    
    @ExcelField(title = "疾病英文名称", align = 2, sort = 30)
    public String getNameEn()
    {
        return nameEn;
    }
    
    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }
    
    @ExcelField(title = "发病率", align = 2, sort = 40)
    public String getIncidence()
    {
        return incidence;
    }
    
    public void setIncidence(String incidence)
    {
        this.incidence = incidence;
    }
    
    @ExcelField(title = "发病年龄", align = 2, sort = 50)
    public String getOnsetAge()
    {
        return onsetAge;
    }
    
    public void setOnsetAge(String onsetAge)
    {
        this.onsetAge = onsetAge;
    }
    
    @ExcelField(title = "突变类型", align = 2, sort = 60)
    public String getMutationType()
    {
        return mutationType;
    }
    
    public void setMutationType(String mutationType)
    {
        this.mutationType = mutationType;
    }
    
    @ExcelField(title = "疾病类型", align = 2, sort = 70)
    public String getDiseaseType()
    {
        return diseaseType;
    }
    
    public void setDiseaseType(String diseaseType)
    {
        this.diseaseType = diseaseType;
    }
    
    @ExcelField(title = "疾病简介", align = 2, sort = 80)
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<Phenotype> getPhenotypeList()
    {
        return phenotypeList;
    }
    
    public void setPhenotypeList(List<Phenotype> phenotypeList)
    {
        this.phenotypeList = phenotypeList;
    }
    
    public List<DiseaseAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<DiseaseAlias> alias)
    {
        this.alias = alias;
    }
    
    public String getHereditaryType()
    {
        return hereditaryType;
    }
    
    public void setHereditaryType(String hereditaryType)
    {
        this.hereditaryType = hereditaryType;
    }
    
    public String getPhenotypes()
    {
        return phenotypes;
    }
    
    public void setPhenotypes(String phenotypes)
    {
        this.phenotypes = phenotypes;
    }
    
    public String getGenes()
    {
        return genes;
    }
    
    public void setGenes(String genes)
    {
        this.genes = genes;
    }
    
    public String getAliasJson()
    {
        return aliasJson;
    }
    
    public void setAliasJson(String aliasJson)
    {
        this.aliasJson = aliasJson;
    }
    
}
