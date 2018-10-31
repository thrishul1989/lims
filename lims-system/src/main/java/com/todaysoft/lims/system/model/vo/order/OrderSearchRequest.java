package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;

/**
 * 订单查询实体类
 * @author admin
 *
 */
public class OrderSearchRequest extends OrderBaseReuqest

{
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    private String taskId;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private String recipientName;//收件人姓名
    
    private String creatorId;//创建人ID
    
    private Date createTime; //创建时间
    
    private String id;
    
    private String ownerId; //客户
    
    private String creatorName;//业务员

    private String projectManager;//项目管理人

    private String prjManagerName;//项目管理人名称

    private String contractId;//合同id
    
    private String contractName;//合同名称
    
    private int pageNo;
    
    private int pageSize;
    
    private String orderId;
    
    private String relationId;
    
    private String sampleCode; //样本编号
    
    private String mainSampleCode;
    
    private String seSampleCode;
    
    private String storeType; //存储类别 1:-> 临时 2:->长期
    
    private String storagingId; //用于前台接收
    
    private String operatorId;
    
    private String operatorName;
    
    private String flag;
    
    private String start;
    
    private String end;
    
    private String orderExaminee;
    
    private Integer transportStatus; //样本状态
    
    private String customerName;//客户姓名
    
    private String salesmanName;
    
    private String sampleId;
    
    private Integer sheduleStatus;//流程状态 0-未启动 1-启动异常 2-启动成功
    
    private String contractCode;
    
    private boolean ifResolveOrderInfo = true; //是否要解析订单详情
    
    private String examineeName; //受检人姓名
    
    private Integer errorStatus; //异常处理状态
    
    private String sampleName; //样本名称
    
    private Integer testingStatus; //订单主状态
    
    private Integer paymentStatus; //支付状态
    
    private String productCode;//产品编码
    
    private String contactPhone;//受检人电话
    
    private String recipientPhone;//收件人电话
    
    private String invoiceStatus; //发票状态
    
    private String colNames;//列头
    
    private String userId;
    
    private String userName;
    
    private Integer schedulePaymentStatus;
    
    private Integer notTestingStatus; //订单主状态

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getInvoiceStatus()
    {
        return invoiceStatus;
    }
    
    public void setInvoiceStatus(String invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public Integer getErrorStatus()
    {
        return errorStatus;
    }
    
    public void setErrorStatus(Integer errorStatus)
    {
        this.errorStatus = errorStatus;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getRelationId()
    {
        return relationId;
    }
    
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
    }
    
    public boolean isIfResolveOrderInfo()
    {
        return ifResolveOrderInfo;
    }
    
    public void setIfResolveOrderInfo(boolean ifResolveOrderInfo)
    {
        this.ifResolveOrderInfo = ifResolveOrderInfo;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public Integer getTransportStatus()
    {
        return transportStatus;
    }
    
    public void setTransportStatus(Integer transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    public String getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(String orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    public String getStart()
    {
        return start;
    }
    
    public void setStart(String start)
    {
        this.start = start;
    }
    
    public String getEnd()
    {
        return end;
    }
    
    public void setEnd(String end)
    {
        this.end = end;
    }
    
    public String getSeSampleCode()
    {
        return seSampleCode;
    }
    
    public void setSeSampleCode(String seSampleCode)
    {
        this.seSampleCode = seSampleCode;
    }
    
    public String getMainSampleCode()
    {
        return mainSampleCode;
    }
    
    public void setMainSampleCode(String mainSampleCode)
    {
        this.mainSampleCode = mainSampleCode;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public String getOperatorId()
    {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    
    public String getStoragingId()
    {
        return storagingId;
    }
    
    public void setStoragingId(String storagingId)
    {
        this.storagingId = storagingId;
    }
    
    public String getStoreType()
    {
        return storeType;
    }
    
    public void setStoreType(String storeType)
    {
        this.storeType = storeType;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public String getColNames()
    {
        return colNames;
    }
    
    public void setColNames(String colNames)
    {
        this.colNames = colNames;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }
    
    public Integer getNotTestingStatus()
    {
        return notTestingStatus;
    }
    
    public void setNotTestingStatus(Integer notTestingStatus)
    {
        this.notTestingStatus = notTestingStatus;
    }
    
}
