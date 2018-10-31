package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.OrderSample;
import com.todaysoft.lims.system.model.vo.OrderSampleDetail;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.SampleReceiveDetail;

public interface IOrderSampleService {

	
 OrderSampleDetail getOrderSampleDetail(Integer orderId);


}
