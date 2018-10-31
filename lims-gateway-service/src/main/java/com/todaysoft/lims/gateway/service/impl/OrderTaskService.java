package com.todaysoft.lims.gateway.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.OrderExperimentProduct;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductListRequest;
import com.todaysoft.lims.gateway.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderTaskCreateRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.service.IOrderExperimentProductService;
import com.todaysoft.lims.gateway.service.IOrderSampleService;
import com.todaysoft.lims.gateway.service.IOrderTaskService;
import com.todaysoft.lims.gateway.service.ISampleReceiveService;
import com.todaysoft.lims.gateway.service.ISampleService;
import com.todaysoft.lims.gateway.service.adapter.OrderTaskAdapter;

@Service
public class OrderTaskService implements IOrderTaskService {
	
	@Autowired
	private OrderTaskAdapter adapter;
	
	@Autowired
	private IOrderSampleService orderSampleService;
	
	@Autowired
	private ISampleReceiveService sampleReceiveService;
	
	@Autowired
	private IOrderExperimentProductService orderExpProductService;

	@Override
	public Integer create(OrderTaskCreateRequest request) {
		
		//保存实验产物数据
		if("2".equals(request.getOutputType())){
			OrderExperimentProductCreateRequest expProductCreate = new OrderExperimentProductCreateRequest();
			expProductCreate.setOrderId(request.getOrderId());
			expProductCreate.setSampleDetailId(request.getSampleDetailId());
			expProductCreate.setTaskId(request.getTaskId());
			expProductCreate.setExpProductId(request.getOutputKey());
			expProductCreate.setLocationCode(request.getOutputLocation());
			expProductCreate.setStoreAmount(request.getOutputAmount());
			expProductCreate.setUnit(request.getOutputUnit());
			expProductCreate.setStoreTime(new Date());
			orderExpProductService.create(expProductCreate);
		}
		
		//样本来源为样本接收
		OrderSampleCreateRequest sampleCreate = new OrderSampleCreateRequest();
		if("1".equals(request.getInputType())){
			sampleCreate.setOrderId(request.getOrderId());
			sampleCreate.setSampleDetailId(request.getSampleDetailId());
			sampleCreate.setSampleType("0");
			sampleCreate.setSource(0);
			sampleCreate.setUsageAmount(request.getInputAmount());
			sampleCreate.setUnit(request.getInputUnit());
			SampleReceiveDetail sampleDetail = sampleReceiveService.getSampleReceiveDetail(request.getSampleDetailId());
			sampleCreate.setSampleKey(Integer.parseInt(sampleDetail.getCode()));
			sampleCreate.setLocationCode(sampleDetail.getTemporaryStorageLocation());
			sampleCreate.setStoreTime(new Date());
			orderSampleService.create(sampleCreate);
		}
		//样本来源为上一步的实验产物
		if("2".equals(request.getInputType())){
			sampleCreate.setOrderId(request.getOrderId());
			sampleCreate.setSampleDetailId(request.getSampleDetailId());
			sampleCreate.setSampleType("1");
			sampleCreate.setUsageAmount(request.getInputAmount());
			sampleCreate.setUnit(request.getInputUnit());
			OrderExperimentProductListRequest oepList = new OrderExperimentProductListRequest();
			oepList.setOrderId(request.getOrderId());
			oepList.setLocationCode(request.getInputLocation());
			OrderExperimentProduct orderExpProduct = orderExpProductService.list(oepList).get(0);
			sampleCreate.setSampleKey(orderExpProduct.getExpProductId());
			sampleCreate.setSource(orderExpProduct.getTaskId());
			sampleCreate.setLocationCode(orderExpProduct.getLocationCode());
			sampleCreate.setStoreTime(orderExpProduct.getStoreTime());
			orderSampleService.create(sampleCreate);
		}
		return adapter.create(request);
	}

}
