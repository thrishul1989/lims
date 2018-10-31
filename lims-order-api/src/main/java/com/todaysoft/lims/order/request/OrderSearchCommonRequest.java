package com.todaysoft.lims.order.request;

import com.todaysoft.lims.base.RecordFilter;

public class OrderSearchCommonRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String code;
    
    private String orderCode;
    
    private String contractCode;
    
    private String contractName;
    
    private String orderType;
    
    private String examineeName;
    
    private String customerName;
    
    private String customerCompanyName;
    
    private String salesmanName;
    
    private String start;
    
    private String end;
    
    private Integer status;
    
    private Integer paymentStatus;
    
    private String sampleCode;
    
    private String productCode;
    
    private String contactPhone;
    
    private String recipientPhone;
    
    private Integer testingStatus;
    
    private Integer notTestingStatus;
    
    private Integer settlementType;
    
    private String ownerId;
    
    private Integer sheduleStatus; //异常订单
    
    private RecordFilter filter; //请求参数
    
    private OrderTableFilter tableFilter; //是否关联延迟  --输入参数
    
    private OrderColumnFilter columnFilter; //是否关联延迟 --输出参数
    
    private String orderByClause;
    
    private Integer schedulePaymentStatus;
    
    private String creatorName;
    
    private Integer submitSource;

    private String projectManager;//项目管理人
    private String prjManagerName;//项目管理人名称

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
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCustomerCompanyName()
    {
        return customerCompanyName;
    }
    
    public void setCustomerCompanyName(String customerCompanyName)
    {
        this.customerCompanyName = customerCompanyName;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public RecordFilter getFilter()
    {
        return filter;
    }
    
    public void setFilter(RecordFilter filter)
    {
        this.filter = filter;
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
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public OrderColumnFilter getColumnFilter()
    {
        return columnFilter;
    }
    
    public void setColumnFilter(OrderColumnFilter columnFilter)
    {
        this.columnFilter = columnFilter;
    }
    
    public OrderTableFilter getTableFilter()
    {
        return tableFilter;
    }
    
    public void setTableFilter(OrderTableFilter tableFilter)
    {
        this.tableFilter = tableFilter;
    }
    
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public Integer getNotTestingStatus()
    {
        return notTestingStatus;
    }
    
    public void setNotTestingStatus(Integer notTestingStatus)
    {
        this.notTestingStatus = notTestingStatus;
    }
    
    public String getOrderByClause()
    {
        return orderByClause;
    }
    
    public void setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
    }
    
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
}
