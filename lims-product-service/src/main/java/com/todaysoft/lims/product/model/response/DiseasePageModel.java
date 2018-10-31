package com.todaysoft.lims.product.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.product.entity.disease.DiseaseAlias;
import com.todaysoft.lims.product.entity.disease.DiseaseGene;
import com.todaysoft.lims.product.entity.disease.DiseaseHereditary;
import com.todaysoft.lims.product.entity.disease.DiseasePhenotype;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class DiseasePageModel implements Serializable
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
    
    //private List<ProductDisease> productDiseaseList;
    
    private List<SimpleGene> geneList = new ArrayList<SimpleGene>();
    
    private List<DiseaseAlias> alias = new ArrayList<DiseaseAlias>();
    
    private List<DiseaseHereditary> hereditaryList = new ArrayList<DiseaseHereditary>();
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private List<SimplePhenotype> phenotypeList = new ArrayList<SimplePhenotype>();
    
    public DiseasePageModel(Disease entity)
    {
        BeanUtils.copyProperties(entity, this);
        if (Collections3.isNotEmpty(entity.getPhenotypeList()))
        {
            this.phenotypeList = new ArrayList<SimplePhenotype>();
            
            for (DiseasePhenotype d : entity.getPhenotypeList())
            {
                SimplePhenotype p = new SimplePhenotype();
                if (StringUtils.isNotEmpty(d.getPhenotype()))
                {
                    BeanUtils.copyProperties(d.getPhenotype(), p);
                }
                p.setPhneotypeScore(d.getPhneotypeScore());
                this.phenotypeList.add(p);
            }
        }
        
        if (Collections3.isNotEmpty(entity.getGeneList()))
        {
            this.geneList = new ArrayList<SimpleGene>();
            
            for (DiseaseGene dg : entity.getGeneList())
            {
                SimpleGene p = new SimpleGene();
                if (StringUtils.isNotEmpty(dg.getGene()))
                {
                    BeanUtils.copyProperties(dg.getGene(), p);
                }
                p.setGeneScore(dg.getGeneScore());
                this.geneList.add(p);
            }
        }
        
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
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
    
    /* public List<ProductDisease> getProductDiseaseList()
     {
         return productDiseaseList;
     }
     
     public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
     {
         this.productDiseaseList = productDiseaseList;
     }*/
    
    public List<SimpleGene> getGeneList()
    {
        return geneList;
    }
    
    public void setGeneList(List<SimpleGene> geneList)
    {
        this.geneList = geneList;
    }
    
    public List<SimplePhenotype> getPhenotypeList()
    {
        return phenotypeList;
    }
    
    public void setPhenotypeList(List<SimplePhenotype> phenotypeList)
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
    
    public List<DiseaseHereditary> getHereditaryList()
    {
        return hereditaryList;
    }
    
    public void setHereditaryList(List<DiseaseHereditary> hereditaryList)
    {
        this.hereditaryList = hereditaryList;
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
