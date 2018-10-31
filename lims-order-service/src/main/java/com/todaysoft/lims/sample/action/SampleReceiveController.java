package com.todaysoft.lims.sample.action;


import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.SampleReceive;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceiving;
import com.todaysoft.lims.sample.model.CommitChargeRequest;
import com.todaysoft.lims.sample.model.request.OrderListRequest;
import com.todaysoft.lims.sample.model.request.SampleFormRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveOrder;
import com.todaysoft.lims.sample.model.request.SampleReceivePagingRequest;
import com.todaysoft.lims.sample.service.ISampleReceiveService;

@RestController
@RequestMapping("/sampleReceive")
public class SampleReceiveController implements ServletContextAware
{
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
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody SampleFormRequest request){
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
    
    /**
     * 创建样本订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/createOrder")
    public Integer createOrder(@RequestBody SampleReceiveOrder request){
        return service.createOrder(request);
    }
    /**
     * 创建样本明细
     * @param request
     */
    @RequestMapping("/createReceiveDetal")
    public void createReceiveDetal(@RequestBody SampleReceiveDetailRequest request ){
        service.createReceiveDetal(request);
    }
    
    /**
     * 创建样本明细 ---更新
     * @param request
     */
    @RequestMapping("/createReceiveDetalUpdate")
    public void createReceiveDetalUpdate(@RequestBody SampleReceiveDetailRequest request ){
        service.createReceiveDetalUpdate(request);
    }
    /**
     * 保存完刷新样本明细
     * @param request
     * @return
     */
    @RequestMapping(value="/detailList",method = RequestMethod.POST)
    public List<SampleReceiveDetail> detailList(@RequestBody SampleReceiveDetailRequest request){
   	 return service.detailList(request);
   }
    
    
    @RequestMapping(value = "/deleteDetail/{id}", method = RequestMethod.DELETE)
    public void deleteDetail(@PathVariable Integer id){
        service.deleteDetail(id);
    }
    
    /**
     * 创建样本接收记录　
     * @param request
     */
    @RequestMapping("/createReceiveRecord")
    public void createReceiveRecord(@RequestBody SampleFormRequest request){
    	 service.createReceiveRecord(request);
    }
    
    @RequestMapping(value="/getSampleReceiveDetail/{id}")
    public SampleReceiveDetail getSampleReceiveDetail(@PathVariable Integer id){
    	return service.getSampleReceiveDetail(String.valueOf(id));
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
    
    @RequestMapping(value="/getRelationByRecordId")
    public List<SampleReceiveDetail> getRelationByName(@RequestBody SampleFormRequest request){
    	return service.getRelation(request);
    }
    
    
    /**
     * 开始启动列表
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/sampleDetailPaging")
    public Pagination<SampleReceiveDetail> sampleDetailPaging(@RequestBody SampleReceiveDetailPagingRequest request){
        return service.sampleDetailPaging(request);
    }
    
    
    @RequestMapping(value = "/selectSampleSeqNextVal")
    public Integer selectSampleSeqNextVal(){
        return service.selectSampleSeqNextVal();
    }
    
    @RequestMapping(value = "/redoSampleDetail")
    public Boolean redoSampleDetail(@RequestBody String data){
        return service.redoSampleDetail(data);
    }
    
    @RequestMapping(value = "/checkedOderName/{orderName}")
    public Boolean checkedOderName(@PathVariable String orderName){
    	return service.checkedOderName(orderName);
    }
    
    /**
     * 根据id获取订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/searchOrderById/{id}", method = RequestMethod.GET)
    public SampleOrder searchOrderById(@PathVariable Integer id)
    {
        return service.searchOrderById(id);
    }

    @RequestMapping(value = "/searchSampleDetailByOrderId")
    public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(@RequestBody OrderListRequest request){
        return service.searchSampleDetailByOrderId(request);
    }
    
    
    
    @RequestMapping(value = "/commitCharge")
    public Integer commitCharge(@RequestBody CommitChargeRequest request){
        return service.commitCharge(request);
    }

	@Override
	public void setServletContext(ServletContext servletContext) {
		
	}
	
	
	
	
    
}
