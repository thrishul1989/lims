package com.todaysoft.lims.system.modules.smm.model;

import java.util.List;

public class InvoiceUser
{
    private String id;
    
    private String testInstitution;//检测机构
    
    private List<InvoiceUserModel> userList;
    
    private boolean delFlag;
    
    private int pageNo;
    
    private int pageSize;
    
    private String invoiceUserIds;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    public List<InvoiceUserModel> getUserList()
    {
        return userList;
    }
    
    public void setUserList(List<InvoiceUserModel> userList)
    {
        this.userList = userList;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getInvoiceUserIds()
    {
        return invoiceUserIds;
    }
    
    public void setInvoiceUserIds(String invoiceUserIds)
    {
        this.invoiceUserIds = invoiceUserIds;
    }
}
