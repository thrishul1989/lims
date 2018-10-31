package com.todaysoft.lims.product.entity.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DISEASE_GENE")
public class DiseaseGene extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Gene gene;
    
    private Disease disease;
    
    private Double geneScore;
    
    @ManyToOne(fetch = FetchType.LAZY)
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GENE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Gene getGene()
    {
        return gene;
    }
    
    public void setGene(Gene gene)
    {
        this.gene = gene;
    }
    
    @Column(name = "SCORE")
    public Double getGeneScore()
    {
        return geneScore;
    }
    
    public void setGeneScore(Double geneScore)
    {
        this.geneScore = geneScore;
    }
    
}
