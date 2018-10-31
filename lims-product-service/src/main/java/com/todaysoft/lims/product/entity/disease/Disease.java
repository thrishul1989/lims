package com.todaysoft.lims.product.entity.disease;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.ProductDisease;

@Entity
@Table(name = "LIMS_DISEASE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "geneList"})
public class Disease extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;
    
    private String name;
    
    private String nameEn; //疾病英文名称 
    
    private String incidence; //发病率
    
    private String onsetAge; // '发病年龄',
    
    private String mutationType; // 突变类型
    
    private String diseaseType; // 疾病类型：字典-DISEASE_TYPE
    
    private String description; //疾病简介
    
    private List<ProductDisease> productDiseaseList;
    
    private List<DiseaseGene> geneList = new ArrayList<DiseaseGene>();
    
    private List<DiseasePhenotype> phenotypeList = new ArrayList<DiseasePhenotype>();
    
    private List<DiseaseAlias> alias = new ArrayList<DiseaseAlias>();
    
    private List<DiseaseHereditary> hereditaryList = new ArrayList<DiseaseHereditary>();
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    @OneToMany(mappedBy = "disease", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<ProductDisease> getProductDiseaseList()
    {
        return productDiseaseList;
    }
    
    public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
    {
        this.productDiseaseList = productDiseaseList;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "NAME_EN")
    public String getNameEn()
    {
        return nameEn;
    }
    
    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }
    
    @Column(name = "INCIDENCE")
    public String getIncidence()
    {
        return incidence;
    }
    
    public void setIncidence(String incidence)
    {
        this.incidence = incidence;
    }
    
    @Column(name = "ONSET_AGE")
    public String getOnsetAge()
    {
        return onsetAge;
    }
    
    public void setOnsetAge(String onsetAge)
    {
        this.onsetAge = onsetAge;
    }
    
    @Column(name = "MUTATION_TYPE")
    public String getMutationType()
    {
        return mutationType;
    }
    
    public void setMutationType(String mutationType)
    {
        this.mutationType = mutationType;
    }
    
    @Column(name = "DISEASE_TYPE")
    public String getDiseaseType()
    {
        return diseaseType;
    }
    
    public void setDiseaseType(String diseaseType)
    {
        this.diseaseType = diseaseType;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @OneToMany(mappedBy = "diseaseId", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<DiseaseAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<DiseaseAlias> alias)
    {
        this.alias = alias;
    }
    
    @OneToMany(mappedBy = "disease", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<DiseaseHereditary> getHereditaryList()
    {
        return hereditaryList;
    }
    
    public void setHereditaryList(List<DiseaseHereditary> hereditaryList)
    {
        this.hereditaryList = hereditaryList;
    }
    
    @ManyToMany(mappedBy = "disease", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OrderBy(clause = "geneScore desc")
    public List<DiseaseGene> getGeneList()
    {
        return geneList;
    }
    
    public void setGeneList(List<DiseaseGene> geneList)
    {
        this.geneList = geneList;
    }
    
    @ManyToMany(mappedBy = "disease", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(clause = "phneotypeScore desc")
    public List<DiseasePhenotype> getPhenotypeList()
    {
        return phenotypeList;
    }
    
    public void setPhenotypeList(List<DiseasePhenotype> phenotypeList)
    {
        this.phenotypeList = phenotypeList;
    }
    
    @Column(name = "CREATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG", nullable = false)
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
}
