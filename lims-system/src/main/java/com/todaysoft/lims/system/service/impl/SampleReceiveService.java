package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.form.SampleReceiveRecordFormRequest;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.Project;
import com.todaysoft.lims.system.model.vo.SampleReceiveDetail;
import com.todaysoft.lims.system.model.vo.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.system.model.vo.SampleReceiveOrder;
import com.todaysoft.lims.system.model.vo.SampleReceiveRecord;
import com.todaysoft.lims.system.model.vo.TestingScheduleStartRequest;
import com.todaysoft.lims.system.model.vo.charge.CommitChargeRequest;
import com.todaysoft.lims.system.model.vo.testingtask.SampleTracing;
import com.todaysoft.lims.system.model.vo.testingtask.SampleTracingPaging;
import com.todaysoft.lims.system.service.ISampleReceiveService;
import com.todaysoft.lims.system.service.adapter.request.OrderListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProjectListRequest;
import com.todaysoft.lims.system.util.Constant;

@Service
public class SampleReceiveService extends RestService implements ISampleReceiveService{
	
    private static Logger log = LoggerFactory.getLogger(SampleReceiveService.class);
    
    @Override
    public Pagination<SampleReceiveRecord> paging(SampleReceiveRecordSearcher searcher, int pageNo, int pageSize) {
    	
    	SampleReceiveRecordSearcher request = new SampleReceiveRecordSearcher();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/sampleReceive/paging");
        ResponseEntity<Pagination<SampleReceiveRecord>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveRecordSearcher>(request), new ParameterizedTypeReference<Pagination<SampleReceiveRecord>>()
            {
            });

        return exchange.getBody();
    }
    
    

	@Override
	public Pagination<SampleReceiveRecord> getSampleReceiveDetail() {
		
		Pagination<SampleReceiveRecord> pagination = new Pagination<>();
        pagination.setPageNo(1);
        pagination.setPageSize(10);
        pagination.setTotalCount(16);
		
		return pagination;
	}

	@Override
	public SampleReceiveRecord getSampleReceiveRecord(Integer id) {
		
		String url = GatewayService.getServiceUrl("/sampleReceive/{id}");
		return template.getForObject(url, SampleReceiveRecord.class,Collections.singletonMap("id", id));
	}

	@Override
	public SampleReceiveDetail getSampleReceiveDetail(Integer id) {
		
		String url = GatewayService.getServiceUrl("/sampleReceive/sampleReceiveDetail/{id}");
		return template.getForObject(url, SampleReceiveDetail.class,Collections.singletonMap("id", id));

	}

	
	/**
	 * 添加样本接收明细
	 */
	@Override
	public Integer createSampleReceiveDetail(SampleReceiveDetail request) {
		String url = GatewayService.getServiceUrl("/sampleReceive/createSampleReceiveDetal");
		return template.postForObject(url, request, Integer.class);
		
	}
	
	/**
	 * 添加样本接收明细
	 */
	@Override
	public Integer createSampleReceiveDetailUpdate(SampleReceiveDetail request) {
		String url = GatewayService.getServiceUrl("/sampleReceive/createSampleReceiveDetalUpdate");
		return template.postForObject(url, request, Integer.class);
		
	}

	/**
	 * 添加额外订单
	 */
	@Override
	public Integer createSampleReceiveOrder(SampleReceiveOrder request) {
		String url = GatewayService.getServiceUrl("/sampleReceive/createOrder");
		return template.postForObject(url, request, Integer.class);

	}

	@Override
	public void modifySampleReceiveDetail(SampleReceiveDetail data) {
		
		System.out.println(data);
	}

	/*@Override
	public SampleReceiveOrder getSampleReceiveOrder(Integer id) {
		return null;
	}*/

	/**
	 * 删除样本接收记录
	 */
	@Override
	public void deleteSampleRreceiveRecord(Integer id) {
		String url = GatewayService.getServiceUrl("/sampleReceive/{id}");
		template.delete(url, Collections.singletonMap("id", id));
	}
	/**
	 * 删除样本接收明细
	 */
	@Override
	public Integer deleteSampleReceiveDetail(Integer id) {
		
		String url = GatewayService.getServiceUrl("/sampleReceive/deleteDetail/{id}");
		template.delete(url, Collections.singletonMap("id", id));
		return id;
	}

