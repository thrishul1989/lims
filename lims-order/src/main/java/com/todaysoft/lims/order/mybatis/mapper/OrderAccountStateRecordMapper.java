package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;

public interface OrderAccountStateRecordMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(OrderAccountStateRecord record);
    
    int insertSelective(OrderAccountStateRecord record);
    
    OrderAccountStateRecord selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(OrderAccountStateRecord record);
    
    int updateByPrimaryKey(OrderAccountStateRecord record);
    
    int countByCondition(String merNum, String termId, String referNo, String tradingType);
    
    List<OrderAccountStateRecord> search(OrderAccountStateRecordSearch searcher);
}