package com.todaysoft.lims.system.model.vo;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder(toBuilder = true)
@AllArgsConstructor
public class Customer
{
    private Set<String> ids;

    private String keys;

    private String companyId;
    public Customer()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getRoleType()
    {
        return roleType;
    }
    
    public void setRoleType(Integer roleType)
    {
        this.roleType = roleType;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public String getPhoneNum()
    {
        return phoneNum;
    }
    
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getSalt()
    {
        return salt;
    }
    
    public void setSalt(String salt)
    {
        this.salt = salt;
    }
    
    public String getHeadImg()
    {
        return headImg;
    }
    
    public void setHeadImg(String headImg)
    {
        this.headImg = headImg;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public Company getCompany()
    {
        return company;
    }
    
    public void setCompany(Company company)
    {
        this.company = company;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getRoomNum()
    {
        return roomNum;
    }
    
    public void setRoomNum(String roomNum)
    {
        this.roomNum = roomNum;
    }
    
    public String getDept()
    {
        return dept;
    }
    
    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    public String getLandLine()
    {
        return landLine;
    }
    
    public void setLandLine(String landLine)
    {
        this.landLine = landLine;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getAnalyseType()
    {
        return analyseType;
    }
    
    public void setAnalyseType(String analyseType)
    {
        this.analyseType = analyseType;
    }
    
    public String getCooperate()
    {
        return cooperate;
    }
    
    public void setCooperate(String cooperate)
    {
        this.cooperate = cooperate;
    }
    
    public String getCharacteristic()
    {
        return characteristic;
    }
    
    public void setCharacteristic(String characteristic)
    {
        this.characteristic = characteristic;
    }
    
    public String getResearchFiled()
    {
        return researchFiled;
    }
    
    public void setResearchFiled(String researchFiled)
    {
        this.researchFiled = researchFiled;
    }
    
    public String getIntroduction()
    {
        return introduction;
    }
    
    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public String getRegistDate()
    {
        return registDate;
    }
    
    public void setRegistDate(String registDate)
    {
        this.registDate = registDate;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public Integer getDisableStatus()
    {
        return disableStatus;
    }
    
    public void setDisableStatus(Integer disableStatus)
    {
        this.disableStatus = disableStatus;
    }
    
    public Integer getActivateStatus()
    {
        return activateStatus;
    }
    
    public void setActivateStatus(Integer activateStatus)
    {
        this.activateStatus = activateStatus;
    }
    
    public BusinessInfo getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(BusinessInfo createId)
    {
        this.createId = createId;
    }
    
    public String getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    
    public String getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    private String id;
    
    private Integer roleType;//0 客户，1附属账号
    
    private String realName;//真实姓名
    
    private String name;
    
    private String phoneNum;//
    
    private String password;
    
    private String salt;//盐值
    
    private String headImg;//头像
    
    private String birthday;
    
    private Integer sex;
    
    private Company company;//所属单位
    
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

    private BusinessInfo currentId;//当前业务员

    private String business;//改绑业务员

    private String currentName;
    
    private String createDate;
    
    private String updateDate;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer countSubCustomer;//附属账号数，额外字段
    
    private List<CustomerRelation> customerRelationList;

    private String colNames;

    public String getColNames() {
        return colNames;
    }

    public void setColNames(String colNames) {
        this.colNames = colNames;
    }

    public List<CustomerRelation> getCustomerRelationList()
    {
        return customerRelationList;
    }
    
    public void setCustomerRelationList(List<CustomerRelation> customerRelationList)
    {
        this.customerRelationList = customerRelationList;
    }
    
    public Integer getCountSubCustomer()
    {
        return countSubCustomer;
    }
    
    public void setCountSubCustomer(Integer countSubCustomer)
    {
        this.countSubCustomer = countSubCustomer;
    }
    
    private String deptName;
    
    private String positionName;
    
    private String sexName;
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public String getPositionName()
    {
        return positionName;
    }
    
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }
    
    public String getSexName()
    {
        return sexName;
    }
    
    public void setSexName(String sexName)
    {
        this.sexName = sexName;
    }
    
    private String testingType;//营销中心
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getName()
    {
        return realName;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public BusinessInfo getCurrentId() {
        return currentId;
    }

    public void setCurrentId(BusinessInfo currentId) {
        this.currentId = currentId;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
}
