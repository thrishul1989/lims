package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DISEASE_PHENOTYPE")
public class DiseasePhenotype extends UuidKeyEntity
{
    private static final long serialVersionUID = -4912273084000370734L;
    
    private String diseaseId;
    
    private String phenotypeId;
    
    @Column(name = "DISEASE_ID")
    public String getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        this.diseaseId = diseaseId;
    }
    
    @Column(name = "PHENOTYPE_ID")
    public String getPhenotypeId()
    {
        return phenotypeId;
    }
    
    public void setPhenotypeId(String phenotypeId)
    {
        this.phenotypeId = phenotypeId;
    }
}
