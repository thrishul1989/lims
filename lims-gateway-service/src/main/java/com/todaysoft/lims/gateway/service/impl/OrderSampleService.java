package com.todaysoft.lims.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.ExperimentProduct;
import com.todaysoft.lims.gateway.model.OrderSample;
import com.todaysoft.lims.gateway.model.OrderSampleDetail;
import com.todaysoft.lims.gateway.model.OrderSampleDetailTrack;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderSampleListRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;
import com.todaysoft.lims.gateway.service.IExperimentProductService;
import com.todaysoft.lims.gateway.service.IOrderSampleService;
import com.todaysoft.lims.gateway.service.ISampleReceiveService;
import com.todaysoft.lims.gateway.service.ISampleService;
import com.todaysoft.lims.gateway.service.ITaskService;
import com.todaysoft.lims.gateway.service.adapter.OrderSampleAdapter;

@Service
public class OrderSampleService implements IOrderSampleService {
	
	@Autowired
	private OrderSampleAdapter adapter;
	
	@Autowired
	private ISampleService sampleService;
	
	@Autowired
	private IExperimentProductService expProductService;
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private ISampleReceiveService sampleReceiveService;
	
	@Override
	public List<OrderSample> list(OrderSampleListRequest request) {
		List<OrderSample> osList = adapter.list(request);
		List<OrderSample> records = new ArrayList<OrderSample>();
		
		if(osList.size() > 0){
			for(OrderSample os : osList){
				setOrderSampleAttribute(os);
				records.add(os);
			}
		}
		return records;
	}

	@Override
	public Integer create(OrderSampleCreateRequest request) {
		return adapter.create(request);
	}

	@Override
	public OrderSample get(Integer id) {
		OrderSample os = adapter.get(id);
		setOrderSampleAttribute(os);
		return os;
	}
	
	
	@Override
	public OrderSampleDetail getOrderSampleDetail(Integer orderId){
		OrderSampleDetail osd = new OrderSampleDetail();
		List<OrderSampleDetailTrack> sampleDetailTrackList = new ArrayList<OrderSampleDetailTrack>();
		SampleReceiveOrder order = sampleReceiveService.getOrderById(orderId.toString());
		List<SampleReceiveDetail> sampleDetailList =  sampleReceiveService.getDetailByOrderId(orderId);
		if(sampleDetailList.size() > 0){
			for(SampleReceiveDetail srd : sampleDetailList){
				OrderSampleListRequest osListRequest = new OrderSampleListRequest();
				osListRequest.setOrderId(orderId);
				osListRequest.setSampleDetailId(srd.getId());
				List<OrderSample> orderSampleList = list(osListRequest);
				OrderSampleDetailTrack osdt = new OrderSampleDetailTrack();
				osdt.setOrderSampleList(orderSampleList);
				osdt.setSampleDetail(srd);
				sampleDetailTrackList.add(osdt);
			}
		osd.setOrder(order);
		osd.setSampleDetailTrackList(sampleDetailTrackList);
		}
		return osd;
	}
	
	
	
	

	public void setOrderSampleAttribute(OrderSample os){
		if("0".equals(os.getSampleType())){
			Sample sample = sampleService.getSample(os.getSampleKey());
			os.setSampleName(sample.getName());
		}
		if("1".equals(os.getSampleType())){
			ExperimentProduct expProduct = expProductService.getExperimentProduct(os.getSampleKey());
			os.setSampleName(expProduct.getName());
		}
		if(os.getSource() == 0){
			//Sample sample = sampleService.getSample(os.getSampleKey());
			os.setSourceName("样本接收");
		}else{
			Task task = taskService.get(os.getSource());
			os.setSourceName(task.getTaskName());
		}
	}
}
