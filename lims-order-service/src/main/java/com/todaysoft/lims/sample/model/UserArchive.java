package com.todaysoft.lims.sample.model;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.model.enums.Gender;

public class UserArchive extends UuidKeyEntity
{
    private static final long serialVersionUID = -734009797334342546L;
    
    private String name;
    
    private Gender gender;
    
    private String phone;// 手机
    
    private String officePhone;// 座机
    
    private String email;
    
    private String employeeNo;// 工号
    
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
}
