package com.todaysoft.lims.sample.entity.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractUpdateRequest
{
    private String id;
    
    private String code;//合同编号
    
    private String name;//合同名称
    
    private String status;//合同状态：0-草稿 1-待确认 2-已确认
    
    private String remark;//合同备注
    
    private Integer marketingCenter;//营销中心，对应原有的检测类型
    
    private String signUser;//业务员
    
    private Date signDate;//签订日期
    
    private Date effectiveStart;//有效期-开始日期
    
    private Date effectiveEnd;//有效期-结束日期
    
    private String original;//合同原件
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime;//创建时间
    
    private String updateId;//最后一次更新人ID
    
    private String updateName;//最后一次更新人姓名
    
    private Date updateTime;//最后一次更新人时间
    
    private boolean deleted;//删除标记，0：未删除；1：已删除
    
    private Date deleteTime;//删除时间
    
    private String hospitalAdmited; //是否入院
    
    private List<ContractSample> conSamples = new ArrayList<ContractSample>();
    
    private List<ContractProduct> conProducts = new ArrayList<ContractProduct>();
    
    private List<ContractUser> conUsers = new ArrayList<ContractUser>();
    
    private BigDecimal amount;
    
    private Integer incomingAmount;
    
    private List<ContractSettleBill> settleBill = new ArrayList<ContractSettleBill>();
    
    private Integer startMode;//启动方式
    
    private Integer invoiceForm;//开票形式
    
    private ContractContent contractContent;

    private String projectManager;//项目管理人

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public ContractContent getContractContent()
    {
        return contractContent;
    }
    
    public void setContractContent(ContractContent contractContent)
    {
        this.contractContent = contractContent;
    }
    
    public Integer getStartMode()
    {
        return startMode;
    }
    
    public void setStartMode(Integer startMode)
    {
        this.startMode = startMode;
    }
    
    public Integer getInvoiceForm()
    {
        return invoiceForm;
    }
    
    public void setInvoiceForm(Integer invoiceForm)
    {
        this.invoiceForm = invoiceForm;
    }
    
    public List<ContractSettleBill> getSettleBill()
    {
        return settleBill;
    }
    
    public void setSettleBill(List<ContractSettleBill> settleBill)
    {
        this.settleBill = settleBill;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
    
    public Date getSignDate()
    {
        return signDate;
    }
    
    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }
    
    public Date getEffectiveStart()
    {
        return effectiveStart;
    }
    
    public void setEffectiveStart(Date effectiveStart)
    {
        this.effectiveStart = effectiveStart;
    }
    
    public Date getEffectiveEnd()
    {
        return effectiveEnd;
    }
    
    public void setEffectiveEnd(Date effectiveEnd)
    {
        this.effectiveEnd = effectiveEnd;
    }
    
    public String getOriginal()
    {
        return original;
    }
    
    public void setOriginal(String original)
    {
        this.original = original;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateId()
    {
        return updateId;
    }
    
    public void setUpdateId(String updateId)
    {
        this.updateId = updateId;
    }
    
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public List<ContractSample> getConSamples()
    {
        return conSamples;
    }
    
    public void setConSamples(List<ContractSample> conSamples)
    {
        this.conSamples = conSamples;
    }
    
    public List<ContractProduct> getConProducts()
    {
        return conProducts;
    }
    
    public void setConProducts(List<ContractProduct> conProducts)
    {
        this.conProducts = conProducts;
    }
    
    public List<ContractUser> getConUsers()
    {
        return conUsers;
    }
    
    public void setConUsers(List<ContractUser> conUsers)
    {
        this.conUsers = conUsers;
    }
    
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
    
}
