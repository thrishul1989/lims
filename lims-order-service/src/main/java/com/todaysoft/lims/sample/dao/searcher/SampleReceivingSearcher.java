package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceiving;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class SampleReceivingSearcher extends IDataAuthoritySearcher implements ISearcher<SampleReceiving>
{
    
    private String orderCode;
    
    private String orderId;
    
    private Integer qcStatus;
    
    private String sampleCode;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String ownerId; //客户
    
    private String creatorName;//业务员
    
    private String examineeName;
    
    private String contractName;
    
    private String start;
    
    private String end;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM SampleReceiving s ,Order o WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM SampleReceiving s ,Order o WHERE 1=1 AND s.orderId=o.id ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addAuthFilter(hql, paramNames, parameters, "receiverId");
        addFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND o.code LIKE :code ");
            paramNames.add("code");
            parameters.add("%" + orderCode + "%");
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND o.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        if (StringUtils.isNotEmpty(ownerId))
        {
            hql.append(" AND o.ownerId =:ownerId");
            paramNames.add("ownerId");
            parameters.add(ownerId);
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND o.creatorName LIKE:creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
        }
        
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND o.id in (select e.order.id from OrderExaminee e where name LIKE :examineeName)");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        
        if (StringUtils.isNotEmpty(contractName))
        {
            hql.append(" AND o.contract.name LIKE :contractName ");
            paramNames.add("contractName");
            parameters.add("%" + contractName + "%");
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.id in (select d.sampleReceiving.id from SampleReceivingDetail d where sampleCode LIKE :sampleCode)");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.receiveTime > :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.receiveTime < :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        hql.append(" order by s.receiveTime desc");
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Override
    public Class<SampleReceiving> getEntityClass()
    {
        return SampleReceiving.class;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public NamedQueryer toDetailQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleReceivingDetail s WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDetailFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addDetailFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != id && !"".equals(id))
        {
            hql.append(" AND s.sampleReceiving.id =:id");
            paramNames.add("id");
            parameters.add(id);
        }
        if (StringUtils.isNotEmpty(qcStatus))
        {
            hql.append(" AND s.qcStatus =:qcStatus");
            paramNames.add("qcStatus");
            parameters.add(qcStatus);
        }
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.sampleCode =:sampleCode");
            paramNames.add("sampleCode");
            parameters.add(sampleCode);
        }
    }
    
    public Integer getQcStatus()
    {
        return qcStatus;
    }
    
    public void setQcStatus(Integer qcStatus)
    {
        this.qcStatus = qcStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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
    
    private String transportType;
    
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    private String expressNo;
    
    public String getExpressNo()
    {
        return expressNo;
    }
    
    public void setExpressNo(String expressNo)
    {
        this.expressNo = expressNo;
    }
    
    private String sampleName;
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public NamedQueryer toPackageQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM AppSampleTransport s ,AppSampleTransportRelation r WHERE 1=1 AND s.id = r.packageId ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addPackageFilter(hql, paramNames, parameters);
        hql.append(" order by s.packDate desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addPackageFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(transportType))
        {
            hql.append(" and s.transportType =:transportType ");
            paramNames.add("transportType");
            parameters.add(transportType);
        }
        
        if (StringUtils.isNotEmpty(expressNo))
        {
            hql.append(" and (s.expressNo =:expressNo or s.transportName =:expressNo ) ");
            paramNames.add("expressNo");
            parameters.add(expressNo);
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" and r.sampleId  in (select bi.sampleId from OrderSampleView bi where bi.sampleCode LIKE :sampleCode)  ");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(sampleName))
        {
            hql.append(" and  r.sampleId  in (select bi.sampleId from OrderSampleView bi where bi.sampleName LIKE :sampleName)   ");
            paramNames.add("sampleName");
            parameters.add("%" + sampleName + "%");
        }
        
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" and r.orderNo like :orderCode ");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" and ( s.createId in (select bi.id from BusinessInfo bi where bi.realName LIKE :creatorName   )  or s.createId in (select c from Customer c where c.realName LIKE :ccName   )  ) ");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
            
            paramNames.add("ccName");
            parameters.add("%" + creatorName + "%");
        }
        
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.sendDate > :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.sendDate < :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
}
