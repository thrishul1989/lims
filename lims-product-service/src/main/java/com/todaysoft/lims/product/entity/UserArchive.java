package com.todaysoft.lims.product.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_USER_ARCHIVE")
public class UserArchive extends UuidKeyEntity
{
    private static final long serialVersionUID = -734009797334342546L;
    
    private String name;
    
    private Gender gender;
    
    private String phone;// 手机
    
    private String officePhone;// 座机
    
    private String email;
    
    private String employeeNo;// 工号
    
    private String deptId;
    
    private String leaderId;
    
    @Column(name = "LEADER_ID")
    public String getLeaderId()
    {
        return leaderId;
    }
    
    public void setLeaderId(String leaderId)
    {
        this.leaderId = leaderId;
    }
    
    @Column(name = "DEPT_ID")
    public String getDeptId()
    {
        return deptId;
    }
    
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
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
    
    @Column(name = "GENDER")
    public Gender getGender()
    {
        return gender;
    }
    
    public void setGender(Gender gender)
    {
        this.gender = gender;
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
    
    @Column(name = "OFFICE_PHONE")
    public String getOfficePhone()
    {
        return officePhone;
    }
    
    public void setOfficePhone(String officePhone)
    {
        this.officePhone = officePhone;
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
    
    @Column(name = "EMPLOYEE_NO")
    public String getEmployeeNo()
    {
        return employeeNo;
    }
    
    public void setEmployeeNo(String employeeNo)
    {
        this.employeeNo = employeeNo;
    }
}
