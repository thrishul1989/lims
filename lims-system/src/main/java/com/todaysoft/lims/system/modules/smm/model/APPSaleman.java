package com.todaysoft.lims.system.modules.smm.model;

import java.io.Serializable;
import java.util.Date;

public class APPSaleman implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    private String qqq;
    
    public String getQqq()
    {
        return qqq;
    }
    
    public void setQqq(String qqq)
    {
        this.qqq = qqq;
    }
    
    private UserDetailsModel user;
    
    private String roleType;
    
    private String testingType;
    
    private String headImg;
    
    private String isFirstLogin;
    
    private String createUser;
    
    private Date createTime;
    
    private String updateUser;
    
    private Date uodateTime;

    private String projectManager; //项目管理人

    private Integer belongArea;//所属区域

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public UserDetailsModel getUser()
    {
        return user;
    }
    
    public void setUser(UserDetailsModel user)
    {
        this.user = user;
    }
    
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getHeadImg()
    {
        return headImg;
    }
    
    public void setHeadImg(String headImg)
    {
        this.headImg = headImg;
    }
    
    public String getIsFirstLogin()
    {
        return isFirstLogin;
    }
    
    public void setIsFirstLogin(String isFirstLogin)
    {
        this.isFirstLogin = isFirstLogin;
    }
    
    public String getCreateUser()
    {
        return createUser;
    }
    
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateUser()
    {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }
    
    public Date getUodateTime()
    {
        return uodateTime;
    }
    
    public void setUodateTime(Date uodateTime)
    {
        this.uodateTime = uodateTime;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }


}
