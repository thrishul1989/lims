package com.todaysoft.lims.testing.base.request;

import java.util.Date;

public class PrimerCreateRequest
{
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

    private String applicationType;//检测方法id

    private Date createTime;

    private boolean deleted;

    private Date deleteTime;

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getCodingExon() {
        return codingExon;
    }

    public void setCodingExon(String codingExon) {
        this.codingExon = codingExon;
    }

    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
    }

    public String getChromosomeLocation() {
        return chromosomeLocation;
    }

    public void setChromosomeLocation(String chromosomeLocation) {
        this.chromosomeLocation = chromosomeLocation;
    }

    public Long getPcrStartPoint() {
        return pcrStartPoint;
    }

    public void setPcrStartPoint(Long pcrStartPoint) {
        this.pcrStartPoint = pcrStartPoint;
    }

    public Long getPcrEndPoint() {
        return pcrEndPoint;
    }

    public void setPcrEndPoint(Long pcrEndPoint) {
        this.pcrEndPoint = pcrEndPoint;
    }

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    public String getForwardPrimerSequence() {
        return forwardPrimerSequence;
    }

    public void setForwardPrimerSequence(String forwardPrimerSequence) {
        this.forwardPrimerSequence = forwardPrimerSequence;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }

    public void setReversePrimerName(String reversePrimerName) {
        this.reversePrimerName = reversePrimerName;
    }

    public String getReversePrimerSequence() {
        return reversePrimerSequence;
    }

    public void setReversePrimerSequence(String reversePrimerSequence) {
        this.reversePrimerSequence = reversePrimerSequence;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
