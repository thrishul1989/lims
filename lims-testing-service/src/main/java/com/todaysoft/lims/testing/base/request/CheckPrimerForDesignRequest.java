package com.todaysoft.lims.testing.base.request;

public class CheckPrimerForDesignRequest
{
    private String chromosomeNumber;//染色体编号
    
    private Long pcrStartPoint; //PCR起始点
    
    private Long pcrEndPoint; //PCR终止点

    private String applicationType;//检测方法id

    private Long pcrPoint;

    private String semantic;

    private String productCode;

    private String forwardPrimerName;//正向引物名

    private String reversePrimerName;//反向引物名

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }

    public void setReversePrimerName(String reversePrimerName) {
        this.reversePrimerName = reversePrimerName;
    }

    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    public Long getPcrStartPoint()
    {
        return pcrStartPoint;
    }
    
    public void setPcrStartPoint(Long pcrStartPoint)
    {
        this.pcrStartPoint = pcrStartPoint;
    }
    
    public Long getPcrEndPoint()
    {
        return pcrEndPoint;
    }
    
    public void setPcrEndPoint(Long pcrEndPoint)
    {
        this.pcrEndPoint = pcrEndPoint;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Long getPcrPoint() {
        return pcrPoint;
    }

    public void setPcrPoint(Long pcrPoint) {
        this.pcrPoint = pcrPoint;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
