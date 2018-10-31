package com.todaysoft.lims.order.service;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;

public interface IReconciliationService
{
    
    List<OrderAccountStateRecord> searchAccountStateRecord(OrderAccountStateRecordSearch searcher);
    
    void reconciliation(List<OrderAccountStateRecord> bankList, int interfaceCode, OrderAccountCheckTask batch);
    
}
