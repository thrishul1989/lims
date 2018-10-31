package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRIMER")
public class Primer extends UuidKeyEntity
{
    private static final long serialVersionUID = 3720574818652520498L;
    
    private Long pcrStartPoint; //PCR起始点
    
    private Long pcrEndPoint; //PCR终止点
    
    private String forwardPrimerName;//正向引物名
    
    private String forwardPrimerSequence;//正向引物序列
    
    private String reversePrimerName;//反向引物名
    
    private String reversePrimerSequence;//反向引物序列

    private String productCode;

    private String applicationType;

    private boolean deleted;

    private String gene;

    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "REVERSE_SEQUENCE")
    public String getReversePrimerSequence()
    {
        return reversePrimerSequence;
    }
    
    public void setReversePrimerSequence(String reversePrimerSequence)
    {
        this.reversePrimerSequence = reversePrimerSequence;
    }
    
    @Column(name = "FORWARD_SEQUENCE")
    public String getForwardPrimerSequence()
    {
        return forwardPrimerSequence;
    }
    
    public void setForwardPrimerSequence(String forwardPrimerSequence)
    {
        this.forwardPrimerSequence = forwardPrimerSequence;
    }
    
    @Column(name = "FORWARD_PRIMER_NAME")
    public String getForwardPrimerName()
    {
        return forwardPrimerName;
    }
    
    public void setForwardPrimerName(String forwardPrimerName)
    {
        this.forwardPrimerName = forwardPrimerName;
    }
    
    @Column(name = "REVERSE_PRIMER_NAME")
    public String getReversePrimerName()
    {
        return reversePrimerName;
    }
    
    public void setReversePrimerName(String reversePrimerName)
    {
        this.reversePrimerName = reversePrimerName;
    }
    
    @Column(name = "START_LOCUS")
    public Long getPcrStartPoint()
    {
        return pcrStartPoint;
    }
    
    public void setPcrStartPoint(Long pcrStartPoint)
    {
        this.pcrStartPoint = pcrStartPoint;
    }
    
    @Column(name = "END_LOCUS")
    public Long getPcrEndPoint()
    {
        return pcrEndPoint;
    }
    
    public void setPcrEndPoint(Long pcrEndPoint)
    {
        this.pcrEndPoint = pcrEndPoint;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "APPLICATION_TYPE")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name="GENE_SYMBOL")
    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }
}
