package com.todaysoft.lims.maintain.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;

/**
 * 订单查询实体类
 * @author admin
 *
 */
public class OrderSearchRequest
{
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private String recipientName;//收件人姓名
    
    private String creatorId;//创建人ID
    
    private Date createTime; //创建时间
    
    private int pageNo;
    
    private int pageSize;
    
    private String relationId;
    
    private String orderId;
    
    private String sampleCode; //扫描---》查询
    
    private String mainSampleCode;
    
    private String seSampleCode;
    
    private String storeType; //存储类别 1:-> 临时 2:->长期
    
    private String storagingId; //用于前台接收
    
    private String operatorId;
    
    private String operatorName;
    
    private String flag;
    
    private boolean likeSearch; //精确、模糊匹配标志
    
    private String ownerId; //客户
    
    private String creatorName;//业务员
    
    private String contractId;//合同id
    
    private String contractName;//合同名称
    
    private String start;
    
    private String end;
    
    private String orderExaminee;
    
    private Integer transportStatus; //样本状态
    
    private String customerName;//客户姓名
    
    private String salesmanName; //业务员姓名
    
    private String sampleId;
    
    private Integer sheduleStatus;//
    
    private String contractCode;
    
    private boolean ifResolveOrderInfo = true; //是否要解析订单详情
    
    private String id; //修改接收样本id
    
    private String examineeName; //受检人姓名
    
    private Integer errorStatus; //异常处理状态
    
    private String sampleName; //样本名称
    
    private Integer testingStatus; //订单主状态
    
    private Integer paymentStatus; //支付状态
    
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getRelationId()
    {
        return relationId;
    }
    
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public boolean isIfResolveOrderInfo()
    {
        return ifResolveOrderInfo;
    }
    
    public void setIfResolveOrderInfo(boolean ifResolveOrderInfo)
    {
        this.ifResolveOrderInfo = ifResolveOrderInfo;
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
    
    public boolean isLikeSearch()
    {
        return likeSearch;
    }
    
    public void setLikeSearch(boolean likeSearch)
    {
        this.likeSearch = likeSearch;
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
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
}
