package com.todaysoft.lims.order.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.order.mybatis.mapper.OrderPaymentConfirmMapper;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.service.IAccountCheckMistakeScratchPoolService;
import com.todaysoft.lims.order.service.IAccountCheckMistakeService;
import com.todaysoft.lims.order.service.IAccountCheckTaskService;
import com.todaysoft.lims.order.service.IAccountCheckTransactionService;
import com.todaysoft.lims.order.util.ReconciliationConstant;

@Service
public class AccountCheckTransactionServiceImpl implements IAccountCheckTransactionService
{
    private static final Log LOG = LogFactory.getLog(AccountCheckTransactionServiceImpl.class);
    
    @Autowired
    private IAccountCheckTaskService accountCheckTaskService;
    
    @Autowired
    private IAccountCheckMistakeScratchPoolService accountCheckMistakeScratchPoolService;
    
    @Autowired
    private IAccountCheckMistakeService accountCheckMistakeService;
    
    @Autowired
    private OrderPaymentConfirmMapper orderPaymentConfirmMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(OrderAccountCheckTask batch, List<AccountCheckMistake> mistakeList, List<AccountCheckMistakeScratchPool> insertScreatchRecordList, List<AccountCheckMistakeScratchPool> removeScreatchRecordList, List<OrderPaymentConfirm> updateSuccessRecordList)
    {
        LOG.info("========>  对完数据后业务数据处理开始=========>");
        
        LOG.info("===> step1:保存任务记录====");
        if (batch.getReconciliationStatus() == null)
        {
            batch.setReconciliationStatus(1);
        }
        accountCheckTaskService.saveOrUpdateData(batch);
        
        LOG.info("===> step2:保存差错记录====总共[" + mistakeList.size() + "]条");
        accountCheckMistakeService.saveListDate(mistakeList);
        
        LOG.info("===> step3:保存记录到缓存池中====总共[" + insertScreatchRecordList.size() + "]条");
        accountCheckMistakeScratchPoolService.savaListDate(insertScreatchRecordList);
        
        LOG.info("===> step4:从缓存池中删除已匹配记录====总共[" + removeScreatchRecordList.size() + "]条");
        accountCheckMistakeScratchPoolService.deleteFromPool(removeScreatchRecordList);
        
        LOG.info("===> step5:更新LIMS平台支付记录====总共[" + updateSuccessRecordList.size() + "]条");
        successPaymentRecord(updateSuccessRecordList);
        
        LOG.info("<========  对完数据后业务数据处理结束<=========");
    }
    
    public void successPaymentRecord(List<OrderPaymentConfirm> updateSuccessRecordList)
    {
        for (OrderPaymentConfirm record : updateSuccessRecordList)
        {
            record.setReconciliationStatus(ReconciliationConstant.RECONCILIATION_STATUS_SUCCESS);
            orderPaymentConfirmMapper.updateByPrimaryKeySelective(record);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDateFromPool(List<AccountCheckMistakeScratchPool> list, List<AccountCheckMistake> mistakeList)
    {
        LOG.info("========>  清理缓冲池中没有对账的数据，记录差错=========>");
        
        LOG.info("===> step1:保存差错记录====总共[" + mistakeList.size() + "]条");
        accountCheckMistakeService.saveListDate(mistakeList);
        
        LOG.info("===> step2:从缓存池中删除已匹配记录====总共[" + list.size() + "]条");
        accountCheckMistakeScratchPoolService.deleteFromPool(list);
        
        LOG.info("<========  清理缓冲池中没有对账的数据，记录差错结束<=========");
    }
    
}
