package com.todaysoft.lims.order.service;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPoolExample;

public interface IAccountCheckMistakeScratchPoolService
{
    
    List<AccountCheckMistakeScratchPool> listScratchPoolRecord(AccountCheckMistakeScratchPoolExample object);
    
    void savaListDate(List<AccountCheckMistakeScratchPool> insertScreatchRecordList);
    
    void deleteFromPool(List<AccountCheckMistakeScratchPool> removeScreatchRecordList);
    
    void validateScratchPool();
    
}
