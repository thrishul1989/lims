package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.SampleReceive;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.model.CommitChargeRequest;
import com.todaysoft.lims.sample.model.request.OrderListRequest;
import com.todaysoft.lims.sample.model.request.SampleFormRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveOrder;
import com.todaysoft.lims.sample.model.request.SampleReceivePagingRequest;

public interface ISampleReceiveService
{
    
    Pagination<SampleReceive> paging(SampleReceivePagingRequest request);
    
    SampleReceive getSampleReceive(Integer id);
    
    void create(SampleFormRequest request);
    
    void modify(SampleFormRequest request);
    
    void delete(Integer id);
    
    Integer createOrder(SampleReceiveOrder request);
    
    void createReceiveDetal(SampleReceiveDetailRequest request);
    
    List<SampleOrder> list(OrderListRequest request);
    
    List<SampleReceiveDetail> detailList(SampleReceiveDetailRequest request);
    
    void deleteDetail(Integer id);
    
    void createReceiveRecord(SampleFormRequest request);
    
    Pagination<SampleOrder> paging(OrderListRequest request);
    
    List<SampleOrder> getOrder(OrderListRequest request);
    
    SampleOrder getOrderById(String id);
    
    List<SampleReceiveDetail> getDetailByOrderId(Integer orderId);
    
    List<SampleReceiveDetail> getRelation(SampleFormRequest request);
    
    SampleReceiveDetail getSampleReceiveDetail(String id);
    
    Pagination<SampleReceiveDetail> sampleDetailPaging(SampleReceiveDetailPagingRequest request);
    
    Integer selectSampleSeqNextVal();
    
    Boolean redoSampleDetail(String isSave);
    
    Boolean checkedOderName(String isSave);
    
    void createReceiveDetalUpdate(SampleReceiveDetailRequest request);
    
    SampleOrder searchOrderById(Integer id);
    
    Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(OrderListRequest request);
    
    Integer commitCharge(CommitChargeRequest request);
    
}
