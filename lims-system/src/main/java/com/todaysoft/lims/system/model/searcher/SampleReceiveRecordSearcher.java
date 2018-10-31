package com.todaysoft.lims.system.model.searcher;

import java.util.List;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.persist.DataAuthoritySearcher;

public class SampleReceiveRecordSearcher
{
    
    private String id;
    
    private String code;
    
    private String state;
    
    private String acceptPerson;
    
    private int pageNo;
    
    private int pageSize;
    
    private String recordId;
    
    private String orderCode;
    
    private String orderId;
    
    private String sampleCode;
    
    ///新增查询条件
    
    private String orderType;
    
    private String ownerId;
    
    private String creatorName;
    
    private String examineeName;
    
    private String contractName;
    
    private String customerName;
    
    private String start;
    
    private String end;
    
    //新增查询条件
    
    private String transportType;
    
    private String expressNo;
    
    private String sampleName;
    
    private RecordFilter filter;
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getExpressNo()
    {
        return expressNo;
    }
    
    public void setExpressNo(String expressNo)
    {
        this.expressNo = expressNo;
    }
    
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
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
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
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
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getRecordId()
    {
        return recordId;
    }
    
    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getAcceptPerson()
    {
        return acceptPerson;
    }
    
    public void setAcceptPerson(String acceptPerson)
    {
        this.acceptPerson = acceptPerson;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public RecordFilter getFilter()
    {
        return filter;
    }
    
    public void setFilter(RecordFilter filter)
    {
        this.filter = filter;
    }
}
