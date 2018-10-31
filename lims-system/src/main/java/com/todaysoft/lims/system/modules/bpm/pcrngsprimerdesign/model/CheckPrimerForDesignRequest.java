package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model;

public class CheckPrimerForDesignRequest
{
    private String chromosomeNumber;//染色体编号
    
    private Long pcrStartPoint; //PCR起始点
    
    private Long pcrEndPoint; //PCR终止点
    
    private String forwardPrimerName;//正向引物名
    
    private String gene;//突变基因
    
    private String codingExon; //外显子编号
    
    private String reversePrimerName;//反向引物名
    
    private String applicationType;//应用类型
    
    //0数据库已存，1数据库未保存，校验通过，2数据库未存，正向引物名存在，3数据库未存，反向引物名存在
    public static final String PRIMER_DESIGN_CHECK_SAVE = "0";
    
    public static final String PRIMER_DESIGN_CHECK_YES = "1";
    
    public static final String PRIMER_DESIGN_CHECK_NO_F = "2";
    
    public static final String PRIMER_DESIGN_CHECK_NO_R = "3";
    
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
    
    public String getForwardPrimerName()
    {
        return forwardPrimerName;
    }
    
    public void setForwardPrimerName(String forwardPrimerName)
    {
        this.forwardPrimerName = forwardPrimerName;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getCodingExon()
    {
        return codingExon;
    }
    
    public void setCodingExon(String codingExon)
    {
        this.codingExon = codingExon;
    }
    
    public String getReversePrimerName()
    {
        return reversePrimerName;
    }
    
    public void setReversePrimerName(String reversePrimerName)
    {
        this.reversePrimerName = reversePrimerName;
    }
    
    public String getApplicationType()
    {
        return applicationType;
    }
    
    public void setApplicationType(String applicationType)
    {
        this.applicationType = applicationType;
    }
}
