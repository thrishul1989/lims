package com.todaysoft.lims.sample.service;

import java.util.List;
import java.util.Map;

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
import com.todaysoft.lims.sample.util.Response;

public interface ISampleReceivingService
{
    Pagination<SampleReceiving> sampleReceivingPaging(SampleReceivePagingRequest request);
    
    Pagination<SampleTransferring> sampleTransferringPaging(SampleTransferringDetailSearcher request);
    
    void create(SampleReceivingFormRequest request);
    
    void update(SampleReceivingFormRequest request);
    
    SampleReceiving getSampleReceivingById(String id);
    
    List<OrderSampleView> getSampleByView(OrderSearchRequest request);
    
    void createTransfer(SampleTransferFormRequest request);
    
    Response<ReceivedSample> getTransferSample(OrderSearchRequest request);
    
    void createStoraging(SampleStoragingFormRequest request);
    
    Pagination<SampleStoraging> sampleStoragePaging(SampleStoragingSearcher request);
    
    Pagination<SampleReceivingDetail> sampleDetail(SampleReceivePagingRequest request);
    
    Pagination<SampleStoragingDetail> storageDetail(SampleStoragingSearcher request);
    
    List<ReceivedSample> getReceivedSampleByCode(OrderSearchRequest request);
    
    Response<ReceivedSample> getHasReceivedSample(OrderSearchRequest request);
    
    List<ReceivedSample> getHasInboundSample(OrderSearchRequest request);
    
    public String searchFreeLocation(String id, String storageType);
    
    List<SampleReceivingDetail> getSampleDetailByCode(SampleReceivePagingRequest request);
    
    SampleTransferring getSampleTransferringById(String id);
    
    //List<SampleStoragingDetail> getHasInboundSample(OrderSearchRequest request);
    
    void autoStartProcess(String id);
    
    StoreContainer getBestDevice(String sampleTypeId, String storgeType);
    
    String updateLocationState(String code);
    
    Map<String, Object> getReceivedSampleNameByCode(OrderSearchRequest request);
    
    Pagination<AppSampleTransport> packageSampleList(SampleReceivePagingRequest request);

    List<SampleStoragingDetail> getSampleStoragingDetailBySampleCode(String sampleCode);
    
}
