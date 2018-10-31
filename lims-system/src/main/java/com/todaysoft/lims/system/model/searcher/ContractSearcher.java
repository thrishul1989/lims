package com.todaysoft.lims.system.model.searcher;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;

public class ContractSearcher
{
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String name;
    
    private String code;
    
    private String id;
    
    private String status;
    
    private Integer marketingCenter;
    
    private String signUser;
    
    private String flag;
    
    private String signUserName;
    
    private String settlementMode; //结算方式
    
    private Integer orderAmount; //应结费用
    
    private BigDecimal orderAmountRequest; //应结费用
    
    private String applyCode;
    
    private String applyPerson;
    
    private String applyType;
    
    private Integer[] applyStatus;
    
    private String[] payStatus; //合同付款用的  2 || 3
    
    private boolean secondOrderBy; //二级排序
    
    private boolean orderRule; //排序规则  ，默认false 逆序
    
    private String editSignUser;//变更业务员

    private String editPrjManager;//变更项目管理人
    
    private String operateId;
    
    private String operateName;
    
    private String colNames;//列头

    private String projectManager ;//项目管理人

    private String prjManagerName;//项目管理人名称

    public String getEditPrjManager() {
        return editPrjManager;
    }

    public void setEditPrjManager(String editPrjManager) {
        this.editPrjManager = editPrjManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public boolean isOrderRule()
    {
        return orderRule;
    }
    
    public void setOrderRule(boolean orderRule)
    {
        this.orderRule = orderRule;
    }
    
    public boolean isSecondOrderBy()
    {
        return secondOrderBy;
    }
    
    public void setSecondOrderBy(boolean secondOrderBy)
    {
        this.secondOrderBy = secondOrderBy;
    }
    
    public String[] getPayStatus()
    {
        return payStatus;
    }
    
    public void setPayStatus(String[] payStatus)
    {
        this.payStatus = payStatus;
    }
    
    private String userId; //客户
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Integer[] getApplyStatus()
    {
        return applyStatus;
    }
    
    public void setApplyStatus(Integer[] applyStatus)
    {
        this.applyStatus = applyStatus;
    }
    
    public String getApplyCode()
    {
        return applyCode;
    }
    
    public void setApplyCode(String applyCode)
    {
        this.applyCode = applyCode;
    }
    
    public String getApplyPerson()
    {
        return applyPerson;
    }
    
    public void setApplyPerson(String applyPerson)
    {
        this.applyPerson = applyPerson;
    }
    
    public String getApplyType()
    {
        return applyType;
    }
    
    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }
    
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    public BigDecimal getOrderAmountRequest()
    {
        return orderAmountRequest;
    }
    
    public void setOrderAmountRequest(BigDecimal orderAmountRequest)
    {
        this.orderAmountRequest = orderAmountRequest;
    }
    
    public String getSettlementMode()
    {
        return settlementMode;
    }
    
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    
    public String getSignUserName()
    {
        return signUserName;
    }
    
    public void setSignUserName(String signUserName)
    {
        this.signUserName = signUserName;
    }
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public Integer getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(Integer marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getSignUser()
    {
        return signUser;
    }
    
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public String getEditSignUser()
    {
        return editSignUser;
    }

    public void setEditSignUser(String editSignUser)
    {
        this.editSignUser = editSignUser;
    }

    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    public String getColNames()
    {
        return colNames;
    }

    public void setColNames(String colNames)
    {
        this.colNames = colNames;
    }
    
}
