package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

public class BioanalysisAssignTask
{
    private String id;
    
    private String sequenceNo;//捕获测序号
    
    private String dataCode;//数据编号
    
    private String analysisSample;//待分析样本
    
    private String libCode;//文库号
    
    private String indexCode;//接头编号
    
    private String analyticCoordinate;//分析坐标
    
    private String item;//检测项目
    
    private String sampleName;//样本名称
    
    private String receiveType;//接收类型
    
    private String sex;//性别
    
    private String organization;//送检机构
    
    private String remark;//备注
    
    private String contractNo;//合同编号
    
    private String comparisonSample;//对照样本
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getAnalysisSample()
    {
        return analysisSample;
    }
    
    public void setAnalysisSample(String analysisSample)
    {
        this.analysisSample = analysisSample;
    }
    
    public String getLibCode()
    {
        return libCode;
    }
    
    public void setLibCode(String libCode)
    {
        this.libCode = libCode;
    }
    
    public String getIndexCode()
    {
        return indexCode;
    }
    
    public void setIndexCode(String indexCode)
    {
        this.indexCode = indexCode;
    }
    
    public String getAnalyticCoordinate()
    {
        return analyticCoordinate;
    }
    
    public void setAnalyticCoordinate(String analyticCoordinate)
    {
        this.analyticCoordinate = analyticCoordinate;
    }
    
    public String getItem()
    {
        return item;
    }
    
    public void setItem(String item)
    {
        this.item = item;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getReceiveType()
    {
        return receiveType;
    }
    
    public void setReceiveType(String receiveType)
    {
        this.receiveType = receiveType;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getOrganization()
    {
        return organization;
    }
    
    public void setOrganization(String organization)
    {
        this.organization = organization;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getSequenceNo()
    {
        return sequenceNo;
    }
    
    public void setSequenceNo(String sequenceNo)
    {
        this.sequenceNo = sequenceNo;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public String getComparisonSample()
    {
        return comparisonSample;
    }
    
    public void setComparisonSample(String comparisonSample)
    {
        this.comparisonSample = comparisonSample;
    }
    
}
