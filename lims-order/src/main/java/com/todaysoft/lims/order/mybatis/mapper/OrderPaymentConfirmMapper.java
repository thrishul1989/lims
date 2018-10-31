package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirmExample;

public interface OrderPaymentConfirmMapper
{
    int countByExample(OrderPaymentConfirmExample example);
    
    int deleteByExample(OrderPaymentConfirmExample example);
    
    int deleteByPrimaryKey(String id);
    
    int insert(OrderPaymentConfirm record);
    
    int insertSelective(OrderPaymentConfirm record);
    
    List<OrderPaymentConfirm> selectByExample(OrderPaymentConfirmExample example);
    
    OrderPaymentConfirm selectByPrimaryKey(String id);
    
    int updateByExampleSelective(@Param("record") OrderPaymentConfirm record, @Param("example") OrderPaymentConfirmExample example);
    
    int updateByExample(@Param("record") OrderPaymentConfirm record, @Param("example") OrderPaymentConfirmExample example);
    
    int updateByPrimaryKeySelective(OrderPaymentConfirm record);
    
    int updateByPrimaryKey(OrderPaymentConfirm record);
}