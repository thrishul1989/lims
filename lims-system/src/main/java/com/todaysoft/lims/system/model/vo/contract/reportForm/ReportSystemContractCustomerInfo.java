package com.todaysoft.lims.system.model.vo.contract.reportForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
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
    
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getDept()
    {
        return dept;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    public String getInstitutionName()
    {
        return institutionName;
    }
    public void setInstitutionName(String institutionName)
    {
        this.institutionName = institutionName;
    }
    
    public String getPosition()
    {
        return position;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    
}
