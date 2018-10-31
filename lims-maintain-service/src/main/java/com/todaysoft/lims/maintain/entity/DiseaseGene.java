package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DISEASE_GENE")
public class DiseaseGene extends UuidKeyEntity
{
    private static final long serialVersionUID = 5980277536807401645L;
    
    private String diseaseId;
    
    private String geneId;
    
    @Column(name = "DISEASE_ID")
    public String getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        this.diseaseId = diseaseId;
    }
    
    @Column(name = "GENE_ID")
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
}
