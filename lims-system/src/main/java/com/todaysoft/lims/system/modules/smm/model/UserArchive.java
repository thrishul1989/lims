package com.todaysoft.lims.system.modules.smm.model;

public class UserArchive
{
    private String id;
    
    private String name;
    
    private Gender gender;
    
    private String sex;
    
    private String phone;// 手机
    
    private String officePhone;// 座机
    
    private String email;
    
    private String employeeNo;// 工号
    
    private String deptId;
    
    private String leaderId;
    
    private String province;
    
    private String city;
    
    private String county;
    
    public String getDeptId()
    {
        return deptId;
    }
    
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }
    
    public String getLeaderId()
    {
        return leaderId;
    }
    
    public void setLeaderId(String leaderId)
    {
        this.leaderId = leaderId;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getCounty()
    {
        return county;
    }
    
    public void setCounty(String county)
    {
        this.county = county;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Gender getGender()
    {
        return gender;
    }
    
    public void setGender(Gender gender)
    {
        this.gender = gender;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getOfficePhone()
    {
        return officePhone;
    }
    
    public void setOfficePhone(String officePhone)
    {
        this.officePhone = officePhone;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getEmployeeNo()
    {
        return employeeNo;
    }
    
    public void setEmployeeNo(String employeeNo)
    {
        this.employeeNo = employeeNo;
    }
    
    private String leadName;//页面显示用
    
    private String deptName;//页面显示用
    
    public String getLeadName()
    {
        return leadName;
    }
    
    public void setLeadName(String leadName)
    {
        this.leadName = leadName;
    }
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getSex()
    {
        return sex;
    }
    
}
