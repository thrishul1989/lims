package com.todaysoft.lims.smm.response;

import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.UserArchive;
import com.todaysoft.lims.smm.entity.enums.Gender;
import com.todaysoft.lims.smm.entity.enums.UserState;

public class UserSimpleModel
{
    private String id;
    
    private String username;
    
    private String name;
    
    private String employeeNo;
    
    private Gender sex;
    
    private String phone;
    
    private String officePhone;
    
    private String email;
    
    private UserState state;
    
    private String leaderId;
    
    public UserSimpleModel(User user)
    {
        this.id = user.getId();
        this.username = user.getUsername();
        this.state = user.getState();
        
        if (null != user.getArchive())
        {
            UserArchive archive = user.getArchive();
            this.name = archive.getName();
            this.employeeNo = archive.getEmployeeNo();
            this.sex = archive.getGender();
            this.phone = archive.getPhone();
            this.officePhone = archive.getOfficePhone();
            this.email = archive.getEmail();
            this.leaderId = archive.getLeaderId();
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmployeeNo()
    {
        return employeeNo;
    }
    
    public void setEmployeeNo(String employeeNo)
    {
        this.employeeNo = employeeNo;
    }
    
    public Gender getSex()
    {
        return sex;
    }
    
    public void setSex(Gender sex)
    {
        this.sex = sex;
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
    
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
    
    public String getLeaderId()
    {
        return leaderId;
    }
    
    public void setLeaderId(String leaderId)
    {
        this.leaderId = leaderId;
    }
    
}
