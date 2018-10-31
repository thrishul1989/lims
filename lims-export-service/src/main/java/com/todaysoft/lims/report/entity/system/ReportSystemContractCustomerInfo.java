package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_CONTRACT_CUSTOMER_INFO")
public class ReportSystemContractCustomerInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 2242066789709309491L;
    private String taskId;
    private ReportSystemContractInfo contractInfo;
    private String name;//姓名
    private String phone;//手机号
    private String sex;//性别 
    private String dept;//科室
    private String institutionName;//单位
    private String position;//职称
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @JsonIgnore
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "PHONE")
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    @Column(name = "SEX")
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
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
    
    @Column(name = "INSTITUTION_NAME")
    public String getInstitutionName()
    {
        return institutionName;
    }
    public void setInstitutionName(String institutionName)
    {
        this.institutionName = institutionName;
    }
    
    @Column(name = "POSITION_NAME")
    public String getPosition()
    {
        return position;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    
}
