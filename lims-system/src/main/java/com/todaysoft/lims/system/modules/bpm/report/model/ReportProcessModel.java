package com.todaysoft.lims.system.modules.bpm.report.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CollectionCnvAnalysisPicResultList;

public class ReportProcessModel
{
    private String id;
    
    private String reportId;
    
    private String reportName;
    
    private String semantic;
    
    private List<TestSampleModel> testSamples;
    
    private List<String> columnNames;
    
    private List<List<String>> columnValues;
    
    private List<ReportProcessResult> processResults;
    
    private List<ReportTestingPicture> pictures;
    
    private List<VerifySampleModel> verifySamples;
    
    private String pictureIds;
    
    private String testingResult;// json字符串
    
    private String checkRecordIds;// 技术分析或数据分析或图片Id
     
    private DataColAndMutationDataModel dataColAndMutationDataModel;
    
    private List<CollectionCnvAnalysisPicResultList>  cnvModel;
    
    private String technicalFamilyGroupId;

    private String isFamilySample;

    public String getIsFamilySample() {
        return isFamilySample;
    }

    public void setIsFamilySample(String isFamilySample) {
        this.isFamilySample = isFamilySample;
    }

    public String getCheckRecordIds()
    {
        return checkRecordIds;
    }
    
    public void setCheckRecordIds(String checkRecordIds)
    {
        this.checkRecordIds = checkRecordIds;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getReportId()
    {
        return reportId;
    }
    
    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }
    
    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public List<TestSampleModel> getTestSamples()
    {
        return testSamples;
    }
    
    public void setTestSamples(List<TestSampleModel> testSamples)
    {
        this.testSamples = testSamples;
    }
    
    public List<String> getColumnNames()
    {
        return columnNames;
    }
    
    public void setColumnNames(List<String> columnNames)
    {
        this.columnNames = columnNames;
    }
    
    public List<List<String>> getColumnValues()
    {
        return columnValues;
    }
    
    public void setColumnValues(List<List<String>> columnValues)
    {
        this.columnValues = columnValues;
    }
    
    public List<ReportProcessResult> getProcessResults()
    {
        return processResults;
    }
    
    public void setProcessResults(List<ReportProcessResult> processResults)
    {
        this.processResults = processResults;
    }
    
    public List<ReportTestingPicture> getPictures()
    {
        return pictures;
    }
    
    public void setPictures(List<ReportTestingPicture> pictures)
    {
        this.pictures = pictures;
    }
    
    public List<VerifySampleModel> getVerifySamples()
    {
        return verifySamples;
    }
    
    public void setVerifySamples(List<VerifySampleModel> verifySamples)
    {
        this.verifySamples = verifySamples;
    }
    
    public String getPictureIds()
    {
        return pictureIds;
    }
    
    public void setPictureIds(String pictureIds)
    {
        this.pictureIds = pictureIds;
    }
    
    public String getTestingResult()
    {
        return testingResult;
    }
    
    public void setTestingResult(String testingResult)
    {
        this.testingResult = testingResult;
    }

    public DataColAndMutationDataModel getDataColAndMutationDataModel()
    {
        return dataColAndMutationDataModel;
    }

    public void setDataColAndMutationDataModel(DataColAndMutationDataModel dataColAndMutationDataModel)
    {
        this.dataColAndMutationDataModel = dataColAndMutationDataModel;
    }

    public List<CollectionCnvAnalysisPicResultList> getCnvModel()
    {
        return cnvModel;
    }

    public void setCnvModel(List<CollectionCnvAnalysisPicResultList> cnvModel)
    {
        this.cnvModel = cnvModel;
    }

    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }

    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }
    
}
