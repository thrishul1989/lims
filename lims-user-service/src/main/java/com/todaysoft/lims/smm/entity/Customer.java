package com.todaysoft.lims.smm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;


@Entity
@Table(name = "APP_USER_INFO")
@Builder(toBuilder = true)
@AllArgsConstructor
public class Customer extends UuidKeyEntity{

    private Integer roleType;//0 客户，1附属账号
    private String realName;//真实姓名
    private String phoneNum;//
    private String password;
    private String salt;//盐值
    private String headImg;//头像
    private String birthday;
    private Integer sex;
  
    private String address;
    private String roomNum;//办公室好
    private String dept;//所属科室
    private String position;//职称
    private String landLine;//座机
    private String email;
    private String analyseType;//客户参与方式
    private String cooperate;//主要合作方向
    private String characteristic;//就诊特点
    private String researchFiled;//擅长疾病，领域
    private String introduction;//介绍
    private String parentId;//附属账号
    private String registDate;
    private Integer delFlag;
    private Integer disableStatus;//0 未禁用 1已禁用
    private Integer activateStatus;//0 未激活 1已激活
    private BusinessInfo createId;//创建人
    private String createDate;
    private String updateDate;
    
   
    
    
    
    public  Customer(){
        
    }
   
    @Column(name = "ROLE_TYPE")
    public Integer getRoleType()
    {
        return roleType;
    }
    public void setRoleType(Integer roleType)
    {
        this.roleType = roleType;
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
    @Column(name = "PHONE_NUM")
    public String getPhoneNum()
    {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
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
 
    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    @Column(name = "ROOM_NUM")
    public String getRoomNum()
    {
        return roomNum;
    }
    public void setRoomNum(String roomNum)
    {
        this.roomNum = roomNum;
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
    @Column(name = "ANALYSIS_TYPE")
    public String getAnalyseType()
    {
        return analyseType;
    }
    public void setAnalyseType(String analyseType)
    {
        this.analyseType = analyseType;
    }
    @Column(name = "COOPERATE")
    public String getCooperate()
    {
        return cooperate;
    }
    public void setCooperate(String cooperate)
    {
        this.cooperate = cooperate;
    }
    @Column(name = "CHARACTERISTIC")
    public String getCharacteristic()
    {
        return characteristic;
    }
    public void setCharacteristic(String characteristic)
    {
        this.characteristic = characteristic;
    }
    @Column(name = "RESEARCH_FILED")
    public String getResearchFiled()
    {
        return researchFiled;
    }
    public void setResearchFiled(String researchFiled)
    {
        this.researchFiled = researchFiled;
    }
    @Column(name = "INTRODUCTION")
    public String getIntroduction()
    {
        return introduction;
    }
    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
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
    @Column(name = "REGISTER_DATE")
    public String getRegistDate()
    {
        return registDate;
    }
    public void setRegistDate(String registDate)
    {
        this.registDate = registDate;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATE_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public BusinessInfo getCreateId()
    {
        return createId;
    }
    public void setCreateId(BusinessInfo createId)
    {
        this.createId = createId;
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
