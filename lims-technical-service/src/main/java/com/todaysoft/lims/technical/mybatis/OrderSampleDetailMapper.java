package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.OrderSampleDetail;

public interface OrderSampleDetailMapper
{
    int insert(OrderSampleDetail record);
    
    int insertSelective(OrderSampleDetail record);
    
    List<OrderSampleDetail> selectByOrderId(String orderId);
    
    OrderSampleDetail selectBySampleCode(String sampleCode);
    
    OrderSampleDetail selectBySampleId(String sampleId);
    
    List<OrderSampleDetail> selectByFamilyAnalysisId(String familyAnalysisId);
}