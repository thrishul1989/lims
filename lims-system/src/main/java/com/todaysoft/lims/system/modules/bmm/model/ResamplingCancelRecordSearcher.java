package com.todaysoft.lims.system.modules.bmm.model;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;

public class ResamplingCancelRecordSearcher
{
    private String orderCode;
    
    private String sampleCode;

    private String  prjManagerName;//项目管理人名称

    private  String projectManager;//项目管理人
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
}
