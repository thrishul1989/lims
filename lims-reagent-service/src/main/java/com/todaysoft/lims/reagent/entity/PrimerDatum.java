package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.*;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRIMER_DATUM")
public class PrimerDatum extends UuidKeyEntity
{
    private static final long serialVersionUID = 3720574818652520498L;
    
    private String chromosomeNumber;
    
    private Long pcrStartPoint;
    
    private Long pcrEndPoint;
    
    private String reversePrimerSequence;
    
    private String forwardPrimerSequence;
    
    private String gene;
    
    private String codingExon;
    
    private String codingExonArea;
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;

    private Long pcrPoint;
    
    @Column(name = "CHROMOSOME")
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
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
    
    @Column(name = "GENE_SYMBOL")
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    @Column(name = "EXON")
    public String getCodingExon()
    {
        return codingExon;
    }
    
    public void setCodingExon(String codingExon)
    {
        this.codingExon = codingExon;
    }
    
    @Column(name = "EXON_AREA")
    public String getCodingExonArea()
    {
        return codingExonArea;
    }
    
    public void setCodingExonArea(String codingExonArea)
    {
        this.codingExonArea = codingExonArea;
    }
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Transient
    public Long getPcrPoint() {
        return pcrPoint;
    }

    public void setPcrPoint(Long pcrPoint) {
        this.pcrPoint = pcrPoint;
    }
}
