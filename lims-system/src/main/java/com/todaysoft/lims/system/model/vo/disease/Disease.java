package com.todaysoft.lims.system.model.vo.disease;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class Disease implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String code;
    
    private String name;
    
    private String nameEn; //疾病英文名称 
    
    private String incidence; //发病率
    
    private String onsetAge; // '发病年龄',
    
    private String mutationType; // 突变类型
    
    private String diseaseType; // 疾病类型：字典-DISEASE_TYPE
    
    private String description; //疾病简介
    
    private List<SimpleGene> geneList = new ArrayList<SimpleGene>();
    
    private List<DiseaseAlias> alias = new ArrayList<DiseaseAlias>();
    
    private List<DiseaseHereditary> hereditaryList = new ArrayList<DiseaseHereditary>();
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    //private List<ProductDisease> productDiseaseList;
    
    private String hereditaryType;//遗传方式
    
    private List<Phenotype> phenotypeList = new ArrayList<Phenotype>();
    
    private List<String> documents = new ArrayList<String>(); //关联文献
    
    public List<String> getDocuments()
    {
        return documents;
    }
    
    public void setDocuments(List<String> documents)
    {
        this.documents = documents;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public List<SimpleGene> getGeneList()
    {
        return geneList;
    }
    
    public void setGeneList(List<SimpleGene> geneList)
    {
        this.geneList = geneList;
    }
    
    public List<Phenotype> getPhenotypeList()
    {
        return phenotypeList;
    }
    
    public void setPhenotypeList(List<Phenotype> phenotypeList)
    {
        this.phenotypeList = phenotypeList;
    }
    
    public List<DiseaseHereditary> getHereditaryList()
    {
        return hereditaryList;
    }
    
    public void setHereditaryList(List<DiseaseHereditary> hereditaryList)
    {
        this.hereditaryList = hereditaryList;
    }
    
    public String getHereditaryType()
    {
        return hereditaryType;
    }
    
    public void setHereditaryType(String hereditaryType)
    {
        this.hereditaryType = hereditaryType;
    }
    
    public List<DiseaseAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<DiseaseAlias> alias)
    {
        this.alias = alias;
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
    
    /*public List<ProductDisease> getProductDiseaseList()
    {
        return productDiseaseList;
    }
    
    public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
    {
        this.productDiseaseList = productDiseaseList;
    }*/
    
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
