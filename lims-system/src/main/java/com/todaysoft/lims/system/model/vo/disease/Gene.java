package com.todaysoft.lims.system.model.vo.disease;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.ProbeGene;
import com.todaysoft.lims.system.model.vo.ProductGene;

public class Gene extends DiseaseGeneFormRequest
{
    private String code; // '基因编号',
    
    private String symbol; // '基因名称',
    
    private String name; // '基因全名',
    
    private Integer exonCount; // '外显子数',
    
    private Integer intronCount; // '内含子数',
    
    private String exomeNo; // 'nm号',
    
    private String chromosomalLocation; // '染色体区域',
    
    private List<GeneAlias> alias = new ArrayList<GeneAlias>();
    
    private List<ProbeGene> probeGeneList = new ArrayList<ProbeGene>();
    
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
    
    private List<String> documents = new ArrayList<String>();
    
    public List<String> getDocuments()
    {
        return documents;
    }
    
    public void setDocuments(List<String> documents)
    {
        this.documents = documents;
    }
    
    private List<SimpleDisease> diseaseGeneList;
    
    public List<SimpleDisease> getDiseaseGeneList()
    {
        return diseaseGeneList;
    }
    
    public void setDiseaseGeneList(List<SimpleDisease> diseaseGeneList)
    {
        this.diseaseGeneList = diseaseGeneList;
    }
    
    private Double geneScore;
    
    /* private List<DiseaseGene> diseaseGeneList = new ArrayList<DiseaseGene>();*/
    
    public Double getGeneScore()
    {
        return geneScore;
    }
    
    public void setGeneScore(Double geneScore)
    {
        this.geneScore = geneScore;
    }
    
    /* public List<DiseaseGene> getDiseaseGeneList()
     {
         return diseaseGeneList;
     }
     
     public void setDiseaseGeneList(List<DiseaseGene> diseaseGeneList)
     {
         this.diseaseGeneList = diseaseGeneList;
     }*/
    
    private List<ProductGene> productGeneList;
    
    public List<ProductGene> getProductGeneList()
    {
        return productGeneList;
    }
    
    public void setProductGeneList(List<ProductGene> productGeneList)
    {
        this.productGeneList = productGeneList;
    }
    
    @Override
    public String getCode()
    {
        return code;
    }
    
    @Override
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Override
    public String getSymbol()
    {
        return symbol;
    }
    
    @Override
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public Integer getExonCount()
    {
        return exonCount;
    }
    
    @Override
    public void setExonCount(Integer exonCount)
    {
        this.exonCount = exonCount;
    }
    
    @Override
    public Integer getIntronCount()
    {
        return intronCount;
    }
    
    @Override
    public void setIntronCount(Integer intronCount)
    {
        this.intronCount = intronCount;
    }
    
    @Override
    public String getExomeNo()
    {
        return exomeNo;
    }
    
    @Override
    public void setExomeNo(String exomeNo)
    {
        this.exomeNo = exomeNo;
    }
    
    @Override
    public String getChromosomalLocation()
    {
        return chromosomalLocation;
    }
    
    @Override
    public void setChromosomalLocation(String chromosomalLocation)
    {
        this.chromosomalLocation = chromosomalLocation;
    }
    
    @Override
    public List<GeneAlias> getAlias()
    {
        return alias;
    }
    
    @Override
    public void setAlias(List<GeneAlias> alias)
    {
        this.alias = alias;
    }
    
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
    
    @Override
    public String getUniportId()
    {
        return uniportId;
    }
    
    @Override
    public void setUniportId(String uniportId)
    {
        this.uniportId = uniportId;
    }
    
    @Override
    public Integer getMissenseNonsense()
    {
        return missenseNonsense;
    }
    
    @Override
    public void setMissenseNonsense(Integer missenseNonsense)
    {
        this.missenseNonsense = missenseNonsense;
    }
    
    @Override
    public Integer getSplicing()
    {
        return splicing;
    }
    
    @Override
    public void setSplicing(Integer splicing)
    {
        this.splicing = splicing;
    }
    
    @Override
    public Integer getRegulatory()
    {
        return regulatory;
    }
    
    @Override
    public void setRegulatory(Integer regulatory)
    {
        this.regulatory = regulatory;
    }
    
    @Override
    public Integer getSmallDeletions()
    {
        return smallDeletions;
    }
    
    @Override
    public void setSmallDeletions(Integer smallDeletions)
    {
        this.smallDeletions = smallDeletions;
    }
    
    @Override
    public Integer getSmallInsertions()
    {
        return smallInsertions;
    }
    
    @Override
    public void setSmallInsertions(Integer smallInsertions)
    {
        this.smallInsertions = smallInsertions;
    }
    
    @Override
    public Integer getSmallIndels()
    {
        return smallIndels;
    }
    
    @Override
    public void setSmallIndels(Integer smallIndels)
    {
        this.smallIndels = smallIndels;
    }
    
    @Override
    public Integer getGrossDeletions()
    {
        return grossDeletions;
    }
    
    @Override
    public void setGrossDeletions(Integer grossDeletions)
    {
        this.grossDeletions = grossDeletions;
    }
    
    @Override
    public Integer getGrossInsertionsDuplications()
    {
        return grossInsertionsDuplications;
    }
    
    @Override
    public void setGrossInsertionsDuplications(Integer grossInsertionsDuplications)
    {
        this.grossInsertionsDuplications = grossInsertionsDuplications;
    }
    
    @Override
    public Integer getComplexRearrangements()
    {
        return complexRearrangements;
    }
    
    @Override
    public void setComplexRearrangements(Integer complexRearrangements)
    {
        this.complexRearrangements = complexRearrangements;
    }
    
    @Override
    public Integer getRepeatVariations()
    {
        return repeatVariations;
    }
    
    @Override
    public void setRepeatVariations(Integer repeatVariations)
    {
        this.repeatVariations = repeatVariations;
    }
    
    public List<ProbeGene> getProbeGeneList()
    {
        return probeGeneList;
    }
    
    public void setProbeGeneList(List<ProbeGene> probeGeneList)
    {
        this.probeGeneList = probeGeneList;
    }
    
}
