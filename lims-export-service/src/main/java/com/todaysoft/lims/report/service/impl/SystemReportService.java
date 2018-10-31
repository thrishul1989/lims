package com.todaysoft.lims.report.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ContractReportSearcher;
import com.todaysoft.lims.report.dao.searcher.CustomerReportSearcher;
import com.todaysoft.lims.report.dao.searcher.CycleReportSearcher;
import com.todaysoft.lims.report.dao.searcher.SystemReportSearcher;
import com.todaysoft.lims.report.entity.ReportTaskRecord;
import com.todaysoft.lims.report.entity.system.DataArea;
import com.todaysoft.lims.report.entity.system.ReceivedSample;
import com.todaysoft.lims.report.entity.system.ReportSystemContractInfo;
import com.todaysoft.lims.report.entity.system.ReportSystemCycleInfo;
import com.todaysoft.lims.report.entity.system.ReportSystemOrderInfo;
import com.todaysoft.lims.report.entity.system.ReportSystemProductInfo;
import com.todaysoft.lims.report.entity.system.ReprotSystemCustomerInfo;
import com.todaysoft.lims.report.entity.system.TestingConcludingReport;
import com.todaysoft.lims.report.entity.system.TestingDataSend;
import com.todaysoft.lims.report.entity.system.TestingResamplingRecord;
import com.todaysoft.lims.report.entity.system.TestingSchedule;
import com.todaysoft.lims.report.model.OtherReportFormModel;
import com.todaysoft.lims.report.model.PoolingSheetVariables;
import com.todaysoft.lims.report.model.request.ContractSystemRequest;
import com.todaysoft.lims.report.model.request.CustomerSystemRequest;
import com.todaysoft.lims.report.model.request.CycleSystemRequest;
import com.todaysoft.lims.report.model.request.OrderSystemRequest;
import com.todaysoft.lims.report.service.IReportTaskRecordService;
import com.todaysoft.lims.report.service.ISystemReportService;
import com.todaysoft.lims.report.utils.DateUtil;
import com.todaysoft.lims.report.utils.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SystemReportService  implements ISystemReportService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IReportTaskRecordService reportTaskRecordService;

    @Override
    public Pagination<ReportSystemOrderInfo> getOrderInfoPagin(OrderSystemRequest request)
    {
        if(StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                String startDate = request.getStartTime()+" 00:00:00";
                String endDate = request.getEndTime()+" 23:59:59";
                
                baseDaoSupport.prepareCallReturn(" { call export_order_from_data(?,?,?,?,?,?,?,?,?,?,?,?,?) }",
                    request.getTaskId(),request.getOrderCode(),
                    request.getOrderType(),request.getTestingStatus(),
                    request.getPaymentStatus(),request.getCustomerId(),
                    request.getCreateName(), startDate,endDate,
                    request.getRecipientPhone(),request.getProductFlag(),request.getSampleFlag(),request.getInvoiceFlag());
               return getPagin(request);
            }
            else
            {
                return getPagin(request);
            }
        }
        return null;
    }
    
    private Pagination<ReportSystemOrderInfo> getPagin(OrderSystemRequest request)
    {
        SystemReportSearcher searcher = new SystemReportSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ReportSystemOrderInfo> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportSystemOrderInfo.class);
        if(null != paging && Collections3.isNotEmpty(paging.getRecords()))
        {
            List<ReportSystemOrderInfo> list =  paging.getRecords();
            for(ReportSystemOrderInfo info : list)
            {
                if(StringUtils.isNotEmpty(info.getOrigin()))
                {
                    String result = exchangeArea(info.getOrigin());
                    if (StringUtils.isNotEmpty(result))
                    {
                        DataArea target = baseDaoSupport.get(DataArea.class,result);
                        if (StringUtils.isNotEmpty(target))
                        {
                            info.setOrigin(target.getFullName());
                        }
                    }
                }
                List<ReportSystemProductInfo> productList = info.getProductList();
                if(Collections3.isNotEmpty(productList))
                {
                    for(ReportSystemProductInfo product : productList)
                    {
                        if(StringUtils.isNotEmpty(product.getLanes()))
                        {
                            String lanes = "";
                            List<String> sequenceCodes = Lists.newArrayList();
                            String[] jsonlans = product.getLanes().split("/");
                            if(jsonlans.length > 0)
                            {
                                for(String lane : jsonlans)
                                {
                                    PoolingSheetVariables variables = JsonUtils.asObject(lane, PoolingSheetVariables.class);
                                    if(null != variables)
                                    {
                                        if (StringUtils.isNotEmpty(variables.getPoolingCode()))
                                        {
                                            if (!sequenceCodes.contains(variables.getPoolingCode()))
                                            {
                                                sequenceCodes.add(variables.getPoolingCode());
                                            }
                                        }
                                    }
                                }
                            }
                            for(String s: sequenceCodes)
                            {
                                if (StringUtils.isEmpty(lanes))
                                {
                                    lanes = s;
                                }
                                else
                                {
                                    if (StringUtils.isNotEmpty(s))
                                    {
                                        lanes += "," + s;
                                    }
                                }
                            }
                            product.setLanes(lanes);
                        }
                    }
                }
            }
        }
        return paging;
    }
    
    private static String exchangeArea(String i)
    {
        String result = "1";
        String[] change = i.split(",");
        if (StringUtils.isNotEmpty(change))
        {
            result = change[change.length - 1];
        }
        return result;
    }

    @Override
    public Pagination<ReportSystemContractInfo> getContractInfoPagin(ContractSystemRequest request)
    {
        if(StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                
                baseDaoSupport.prepareCallReturn(" { call export_contract_from_data(?,?,?,?) }",
                    request.getTaskId(),request.getName(),request.getSignUser(),request.getMarketingCenter()
                    );
               return getContractPagin(request);
            }
            else
            {
                return getContractPagin(request);
            }
        }
        return null;
    }


    private Pagination<ReportSystemContractInfo> getContractPagin(ContractSystemRequest request)
    {
        ContractReportSearcher searcher = new ContractReportSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ReportSystemContractInfo> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportSystemContractInfo.class);
        return paging;
    }

    @Override
    public Pagination<OtherReportFormModel> getCycleInfoPagin(CycleSystemRequest request)
    {
        if(StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                //订单提交时间
                String startDate ="";
                String endDate = "";
                //计划完成时间
                String plaStartDate = "";
                String planEndDate = "";
                if(StringUtils.isNotEmpty(request.getCreateTimeStart()))
                {
                    startDate = request.getCreateTimeStart()+" 00:00:00";
                }
                if(StringUtils.isNotEmpty(request.getCreateTimeEnd()))
                {
                    endDate = request.getCreateTimeEnd()+" 23:59:59";
                }
                if(StringUtils.isNotEmpty(request.getPlanTimeStart()))
                {
                    plaStartDate = request.getPlanTimeStart()+" 00:00:00";
                }
                if(StringUtils.isNotEmpty(request.getPlanTimeEnd()))
                {
                    planEndDate = request.getPlanTimeEnd()+" 23:59:59";
                }
                baseDaoSupport.prepareCallReturn(" { call export_cycle_from_data(?,?,?,?,?,?,?) }",
                    request.getTaskId(),request.getOwnerId(),request.getMarketingCenter(),startDate,endDate,plaStartDate,planEndDate);
               return getCyclePagin(request);
            }
            else
            {
                return getCyclePagin(request);
            }
        }
        return null;
    }
    
    private Pagination<OtherReportFormModel> getCyclePagin(CycleSystemRequest request)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Pagination<OtherReportFormModel> modelPaging= new Pagination<OtherReportFormModel>();
        List<OtherReportFormModel> modelList = Lists.newArrayList();
        CycleReportSearcher searcher = new CycleReportSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ReportSystemCycleInfo> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportSystemCycleInfo.class);
        if(null != paging && Collections3.isNotEmpty(paging.getRecords()))
        {
            List<ReportSystemCycleInfo> list = paging.getRecords();
            for(ReportSystemCycleInfo info : list)
            {
                OtherReportFormModel model = new OtherReportFormModel();
                model.setOrderCode(info.getOrderCode());
                model.setMarketingCenter(info.getOrderType());
                model.setTestingStatus(info.getTestingStatus());
                model.setProductName(info.getProductName());
                model.setTestingMethods(info.getMethodName());
                model.setContractName(info.getContractName());
                //存的是原样本id info.getSampleCode();
                String sampleCodes = "";
                if(StringUtils.isNotEmpty(info.getSampleCode()))
                {
                    ReceivedSample rSample = baseDaoSupport.get(ReceivedSample.class, info.getSampleCode());
                    if(null != rSample)
                    {
                        sampleCodes = rSample.getSampleCode();
                    }
                    List<String> resendSampleList = Lists.newArrayList();
                    getResendSampleId(info.getSampleCode(),resendSampleList);
                    if(Collections3.isNotEmpty(resendSampleList))
                    {
                        for(String str : resendSampleList)
                        {
                            ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, str);
                            sampleCodes +="/"+receivedSample.getSampleCode();
                        }
                    }
                }
                model.setSampleCode(sampleCodes);
                model.setExamineeName(info.getExamineeName());
                model.setCustomerName(info.getCustomerName());
                model.setCompanyName(info.getCompanyName());
                model.setCreateName(info.getCreateName());
                model.setCycleValue(info.getTestingDuration());
                Date actualFinishDate = null;
                Date planFinishTime = null;
                if(null != info.getSubmitTime())
                {
                    model.setSubmitTime(dateFormat.format(info.getSubmitTime()));
                }
                if(null != info.getPlanFinishDate())
                {
                    planFinishTime = info.getPlanFinishDate();
                    model.setPlanFinishTime(dateFormat.format(info.getPlanFinishDate()));
                }
                TestingSchedule schedule = null;
                TestingDataSend dataSend = null;
                TestingConcludingReport conreport = null;
                if(StringUtils.isNotEmpty(info.getScheduleId()))
                {
                    schedule = baseDaoSupport.get(TestingSchedule.class, info.getScheduleId());
                }
                if(null != schedule)
                {
                    dataSend = getContractReportSendDate(schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId());
                }
                
                if("科技服务".equals(info.getOrderType()))
                {
                    if(null != schedule)
                    {
                        conreport = getReportConcludingDate(schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId());
                    }
                    if(null != conreport)
                    {
                        if(null != conreport.getUpdateTime())
                        {
                            actualFinishDate = conreport.getUpdateTime();
                            model.setActualFinishTime(dateFormat.format(conreport.getUpdateTime()));
                        }
                    }
                }
                else
                {
                    if(null != dataSend)
                    {
                        if(null != dataSend.getSendTime())
                        {
                            if(null != info.getReportEmailEndTime())
                            {
                                actualFinishDate = getMaxDate(dataSend.getSendTime(), info.getReportEmailEndTime());
                                model.setActualFinishTime(dateFormat.format(getMaxDate(dataSend.getSendTime(), info.getReportEmailEndTime())));
                            }
                            else
                            {
                                actualFinishDate = dataSend.getSendTime();
                                model.setActualFinishTime(dateFormat.format(dataSend.getSendTime()));
                            }
                        }
                        else
                        {
                            if(null != info.getReportEmailEndTime())
                            {
                                actualFinishDate = info.getReportEmailEndTime();
                                model.setActualFinishTime(dateFormat.format(info.getReportEmailEndTime()));
                            }
                        }
                    }
                    else
                    {
                        if(null != info.getReportEmailEndTime())
                        {
                            actualFinishDate = info.getReportEmailEndTime();
                            model.setActualFinishTime(dateFormat.format(info.getReportEmailEndTime()));
                        }
                    }
                }
                
                if(null != actualFinishDate)
                {
                    if(null != planFinishTime)
                    {
                        if(actualFinishDate.after(planFinishTime))
                        {
                            model.setIsOnTime("否");
                            model.setPostponeDay(DateUtil.getDaysOfTwoDate(planFinishTime,actualFinishDate).toString());
                        }
                        else
                        {
                            model.setIsOnTime("是");
                        }
                    }
                    if (null != info.getSubmitTime())
                    {
                        model.setActualFinishDay(DateUtil.getDaysOfTwoDate(info.getSubmitTime(), actualFinishDate).toString());
                    }
                }
                else
                {
                    if(null != planFinishTime)
                    {
                        if(new Date().after(planFinishTime))
                        {
                            model.setIsOnTime("否");
                        }
                    }
                }
                int resampleCount = 0;
                if(StringUtils.isNotEmpty(sampleCodes))
                {
                    resampleCount = sampleCodes.split("/").length-1;
                }
                model.setResampleCount(String.valueOf(resampleCount));
                model.setNgsseqCount(info.getNgsseqCount());
                model.setLane(info.getLane());
                if(null != info.getSubmitTime() && null != info.getSampleReceiveTime())
                {
                    model.setSampleReceivedDay(DateUtil.getDaysOfTwoDate(info.getSubmitTime(), info.getSampleReceiveTime()).toString());
                }
                if(null != info.getSubmitTime() && null != info.getPaymentCheckTime())
                {
                    model.setOrderPaymentDay(DateUtil.getDaysOfTwoDate(info.getSubmitTime(), info.getPaymentCheckTime()).toString());
                }
                if(null != info.getSampleReceiveTime() && null != info.getStartTime())
                {
                    model.setTestingStartDay(DateUtil.getDaysOfTwoDate(info.getSampleReceiveTime(), info.getStartTime()).toString());
                }
                if(null != info.getDnaStartTime() && null != info.getDnaEndTime())
                {
                    model.setDnaDay(DateUtil.getDaysOfTwoDate(info.getDnaStartTime(), info.getDnaEndTime()).toString());
                }
                Date dateForReport = null;
                if("NGS".equals(info.getMethodName()) || "CapNGS".equals(info.getMethodName()))
                {
                    if(null != info.getLibStartTime() && null != info.getLibEndTime())
                    {
                        model.setLibCreateDay(DateUtil.getDaysOfTwoDate(info.getLibStartTime(), info.getLibEndTime()).toString());
                    }
                    if(null != info.getLibcapStartTime() && null != info.getLibcapEndTime())
                    {
                        model.setLibCatchDay(DateUtil.getDaysOfTwoDate(info.getLibcapStartTime(), info.getLibcapEndTime()).toString());
                    }
                    if(null != info.getQtStartTime() && null != info.getQtEndTime())
                    {
                        model.setRqtDay(DateUtil.getDaysOfTwoDate(info.getQtStartTime(), info.getQtEndTime()).toString());
                    }
                    if(null != info.getSeqStartTime() && null != info.getSeqEndTime())
                    {
                        model.setNgsseqDay(DateUtil.getDaysOfTwoDate(info.getSeqStartTime(), info.getSeqEndTime()).toString());
                    }
                    if(null != info.getBioStartTime() && null != info.getBioEndTime())
                    {
                        model.setBiologyDay(DateUtil.getDaysOfTwoDate(info.getBioStartTime(), info.getBioEndTime()).toString());
                    }
                    if(null != info.getTecStartTime() && null != info.getTecEndTime())
                    {
                        model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getTecStartTime(), info.getTecEndTime()).toString());
                    }
                    
                    if(null != info.getVerifyStartTime() && null != info.getVerifyEndTime())
                    {
                        model.setVerifyDay(DateUtil.getDaysOfTwoDate(info.getVerifyStartTime(), info.getVerifyEndTime()).toString());
                    }
                    if(null != info.getVerifyEndTime())
                    {
                        dateForReport = info.getVerifyEndTime();
                    }
                }
                else
                {
                    if("荧光检测".equals(info.getMethodName()))
                    {
                        if(null != info.getTestStartTime() && null != info.getFluoStartTime())
                        {
                            model.setTestDay(DateUtil.getDaysOfTwoDate(info.getTestStartTime(), info.getFluoStartTime()).toString());
                        }
                        if(null != info.getFluoStartTime() && null != info.getFluoEndTime())
                        {
                            model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getFluoStartTime(), info.getFluoEndTime()).toString());
                        }
                        if(null != info.getFluoEndTime())
                        {
                            dateForReport = info.getFluoEndTime();
                        }
                    }
                    if("Sanger检测".equals(info.getMethodName()))
                    {
                        if(null != info.getTestStartTime() && null != info.getSangerStartTime())
                        {
                            model.setTestDay(DateUtil.getDaysOfTwoDate(info.getTestStartTime(), info.getSangerStartTime()).toString());
                        }
                        if(null != info.getSangerStartTime() && null != info.getSangerEndTime())
                        {
                            model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getSangerStartTime(), info.getSangerEndTime()).toString());
                        }
                        if(null != info.getSangerEndTime())
                        {
                            dateForReport = info.getSangerEndTime();
                        }
                    }
                    if("MLPA检测".equals(info.getMethodName()))
                    {
                        if(null != info.getTestStartTime() && null != info.getMlpaStartTime())
                        {
                            model.setTestDay(DateUtil.getDaysOfTwoDate(info.getTestStartTime(), info.getMlpaStartTime()).toString());
                        }
                        if(null != info.getMlpaStartTime() && null != info.getMlpaEndTime())
                        {
                            model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getMlpaStartTime(), info.getMlpaEndTime()).toString());
                        }
                        if(null != info.getMlpaEndTime())
                        {
                            dateForReport = info.getMlpaEndTime();
                        }
                    }
                    if("动态突变".equals(info.getMethodName()))
                    {
                        if(null != info.getTestStartTime() && null != info.getDtStartTime())
                        {
                            model.setTestDay(DateUtil.getDaysOfTwoDate(info.getTestStartTime(), info.getDtStartTime()).toString());
                        }
                        if(null != info.getDtStartTime() && null != info.getDtEndTime())
                        {
                            model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getDtStartTime(), info.getDtEndTime()).toString());
                        }
                        if(null != info.getDtEndTime())
                        {
                            dateForReport = info.getDtEndTime();
                        }
                    }
                    if("多重PCR检测".equals(info.getMethodName()) || "Long-PCR检测".equals(info.getMethodName()))
                    {
                        if(null != info.getTestStartTime() && null != info.getTecStartTime())
                        {
                            model.setTestDay(DateUtil.getDaysOfTwoDate(info.getTestStartTime(), info.getTecStartTime()).toString());
                        }
                        if(null != info.getTecStartTime() && null != info.getTecEndTime())
                        {
                            model.setTechnicalDay(DateUtil.getDaysOfTwoDate(info.getTecStartTime(), info.getTecEndTime()).toString());
                        }
                        if(null != info.getTecEndTime())
                        {
                            dateForReport = info.getTecEndTime();
                        }
                    }
                }
                if(null != dateForReport && null != info.getReportTime())
                {
                    model.setReportGenerateDay(DateUtil.getDaysOfTwoDate(dateForReport, info.getReportTime()).toString());
                }
                if(null != info.getOneStartTime() && null != info.getTwoEndTime())
                {
                    model.setReportCheckDay(DateUtil.getDaysOfTwoDate(info.getOneStartTime(), info.getTwoEndTime()).toString());
                }
                //报告寄送
                if(null != info.getReportEmailStartTime() && null != info.getReportEmailEndTime())
                {
                    model.setReportSendDay(DateUtil.getDaysOfTwoDate(info.getReportEmailStartTime(), info.getReportEmailEndTime()).toString());
                }
                modelList.add(model);
            }
        }
        modelPaging.setPageSize(paging.getPageSize());
        modelPaging.setPageNo(paging.getPageNo());
        modelPaging.setTotalCount(paging.getTotalCount());
        modelPaging.setRecords(modelList);
        return modelPaging;
    }
    
    private void getResendSampleId(String abnormalSampleId,List<String> list)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.abnormalSampleId = :abnormalSampleId AND t.resendSampleStatus = :resendSampleStatus";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[]{"abnormalSampleId","resendSampleStatus"}, new Object[]{abnormalSampleId,TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if(Collections3.isNotEmpty(records))
        {
            String s = records.get(0).getResendSampleId();
            list.add(s);
            getResendSampleId(s,list);
        }
    }
    
    //合同订单报告发送时间
    private TestingDataSend getContractReportSendDate(String orderId,String productId,String methodId,String sampleId)
    {
        
        String hql = "FROM TestingDataSend t WHERE t.orderId = :orderId AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId";
        List<TestingDataSend> list = baseDaoSupport.findByNamedParam(TestingDataSend.class, hql, new String[] {"orderId","productId","methodId","sampleId"}, new Object[] {orderId,productId,methodId,sampleId});
        if(Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    //科研报告处理时间
    private TestingConcludingReport getReportConcludingDate(String orderId,String productId,String methodId,String sampleId)
    {
        String hql = "FROM TestingConcludingReport t WHERE t.orderId = :orderId AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId";
        List<TestingConcludingReport> list = baseDaoSupport.findByNamedParam(TestingConcludingReport.class, hql, new String[] {"orderId","productId","methodId","sampleId"}, new Object[] {orderId,productId,methodId,sampleId});
        if(Collections3.isNotEmpty(list))
        {
           return list.get(0);
        }
        return null;
    }
    
  //比较时间
    private Date getMaxDate(Date date1,Date date2)
    {
        if(date2.after(date1))
        {
            return date2;
        }
        return date1;
    }

    @Override
    public Pagination<ReprotSystemCustomerInfo> getCustomerInfoPagin(CustomerSystemRequest request)
    {
        if(StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);

                baseDaoSupport.prepareCallReturn(" { call export_customer_from_data(?,?,?,?) }",
                        request.getTaskId(),request.getCustomerName(),
                        request.getCompanyName(),request.getStatus()
                );

                return getCustomerPagin(request);
            }
            else
            {
                return getCustomerPagin(request);
            }
        }
        return null;
    }

    private Pagination<ReprotSystemCustomerInfo> getCustomerPagin(CustomerSystemRequest request)
    {
        CustomerReportSearcher searcher = new CustomerReportSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ReprotSystemCustomerInfo> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(),ReprotSystemCustomerInfo.class);
        return paging;
    }

    @Override
    @Transactional
    public void truncateSystemReport()
    {
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_ORDER_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_SAMPLE_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_PRODUCT_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_INVOICE_INFO");
        System.out.println("------订单报表清空完成！");
        
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CONTRACT_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CONTRACT_SAMPLE_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CONTRACT_PRODUCT_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CONTRACT_CHANGE_INFO");
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CONTRACT_CUSTOMER_INFO");
        System.out.println("------合同报表清空完成！");
        
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CUSTOMER_INFO");
        System.out.println("------客户报表清空完成！");
        
        baseDaoSupport.truncateBySql("truncate table REPORT_SYSTEM_CYCLE_INFO");
        System.out.println("------其他报表清空完成！");
    }
    
}
