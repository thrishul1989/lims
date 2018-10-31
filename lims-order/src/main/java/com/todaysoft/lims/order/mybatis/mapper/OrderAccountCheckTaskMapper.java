package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTaskExample;

public interface OrderAccountCheckTaskMapper
{
    int countByExample(OrderAccountCheckTaskExample example);
    
    int deleteByExample(OrderAccountCheckTaskExample example);
    
    int deleteByPrimaryKey(String id);
    
    int insert(OrderAccountCheckTask record);
    
    int insertSelective(OrderAccountCheckTask record);
    
    List<OrderAccountCheckTask> selectByExample(OrderAccountCheckTaskExample example);
    
    OrderAccountCheckTask selectByPrimaryKey(String id);
    
    int updateByExampleSelective(@Param("record") OrderAccountCheckTask record, @Param("example") OrderAccountCheckTaskExample example);
    
    int updateByExample(@Param("record") OrderAccountCheckTask record, @Param("example") OrderAccountCheckTaskExample example);
    
    int updateByPrimaryKeySelective(OrderAccountCheckTask record);
    
    int updateByPrimaryKey(OrderAccountCheckTask record);
}