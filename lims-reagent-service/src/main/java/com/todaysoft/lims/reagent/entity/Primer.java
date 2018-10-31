package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRIMER")
public class Primer extends UuidKeyEntity
{
    private static final long serialVersionUID = 3720574818652520498L;

    private String gene;//突变基因

    private String codingExon; //外显子编号

    private String chromosomeNumber;//染色体编号

    private String chromosomeLocation;//染色体位置

    private Long pcrStartPoint; //PCR起始点

    private Long pcrEndPoint; //PCR终止点

    private String forwardPrimerName;//正向引物名

    private String forwardPrimerSequence;//正向引物序列

    private String reversePrimerName;//反向引物名

    private String reversePrimerSequence;//反向引物序列

    private String productCode;//产品编号

    private String applicationType;//应用类型  1 产品检测 2 验证&位点检测 3 PCR-NGS验证

    private Date createTime;

    private boolean deleted;

    private Date deleteTime;
    
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

    @Column(name = "CHROMOSOME_LOCATION")
    public String getChromosomeLocation() {
        return chromosomeLocation;
    }

    public void setChromosomeLocation(String chromosomeLocation) {
        this.chromosomeLocation = chromosomeLocation;
    }

    @Column(name="FORWARD_PRIMER_NAME")
    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    @Column(name="REVERSE_PRIMER_NAME")
    public String getReversePrimerName() {
        return reversePrimerName;
    }

    public void setReversePrimerName(String reversePrimerName) {
        this.reversePrimerName = reversePrimerName;
    }

    @Column(name="PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name="APPLICATION_TYPE")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
}
