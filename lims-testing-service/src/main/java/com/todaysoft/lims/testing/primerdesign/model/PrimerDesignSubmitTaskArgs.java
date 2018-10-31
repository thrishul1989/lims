package com.todaysoft.lims.testing.primerdesign.model;


public class PrimerDesignSubmitTaskArgs
{
    private String id;

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

    private Integer result;// 0-成功 1-失败

    private String remark;//备注
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
