package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import com.todaysoft.lims.gateway.model.ProcessTaskModel;
import com.todaysoft.lims.gateway.model.request.ProcessTaskPagingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SampleReceive;
import com.todaysoft.lims.gateway.model.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.gateway.model.request.charge.CommitChargeRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.OrderListRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetailRequest;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceivePagingRequest;


@Component
public class SampleReceiveAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	@Autowired
	private RestTemplate template;
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SampleReceive> paging(SampleReceivePagingRequest request){
		String url = getServiceUrl("/sampleReceive/paging");
		ResponseEntity<Pagination<SampleReceive>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceivePagingRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceive>>(){
		});
		return exchange.getBody();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public SampleReceive getSampleReceive(Integer id){
		String url = getServiceUrl("/sampleReceive/{id}");
		return template.getForObject(url, SampleReceive.class, Collections.singletonMap("id", id));
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer create(SampleFormRequest request){
		String url = getServiceUrl("/sampleReceive/create");
		return template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void modify(SampleFormRequest request){
		String url = getServiceUrl("/sampleReceive/modify");
		template.postForObject(url, request, Integer.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public void delete(Integer id){
		String url = getServiceUrl("/sampleReceive/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}

	/**
	 * 创建订单
	 * @param request
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer createOrder(SampleReceiveOrder request) {
		String url = getServiceUrl("/sampleReceive/createOrder");
		return template.postForObject(url, request, Integer.class);
	}

	/**
	 * 创建样本接收明细
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer createReceiveDetal(SampleReceiveDetail request) {
		String url = getServiceUrl("/sampleReceive/createReceiveDetal");
		return template.postForObject(url, request, Integer.class);
	}

	/**
	 * 获取所有未关联样本接收订单
	 * @param request
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleReceiveOrder> orderList(OrderListRequest request) {
		String url = getServiceUrl("/order/list");
		ResponseEntity<List<SampleReceiveOrder>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request), new ParameterizedTypeReference<List<SampleReceiveOrder>>(){
		});
		return exchange.getBody();
	}
	/**
	 * 获取所有的样本接收明细
	 * @param request
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleReceiveDetail> detailList(SampleReceiveDetailRequest request) {
		String url = getServiceUrl("/sampleReceive/detailList");
		ResponseEntity<List<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveDetailRequest>(request), new ParameterizedTypeReference<List<SampleReceiveDetail>>(){
		});
		return exchange.getBody();
	}

	
	@HystrixCommand(fallbackMethod = "fallback")
	public void deleteDetail(Integer id) {
		String url = getServiceUrl("/sampleReceive/deleteDetail/{id}");
        template.delete(url, Collections.singletonMap("id", id));
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Integer createReceiveRecord(SampleFormRequest request) {
		String url = getServiceUrl("/sampleReceive/createReceiveRecord");
		return template.postForObject(url, request, Integer.class);
	}
	/**
	 * 分页查询接收订单
	 * @param request
	 * @return
	 */
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SampleReceiveOrder> receiveOrderPaging(OrderListRequest request) {
		String url = getServiceUrl("/order/paging");
		ResponseEntity<Pagination<SampleReceiveOrder>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceiveOrder>>(){
		});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public SampleReceiveOrder getOrderById(String id) {
		String url = getServiceUrl("/order/getOrderById/{id}");
		return template.getForObject(url, SampleReceiveOrder.class, Collections.singletonMap("id", id));
	}

	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleReceiveDetail> getRelation(SampleFormRequest request) {
		String url = getServiceUrl("/sampleReceive/getRelationByRecordId");
		ResponseEntity<List<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleFormRequest>(request), new ParameterizedTypeReference<List<SampleReceiveDetail>>(){
		});
		return exchange.getBody();
	}

	
	@HystrixCommand(fallbackMethod = "fallback")
	public SampleReceiveDetail getSampleReceiveDetail(Integer id) {
		String url = getServiceUrl("/sampleReceive/getSampleReceiveDetail/{id}");
		return template.getForObject(url, SampleReceiveDetail.class, Collections.singletonMap("id", id));
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<ProcessTaskModel> todo(ProcessTaskPagingRequest request){
		String url = getServiceUrl("/process/todo");
		ResponseEntity<Pagination<ProcessTaskModel>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<ProcessTaskPagingRequest>(request), new ParameterizedTypeReference<Pagination<ProcessTaskModel>>(){
		});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public void complete(String id) {
		String url = getServiceUrl("/process/complete/{id}");
		template.getForObject(url,null,Collections.singletonMap("id", id));
	}
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public List<SampleReceiveDetail> getDetailByOrderId(Integer id){
		String url = getServiceUrl("/sampleReceive/getDetailByOrderId/{id}");
		ResponseEntity<List<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<Integer>(id), new ParameterizedTypeReference<List<SampleReceiveDetail>>(){
		}, Collections.singletonMap("id", id));
		return exchange.getBody();
	}

	public List<SampleReceiveDetail> getDetailBySampleCode(String id) {
		String url = getServiceUrl("/testingTask/getDetailBySampleCode/{id}");
		ResponseEntity<List<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<String>(id), new ParameterizedTypeReference<List<SampleReceiveDetail>>(){
		}, Collections.singletonMap("id", id));
		return exchange.getBody();
	}

	public Pagination<SampleReceiveDetail> sampleDetailPaging(
			SampleReceiveDetailPagingRequest request) {
		String url = getServiceUrl("/sampleReceive/sampleDetailPaging");
		ResponseEntity<Pagination<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveDetailPagingRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceiveDetail>>(){
		});
		return exchange.getBody();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean redoSampleDetail(String data) {
		String url = getServiceUrl("/sampleReceive/redoSampleDetail");
		return template.postForObject(url, data, Boolean.class);
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public Boolean checkedOderName(String orderName) {
		String url = getServiceUrl("/sampleReceive/checkedOderName/{orderName}");
		return template.getForObject(url, Boolean.class, Collections.singletonMap("orderName", orderName));
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public Integer createReceiveDetalUpdate(SampleReceiveDetail request) {
		String url = getServiceUrl("/sampleReceive/createReceiveDetalUpdate");
		return template.postForObject(url, request, Integer.class);
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public SampleReceiveOrder searchOrderById(Integer id) {
		String url = getServiceUrl("/sampleReceive/searchOrderById/{id}");
		return template.getForObject(url, SampleReceiveOrder.class, Collections.singletonMap("id", id));
	}
	@HystrixCommand(fallbackMethod = "fallback")
	public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(OrderListRequest request) {
		String url = getServiceUrl("/sampleReceive/searchSampleDetailByOrderId");
		ResponseEntity<Pagination<SampleReceiveDetail>> exchange = template.exchange(
				url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request),
				new ParameterizedTypeReference<Pagination<SampleReceiveDetail>>(){
		});
		return exchange.getBody();
		
	}

	public Integer commitCharge(CommitChargeRequest request) {
		String url = getServiceUrl("/sampleReceive/commitCharge");
		return template.postForObject(url, request, Integer.class);
	}
}
