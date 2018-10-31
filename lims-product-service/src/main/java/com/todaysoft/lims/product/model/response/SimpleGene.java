package com.todaysoft.lims.product.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.product.entity.disease.GeneAlias;

public class SimpleGene implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String code; // '基因编号',
    
    private String symbol; // '基因名称',
    
    private String name; // '基因全名',
    
    private Integer exonCount; // '外显子数',
    
    private Integer intronCount; // '内含子数',
    
    private String exomeNo; // 'nm号',
    
    private String chromosomalLocation; // '染色体区域',
    
    private List<GeneAlias> alias = new ArrayList<GeneAlias>();
    
    /* private List<ProductGene> productGeneList;*/
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String uniportId;
    
    private Integer missenseNonsense;
    
    private Integer splicing;
    
    private Integer regulatory;
    
    private Integer smallDeletions;
    
    private Integer smallInsertions;
    
    private Integer smallIndels;
    
    private Integer grossDeletions;
    
    private Integer grossInsertionsDuplications;
    
    private Integer complexRearrangements;
    
    private Integer repeatVariations;
    
    private Double geneScore;
    
    public Double getGeneScore()
    {
        return geneScore;
    }
    
    public void setGeneScore(Double geneScore)
    {
        this.geneScore = geneScore;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getExonCount()
    {
        return exonCount;
    }
    
    public void setExonCount(Integer exonCount)
    {
        this.exonCount = exonCount;
    }
    
    public Integer getIntronCount()
    {
        return intronCount;
    }
    
    public void setIntronCount(Integer intronCount)
    {
        this.intronCount = intronCount;
    }
    
    public String getExomeNo()
    {
        return exomeNo;
    }
    
    public void setExomeNo(String exomeNo)
    {
        this.exomeNo = exomeNo;
    }
    
    public String getChromosomalLocation()
    {
        return chromosomalLocation;
    }
    
    public void setChromosomalLocation(String chromosomalLocation)
    {
        this.chromosomalLocation = chromosomalLocation;
    }
    
    public List<GeneAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<GeneAlias> alias)
    {
        this.alias = alias;
    }
    
    /* 
     public List<ProductGene> getProductGeneList()
     {
         return productGeneList;
     }
     
     public void setProductGeneList(List<ProductGene> productGeneList)
     {
         this.productGeneList = productGeneList;
     }
     */
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getUniportId()
    {
        return uniportId;
    }
    
    public void setUniportId(String uniportId)
    {
        this.uniportId = uniportId;
    }
    
    public Integer getMissenseNonsense()
    {
        return missenseNonsense;
    }
    
    public void setMissenseNonsense(Integer missenseNonsense)
    {
        this.missenseNonsense = missenseNonsense;
    }
    
    public Integer getSplicing()
    {
        return splicing;
    }
    
    public void setSplicing(Integer splicing)
    {
        this.splicing = splicing;
    }
    
    public Integer getRegulatory()
    {
        return regulatory;
    }
    
    public void setRegulatory(Integer regulatory)
    {
        this.regulatory = regulatory;
    }
    
    public Integer getSmallDeletions()
    {
        return smallDeletions;
    }
    
    public void setSmallDeletions(Integer smallDeletions)
    {
        this.smallDeletions = smallDeletions;
    }
    
    public Integer getSmallInsertions()
    {
        return smallInsertions;
    }
    
    public void setSmallInsertions(Integer smallInsertions)
    {
        this.smallInsertions = smallInsertions;
    }
    
    public Integer getSmallIndels()
    {
        return smallIndels;
    }
    
    public void setSmallIndels(Integer smallIndels)
    {
        this.smallIndels = smallIndels;
    }
    
    public Integer getGrossDeletions()
    {
        return grossDeletions;
    }
    
    public void setGrossDeletions(Integer grossDeletions)
    {
        this.grossDeletions = grossDeletions;
    }
    
    public Integer getGrossInsertionsDuplications()
    {
        return grossInsertionsDuplications;
    }
    
    public void setGrossInsertionsDuplications(Integer grossInsertionsDuplications)
    {
        this.grossInsertionsDuplications = grossInsertionsDuplications;
    }
    
    public Integer getComplexRearrangements()
    {
        return complexRearrangements;
    }
    
    public void setComplexRearrangements(Integer complexRearrangements)
    {
        this.complexRearrangements = complexRearrangements;
    }
    
    public Integer getRepeatVariations()
    {
        return repeatVariations;
    }
    
    public void setRepeatVariations(Integer repeatVariations)
    {
        this.repeatVariations = repeatVariations;
    }
    
}
