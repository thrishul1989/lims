package com.todaysoft.lims.testing.base.model;


import java.util.Date;

public class PrimerDatum {

    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
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

    public String getReversePrimerSequence() {
        return reversePrimerSequence;
    }

    public void setReversePrimerSequence(String reversePrimerSequence) {
        this.reversePrimerSequence = reversePrimerSequence;
    }

    public String getForwardPrimerSequence() {
        return forwardPrimerSequence;
    }

    public void setForwardPrimerSequence(String forwardPrimerSequence) {
        this.forwardPrimerSequence = forwardPrimerSequence;
    }

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

    public String getCodingExonArea() {
        return codingExonArea;
    }

    public void setCodingExonArea(String codingExonArea) {
        this.codingExonArea = codingExonArea;
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

    public Long getPcrPoint() {
        return pcrPoint;
    }

    public void setPcrPoint(Long pcrPoint) {
        this.pcrPoint = pcrPoint;
    }
}
