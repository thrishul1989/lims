package com.todaysoft.lims.report.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.adapter.smm.SMMAdapter;
import com.todaysoft.lims.report.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.report.config.Configs;
import com.todaysoft.lims.report.entity.ContractContent;
import com.todaysoft.lims.report.entity.Customer;
import com.todaysoft.lims.report.entity.Order;
import com.todaysoft.lims.report.entity.Product;
import com.todaysoft.lims.report.entity.ReceivedSample;
import com.todaysoft.lims.report.entity.Sequence;
import com.todaysoft.lims.report.entity.TestingConcludingReport;
import com.todaysoft.lims.report.entity.TestingDataSend;
import com.todaysoft.lims.report.entity.TestingMethod;
import com.todaysoft.lims.report.entity.TestingSample;
import com.todaysoft.lims.report.entity.TestingSchedule;
import com.todaysoft.lims.report.model.ConcludingReportDetailModel;
import com.todaysoft.lims.report.model.ConcludingReportFormModel;
import com.todaysoft.lims.report.model.ConcludingReportModel;
import com.todaysoft.lims.report.model.ConcludingReportSearcher;
import com.todaysoft.lims.report.model.ConcludingReportUploadModel;
import com.todaysoft.lims.report.model.TestingStartRecord;
import com.todaysoft.lims.report.service.IConcludingReportService;
import com.todaysoft.lims.report.service.ITestingDataSendService;
import com.todaysoft.lims.report.service.core.event.ConcludingReportEvent;
import com.todaysoft.lims.report.service.core.event.ReportEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ConcludingReportService implements IConcludingReportService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private ITestingDataSendService testingDataSendService;
    
    @Override
    @Transactional
    public void createConcludingReport(String orderId)
    {
        List<TestingConcludingReport> lists =
            baseDaoSupport.find(TestingConcludingReport.class, "from TestingConcludingReport t where t.orderId='" + orderId + "'");
        if (Collections3.isEmpty(lists))
        {
            List<TestingSchedule> schedules = baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where t.orderId='" + orderId + "'");
            TestingSchedule schedule = Collections3.getFirst(schedules);
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            String orderCode = order.getCode();
            String examineeName = "";
            if (null != order.getOrderExamineeList() && Collections3.isNotEmpty(order.getOrderExamineeList()))
            {
                examineeName = Collections3.getFirst(order.getOrderExamineeList()).getName();
            }
            String contractCode = null == order.getContract() ? "" : order.getContract().getCode();
            String contractName = null == order.getContract() ? "" : order.getContract().getName();
            Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
            String customerName = null == customer ? "" : customer.getRealName();
            String customerCompany = "";
            if (null != customer && null != customer.getCompany())
            {
                customerCompany = customer.getCompany().getName();
            }
            String createName = order.getCreatorName();
            String deliveryMode = "";
            String deliveryResult = "";
            String testingPeriod = "";
            if (null != order.getContract())
            {
                List<ContractContent> contractContents =
                    baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order.getContract().getId() + "'");
                if (Collections3.isNotEmpty(contractContents))
                {
                    deliveryMode = Collections3.getFirst(contractContents).getDeliveryMode();
                    deliveryResult = Collections3.getFirst(contractContents).getDeliveryResult();
                    testingPeriod = Collections3.getFirst(contractContents).getTestingPeriod();
                }
            }
            Date createTime = order.getCreateTime();
            
            for (TestingSchedule sch : schedules)
            {
                TestingConcludingReport model = new TestingConcludingReport();
                model.setOrderId(sch.getOrderId());
                model.setProductId(sch.getProductId());
                Product product = baseDaoSupport.get(Product.class, sch.getProductId());
                model.setMethodId(sch.getMethodId());
                model.setProductCode(product.getCode());
                TestingSample testingSample = baseDaoSupport.get(TestingSample.class, sch.getSampleId());
                model.setSampleId(sch.getSampleId());
                if (null != testingSample && null != testingSample.getReceivedSample())
                {
                    model.setSampleCode(testingSample.getReceivedSample().getSampleCode());
                    model.setSampleName(testingSample.getReceivedSample().getSampleName());
                }
                model.setOrderCode(orderCode);
                model.setExamineeName(examineeName);
                model.setContractCode(contractCode);
                model.setContractName(contractName);
                model.setCustomerName(customerName);
                model.setCustomerCompany(customerCompany);
                model.setCreateName(createName);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(createTime);
                calendar.add(calendar.DATE, Integer.parseInt(StringUtils.isEmpty(testingPeriod) ? "0" : testingPeriod));
                model.setReportDate(calendar.getTime());
                model.setDeliveryMode(deliveryMode);
                model.setDeliveryResult(deliveryResult);
                model.setCreateTime(createTime);
                model.setStatu(0);
                baseDaoSupport.insert(model);
            }
        }
        
    }
    
    @Override
    public Pagination<ConcludingReportModel> paging(ConcludingReportSearcher request)
    {
        try
        {
            
            List<TestingConcludingReport> paging = baseDaoSupport.find(request.toQuery(), TestingConcludingReport.class);
            
            Pagination<ConcludingReportModel> res = new Pagination<>();
            res.setTotalCount(paging.size());
            res.setPageSize(request.getPageSize());
            res.setPageNo(request.getPageNo());
            
            List<ConcludingReportModel> list = new ArrayList<ConcludingReportModel>();
            for (TestingConcludingReport bean : paging)
            {
                
                ConcludingReportModel model = new ConcludingReportModel();
                BeanUtils.copyProperties(bean, model);
                
                //改订单是否上传过报告
                List<TestingConcludingReport> reports =
                    baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                        "from TestingConcludingReport t where t.orderId=:orderId and  t.statu = 1",
                        new String[] {"orderId"},
                        new Object[] {bean.getOrderId()});
                if (Collections3.isNotEmpty(reports))
                {
                    model.setIfReported(1);
                }
                else
                {
                    model.setIfReported(0);
                }
                
                //查询检测进度
                List<TestingSchedule> schedules =
                    baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where t.orderId='" + model.getOrderId()
                        + "' and t.proccessState!=2 and t.verifyTarget is null");
                int complete = 0;
                for (TestingSchedule schedule : schedules)
                {
                    if (StringUtils.isNotEmpty(schedule.getEndType()))
                    {
                        complete++;
                    }
                }
                model.setComplete(complete + "/" + schedules.size());
                //设置加急
                Order order = baseDaoSupport.get(Order.class, bean.getOrderId());
                if (null != order)
                {
                    if (null == order.getIfUrgent())
                    {
                        model.setIfUrgent(0);
                    }
                    else
                    {
                        model.setIfUrgent(order.getIfUrgent());
                    }
                    model.setUrgentName(order.getUrgentName());
                    model.setUrgentUpdateTime(order.getUrgentUpdateTime());
                }
                list.add(model);
                
            }
            if (request.getStatu() == 0)
            {
                //按照加急降序、完成百分比排序
                list.sort(new Comparator<ConcludingReportModel>()
                {
                    
                    @Override
                    public int compare(ConcludingReportModel o1, ConcludingReportModel o2)
                    {
                        if (o1.getIfUrgent() > o2.getIfUrgent())
                        {
                            return -1;
                        }
                        else if (o1.getIfUrgent() < o2.getIfUrgent())
                        {
                            return 1;
                        }
                        else
                        {
                            double a =
                                Double.parseDouble(o1.getComplete().split("\\/")[0])
                                    / (0 == Double.parseDouble(o1.getComplete().split("\\/")[1]) ? 1 : Double.parseDouble(o1.getComplete().split("\\/")[1]));
                            double b =
                                Double.parseDouble(o2.getComplete().split("\\/")[0])
                                    / (0 == Double.parseDouble(o2.getComplete().split("\\/")[1]) ? 1 : Double.parseDouble(o2.getComplete().split("\\/")[1]));
                            if (a < b)
                            {
                                
                                return 1;
                            }
                            else if (a > b)
                            {
                                return -1;
                            }
                            return o1.getCreateTime().compareTo(o2.getCreateTime());
                        }
                    }
                });
            }
            
            List<ConcludingReportModel> ll =
                list.subList(request.getPageNo() * 10 - 10, request.getPageNo() * 10 > paging.size() ? paging.size() : request.getPageNo() * 10);
            
            res.setRecords(ll);
            
            return res;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    @Override
    @Transactional
    public ConcludingReportFormModel getDetail(String orderId)
    {
        ConcludingReportFormModel res = new ConcludingReportFormModel();
        List<ConcludingReportDetailModel> doList = new ArrayList<ConcludingReportDetailModel>();
        List<ConcludingReportDetailModel> unDoList = new ArrayList<ConcludingReportDetailModel>();
        List<ConcludingReportDetailModel> reportList = new ArrayList<ConcludingReportDetailModel>();
        ConcludingReportDetailModel model = null;
        //获取已经出报告列表
        List<TestingConcludingReport> reports =
            baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                "from TestingConcludingReport t where t.orderId=:id and t.statu = 1 group by t.concludingReportCode",
                new String[] {"id"},
                new Object[] {orderId});
        for (TestingConcludingReport report : reports)
        {
            model = new ConcludingReportDetailModel();
            model.setConcludingReportName(report.getConcludingReportName());
            model.setConcludingReportCode(report.getConcludingReportCode());
            model.setConcludingReportPath(report.getConcludingReportUrl());
            model.setUpdateTime(report.getUpdateTime());
            model.setUpdateName(report.getUpdateName());
            reportList.add(model);
        }
        //获取待出出报告检测样本列表
        List<TestingConcludingReport> unDos =
            baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                "from TestingConcludingReport t where t.orderId=:id and t.statu = 0 ",
                new String[] {"id"},
                new Object[] {orderId});
        for (TestingConcludingReport unDo : unDos)
        {
            //过滤已被取消的流程
            List<TestingSchedule> schedules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                    "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=:sampleId",
                    new String[] {"orderId", "productId", "methodId", "sampleId"},
                    new Object[] {unDo.getOrderId(), unDo.getProductId(), unDo.getMethodId(), unDo.getSampleId()});
            if (Collections3.isNotEmpty(schedules) && !"0".equals(Collections3.getFirst(schedules).getEndType()))
            {
                unDoList.add(wrap(unDo));
            }
            else
            {
                unDo.setStatu(3);
                baseDaoSupport.update(unDo);
            }
            
        }
        
        //获取已经出报告检测样本列表
        List<TestingConcludingReport> dos =
            baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                "from TestingConcludingReport t where t.orderId=:id and t.statu = 1 ",
                new String[] {"id"},
                new Object[] {orderId});
        for (TestingConcludingReport done : dos)
        {
            doList.add(wrap(done));
        }
        
        res.setUnDoList(unDoList);
        res.setDoList(doList);
        res.setReportList(reportList);
        return res;
    }
    
    public ConcludingReportDetailModel wrap(TestingConcludingReport bean)
    {
        ConcludingReportDetailModel model = new ConcludingReportDetailModel();
        model.setId(bean.getId());
        model.setMethodId(bean.getMethodId());
        TestingMethod method = baseDaoSupport.get(TestingMethod.class, bean.getMethodId());
        model.setMethodName(method.getName());
        model.setSampleCode(bean.getSampleCode());
        model.setConcludingReportCode(bean.getConcludingReportCode());
        model.setConcludingReportName(bean.getConcludingReportName());
        Product product = baseDaoSupport.get(Product.class, bean.getProductId());
        if (null != product)
        {
            model.setProductName(product.getName());
        }
        model.setSampleName(bean.getSampleName());
        model.setUpdateName(bean.getUpdateName());
        model.setUpdateTime(bean.getUpdateTime());
        model.setStatu(bean.getStatu());
        List<TestingSchedule> schedules =
            baseDaoSupport.find(TestingSchedule.class,
                "from TestingSchedule t where " + "t.orderId='" + bean.getOrderId() + "' and t.productId='" + bean.getProductId() + "' and " + "t.methodId='"
                    + bean.getMethodId() + "' and t.sampleId='" + bean.getSampleId() + "'");
        if (Collections3.isNotEmpty(schedules))
        {
            model.setActiveTask(Collections3.getFirst(schedules).getActiveTask());
            String lanCode = testingDataSendService.getSequenceCode(Collections3.getFirst(schedules).getId());
            model.setLanCode(lanCode);
        }
        return model;
    }
    
    public String getReportCodeByName()
    {
        String todayStr = DateUtils.getDate("yyyy");
        String hql = "FROM Sequence s WHERE s.name=:name ";
        List<Sequence> list = baseDaoSupport.findByNamedParam(Sequence.class, hql, new String[] {"name"}, new String[] {"CONCLUDING_REPORT_SEQ"});
        Sequence sequence = Collections3.getFirst(list);
        Long current = sequence.getCurrentValue();
        String result = "";
        if (!todayStr.equals(DateUtils.formatDate(sequence.getBatchDate(), "yyyy")))//年份变化 重置
        {
            sequence.setCurrentValue(1L);
            sequence.setBatchDate(new Date());
            result = todayStr.substring(2, 4) + "RT" + String.format("%07d", 1);
            
        }
        else
        {
            sequence.setCurrentValue(sequence.getCurrentValue() + sequence.getIncrement());
            result = todayStr.substring(2, 4) + "RT" + String.format("%07d", current);
        }
        baseDaoSupport.update(sequence);
        return result;
        
    }
    
    @Override
    @Transactional
    public List<ConcludingReportModel> upload(ConcludingReportUploadModel request, String token)
    {
        List<ConcludingReportModel> res = new ArrayList<>();
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        String concludingReportCode = getReportCodeByName();
        for (String id : request.getConcludingReportIds().split("\\,"))
        {
            TestingConcludingReport bean = baseDaoSupport.get(TestingConcludingReport.class, id);
            bean.setConcludingReportCode(concludingReportCode);
            bean.setConcludingReportName(request.getFileName());
            bean.setConcludingReportUrl(request.getFilePath());
            bean.setUpdateName(loginer.getName());
            bean.setUpdateTime(new Date());
            bean.setStatu(1);
            baseDaoSupport.update(bean);
            //发送产品完成状态消息
            ConcludingReportModel model = new ConcludingReportModel();
            model.setOrderId(bean.getOrderId());
            model.setProductId(bean.getProductId());
            res.add(model);
            
        }
        
        return res;
    }
    
    @Override
    @Transactional
    public void deleteReport(String fileCode)
    {
        baseDaoSupport.execute("update TestingConcludingReport t set t.concludingReportUrl=null,t.concludingReportCode=null,"
            + "t.concludingReportName=null,t.statu=0 where t.concludingReportCode='" + fileCode + "'");
        
    }
    
    @Override
    public void reportCallBackStatus(String orderId, String productId, String tag)
    {
        ReportEvent event = new ReportEvent();
        event.setOrderId(orderId);
        event.setProductId(productId);
        event.setTag(tag);
        Message msg = new Message(configs.getAliyunONSTopic(), "REPORT_COMPLETE", new Gson().toJson(event).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    @Transactional
    public void extraSendReport(ConcludingReportEvent event)
    {
        for (TestingStartRecord record : event.getStartRecors())
        {
            List<TestingConcludingReport> concludingRepots =
                baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                    "from TestingConcludingReport t where t.orderId=:orderId",
                    new String[] {"orderId"},
                    new Object[] {record.getOrder().getId()});
            if (Collections3.isNotEmpty(concludingRepots))
            {
                TestingConcludingReport report = new TestingConcludingReport();
                BeanUtils.copyProperties(Collections3.getFirst(concludingRepots), report);
                report.setId(null);
                report.setUpdateName(null);
                report.setUpdateTime(null);
                report.setStatu(0);
                report.setProductId(record.getProduct().getId());
                report.setMethodId(record.getMethod().getId());
                
                ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, record.getSample().getId());
                if (null != receivedSample)
                {
                    List<TestingSample> testingSamples =
                        baseDaoSupport.find(TestingSample.class, "from TestingSample t where t.receivedSample.id='" + receivedSample.getSampleId()
                            + "' and t.parentSample.id is null");
                    if (Collections3.isNotEmpty(testingSamples))
                    {
                        report.setSampleId(Collections3.getFirst(testingSamples).getId());
                    }
                    
                    report.setSampleName(receivedSample.getSampleName());
                    report.setSampleCode(receivedSample.getSampleCode());
                }
                else
                {
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, record.getSample().getId());
                    if (null != testingSample)
                    {
                        report.setSampleName(testingSample.getReceivedSample().getSampleName());
                        report.setSampleId(testingSample.getId());
                        report.setSampleCode(testingSample.getSampleCode());
                    }
                }
                baseDaoSupport.insert(report);
            }
            
            List<TestingDataSend> dataSends =
                baseDaoSupport.findByNamedParam(TestingDataSend.class,
                    "from TestingDataSend t where t.orderId=:orderId",
                    new String[] {"orderId"},
                    new Object[] {record.getOrder().getId()});
            if (Collections3.isNotEmpty(dataSends))
            {
                TestingDataSend dataSend = new TestingDataSend();
                BeanUtils.copyProperties(Collections3.getFirst(dataSends), dataSend);
                dataSend.setId(null);
                dataSend.setSendName(null);
                dataSend.setSendTime(null);
                dataSend.setStatu(0);
                dataSend.setProductId(record.getProduct().getId());
                dataSend.setMethodId(record.getMethod().getId());
                dataSend.setSampleId(record.getSample().getId());
                ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, record.getSample().getId());
                if (null != receivedSample)
                {
                    List<TestingSample> testingSamples =
                        baseDaoSupport.find(TestingSample.class, "from TestingSample t where t.receivedSample.id='" + receivedSample.getSampleId()
                            + "' and t.parentSample.id is null");
                    if (Collections3.isNotEmpty(testingSamples))
                    {
                        dataSend.setSampleId(Collections3.getFirst(testingSamples).getId());
                    }
                    
                    dataSend.setSampleName(receivedSample.getSampleName());
                    dataSend.setSampleCode(receivedSample.getSampleCode());
                }
                else
                {
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, record.getSample().getId());
                    if (null != testingSample)
                    {
                        dataSend.setSampleName(testingSample.getReceivedSample().getSampleName());
                        dataSend.setSampleId(testingSample.getId());
                        dataSend.setSampleCode(testingSample.getSampleCode());
                    }
                }
                baseDaoSupport.insert(dataSend);
            }
            
        }
        
    }
}
