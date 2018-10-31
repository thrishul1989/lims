package com.todaysoft.lims.invoice.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.invoice.request.OrderInvoiceImportRecord;
import com.todaysoft.lims.utils.DateUtils;

public class VerifyOrderInvoiceImportRecordsContext
{
    private Set<String> uniqueKeys = new HashSet<String>();
    
    private Map<String, String> operators = new HashMap<String, String>();
    
    private Map<String, String> institutions = new HashMap<String, String>();
    
    public VerifyOrderInvoiceImportRecordsContext()
    {
        institutions.put("北京检验所", "0");
        institutions.put("北京迈基诺", "1");
        institutions.put("重庆检验所", "3");
        institutions.put("重庆迈基诺", "2");
    }
    
    public String getOperatorId(String operatorName)
    {
        return operators.get(operatorName);
    }
    
    public void cacheOperator(String operatorName, String operatorId)
    {
        operators.put(operatorName, operatorId);
    }
    
    public Date getInvoiceTime(String timestamp)
    {
        return DateUtils.parseDate(timestamp);
    }
    
    public BigDecimal getInvoiceAmount(String amount)
    {
        try
        {
            return new BigDecimal(amount);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
    
    public String getInstitutionValue(String institutionName)
    {
        return institutions.get(institutionName);
    }
    
    public boolean isUnique(OrderInvoiceImportRecord record)
    {
        return !uniqueKeys.contains(getKey(record));
    }
    
    public void addValidRecord(OrderInvoiceImportRecord record)
    {
        uniqueKeys.add(getKey(record));
    }
    
    private String getKey(OrderInvoiceImportRecord record)
    {
        return record.getOrderCode() + "_" + record.getInstitution();
    }
}
