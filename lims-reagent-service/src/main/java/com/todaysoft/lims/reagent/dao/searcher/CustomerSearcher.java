package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Company;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.utils.StringUtils;

public class CustomerSearcher implements ISearcher<Customer>
{
    private String currentName;

    private String keys;

    private String id;
    public String business;

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    private Integer roleType;//0 客户，1附属账号
    
    private String realName;//真实姓名
    
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
    
    private String createDate;
    
    private String updateDate;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String testingType;//营销中心
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
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
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append("SELECT c FROM Customer c,CustomerRelation cr,BusinessInfo b where cr.business.id = b.id and cr.customer.id = c.id");
        }
        else
        {
            hql.append("SELECT c FROM Customer as c where 1=1 ");
        }
        
        hql.append(" AND c.delFlag = 0 and c.parentId is null ");

        addRealNameFilter(hql, paramNames, parameters);
        addPhoneNumFilter(hql, paramNames, parameters);
        addCompanyFilter(hql, paramNames, parameters);
        addCreateFilter(hql, paramNames, parameters);
        addCurrentFilter(hql, paramNames, parameters);
        addCurrentNameFilter(hql, paramNames, parameters);
        addActiveFilter(hql, paramNames, parameters);
        addDisableFilter(hql, paramNames, parameters);
        addAnalyseTypeFilter(hql, paramNames, parameters);
        addTestingTypeFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addAnalyseTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(analyseType))
        {
            hql.append(" AND c.analyseType = :analyseType");
            paramNames.add("analyseType");
            parameters.add(analyseType);
        }
    }

    @Override
    public Class<Customer> getEntityClass()
    {
        return Customer.class;
    }
    
    private void addRealNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(realName))
        {
            hql.append(" AND c.realName LIKE :realName");
            paramNames.add("realName");
            parameters.add("%" + realName + "%");
        }
    }
    
    private void addPhoneNumFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(phoneNum))
        {
            hql.append(" AND c.phoneNum LIKE :phoneNum");
            paramNames.add("phoneNum");
            parameters.add("%" + phoneNum + "%");
        }
    }
    
    private void addCompanyFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(company))
        {
            if (StringUtils.isNotEmpty(company.getName()))
            {
                hql.append(" AND c.company.name LIKE :company");
                paramNames.add("company");
                parameters.add("%" + company.getName() + "%");
            }
            
        }
    }
    
    private void addCreateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(createId))
        {
            if (StringUtils.isNotEmpty(createId.getRealName()))
            {
                hql.append(" AND c.createId.realName LIKE :name");
                paramNames.add("name");
                parameters.add("%" + createId.getRealName() + "%");
            }
            
        }
    }

    private void addCurrentFilter(StringBuffer hql, List<String> paraNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(currentId)) {
            if (StringUtils.isNotEmpty(currentId.getRealName())) {
                hql.append(" AND EXISTS (SELECT cr.id FROM CustomerRelation cr WHERE cr.customer.id = c.id AND cr.business.realName LIKE :businessName)");
                paraNames.add("businessName");
                parameters.add("%" + currentId.getRealName() + "%");
             }
        }
    }

    private void addCurrentNameFilter(StringBuffer hql, List<String> paraNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(currentName)) {
            hql.append(" AND EXISTS (SELECT cr.id FROM CustomerRelation cr WHERE cr.customer.id = c.id AND cr.business.realName LIKE :currentName)");
            paraNames.add("currentName");
            parameters.add("%" + currentName + "%");
        }
    }



    private void addActiveFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(activateStatus))
        {
            hql.append(" AND c.activateStatus =:activateStatus");
            paramNames.add("activateStatus");
            parameters.add(activateStatus);
        }
    }
    
    private void addDisableFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(disableStatus))
        {
            hql.append(" AND c.disableStatus =:disableStatus");
            paramNames.add("disableStatus");
            parameters.add(disableStatus);
        }
    }
    
    private void addTestingTypeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append(" AND b.testingType = :testingType");
            paramNames.add("testingType");
            parameters.add(testingType);
        }
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

    public BusinessInfo getCurrentId() {
        return currentId;
    }

    public void setCurrentId(BusinessInfo currentId) {
        this.currentId = currentId;
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
