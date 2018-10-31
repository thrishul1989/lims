package com.todaysoft.lims.report.service.impl;


import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ExperimentReportSearcher;
import com.todaysoft.lims.report.dao.searcher.SheetSampleSearcher;
import com.todaysoft.lims.report.dao.searcher.SheetSampleSuccessRateSearcher;
import com.todaysoft.lims.report.dao.searcher.TaskFailSolveSearcher;
import com.todaysoft.lims.report.entity.*;
import com.todaysoft.lims.report.model.*;
import com.todaysoft.lims.report.model.request.SampleExperimentRequest;
import com.todaysoft.lims.report.service.IReportTaskRecordService;
import com.todaysoft.lims.report.utils.DateUtil;
import com.todaysoft.lims.report.utils.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.report.service.IExperimentReportService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExperimentReportService implements IExperimentReportService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Autowired
    private IReportTaskRecordService reportTaskRecordService;

    @Override
    public OrderSamplePageModel paging(SampleExperimentRequest request) {
        if(StringUtils.isNotEmpty(request.getReportTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getReportTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getReportTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                //执行存储过程 组装数据表 同一个任务ID只执行一次
                //CALL orderSampleTemp(1,11122,'2017-06-05 11:23:06','2017-11-05 11:23:06');
                String startDate = request.getStart()+" 00:00:00";
                String endDate = request.getEnd()+" 23:59:59";
                List<String> s = baseDaoSupport.prepareCallReturn(" { call orderSampleTemp(?,?,?,?) }",request.getMarketingCenter().toString(),request.getReportTaskId(),startDate,endDate);
                if(Collections3.isNotEmpty(s))//如果执行完就返回一个值 这个时候去查询结果即可
                {
                    return getResultMap(request);
                }
            }else{
                //不需要再次执行存储过程 直接查询结果表
                return getResultMap(request);
            }
        }
        return null;
    }
    public OrderSamplePageModel getResultMap(SampleExperimentRequest request)
    {
        ExperimentReportSearcher searcher  = new ExperimentReportSearcher();
        searcher.setReportTaskId(request.getReportTaskId());
        //不需要再次执行存储过程 直接查询结果表
        Pagination<ReportOrderSampleInfo> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportOrderSampleInfo.class);
        List<String[]> records = wrap(paging);
        OrderSamplePageModel model = new OrderSamplePageModel(paging,records);
        return model;
    }

    @Override
    public OrderSamplePageModel sheetSamplePaging(SampleExperimentRequest request) {
        if(StringUtils.isNotEmpty(request.getReportTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getReportTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getReportTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                //执行存储过程 组装数据表 同一个任务ID只执行一次
                String startDate = request.getStart()+" 00:00:00";
                String endDate = request.getEnd()+" 23:59:59";
                String semanticArr[] = StringUtils.split(request.getTaskSemantic(),",");
                for(int i =0;i<semanticArr.length;i++)
                {
                    baseDaoSupport.prepareCallReturn(" { call sheetSampleTemp(?,?,?,?) }",semanticArr[i],request.getReportTaskId(),startDate,endDate);
                }
                return getSheetSampleResultMap(request);
            }else{
                //不需要再次执行存储过程 直接查询结果表
                return getSheetSampleResultMap(request);
            }
        }
        return null;
    }

    private OrderSamplePageModel getSheetSampleResultMap(SampleExperimentRequest request) {
        SheetSampleSearcher searcher  = new SheetSampleSearcher();
        searcher.setReportTaskId(request.getReportTaskId());
        //不需要再次执行存储过程 直接查询结果表
        Pagination<ReportSheetSample> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportSheetSample.class);
        List<String[]> records = wrapSheetSample(paging);
        OrderSamplePageModel model = new OrderSamplePageModel(paging,records);
        return model;
    }

    @Override
    public OrderSamplePageModel sheetSampleSuccessRatePaging(SampleExperimentRequest request) {
        if(StringUtils.isNotEmpty(request.getReportTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getReportTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getReportTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                //执行存储过程 组装数据表 同一个任务ID只执行一次
                String startDate = request.getStart()+" 00:00:00";
                String endDate = request.getEnd()+" 23:59:59";
                String semanticArr[] = StringUtils.split(request.getTaskSemantic(),",");
                for(int i =0;i<semanticArr.length;i++)
                {
                    baseDaoSupport.prepareCallReturn(" { call sheetSampleSuccessRateTemp(?,?,?,?) }",semanticArr[i],request.getReportTaskId(),startDate,endDate);
                }
                return getSheetSampleSuccessRateResultMap(request);
            }else{
                //不需要再次执行存储过程 直接查询结果表
                return getSheetSampleSuccessRateResultMap(request);
            }
        }
        return null;
    }

    private OrderSamplePageModel getSheetSampleSuccessRateResultMap(SampleExperimentRequest request) {
        SheetSampleSuccessRateSearcher searcher  = new SheetSampleSuccessRateSearcher();
        searcher.setReportTaskId(request.getReportTaskId());
        //不需要再次执行存储过程 直接查询结果表
        Pagination<ReportSheetSampleSuccessRate> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportSheetSampleSuccessRate.class);
        List<String[]> records = wrapSheetSampleSuccessRate(paging);
        OrderSamplePageModel model = new OrderSamplePageModel(paging,records);
        return model;
    }

    @Override
    public OrderSamplePageModel taskFailSolvePaging(SampleExperimentRequest request) {
        if(StringUtils.isNotEmpty(request.getReportTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getReportTaskId());
            if(null == record)
            {
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getReportTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                //执行存储过程 组装数据表 同一个任务ID只执行一次
                String startDate = request.getStart()+" 00:00:00";
                String endDate = request.getEnd()+" 23:59:59";
                String semanticArr[] = StringUtils.split(request.getTaskSemantic(),",");
                for(int i =0;i<semanticArr.length;i++)
                {
                    baseDaoSupport.prepareCallReturn(" { call taskFailSolve(?,?,?,?) }",semanticArr[i],request.getReportTaskId(),startDate,endDate);
                }
                return getTaskFailSolveResultMap(request);
            }else{
                //不需要再次执行存储过程 直接查询结果表
                return getTaskFailSolveResultMap(request);
            }
        }
        return null;
    }

    private OrderSamplePageModel getTaskFailSolveResultMap(SampleExperimentRequest request) {
        TaskFailSolveSearcher searcher  = new TaskFailSolveSearcher();
        searcher.setReportTaskId(request.getReportTaskId());
        //不需要再次执行存储过程 直接查询结果表
        Pagination<ReportTaskFailSolve> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ReportTaskFailSolve.class);
        List<String[]> records = wrapTaskFailSolve(paging);
        OrderSamplePageModel model = new OrderSamplePageModel(paging,records);
        return model;
    }

    private List<String[]> wrapTaskFailSolve(Pagination<ReportTaskFailSolve> paging) {
        List<String[]> results = Lists.newArrayList();
        if(null != paging && Collections3.isNotEmpty(paging.getRecords()))
        {
            List<ReportTaskFailSolve> sheetSampleList = paging.getRecords();
            for(ReportTaskFailSolve model:sheetSampleList)
            {
                String result[] = new String[12];
                result[0]=model.getTaskName();
                result[1]=model.getOrderCode();
                result[2]=model.getProductName();
                result[3]=model.getMethodName();
                result[4]=model.getReceivedSampleName();
                result[5]=model.getReceivedSampleCode();
                String remark = model.getTaskResultRemark();
                if(StringUtils.isEmpty(remark) && StringUtils.isNotEmpty(model.getTaskDetail()))
                {
                    Map map = JsonUtils.asObject(model.getTaskDetail(),Map.class);
                    if(null != map && map.containsKey("dispose"))
                    {
                        remark = StringUtils.isNotEmpty(map.get("dispose"))?map.get("dispose").toString():"";
                    }
                }
                result[6]=remark;
                result[7]=StringUtils.isNotEmpty(model.getEndTime())?DateUtils.formatDate(model.getEndTime(), "yyyy-MM-dd HH:mm:ss"):"";
                if("2".equals(model.getTaskResult()))//2-直接解决的异常任务  1-异常上报
                {
                    result[8]=model.getSubmitName();
                    result[9]=StringUtils.isNotEmpty(model.getSubmitTime())?DateUtils.formatDate(model.getSubmitTime(), "yyyy-MM-dd HH:mm:ss"):"";
                    String strategy = getStrategyByTaskSemantic(model.getTaskSemantic(),"",model.getTaskDetail());
                    result[10]=strategy;
                    result[11]=remark;
                }else{
                    result[8]=model.getSolveName();
                    result[9]=StringUtils.isNotEmpty(model.getSolveTime())?DateUtils.formatDate(model.getSolveTime(), "yyyy-MM-dd HH:mm:ss"):"";
                    result[10]=model.getSolveStrage();
                    result[11]=model.getSolveRemark();
                }
                results.add(result);
            }
        }
        return results;
    }

    private List<String[]> wrapSheetSampleSuccessRate(Pagination<ReportSheetSampleSuccessRate> paging) {
        List<String[]> results = Lists.newArrayList();
        if(null != paging && Collections3.isNotEmpty(paging.getRecords()))
        {
            List<ReportSheetSampleSuccessRate> sheetSampleList = paging.getRecords();
            for(ReportSheetSampleSuccessRate model:sheetSampleList)
            {
                String result[] = new String[9];
                result[0]=model.getTaskName();
                result[1]=model.getSheetCode();
                result[2]=model.getAssignName();
                result[3]=StringUtils.isNotEmpty(model.getAssignDate())?DateUtils.formatDate(model.getAssignDate(), "yyyy-MM-dd HH:mm:ss"):"";
                result[4]=model.getTestorName();
                result[5]=StringUtils.isNotEmpty(model.getCompletedDate())?DateUtils.formatDate(model.getCompletedDate(), "yyyy-MM-dd HH:mm:ss"):"";
                result[6]=StringUtils.toStr(model.getSampleNum());
                if(StringUtils.isNotEmpty(model.getCompletedDate()))
                {
                    Double days = DateUtil.getDaysOfTwoDate(model.getAssignDate(),model.getCompletedDate());
                    result[7]= StringUtils.isNotEmpty(days)?days.toString()+"":"";
                }
                Double rate = new Double(0);
                if(null != model.getTotalTaskNum())
                {
                    rate = DateUtil.getDivByTwoDouble(new Double(model.getSuccessTaskNum()*100),new Double(model.getTotalTaskNum()));
                }
                result[8]= String.valueOf(rate);
                results.add(result);
            }
        }
        return results;
    }

    private List<String[]> wrapSheetSample(Pagination<ReportSheetSample> paging) {
        List<String[]> results = Lists.newArrayList();
        if(null != paging && Collections3.isNotEmpty(paging.getRecords()))
        {
            List<ReportSheetSample> sheetSampleList = paging.getRecords();
            for(ReportSheetSample model:sheetSampleList)
            {
                String result[] = new String[9];
                result[0]=model.getTaskName();
                result[1]=model.getSheetCode();
                result[2]=model.getAssignName();
                result[3]=StringUtils.isNotEmpty(model.getAssignDate())?DateUtils.formatDate(model.getAssignDate(), "yyyy-MM-dd HH:mm:ss"):"";
                result[4]=model.getTestorName();
                result[5]=StringUtils.isNotEmpty(model.getCompletedDate())?DateUtils.formatDate(model.getCompletedDate(), "yyyy-MM-dd HH:mm:ss"):"";
                result[6]=StringUtils.toStr(model.getSampleNum());
                String kitName = "";
                if(StringUtils.isNotEmpty(model.getVariables()))
                {
                    kitName = getReagentNameBySheet(model.getSemantic(),model.getVariables());
                }
                result[7]=kitName;
                if(StringUtils.isNotEmpty(model.getCompletedDate()))
                {
                    Double days = DateUtil.getDaysOfTwoDate(model.getAssignDate(),model.getCompletedDate());
                    result[8]= StringUtils.isNotEmpty(days)?days.toString()+"":"";
                }
                results.add(result);
            }
        }
        return results;
    }

    private List<String[]> wrap(Pagination<ReportOrderSampleInfo> paging) {
        List<String[]> results = Lists.newArrayList();
        if(null != paging && Collections3.isNotEmpty(paging.getRecords())){
            List<ReportOrderSampleInfo> reportOrderList = paging.getRecords();
            for(ReportOrderSampleInfo model:reportOrderList)
            {
                String[] modelArr = new String[41];
                modelArr[0]=StringUtils.isNotEmpty(model.getOrderCode())?model.getOrderCode():"";
                modelArr[1]=StringUtils.isNotEmpty(model.getSampleCode())?model.getSampleCode():"";
                modelArr[2]=StringUtils.isNotEmpty(model.getProductName())?model.getProductName():"";
                modelArr[3]=StringUtils.isNotEmpty(model.getContractName())?model.getContractName():"";
                modelArr[4]=StringUtils.isNotEmpty(model.getMarkerCenter())?model.getMarkerCenter():"";
                modelArr[5]=StringUtils.isNotEmpty(model.getInspectName())?model.getInspectName():"";
                modelArr[6]=StringUtils.isNotEmpty(model.getCustomerName())?model.getCustomerName():"";
                modelArr[7]=StringUtils.isNotEmpty(model.getCustomerCompany())?model.getCustomerCompany():"";
                modelArr[8]=StringUtils.isNotEmpty(model.getCreatorName())?model.getCreatorName():"";
                modelArr[9]=StringUtils.isNotEmpty(model.getLsLocation())?model.getLsLocation():"";
                modelArr[10]=StringUtils.isNotEmpty(model.getTsLocation())?model.getTsLocation():"";
                List<ReportOrderDna> dnaList = model.getDnaList();
                if(Collections3.isNotEmpty(dnaList))
                {
                    for(ReportOrderDna dnaSample:dnaList)
                    {
                        String[] dnaSampleArr = new String[41];
                        dnaSampleArr=modelArr.clone();
                        dnaSampleArr[11]=StringUtils.isNotEmpty(dnaSample.getDnaSheetCode())?dnaSample.getDnaSheetCode():"";
                        dnaSampleArr[12]=StringUtils.isNotEmpty(dnaSample.getDnaAssignName())?dnaSample.getDnaAssignName():"";
                        dnaSampleArr[13]=StringUtils.isNotEmpty(dnaSample.getDnaAssignDate())? DateUtils.formatDate(dnaSample.getDnaAssignDate(), "yyyy-MM-dd HH:mm:ss"):"";
                        dnaSampleArr[14]=StringUtils.isNotEmpty(dnaSample.getDnaTestorName())?dnaSample.getDnaTestorName():"";
                        dnaSampleArr[15]=StringUtils.isNotEmpty(dnaSample.getDnaCompleteDate())?DateUtils.formatDate(dnaSample.getDnaCompleteDate(), "yyyy-MM-dd HH:mm:ss"):"";
                        dnaSampleArr[16]=StringUtils.isNotEmpty(dnaSample.getDnaSampleCode())?dnaSample.getDnaSampleCode():"";
                        if(StringUtils.isNotEmpty(dnaSample.getAttribution()))
                        {
                            DNAAttributes dnaAttributes = JsonUtils.asObject(dnaSample.getAttribution(),DNAAttributes.class);
                            if(null != dnaAttributes )
                            {
                                dnaSampleArr[17]=StringUtils.isNotEmpty(dnaAttributes.getConcn())?dnaAttributes.getConcn().toString():"";
                                dnaSampleArr[18]=StringUtils.isNotEmpty(dnaAttributes.getRatio2628())?dnaAttributes.getRatio2628().toString():"";
                                dnaSampleArr[19]=StringUtils.isNotEmpty(dnaAttributes.getRatio2623())?dnaAttributes.getRatio2623().toString():"";
                                dnaSampleArr[20]=StringUtils.isNotEmpty(dnaAttributes.getVolume())?dnaAttributes.getVolume().toString():"";
                                dnaSampleArr[21]=StringUtils.isNotEmpty(dnaAttributes.getQualityLevel())?dnaAttributes.getQualityLevel():"";
                            }
                        }
                        if(StringUtils.isNotEmpty(dnaSample.getReagentVariables()))
                        {
                            DNAQcSheetVariables dnaQcSheetVariables = JsonUtils.asObject(dnaSample.getReagentVariables(),DNAQcSheetVariables.class);
                            if(null != dnaQcSheetVariables)
                            {
                                String reagentId = dnaQcSheetVariables.getQualityControlReagentKitId();
                                ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class,reagentId);
                                dnaSampleArr[22]=StringUtils.isNotEmpty(reagentKit.getName())?reagentKit.getName():"";
                            }
                        }
                        if(StringUtils.isNotEmpty(dnaSample.getQualification()))
                        {
                            if("0".equals(dnaSample.getQualification()))
                            {
                                dnaSampleArr[23] = "合格";
                            }else{
                                dnaSampleArr[23] = "不合格";
                            }
                            dnaSampleArr[24]=StringUtils.isNotEmpty(dnaSample.getRemark())?dnaSample.getRemark():"";
                        }
                        dnaSampleArr[25]=StringUtils.isNotEmpty(dnaSample.getDnaLocation())?dnaSample.getDnaLocation():"";
                        List<ReportOrderLib> libList = dnaSample.getLibList();
                        if(Collections3.isNotEmpty(libList))
                        {
                            for(ReportOrderLib libSample:libList)
                            {
                                String[] libSampleArr = new String[41];
                                libSampleArr=dnaSampleArr.clone();
                                libSampleArr[26]=StringUtils.isNotEmpty(libSample.getLibSheetCode())?libSample.getLibSheetCode():"";
                                libSampleArr[27]=StringUtils.isNotEmpty(libSample.getLibAssignName())?libSample.getLibAssignName():"";
                                libSampleArr[28]=StringUtils.isNotEmpty(libSample.getLibAssignDate())? DateUtils.formatDate(libSample.getLibAssignDate(), "yyyy-MM-dd HH:mm:ss"):"";
                                libSampleArr[29]=StringUtils.isNotEmpty(libSample.getLibTestorName())?libSample.getLibTestorName():"";
                                libSampleArr[30]=StringUtils.isNotEmpty(libSample.getLibCompleteDate())?DateUtils.formatDate(libSample.getLibCompleteDate(), "yyyy-MM-dd HH:mm:ss"):"";
                                libSampleArr[31]=StringUtils.isNotEmpty(libSample.getLibSampleCode())?libSample.getLibSampleCode():"";
                                if(StringUtils.isNotEmpty(libSample.getAttribution()))
                                {
                                    LibraryAttributes libAttributes = JsonUtils.asObject(libSample.getAttribution(),LibraryAttributes.class);
                                    if(null != libAttributes )
                                    {
                                         libSampleArr[32]=StringUtils.isNotEmpty(libAttributes.getConcn())?libAttributes.getConcn().toString():"";
                                         libSampleArr[33]=StringUtils.isNotEmpty(libAttributes.getRatio2628())?libAttributes.getRatio2628().toString():"";
                                         libSampleArr[34]=StringUtils.isNotEmpty(libAttributes.getRatio2623())?libAttributes.getRatio2623().toString():"";
                                         libSampleArr[35]=StringUtils.isNotEmpty(libAttributes.getVolume())?libAttributes.getVolume().toString():"";
                                         libSampleArr[36]=StringUtils.isNotEmpty(libAttributes.getQualityLevel())?libAttributes.getQualityLevel():"";
                                    }
                                }
                                if(StringUtils.isNotEmpty(libSample.getReagentVariables()))
                                {
                                    LibraryQcSheetVariables libQcSheetVariables = JsonUtils.asObject(libSample.getReagentVariables(),LibraryQcSheetVariables.class);
                                    if(null != libQcSheetVariables)
                                    {
                                        String reagentId = libQcSheetVariables.getQualityControlReagentKitId();
                                        ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class,reagentId);
                                        libSampleArr[37]=StringUtils.isNotEmpty(reagentKit.getName())?reagentKit.getName():"";
                                    }
                                }
                                if(StringUtils.isNotEmpty(libSample.getQualification()))
                                {
                                    if("0".equals(libSample.getQualification()))
                                    {
                                        libSampleArr[38] = "合格";
                                    }else{
                                        libSampleArr[38] = "不合格";
                                    }
                                    libSampleArr[39]=StringUtils.isNotEmpty(libSample.getRemark())?libSample.getRemark():"";
                                }
                                libSampleArr[40]=StringUtils.isNotEmpty(libSample.getLibLocation())?libSample.getLibLocation():"";
                                results.add(libSampleArr);
                            }
                        }else{
                            results.add(dnaSampleArr);
                        }
                    }
                }else{
                    results.add(modelArr);
                }
            }
        }
        return results;
    }

    public String getReagentNameBySheet(String semantic, String variables)
    {
        String reagentKitId="";
        Map map = JsonUtils.asObject(variables,Map.class);
        switch (semantic)
        {
            case TaskSemantic.DNA_EXTRACT:
                reagentKitId = null!=map.get("extractReagentKitId")?map.get("extractReagentKitId").toString():"";
                break;
            case TaskSemantic.DNA_QC:
                reagentKitId = null!=map.get("qualityControlReagentKitId")?map.get("qualityControlReagentKitId").toString():"";
                break;
            case TaskSemantic.CDNA_EXTRACT:
                reagentKitId = null!=map.get("extractReagentKitId")?map.get("extractReagentKitId").toString():"";
                break;
            case TaskSemantic.CDNA_QC:
                reagentKitId = null!=map.get("qualityControlReagentKitId")?map.get("qualityControlReagentKitId").toString():"";
                break;
            case TaskSemantic.LIBRARY_CRE:
                reagentKitId = null!=map.get("createReagentKitId")?map.get("createReagentKitId").toString():"";
                break;
            case TaskSemantic.LIBRARY_QC:
                reagentKitId = null!=map.get("qualityControlReagentKitId")?map.get("qualityControlReagentKitId").toString():"";
                break;
            case TaskSemantic.LIBRARY_CAP:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.RQT:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.QT:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.NGS_SEQ:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.DT:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.LONG_PCR:
                reagentKitId = null!=map.get("pcrReagentKitId")?map.get("pcrReagentKitId").toString():"";
                break;
            case TaskSemantic.LONG_CRE:
                reagentKitId = null!=map.get("creReagentKitId")?map.get("creReagentKitId").toString():"";
                break;
            case TaskSemantic.LONG_QC:
                reagentKitId = null!=map.get("qcReagentKitId")?map.get("qcReagentKitId").toString():"";
                break;
            case TaskSemantic.MULTI_PCR:
                reagentKitId = null!=map.get("pcrReagentKitId")?map.get("pcrReagentKitId").toString():"";
                break;
            case TaskSemantic.MULTIPCR_QC:
                reagentKitId = null!=map.get("qcReagentKitId")?map.get("qcReagentKitId").toString():"";
                break;
            case TaskSemantic.SANGER_PCR_ONE:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.SANGER_PCR_TWO:
                reagentKitId = null!=map.get("secondPcrReagentKitId")?map.get("secondPcrReagentKitId").toString():"";
                break;
            case TaskSemantic.FLUO_TEST:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.PCR_ONE:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            case TaskSemantic.PCR_TWO:
                reagentKitId = null!=map.get("secondPcrReagentKitId")?map.get("secondPcrReagentKitId").toString():"";
                break;
            case TaskSemantic.PCR_NGS:
                reagentKitId = null!=map.get("reagentKitId")?map.get("reagentKitId").toString():"";
                break;
            default:
                break;
        }
        String result = "";
        if(StringUtils.isNotEmpty(reagentKitId))
        {
            ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class,reagentKitId);
            if(null != reagentKit)
            {
                result = reagentKit.getName();
            }
        }
        return result;
    }

    public String getStrategyByTaskSemantic(String semantic,String resultStrategy,String details) {
        String result = "";
        switch (semantic){
            case TaskSemantic.DNA_QC:
                result="重新提取";
                break;
            case TaskSemantic.CDNA_QC:
                result="重新提取";
                break;
            case TaskSemantic.LIBRARY_QC:
                result="重新建库";
                break;
            case TaskSemantic.LIBRARY_CAP:
                result="重新捕获";
                break;
            case TaskSemantic.DT_DATA_ANALYSIS:
                result="重新实验";
                break;
            case TaskSemantic.MLPA_DATA_ANALYSIS:
                if("2".equals(resultStrategy))
                {
                    result="重新提取DNA/CDNA(原始样本为DNA/CDNA则重做质检)";
                }else if("3".equals(resultStrategy))
                {
                    result="重新实验";
                }
                break;
            case TaskSemantic.LONG_QC:
                result="重新实验";
                break;
            case TaskSemantic.MULTIPCR_QC:
                result="重新实验";
                break;
            case TaskSemantic.PRIMER_DESIGN:
                result=details.replace("\"", "");
                break;
            case TaskSemantic.PCR_ONE:
                result=details.replace("\"", "");
                break;
            case TaskSemantic.DATA_ANALYSIS:
                if(StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details,Map.class);
                    if(null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose"))?map.get("dispose").toString():"";
                    }
                }
                break;
            case TaskSemantic.SANGER_PCR_ONE:
                result=details.replace("\"", "");
                break;
            case TaskSemantic.SANGER_DATA_ANALYSIS:
                if(StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details,Map.class);
                    if(null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose"))?map.get("dispose").toString():"";
                    }
                }
                break;
            case TaskSemantic.FLUO_ANALYSE:
                result="重新实验";
                break;
            case TaskSemantic.QPCR:
                result="重新实验";
                break;
            case TaskSemantic.PCR_NGS:
                result=details.replace("\"", "");
                break;
            case TaskSemantic.PCR_NGS_DATA_ANALYSIS:
                if(StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details,Map.class);
                    if(null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose"))?map.get("dispose").toString():"";
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }
}
