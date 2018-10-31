package com.todaysoft.lims.technical.mybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Primer {
    private String id;

    private String geneSymbol;

    private String exon;

    private String chromosome;

    private String chromosomeLocation;

    private BigDecimal startLocus;

    private BigDecimal endLocus;

    private String forwardPrimerName;

    private String forwardSequence;

    private String reversePrimerName;

    private String reverseSequence;

    private String productCode;

    private String applicationType;

    private Date createTime;

    private Boolean delFlag;

    private Date deleteTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getExon() {
        return exon;
    }

    public void setExon(String exon) {
        this.exon = exon;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getChromosomeLocation() {
        return chromosomeLocation;
    }

    public void setChromosomeLocation(String chromosomeLocation) {
        this.chromosomeLocation = chromosomeLocation;
    }

    public BigDecimal getStartLocus() {
        return startLocus;
    }

    public void setStartLocus(BigDecimal startLocus) {
        this.startLocus = startLocus;
    }

    public BigDecimal getEndLocus() {
        return endLocus;
    }

    public void setEndLocus(BigDecimal endLocus) {
        this.endLocus = endLocus;
    }

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    public String getForwardSequence() {
        return forwardSequence;
    }

    public void setForwardSequence(String forwardSequence) {
        this.forwardSequence = forwardSequence;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }

    public void setReversePrimerName(String reversePrimerName) {
        this.reversePrimerName = reversePrimerName;
    }

    public String getReverseSequence() {
        return reverseSequence;
    }

    public void setReverseSequence(String reverseSequence) {
        this.reverseSequence = reverseSequence;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}