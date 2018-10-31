package com.todaysoft.lims.system.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.AppSampleTransport;
import com.todaysoft.lims.system.model.vo.order.OrderSampleView;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceiving;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoraging;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoragingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferring;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.system.util.Response;

public interface ISampleReceivingService
{
    
    void update(SampleReceivingFormRequest request);
    
    void create(SampleReceivingFormRequest request);
    
    List<OrderSampleView> getSampleByView(OrderSearchRequest request);
    
    void createTransfer(SampleTransferFormRequest request);
    
    Response<ReceivedSample> getTransferSample(OrderSearchRequest request);
    
    void createStoraging(SampleStoragingFormRequest request);
    
    Pagination<ReceiveSampleOrderResponse> sampleReceivingPaging(SampleReceiveRecordSearcher searcher, int pageNo, int pageSize);
    
    Pagination<SampleTransferring> sampleTransferringPaging(SampleTransferringDetail searcher, int pageNo, int pageSize);
    
    Pagination<SampleStoraging> sampleStoragePaging(SampleStoraging searcher, int pageNo, int defaultpagesize);
    
    Pagination<SampleReceivingDetail> sampleReceivingDetail(SampleStoraging request, int pageNo, int defaultpagesize);
    
    Pagination<SampleReceivingDetail> sampleDetail(SampleReceiveRecordSearcher searcher, int pageNo, int defaultpagesize);
    
    Pagination<ReceivedSample> storageDetail(SampleStoraging searcher, int pageNo, int defaultpagesize);
    
    List<ReceivedSample> getReceivedSampleByCode(OrderSearchRequest request);
    
    Response<ReceivedSample> getHasReceivedSample(OrderSearchRequest request);
    
    List<ReceivedSample> getHasInboundSample(OrderSearchRequest request);
    
    List<SampleReceivingDetail> getSampleDetailByCode(SampleReceiveRecordSearcher search);
    
    SampleReceiving getSampleReceivingById(String recordId);
    
    SampleTransferring getSampleTransferringById(String id);
    
    String downloadCode(InputStream is, SampleTransferring list);
    
    Pagination<AppSampleTransport> packageSampleList(SampleReceiveRecordSearcher searcher, int pageNo, int defaultpagesize);
    
    List<OrderSampleView> seacherBasicSample(OrderSearchRequest request);
    
}
