package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

public class MutationDataRequest
{
    private String id; //任务Id
    
    private String dataCode;
    
    private String dataId;
    
    private String sampleId;
    
    private String projectId;
    
    private String doctorId;
    
    private String analysisSampleId;
    
    private String analysisFamilyId;
    
    private String ifRecheck;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String sampleCode;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getAnalysisFamilyId()
    {
        return analysisFamilyId;
    }
    
    public void setAnalysisFamilyId(String analysisFamilyId)
    {
        this.analysisFamilyId = analysisFamilyId;
    }
    
    public String getDoctorId()
    {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId)
    {
        this.doctorId = doctorId;
    }
    
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getDataId()
    {
        return dataId;
    }
    
    public void setDataId(String dataId)
    {
        this.dataId = dataId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    
    public String getIfRecheck()
    {
        return ifRecheck;
    }
    
    public void setIfRecheck(String ifRecheck)
    {
        this.ifRecheck = ifRecheck;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
