package com.todaysoft.lims.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Project;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.SampleReceive;
import com.todaysoft.lims.gateway.model.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.gateway.model.request.charge.CommitChargeRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.OrderListRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetailRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceivePagingRequest;
import com.todaysoft.lims.gateway.service.ISampleReceiveService;
import com.todaysoft.lims.gateway.service.adapter.SampleReceiveAdapter;


@Service
public class SampleReceiveService implements ISampleReceiveService{

	@Autowired
	private SampleReceiveAdapter sampleReceiceAdapter;

	@Autowired
	private ProjectService projectService;
	@Override
	public Pagination<SampleReceive> paging(SampleReceivePagingRequest request) {

		
		Pagination<SampleReceive> pagings = sampleReceiceAdapter.paging(request);
	
		List<SampleReceive> srs = pagings.getRecords();
		for (SampleReceive sampleReceive : srs) {
			if(!StringUtils.isEmpty(sampleReceive.getRelatedItems())){
				sampleReceive.setProject(projectService.get(Integer.parseInt(sampleReceive.getRelatedItems())));
			}
			if(!StringUtils.isEmpty(sampleReceive.getOrderId())){
				SampleReceiveOrder order =  sampleReceiceAdapter.getOrderById(sampleReceive.getOrderId());
				sampleReceive.setOrderName(order.getOrderName());
			}
		}
		
		return pagings;
		
	
    }

	@Override
	public SampleReceive getSampleReceive(Integer id) {
		return sampleReceiceAdapter.getSampleReceive(id);
	}

	@Override
	public void delete(Integer id) {
		sampleReceiceAdapter.delete(id);
	}

	@Override
	public void create(SampleFormRequest request) {
		sampleReceiceAdapter.create(request);		
	}

	@Override
	public void modify(SampleFormRequest request) {
		sampleReceiceAdapter.modify(request);		
	}

	@Override
	public Integer createOrder(SampleReceiveOrder request) {
		return sampleReceiceAdapter.createOrder(request);	
	}

	/**
	 * 创建样本接收明细
	 */
	@Override
	public Integer createReceiveDetal(SampleReceiveDetail request) {
		return sampleReceiceAdapter.createReceiveDetal(request);	
	}

	/**
	 * 所有未关联样本接收订单
	 */
	@Override
	public List<SampleReceiveOrder> orderlist(OrderListRequest request) {
		return sampleReceiceAdapter.orderList(request);
	}

	@Override
	public List<SampleReceiveDetail> detaillist(
			SampleReceiveDetailRequest request) {
		return sampleReceiceAdapter.detailList(request);
	}

	@Override
	public void deletedetail(Integer id) {
		sampleReceiceAdapter.deleteDetail(id);
	}

	@Override
	public Integer createReceiveRecord(SampleFormRequest request) {
		return sampleReceiceAdapter.createReceiveRecord(request);
	}

	@Override
	public Pagination<SampleReceiveOrder> receiveOrderPaging(OrderListRequest request) {
		return sampleReceiceAdapter.receiveOrderPaging(request);
	}

	@Override
	public SampleReceiveOrder getOrderById(String id) {
		return sampleReceiceAdapter.getOrderById(id);
	}


	@Override
	public List<SampleReceiveDetail> getRelation(SampleFormRequest request) {
		
		return sampleReceiceAdapter.getRelation(request);
	}

	@Override
	public SampleReceiveDetail getSampleReceiveDetail(Integer id) {
		
		return sampleReceiceAdapter.getSampleReceiveDetail(id);
	}


	@Override
	public List<SampleReceiveDetail> getDetailByOrderId(Integer id) {
		
		return sampleReceiceAdapter.getDetailByOrderId(id);
	}

	@Override
	public List<SampleReceiveDetail> getDetailBySampleCode(String id) {
		return sampleReceiceAdapter.getDetailBySampleCode(id);
	}

	@Override
	public Pagination<SampleReceiveDetail> sampleDetailPaging(SampleReceiveDetailPagingRequest request) {
		
		return sampleReceiceAdapter.sampleDetailPaging(request);
	}

	@Override
	public Boolean redoSampleDetail(String data) {
		return sampleReceiceAdapter.redoSampleDetail(data);
	}
	
	@Override
	public Boolean checkedOderName(String orderName) {
		return sampleReceiceAdapter.checkedOderName(orderName);
	}

	@Override
	public Integer createSampleReceiveDetalUpdate(SampleReceiveDetail request) {
		return sampleReceiceAdapter.createReceiveDetalUpdate(request);	
	}
	
	

	@Override
	public SampleReceiveOrder searchOrderById(Integer id) {
		return sampleReceiceAdapter.searchOrderById(id);
	}

	@Override
	public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(
			OrderListRequest request) {
		return sampleReceiceAdapter.searchSampleDetailByOrderId(request);
	}

	@Override
	public Integer commitCharge(CommitChargeRequest request) {
		return sampleReceiceAdapter.commitCharge(request);
	}

	

	
}
