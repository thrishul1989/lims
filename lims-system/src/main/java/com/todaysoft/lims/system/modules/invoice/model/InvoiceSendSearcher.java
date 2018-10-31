package com.todaysoft.lims.system.modules.invoice.model;

import java.util.Set;

public class InvoiceSendSearcher
{
    private Set<String> ids;
    private String category;//任务类型：1-默认开票 2-申请开票
    
    private String drawerNo; //开票单号
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddress;
    
    private int pageNo;
    
    private int pageSize;
    
    private String testingType;//营销中心
    
    private String orderCode;//订单编号

    private Integer status;
    
    private String trackNumber;//快递单号

    public Set<String> getIds()
    {
        return ids;
    }

    public void setIds(Set<String> ids)
    {
        this.ids = ids;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDrawerNo()
    {
        return drawerNo;
    }

    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }

    public String getRecipientName()
    {
        return recipientName;
    }

    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone()
    {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getTestingType()
    {
        return testingType;
    }

    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getTrackNumber()
    {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }

}
