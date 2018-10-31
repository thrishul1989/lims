package com.todaysoft.lims.technical.export.mybatis.model;

import java.util.List;

public class ReportExportMutationInfoExplain
{
    private String objectId;
    private String tagReportDisease;
    private String tagPublication;
    private String geneType;
    private String pathogenicAnalysis;
    private String geneSymbol;
    private List<ReportExportDiseaseInfo> diseaseInfos;
    private List<ReportExportEvidenceInfo> evidenceInfos;
    
    public String getObjectId()
    {
        return objectId;
    }
    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }
    public String getTagReportDisease()
    {
        return tagReportDisease;
    }
    public void setTagReportDisease(String tagReportDisease)
    {
        this.tagReportDisease = tagReportDisease;
    }
    public String getTagPublication()
    {
        return tagPublication;
    }
    public void setTagPublication(String tagPublication)
    {
        this.tagPublication = tagPublication;
    }
    public String getGeneType()
    {
        return geneType;
    }
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    public String getPathogenicAnalysis()
    {
        return pathogenicAnalysis;
    }
    public void setPathogenicAnalysis(String pathogenicAnalysis)
    {
        this.pathogenicAnalysis = pathogenicAnalysis;
    }
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    public List<ReportExportDiseaseInfo> getDiseaseInfos()
    {
        return diseaseInfos;
    }
    public void setDiseaseInfos(List<ReportExportDiseaseInfo> diseaseInfos)
    {
        this.diseaseInfos = diseaseInfos;
    }
    public List<ReportExportEvidenceInfo> getEvidenceInfos()
    {
        return evidenceInfos;
    }
    public void setEvidenceInfos(List<ReportExportEvidenceInfo> evidenceInfos)
    {
        this.evidenceInfos = evidenceInfos;
    }
    
    
}
