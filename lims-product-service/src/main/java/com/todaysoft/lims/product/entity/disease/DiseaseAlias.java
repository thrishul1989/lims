package com.todaysoft.lims.product.entity.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DISEASE_ALIAS")
public class DiseaseAlias extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String nameEn;
    
    private Disease diseaseId;
    
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISEASE_ID")
    @JsonIgnore
    public Disease getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(Disease diseaseId)
    {
        this.diseaseId = diseaseId;
    }
    
}