/*	@Override
	public SampleReceiveRecord getSampleReceiveRecord(Integer id) {
		
		System.out.println(id);
		return new SampleReceiveRecord();
	}*/

	@Override
	public List<Project> getProjectList(ProjectListRequest request) {

		String url=GatewayService.getServiceUrl("/project/list");
		ResponseEntity<List<Project>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<ProjectListRequest>(request), new ParameterizedTypeReference<List<Project>>()
	            {
	            });
		return exchange.getBody();
	}

	/**
	 * 关联样本订单combox下拉框-所有未关联样本接收订单
	 */
	@Override
	public List<SampleReceiveOrder> getOrderList(OrderListRequest request) {
		String url=GatewayService.getServiceUrl("/sampleReceive/orderlist");
		ResponseEntity<List<SampleReceiveOrder>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request), new ParameterizedTypeReference<List<SampleReceiveOrder>>()
	            {
	            });
		return exchange.getBody();
	}

	
	
	
	@Override
	public List<SampleReceiveDetail> getDetailList(SampleReceiveDetail request) {
		
		String url=GatewayService.getServiceUrl("/sampleReceive/detailList");
		ResponseEntity<List<SampleReceiveDetail>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveDetail>(request), new ParameterizedTypeReference<List<SampleReceiveDetail>>()
	            {
	            });
		return exchange.getBody();
	}

	
	
	//增加样品接收记录  first
	//2016年7月15日11:21:03
	@Override
	public Integer createReceiveRecord(SampleReceiveRecordFormRequest data) {
		String url = GatewayService.getServiceUrl("/sampleReceive/createReceiveRecord");
		return template.postForObject(url, data, Integer.class);
	}

	/**
	 * 订单管理的---分页查询
	 */
	@Override
	public Pagination<SampleReceiveOrder> paging(OrderListRequest request,
			int pageNo, int pageSize) {
		
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/sampleReceive/receiveOrderPaging");
        ResponseEntity<Pagination<SampleReceiveOrder>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceiveOrder>>()
            {
            });

        return exchange.getBody();
	}

	@Override
	public SampleReceiveOrder getOrder(String id) {
		String url = GatewayService.getServiceUrl("/sampleReceive/getOrderById/{id}");
		return template.getForObject(url, SampleReceiveOrder.class,Collections.singletonMap("id", id));
		
	}

	@Override
	public List<Product> getProductList(ProductListRequest request) {
		
		String url=GatewayService.getServiceUrl("/product/list");
		ResponseEntity<List<Product>> exchange =
	        template.exchange(url, HttpMethod.POST, new HttpEntity<ProductListRequest>(request), new ParameterizedTypeReference<List<Product>>()
	       {
	     });
		return exchange.getBody();
	}

	@Override
	public Project getProjectbyId(String id) {
		String url = GatewayService.getServiceUrl("/project/{id}");
		return template.getForObject(url, Project.class,Collections.singletonMap("id", id));
	}


	
	 public List<SampleReceiveDetail> getDetailByRecordId(SampleReceiveRecord request){
		
		String url = GatewayService.getServiceUrl("/sampleReceive/getRelationByRecordId");

		ResponseEntity<List<SampleReceiveDetail>> exchange =
	            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveRecord>(request), new ParameterizedTypeReference<List<SampleReceiveDetail>>()
	            {
	            });
		return exchange.getBody();
	}
	
	
    /**
	 * 查询所有未启动的主样本明细
	 */

	@Override
	public Pagination<SampleReceiveDetail> sampleDetailPaging(
			SampleReceiveDetailPagingRequest request, int pageNo, int pageSize) {
		
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        //request.setState("1"); //默认查询未启动
        request.setSampleIdentification("2"); //zhuyangben
        String url = GatewayService.getServiceUrl("/sampleReceive/sampleDetailPaging");
        ResponseEntity<Pagination<SampleReceiveDetail>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<SampleReceiveDetailPagingRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceiveDetail>>()
        {
         });

        return exchange.getBody();
	}

	@Override
	public Boolean start(TestingScheduleStartRequest request) {
		String url = GatewayService.getServiceUrl("/testing/start");
		return template.postForObject(url, request, Boolean.class);
	}



	


	@Override
	public List<SampleReceiveDetail> doSome(String productId, Integer id) {
		
		/*List<SampleReceiveDetail> list = new  ArrayList<SampleReceiveDetail>();
		if(productId != null && id != null){
			if(productId.indexOf(",") > 0 && productId.indexOf(",")!=productId.length()){
				String[] ids = productId.split(",");
				for (String string : ids) {
					SampleReceiveDetail detail = getSampleReceiveDetail(id);
					detail.setProductId(productId);
					detail.setProductId(string);
					list.add(detail);
				}
			}else{
				SampleReceiveDetail newDetail = getSampleReceiveDetail(id);
				newDetail.setProductId(productId);
				list.add(newDetail);
			}
		}
		return list;*/
		return null;
	}



	@Override
	public Boolean redoSampleDetail(String data) {
		String url = GatewayService.getServiceUrl("/sampleReceive/redoSampleDetail");
		return template.postForObject(url,data,Boolean.class);
	}



	
	
	@Override
	public List<SampleTracing> sampleTraceList(SampleTracingPaging request) {
        String url = GatewayService.getServiceUrl("/testingTask/sampleTraceList");
        ResponseEntity<List<SampleTracing>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<SampleTracingPaging>(request), new ParameterizedTypeReference<List<SampleTracing>>()
        {
         });

        return exchange.getBody();
	}



	@Override
	public boolean checkedByName(String orderName) {
		String url = GatewayService.getServiceUrl("/sampleReceive/checkedOderName/{orderName}");
		return template.getForObject(url, boolean.class,Collections.singletonMap("orderName", orderName));
	}

	@Override
	public List<SampleTracing> getOriginalSamples(Integer sampleDetailId) {
		String url = GatewayService.getServiceUrl("/testingTask/sampleTracePaging/{sampleDetailId}");
		ResponseEntity<List<SampleTracing>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<Integer>(sampleDetailId), new ParameterizedTypeReference<List<SampleTracing>>(){
		}, Collections.singletonMap("sampleDetailId", sampleDetailId));
		return exchange.getBody();
	}



	@Override
	public SampleReceiveOrder getOrderById(Integer orderId) {
		String url = GatewayService.getServiceUrl("/sampleReceive/searchOrderById/{id}");
		return template.getForObject(url, SampleReceiveOrder.class,Collections.singletonMap("id", orderId));
	}



	@Override
	public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(Integer orderId, int pageNo, int pageSize) {
		OrderListRequest request = new OrderListRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setOrderId(orderId);
	    String url = GatewayService.getServiceUrl("/sampleReceive/searchSampleDetailByOrderId");
        ResponseEntity<Pagination<SampleReceiveDetail>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<OrderListRequest>(request), new ParameterizedTypeReference<Pagination<SampleReceiveDetail>>()
         {
         });

	    return exchange.getBody();
	}



	@Override
	public Integer commitCharge(CommitChargeRequest request) {
		String url = GatewayService.getServiceUrl("/sampleReceive/commitCharge");
		return template.postForObject(url, request, Integer.class);
	}
}
