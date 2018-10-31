package com.todaysoft.lims.system.model.vo.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;

public class Contract
{
    public static final String DRAFT = "0";//草稿
    
    public static final String CONFIRMING = "1";//待确认
    
    public static final String CONFIRMED = "2";//已确认  
    
    public static final String KNOT = "3";//结项
    
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
    
    private String createDate;
    
    private List<ContractSample> conSamples = new ArrayList<ContractSample>();
    
    private List<ContractProduct> conProducts = new ArrayList<ContractProduct>();
    
    private List<ContractUser> conUsers = new ArrayList<ContractUser>();
    
    private String confirmerId;
    
    private String confirmerName;
    
    private Date confirmTime;
    
    private String signUsername;
    
    private String originalRealName;
    
    private ContractContent contractContent;
    
    private ContractPartyB contractPartyB;
    
    private ContractPartyA contractPartyA;
    
    private BigDecimal amount = new BigDecimal(0);
    
    private Integer incomingAmount = 0;
    
    private BigDecimal orderAmount; //已产生费用
    
    private double remainAmount;
    
    private Integer startMode;//启动方式
    
    private Integer invoiceForm;//开票形式
    
    private InvoiceApply invoiceApply;
    
    private Integer countInvoiceAmount;
    
    private String knotName;//结项人姓名
    
    private String knotId;//结项人id
    
    private Date knotTime;//结项日期

    private String projectManager;//项目管理人

    private String prjManagerName ;//项目管理人名称---冗余

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

    private List<ContractInvoiceInfo> contractInvoiceInfoList = Lists.newArrayList();

    private List<Order> orders = Lists.newArrayList();
    
    public String getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    
    public String getKnotName()
    {
        return knotName;
    }
    
    public void setKnotName(String knotName)
    {
        this.knotName = knotName;
    }
    
    public String getKnotId()
    {
        return knotId;
    }
    
    public void setKnotId(String knotId)
    {
        this.knotId = knotId;
    }
    
    public Date getKnotTime()
    {
        return knotTime;
    }
    
    public void setKnotTime(Date knotTime)
    {
        this.knotTime = knotTime;
    }
    
    public ContractPartyA getContractPartyA()
    {
        return contractPartyA;
    }
    
    public void setContractPartyA(ContractPartyA contractPartyA)
    {
        this.contractPartyA = contractPartyA;
    }
    
    public Integer getCountInvoiceAmount()
    {
        return countInvoiceAmount;
    }
    
    public void setCountInvoiceAmount(Integer countInvoiceAmount)
    {
        this.countInvoiceAmount = countInvoiceAmount;
    }
    
    public InvoiceApply getInvoiceApply()
    {
        return invoiceApply;
    }
    
    public void setInvoiceApply(InvoiceApply invoiceApply)
    {
        this.invoiceApply = invoiceApply;
    }
    
    public ContractPartyB getContractPartyB()
    {
        return contractPartyB;
    }
    
    public void setContractPartyB(ContractPartyB contractPartyB)
    {
        this.contractPartyB = contractPartyB;
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
    
    public double getRemainAmount()
    {
        return remainAmount;
    }
    
    public void setRemainAmount(double remainAmount)
    {
        this.remainAmount = remainAmount;
    }
    
    private List<ContractSettleBill> settleBill = new ArrayList<ContractSettleBill>();
    
    public List<ContractSettleBill> getSettleBill()
    {
        return settleBill;
    }
    
    public void setSettleBill(List<ContractSettleBill> settleBill)
    {
        this.settleBill = settleBill;
    }
    
    public BigDecimal getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(BigDecimal orderAmount)
    {
        this.orderAmount = orderAmount;
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
    
    public ContractContent getContractContent()
    {
        return contractContent;
    }
    
    public void setContractContent(ContractContent contractContent)
    {
        this.contractContent = contractContent;
    }
    
    public String getOriginalRealName()
    {
        return originalRealName;
    }
    
    public void setOriginalRealName(String originalRealName)
    {
        this.originalRealName = originalRealName;
    }
    
    public String getSignUsername()
    {
        return signUsername;
    }
    
    public void setSignUsername(String signUsername)
    {
        this.signUsername = signUsername;
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
    
    public String getConfirmerId()
    {
        return confirmerId;
    }
    
    public void setConfirmerId(String confirmerId)
    {
        this.confirmerId = confirmerId;
    }
    
    public String getConfirmerName()
    {
        return confirmerName;
    }
    
    public void setConfirmerName(String confirmerName)
    {
        this.confirmerName = confirmerName;
    }
    
    public Date getConfirmTime()
    {
        return confirmTime;
    }
    
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
    
    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }
    
    @SuppressWarnings("unused")
    private String realPrice;//真是价格分转元
    
    public String getRealPrice()
    {
        if (null != amount)
        {
            return amount.divide(new BigDecimal(100), 2).toString();
        }
        else
        {
            return "0";
        }
        
    }
    
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }
    
    private BigDecimal realincomingAmount;
    
    private BigDecimal realcountInvoiceAmount;
    
    public BigDecimal getRealincomingAmount()
    {
        if (null != incomingAmount)
        {
            return BigDecimal.valueOf(Integer.valueOf(incomingAmount)).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setRealincomingAmount(BigDecimal realincomingAmount)
    {
        this.realincomingAmount = realincomingAmount;
    }
    
    public BigDecimal getRealcountInvoiceAmount()
    {
        if (null != countInvoiceAmount)
        {
            return BigDecimal.valueOf(Integer.valueOf(countInvoiceAmount)).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setRealcountInvoiceAmount(BigDecimal realcountInvoiceAmount)
    {
        this.realcountInvoiceAmount = realcountInvoiceAmount;
    }

    public List<ContractInvoiceInfo> getContractInvoiceInfoList() {
        return contractInvoiceInfoList;
    }

    public void setContractInvoiceInfoList(List<ContractInvoiceInfo> contractInvoiceInfoList) {
        this.contractInvoiceInfoList = contractInvoiceInfoList;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
