package com.todaysoft.lims.sample.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_USER_INFO")
public class Customer extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String realName;//真实姓名
    
    private Integer delFlag;
    
    private Integer disableStatus;//0 未禁用 1已禁用
    
    private Integer activateStatus;//0 未激活 1已激活
    
    private String analyseType;//客户参与方式
    
    private Company company;
    
    private String phoneNum;
    
    private Integer sex;
    
    private String dept;//所属科室
    
    private String position;//职称
    
    @Column(name = "ANALYSIS_TYPE")
    public String getAnalyseType()
    {
        return analyseType;
    }
    
    public void setAnalyseType(String analyseType)
    {
        this.analyseType = analyseType;
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
    
    @Column(name = "ACTIVATE_STATUS")
    public Integer getActivateStatus()
    {
        return activateStatus;
    }
    
    public void setActivateStatus(Integer activateStatus)
    {
        this.activateStatus = activateStatus;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "INSTITUTION_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Company getCompany()
    {
        return company;
    }
    
    public void setCompany(Company company)
    {
        this.company = company;
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
    
    @Column(name = "SEX")
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
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
    
    @Column(name = "POSITION")
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
}
