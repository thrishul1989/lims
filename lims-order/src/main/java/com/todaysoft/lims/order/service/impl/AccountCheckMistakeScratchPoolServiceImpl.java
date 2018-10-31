package com.todaysoft.lims.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.order.model.enums.MistakeHandleStatusEnum;
import com.todaysoft.lims.order.model.enums.ReconciliationMistakeTypeEnum;
import com.todaysoft.lims.order.mybatis.mapper.AccountCheckMistakeScratchPoolMapper;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPoolExample;
import com.todaysoft.lims.order.service.IAccountCheckMistakeScratchPoolService;
import com.todaysoft.lims.order.service.IAccountCheckTransactionService;
import com.todaysoft.lims.order.util.DateUtil;

@Service
public class AccountCheckMistakeScratchPoolServiceImpl implements IAccountCheckMistakeScratchPoolService
{
    
    @Autowired
    private AccountCheckMistakeScratchPoolMapper accountCheckMistakeScratchPoolMapper;
    
    @Autowired
    private IAccountCheckTransactionService accountCheckTransactionService;
    
    @Override
    public List<AccountCheckMistakeScratchPool> listScratchPoolRecord(AccountCheckMistakeScratchPoolExample example)
    {
        if (example == null)
        {
            example = new AccountCheckMistakeScratchPoolExample();
        }
        return accountCheckMistakeScratchPoolMapper.selectByExample(example);
    }
    
    @Override
    @Transactional
    public void savaListDate(List<AccountCheckMistakeScratchPool> insertScreatchRecordList)
    {
        for (AccountCheckMistakeScratchPool pool : insertScreatchRecordList)
        {
            accountCheckMistakeScratchPoolMapper.insert(pool);
        }
    }
    
    @Override
    @Transactional
    public void deleteFromPool(List<AccountCheckMistakeScratchPool> removeScreatchRecordList)
    {
        for (AccountCheckMistakeScratchPool pool : removeScreatchRecordList)
        {
            accountCheckMistakeScratchPoolMapper.deleteByPrimaryKey(pool.getId());
        }
    }
    
    @Override
    public void validateScratchPool()
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(DateUtil.addDay(new Date(), 0));
        AccountCheckMistakeScratchPoolExample example = new AccountCheckMistakeScratchPoolExample();
        example.createCriteria().andBillDateEqualTo(dateStr);
        List<AccountCheckMistakeScratchPool> list = listScratchPoolRecord(example);
        List<AccountCheckMistake> mistakeList = null;
        // 如果有数据
        if (!list.isEmpty())
        {
            mistakeList = new ArrayList<AccountCheckMistake>();
            for (AccountCheckMistakeScratchPool scratchRecord : list)
            {
                // 创建差错记录
                AccountCheckMistake mistake = new AccountCheckMistake();
                mistake.setAccountCheckTaskId(scratchRecord.getCheckTaskId());
                mistake.setBillDate(scratchRecord.getBillDate());
                mistake.setErrType(ReconciliationMistakeTypeEnum.BANK_MISS.getDesc());
                mistake.setHandleStatus(MistakeHandleStatusEnum.NOHANDLE.name());
                mistake.setOrderNo(scratchRecord.getOrderNo());
                mistake.setTradeTime(scratchRecord.getTranDate());
                mistake.setTrxNo(scratchRecord.getReferNo());
                mistake.setOrderAmount(scratchRecord.getCheckAmount());
                mistake.setBankType(scratchRecord.getPayType().toString());
                mistake.setCreateTime(new Date());
                mistake.setPaymentFullTime(scratchRecord.getPaymentTime());
                mistakeList.add(mistake);
            }
            
            accountCheckTransactionService.removeDateFromPool(list, mistakeList);
            
        }
    }
    
}
