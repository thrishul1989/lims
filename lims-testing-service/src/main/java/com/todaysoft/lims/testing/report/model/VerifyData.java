package com.todaysoft.lims.testing.report.model;

import com.google.common.collect.Maps;
import com.todaysoft.lims.testing.base.entity.DataTemplate;

import java.util.Map;

public class VerifyData
{
    private String vilidMethod;
    
    private String combineCode;
    
    private String familyRelation;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String gene;
    
    private String exonRegion;//外显子区域
    
    private String chromosomeLocation;//染色体位置
    
    private String combineType;//纯合/杂合
    
    private String mutationSource; //突变来源
    
    private String remark; //备注

    private String orderCode;

    private DataTemplate dataTemplate;

    private Map<String,String> map = Maps.newHashMap();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getVilidMethod()
    {
        return vilidMethod;
    }
    
    public void setVilidMethod(String vilidMethod)
    {
        this.vilidMethod = vilidMethod;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getExonRegion()
    {
        return exonRegion;
    }
    
    public void setExonRegion(String exonRegion)
    {
        this.exonRegion = exonRegion;
    }
    
    public String getChromosomeLocation()
    {
        return chromosomeLocation;
    }
    
    public void setChromosomeLocation(String chromosomeLocation)
    {
        this.chromosomeLocation = chromosomeLocation;
    }
    
    public String getCombineType()
    {
        return combineType;
    }
    
    public void setCombineType(String combineType)
    {
        this.combineType = combineType;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public VerifyData(String vilidMethod, String combineCode, String familyRelation, String sampleName, String sampleCode, String gene, String exonRegion,
        String chromosomeLocation, String combineType, String mutationSource, String remark)
    {
        super();
        this.vilidMethod = vilidMethod;
        this.combineCode = combineCode;
        this.familyRelation = familyRelation;
        this.sampleName = sampleName;
        this.sampleCode = sampleCode;
        this.gene = gene;
        this.exonRegion = exonRegion;
        this.chromosomeLocation = chromosomeLocation;
        this.combineType = combineType;
        this.mutationSource = mutationSource;
        this.remark = remark;
    }
    
    public VerifyData()
    {
        super();
    }

    public DataTemplate getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }
}
