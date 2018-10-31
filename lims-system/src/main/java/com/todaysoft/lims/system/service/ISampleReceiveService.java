package com.todaysoft.lims.system.service;

import java.util.List;

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
import com.todaysoft.lims.system.service.adapter.request.OrderListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProjectListRequest;

public interface ISampleReceiveService
{
    Pagination<SampleReceiveRecord> paging(SampleReceiveRecordSearcher searcher, int pageNo, int pageSize);
    
    //获取sampleReceiveDetail
    Pagination<SampleReceiveRecord> getSampleReceiveDetail();
    
    //增加样本接收明细
     public Integer createSampleReceiveDetail(SampleReceiveDetail data);
    
     //修改样品接收明细
     public void modifySampleReceiveDetail(SampleReceiveDetail data);
     
    //增加样品订单
     public Integer createSampleReceiveOrder(SampleReceiveOrder data);
    
   //获取sampleServiceRecord
    public SampleReceiveRecord getSampleReceiveRecord(Integer id);
    
    //查看sampleServiceDetail
    public SampleReceiveDetail getSampleReceiveDetail(Integer id);
    
    	
    //删除sampleServiceRecord
    public void deleteSampleRreceiveRecord(Integer id);
    
    //删除sampleReceiveDetail
    public Integer deleteSampleReceiveDetail(Integer id);
    
    //查看关联项目list 
    public List<Project> getProjectList(ProjectListRequest request);
    
    //查看关联产品list
    public List<Product> getProductList(ProductListRequest request);
    
    //获取sampleReceiveorderList
    public List<SampleReceiveOrder> getOrderList(OrderListRequest request);

    //获取sampleReceiveDetail
	public List<SampleReceiveDetail> getDetailList(SampleReceiveDetail sr);
	
	//查看sampleServiceOrder
    public  SampleReceiveOrder getOrder(String id);
    
    //根据id获取关联项目
    public Project getProjectbyId(String id);
    
    //根据serviceId获取中间表对象
    public List<SampleReceiveDetail> getDetailByRecordId(SampleReceiveRecord request);
    
	Integer createReceiveRecord(SampleReceiveRecordFormRequest data);

	/**
	 * 获取订单列表
	 * @param searcher
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<SampleReceiveOrder> paging(OrderListRequest searcher,int pageNo, int pageSize);

	Pagination<SampleReceiveDetail> sampleDetailPaging(
			SampleReceiveDetailPagingRequest searcher, int pageNo, int i);

	Boolean start(TestingScheduleStartRequest request);

	List<SampleReceiveDetail> doSome(String productId ,Integer id);

	Boolean redoSampleDetail(String data);

	List<SampleTracing> sampleTraceList(SampleTracingPaging searcher);

	public boolean checkedByName(String orderName);
	
	//样本跟踪，获取原始样本数据
	List<SampleTracing> getOriginalSamples(Integer sampleDetailId);

	Integer createSampleReceiveDetailUpdate(SampleReceiveDetail data);

	SampleReceiveOrder getOrderById(Integer orderId);

	Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(Integer orderId,int pageNo, int i);

	Integer commitCharge(CommitChargeRequest request);
	
}
