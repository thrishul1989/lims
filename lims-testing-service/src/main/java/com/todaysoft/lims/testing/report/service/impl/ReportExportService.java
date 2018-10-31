package com.todaysoft.lims.testing.report.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mysql.fabric.xmlrpc.base.Array;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.Customer;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderExamineeDiagnosis;
import com.todaysoft.lims.testing.base.entity.OrderExamineeGene;
import com.todaysoft.lims.testing.base.entity.OrderExamineePhenotype;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.OrderSampleView;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.report.ReportExportDetectionResult;
import com.todaysoft.lims.testing.base.entity.report.ReportExportMethodColumnConfig;
import com.todaysoft.lims.testing.base.entity.report.ReportExportSampleBaseInfo;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitTaskModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.report.model.MetadataSample;
import com.todaysoft.lims.testing.report.model.OrderSampleDetails;
import com.todaysoft.lims.testing.report.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.testing.report.request.ReportExportDetectionResultInfoRequest;
import com.todaysoft.lims.testing.report.service.IOrderService;
import com.todaysoft.lims.testing.report.service.IReportExportService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportExportService implements IReportExportService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private RestTemplate template;
    
    @Override
    @Transactional
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfo(GetReportExportSampleBaseInfoRequest request)
    {
        deleteReportExportMutationInfoByTaskId(request.getTaskId());
        TestingReport testingReport= getTestingReportById(request.getTaskId());
        OrderSampleView orderSampleView=getOrderSampleView(testingReport.getSampleCode());
        Order order= orderService.getOrderById(orderSampleView.getOrderId());
        if(order!=null) {
            ReportExportSampleBaseInfo reportExportSampleBaseInfo= convertToReportExportSampleBaseInfo(testingReport,orderSampleView,order); 
            reportExportSampleBaseInfo.setTaskId(request.getTaskId());
            reportExportSampleBaseInfo.setAnalysisResult(request.getAnalysisResult());
            baseDaoSupport.insert(reportExportSampleBaseInfo);
            return reportExportSampleBaseInfo;
        }
        return null;
    }
    
    private void deleteReportExportMutationInfoByTaskId(String taskId) {
        String hql = "FROM ReportExportSampleBaseInfo WHERE taskId= :taskId";
        NamedQueryer queryer = NamedQueryer.builder()
            .hql(hql)
            .names(Lists.newArrayList("taskId"))
            .values(Lists.newArrayList(taskId))
            .build();
        List<ReportExportSampleBaseInfo> reportExportSampleBaseInfos=baseDaoSupport.find(queryer,ReportExportSampleBaseInfo.class);
        for(ReportExportSampleBaseInfo reportExportSampleBaseInfo:reportExportSampleBaseInfos) {
            baseDaoSupport.delete(reportExportSampleBaseInfo);
        }
    }
    
    private ReportExportSampleBaseInfo convertToReportExportSampleBaseInfo(TestingReport testingReport,OrderSampleView orderSampleView, Order order)
    {
        ReportExportSampleBaseInfo reportExportSampleBaseInfo=new ReportExportSampleBaseInfo();
        reportExportSampleBaseInfo.setOrderCode(order.getCode()==null?"":order.getCode());
        List<OrderPrimarySample> primarySamples=order.getOrderPrimarySample();
        if(primarySamples!=null) {
            reportExportSampleBaseInfo.setSampleCode(primarySamples.get(0)==null?"":primarySamples.get(0).getSampleCode());//样本编号
            MetadataSample metaSample = orderService.get(primarySamples.get(0).getSampleTypeId());
            if (StringUtils.isNotEmpty(metaSample))
            {
                reportExportSampleBaseInfo.setSampleType(metaSample.getName()); //样本类型
                OrderSampleDetails orderSampleDetails = getSampleDetailById(orderSampleView.getId());
                reportExportSampleBaseInfo.setSamplingInfoDate(DateUtils.formatDate(orderSampleDetails.getSamplingDate(), "yyyy-MM-dd"));//采样日期
            }
        }
        
        if (StringUtils.isNotEmpty(order.getOwnerId()))
        {
            Customer cc = getCustomer(order.getOwnerId());
            reportExportSampleBaseInfo.setInspectionUnit(cc.getCompany().getName());    //送检单位
        }
        
        List<OrderExaminee> orderExaminees= order.getOrderExamineeList();
        if(orderExaminees!=null) {
            OrderExaminee orderExaminee=orderExaminees.get(0);
            reportExportSampleBaseInfo.setExamineeName(orderExaminee.getName());    //姓        名
            if(orderExaminee.getSex().equals("0")) {             //性        别
                reportExportSampleBaseInfo.setSex("男"); 
            }else if(orderExaminee.getSex().equals("1")) {
                reportExportSampleBaseInfo.setSex("女");
            }else {
                reportExportSampleBaseInfo.setSex(orderExaminee.getSex()); 
            }
            reportExportSampleBaseInfo.setAge(orderExaminee.getAgeSnapshot());//年        龄
            reportExportSampleBaseInfo.setMedicalRecordNumber(orderExaminee.getRecordNo()); //病  历  号
            if(orderExaminee.getOrderExamineeDiagnosis()!=null) {
                List<OrderExamineeDiagnosis> orderExamineeDiagnosiss=orderExaminee.getOrderExamineeDiagnosis();
                String clinicalDiagnosis="";
                for(OrderExamineeDiagnosis orderExamineeDiagnosis:orderExamineeDiagnosiss) {
                    clinicalDiagnosis+=orderExamineeDiagnosis.getDisease().getName()+",";
                }
                if(clinicalDiagnosis.contains(",")) {
                    clinicalDiagnosis=clinicalDiagnosis.substring(0,clinicalDiagnosis.lastIndexOf(","));
                }
                reportExportSampleBaseInfo.setClinicalDiagnosis(clinicalDiagnosis); //临床诊断
            }
            
            if(orderExaminee.getOrderExamineePhenotype()!=null) {
                List<OrderExamineePhenotype> orderExamineePhenotypes=orderExaminee.getOrderExamineePhenotype();
                String clinicalPhenotype="";
                for(OrderExamineePhenotype orderExamineePhenotype:orderExamineePhenotypes) {
                    clinicalPhenotype+=orderExamineePhenotype.getPhenotype().getName()+",";
                }
                if(clinicalPhenotype.contains(",")) {
                    clinicalPhenotype=clinicalPhenotype.substring(0,clinicalPhenotype.lastIndexOf(","));
                }
                reportExportSampleBaseInfo.setClinicalPhenotype(clinicalPhenotype); //临床表型
            }
            
            if(orderExaminee.getOrderExamineeGene()!=null) {
                List<OrderExamineeGene> orderExamineeGenes=orderExaminee.getOrderExamineeGene();
                String focusOnGenes="";
                for(OrderExamineeGene orderExamineeGene:orderExamineeGenes) {
                    focusOnGenes+=orderExamineeGene.getGene().getName()+",";
                }
                if(focusOnGenes.contains(",")) {
                    focusOnGenes=focusOnGenes.substring(0,focusOnGenes.lastIndexOf(","));
                }
                reportExportSampleBaseInfo.setFocusOnGenes(focusOnGenes);//重点关注基因
            }
            reportExportSampleBaseInfo.setMedicalHistoryDescription(orderExaminee.getMedicalHistory()); //病史摘要
            reportExportSampleBaseInfo.setFamilyHistoryDescription(orderExaminee.getFamilyMedicalHistory());   //家族史摘要
        }
        
        
        /*List<OrderProduct> orderProducts=order.getOrderProductList();
        String productName="";
        if(orderProducts!=null&&orderProducts.size()>0) {
            for(OrderProduct orderProduct:orderProducts) {
                productName+=orderProduct.getProductName();
            }
        }
        if(productName.contains(",")) productName=productName.substring(0, productName.lastIndexOf(","));*/
        reportExportSampleBaseInfo.setAnalysisProject(testingReport.getProductName()); //分析项目
        return reportExportSampleBaseInfo;
    }

    private TestingReport getTestingReportById(String id)
    {
        TestingReport report = baseDaoSupport.get(TestingReport.class, id);
        return report;
    }

    private OrderSampleView getOrderSampleView(String sampleCode)
    {
        String hql = "FROM OrderSampleView os WHERE os.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder()
            .hql(hql)
            .names(Lists.newArrayList("sampleCode"))
            .values(Lists.newArrayList(sampleCode))
            .build();
        List<OrderSampleView> list = baseDaoSupport.find(queryer, OrderSampleView.class);
        if(Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    private Customer getCustomer(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/{id}");
        return template.getForObject(url, Customer.class, Collections.singletonMap("id", id));
    }
    
    private OrderSampleDetails getSampleDetailById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getSampleDetailById/{id}");
        return template.getForObject(url, OrderSampleDetails.class, Collections.singletonMap("id", id));
    }

    @Override
    @Transactional
    public  Map<String,List<ReportExportDetectionResult>> getReportExportDetectionResultInfo(ReportExportDetectionResultInfoRequest request)
    {
        if(request.getDetectionResultIds()==null||request.getDetectionResultIds().size()<=0) { 
            return new HashMap<>();
        }
        List<String> resutIds=new ArrayList<String>();
        for(String resultId:request.getDetectionResultIds()) {
            if(!resutIds.contains(resultId.substring(0, resultId.lastIndexOf("_")))) {
                resutIds.add(resultId.substring(0, resultId.lastIndexOf("_"))); 
            }
        }
        deleteReportExportDetectionResultInfoByTaskId(request.getTaskId());
        String hql = "FROM TestingTaskResult WHERE taskId IN :taskIds";
        List<TestingTaskResult> testingTaskResults =
            baseDaoSupport.findByNamedParam(TestingTaskResult.class, hql, new String[] {"taskIds"}, new Object[] {resutIds});
        List<ReportExportDetectionResult> reportExportDetectionResults=new ArrayList<ReportExportDetectionResult>();
        int i=0;
        if(testingTaskResults!=null&&testingTaskResults.size()>0) {
            for(TestingTaskResult testingTaskResult:testingTaskResults) {
                if(StringUtils.isNotBlank(testingTaskResult.getDetails())) {
                    FluoAnalyseSubmitTaskModel fluoanalysesubmittaskmodel = JsonUtils.asObject(testingTaskResult.getDetails(), FluoAnalyseSubmitTaskModel.class);//FluoAnalyseSubmitTaskModel该类型包含了其他类型的基本字段
                    if(fluoanalysesubmittaskmodel.getSuccessArgs()!=null&&fluoanalysesubmittaskmodel.getSuccessArgs().size()>0) {
                        for(FluoDataSubmitTaskSuccessArgs fluoDataSubmitTaskSuccessArgs:fluoanalysesubmittaskmodel.getSuccessArgs()) {
                            if(request.getDetectionResultIds().contains(testingTaskResult.getTaskId()+"_"+i)) {
                                Map<String, String> result= fluoDataSubmitTaskSuccessArgs.getMap();
                                if(result!=null&&result.size()>0) {
                                    ReportExportDetectionResult reportExportDetectionResult=new ReportExportDetectionResult();
                                    if(StringUtils.isNotBlank(result.get("临床相关性判断"))) {
                                        reportExportDetectionResults.add(reportExportDetectionResult);
                                        reportExportDetectionResult.setMethodName(request.getMethodName());
                                        reportExportDetectionResult.setTaskId(request.getTaskId());
                                        reportExportDetectionResult.setClinicalJudgment(result.get("临床相关性判断"));
                                        List<ReportExportMethodColumnConfig> reportExportMethodColumnConfigs =getReportExportMethodColumnConfig(request.getMethodName());
                                        HashMap<String, String> object=new LinkedHashMap<String,String>();
                                        object.put("taskResultId", testingTaskResult.getTaskId()+"_"+i);
                                        for(ReportExportMethodColumnConfig reportExportMethodColumnConfig:reportExportMethodColumnConfigs) {
                                            if(StringUtils.isNotBlank(result.get(reportExportMethodColumnConfig.getShowName())))
                                            {
                                                object.put(reportExportMethodColumnConfig.getColumnName(), result.get(reportExportMethodColumnConfig.getShowName()));
                                            }else {
                                                object.put(reportExportMethodColumnConfig.getColumnName(),"");
                                            }
                                        }
                                        reportExportDetectionResult.setResult(object);
                                        reportExportDetectionResult.setDetails(JSON.toJSONString(object));
                                    }
                                }
                            }
                            i++;
                        }
                    }
                }
             }
            for(ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults) {
                baseDaoSupport.insert(reportExportDetectionResult);
                //reportExportDetectionResult.setDetails("");//避免传到前端
            }
        }
       return reportExportDetectionResults.stream().collect(Collectors.groupingBy(ReportExportDetectionResult::getClinicalJudgment));
    }
    
    private void deleteReportExportDetectionResultInfoByTaskId(String taskId) {
        String hql = "FROM ReportExportDetectionResult WHERE taskId= :taskId";
        NamedQueryer queryer = NamedQueryer.builder()
            .hql(hql)
            .names(Lists.newArrayList("taskId"))
            .values(Lists.newArrayList(taskId))
            .build();
        List<ReportExportDetectionResult> reportExportDetectionResults=baseDaoSupport.find(queryer,ReportExportDetectionResult.class);
        for(ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults) {
            baseDaoSupport.delete(reportExportDetectionResult);
        }
    }

    @Override
    public List<ReportExportMethodColumnConfig> getReportExportMethodColumnConfig(String method)
    {
        String confighql = "FROM ReportExportMethodColumnConfig WHERE method = :method order by sortIndex asc";
        List<ReportExportMethodColumnConfig> reportExportMethodColumnConfigs =
            baseDaoSupport.findByNamedParam(ReportExportMethodColumnConfig.class, confighql, new String[] {"method"}, new Object[] {method});
        return reportExportMethodColumnConfigs;
    }
}
