package com.todaysoft.lims.testing.reportreview.service.impl;

import java.beans.Beans;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingReportReview;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bcm.ProductSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;
import com.todaysoft.lims.testing.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.testing.reportreview.model.CompletedReportSearcher;
import com.todaysoft.lims.testing.reportreview.model.FirstReviewSearcher;
import com.todaysoft.lims.testing.reportreview.model.ReportReviewModel;
import com.todaysoft.lims.testing.reportreview.model.SecondReviewSearcher;
import com.todaysoft.lims.testing.reportreview.service.IReportReviewService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportReviewService implements IReportReviewService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private IExtraSampleService extraSampleService;
    
    @Autowired
    private SMMAdapter smmAdapter;
    
    @Override
    public Pagination<TestingReport> pagingFirst(FirstReviewSearcher searcher)
    {
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), TestingReport.class);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (TestingReport tr : pagination.getRecords())
            {
                wrap(tr);
            }
        }
        return pagination;
    }
    
    @Override
    public Pagination<TestingReport> pagingSecond(SecondReviewSearcher searcher)
    {
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), TestingReport.class);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (TestingReport tr : pagination.getRecords())
            {
                wrap(tr);
            }
        }
        return pagination;
    }
    
    @Override
    public Pagination<TestingReport> pagingCompletedReport(CompletedReportSearcher searcher)
    {
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), TestingReport.class);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (TestingReport tr : pagination.getRecords())
            {
                wrap(tr);
            }
        }
        return pagination;
    }
    
    @Override
    @Transactional
    public Map<String,String> firstReview(TestingReportReview request)
    {
        createReview(request);
        TestingReport report = getReport(request.getReport().getId());
        report.setFirstReviewStatus(request.getReviewResult());
        report.setReviewerName(request.getReviewerName());
        
        OrderProduct op = getOp(report.getOrder().getId(), report.getProduct().getId());
        if (TestingReportReview.RESULT_YES.equals(request.getReviewResult()))
        {
            report.setSecondReviewStatus(TestingReportReview.RESULT_TODO);
            if (null != op)
            {
                op.setReportStatus(OrderProduct.REPORT_STATUS_TODO_SECOND_REVIEW);
            }
        }
        else
        {
            if (null != op)
            {
                op.setProductStatus(3);
                op.setReportStatus(OrderProduct.REPORT_STATUS_NO_REPORT);
            }
            
            report.setResubmit(true);
            report.setResubmitCount(report.getResubmitCount() == null ? 1 : report.getResubmitCount() + 1);
            //根据任务完成情况，把任务打回待整合或已出报告
            searchStatusByOrderAndProduct(report);
        }
        baseDaoSupport.merge(op);
        baseDaoSupport.merge(report);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", report.getOrder().getId());
        map.put("productId", report.getProduct().getId());
        return map;
    }
    
    @Override
    @Transactional
    public Map<String, String> secondReview(TestingReportReview request)
    {
        createReview(request);
        TestingReport report = getReport(request.getReport().getId());
        report.setSecondReviewStatus(request.getReviewResult());
        report.setReviewerName(request.getReviewerName());
        
        OrderProduct op = getOp(report.getOrder().getId(), report.getProduct().getId());
        if (TestingReportReview.RESULT_NO.equals(request.getReviewResult()))
        {
            //根据任务完成情况，把任务打回待整合或已出报告
            searchStatusByOrderAndProduct(report);
            
            if (null != op)
            {
                op.setProductStatus(3);
                op.setReportStatus(OrderProduct.REPORT_STATUS_NO_REPORT);
            }
            report.setResubmit(true);
            report.setResubmitCount(report.getResubmitCount() == null ? 1 : report.getResubmitCount() + 1);
        }
        else
        {
            if (null != op)
            {
                op.setProductStatus(5);
                op.setReportStatus(OrderProduct.REPORT_STATUS_TODO_SEND);
            }
        }
        baseDaoSupport.merge(op);
        baseDaoSupport.merge(report);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", report.getOrder().getId());
        map.put("productId", report.getProduct().getId());
        return map;
    }
    
    @Override
    @Transactional
    public void createReview(TestingReportReview request)
    {
        baseDaoSupport.insert(request);
    }
    
    @Override
    public TestingReport getReport(String id)
    {
        TestingReport report = baseDaoSupport.get(TestingReport.class, id);
        wrap(report);
        return report;
    }
    
    private void wrap(TestingReport report)
    {
        if (null != report.getProduct() && null != report.getProduct().getProductDuty())
        {
            report.setProductLeader(report.getProduct().getProductDuty().getArchive().getName());
        }
        
        if (null != report.getOrder())
        {
            report.setOrderCode(report.getOrder().getCode());
            
            List<OrderExaminee> orderExamineeList = report.getOrder().getOrderExamineeList();
            if (Collections3.isNotEmpty(orderExamineeList))
            {
                OrderExaminee examinee = Collections3.getFirst(orderExamineeList);
                report.setSampleName(examinee.getName());
            }
        }
        
        OrderProduct op = getOp(report.getOrder().getId(), report.getProduct().getId());
        if (null != op)
        {
            report.setReportDate(op.getReportTime());
            report.setDataUrl(op.getDataUrl());
            report.setCombineFileName(report.getReportName() + "/" + op.getDataUrl());
            if (StringUtils.isNotEmpty(report.getReportName()))
            {
                int index = report.getReportName().lastIndexOf(".");
                String fileType = report.getReportName().substring(index + 1);
                if ("pdf".equals(fileType))
                {
                    report.setPdfFile(true);
                }
                if("docx".equals(fileType))//ww
                {
                    report.setDocxFile(true);
                }
            }
            
            if (StringUtils.isNotEmpty(op.getReportStatus()))
            {
                report.setReportStatus(op.getReportStatus());
            }
            else
            {
                //新增reportStatus之前历史记录，根据productStatus判断当前报告状态
                if (4 == op.getProductStatus())
                {
                    if (TestingReportReview.RESULT_TODO.equals(report.getFirstReviewStatus()))
                    {
                        report.setReportStatus(OrderProduct.REPORT_STATUS_TODO_FIRST_REVIEW);
                    }
                    else if (TestingReportReview.RESULT_TODO.equals(report.getSecondReviewStatus()))
                    {
                        report.setReportStatus(OrderProduct.REPORT_STATUS_TODO_SECOND_REVIEW);
                    }
                }
                else if (5 == op.getProductStatus())
                {
                    report.setReportStatus(OrderProduct.REPORT_STATUS_TODO_SEND);
                }
                else if (6 == op.getProductStatus())
                {
                    report.setReportStatus(OrderProduct.REPORT_STATUS_SENDED);
                }
                else if (7 == op.getProductStatus())
                {
                    report.setReportStatus(OrderProduct.REPORT_STATUS_UNSEND);
                }
                else
                {
                    report.setReportStatus(OrderProduct.REPORT_STATUS_NO_REPORT);
                }
            }
            if (null != op.getProduct())
            {
                report.setProductName(op.getProduct().getName());
            }
        }
        
        ProductSimpleModel productModel = bcmadapter.getProduct(report.getProduct().getId());
        if (null != productModel)
        {
            report.setTechnicalMan(Collections3.convertToString(productModel.getTechnicalPrincipals(), ","));
        }
        
        Order order = baseDaoSupport.get(Order.class, report.getOrder().getId());
        if (null != order)
        {
            report.setMarketingCenter(order.getType().getId());
            report.setAnalType(order.getDoctorAssist());
        }
        //样本编号
        ReceivedSample primarySample = extraSampleService.getPrimarySampleByOrderId(report.getOrder().getId());
        if (null != primarySample)
        {
            TestingSample testingSample = extraSampleService.getTestingSampleBySampleCode(primarySample.getSampleCode());
            if (null != testingSample)
            {
                report.setSampleCode(testingSample.getSampleCode());
            }
            //应出报告日期
            BioSampleSimpleModel bssm = new BioSampleSimpleModel(report.getOrder().getId(), report.getProduct().getId(), primarySample.getSampleCode());
            OrderSimpleModel orderModel = bmmadapter.getOrder(bssm);
            if (null != orderModel.getSubmitTime() && null != productModel.getTestingDuration())
            {
                report.setShouldReportDate(DateUtils.addDays(orderModel.getSubmitTime(), productModel.getTestingDuration()));
            }
        }
        //前一步审核意见
        if (Collections3.isNotEmpty(report.getReviewList()))
        {
            report.setRemark(Collections3.getLast(report.getReviewList()).getReviewOpinion());
        }
    }
    
    private OrderProduct getOp(String orderId, String productId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderProduct op WHERE op.order.id = :orderId AND op.product.id = :productId")
                .names(Lists.newArrayList("orderId", "productId"))
                .values(Lists.newArrayList(orderId, productId))
                .build();
        List<OrderProduct> opList = baseDaoSupport.find(queryer, OrderProduct.class);
        if (Collections3.isNotEmpty(opList))
        {
            return Collections3.getFirst(opList);
        }
        return null;
    }
    
    private void searchStatusByOrderAndProduct(TestingReport report)
    {
        List<TestingSchedule> testingSchedules = getTestingScheduleByOrderIdAndProductId(report.getOrder().getId(), report.getProduct().getId());
        int completeNum = 0;
        if (Collections3.isEmpty(testingSchedules))
        {
            return;
        }
        int totalNum = testingSchedules.size();
        for (TestingSchedule record : testingSchedules)
        {
            if (null != record.getEndTime() || StringUtils.isNotEmpty(record.getEndType()))
            {
                completeNum++;
            }
        }
        
        if (totalNum != 0 && totalNum == completeNum)
        {
            //说明该产品的检测或验证都完成了
            report.setStatus(TestingReport.REPORT_STATUS_ABLE_REPORT);
        }
        else
        {
            //说明该产品的检测或验证未完成
            report.setStatus(TestingReport.REPORT_STATUS_WAIT_DATA);
        }
    }
    
    private List<TestingSchedule> getTestingScheduleByOrderIdAndProductId(String orderId, String productId)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId ORDER BY ISNULL(t.verifyKey) DESC,t.methodId";
        List<TestingSchedule> records =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId"}, new String[] {orderId, productId});
        return records;
    }

    @Override
    public List<ReportReviewModel> getReportReviewModelByReportId(String reportId)
    {
        List<ReportReviewModel> modelList = Lists.newArrayList();
        String hql = "FROM TestingReportReview t WHERE t.report.id = :reportId order by t.reviewTime asc";
        List<TestingReportReview> records =
            baseDaoSupport.findByNamedParam(TestingReportReview.class, hql, new String[] {"reportId"}, new String[] {reportId});
        if(Collections3.isNotEmpty(records))
        {
            for(TestingReportReview review : records)
            {
                ReportReviewModel model = new ReportReviewModel();
                BeanUtils.copyProperties(review, model);
                modelList.add(model);
            }
        }
        return modelList;
    }
    
}
