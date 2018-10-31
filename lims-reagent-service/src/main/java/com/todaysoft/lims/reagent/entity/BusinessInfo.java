package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "BUSINESS_INFO")
@Where(clause="DEL_FLAG=0")
public class BusinessInfo extends UuidKeyEntity

{
    
    private String roleType;//1：销售总监；2：大区经理；3：片区经理；4：销售员',
    
    private String testingType;//检测类型
    
    private String phoneNum;
    
    private String realName;
    
    private String headImg;
    
    private String birthday;
    
    private Integer sex;
    
    private String password;
    
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
    
    @Column(name = "PHONE_NUM")
    public String getPhoneNum()
    {
        return phoneNum;
    }
    
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }
    
    @Column(name = "REAL_NAME")
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
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
    
    @Column(name = "BIRTHDAY")
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    @Column(name = "SEX")
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    @Column(name = "PASSWORD")
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Column(name = "SALT")
    public String getSalt()
    {
        return salt;
    }
    
    public void setSalt(String salt)
    {
        this.salt = salt;
    }
    
    @Column(name = "DEPT")
    public String getDept()
    {
        return dept;
    }
    
    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    @Column(name = "USER_NO")
    public String getUserNo()
    {
        return userNo;
    }
    
    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }
    
    @Column(name = "LANDLINE")
    public String getLandLine()
    {
        return landLine;
    }
    
    public void setLandLine(String landLine)
    {
        this.landLine = landLine;
    }
    
    @Column(name = "EMAIL")
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Column(name = "PARENT_ID")
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    

    @Column(name = "DEL_FLAG")
 
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @Column(name = "DISABLE_STATUS")
    public Integer getDisableStatus()
    {
        return disableStatus;
    }
    
    public void setDisableStatus(Integer disableStatus)
    {
        this.disableStatus = disableStatus;
    }
    
    @Column(name = "IS_FIRST_LOGIN")
    public Integer getIsFirstLogin()
    {
        return isFirstLogin;
    }
    
    public void setIsFirstLogin(Integer isFirstLogin)
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
    
    @Column(name = "CREATE_DATE")
    public String getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
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
    
    @Column(name = "UPDATE_DATE")
    public String getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    
}
