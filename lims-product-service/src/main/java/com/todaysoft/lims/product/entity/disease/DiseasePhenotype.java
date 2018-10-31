package com.todaysoft.lims.product.entity.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.Phenotype;

@Entity
@Table(name = "LIMS_DISEASE_PHENOTYPE")
public class DiseasePhenotype extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String dependency;
    
    private Phenotype phenotype;
    
    private Disease disease;
    
    private Double phneotypeScore;
    
    @Column(name = "SCORE")
    public Double getPhneotypeScore()
    {
        return phneotypeScore;
    }
    
    public void setPhneotypeScore(Double phneotypeScore)
    {
        this.phneotypeScore = phneotypeScore;
    }
    
    @Column(name = "DEPENDENCY")
    public String getDependency()
    {
        return dependency;
    }
    
    public void setDependency(String dependency)
    {
        this.dependency = dependency;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PHENOTYPE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Phenotype getPhenotype()
    {
        return phenotype;
    }
    
    public void setPhenotype(Phenotype phenotype)
    {
        this.phenotype = phenotype;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISEASE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Disease getDisease()
    {
        return disease;
    }
    
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }
    
    @Override
    public boolean equals(Object o)
    {
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
}
