package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class ExamineeInfoModel
{
  //-------------受检人信息----------
    private String examineeName;//名称
    private String nation;//民族
    private String sex;//性别
    private String birthday;//出生日期
    private String age;//申请检测时年龄
    private String origin;//籍贯
    private String contactName;//联系人
    private String contactPhone;//联系电话
    private String contactEmail;//联系邮箱
    private String recordNo;//病例号
    public String getExamineeName()
    {
        return examineeName;
    }
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    public String getNation()
    {
        return nation;
    }
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    public String getBirthday()
    {
        return birthday;
    }
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    public String getAge()
    {
        return age;
    }
    public void setAge(String age)
    {
        this.age = age;
    }
    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    public String getContactName()
    {
        return contactName;
    }
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    public String getContactPhone()
    {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    public String getContactEmail()
    {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    public String getRecordNo()
    {
        return recordNo;
    }
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
}
