package com.todaysoft.lims.order.service;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;

public interface IAccountCheckTransactionService
{
    
    void saveData(OrderAccountCheckTask batch, List<AccountCheckMistake> mistakeList, List<AccountCheckMistakeScratchPool> insertScreatchRecordList, List<AccountCheckMistakeScratchPool> removeScreatchRecordList, List<OrderPaymentConfirm> updateSuccessRecordList);
    
    void removeDateFromPool(List<AccountCheckMistakeScratchPool> list, List<AccountCheckMistake> mistakeList);
    
}
