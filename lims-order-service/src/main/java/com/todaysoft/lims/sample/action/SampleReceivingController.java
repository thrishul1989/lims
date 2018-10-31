package com.todaysoft.lims.sample.action;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleTransferringDetailSearcher;
import com.todaysoft.lims.sample.entity.StoreContainer;
import com.todaysoft.lims.sample.entity.order.AppSampleTransport;
import com.todaysoft.lims.sample.entity.order.OrderSampleView;
import com.todaysoft.lims.sample.entity.samplereceiving.ReceivedSample;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceiving;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoraging;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoragingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferring;
import com.todaysoft.lims.sample.model.SampleReceivingFormRequest;
import com.todaysoft.lims.sample.model.SampleStoragingFormRequest;
import com.todaysoft.lims.sample.model.SampleTransferFormRequest;
import com.todaysoft.lims.sample.model.order.OrderSearchRequest;
import com.todaysoft.lims.sample.model.request.SampleReceivePagingRequest;
import com.todaysoft.lims.sample.model.sampleReceiving.SampleStoragingSearcher;
import com.todaysoft.lims.sample.ons.IMessageProducer;
import com.todaysoft.lims.sample.service.ISampleReceivingService;
import com.todaysoft.lims.sample.service.storaging.ISampleStoragingService;
import com.todaysoft.lims.sample.util.Response;

@RestController
@RequestMapping("/bcm/sampleReceiving")
public class SampleReceivingController
{
    @Autowired
    private ISampleReceivingService service;
    
    @Autowired
    private ISampleStoragingService storagingService;
    
    @Autowired
    private IMessageProducer producer;
    
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    
    @RequestMapping(value = "/sampleReceivingPaging")
    public Pagination<SampleReceiving> sampleReceivingPaging(@RequestBody SampleReceivePagingRequest request)
    {
        return service.sampleReceivingPaging(request);
    }
    
    @RequestMapping(value = "/packageSampleList")
    public Pagination<AppSampleTransport> packageSampleList(@RequestBody SampleReceivePagingRequest request)
    {
        return service.packageSampleList(request);
    }
    
    @RequestMapping(value = "/sampleTransferringPaging")
    public Pagination<SampleTransferring> sampleTransferringPaging(@RequestBody SampleTransferringDetailSearcher request)
    {
        return service.sampleTransferringPaging(request);
    }
    
    @RequestMapping(value = "/sampleStoragePaging")
    public Pagination<SampleStoraging> sampleStoragePaging(@RequestBody SampleStoragingSearcher request)
    {
        return service.sampleStoragePaging(request);
    }
    
    @RequestMapping(value = "/create")
    public void create(@RequestBody SampleReceivingFormRequest request)
    {
        service.create(request);
        producer.sendSampleReceiveMessage(request.getOrderId());
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                storagingService.received(request);
            }
        }).start();
    }
    
    @RequestMapping(value = "/update")
    public void update(@RequestBody SampleReceivingFormRequest request)
    {
        service.update(request);
    }
    
    /**
     * 根据id查询样本收样对象
     * 
     * @param
     */
    @RequestMapping(value = "/getSampleReceivingById/{id}", method = RequestMethod.GET)
    public SampleReceiving getSampleReceivingById(@PathVariable String id)
    {
        return service.getSampleReceivingById(id);
    }
    
    @RequestMapping(value = "/getSampleTransferringById/{id}", method = RequestMethod.GET)
    public SampleTransferring getSampleTransferringById(@PathVariable String id)
    {
        return service.getSampleTransferringById(id);
    }
    
    /**
     * 根据订单和样本code查询数据
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSampleByView")
    private List<OrderSampleView> getSampleByView(@RequestBody OrderSearchRequest request)
    {
        return service.getSampleByView(request);
    }
    
    /**
     * 在已收样库中获取 --收样
     * orderId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReceivedSampleByCode")
    private List<ReceivedSample> getReceivedSampleByCode(@RequestBody OrderSearchRequest request)
    {
        return service.getReceivedSampleByCode(request);
    }
    
    @RequestMapping(value = "getHasReceivedSample")
    private Response<ReceivedSample> getHasReceivedSample(@RequestBody OrderSearchRequest request)
    {
        return service.getHasReceivedSample(request);
    }
    
    @RequestMapping(value = "getReceivedSampleNameByCode")
    private Map<String, Object> getReceivedSampleNameByCode(@RequestBody OrderSearchRequest request)
    {
        return service.getReceivedSampleNameByCode(request);
    }
    
    @RequestMapping(value = "/createTransfer")
    public void createTransfer(@RequestBody SampleTransferFormRequest request)
    {
        service.createTransfer(request);
    }
    
    /**
     * 根据订单和样本code查询数据
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTransferSample")
    private Response<ReceivedSample> getTransferSample(@RequestBody OrderSearchRequest request)
    {
        Response<ReceivedSample> response = service.getTransferSample(request);
        
        // 收样成功，通知流程处理中心
        if (response.isSuccess())
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    storagingService.storaged(request.getSampleCode(), request.getStoreType());
                }
            }).start();
        }
        return response;
    }
    
    @RequestMapping(value = "/getHasInboundSample")
    private List<ReceivedSample> getHasInboundSample(@RequestBody OrderSearchRequest request)
    {
        return service.getHasInboundSample(request);
    }
    
    @RequestMapping(value = "/createStoraging")
    public void createStoraging(@RequestBody SampleStoragingFormRequest request)
    {
        service.createStoraging(request);
    }
    
    /**
     * 根据收样查询明细
     */
    @RequestMapping("/sampleDetail")
    public Pagination<SampleReceivingDetail> sampleDetail(@RequestBody SampleReceivePagingRequest request)
    {
        Pagination<SampleReceivingDetail> pagination = service.sampleDetail(request);
        return pagination;
    }
    
    /**
     * 根据收样查询明细
     */
    @RequestMapping("/storageDetail")
    public Pagination<SampleStoragingDetail> storageDetail(@RequestBody SampleStoragingSearcher request)
    {
        Pagination<SampleStoragingDetail> pagination = service.storageDetail(request);
        return pagination;
    }
    
    /**
     * 自动分配样本存储位置
     */
    @RequestMapping(value = "/storageDetail", method = RequestMethod.GET)
    public String searchFreeLocation(String typeId, String storageType)
    {
        return service.searchFreeLocation(typeId, storageType);
    }
    
    /**
     * 查询最符合条件的容器
     * @param typeId
     * @param storageType
     * @return
     */
    @RequestMapping(value = "/getBestDevice", method = RequestMethod.GET)
    private StoreContainer getBestDevice(String typeId, String storageType)
    {
        return service.getBestDevice(typeId, storageType);
    }
    
    @RequestMapping(value = "/updateLocationState/{code}")
    private String updateLocationState(@PathVariable String code)
    {
        return service.updateLocationState(code);
    }
    
    @RequestMapping(value = "/getSampleDetailByCode")
    private List<SampleReceivingDetail> getSampleDetailByCode(@RequestBody SampleReceivePagingRequest request)
    {
        return service.getSampleDetailByCode(request);
    }
    
}
