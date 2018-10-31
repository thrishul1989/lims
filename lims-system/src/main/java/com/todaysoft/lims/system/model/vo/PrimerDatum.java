package com.todaysoft.lims.system.model.vo;

import java.io.Serializable;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class PrimerDatum implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String chromosomeNumber;//染色体编号
    
    private Long pcrStartPoint; //PCR起始点
    
    private Long pcrEndPoint; //PCR终止点
    
    private String reversePrimerSequence;//反向引物序列
    
    private String forwardPrimerSequence;//正向引物序列
    
    private String gene;//基因
    
    private String codingExon; //外显子
    
    private String codingExonArea; //外显子区域
    
    private Long pcrPoint;//用来接收页面查询的参数 判断是否满足   pcrStartPoint+50<=pcrPoint   pcrPoint<=pcrEndPoint-50
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "染色体编号", align = 2, sort = 4)
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    @ExcelField(title = "PCR起始点", align = 2, sort = 5)
    public Long getPcrStartPoint()
    {
        return pcrStartPoint;
    }
    
    public void setPcrStartPoint(Long pcrStartPoint)
    {
        this.pcrStartPoint = pcrStartPoint;
    }
    
    @ExcelField(title = "PCR终止点", align = 2, sort = 6)
    public Long getPcrEndPoint()
    {
        return pcrEndPoint;
    }
    
    public void setPcrEndPoint(Long pcrEndPoint)
    {
        this.pcrEndPoint = pcrEndPoint;
    }
    
    @ExcelField(title = "Reverse primer sequence", align = 2, sort = 8)
    public String getReversePrimerSequence()
    {
        return reversePrimerSequence;
    }
    
    public void setReversePrimerSequence(String reversePrimerSequence)
    {
        this.reversePrimerSequence = reversePrimerSequence;
    }
    
    @ExcelField(title = "Forward primer sequence", align = 2, sort = 7)
    public String getForwardPrimerSequence()
    {
        return forwardPrimerSequence;
    }
    
    public void setForwardPrimerSequence(String forwardPrimerSequence)
    {
        this.forwardPrimerSequence = forwardPrimerSequence;
    }
    
    @ExcelField(title = "Gene Symbol", align = 2, sort = 1)
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    @ExcelField(title = "Coding Exon Number", align = 2, sort = 2)
    public String getCodingExon()
    {
        return codingExon;
    }
    
    public void setCodingExon(String codingExon)
    {
        this.codingExon = codingExon;
    }
    
    @ExcelField(title = "HG19（外显子区域）", align = 2, sort = 3)
    public String getCodingExonArea()
    {
        return codingExonArea;
    }
    
    public void setCodingExonArea(String codingExonArea)
    {
        this.codingExonArea = codingExonArea;
    }
    
    public Long getPcrPoint()
    {
        return pcrPoint;
    }
    
    public void setPcrPoint(Long pcrPoint)
    {
        this.pcrPoint = pcrPoint;
    }
}
