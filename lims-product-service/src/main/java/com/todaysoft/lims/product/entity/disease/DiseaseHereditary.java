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
@Table(name = "LIMS_DISEASE_HEREDITARY")
public class DiseaseHereditary extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String hereditaryType;
    
    private Disease disease;
    
    @Column(name = "HEREDITARY_TYPE")
    public String getHereditaryType()
    {
        return hereditaryType;
    }
    
    public void setHereditaryType(String hereditaryType)
    {
        this.hereditaryType = hereditaryType;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISEASE_ID")
    @JsonIgnore
    public Disease getDisease()
    {
        return disease;
    }
    
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }
    
}
