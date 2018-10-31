package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderReduceCheck;
import com.todaysoft.lims.utils.StringUtils;

@Builder
@AllArgsConstructor
public class OrderReduceSearcher implements ISearcher<OrderReduce>
{
    
    private String id;
    
    private String applyReason;//'申请退款原因',
    
    private Integer applyAmount; //'申请减免退款金额',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；3：审核中；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private List<OrderReduceCheck> orderReduceCheckList;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Date applyEndTime;//'额外字段',
    
    private String examineeName;//额外字段
    
    private String orderCode;//额外字段
    
    private String orderType;//额外字段
    
    private String ownerId;//额外字段
    
    public OrderReduceSearcher()
    {
        
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM OrderReduce s left join s.orderId.orderExamineeList e  WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by  s.applyTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(examineeName))
        {
            
            hql.append(" AND e.name LIKE :examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderCode))
        {
            
            hql.append(" AND s.orderId.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + orderCode + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            
            hql.append(" AND s.orderId.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
            
        }
        
        if (StringUtils.isNotEmpty(ownerId))
        {
            
            hql.append(" AND s.orderId.ownerId in (select c.id from Customer c where c.realName LIKE :ownerId )");
            paramNames.add("ownerId");
            parameters.add("%" + ownerId + "%");
            
        }
        if (StringUtils.isNotEmpty(creatorId))
        {
            
            hql.append(" AND s.orderId.creatorId in (select u.id from User u where u.archive.name LIKE :creatorId )");
            paramNames.add("creatorId");
            parameters.add("%" + creatorId + "%");
            
        }
        
        if (StringUtils.isNotEmpty(status))
        {
            
            hql.append(" AND s.status = :status");
            paramNames.add("status");
            parameters.add(status);
            
        }
        
        if (StringUtils.isNotEmpty(applyTime))
        {
            
            hql.append(" AND s.applyTime > :applyTime");
            paramNames.add("applyTime");
            parameters.add(applyTime);
            
        }
        
        if (StringUtils.isNotEmpty(applyEndTime))
        {
            
            Calendar c = Calendar.getInstance();
            c.setTime(applyEndTime);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            hql.append(" AND s.applyTime < :applyEndTime");
            paramNames.add("applyEndTime");
            parameters.add(c.getTime());
            
        }
        
    }
    
    @Override
    public Class<OrderReduce> getEntityClass()
    {
        // TODO Auto-generated method stub
        return OrderReduce.class;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    public Integer getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(Integer applyAmount)
    {
        this.applyAmount = applyAmount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public List<OrderReduceCheck> getOrderReduceCheckList()
    {
        return orderReduceCheckList;
    }
    
    public void setOrderReduceCheckList(List<OrderReduceCheck> orderReduceCheckList)
    {
        this.orderReduceCheckList = orderReduceCheckList;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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
    
}
