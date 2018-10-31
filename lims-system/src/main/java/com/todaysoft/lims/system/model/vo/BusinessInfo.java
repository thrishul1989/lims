package com.todaysoft.lims.system.model.vo;

import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;

public class BusinessInfo

{
    
    private String id;
    
    private String roleType;//1：销售总监；2：大区经理；3：片区经理；4：销售员',
    
    private String testingType;//检测类型
    
    private String phoneNum;
    
    private String realName;
    
    private String name;
    
    private String headImg;
    
    private String birthday;
    
    private Integer sex;
    
    private String password;
    
    private String userName;
    
    private UserDetailsModel user;
    
    private String parentName;

    public String getParentName()
    {
        return parentName;
    }
    
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }
    
    private String testingTypeName;
    
    public String getTestingTypeName()
    {
        return testingTypeName;
    }
    
    public void setTestingTypeName(String testingTypeName)
    {
        this.testingTypeName = testingTypeName;
    }
    
    public UserDetailsModel getUser()
    {
        return user;
    }
    
    public void setUser(UserDetailsModel user)
    {
        this.user = user;
    }
    
    private TestingType type;//额外字段
    
    public TestingType getType()
    {
        return type;
    }
    
    public void setType(TestingType type)
    {
        this.type = type;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getPhoneNum()
    {
        return phoneNum;
    }
    
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public String getHeadImg()
    {
        return headImg;
    }
    
    public void setHeadImg(String headImg)
    {
        this.headImg = headImg;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getSalt()
    {
        return salt;
    }
    
    public void setSalt(String salt)
    {
        this.salt = salt;
    }
    
    public String getDept()
    {
        return dept;
    }
    
    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    public String getUserNo()
    {
        return userNo;
    }
    
    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }
    
    public String getLandLine()
    {
        return landLine;
    }
    
    public void setLandLine(String landLine)
    {
        this.landLine = landLine;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public Integer getDisableStatus()
    {
        return disableStatus;
    }
    
    public void setDisableStatus(Integer disableStatus)
    {
        this.disableStatus = disableStatus;
    }
    
    public Integer getIsFirstLogin()
    {
        return isFirstLogin;
    }
    
    public void setIsFirstLogin(Integer isFirstLogin)
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
    
    public String getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    
    public String getUpdateUser()
    {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }
    
    public String getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    private String salt;
    
    private String dept;//部门
    
    private String userNo;//工号
    
    private String landLine;//座机
    
    private String email;
    
    private String parentId;//上级业务员
    
    private Integer delFlag;
    
    private Integer disableStatus;//0：未禁用；1：已禁用；',
    
    private Integer isFirstLogin;//是否首次登陆，0：是；1：否；',
    
    private String createUser;//创建人
    
    private String createDate;
    
    private String updateUser;
    
    private String updateDate;

    private String projectManager;//项目管理人

    private String projectManagerName;//项目管理人姓名--冗余

    private Integer belongArea;//所属区域

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public String getName()
    {
        return realName;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }
}
