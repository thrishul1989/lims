package com.todaysoft.lims.gateway.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Project;
import com.todaysoft.lims.gateway.model.SampleReceive;
import com.todaysoft.lims.gateway.model.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.gateway.model.request.charge.CommitChargeRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.OrderListRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetailRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceivePagingRequest;


public interface ISampleReceiveService {

	Pagination<SampleReceive> paging(SampleReceivePagingRequest request);

	SampleReceive getSampleReceive(Integer id);

	void create(SampleFormRequest request);

	void modify(SampleFormRequest request);

	void delete(Integer id);

	Integer createOrder(SampleReceiveOrder request);

	Integer createReceiveDetal(SampleReceiveDetail request);

	List<SampleReceiveOrder> orderlist(OrderListRequest request);

	List<SampleReceiveDetail> detaillist(SampleReceiveDetailRequest request);

	void deletedetail(Integer id);

	Integer createReceiveRecord(SampleFormRequest request);

	Pagination<SampleReceiveOrder> receiveOrderPaging(OrderListRequest request);

	SampleReceiveOrder getOrderById(String id);


	List<SampleReceiveDetail> getRelation(SampleFormRequest request);

	List<SampleReceiveDetail> getDetailByOrderId(Integer id);


	SampleReceiveDetail getSampleReceiveDetail(Integer id);

	List<SampleReceiveDetail> getDetailBySampleCode(String id);

	Pagination<SampleReceiveDetail> sampleDetailPaging(SampleReceiveDetailPagingRequest request);

	Boolean redoSampleDetail(String data);
	
	Boolean checkedOderName(String orderName);

	Integer createSampleReceiveDetalUpdate(SampleReceiveDetail request);

	SampleReceiveOrder searchOrderById(Integer id);

	Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(OrderListRequest request);

	Integer commitCharge(CommitChargeRequest request);
	
}
