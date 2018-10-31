package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;


@Entity
@Table(name = "LIMS_USER_ARCHIVE")
public class UserArchive extends UuidKeyEntity
{
    private static final long serialVersionUID = -734009797334342546L;
    
 private String name;
    
    private Gender gender;
    
    private String phone;
    
    private String officePhone;
    
    private String email;
    
    private String employeeNo;
    
    private String deptId;
    
    private String leaderId;
    
    private String province;
    
    private String city;
    
    private String county;
    
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
    
    @Column(name = "DEPT_ID")
    public String getDeptId()
    {
        return deptId;
    }
    
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }
    
    @Column(name = "LEADER_ID")
    public String getLeaderId()
    {
        return leaderId;
    }
    
    public void setLeaderId(String leaderId)
    {
        this.leaderId = leaderId;
    }
    
    @Column(name = "PROVINCE")
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    @Column(name = "CITY")
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    @Column(name = "COUNTY")
    public String getCounty()
    {
        return county;
    }
    
    public void setCounty(String county)
    {
        this.county = county;
    }
}
