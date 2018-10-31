package com.todaysoft.lims.system.modules.smm.model;

public class APPSalemanSearcher
{
    private String username;
    
    private String name;
    
    private String phone;
    
    private UserState status;
    
    private String roleType;
    
    private String dept;
    
    private String testingType;
    
    private int pageNo;
    
    private int pageSize;

    private String projectManager;//项目管理人

    private String prjManagerName;//项目管理人名称

    private Integer belongArea;//所属区域

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
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
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public UserState getStatus()
    {
        return status;
    }
    
    public void setStatus(UserState status)
    {
        this.status = status;
    }
    
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
    public String getDept()
    {
        return dept;
    }
    
    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
}
