package com.todaysoft.lims.system.model.vo.disease;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class DiseaseGeneFormRequest
{
    
    private String code; // '基因编号',
    
    private String name; // '基因全名',
    
    private Integer exonCount; // '外显子数',
    
    private Integer intronCount; // '内含子数',
    
    private String exomeNo; // 'nm号',
    
    private String chromosomalLocation; //'染色体区域',
    
    private String aliasJson; //前台接收别名json传
    
    private List<GeneAlias> alias = new ArrayList<GeneAlias>();
    
    private String id;
    
    private String symbol; //'基因名称',
    
    private String importAlias; //导入时接收
    
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
    
    private Integer publicTotal;
    
    private Integer isOverwrite;//页面展示字段
    
    public Integer getIsOverwrite()
    {
        return isOverwrite;
    }
    
    public void setIsOverwrite(Integer isOverwrite)
    {
        this.isOverwrite = isOverwrite;
    }
    
    @ExcelField(title = "OMIM ID", align = 2, sort = 10)
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @ExcelField(title = "基因名称", align = 2, sort = 20)
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    @ExcelField(title = "基因全名", align = 2, sort = 30)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @ExcelField(title = "Uniport ID", align = 2, sort = 40)
    public String getUniportId()
    {
        return uniportId;
    }
    
    public void setUniportId(String uniportId)
    {
        this.uniportId = uniportId;
    }
    
    @ExcelField(title = "外显子数", align = 2, sort = 50)
    public Integer getExonCount()
    {
        return exonCount;
    }
    
    public void setExonCount(Integer exonCount)
    {
        this.exonCount = exonCount;
    }
    
    @ExcelField(title = "内含子数", align = 2, sort = 60)
    public Integer getIntronCount()
    {
        return intronCount;
    }
    
    public void setIntronCount(Integer intronCount)
    {
        this.intronCount = intronCount;
    }
    
    @ExcelField(title = "NM号", align = 2, sort = 70)
    public String getExomeNo()
    {
        return exomeNo;
    }
    
    public void setExomeNo(String exomeNo)
    {
        this.exomeNo = exomeNo;
    }
    
    @ExcelField(title = "染色体区域", align = 2, sort = 80)
    public String getChromosomalLocation()
    {
        return chromosomalLocation;
    }
    
    public void setChromosomalLocation(String chromosomalLocation)
    {
        this.chromosomalLocation = chromosomalLocation;
    }
    
    @ExcelField(title = "基因别名", align = 2, sort = 90)
    public String getImportAlias()
    {
        return importAlias;
    }
    
    public void setImportAlias(String importAlias)
    {
        this.importAlias = importAlias;
    }
    
    @ExcelField(title = "Missense/nonsense", align = 2, sort = 100)
    public Integer getMissenseNonsense()
    {
        return missenseNonsense;
    }
    
    public void setMissenseNonsense(Integer missenseNonsense)
    {
        this.missenseNonsense = missenseNonsense;
    }
    
    @ExcelField(title = "Splicing", align = 2, sort = 110)
    public Integer getSplicing()
    {
        return splicing;
    }
    
    public void setSplicing(Integer splicing)
    {
        this.splicing = splicing;
    }
    
    @ExcelField(title = "Regulatory", align = 2, sort = 120)
    public Integer getRegulatory()
    {
        return regulatory;
    }
    
    public void setRegulatory(Integer regulatory)
    {
        this.regulatory = regulatory;
    }
    
    @ExcelField(title = "Small deletions", align = 2, sort = 130)
    public Integer getSmallDeletions()
    {
        return smallDeletions;
    }
    
    public void setSmallDeletions(Integer smallDeletions)
    {
        this.smallDeletions = smallDeletions;
    }
    
    @ExcelField(title = "Small insertions", align = 2, sort = 140)
    public Integer getSmallInsertions()
    {
        return smallInsertions;
    }
    
    public void setSmallInsertions(Integer smallInsertions)
    {
        this.smallInsertions = smallInsertions;
    }
    
    @ExcelField(title = "Small indels", align = 2, sort = 150)
    public Integer getSmallIndels()
    {
        return smallIndels;
    }
    
    public void setSmallIndels(Integer smallIndels)
    {
        this.smallIndels = smallIndels;
    }
    
    @ExcelField(title = "Gross deletions", align = 2, sort = 150)
    public Integer getGrossDeletions()
    {
        return grossDeletions;
    }
    
    public void setGrossDeletions(Integer grossDeletions)
    {
        this.grossDeletions = grossDeletions;
    }
    
    @ExcelField(title = "Gross insertions/duplications", align = 2, sort = 160)
    public Integer getGrossInsertionsDuplications()
    {
        return grossInsertionsDuplications;
    }
    
    public void setGrossInsertionsDuplications(Integer grossInsertionsDuplications)
    {
        this.grossInsertionsDuplications = grossInsertionsDuplications;
    }
    
    @ExcelField(title = "Complex rearrangements", align = 2, sort = 170)
    public Integer getComplexRearrangements()
    {
        return complexRearrangements;
    }
    
    public void setComplexRearrangements(Integer complexRearrangements)
    {
        this.complexRearrangements = complexRearrangements;
    }
    
    @ExcelField(title = "Repeat variations", align = 2, sort = 180)
    public Integer getRepeatVariations()
    {
        return repeatVariations;
    }
    
    public void setRepeatVariations(Integer repeatVariations)
    {
        this.repeatVariations = repeatVariations;
    }
    
    public Integer getPublicTotal()
    {
        return publicTotal;
    }
    
    public void setPublicTotal(Integer publicTotal)
    {
        this.publicTotal = publicTotal;
    }
    
    public String getAliasJson()
    {
        return aliasJson;
    }
    
    public void setAliasJson(String aliasJson)
    {
        this.aliasJson = aliasJson;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<GeneAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<GeneAlias> alias)
    {
        this.alias = alias;
    }
}
