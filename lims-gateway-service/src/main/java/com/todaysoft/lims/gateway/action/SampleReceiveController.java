package com.todaysoft.lims.gateway.action;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.todaysoft.lims.gateway.service.ISampleReceiveService;


@RestController
@RequestMapping("/sampleReceive")
public class SampleReceiveController{
    @Autowired
    private ISampleReceiveService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<SampleReceive> paging(@RequestBody SampleReceivePagingRequest request){
        return service.paging(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SampleReceive getSampleById(@PathVariable Integer id){
        return service.getSampleReceive(id);
    }
    
    @RequestMapping(value = "/create")
    public void create(@RequestBody SampleFormRequest request){
        service.create(request);
    }
    
    /**
     * 创建样本接收 关联订单
     * @param request
     */
    @RequestMapping(value = "/createOrder")
    public Integer createOrder(@RequestBody SampleReceiveOrder request){
        return service.createOrder(request);
    }
    
    /**
     * 添加样本明细
     * @param request
     */
    @RequestMapping("/createSampleReceiveDetal")
    public void createSampleReceiveDetal(@RequestBody SampleReceiveDetail request){
        service.createReceiveDetal(request);
    }
   
    /**
     * 添加样本明细
     * @param request
     */
    @RequestMapping("/createSampleReceiveDetalUpdate")
    public void createSampleReceiveDetalUpdate(@RequestBody SampleReceiveDetail request){
        service.createSampleReceiveDetalUpdate(request);
    }
    
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody SampleFormRequest request){
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
    
   /**
    * 关联combox下拉框内容--所有未关联样本接收订单
    * @param request
    * @return
    */
    @RequestMapping(value = "/orderlist",method = RequestMethod.POST)
	public List<SampleReceiveOrder> list(@RequestBody OrderListRequest request){
		return service.orderlist(request);
	}
  
    
    @RequestMapping(value = "/detailList",method = RequestMethod.POST)
    public List<SampleReceiveDetail> detaillist(@RequestBody SampleReceiveDetailRequest request){
		return service.detaillist(request);
	}
    
    
    @RequestMapping(value = "/deleteDetail/{id}", method = RequestMethod.DELETE)
    public void deletedetail(@PathVariable Integer id){
        service.deletedetail(id);
    }
        
    
    /**
     * 创建样本接收记录
     * @param request
     */
    @RequestMapping("/createReceiveRecord")
    public void createReceiveRecord(@RequestBody SampleFormRequest request){
        service.createReceiveRecord(request);
    }
    
    
    /**
     * 订单管理 --- 分页查询
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/receiveOrderPaging")
    public Pagination<SampleReceiveOrder> receiveOrderPaging(@RequestBody OrderListRequest request){
        return service.receiveOrderPaging(request);
    }
    
    
    @RequestMapping(value = "/getOrderById/{id}", method = RequestMethod.GET)
    public SampleReceiveOrder getOrderById(@PathVariable String id){
        return service.getOrderById(id);
    }

    
    
    @RequestMapping(value="/getRelationByRecordId")
    public List<SampleReceiveDetail> getRelationByRecordId(@RequestBody SampleFormRequest request){
    	return service.getRelation(request);
    }
    
    @RequestMapping(value="/sampleReceiveDetail/{id}")
    public 	SampleReceiveDetail getSampleReceiveDetail(@PathVariable Integer id) {
    	return service.getSampleReceiveDetail(id);
    }

    
    
    
    /**
     * 根据订单id查询样本明细
     * @param id
     * @return
     */
    @RequestMapping(value="/getDetailByOrderId/{id}",method = RequestMethod.GET)
    public List<SampleReceiveDetail> getDetailByOrderId(@PathVariable Integer id){
   	 return service.getDetailByOrderId(id);
    }
    
    
    @RequestMapping(value="/getDetailBySampleCode/{id}",method = RequestMethod.GET)
    public List<SampleReceiveDetail> getDetailBySampleCode(@PathVariable String id){
   	 return service.getDetailBySampleCode(id);
    }
    
    
    
    @RequestMapping(value = "/sampleDetailPaging")
    public Pagination<SampleReceiveDetail> sampleDetailPaging(@RequestBody SampleReceiveDetailPagingRequest request){
        return service.sampleDetailPaging(request);
    }
    
    @RequestMapping(value="/redoSampleDetail")
    public Boolean redoSampleDetail(@RequestBody String data){
    	return service.redoSampleDetail(data);
    	
    }
    
    @RequestMapping(value="/checkedOderName/{orderName}")
    public Boolean checkedOderName(@PathVariable String orderName){
    	return	service.checkedOderName(orderName);
    	
    }
    
    @RequestMapping(value = "/searchOrderById/{id}", method = RequestMethod.GET)
    public SampleReceiveOrder searchOrderById(@PathVariable Integer id){
        return service.searchOrderById(id);
    }
    
    @RequestMapping(value = "/searchSampleDetailByOrderId")
    public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(@RequestBody OrderListRequest request){
        return service.searchSampleDetailByOrderId(request);
    }
    
    
    @RequestMapping(value = "/commitCharge")
    public void commitCharge(@RequestBody CommitChargeRequest request){
        service.commitCharge(request);
    }
}
