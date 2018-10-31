package com.todaysoft.lims.order.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.order.model.reconciliation.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.order.mybatis.mapper.OrderAccountCheckTaskMapper;
import com.todaysoft.lims.order.mybatis.mapper.OrderAccountStateRecordMapper;
import com.todaysoft.lims.order.mybatis.mapper.OrderPaymentConfirmMapper;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTaskExample;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirmExample;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;
import com.todaysoft.lims.order.mybatis.parameter.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.service.IAccountCheckTaskService;

@Service
public class AccountCheckTaskServiceImpl implements IAccountCheckTaskService
{
    
    private static final Log LOG = LogFactory.getLog(AccountCheckTaskServiceImpl.class);
    
    @Autowired
    private OrderAccountCheckTaskMapper orderAccountCheckTaskMapper;
    
    @Autowired
    private OrderPaymentConfirmMapper orderPaymentConfirmMapper;
    
    @Autowired
    private OrderAccountStateRecordMapper orderAccountStateRecordMapper;
    
    @Override
    public Boolean isChecked(int interfaceCode, String billDate)
    {
        LOG.info("检查,支付方式[" + interfaceCode + "],订单日期[" + billDate + "]");
        OrderAccountCheckTaskExample example = new OrderAccountCheckTaskExample();
        example.createCriteria().andInterfaceCodeEqualTo(interfaceCode).andTradingDateEqualTo(billDate);
        List<OrderAccountCheckTask> list = orderAccountCheckTaskMapper.selectByExample(example);
        return !list.isEmpty();
    }
    
    @Override
    @Transactional
    public void saveOrUpdateData(OrderAccountCheckTask batch)
    {
        OrderAccountCheckTask task = orderAccountCheckTaskMapper.selectByPrimaryKey(batch.getId());
        if (task == null)
        {
            orderAccountCheckTaskMapper.insertSelective(batch);
        }
        else
        {
            orderAccountCheckTaskMapper.updateByPrimaryKey(batch);
        }
        
    }
    
    @Override
    public OrderAccountCheckTask selectByExample(int interfaceCode, String billDate)
    {
        LOG.info("检查,支付方式[" + interfaceCode + "],订单日期[" + billDate + "]");
        OrderAccountCheckTaskExample example = new OrderAccountCheckTaskExample();
        example.createCriteria().andInterfaceCodeEqualTo(interfaceCode).andTradingDateEqualTo(billDate);
        List<OrderAccountCheckTask> list = orderAccountCheckTaskMapper.selectByExample(example);
        return (null != list && list.size() > 0) ? list.get(0) : null;
    }
    
    @Override
    public OrderSearchResponse<OrderAccountCheckTask> searchAccountCheckTask(OrderAccountCheckTaskSearcher searcher)
    {
        OrderAccountCheckTaskExample example = adapt(searcher);
        int count = orderAccountCheckTaskMapper.countByExample(example);
        
        List<OrderAccountCheckTask> records;
        
        if (count > 0)
        {
            Integer pageNo = searcher.getPageNo();
            Integer pageSize = searcher.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                example.setLimitStart(offset);
                example.setLimitEnd(searcher.getPageSize());
            }
            
            records = orderAccountCheckTaskMapper.selectByExample(example);
            
        }
        else
        {
            records = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(searcher.getPageNo(), searcher.getPageSize(), count, records);
    }
    
    private OrderAccountCheckTaskExample adapt(OrderAccountCheckTaskSearcher searcher)
    {
        OrderAccountCheckTaskExample example = new OrderAccountCheckTaskExample();
        com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTaskExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(searcher.getReconciliationDate()))
        {
            criteria.andTradingDateEqualTo(searcher.getReconciliationDate());
        }
        if (null != searcher.getReconciliationResult())
        {
            criteria.andReconciliationResultEqualTo(searcher.getReconciliationResult());
        }
        if (null != searcher.getReconciliationStatus())
        {
            criteria.andReconciliationStatusEqualTo(searcher.getReconciliationStatus());
        }
        if (null != searcher.getSettleStatus())
        {
            criteria.andSettleStatusEqualTo(searcher.getSettleStatus());
        }
        example.setOrderByClause("RECONCILIATION_DATE DESC");
        return example;
    }
    
    @Override
    public OrderSearchResponse<OrderPaymentConfirm> getPaymentRecordByDate(OrderPaymentConfirmSearcher searcher)
    {
        OrderPaymentConfirmExample example = adapt(searcher);
        int count = orderPaymentConfirmMapper.countByExample(example);
        
        List<OrderPaymentConfirm> records;
        
        if (count > 0)
        {
            Integer pageNo = searcher.getPageNo();
            Integer pageSize = searcher.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                example.setLimitStart(offset);
                example.setLimitEnd(searcher.getPageSize());
            }
            
            records = orderPaymentConfirmMapper.selectByExample(example);
            
        }
        else
        {
            records = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(searcher.getPageNo(), searcher.getPageSize(), count, records);
    }
    
    private OrderPaymentConfirmExample adapt(OrderPaymentConfirmSearcher searcher)
    {
        OrderPaymentConfirmExample example = new OrderPaymentConfirmExample();
        com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirmExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(searcher.getOrderCode()))
        {
            criteria.andOrderCodeEqualTo(searcher.getOrderCode());
        }
        if (StringUtils.isNotEmpty(searcher.getReferNo()))
        {
            criteria.andReferNoEqualTo(searcher.getReferNo());
        }
        
        if (StringUtils.isNotEmpty(searcher.getTranDate()))
        {
            criteria.andTranDateEqualTo(searcher.getTranDate());
        }
        if (searcher.getReconciliationStatus() != null)
        {
            if (searcher.getReconciliationStatus().equals(1))
            {
                criteria.andReconciliationStatusEqualTo(searcher.getReconciliationStatus());
            }
            else
            {
                criteria.andReconciliationStatusIsNull();
            }
            
        }
        
        return example;
    }
    
    @Override
    public void handleTask(OrderAccountCheckTask request)
    {
        orderAccountCheckTaskMapper.updateByPrimaryKeySelective(request);
    }
    
    @Override
    public OrderAccountStateRecord accountStateRecordDetail(OrderPaymentConfirmSearcher request)
    {
        OrderAccountStateRecordSearch searcher = new OrderAccountStateRecordSearch();
        BeanUtils.copyProperties(request, searcher);
        searcher.setTransactionDate(request.getTranDate());
        List<OrderAccountStateRecord> records = orderAccountStateRecordMapper.search(searcher);
        if (null != records && records.size() > 0)
        {
            return records.get(0);
        }
        return new OrderAccountStateRecord();
    }
}
