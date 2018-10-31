package com.todaysoft.lims.smm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "APP_BUSINESS_CONFIG")
public class APPSaleman implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private User user;
    
    private String userId;
    
    private String roleType;
    
    private String testingType;
    
    private String headImg;
    
    private String isFirstLogin;
    
    private String createUser;
    
    private Date createTime;
    
    private String updateUser;
    
    private Date uodateTime;

    private String projectManager;// 项目管理人

    private Integer belongArea; //所属区域
    
    @Transient
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @Id
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "ROLE_TYPE")
    public String getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    
    @Column(name = "TESTING_TYPE")
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    @Column(name = "HEAD_IMG")
    public String getHeadImg()
    {
        return headImg;
    }
    
    public void setHeadImg(String headImg)
    {
        this.headImg = headImg;
    }
    
    @Column(name = "IS_FIRST_LOGIN")
    public String getIsFirstLogin()
    {
        return isFirstLogin;
    }
    
    public void setIsFirstLogin(String isFirstLogin)
    {
        this.isFirstLogin = isFirstLogin;
    }
    
    @Column(name = "CREATE_USER")
    public String getCreateUser()
    {
        return createUser;
    }
    
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "UPDATE_USER")
    public String getUpdateUser()
    {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUodateTime()
    {
        return uodateTime;
    }
    
    public void setUodateTime(Date uodateTime)
    {
        this.uodateTime = uodateTime;
    }

    @Column(name = "PROJECT_MANAGER")
    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    @Column(name = "BELONG_AREA")
    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }
}
