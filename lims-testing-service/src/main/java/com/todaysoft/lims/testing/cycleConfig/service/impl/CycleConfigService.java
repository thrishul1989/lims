package com.todaysoft.lims.testing.cycleConfig.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.Customer;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SampleReceiving;
import com.todaysoft.lims.testing.base.entity.SampleReceivingDetail;
import com.todaysoft.lims.testing.base.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.testing.base.entity.ScheduleTestingConfig;
import com.todaysoft.lims.testing.base.entity.ScheduleTestingNodeConfig;
import com.todaysoft.lims.testing.base.entity.TestingConcludingReport;
import com.todaysoft.lims.testing.base.entity.TestingDataSend;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingReportEmail;
import com.todaysoft.lims.testing.base.entity.TestingReportReview;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.utils.DateUtil;
import com.todaysoft.lims.testing.cycleConfig.dao.GlobalConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.OtherReportFormSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.TestingConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.model.OtherReportFormModel;
import com.todaysoft.lims.testing.cycleConfig.model.ScheduleTestingNodeConfigModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.testing.cycleConfig.service.ICycleConfigService;
import com.todaysoft.lims.testing.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.testing.cycleConfig.service.request.TaskConfigRequest;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class CycleConfigService implements ICycleConfigService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Override
    public Pagination<WarningGlobalConfigModel> globalPagining(GlobalConfigSearcher searcher)
    {
        Pagination<WarningGlobalConfigModel> pagination =
            baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), WarningGlobalConfigModel.class);
        return pagination;
    }
    
    @Override
    public Pagination<WarningTestingConfigModel> testingPagining(TestingConfigSearcher searcher)
    {
        Pagination<WarningTestingConfigModel> pag = new Pagination<WarningTestingConfigModel>();
        Pagination<ScheduleTestingConfig> pagination =
            baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), ScheduleTestingConfig.class);
        List<ScheduleTestingConfig> list = pagination.getRecords();
        if (Collections3.isNotEmpty(list))
        {
            for (ScheduleTestingConfig testingConfig : list)
            {
                TestingMethod tm = baseDaoSupport.get(TestingMethod.class, testingConfig.getTestingMethod().getId());
                if (null != tm)
                {
                    testingConfig.setTestingMethodName(tm.getName());
                    testingConfig.setMethodType(Integer.valueOf(tm.getType()));
                }
            }
        }
        BeanUtils.copyProperties(pagination, pag);
        return pag;
    }
    
    @Override
    @Transactional
    public void modify(GlobalConfigRequest request)
    {
        GlobalConfigSearcher searcher = new GlobalConfigSearcher();
        searcher.setEventKey(request.getEventKey());
        List<ScheduleGlobalConfig> list = baseDaoSupport.find(searcher.toQuery(), ScheduleGlobalConfig.class);
        if (Collections3.isNotEmpty(list))
        {
            ScheduleGlobalConfig entity = list.get(0);
            entity.setThreshold(request.getThreshold());
            baseDaoSupport.update(entity);
        }
    }
    
    @Override
    public List<TestingMethod> getTestingMethodList()
    {
        String hql = "FROM TestingMethod t WHERE t.type = 1 ";
        List<TestingMethod> records = baseDaoSupport.find(TestingMethod.class, hql);
        return records;
    }
    
    @Override
    public TestingMethod getTestingMethodListById(String id)
    {
        TestingMethod tm = baseDaoSupport.get(TestingMethod.class, id);
        return tm;
    }
    
    @Override
    public TaskConfig getTaskConfigBySemantic(String semantic)
    {
        return bcmadapter.getTaskConfigBySemantic(semantic);
    }
    
    @Override
    public boolean validate(TestingConfigSearcher request)
    {
        List<ScheduleTestingConfig> list = baseDaoSupport.find(request.toQuery(), ScheduleTestingConfig.class);
        
        if (Collections3.isNotEmpty(list))
        {
            ScheduleTestingConfig wc = Collections3.getFirst(list);
            if (wc.getId().equals(request.getId()))
            {
                return true;
            }
            else
            {
                return false;
            }
            
        }
        return true;
        
    }
    
    @Override
    @Transactional
    public void create(TaskConfigRequest request)
    {
        ScheduleTestingConfig testingConfig = new ScheduleTestingConfig();
        int sumThreshold = 0;
        int max = 0;// dna提取质检、cdna提取质检 最大
        int other = 0;// 除dna提取质检、cdna提取质检
        int dna = 0;
        int cdna = 0;
        List<ScheduleTestingNodeConfigModel> list = JSON.parseArray(request.getContent() + "", ScheduleTestingNodeConfigModel.class);
        if (Collections3.isNotEmpty(list))
        {
            for (ScheduleTestingNodeConfigModel stnf : list)
            {
                if (TaskSemantic.DNA_EXTRACT.equals(stnf.getNodeSemantic()) || TaskSemantic.DNA_QC.equals(stnf.getNodeSemantic())
                    || TaskSemantic.CDNA_EXTRACT.equals(stnf.getNodeSemantic()) || TaskSemantic.CDNA_QC.equals(stnf.getNodeSemantic()))
                {
                    if (TaskSemantic.DNA_EXTRACT.equals(stnf.getNodeSemantic()) || TaskSemantic.DNA_QC.equals(stnf.getNodeSemantic()))
                    {
                        dna += stnf.getThreshold();
                    }
                    if (TaskSemantic.CDNA_EXTRACT.equals(stnf.getNodeSemantic()) || TaskSemantic.CDNA_QC.equals(stnf.getNodeSemantic()))
                    {
                        cdna += stnf.getThreshold();
                    }
                    max = Math.max(dna, cdna);
                }
                else
                {
                    other += stnf.getThreshold();
                }
                sumThreshold = max + other;
            }
        }
        
        if (StringUtils.isNotEmpty(request.getId()))
        {
            testingConfig = baseDaoSupport.get(ScheduleTestingConfig.class, request.getId());
            // stc.setTestingMethod(baseDaoSupport.get(TestingMethod.class, request.getTestingMethodId()));
            testingConfig.setConfigName(request.getConfigName());
            testingConfig.setConfigDesc(request.getConfigDesc());
            testingConfig.setThreshold(sumThreshold);
            baseDaoSupport.update(testingConfig);
            baseDaoSupport.executeHql("delete ScheduleTestingNodeConfig s where s.scheduleTestingConfig.id = ?", new Object[] {request.getId()});
        }
        else
        {
            testingConfig.setTestingMethod(baseDaoSupport.get(TestingMethod.class, request.getTestingMethodId()));
            testingConfig.setConfigName(request.getConfigName());
            testingConfig.setConfigDesc(request.getConfigDesc());
            testingConfig.setThreshold(sumThreshold);
            testingConfig.setCreateTime(new Date());
            baseDaoSupport.insert(testingConfig);
        }
        
        if (Collections3.isNotEmpty(list))
        {
            for (ScheduleTestingNodeConfigModel stnf : list)
            {
                ScheduleTestingNodeConfig nodeConfig = new ScheduleTestingNodeConfig();
                nodeConfig.setScheduleTestingConfig(testingConfig);
                nodeConfig.setNodeSemantic(stnf.getNodeSemantic());
                nodeConfig.setNodeName(stnf.getNodeName());
                nodeConfig.setThreshold(stnf.getThreshold());
                baseDaoSupport.insert(nodeConfig);
            }
        }
    }
    
    @Override
    public ScheduleTestingConfig getTestingConfigById(String id)
    {
        return baseDaoSupport.get(ScheduleTestingConfig.class, id);
    }
    
    @Override
    @Transactional
    public Integer delete(String id)
    {
        ScheduleTestingConfig stc = baseDaoSupport.get(ScheduleTestingConfig.class, id);
        String hql = "FROM ProductTestingMethod t WHERE t.scheduleConfigId = :scheduleConfigId";
        List<ProductTestingMethod> productTestingMethodList =
            baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[] {"scheduleConfigId"}, new String[] {id});
        if (Collections3.isNotEmpty(productTestingMethodList))
        {
            return 1;
        }
        stc.setDelFlag(true);
        stc.setDeleteTime(new Date());
        baseDaoSupport.update(stc);
        return null;
    }
    
    @Override
    public List<WarningTestingConfigModel> getScheduleTestingConfigList(TestingConfigSearcher request)
    {
        TestingConfigSearcher searcher = new TestingConfigSearcher();
        searcher.setTestingMethodId(request.getTestingMethodId());
        return baseDaoSupport.find(searcher.toQuery(), WarningTestingConfigModel.class);
    }
    
    @Override
    public List<OtherReportFormModel> getCycleReportFormList(OtherReportFormSearcher request)
    {
        List<OtherReportFormModel> modelList = Lists.newArrayList();
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append("SELECT s FROM Order s left join s.orderProductList op WHERE s.deleted =false ");
        //hql.append("FROM TestingSchedule s WHERE EXISTS(select o.id from Order o where o.id = s.orderId and o.deleted =false");
        addFiter(hql, names, values, request);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<Order> orderList = baseDaoSupport.find(queryer, Order.class);
        //List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
        if (Collections3.isNotEmpty(orderList))
        {
            for (Order order : orderList)
            {
                //物流运输
                //OrderPlanTask orderPlanSampleReceive = getOrderPlanTaskByOrderId(order.getId(), "SAMPLE_RECEIVE");
                //财务确认
                OrderPlanTask orderPlanPayment = getOrderPlanTaskByOrderId(order.getId(), "PAYMENT_CONFIRM");
                //实验启动时间
                Date scheduleStartTime = getScheduleStartDate(order.getId());
                List<TestingSchedule> scheduleList = getTestingScheduleByOrderId(order.getId());
                if (Collections3.isNotEmpty(scheduleList))
                {
                    for (TestingSchedule schedule : scheduleList)
                    {
                        OtherReportFormModel model = new OtherReportFormModel();
                        model.setOrderCode(order.getCode());
                        if (null != order.getType())
                        {
                            model.setMarketingCenter(order.getType().getName());
                        }
                        
                        if (1 == order.getTestingStatus())
                        {
                            model.setTestingStatus("待检测下放");
                        }
                        if (2 == order.getTestingStatus())
                        {
                            model.setTestingStatus("检测中");
                        }
                        if (3 == order.getTestingStatus())
                        {
                            model.setTestingStatus("已暂停");
                        }
                        if (4 == order.getTestingStatus())
                        {
                            model.setTestingStatus("已取消");
                        }
                        if (5 == order.getTestingStatus())
                        {
                            model.setTestingStatus("已完成");
                        }
                        Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                        model.setProductName(product.getName());
                        TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                        model.setTestingMethods(method.getName());
                        if (null != order.getContract())
                        {
                            model.setContractName(order.getContract().getName());
                        }
                        String sampleCodes = getSampleCode(schedule.getSampleId());
                        model.setSampleCode(sampleCodes);
                        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
                        {
                            model.setExamineeName(order.getOrderExamineeList().get(0).getName());
                        }
                        Customer cus = baseDaoSupport.get(Customer.class, order.getOwnerId());
                        if (null != cus)
                        {
                            model.setCustomerName(cus.getRealName());
                            if (null != cus.getCompany())
                            {
                                model.setCompanyName(cus.getCompany().getName());
                            }
                        }
                        model.setCreateName(order.getCreatorName());
                        /*if(StringUtils.isNotEmpty(getScheduleTestingConfig(schedule.getProductId(), schedule.getMethodId())))
                        {
                            ScheduleTestingConfig scheduleConfig = baseDaoSupport.get(ScheduleTestingConfig.class, getScheduleTestingConfig(schedule.getProductId(), schedule.getMethodId()));
                            if (null != scheduleConfig)
                            {
                                model.setCycleValue(String.valueOf(scheduleConfig.getThreshold()));
                            }
                        }*/
                        //产品周期
                        if (null != product.getTestingDuration())
                        {
                            model.setCycleValue(product.getTestingDuration().toString());
                        }
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date planFinishTime = null;
                        if (null != order.getSubmitTime())
                        {
                            model.setSubmitTime(DateUtil.format(order.getSubmitTime(), "yyyy-MM-dd HH:mm:ss"));
                            //预计完成时间
                            if (null != product.getTestingDuration())
                            {
                                planFinishTime = DateUtil.plusDay(order.getSubmitTime(), product.getTestingDuration());
                                model.setPlanFinishTime(DateUtil.format(planFinishTime, "yyyy-MM-dd HH:mm:ss"));
                            }
                        }
                        //实际完成时间
                        Date actualFinishDate = null;
                        TestingReportEmail reportEmail = getReportEmailDate(schedule.getOrderId(), schedule.getProductId());
                        TestingDataSend dataSend =
                            getContractReportSendDate(schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId());
                        TestingConcludingReport conreport = null;
                        if (null != order.getType())
                        {
                            if ("4".equals(order.getType().getId()))
                            {
                                conreport =
                                    getReportConcludingDate(schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId());
                            }
                        }
                        
                        if (null != order.getContract())
                        {
                            ContractContent content = getContractContent(order.getContract().getId());
                            if (null != content)
                            {
                                if (content.getDeliveryMode().contains("3"))
                                {
                                    if (null != reportEmail)
                                    {
                                        if (null != reportEmail.getUpdateTime())
                                        {
                                            if (null != dataSend)
                                            {
                                                if (null != dataSend.getSendTime())
                                                {
                                                    actualFinishDate = getMaxDate(dataSend.getSendTime(), reportEmail.getUpdateTime());
                                                    model.setActualFinishTime(DateUtil.format(getMaxDate(dataSend.getSendTime(), reportEmail.getUpdateTime()),
                                                        "yyyy-MM-dd HH:mm:ss"));
                                                }
                                            }
                                        }
                                        
                                    }
                                }
                                else
                                {
                                    if (null != dataSend)
                                    {
                                        if (null != dataSend.getSendTime())
                                        {
                                            actualFinishDate = dataSend.getSendTime();
                                            model.setActualFinishTime(DateUtil.format(dataSend.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
                                        }
                                    }
                                }
                            }
                            
                        }
                        else
                        {
                            if ("4".equals(order.getType().getId()))
                            {
                                if (null != conreport)
                                {
                                    if (null != conreport.getUpdateTime())
                                    {
                                        if (null != dataSend)
                                        {
                                            if (null != dataSend.getSendTime())
                                            {
                                                actualFinishDate = getMaxDate(dataSend.getSendTime(), conreport.getUpdateTime());
                                                model.setActualFinishTime(DateUtil.format(getMaxDate(dataSend.getSendTime(), conreport.getUpdateTime()),
                                                    "yyyy-MM-dd HH:mm:ss"));
                                            }
                                        }
                                        else
                                        {
                                            actualFinishDate = conreport.getUpdateTime();
                                            model.setActualFinishTime(DateUtil.format(conreport.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (null != reportEmail)
                                {
                                    if (null != reportEmail.getUpdateTime())
                                    {
                                        actualFinishDate = reportEmail.getUpdateTime();
                                        model.setActualFinishTime(DateUtil.format(reportEmail.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                                    }
                                }
                            }
                        }
                        
                        if (null != actualFinishDate)
                        {
                            if (null != planFinishTime)
                            {
                                if (actualFinishDate.after(planFinishTime))
                                {
                                    model.setIsOnTime("否");
                                    model.setPostponeDay(DateUtil.getDaysOfTwoDate(planFinishTime, actualFinishDate).toString());
                                }
                                else
                                {
                                    model.setIsOnTime("是");
                                }
                            }
                            if (null != order.getSubmitTime())
                            {
                                model.setActualFinishDay(DateUtil.getDaysOfTwoDate(order.getSubmitTime(), actualFinishDate).toString());
                            }
                        }
                        else
                        {
                            if (null != planFinishTime)
                            {
                                if (new Date().after(planFinishTime))
                                {
                                    model.setIsOnTime("否");
                                }
                            }
                        }
                        
                        int resampleCount = 0;
                        /*List<TestingScheduleHistory> historys = getTestingScheduleHistory(schedule.getId());
                        if (Collections3.isNotEmpty(historys))
                        {
                            for (TestingScheduleHistory history : historys)
                            {
                                AbnormalSolveRecord abSolverRecord = getAbnormalSolveRecordByTaskId(history.getTaskId());
                                if (null != abSolverRecord)
                                {
                                    resampleCount++;
                                }
                            }
                        }*/
                        if (StringUtils.isNotEmpty(sampleCodes))
                        {
                            resampleCount = sampleCodes.split("/").length - 1;
                        }
                        model.setResampleCount(String.valueOf(resampleCount));
                        model.setNgsseqCount(String.valueOf(getNgsseqCount(schedule.getId())));
                        model.setLane(getLane(schedule.getId()));
                        Date sampleReceivingDate = getSampleReceivingDate(schedule.getSampleId());
                        if (null != sampleReceivingDate && null != order.getSubmitTime())
                        {
                            model.setSampleReceivedDay(DateUtil.getDaysOfTwoDate(order.getSubmitTime(), sampleReceivingDate).toString());
                        }
                        if (null != scheduleStartTime && null != sampleReceivingDate)
                        {
                            model.setTestingStartDay(DateUtil.getDaysOfTwoDate(sampleReceivingDate, scheduleStartTime).toString());
                        }
                        if (null != orderPlanPayment)
                        {
                            if (null != orderPlanPayment.getActualFinishDate() && null != order.getSubmitTime())
                            {
                                String actualFinishDate2 = DateUtil.format(orderPlanPayment.getActualFinishDate(), "yyyy-MM-dd") + " 23:59:59";
                                try
                                {
                                    model.setOrderPaymentDay(DateUtil.getDaysOfTwoDate(order.getSubmitTime(), dateFormat.parse(actualFinishDate2)).toString());
                                }
                                catch (ParseException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Date dnaextStartTime = getTaskDateById(schedule.getId(), "DNA-EXT", true);
                        Date dnaqcEndTime = getTaskDateById(schedule.getId(), "DNA-QC", false);
                        if (null != dnaextStartTime && null != dnaqcEndTime)
                        {
                            model.setDnaDay(DateUtil.getDaysOfTwoDate(dnaextStartTime, dnaqcEndTime).toString());
                        }
                        else
                        {
                            Date cdnaextStartTime = getTaskDateById(schedule.getId(), "CDNA-EXT", true);
                            Date cdnaqcEndTime = getTaskDateById(schedule.getId(), "CDNA-QC", false);
                            if (null != cdnaextStartTime && null != cdnaqcEndTime)
                            {
                                model.setDnaDay(DateUtil.getDaysOfTwoDate(cdnaextStartTime, cdnaqcEndTime).toString());
                            }
                        }
                        //验证完成时间或技术分析完成时间
                        Date dateForReport = null;
                        if ("NGS".equals(method.getName()) || "CapNGS".equals(method.getName()))
                        {
                            Date libcreStartTime = getTaskDateById(schedule.getId(), "LIBRARY-CRE", true);
                            Date libqcEndTime = getTaskDateById(schedule.getId(), "LIBRARY-QC", false);
                            if (null != libcreStartTime && null != libqcEndTime)
                            {
                                model.setLibCreateDay(DateUtil.getDaysOfTwoDate(libcreStartTime, libqcEndTime).toString());
                            }
                            Date libcatchStartTime = getTaskDateById(schedule.getId(), "LIBRARY-CAP", true);
                            Date libcatchEndTime = getTaskDateById(schedule.getId(), "LIBRARY-CAP", false);
                            if (null != libcatchStartTime && null != libcatchEndTime)
                            {
                                model.setLibCatchDay(DateUtil.getDaysOfTwoDate(libcatchStartTime, libcatchEndTime).toString());
                            }
                            Date rqtStartTime = getTaskDateById(schedule.getId(), "RQT", true);
                            Date qtEndTime = getTaskDateById(schedule.getId(), "QT", false);
                            if (null != rqtStartTime && null != qtEndTime)
                            {
                                model.setRqtDay(DateUtil.getDaysOfTwoDate(rqtStartTime, qtEndTime).toString());
                            }
                            Date ngsseqStartTime = getTaskDateById(schedule.getId(), "NGS-SEQ", true);
                            Date ngsseqEndTime = getTaskDateById(schedule.getId(), "NGS-SEQ", false);
                            if (null != ngsseqStartTime && null != ngsseqEndTime)
                            {
                                model.setNgsseqDay(DateUtil.getDaysOfTwoDate(ngsseqStartTime, ngsseqEndTime).toString());
                            }
                            Date biologyStartTime = getTaskDateById(schedule.getId(), "BIOLOGY-ANALY", true);
                            Date biologyEndTime = getTaskDateById(schedule.getId(), "BIOLOGY-ANALY", false);
                            if (null != biologyStartTime && null != biologyEndTime)
                            {
                                model.setBiologyDay(DateUtil.getDaysOfTwoDate(biologyStartTime, biologyEndTime).toString());
                            }
                            Date tecStartTime = getTaskDateById(schedule.getId(), "TECHNICAL-ANALY", true);
                            Date tecEndTime = getTaskDateById(schedule.getId(), "TECHNICAL-ANALY", false);
                            if (null != tecStartTime && null != tecEndTime)
                            {
                                model.setTechnicalDay(DateUtil.getDaysOfTwoDate(tecStartTime, tecEndTime).toString());
                            }
                            //验证周期
                            Date tecFirstEndTime = getEndDateBySemantic(schedule.getId(), "TECHNICAL-ANALY");//TECHNICAL-ANALY
                            Date verifyScheduleDate = getDateForVerifySchedule(schedule.getId());
                            dateForReport = verifyScheduleDate;
                            if (null != tecFirstEndTime && null != verifyScheduleDate)
                            {
                                model.setVerifyDay(DateUtil.getDaysOfTwoDate(tecFirstEndTime, verifyScheduleDate).toString());
                            }
                        }
                        else
                        {
                            Date qcFirstEndDate = getEndDateBySemantic(schedule.getId(), "DNA-QC");//DNA-QC
                            if (null == qcFirstEndDate)
                            {
                                qcFirstEndDate = getEndDateBySemantic(schedule.getId(), "CDNA-QC");//CDNA-QC
                            }
                            if ("荧光检测".equals(method.getName()))
                            {
                                Date fluoDate = getStartDateBySemantic(schedule.getId(), "FLUO-ANALYSE");
                                if (null != qcFirstEndDate && null != fluoDate)
                                {
                                    model.setTestDay(DateUtil.getDaysOfTwoDate(qcFirstEndDate, fluoDate).toString());
                                }
                                Date fluoStartTime = getTaskDateById(schedule.getId(), "FLUO-ANALYSE", true);
                                Date fluoEndTime = getTaskDateById(schedule.getId(), "FLUO-ANALYSE", false);
                                dateForReport = fluoEndTime;
                                if (null != fluoStartTime && null != fluoEndTime)
                                {
                                    model.setTechnicalDay(DateUtil.getDaysOfTwoDate(fluoStartTime, fluoEndTime).toString());
                                }
                            }
                            if ("Sanger检测".equals(method.getName()))
                            {
                                Date sangerDate = getStartDateBySemantic(schedule.getId(), "SANGER-DATA-ANALYSIS");
                                if (null != qcFirstEndDate && null != sangerDate)
                                {
                                    model.setTestDay(DateUtil.getDaysOfTwoDate(qcFirstEndDate, sangerDate).toString());
                                }
                                Date sangerStartTime = getTaskDateById(schedule.getId(), "SANGER-DATA-ANALYSIS", true);
                                Date sangerEndTime = getTaskDateById(schedule.getId(), "SANGER-DATA-ANALYSIS", false);
                                dateForReport = sangerEndTime;
                                if (null != sangerStartTime && null != sangerEndTime)
                                {
                                    model.setTechnicalDay(DateUtil.getDaysOfTwoDate(sangerStartTime, sangerEndTime).toString());
                                }
                            }
                            if ("MLPA检测".equals(method.getName()))
                            {
                                Date mlpaDate = getStartDateBySemantic(schedule.getId(), "MLPA-DATA-ANALYSIS");
                                if (null != qcFirstEndDate && null != mlpaDate)
                                {
                                    model.setTestDay(DateUtil.getDaysOfTwoDate(qcFirstEndDate, mlpaDate).toString());
                                }
                                Date mlpaStartTime = getTaskDateById(schedule.getId(), "MLPA-DATA-ANALYSIS", true);
                                Date mlpaEndTime = getTaskDateById(schedule.getId(), "MLPA-DATA-ANALYSIS", false);
                                dateForReport = mlpaEndTime;
                                if (null != mlpaStartTime && null != mlpaEndTime)
                                {
                                    model.setTechnicalDay(DateUtil.getDaysOfTwoDate(mlpaStartTime, mlpaEndTime).toString());
                                }
                            }
                            if ("动态突变".equals(method.getName()))
                            {
                                Date dtDate = getStartDateBySemantic(schedule.getId(), "DT-DATA-ANALYSIS");
                                if (null != qcFirstEndDate && null != dtDate)
                                {
                                    model.setTestDay(DateUtil.getDaysOfTwoDate(qcFirstEndDate, dtDate).toString());
                                }
                                Date dtStartTime = getTaskDateById(schedule.getId(), "DT-DATA-ANALYSIS", true);
                                Date dtEndTime = getTaskDateById(schedule.getId(), "DT-DATA-ANALYSIS", false);
                                dateForReport = dtEndTime;
                                if (null != dtStartTime && null != dtEndTime)
                                {
                                    model.setTechnicalDay(DateUtil.getDaysOfTwoDate(dtStartTime, dtEndTime).toString());
                                }
                            }
                            if ("多重PCR检测".equals(method.getName()) || "Long-PCR检测".equals(method.getName()))
                            {
                                Date dadate = getStartDateBySemantic(schedule.getId(), "TECHNICAL-ANALY");
                                if (null != qcFirstEndDate && null != dadate)
                                {
                                    model.setTestDay(DateUtil.getDaysOfTwoDate(qcFirstEndDate, dadate).toString());
                                }
                                Date daStartTime = getTaskDateById(schedule.getId(), "TECHNICAL-ANALY", true);
                                Date daEndTime = getTaskDateById(schedule.getId(), "TECHNICAL-ANALY", false);
                                dateForReport = daEndTime;
                                if (null != daStartTime && null != daEndTime)
                                {
                                    model.setTechnicalDay(DateUtil.getDaysOfTwoDate(daStartTime, daEndTime).toString());
                                }
                            }
                        }
                        //报告整合
                        /*OrderPlanTask reportGeneTask = getOrderPlanTaskByOrderProductId(order.getId(), schedule.getProductId(), "REPORT_GENERATE");
                        if(null != reportGeneTask)
                        {
                            if(null != reportGeneTask.getActualStartDate() && null != reportGeneTask.getActualFinishDate())
                            {
                                model.setReportGenerateDay(DateUtil.getDaysOfTwoDate(reportGeneTask.getActualStartDate(), reportGeneTask.getActualFinishDate()).toString());
                            }
                        }*/
                        Date geneReportTime = getGeneReportTime(schedule.getOrderId(), schedule.getProductId());
                        if (null != dateForReport && null != geneReportTime)
                        {
                            model.setReportGenerateDay(DateUtil.getDaysOfTwoDate(dateForReport, geneReportTime).toString());
                        }
                        //报告审核
                        /*OrderPlanTask reportVerifyTask = getOrderPlanTaskByOrderProductId(order.getId(), schedule.getProductId(), "REPORT_VERIFY");
                        if(null != reportVerifyTask)
                        {
                            if(null != reportVerifyTask.getActualStartDate() && null != reportVerifyTask.getActualFinishDate())
                            {
                                model.setReportCheckDay(DateUtil.getDaysOfTwoDate(reportVerifyTask.getActualStartDate(), reportVerifyTask.getActualFinishDate()).toString());
                            }
                        }*/
                        Date oneStartTime = getVerifyReportTime(schedule.getOrderId(), schedule.getProductId(), "1");
                        Date twoEndTime = getVerifyReportTime(schedule.getOrderId(), schedule.getProductId(), "2");
                        if (null != oneStartTime && null != twoEndTime)
                        {
                            model.setReportCheckDay(DateUtil.getDaysOfTwoDate(oneStartTime, twoEndTime).toString());
                        }
                        //报告发送
                        /*OrderPlanTask reportSendTask = getOrderPlanTaskByOrderProductId(order.getId(), schedule.getProductId(), "REPORT_DELIVER");
                        if(null != reportSendTask)
                        {
                            if(null != reportSendTask.getActualStartDate() && null != reportSendTask.getActualFinishDate())
                            {
                                model.setReportSendDay(DateUtil.getDaysOfTwoDate(reportSendTask.getActualStartDate(), reportSendTask.getActualFinishDate()).toString());
                            }
                        }*/
                        
                        if (null != reportEmail)
                        {
                            if (null != reportEmail.getCreateTime() && null != reportEmail.getUpdateTime())
                            {
                                model.setReportSendDay(DateUtil.getDaysOfTwoDate(reportEmail.getCreateTime(), reportEmail.getUpdateTime()).toString());
                            }
                        }
                        if (null != dataSend)
                        {
                            if (null != dataSend.getCreateTime() && null != dataSend.getSendTime())
                            {
                                model.setReportSendDay(DateUtil.getDaysOfTwoDate(dataSend.getCreateTime(), dataSend.getSendTime()).toString());
                            }
                        }
                        if (null != conreport)
                        {
                            if (null != conreport.getCreateTime() && null != conreport.getUpdateTime())
                            {
                                model.setReportSendDay(DateUtil.getDaysOfTwoDate(conreport.getCreateTime(), conreport.getUpdateTime()).toString());
                            }
                        }
                        
                        modelList.add(model);
                    }
                }
            }
        }
        return modelList;
    }
    
    //比较时间
    private Date getMaxDate(Date date1, Date date2)
    {
        if (date2.after(date1))
        {
            return date2;
        }
        return date1;
    }
    
    //出报告时间
    private Date getGeneReportTime(String orderId, String productId)
    {
        String hql = "FROM OrderProduct o WHERE o.order.id = :orderId AND o.product.id = :productId";
        List<OrderProduct> list =
            baseDaoSupport.findByNamedParam(OrderProduct.class, hql, new String[] {"orderId", "productId"}, new Object[] {orderId, productId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0).getReportTime();
        }
        return null;
    }
    
    //报告审核时间
    private Date getVerifyReportTime(String orderId, String productId, String reviewNode)
    {
        String hql = "SELECT t.id FROM TestingReport t WHERE t.order.id = :orderId AND t.product.id = :productId";
        List<String> list = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"orderId", "productId"}, new Object[] {orderId, productId});
        if (Collections3.isNotEmpty(list))
        {
            String hql2 = "FROM TestingReportReview v WHERE v.report.id = :reportId AND v.reviewNode = :reviewNode ";
            if ("1".equals(reviewNode))
            {
                hql2 += " order by v.reviewTime asc";
            }
            else if ("2".equals(reviewNode))
            {
                hql2 += " AND v.reviewResult = '1' order by v.reviewTime desc";
            }
            List<TestingReportReview> reviewList =
                baseDaoSupport.findByNamedParam(TestingReportReview.class,
                    hql2,
                    new String[] {"reportId", "reviewNode"},
                    new Object[] {list.get(0), reviewNode});
            if (Collections3.isNotEmpty(reviewList))
            {
                return reviewList.get(0).getReviewTime();
            }
        }
        return null;
    }
    
    //报告寄送时间
    private TestingReportEmail getReportEmailDate(String orderId, String productId)
    {
        String hql = "FROM TestingReportEmail t WHERE t.order.id = :orderId AND t.product.id = :productId";
        List<TestingReportEmail> list =
            baseDaoSupport.findByNamedParam(TestingReportEmail.class, hql, new String[] {"orderId", "productId"}, new Object[] {orderId, productId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    //合同订单报告发送时间
    private TestingDataSend getContractReportSendDate(String orderId, String productId, String methodId, String sampleId)
    {
        
        String hql = "FROM TestingDataSend t WHERE t.orderId = :orderId AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId";
        List<TestingDataSend> list =
            baseDaoSupport.findByNamedParam(TestingDataSend.class, hql, new String[] {"orderId", "productId", "methodId", "sampleId"}, new Object[] {orderId,
                productId, methodId, sampleId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    //科研报告处理时间
    private TestingConcludingReport getReportConcludingDate(String orderId, String productId, String methodId, String sampleId)
    {
        String hql =
            "FROM TestingConcludingReport t WHERE t.orderId = :orderId AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId";
        List<TestingConcludingReport> list =
            baseDaoSupport.findByNamedParam(TestingConcludingReport.class, hql, new String[] {"orderId", "productId", "methodId", "sampleId"}, new Object[] {
                orderId, productId, methodId, sampleId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
        
    }
    
    //获取
    private ContractContent getContractContent(String contractId)
    {
        String hql = "from ContractContent c where c.contractId = :contractId";
        List<ContractContent> list = baseDaoSupport.findByNamedParam(ContractContent.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
        
    }
    
    // 检测流程
    private List<TestingSchedule> getTestingScheduleByOrderId(String orderId)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.verifyKey is null";
        List<TestingSchedule> list = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId"}, new Object[] {orderId});
        return list;
    }
    
    // 获取样本
    private String getSampleCode(String sampleId)
    {
        String sampleCodes = "";
        String abnormalSampleId = "";
        TestingSample testingSample = baseDaoSupport.get(TestingSample.class, sampleId);
        if (null != testingSample)
        {
            sampleCodes = testingSample.getReceivedSample().getSampleCode();
            abnormalSampleId = testingSample.getReceivedSample().getSampleId();
        }
        else
        {
            ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
            if (null != receivedSample)
            {
                sampleCodes = receivedSample.getSampleCode();
            }
            abnormalSampleId = sampleId;
        }
        List<String> resendSampleList = Lists.newArrayList();
        getResendSampleId(abnormalSampleId, resendSampleList);
        if (Collections3.isNotEmpty(resendSampleList))
        {
            for (String str : resendSampleList)
            {
                ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, str);
                sampleCodes += "/" + receivedSample.getSampleCode();
            }
        }
        return sampleCodes;
        
    }
    
    private void getResendSampleId(String abnormalSampleId, List<String> list)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.abnormalSampleId = :abnormalSampleId AND t.resendSampleStatus = :resendSampleStatus";
        List<TestingResamplingRecord> records =
            baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"abnormalSampleId", "resendSampleStatus"}, new Object[] {
                abnormalSampleId, TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if (Collections3.isNotEmpty(records))
        {
            String s = records.get(0).getResendSampleId();
            list.add(s);
            getResendSampleId(s, list);
        }
    }
    
    //样本接收时间
    private Date getSampleReceivingDate(String sampleId)
    {
        String sampleCode = "";
        TestingSample testingSample = baseDaoSupport.get(TestingSample.class, sampleId);
        if (null != testingSample)
        {
            sampleCode = testingSample.getReceivedSample().getSampleCode();
        }
        else
        {
            ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
            if (null != receivedSample)
            {
                sampleCode = receivedSample.getSampleCode();
            }
        }
        List<SampleReceivingDetail> list =
            baseDaoSupport.find(SampleReceivingDetail.class, "FROM SampleReceivingDetail s WHERE s.sampleCode = '" + sampleCode + "'");
        if (Collections3.isNotEmpty(list))
        {
            SampleReceiving sampleReceiving = list.get(0).getSampleReceiving();
            if (null != sampleReceiving)
            {
                return sampleReceiving.getReceiveTime();
            }
        }
        return null;
        
    }
    
    // 获取产品、检测方法周期配置
    private String getScheduleTestingConfig(String productId, String methodId)
    {
        String hql = "FROM ProductTestingMethod t WHERE t.product.id = :productId AND t.testingMethod.id = :methodId";
        List<ProductTestingMethod> list =
            baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[] {"productId", "methodId"}, new Object[] {productId, methodId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0).getScheduleConfigId();
        }
        return null;
    }
    
    // 订单监控
    private OrderPlanTask getOrderPlanTask(TestingSchedule schedule)
    {
        String hql =
            "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
        List<OrderPlanTask> list =
            baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                hql,
                new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic"},
                new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), "GROUP_PRODUCT_SAMPLE_METHOD"});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    // 流程历史
    private List<TestingScheduleHistory> getTestingScheduleHistory(String scheduleId)
    {
        String hql = "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId ";
        List<TestingScheduleHistory> list =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
        return list;
    }
    
    // 获取异常记录
    private AbnormalSolveRecord getAbnormalSolveRecordByTaskId(String taskId)
    {
        String hql = "FROM AbnormalSolveRecord t WHERE t.taskId = :taskId AND t.strategy = :strategy";
        List<AbnormalSolveRecord> list =
            baseDaoSupport.findByNamedParam(AbnormalSolveRecord.class, hql, new String[] {"taskId", "strategy"}, new Object[] {taskId, "RESAMPLING"});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    // 上机次数
    private int getNgsseqCount(String scheduleId)
    {
        int ngsseqCount = 0;
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM TestingScheduleHistory t WHERE "
                    + " EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId and tt.semantic = 'NGS-SEQ') AND t.scheduleId = :scheduleId")
                .names(Arrays.asList("scheduleId"))
                .values(Arrays.asList(scheduleId))
                .build();
        ngsseqCount = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        return ngsseqCount;
    }
    
    // lane号(产品最后一次上机的lane号)
    private String getLane(String scheduleId)
    {
        String sequenceCode = "";
        String hql =
            "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId AND EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId AND tt.semantic= 'NGS-SEQ' ) ORDER BY t.timestamp DESC ";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
        List<TestingScheduleHistory> histories = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(histories))
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, histories.get(0).getTaskId());
            sequenceCode = task.getTestingLaneCode();
            /*String hql1 = "FROM TestingSheetTask t WHERE t.testingTaskId = :testingTaskId";
            NamedQueryer queryer1 = NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("testingTaskId")).values(Lists.newArrayList(histories.get(0).getTaskId())).build();
            List<TestingSheetTask> sheetTasks = baseDaoSupport.find(queryer1, TestingSheetTask.class);
            if (Collections3.isNotEmpty(sheetTasks))
            {
                TestingSheetTask sheetTask = Collections3.getFirst(sheetTasks);
                TestingSheet sheet = sheetTask.getTestingSheet();
                if (null != sheet && StringUtils.isNotEmpty(sheet.getVariables()))
                {
                    PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                    if (StringUtils.isEmpty(sequenceCode))
                    {
                        sequenceCode = variables.getPoolingCode();
                    }
                }
            }*/
        }
        return sequenceCode;
    }
    
    // 根据order semantic
    private OrderPlanTask getOrderPlanTaskByOrderId(String orderId, String semantic)
    {
        String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :taskSemantic";
        List<OrderPlanTask> list =
            baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, new String[] {"orderId", "taskSemantic"}, new Object[] {orderId, semantic});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    //流程启动时间
    private Date getScheduleStartDate(String orderId)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId ORDER BY t.startTime";
        List<TestingSchedule> list = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId"}, new Object[] {orderId});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0).getStartTime();
        }
        return null;
    }
    
    //根据任务获取时间
    private Date getTaskDateById(String scheduleId, String semantic, boolean isAsc)
    {
        String hql =
            "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId AND EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId AND tt.semantic=:semantics) ";
        if (true == isAsc)
        {
            hql += " ORDER BY t.timestamp";
        }
        else
        {
            hql += " ORDER BY t.timestamp DESC";
        }
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId", "semantics")).values(Lists.newArrayList(scheduleId, semantic)).build();
        List<TestingScheduleHistory> histories = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(histories))
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, histories.get(0).getTaskId());
            if (null != task)
            {
                if (true == isAsc)
                {
                    return task.getStartTime();
                }
                else
                {
                    return task.getEndTime();
                }
            }
        }
        return null;
        
    }
    
    //第一次任务完成时间
    private Date getEndDateBySemantic(String scheduleId, String semantics)
    {
        String hql =
            "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId AND EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId AND tt.semantic=:semantics) ORDER BY t.timestamp";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId", "semantics")).values(Lists.newArrayList(scheduleId, semantics)).build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(historys))
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, historys.get(0).getTaskId());
            if (null != task)
            {
                return task.getEndTime();
            }
        }
        return null;
    }
    
    //最后一次开始时间
    private Date getStartDateBySemantic(String scheduleId, String semantics)
    {
        String hql =
            "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId AND EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId AND tt.semantic=:semantics) ORDER BY t.timestamp DESC";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId", "semantics")).values(Lists.newArrayList(scheduleId, semantics)).build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(historys))
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, historys.get(0).getTaskId());
            if (null != task)
            {
                return task.getStartTime();
            }
        }
        return null;
        
    }
    
    //获取验证流程最后完成时间
    private Date getDateForVerifySchedule(String scheduleId)
    {
        Date date = null;
        String hql = "FROM TestingSchedule t WHERE t.verifyTarget = :scheduleId";
        List<TestingSchedule> verifyScheduleList =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
        if (Collections3.isNotEmpty(verifyScheduleList))
        {
            for (TestingSchedule s : verifyScheduleList)
            {
                if (null == date)
                {
                    date = s.getEndTime();
                }
                else
                {
                    if (null != s.getEndTime())
                    {
                        if (s.getEndTime().after(date))
                        {
                            date = s.getEndTime();
                        }
                    }
                }
            }
        }
        return date;
    }
    
    // 根据order product semantic
    private OrderPlanTask getOrderPlanTaskByOrderProductId(String orderId, String productId, String semantic)
    {
        String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :taskSemantic";
        List<OrderPlanTask> list =
            baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, new String[] {"orderId", "productId", "taskSemantic"}, new Object[] {orderId, productId,
                semantic});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    private void addFiter(StringBuffer hql, List<String> names, List<Object> values, OtherReportFormSearcher request)
    {
        if (StringUtils.isNotEmpty(request.getProductName()))
        {
            hql.append(" AND op.product.name = :productName ");
            names.add("productName");
            values.add(request.getProductName());
        }
        if (StringUtils.isNotEmpty(request.getTestingMethod()))
        {
            hql.append(" AND EXISTS(select pt.id from ProductTestingMethod pt where pt.product.id = op.product.id and pt.testingMethod.id = :testingMethod )");
            names.add("testingMethod");
            values.add(request.getTestingMethod());
        }
        if (StringUtils.isNotEmpty(request.getOwnerId()))
        {
            hql.append(" AND s.ownerId =:ownerId");
            names.add("ownerId");
            values.add(request.getOwnerId());
        }
        if (StringUtils.isNotEmpty(request.getMarketingCenter()))
        {
            hql.append(" AND s.type.id =:orderType");
            names.add("orderType");
            values.add(request.getMarketingCenter());
        }
        if (StringUtils.isNotEmpty(request.getCreateTimeStart()))
        {
            hql.append(" AND s.submitTime >= STR_TO_DATE(:start,'%Y-%m-%d %H:%i:%s') ");
            names.add("start");
            values.add(request.getCreateTimeStart());
        }
        if (StringUtils.isNotEmpty(request.getCreateTimeEnd()))
        {
            hql.append(" AND s.submitTime <= STR_TO_DATE(:end,'%Y-%m-%d %H:%i:%s') ");
            names.add("end");
            values.add(request.getCreateTimeEnd() + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(request.getPlanTimeStart()))
        {
            hql.append(" AND date_add(s.submitTime, interval 5 day) >= :planTimeStart");
            if (StringUtils.isNotEmpty(request.getPlanTimeEnd()))
            {
                hql.append(" AND date_add(s.submitTime, interval 5 day) <= :planTimeEnd");
                names.add("planTimeEnd");
                values.add(request.getPlanTimeEnd() + " 23:59:59");
            }
            names.add("planTimeStart");
            values.add(request.getPlanTimeStart());
        }
        
        /*if (StringUtils.isNotEmpty(request.getActualTimeStart()))
        {
            hql.append(" AND EXISTS(select osm.id from OrderScheduleMonitor osm where osm.orderId = s.id and osm.actualFinishDate >= STR_TO_DATE(:actualTimeStart,'%Y-%m-%d %H:%i:%s')");
            if (StringUtils.isNotEmpty(request.getActualTimeEnd()))
            {
                hql.append(" and osm.actualFinishDate <= STR_TO_DATE(:actualTimeEnd,'%Y-%m-%d %H:%i:%s')");
                names.add("actualTimeEnd");
                values.add(request.getActualTimeEnd() + " 23:59:59");
            }
            hql.append(")");
            names.add("actualTimeStart");
            values.add(request.getActualTimeStart());
        }*/
    }
    
    @Override
    public List<TestingMethod> getTestingMethodListIncludeVerity()
    {
        String hql = "FROM TestingMethod t WHERE t.type = 1 or t.type = 3 ";
        List<TestingMethod> records = baseDaoSupport.find(TestingMethod.class, hql);
        return records;
    }
}
