package com.todaysoft.lims.order.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.order.mybatis.mapper.AccountCheckMistakeMapper;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeExample;
import com.todaysoft.lims.order.mybatis.parameter.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.service.IAccountCheckMistakeService;

@Service
public class AccountCheckMistakeServiceImpl implements IAccountCheckMistakeService
{
    @Autowired
    private AccountCheckMistakeMapper accountCheckMistakeMapper;
    
    @Override
    @Transactional
    public void saveListDate(List<AccountCheckMistake> mistakeList)
    {
        for (AccountCheckMistake mistake : mistakeList)
        {
            accountCheckMistakeMapper.insert(mistake);
        }
    }
    
    @Override
    public OrderSearchResponse<AccountCheckMistake> mistakePaging(OrderPaymentConfirmSearcher searcher)
    {
        AccountCheckMistakeExample example = adapt(searcher);
        int count = accountCheckMistakeMapper.countByExample(example);
        
        List<AccountCheckMistake> records;
        
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
            
            records = accountCheckMistakeMapper.selectByExample(example);
            
        }
        else
        {
            records = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(searcher.getPageNo(), searcher.getPageSize(), count, records);
    }
    
    private AccountCheckMistakeExample adapt(OrderPaymentConfirmSearcher searcher)
    {
        AccountCheckMistakeExample example = new AccountCheckMistakeExample();
        com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeExample.Criteria criteria = example.createCriteria();
        com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeExample.Criteria orCriteria = example.createCriteria();
        if (StringUtils.isNotEmpty(searcher.getOrderCode()))
        {
            criteria.andOrderNoLike("%" + searcher.getOrderCode() + "%");
            orCriteria.andOrderNoLike("%" + searcher.getOrderCode() + "%");
        }
        if (StringUtils.isNotEmpty(searcher.getErrType()))
        {
            criteria.andErrTypeLike("%" + searcher.getErrType() + "%");
            orCriteria.andErrTypeLike("%" + searcher.getErrType() + "%");
        }
        if (StringUtils.isNotEmpty(searcher.getReferNo()))
        {
            criteria.andTrxNoLike("%" + searcher.getReferNo() + "%");
            orCriteria.andTrxNoLike("%" + searcher.getReferNo() + "%");
        }
        if (StringUtils.isNotEmpty(searcher.getBankTrxNo()))
        {
            criteria.andBankTrxNoLike("%" + searcher.getBankTrxNo() + "%");
            orCriteria.andBankTrxNoLike("%" + searcher.getBankTrxNo() + "%");
        }
        if (StringUtils.isNotEmpty(searcher.getTranDate()))
        {
            criteria.andBankTradeTimeEqualTo(searcher.getTranDate());
            orCriteria.andTradeTimeEqualTo(searcher.getTranDate());
        }
        example.or(orCriteria);
        return example;
    }
}
