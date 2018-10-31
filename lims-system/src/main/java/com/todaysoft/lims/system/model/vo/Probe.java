package com.todaysoft.lims.system.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Probe
{
    
    private String id;
    
    private String code;
    
    private String name;
    
    private String testingPlatForm;
    
    private Integer enabled;
    
    private String remark;
    
    private Date createTime;
    
    private Integer delFlag;
    
    private Date deleteTime;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Double dataSize;//冗余字段
    
    private List<ProbeGene> probeGeneList = new ArrayList<ProbeGene>();
    
    public Double getDataSize()
    {
        return dataSize;
    }
    
    public void setDataSize(Double dataSize)
    {
        this.dataSize = dataSize;
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getTestingPlatForm()
    {
        return testingPlatForm;
    }
    
    public void setTestingPlatForm(String testingPlatForm)
    {
        this.testingPlatForm = testingPlatForm;
    }
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
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
    
    public List<ProbeGene> getProbeGeneList()
    {
        return probeGeneList;
    }
    
    public void setProbeGeneList(List<ProbeGene> probeGeneList)
    {
        this.probeGeneList = probeGeneList;
    }
}
