package com.todaysoft.lims.sample.dao.searcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.contract.Contract;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.utils.StringUtils;

public class ContractSearcher extends IDataAuthoritySearcher implements ISearcher<Contract>
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String status;
    
    private Integer marketingCenter;
    
    private String signUser;
    
    private String flag;
    
    private String settlementMode; //结算方式
    
    private BigDecimal orderAmountRequest; //应结费用
    
    private boolean orderRule; //排序规则  默认false 逆序
    
    private String projectManager;//项目管理人
    
    public String getProjectManager()
    {
        return projectManager;
    }
    
    public void setProjectManager(String projectManager)
    {
        this.projectManager = projectManager;
    }
    
    public boolean isOrderRule()
    {
        return orderRule;
    }
    
    public void setOrderRule(boolean orderRule)
    {
        this.orderRule = orderRule;
    }
    
    public BigDecimal getOrderAmountRequest()
    {
        return orderAmountRequest;
    }
    
    public void setOrderAmountRequest(BigDecimal orderAmountRequest)
    {
        this.orderAmountRequest = orderAmountRequest;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSettlementMode()
    {
        return settlementMode;
    }
    
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    @Override
    public Class<Contract> getEntityClass()
    {
        return Contract.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Contract s  WHERE s.deleted=0");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addStatusFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        addSignUserFilter(hql, paramNames, parameters);
        addFlagFilter(hql, paramNames, parameters);
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
        if (StringUtils.isEmpty(signUser))
        {
            hql.append("FROM Contract s  WHERE s.deleted=0");
        }
        else
        {
            hql.append("select s FROM Contract s,BusinessInfo b  WHERE s.deleted=0 and s.signUser = b.id");
        }
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addStatusFilter(hql, paramNames, parameters);
        addPrjManagerFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        addSignUserFilter(hql, paramNames, parameters);
        if ("2".equals(status))
        {
            addFlagFilter(hql, paramNames, parameters);
        }
        //addModeFilter(hql, paramNames, parameters);
        hql.append(" order by s.updateTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private String[] payStatus;
    
    public String[] getPayStatus()
    {
        return payStatus;
    }
    
    public void setPayStatus(String[] payStatus)
    {
        this.payStatus = payStatus;
    }
    
    private boolean secondOrderBy; //二级排序标志
    
    public boolean isSecondOrderBy()
    {
        return secondOrderBy;
    }
    
    public void setSecondOrderBy(boolean secondOrderBy)
    {
        this.secondOrderBy = secondOrderBy;
    }
    
    public NamedQueryer toPaymentQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        if (StringUtils.isEmpty(signUser))
        {
            hql.append("select s FROM Contract s  WHERE s.deleted=0  ");
        }
        else
        {
            hql.append("select distinct s FROM Contract s,BusinessInfo b   WHERE s.deleted=0 and s.signUser = b.id  ");
        }
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        
        addPayStatusFilter(hql, paramNames, parameters); //已确认 || 已确认或者已结项
        addMarketingCenterFilter(hql, paramNames, parameters);
        addSignUserFilter(hql, paramNames, parameters);
        //addNoStatusFilter(hql, paramNames, parameters); //非结项
        //addStatusFilter(hql, paramNames, parameters); //已确认
        // addFlagFilter(hql, paramNames, parameters); 不加过期限制   需要限制 查询条件加上"inDate".equals(flag)
        addModeFilter(hql, paramNames, parameters);
        if (secondOrderBy)
        {
            hql.append(" order by  (select sum(o.amount+o.subsidiarySampleAmount-o.discountAmount) from Order o  where o.contract.id = s.id ) - s.incomingAmount   desc ");
            hql.append(" , s.effectiveEnd asc ");
        }
        else
        {
            hql.append("order by s.effectiveEnd asc");
        }
        // hql.append(" order by s.effectiveEnd asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addModeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(settlementMode))
        {
            hql.append(" AND s.id in (select con.contractId from ContractContent con where con.settlementMode =:mode  ) ");
            paramNames.add("mode");
            parameters.add(settlementMode);
        }
        else
        {
            hql.append(" AND s.id in (select con.contractId from ContractContent con where con.settlementMode !=:mode  ) ");
            paramNames.add("mode");
            parameters.add(Constant.CONTRACT_SETTLE_METHOD_ONEORDER);
        }
    }
    
    private void addSettleModeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(settlementMode))
        {
            hql.append(" AND s.id in (select con.contractId from ContractContent con where con.settlementMode =:mode  ) ");
            paramNames.add("mode");
            parameters.add(settlementMode);
        }
        else
        {
            hql.append(" AND s.id in (select con.contractId from ContractContent con where con.settlementMode !=:mode  ) ");
            paramNames.add("mode");
            parameters.add(Constant.CONTRACT_SETTLE_METHOD_ONEORDER);
        }
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
    }
    
    /*  private void addNoStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
      {
          if (!StringUtils.isEmpty(status))
          {
              hql.append(" AND s.status != :status");
              paramNames.add("status");
              parameters.add(status);
          }
      }*/
    
    private void addPayStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(payStatus))
        {
            hql.append(" AND s.status in :status");
            paramNames.add("status");
            parameters.add(payStatus);
        }
    }
    
    private void addStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND s.status = :status");
            paramNames.add("status");
            parameters.add(status);
        }
    }
    
    private void addMarketingCenterFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != marketingCenter)
        {
            hql.append(" AND s.marketingCenter LIKE :marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(marketingCenter);
        }
    }
    
    private void addPrjManagerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(projectManager))
        {
            hql.append(" AND s.projectManager = :projectManager");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }
        
    }
    
    private void addSignUserFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(signUser))
        {
            hql.append(" AND b.id = :signUser");
            paramNames.add("signUser");
            parameters.add(signUser);
        }
        
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
    }
    
    private void addFlagFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if ("inDate".equals(flag))
        {
            hql.append(" AND s.signDate <= str_to_date(now(),'%Y-%m-%d') and str_to_date(now(),'%Y-%m-%d') <= s.effectiveEnd");
        }
        else
        {
            hql.append(" AND str_to_date(now(),'%Y-%m-%d') > s.effectiveEnd ");
        }
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
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public NamedQueryer toPayQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM ContractPaymentRecord u WHERE  u.contractId =:id ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("id");
        parameters.add(id);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    public NamedQueryer toSettlementQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("select b FROM ContractSettleBill b  WHERE b.status =:status");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        addSettlementFilter(hql, paramNames, parameters);
        hql.append(" order by b.createTime asc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addSettlementFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        paramNames.add("status");
        parameters.add(0);
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND b.contract.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND b.contract.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        if (StringUtils.isNotEmpty(marketingCenter))
        {
            hql.append(" AND b.contract.marketingCenter =:marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(marketingCenter);
        }
        if (!StringUtils.isEmpty(signUser))
        {
            
            hql.append(" AND b.contract.signUser = :signUser");
            paramNames.add("signUser");
            parameters.add(signUser);
            
        }
        if (StringUtils.isNotEmpty(orderAmountRequest))
        {
            hql.append(" AND b.orderAmount =:orderAmount");
            paramNames.add("orderAmount");
            parameters.add(orderAmountRequest.multiply(new BigDecimal(100)).intValue());
        }
        
    }
    
    public NamedQueryer toSettleManagerQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        if (StringUtils.isEmpty(signUser))
        {
            hql.append("select distinct s FROM Contract s left join s.settleBill bill WHERE s.deleted=0 and s.status = :status ");
        }
        else
        {
            hql.append("select distinct s FROM Contract s,BusinessInfo b left join s.settleBill bill WHERE s.deleted=0 and s.status = :status and s.signUser = b.id ");
        }
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("status");
        parameters.add(Constant.CONTRACT_STATUS_CONFIRM);
        addSettleModeFilter(hql, paramNames, parameters);
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        addSignUserFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        hql.append(" order by  IF(ISNULL(bill.createTime),1,0) , bill.createTime asc ");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private String applyCode;
    
    private String applyPerson;
    
    private String applyType;
    
    private Integer[] applyStatus;
    
    public Integer[] getApplyStatus()
    {
        return applyStatus;
    }
    
    public void setApplyStatus(Integer[] applyStatus)
    {
        this.applyStatus = applyStatus;
    }
    
    public String getApplyCode()
    {
        return applyCode;
    }
    
    public void setApplyCode(String applyCode)
    {
        this.applyCode = applyCode;
    }
    
    public String getApplyPerson()
    {
        return applyPerson;
    }
    
    public void setApplyPerson(String applyPerson)
    {
        this.applyPerson = applyPerson;
    }
    
    public String getApplyType()
    {
        return applyType;
    }
    
    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }
    
    /**
     * 多表查询  ---增加APP发票申请表  申请编号、申请人、开票方式
     * @return
     */
    public NamedQueryer toInvoiceQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct a FROM Contract s ,InvoiceApply a WHERE a.contractId = s.id and s.deleted=0 and a.invoiceMethod =:inmethod ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addRequiredFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        addInvoiceQuery(hql, paramNames, parameters);
        addOrder(hql);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addRequiredFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        paramNames.add("inmethod");
        parameters.add(Constant.CONTRACT_INVOICE_METHOD_FOCUS); //集中开票
    }
    
    private void addOrder(StringBuffer hql)
    {
        if (orderRule)
        {
            hql.append(" order by a.applyTime desc");
        }
        else
        {
            hql.append(" order by a.applyTime asc");
        }//按照申请时间asc排序
    }
    
    private void addInvoiceQuery(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(applyCode))
        {
            hql.append(" AND a.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + applyCode + "%");
        }
        
        if (StringUtils.isNotEmpty(applyPerson))
        {
            hql.append(" AND a.creatorId in (select bi.id from BusinessInfo bi where bi.realName LIKE :creatorId)");
            paramNames.add("creatorId");
            parameters.add("%" + applyPerson + "%");
            
        }
        if (StringUtils.isNotEmpty(applyStatus))
        {
            hql.append(" AND a.status in :applyStatus ");
            paramNames.add("applyStatus");
            parameters.add(applyStatus);
        }
        
    }
    
    public NamedQueryer toReportFormQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Contract s  WHERE s.deleted=0");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addNameFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        addSignUserFilter2(hql, paramNames, parameters);
        addContractStatusFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addContractStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND s.status = :status");
            if ("2-1".equals(status))
            {
                hql.append(" AND str_to_date(now(),'%Y-%m-%d') > s.effectiveEnd ");
                status = "2";
                
            }
            else
            {
                if ("2".equals(status))
                {
                    hql.append(" AND s.signDate <= str_to_date(now(),'%Y-%m-%d') and str_to_date(now(),'%Y-%m-%d') <= s.effectiveEnd");
                }
            }
            paramNames.add("status");
            parameters.add(status);
        }
    }
    
    private void addSignUserFilter2(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(signUser))
        {
            hql.append(" AND s.signUser = :signUser");
            paramNames.add("signUser");
            parameters.add(signUser);
        }
        
    }
}
