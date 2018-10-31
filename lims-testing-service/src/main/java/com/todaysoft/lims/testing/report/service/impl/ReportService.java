package com.todaysoft.lims.testing.report.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.adapter.impl.DictAdapter;
import com.todaysoft.lims.testing.base.adapter.impl.ProductAdapter;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.Customer;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.Dict;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderExamineeDiagnosis;
import com.todaysoft.lims.testing.base.entity.OrderExamineeGene;
import com.todaysoft.lims.testing.base.entity.OrderExamineePhenotype;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.OrderResearchSample;
import com.todaysoft.lims.testing.base.entity.OrderSampleView;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductPrincipal;
import com.todaysoft.lims.testing.base.entity.ProductProbe;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.Sample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.testing.base.entity.TestingDataPic;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingReportSample;
import com.todaysoft.lims.testing.base.entity.TestingReportSendCallBack;
import com.todaysoft.lims.testing.base.entity.TestingReportSendDetails;
import com.todaysoft.lims.testing.base.entity.TestingReportTempSave;
import com.todaysoft.lims.testing.base.entity.TestingReportUploadRecord;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.entity.User;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.UserGroup;
import com.todaysoft.lims.testing.base.model.UserGroupMember;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bcm.ProductSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.DateUtil;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitTaskArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitTaskModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitTaskModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.mybatis.mapper.TestingReportViewMapper;
import com.todaysoft.lims.testing.mybatis.model.TestingSheetModel;
import com.todaysoft.lims.testing.ons.IMessageProducer;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.testing.pooling.model.PoolingSheetVariables;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.testing.report.dao.ReportSendSearcher;
import com.todaysoft.lims.testing.report.dao.ReportTaskSearcher;
import com.todaysoft.lims.testing.report.model.BaseInfo;
import com.todaysoft.lims.testing.report.model.CollectionCnvAnalysisPicResultList;
import com.todaysoft.lims.testing.report.model.DataColAndMutationDataModel;
import com.todaysoft.lims.testing.report.model.DtDataInfo;
import com.todaysoft.lims.testing.report.model.ExamineeModel;
import com.todaysoft.lims.testing.report.model.FlouDataInfo;
import com.todaysoft.lims.testing.report.model.MlpaDataInfo;
import com.todaysoft.lims.testing.report.model.MutationSourceUpdateModel;
import com.todaysoft.lims.testing.report.model.NgsInfo;
import com.todaysoft.lims.testing.report.model.ReportAndVerifyModel;
import com.todaysoft.lims.testing.report.model.ReportCallbackModel;
import com.todaysoft.lims.testing.report.model.ReportDataModel;
import com.todaysoft.lims.testing.report.model.ReportHandleModel;
import com.todaysoft.lims.testing.report.model.ReportProcessModel;
import com.todaysoft.lims.testing.report.model.ReportProcessModelArgs;
import com.todaysoft.lims.testing.report.model.ReportProcessResult;
import com.todaysoft.lims.testing.report.model.ReportRequest;
import com.todaysoft.lims.testing.report.model.ReportSendCallBackModel;
import com.todaysoft.lims.testing.report.model.ReportSendDataModel;
import com.todaysoft.lims.testing.report.model.ReportTaskDetail;
import com.todaysoft.lims.testing.report.model.ReportTaskSchedule;
import com.todaysoft.lims.testing.report.model.ReportTestingPicture;
import com.todaysoft.lims.testing.report.model.ReportUploadRecordRequest;
import com.todaysoft.lims.testing.report.model.ReportUploadRequest;
import com.todaysoft.lims.testing.report.model.ResponseModel;
import com.todaysoft.lims.testing.report.model.ReturnModel;
import com.todaysoft.lims.testing.report.model.SampleModel;
import com.todaysoft.lims.testing.report.model.SangerDataInfo;
import com.todaysoft.lims.testing.report.model.SendDataRequest;
import com.todaysoft.lims.testing.report.model.TestSampleModel;
import com.todaysoft.lims.testing.report.model.TestingDataModel;
import com.todaysoft.lims.testing.report.model.TestingDataPicModel;
import com.todaysoft.lims.testing.report.model.TestingReportAssignModel;
import com.todaysoft.lims.testing.report.model.TestingReportPagingRequest;
import com.todaysoft.lims.testing.report.model.VerifyData;
import com.todaysoft.lims.testing.report.model.VerifyDataModel;
import com.todaysoft.lims.testing.report.model.VerifyRecordModel;
import com.todaysoft.lims.testing.report.model.VerifyResultModel;
import com.todaysoft.lims.testing.report.model.VerifySampleModel;
import com.todaysoft.lims.testing.report.request.UpdateMutationSourceRequest;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.testing.report.service.IOrderService;
import com.todaysoft.lims.testing.report.service.IReportCancelService;
import com.todaysoft.lims.testing.report.service.IReportService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Service
public class ReportService implements IReportService, IEntityWrapper<Object, TestingReport> {

    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Autowired
    private IExtraSampleService extraSampleService;

    @Autowired
    private BMMAdapter bmmadapter;

    @Autowired
    private BCMAdapter bcmadapter;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ITestingScheduleService scheduleService;

    @Autowired
    private IDataTemplateService dataTemplateService;

    @Autowired
    private IReportCancelService reportCancelService;

    @Autowired
    private IMessageProducer producer;

    @Autowired
    private RestTemplate template;

    @Autowired
    private DictAdapter dictadapter;

    @Autowired
    private ProductAdapter productAdapter;

    @Autowired
    private SMMAdapter smmadapter;

    @Autowired
    private TestingReportViewMapper viewMapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITestingSampleService  testingSampleService;

    private static Logger log = LoggerFactory.getLogger(ReportService.class);

    private static String tag = "old";

    @Override
    public Pagination<TestingReport> paging(ReportTaskSearcher searcher, int pageNo, int pageSize) {
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher, pageNo, pageSize, this);
        if (null != pagination && Collections3.isNotEmpty(pagination.getRecords())) {
            for (TestingReport tr : pagination.getRecords()) {
                wrapForLane(tr);
                // 设置加急
                tr.setIfUrgent(tr.getOrder().getIfUrgent());
                tr.setUrgentName(tr.getOrder().getUrgentName());
                tr.setUrgentUpdateTime(tr.getOrder().getUrgentUpdateTime());
                if (null != tr.getProduct().getProductDuty()) {
                    if (null != tr.getProduct().getProductDuty().getArchive()) {
                        tr.setProductDutyMan(tr.getProduct().getProductDuty().getArchive().getName());
                    }
                }
            }
        }
        return pagination;
    }

    @Override
    public Pagination<TestingReport> assignPaging(ReportTaskSearcher searcher, int pageNo, int pageSize) {
        if (null == searcher.getReportState()) {
            searcher.setStatus(3);//3是待下达 只匹配未处理的报告
        } else {
            searcher.setStatus(searcher.getReportState());
        }
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher.toAssingQuery(), pageNo, pageSize, this);
        if (null != pagination && Collections3.isNotEmpty(pagination.getRecords())) {
            for (TestingReport tr : pagination.getRecords()) {
                wrapForLane(tr);
                // 设置加急
                tr.setIfUrgent(tr.getOrder().getIfUrgent());
                tr.setUrgentName(tr.getOrder().getUrgentName());
                tr.setUrgentUpdateTime(tr.getUrgentUpdateTime());
                if (null != tr.getProduct().getProductDuty()) {
                    if (null != tr.getProduct().getProductDuty().getArchive()) {
                        tr.setProductDutyMan(tr.getProduct().getProductDuty().getArchive().getName());
                    }
                }
            }
        }
        return pagination;
    }

    @Override
    public Pagination<TestingReport> assignedPaging(ReportTaskSearcher searcher, int pageNo, int pageSize) {
        //已下达 匹配未处理跟已处理的 即不过滤状态
        if (null == searcher.getReportState()) {
            searcher.setStatus(3);//3是待下达 只匹配未处理的报告
        } else {
            searcher.setStatus(searcher.getReportState());
        }
        Pagination<TestingReport> pagination = baseDaoSupport.find(searcher.toAssingedQuery(), pageNo, pageSize, this);
        if (null != pagination && Collections3.isNotEmpty(pagination.getRecords())) {
            for (TestingReport tr : pagination.getRecords()) {
                wrapForLane(tr);
                // 设置加急
                tr.setIfUrgent(tr.getOrder().getIfUrgent());
                tr.setUrgentName(tr.getOrder().getUrgentName());
                tr.setUrgentUpdateTime(tr.getUrgentUpdateTime());
                if (null != tr.getProduct().getProductDuty()) {
                    if (null != tr.getProduct().getProductDuty().getArchive()) {
                        tr.setProductDutyMan(tr.getProduct().getProductDuty().getArchive().getName());
                    }
                }
            }
        }
        return pagination;
    }

    @Override
    public TestingReport wrap(Object entity) {
        if (null == entity) {
            return null;
        }
        TestingReport testingReport = (TestingReport) entity;
        String productId = testingReport.getProduct().getId();
        String jsoStr = getMethodMapTemp(productId);
        testingReport.setMapTemplate(jsoStr);
        return testingReport;
    }

    @Override
    @Transactional
    public void saveOrUpdateReport(VariableModel model) {

        if (null == model) {
            return;
        }
        String scheduleIds = model.getScheduleIds();
        if (StringUtils.isEmpty(scheduleIds)) {
            return;
        }
        // 1.(科研订单不会走到这边的报告整合，会单独处理)根据流程id查询orderId,productId 2.根据这两个条件判断是否已经存入该记录 3.没有的话，保存，有的话更新记录
        String ids[] = scheduleIds.split(",");
        for (String scheduleId : ids) {
            TestingSchedule testingSchedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
            if (null == testingSchedule) {
                throw new IllegalStateException();
            }
            String orderId = testingSchedule.getOrderId();
            String productId = testingSchedule.getProductId();
            if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(productId)) {
                throw new IllegalStateException();
            }
            Order order = baseDaoSupport.get(Order.class, orderId);
            if (null == order) {
                throw new IllegalStateException();
            }
            if ("4".equals(order.getType().getId()) || 4 == order.getTestingStatus().intValue())// 科技订单不进入报告整合 订单取消也不进入
            {
                continue;
            } else {
                if (null != order.getContract()) {
                    List<ContractContent> contractContents =
                            baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order.getContract().getId() + "'");
                    if (Collections3.isNotEmpty(contractContents) && StringUtils.isNotEmpty(contractContents.get(0).getDeliveryMode())) {
                        String[] modes = Collections3.getFirst(contractContents).getDeliveryMode().split(",");
                        List<String> modeList = Arrays.asList(modes);
                        if (!modeList.contains("3")) {
                            continue;
                        }

                    }
                }

            }
            // 如果做到取消实验在异常上报那边（其他取消实验也是如此） 此时也会进入到报告整合（根据订单产品查询是否有正常完成的实验，如果有就让进入，没有就过滤）
            // List<TestingSchedule> testingScheduleEndSuccess = getEndSuccessTestingSchedule(orderId, productId);
            // if(Collections3.isEmpty(testingScheduleEndSuccess))
            // {
            // continue;
            // }
            insertOrUpdataReportByOrderIdAndProductId(orderId, productId);
        }
    }

    @Transactional
    private void insertOrUpdataReportByOrderIdAndProductId(String orderId, String productId) {
        List<TestingReport> records = getReportListByOrderIdAndProductId(orderId, productId);
        TestingReport testingReport;
        List<TestingSchedule> testingSchedules = getTestingScheduleByOrderIdAndProductId(orderId, productId);
        int completeNum = 0;
        if (Collections3.isEmpty(testingSchedules)) {
            return;
        }
        Order order = baseDaoSupport.get(Order.class, orderId);
        if (null == order) {
            throw new IllegalStateException();
        }
        Product product = baseDaoSupport.get(Product.class, productId);
        if (null == product) {
            throw new IllegalStateException();
        }
        int totalNum = testingSchedules.size();
        int cancerNum = 0;
        for (TestingSchedule record : testingSchedules) {
            if (null != record.getEndTime() || StringUtils.isNotEmpty(record.getEndType())) {
                completeNum++;
            }
            if (TestingSchedule.CANCEL.intValue() == record.getProccessState().intValue()) {
                cancerNum++;
            }
        }
        if (0 == completeNum) {
            log.info("无已完成流程,无需出报告！");
            return;
        }
        if (Collections3.isEmpty(records)) {

            testingReport = new TestingReport();
            if (totalNum == cancerNum || completeNum == cancerNum) {
                testingReport.setDelFlag(true);
            } else {
                testingReport.setDelFlag(false);
            }

            testingReport.setOrder(order);
            testingReport.setOrderCode(order.getCode());
            testingReport.setProduct(product);
            testingReport.setProductCode(product.getCode());
            testingReport.setProductName(product.getName());
            testingReport.setTotalNum(totalNum);
            testingReport.setCompleteNum(completeNum);
            testingReport.setCreateDate(new Date());
            testingReport.setUpdateDate(new Date());
            testingReport.setResubmit(false);
            testingReport.setAssignStatus(0);
            ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
            if (null == primarySample) {
                throw new IllegalStateException();
            }
            TestingSample testingSample = extraSampleService.getTestingSampleBySampleCode(primarySample.getSampleCode());
            if (null != testingSample) {
                testingReport.setSampleId(testingSample.getId());
                testingReport.setSampleCode(testingSample.getSampleCode());
            } else {
                log.info(" testingSample is null ");
            }
            testingReport.setSampleName(primarySample.getSampleName());
            BioSampleSimpleModel bssm = new BioSampleSimpleModel(orderId, productId, primarySample.getSampleCode());
            OrderSimpleModel orderModel = bmmadapter.getOrder(bssm);
            if (null != orderModel) {
                testingReport.setTestUnit(orderModel.getCustomerCompanyName());
                testingReport.setCustomer(orderModel.getCustomerName());
                testingReport.setAnalType(orderModel.getDoctorAssist());
                testingReport.setBusinessMan(orderModel.getBusinessLeader());
                testingReport.setMarketingCenter(order.getType().getId());
            }
            ProductSimpleModel productModel = bcmadapter.getProduct(product.getId());
            Map<String, String> mapResult = getTechManMapByProduct(orderId, product.getId(), product.getCode());
            testingReport.setTechnicalMan(mapResult.get("name"));
            testingReport.setTechnicalManId(mapResult.get("id"));

            if (null != orderModel.getSubmitTime() && null != productModel.getTestingDuration()) {
                testingReport.setShouldReportDate(DateUtils.addDays(orderModel.getSubmitTime(), productModel.getTestingDuration()));
            }
            if (totalNum != 0 && totalNum == completeNum) {
                // 说明该产品的检测或验证都完成了
                testingReport.setStatus(TestingReport.REPORT_STATUS_ABLE_REPORT);
            } else {
                // 说明该产品的检测或验证未完成
                testingReport.setStatus(TestingReport.REPORT_STATUS_WAIT_DATA);
            }
            // 生成一个报告编号--
            String reportNo = commonService.getReportNoByName(TestingReport.REPORT_NO);
            testingReport.setReportCode(reportNo);
            baseDaoSupport.insert(testingReport);
            log.info("insert report id:" + testingReport.getId());
            //打开报告整合监控节点  2017.10.31
            producer.sendOpenReportGenerateEvent(orderId, productId);
        } else {
            // 更新记录
            testingReport = Collections3.getFirst(records);
            Map<String, String> mapResult = getTechManMapByProduct(orderId, productId, product.getCode());
            testingReport.setTechnicalMan(mapResult.get("name"));
            testingReport.setTechnicalManId(mapResult.get("id"));
            if (testingReport.getStatus() == TestingReport.REPORT_STATUS_COMPLETED) {
                // 已出报告的不做操作
                return;
            }
            if (totalNum == cancerNum)// 如果都取消了 至为删除状态
            {
                testingReport.setDelFlag(true);
            } else {
                testingReport.setDelFlag(false);
            }
            if (totalNum != 0 && totalNum == completeNum) {
                // 说明该产品的检测或验证都完成了
                testingReport.setStatus(TestingReport.REPORT_STATUS_ABLE_REPORT);
            } else {
                // 说明该产品的检测或验证未完成
                testingReport.setStatus(TestingReport.REPORT_STATUS_WAIT_DATA);
            }
            testingReport.setTotalNum(totalNum);
            testingReport.setCompleteNum(completeNum);
            testingReport.setUpdateDate(new Date());
            baseDaoSupport.update(testingReport);
        }
    }

    public List<TestingSchedule> getEndSuccessTestingSchedule(String orderId, String productId) {
        // 1正常结束 0异常结束
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId AND t.endType = 1 ";
        List<TestingSchedule> records =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"orderId", "productId"}, new String[]{orderId, productId});
        return records;
    }

    @Override
    public ReportTaskDetail getDetails(String id) {
        TestingReport testingReportEntity = baseDaoSupport.get(TestingReport.class, id);
        TestingReport testingReport = wrap(testingReportEntity);
        if (null == testingReport) {
            throw new IllegalStateException();
        }
        ReportTaskDetail taskDetail = new ReportTaskDetail();
        taskDetail.setOrderCode(testingReport.getOrderCode());
        taskDetail.setCustomer(testingReport.getCustomer());
        taskDetail.setMarketingCenter(testingReport.getMarketingCenter());
        taskDetail.setShouldReportDate(testingReport.getShouldReportDate());
        taskDetail.setTestUnit(testingReport.getTestUnit());
        taskDetail.setStatus(testingReport.getStatus());
        taskDetail.setTotalNum(testingReport.getTotalNum());
        taskDetail.setCompleteNum(testingReport.getCompleteNum());

        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();

        ReportTaskSchedule reportTaskSchedule;

        String verifyKey = "";

        TestingTechnicalAnalyRecord analyRecord = null;

        List<ReportTaskSchedule> reportTaskScheduleList = Lists.newArrayList();

        List<TestingSchedule> scheduleList = getTestingScheduleByOrderIdAndProductId(orderId, productId);

        for (TestingSchedule testingSchedule : scheduleList) {
            verifyKey = testingSchedule.getVerifyKey();
            reportTaskSchedule = new ReportTaskSchedule();
            reportTaskSchedule.setScheduleId(testingSchedule.getId());
            reportTaskSchedule.setOrderCode(testingReport.getOrderCode());
            reportTaskSchedule.setProductName(testingReport.getProductName());
            if (TestingSchedule.CANCEL.intValue() == testingSchedule.getProccessState().intValue()) {
                reportTaskSchedule.setActiveTaskName("已取消");
            } else {
                reportTaskSchedule.setActiveTaskName(testingSchedule.getActiveTask());
            }
            reportTaskSchedule.setStartTime(testingSchedule.getStartTime());
            TestingMethod method = baseDaoSupport.get(TestingMethod.class, testingSchedule.getMethodId());
            String methodName = method.getName();
            reportTaskSchedule.setMethod(methodName);
            TestingSample testingSample = baseDaoSupport.get(TestingSample.class, testingSchedule.getSampleId());
            if (null != testingSample) {
                ReceivedSample receivedSample = testingSample.getReceivedSample();
                if (null != receivedSample) {
                    reportTaskSchedule.setSampleCode(receivedSample.getSampleCode());
                    reportTaskSchedule.setSampleName(receivedSample.getSampleName());
                    reportTaskSchedule.setSampleTypeName(receivedSample.getSampleTypeName());
                }
            } else {
                ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, testingSchedule.getSampleId());
                if (null != receivedSample) {
                    reportTaskSchedule.setSampleCode(receivedSample.getSampleCode());
                    reportTaskSchedule.setSampleName(receivedSample.getSampleName());
                    reportTaskSchedule.setSampleTypeName(receivedSample.getSampleTypeName());
                }
            }
            if (StringUtils.isNotEmpty(verifyKey)) {
                if ("Sanger验证".equals(methodName) || "PCR-NGS验证".equals(methodName)) {
                    SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
                    if (null != sangerVerifyRecord) {
                        analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                    }
                } else if ("MLPA验证".equals(methodName)) {
                    MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
                    if (null != mlpaVerifyRecord) {
                        analyRecord = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord();
                    }
                } else if ("QPCR验证".equals(methodName)) {
                    QpcrVerifyRecord qpcrVerifyRecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
                    if (null != qpcrVerifyRecord) {
                        analyRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
                    }
                }
                if (null != analyRecord) {
                    reportTaskSchedule.setChromosomeLocation(analyRecord.getChromosomalLocation());
                    reportTaskSchedule.setGene(analyRecord.getGeneSymbol());
                }
            }
            reportTaskScheduleList.add(reportTaskSchedule);
        }
        taskDetail.setList(reportTaskScheduleList);
        return taskDetail;
    }

    @Override
    @Transactional
    public ReturnModel batchUpload(ReportUploadRequest request) {
        ReturnModel rm = new ReturnModel();
        List<Map<String, String>> orderProductIds = Lists.newArrayList();
        List<String> list = request.getList();
        String reportNo = "";
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        String s = "总共上传文件个数：" + list.size();
        int i = 0;
        for (String combine : list) {
            String combinePath[] = combine.split(",");// arr[0]是文件名 arr[1]是七牛地址
            if (null == combinePath || combinePath.length != 2) {
                log.error("file name contains more douhao , ");
                throw new IllegalStateException();
            }
            String temp = combinePath[0];
            String qiniuName = combinePath[1];
            String arr[] = temp.split("_");
            if (arr == null || arr.length < 5) {
                sb2.append("</br>" + temp.substring(20, temp.length() - 1) + "、");// 文件命名不符合规则
                i++;
                continue;
            }
            String productCode = arr[1];
            String orderCode = arr[2];
            // 设置testingreport状态已出报告 并设置
            if (StringUtils.isEmpty(orderCode) || StringUtils.isEmpty(productCode)) {
                continue;
            }
            TestingReport testingReport = getReportByOrderAndProduct(orderCode, productCode, Integer.valueOf(request.getStatus()));
            if (null == testingReport) {
                sb.append("</br>" + temp.substring(20, temp.length() - 1) + "、");// 文件订单编号或产品编号不匹配
                i++;
                continue;
            }
            reportNo = commonService.getReportNoByName(TestingReport.REPORT_NO);
            testingReport.setUpdateDate(new Date());
            testingReport.setStatus(TestingReport.REPORT_STATUS_COMPLETED);
            testingReport.setReportName(temp);
            if (StringUtils.isEmpty(testingReport.getReportCode())) {
                testingReport.setReportCode(reportNo);
            }
            testingReport.setFirstReviewStatus("0");
            baseDaoSupport.update(testingReport);
            // 更新orderproduct表
            OrderProduct orderProduct = getOrderProductByOrderAndProduct(testingReport.getOrder().getId(), testingReport.getProduct().getId());
            orderProduct.setReportTime(new Date());
            if (StringUtils.isEmpty(orderProduct.getReportNo())) {
                orderProduct.setReportNo(testingReport.getReportCode());
            }
            orderProduct.setDataUrl(qiniuName);
            orderProduct.setReportStatus(1);
            orderProduct.setProductStatus(4);
            baseDaoSupport.update(orderProduct);
            Map<String, String> map = new HashMap<>();
            map.put("orderId", testingReport.getOrder().getId());
            map.put("productId", testingReport.getProduct().getId());
            orderProductIds.add(map);
        }
        s += "</br>成功个数：" + (list.size() - i) + "</br>失败个数：" + i;
        if (StringUtils.isNotEmpty(sb.toString())) {
            s += sb.toString().substring(0, sb.toString().length() - 1) + "</br>文件订单编号或产品编号不匹配!";
        }
        if (StringUtils.isNotEmpty(sb2.toString())) {
            s += sb2.toString().substring(0, sb2.toString().length() - 1) + "</br>文件命名不符合规则!";
        }
        rm.setMsg(s);
        rm.setOrderProductIds(orderProductIds);
        return rm;

    }

    private List<TestingReport> getReportListByOrderIdAndProductId(String orderId, String productId) {
        String hql = "FROM TestingReport t WHERE t.order.id = :orderId AND t.product.id = :productId ";
        List<TestingReport> records =
                baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[]{"orderId", "productId"}, new String[]{orderId, productId});
        return records;
    }

    private TestingReport getReportByOrderAndProduct(String orderCode, String productCode, Integer status) {
        String hql = "FROM TestingReport t WHERE t.order.code = :orderCode AND t.product.code = :productCode AND t.status = :status";
        List<TestingReport> records =
                baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[]{"orderCode", "productCode", "status"}, new Object[]{orderCode,
                        productCode, status});
        return Collections3.getFirst(records);
    }

    private TestingReport getReportById(String id) {
        String hql = "FROM TestingReport t WHERE t.id = :id";
        List<TestingReport> records = baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[]{"id"}, new String[]{id});
        return Collections3.getFirst(records);
    }

    private OrderProduct getOrderProductByOrderAndProduct(String orderId, String productId) {
        String hql = "FROM OrderProduct t WHERE t.order.id = :orderId AND t.product.id = :productId ";
        List<OrderProduct> records =
                baseDaoSupport.findByNamedParam(OrderProduct.class, hql, new String[]{"orderId", "productId"}, new String[]{orderId, productId});
        return Collections3.getFirst(records);
    }

    private List<TestingSchedule> getTestingScheduleByOrderIdAndProductId(String orderId, String productId) {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId ORDER BY ISNULL(t.verifyKey) DESC,t.methodId";
        List<TestingSchedule> records =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"orderId", "productId"}, new String[]{orderId, productId});
        return records;
    }

    @Override
    public ReportDataModel getModelByReportId(TestingReport request) {
        ReportDataModel model = new ReportDataModel();

        List<BaseInfo> baseInfoList = Lists.newArrayList();// 基本信息

        List<NgsInfo> ngsInfoList = Lists.newArrayList();// NGS测序数据

        List<NgsInfo> capNgsList = Lists.newArrayList();// CAPNGS测序数据

        List<VerifyData> mlpaVerifyDataList = Lists.newArrayList();// 验证数据结果明细

        List<VerifyData> sangerVerifyDataList = Lists.newArrayList();// 验证数据结果明细

        List<VerifyData> qpcrVerifyDataList = Lists.newArrayList();// 验证数据结果明细

        List<VerifyData> pcrNgsVerifyDataList = Lists.newArrayList();// 验证数据结果明细

        List<NgsInfo> multiplePcrList = Lists.newArrayList();// 多重PCR

        List<NgsInfo> longPcrList = Lists.newArrayList();// Long-PCR

        List<MlpaDataInfo> mlpaDataInfoList = Lists.newArrayList();// MLPA结果明细

        List<DtDataInfo> dtDataInfoList = Lists.newArrayList();// 动态突变结果明细

        List<FlouDataInfo> flouDataInfoList = Lists.newArrayList();// 荧光检测结果明细

        List<SangerDataInfo> sangerDataInfoList = Lists.newArrayList();// Sanger检测

        String reportIds = request.getId();

        DataTemplate mlpaVerifyTemplate = getVerifyDataTemplateByMethodId("MLPA");

        DataTemplate sangerVerifyTemplate = getVerifyDataTemplateByMethodId("SANGER");

        DataTemplate pcrngsVerifyTemplate = getVerifyDataTemplateByMethodId("QPCR");

        DataTemplate qpcrVerifyTemplate = getVerifyDataTemplateByMethodId("PCR-NGS");

        if (StringUtils.isNotEmpty(reportIds)) {
            String ids[] = reportIds.split(",");
            for (String reportId : ids) {
                TestingReport testingreport = baseDaoSupport.get(TestingReport.class, reportId);
                if (null != testingreport) {
                    // NGS数据模板
                    DataTemplate ngsTemplate = getTemplateByProductAndMethod(testingreport.getProduct().getId(), "NGS");
                    // CAPNGS数据模板
                    DataTemplate capNgsTemplate = getTemplateByProductAndMethod(testingreport.getProduct().getId(), "CAP-NGS");
                    // LONGPCR
                    DataTemplate longPcrTemplate = getTemplateByProductAndMethod(testingreport.getProduct().getId(), "Long-PCR");
                    // 多重PCR
                    DataTemplate mutilPcrTemplate = getTemplateByProductAndMethod(testingreport.getProduct().getId(), "MULTI-PCR");

                    // 基本信息
                    BaseInfo baseInfo = getBaseInfoByReport(testingreport);
                    baseInfoList.add(baseInfo);
                    // NGS测序数据
                    List<NgsInfo> ngsInfoRecords = getNgsOrCapNgsScheduleVerifyDataList(testingreport, 0);// NGS
                    ngsInfoRecords.forEach(x -> x.setDataTemplate(ngsTemplate));
                    ngsInfoList.addAll(ngsInfoRecords);

                    // CAPNGS测序数据
                    List<NgsInfo> capNgsRecords = getNgsOrCapNgsScheduleVerifyDataList(testingreport, 1);// CAPNGS
                    capNgsRecords.forEach(x -> x.setDataTemplate(capNgsTemplate));
                    capNgsList.addAll(capNgsRecords);

                    // 验证结果明细
                    List<VerifyData> verifyDatas = getVerifyDataList(testingreport);

                    // 验证的模板是唯一的，直接取 验证模板排序固定，加几个字段
                    // 顺序 合并编号 家系关系 样本名称 样本编号 基因 外显子区域 染色体位置 杂合/纯合 突变来源 备注

                    for (VerifyData data : verifyDatas) {
                        if (StringUtils.isEmpty(data.getVilidMethod()))
                            continue;
                        switch (data.getVilidMethod().toUpperCase()) {
                            case "MLPA":
                                data.setDataTemplate(mlpaVerifyTemplate);
                                mlpaVerifyDataList.add(data);
                                break;
                            case "SANGER":
                                data.setDataTemplate(sangerVerifyTemplate);
                                sangerVerifyDataList.add(data);
                                break;
                            case "QPCR":
                                data.setDataTemplate(qpcrVerifyTemplate);
                                qpcrVerifyDataList.add(data);
                                break;
                            case "PCR-NGS":
                                data.setDataTemplate(pcrngsVerifyTemplate);
                                pcrNgsVerifyDataList.add(data);
                                break;
                            default:
                                break;
                        }
                    }

                    // 多重PCR
                    List<NgsInfo> multiplePcrs = getNgsOrCapNgsScheduleVerifyDataList(testingreport, 2);// 多重PCR
                    multiplePcrs.forEach(x -> x.setDataTemplate(mutilPcrTemplate));
                    multiplePcrList.addAll(multiplePcrs);

                    // LongPCR
                    List<NgsInfo> longPcrs = getNgsOrCapNgsScheduleVerifyDataList(testingreport, 3);// LongPCR
                    longPcrs.forEach(x -> x.setDataTemplate(longPcrTemplate));
                    longPcrList.addAll(longPcrs);

                    // MLPA结果
                    List<MlpaDataInfo> mlpaDataInfos = getMlpaDataList(testingreport);
                    mlpaDataInfoList.addAll(mlpaDataInfos);

                    // 动态突变
                    List<DtDataInfo> dtDataInfos = getDtDataList(testingreport);
                    dtDataInfoList.addAll(dtDataInfos);

                    // 荧光检测
                    List<FlouDataInfo> flouDataInfos = getFlouDataList(testingreport);
                    flouDataInfoList.addAll(flouDataInfos);

                    // sanger检测
                    List<SangerDataInfo> sangerDataInfos = getSangerDataList(testingreport);
                    sangerDataInfoList.addAll(sangerDataInfos);
                }
            }
        }
        model.setBaseInfoList(baseInfoList);
        model.setNgsInfoList(ngsInfoList);
        model.setCapNgsList(capNgsList);
        model.setMlpaVerifyDataList(mlpaVerifyDataList);
        model.setSangerVerifyDataList(sangerVerifyDataList);
        model.setQpcrVerifyDataList(qpcrVerifyDataList);
        model.setPcrNgsVerifyDataList(pcrNgsVerifyDataList);
        model.setMultiplePcrList(multiplePcrList);
        model.setLongPcrList(longPcrList);
        model.setMlpaDataInfoList(mlpaDataInfoList);
        model.setDtDataInfoList(dtDataInfoList);
        model.setFlouDataInfoList(flouDataInfoList);
        model.setSangerDataInfoList(sangerDataInfoList);
        return model;
    }

    public List<NgsInfo> getNgsOrCapNgsScheduleVerifyDataList(TestingReport testingReport, int type) {
        TestingMethod testingMethod = getByMethodByType(type);// 检测方法
        List<NgsInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        // 查询出在技术分析提交的验证数据 再查寻下是否存在不验证的数据 这个数据只存在record 通过数据编号区分出来
        List<String> analRecordIds = Lists.newArrayList();
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, true, "");
        for (TestingSchedule schedule : schedules) {
            NgsInfo analRecord = getByVerifyKey(schedule);
            records.add(analRecord);
        }

        // 查询是否存在不验证的数据 查询检测的流程 然后找到技术分析的任务数据编号 去查询不验证的数据
        if (null == testingMethod) {
            throw new IllegalStateException();
        }
        List<TestingSchedule> testSchedules = getListByProductAndOrder(orderId, productId, false, testingMethod.getId());
        for (TestingSchedule testingSchedule : testSchedules) {
            TestingTask technicalTask = getLastTechnicalTaskBySchedule(testingSchedule);
            if (null == technicalTask) {
                continue;
            }
            String dataCode = technicalTask.getTestingAnalyDataCode();
            if (StringUtils.isEmpty(dataCode)) {
            }
            List<TestingTechnicalAnalyRecord> recordList = getRecordByDataCode(dataCode);
            for (TestingTechnicalAnalyRecord record : recordList) {
                NgsInfo ngsInfo = new NgsInfo();
                BeanUtils.copyProperties(record, ngsInfo);
                if (StringUtils.isNotEmpty(record.getOtherFields())) {
                    Map<String, String> map = JsonUtils.asObject(record.getOtherFields(), Map.class);
                    ngsInfo.setMap(map);
                }
                records.add(ngsInfo);
            }
        }

        List<NgsInfo> results = Lists.newArrayList();
        for (NgsInfo analRecord : records) {
            // 过滤数据 重复的 每个方法的
            if (!analRecordIds.contains(analRecord.getId())) {
                String dataCode = analRecord.getDataCode();
                if (StringUtils.isEmpty(dataCode)) {
                    continue;
                }
                int index = dataCode.lastIndexOf("_");
                String semantic = dataCode.substring(index + 1);
                if (0 == type)// ngs
                {
                    if ("NGS".equalsIgnoreCase(semantic)) {
                        results.add(analRecord);
                        analRecordIds.add(analRecord.getId());
                    }
                } else if (1 == type)// capNGS
                {
                    if ("CapNGS".equalsIgnoreCase(semantic)) {
                        results.add(analRecord);
                        analRecordIds.add(analRecord.getId());
                    }
                } else if (2 == type) {// 多重PCR
                    if ("MPCR".equalsIgnoreCase(semantic)) {
                        results.add(analRecord);
                        analRecordIds.add(analRecord.getId());
                    }

                } else {// LONGPCR的
                    if ("LPCR".equalsIgnoreCase(semantic)) {
                        results.add(analRecord);
                        analRecordIds.add(analRecord.getId());
                    }
                }

            }

        }
        return results;
    }

    public List<MlpaDataInfo> getMlpaDataList(TestingReport testingReport) {
        List<MlpaDataInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        TestingMethod testingMethod = getTestMethodBySemantic(TestingMethod.MLPA_TEST);
        DataTemplate mlpaTestDataTemplate = getTestDataTemplateById(productId, testingMethod.getId());
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, false, testingMethod.getId());// 查询出MLPA检测流程
        String methodTaskSemantic = "";
        for (TestingSchedule schedule : schedules) {
            methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
            TestingTaskResult dataAnalysisTaskResult = getLastDataAnalysysTaskResultBySchedule(schedule, methodTaskSemantic);
            if (null == dataAnalysisTaskResult) {
                continue;
            }
            String details = dataAnalysisTaskResult.getDetails();
            if (StringUtils.isNotEmpty(details)) {
                MlpaDataSubmitTaskModel taskArgs = JsonUtils.asObject(details, MlpaDataSubmitTaskModel.class);
                if (null != taskArgs) {
                    List<MlpaDataSubmitTaskSuccessArgs> list = taskArgs.getSuccessArgs();
                    log.info("MLPA TEST ARGS COUNT:" + list.size());
                    if (Collections3.isNotEmpty(list)) {
                        for (MlpaDataSubmitTaskSuccessArgs args : list) {
                            MlpaDataInfo mlpaDataInfo = new MlpaDataInfo();
                            BeanUtils.copyProperties(args, mlpaDataInfo);
                            mlpaDataInfo.setDataTemplate(mlpaTestDataTemplate);
                            records.add(mlpaDataInfo);
                        }
                    }
                }
            }

        }
        return records;
    }

    public List<DtDataInfo> getDtDataList(TestingReport testingReport) {
        List<DtDataInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        TestingMethod testingMethod = getTestMethodBySemantic(TestingMethod.DT);
        DataTemplate dataTemplate = getTestDataTemplateById(productId, testingMethod.getId());
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, false, testingMethod.getId());// 查询出MLPA检测流程
        String methodTaskSemantic = "";
        for (TestingSchedule schedule : schedules) {
            methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
            TestingTaskResult dataAnalysisTaskResult = getLastDataAnalysysTaskResultBySchedule(schedule, methodTaskSemantic);
            if (null == dataAnalysisTaskResult) {
                continue;
            }
            String details = dataAnalysisTaskResult.getDetails();
            if (StringUtils.isNotEmpty(details)) {
                DTDataSubmitTaskArgs taskArgs = JsonUtils.asObject(details, DTDataSubmitTaskArgs.class);
                if (null != taskArgs) {
                    List<DTDataSubmitTaskSuccessArgs> args = taskArgs.getSuccessArgs();
                    log.info("DT TEST ARGS COUNT:" + args.size());
                    if (Collections3.isNotEmpty(args)) {
                        for (DTDataSubmitTaskSuccessArgs data : args) {
                            DtDataInfo dtdatainfo = new DtDataInfo();
                            BeanUtils.copyProperties(data, dtdatainfo);
                            dtdatainfo.setDataTemplate(dataTemplate);
                            records.add(dtdatainfo);
                        }
                    }
                }
            }

        }
        return records;
    }

    public List<FlouDataInfo> getFlouDataList(TestingReport testingReport) {
        List<FlouDataInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        TestingMethod testingMethod = getTestMethodBySemantic(TestingMethod.FLUO_TEST);
        DataTemplate dataTemplate = getTestDataTemplateById(productId, testingMethod.getId());
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, false, testingMethod.getId());// 查询出MLPA检测流程
        String methodTaskSemantic = "";
        for (TestingSchedule schedule : schedules) {
            methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
            TestingTaskResult dataAnalysisTaskResult = getLastDataAnalysysTaskResultBySchedule(schedule, methodTaskSemantic);
            if (null == dataAnalysisTaskResult) {
                continue;
            }
            String details = dataAnalysisTaskResult.getDetails();
            log.info("FLUO TASK ID IS:" + dataAnalysisTaskResult.getTaskId() + "~FLUO TASK details JSON :" + details);
            if (StringUtils.isNotEmpty(details)) {
                FluoAnalyseSubmitTaskModel fluoanalysesubmittaskmodel = JsonUtils.asObject(details, FluoAnalyseSubmitTaskModel.class);
                if (null != fluoanalysesubmittaskmodel) {
                    List<FluoDataSubmitTaskSuccessArgs> args = fluoanalysesubmittaskmodel.getSuccessArgs();
                    log.info("FLUO TEST ARGS COUNT:" + args.size());
                    if (Collections3.isNotEmpty(args)) {
                        for (FluoDataSubmitTaskSuccessArgs data : args) {
                            FlouDataInfo floudatainfo = new FlouDataInfo();
                            BeanUtils.copyProperties(data, floudatainfo);
                            floudatainfo.setDataTemplate(dataTemplate);
                            records.add(floudatainfo);
                        }
                    }
                }
            }

        }
        return records;
    }

    public List<SangerDataInfo> getSangerDataList(TestingReport testingReport) {
        List<SangerDataInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        TestingMethod testingMethod = getTestMethodBySemantic(TestingMethod.SANGER_TEST);
        DataTemplate dataTemplate = getTestDataTemplateById(productId, testingMethod.getId());
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, false, testingMethod.getId());// 查询出MLPA检测流程
        String methodTaskSemantic = "";
        for (TestingSchedule schedule : schedules) {
            methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
            List<TestingTaskResult> sangerResults = getLastDataAnalysysTasksResultBySchedule(schedule, methodTaskSemantic);
            if (Collections3.isEmpty(sangerResults)) {
                continue;
            }
            List<String> combineCodes = Lists.newArrayList();
            for (TestingTaskResult result : sangerResults) {
                String details = result.getDetails();
                if (StringUtils.isNotEmpty(details)) {
                    DataPcrSangerSubmitTaskArgs dataPcrSangerSubmitTaskArgs = JsonUtils.asObject(details, DataPcrSangerSubmitTaskArgs.class);
                    if (null != dataPcrSangerSubmitTaskArgs) {
                        if (!combineCodes.contains(dataPcrSangerSubmitTaskArgs.getCombineCode())) {
                            SangerDataInfo sangerDataInfo = new SangerDataInfo();
                            BeanUtils.copyProperties(dataPcrSangerSubmitTaskArgs, sangerDataInfo);
                            sangerDataInfo.setDataTemplate(dataTemplate);
                            records.add(sangerDataInfo);
                            combineCodes.add(dataPcrSangerSubmitTaskArgs.getCombineCode());
                        }
                    }
                }
            }
        }
        return records;
    }

    public BaseInfo getBaseInfoByReport(TestingReport testingreport) {
        BaseInfo baseinfo = new BaseInfo();
        BioSampleSimpleModel simplemodel = new BioSampleSimpleModel();
        String orderId = testingreport.getOrder().getId();
        String productId = testingreport.getProduct().getId();
        simplemodel.setOrderId(orderId);
        simplemodel.setProductId(productId);
        String sampleCode = "";
        String sampleTypeName = "";
        ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
        if (null != primarySample) {
            sampleCode = primarySample.getSampleCode();
            sampleTypeName = primarySample.getSampleTypeName();
        }
        simplemodel.setSampleCode(sampleCode);
        OrderSimpleModel osm = bmmadapter.getOrder(simplemodel);
        if (null != osm) {
            if (osm.getFamilyMedicalHistory() != null && osm.getFamilyMedicalHistory() != "") {
                //如果家族史摘要不为空，是否有家族史这个字段为是
                osm.setFamilyHistory("是");
            }
            String submitTime = DateUtils.formatDate(osm.getSubmitTime(), "yyyy-MM-dd HH:mm:ss");
            baseinfo =
                    new BaseInfo(osm.getName(), osm.getExamineeSex(), osm.getRecordNo(), submitTime, osm.getAge(), osm.getDiagnosisName(), osm.getGeneName(),
                            osm.getMedicalHistory(), osm.getCustomerName(), osm.getBusinessLeader(), osm.getRemark(), osm.getFamilyHistory(),
                            osm.getFamilyMedicalHistory());
            baseinfo.setTestUnit(osm.getCustomerCompanyName());
            baseinfo.setTestType(osm.getMarketingCenter());
        }
        String testType = "";
        String type = testingreport.getOrder().getType().getId();
        if ("1".equals(type)) {
            testType = "临床遗传";
        } else if ("2".equals(type)) {
            testType = "临床肿瘤";
        } else if ("3".equals(type)) {
            testType = "健康筛查";
        } else if ("4".equals(type)) {
            testType = "科技服务";
        }
        baseinfo.setTestType(testType);

        baseinfo.setProductCode(testingreport.getProduct().getCode());
        baseinfo.setProductName(testingreport.getProduct().getName());
        baseinfo.setSampleCode(sampleCode);
        baseinfo.setSampleForm(sampleTypeName);
        baseinfo.setReportAuditor(testingreport.getReportName());
        baseinfo.setOrderCode(testingreport.getOrder().getCode());

        Order order = baseDaoSupport.get(Order.class, orderId);
        Set<String> methodNames = new HashSet<String>();
        Set<String> ppsName = new HashSet<String>();
        Set<String> probeName = new HashSet<String>();
        Set<String> filenames = new HashSet<String>();
        Product product = testingreport.getProduct();
        if (null != product) {
            List<ProductTestingMethod> methods = product.getProductTestingMethodList();
            if (Collections3.isNotEmpty(methods)) {
                for (ProductTestingMethod m : methods) {
                    List<OrderPrimarySample> ops = order.getOrderPrimarySample();
                    if (Collections3.isNotEmpty(ops)) {
                        for (OrderPrimarySample sample : ops) {
                            filenames.add(String.format("%1$s_%2$s_%3$s", sample.getSampleCode(), product.getCode(), getRecordMethodName(m.getTestingMethod()
                                    .getSemantic())));
                        }
                    }
                    methodNames.add(m.getTestingMethod().getName());
                    List<ProductProbe> probes = m.getProductProbe();
                    if (Collections3.isNotEmpty(probes)) {
                        probes.forEach(probe -> probeName.add(probe.getProbe().getName()));
                    }
                }
            }

            List<ProductPrincipal> pps = product.getProductPrincipalList();
            if (Collections3.isNotEmpty(pps)) {
                pps.forEach(pp -> ppsName.add(pp.getPrincipal().getArchive().getName()));
            }
        }
        String reportDate = DateUtils.formatDate(DateUtils.addDays(osm.getSubmitTime(), product.getTestingDuration()), "yyyy-MM-dd HH:mm:ss");
        baseinfo.setReportDate(reportDate);
        baseinfo.setProbe(Collections3.convertToString(probeName, ","));
        baseinfo.setTestMethod(Collections3.convertToString(methodNames, ","));
        baseinfo.setTechnicalMan(Collections3.convertToString(ppsName, ","));
        baseinfo.setFileName(Collections3.convertToString(filenames, ","));

        wrapForLane(testingreport);
        baseinfo.setSequenceCode(testingreport.getSequenceCode());

        return baseinfo;
    }

    // NGS信息
    public List<NgsInfo> getngsInfoRecordsByReport(TestingReport testingReport) {
        List<NgsInfo> records = Lists.newArrayList();
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, true, "");
        List<String> analRecordIds = Lists.newArrayList();
        for (TestingSchedule schedule : schedules) {
            NgsInfo analRecord = getByVerifyKey(schedule);
            if (!analRecordIds.contains(analRecord.getId())) {
                records.add(analRecord);
                analRecordIds.add(analRecord.getId());
            }
        }
        return records;
    }

    public NgsInfo getByVerifyKey(TestingSchedule schedule) {
        NgsInfo ngsInfo = new NgsInfo();
        TestingTechnicalAnalyRecord record;
        String verifyKey = schedule.getVerifyKey();
        SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
        if (null != sangerverifyrecord) {
            record = sangerverifyrecord.getVerifyRecord().getAnalyRecord();
            String schemes = getSchemeByRecord(record);
            BeanUtils.copyProperties(record, ngsInfo);
            ngsInfo.setVerifyScheme(schemes);
            if (StringUtils.isNotEmpty(record.getOtherFields())) {
                Map<String, String> map = JsonUtils.asObject(record.getOtherFields(), Map.class);
                ngsInfo.setMap(map);
            }
            return ngsInfo;
        }
        MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
        if (null != mlpaverifyrecord) {
            record = mlpaverifyrecord.getVerifyRecord().getAnalyRecord();
            String schemes = getSchemeByRecord(record);
            BeanUtils.copyProperties(record, ngsInfo);
            ngsInfo.setVerifyScheme(schemes);
            if (StringUtils.isNotEmpty(record.getOtherFields())) {
                Map<String, String> map = JsonUtils.asObject(record.getOtherFields(), Map.class);
                ngsInfo.setMap(map);
            }
            return ngsInfo;
        }
        QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
        if (null != qpcrverifyrecord) {
            record = qpcrverifyrecord.getVerifyRecord().getAnalyRecord();
            String schemes = getSchemeByRecord(record);
            BeanUtils.copyProperties(record, ngsInfo);
            ngsInfo.setVerifyScheme(schemes);
            if (StringUtils.isNotEmpty(record.getOtherFields())) {
                Map<String, String> map = JsonUtils.asObject(record.getOtherFields(), Map.class);
                ngsInfo.setMap(map);
            }
            return ngsInfo;
        }
        return null;
    }

    public VerifyRecordModel getVerifyRecordByVerifyKey(String verifyKey) {
        VerifyRecordModel model = new VerifyRecordModel();
        TestingVerifyRecord record;
        SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
        if (null != sangerverifyrecord) {
            record = sangerverifyrecord.getVerifyRecord();
            model.setCombineCode(sangerverifyrecord.getCode());
            model.setVerifyRecord(record);
            return model;
        }
        MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
        if (null != mlpaverifyrecord) {
            record = mlpaverifyrecord.getVerifyRecord();
            model.setCombineCode(mlpaverifyrecord.getCombineCode());
            model.setVerifyRecord(record);
            return model;
        }
        QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
        if (null != qpcrverifyrecord) {
            record = qpcrverifyrecord.getVerifyRecord();
            model.setCombineCode(qpcrverifyrecord.getCombineCode());
            model.setVerifyRecord(record);
            return model;
        }
        return null;
    }

    public String getSchemeByRecord(TestingTechnicalAnalyRecord record) {
        if (null == record) {
            return null;
        }
        List<TestingVerifyRecord> records = record.getVerifyRecords();
        List<String> schemes = Lists.newArrayList();
        for (TestingVerifyRecord recordTemp : records) {
            String scheme = recordTemp.getInputSampleFamilyRelation();
            if (!schemes.contains(scheme)) {
                schemes.add(scheme);
            }
        }
        return StringUtils.join(schemes, ",");
    }

    public List<VerifyData> getVerifyDataList(TestingReport testingReport) {
        List<VerifyData> records = Lists.newArrayList();
        VerifyData verifyData;
        String orderId = testingReport.getOrder().getId();
        String productId = testingReport.getProduct().getId();
        String orderCode = testingReport.getOrder().getCode();
        if (null == orderId || null == productId) {
            throw new IllegalStateException();
        }
        List<TestingSchedule> schedules = getListByProductAndOrder(orderId, productId, true, "");
        String methodTaskSemantic = "";
        for (TestingSchedule schedule : schedules) {
            verifyData = new VerifyData();
            verifyData.setOrderCode(orderCode);
            String verifyKey = schedule.getVerifyKey();
            VerifyRecordModel recordModel = getVerifyRecordByVerifyKey(verifyKey);
            TestingTechnicalAnalyRecord analyRecord = recordModel.getVerifyRecord().getAnalyRecord();
            verifyData.setVilidMethod(analyRecord.getVerifyMethod());
            verifyData.setCombineCode(recordModel.getCombineCode());
            setCombineCodeByScheduleAndAnalyRecord(schedule, analyRecord, verifyData);// 设置样本编号 名称 合并后编号 基因 外显子区域，染色体
            verifyData.setFamilyRelation(recordModel.getVerifyRecord().getInputSampleFamilyRelation());
            // 查询数据分析任务节点提交的数据结果 主意：MLPA验证一个任务会上传多个结果
            // 1.查询最后一次的数据分析任务
            methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
            TestingTaskResult dataAnalysisTaskResult = getLastDataAnalysysTaskResultBySchedule(schedule, methodTaskSemantic);
            if (null == dataAnalysisTaskResult) {
                records.add(verifyData);
                continue;
            }
            List<VerifyResultModel> resultModels = getModelByTestingTaskResult(dataAnalysisTaskResult, methodTaskSemantic);
            for (VerifyResultModel model : resultModels) {
                VerifyData verifyDataResult = new VerifyData();
                BeanUtils.copyProperties(verifyData, verifyDataResult);
                verifyDataResult.setCombineType(model.getCombineType());
                verifyDataResult.setMutationSource(model.getMutationSource());
                verifyDataResult.setRemark(model.getRemark());
                if (null != model.getMap() || model.getMap().size() > 0) {
                    if (StringUtils.isEmpty(model.getMap().get("合并编号"))) {
                        model.getMap().put("合并编号", recordModel.getCombineCode());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("家系关系"))) {
                        model.getMap().put("家系关系", verifyData.getFamilyRelation());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("样本名称"))) {
                        model.getMap().put("样本名称", verifyData.getSampleName());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("样本编号"))) {
                        model.getMap().put("样本编号", verifyData.getSampleCode());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("基因"))) {
                        model.getMap().put("基因", verifyData.getGene());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("外显子区域"))) {
                        model.getMap().put("外显子区域", verifyData.getExonRegion());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("染色体位置"))) {
                        model.getMap().put("染色体位置", verifyData.getChromosomeLocation());
                    }
                    if (StringUtils.isEmpty(model.getMap().get("突变来源"))) {
                        model.getMap().put("突变来源", verifyData.getMutationSource());
                    }

                    if (StringUtils.isEmpty(model.getMap().get("订单编号"))) {
                        model.getMap().put("订单编号", testingReport.getOrderCode());
                    }
                }
                verifyDataResult.setMap(model.getMap());
                records.add(verifyDataResult);
            }
        }
        records.sort((comparing(VerifyData::getOrderCode)));
        return records;
    }

    public List<VerifyResultModel> getModelByTestingTaskResult(TestingTaskResult dataAnalysisTaskResult, String dataSemantic) {
        List<VerifyResultModel> list = Lists.newArrayList();
        String details = dataAnalysisTaskResult.getDetails();
        if (StringUtils.isNotEmpty(details)) {
            if (TaskSemantic.MLPA_DATA_ANALYSIS.equals(dataSemantic)) {
                MlpaDataSubmitTaskModel taskArgs = JsonUtils.asObject(details, MlpaDataSubmitTaskModel.class);
                if (null != taskArgs) {
                    List<MlpaDataSubmitTaskSuccessArgs> records = taskArgs.getSuccessArgs();
                    if (Collections3.isNotEmpty(records)) {
                        for (MlpaDataSubmitTaskSuccessArgs args : records) {
                            VerifyResultModel model = new VerifyResultModel();
                            model.setCombineType(args.getPureHeteroNucleus());
                            model.setMutationSource(args.getMutationSource());
                            model.setRemark(args.getRemark());
                            model.setMap(args.getMap());
                            list.add(model);
                        }
                    }
                }
            } else if (TaskSemantic.DATA_ANALYSIS.equals(dataSemantic)) {
                DataPcrSubmitTaskArgs taskArgs = JsonUtils.asObject(details, DataPcrSubmitTaskArgs.class);
                if (null != taskArgs) {
                    VerifyResultModel model = new VerifyResultModel();
                    model.setCombineType(taskArgs.getCombineType());
                    model.setMutationSource(taskArgs.getMutationSource());
                    model.setMap(taskArgs.getMap());
                    if (StringUtils.isEmpty(taskArgs.getRemark())) {
                        model.setRemark(dataAnalysisTaskResult.getRemark());
                    } else {
                        model.setRemark(taskArgs.getRemark());
                    }
                    list.add(model);
                }

            } else if (TaskSemantic.PCR_NGS_DATA_ANALYSIS.equals(dataSemantic)) {
                PcrNgsDataSubmitTaskArgs taskArgs = JsonUtils.asObject(details, PcrNgsDataSubmitTaskArgs.class);
                if (null != taskArgs) {
                    VerifyResultModel model = new VerifyResultModel();
                    model.setCombineType(taskArgs.getCombineType());
                    model.setMutationSource(taskArgs.getMutationSource());
                    model.setRemark(taskArgs.getRemark());
                    model.setMap(taskArgs.getMap());
                    list.add(model);
                }
            } else if (TaskSemantic.QPCR.equals(dataSemantic)) {
                QpcrSubmitTask taskArgs = JsonUtils.asObject(details, QpcrSubmitTask.class);
                if (null != taskArgs) {
                    VerifyResultModel model = new VerifyResultModel();
                    model.setCombineType(taskArgs.getCombineType());
                    model.setMutationSource(taskArgs.getMutationSource());
                    model.setRemark(taskArgs.getRemark());
                    model.setMap(taskArgs.getMap());
                    list.add(model);
                }
            }
        }
        return list;
    }

    private String getByTestingMethod(TestingSchedule schedule) {
        String result = "";
        TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        String testingMethodSemantic = testingMethod.getSemantic();
        if (TestingMethod.MLPA.equals(testingMethodSemantic)) {
            result = TaskSemantic.MLPA_DATA_ANALYSIS;
        } else if (TestingMethod.PCR_NGS.equals(testingMethodSemantic)) {
            result = TaskSemantic.PCR_NGS_DATA_ANALYSIS;
        } else if (TestingMethod.SANGER.equals(testingMethodSemantic)) {
            result = TaskSemantic.DATA_ANALYSIS;
        } else if (TestingMethod.QPCR.equals(testingMethodSemantic)) {
            result = TaskSemantic.QPCR;
        } else if (TestingMethod.SANGER_TEST.equals(testingMethodSemantic)) {
            result = TaskSemantic.SANGER_DATA_ANALYSIS;
        } else if (TestingMethod.MLPA_TEST.equals(testingMethodSemantic)) {
            result = TaskSemantic.MLPA_DATA_ANALYSIS;
        } else if (TestingMethod.DT.equals(testingMethodSemantic)) {
            result = TaskSemantic.DT_DATA_ANALYSIS;
        } else if (TestingMethod.FLUO_TEST.equals(testingMethodSemantic)) {
            result = TaskSemantic.FLUO_ANALYSE;
        }
        return result;
    }

    public TestingTaskResult getLastDataAnalysysTaskResultBySchedule(TestingSchedule schedule, String semantic) {
        String scheduleId = schedule.getId();
        String hql =
                "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId AND EXISTS( select t.id FROM TestingTask t WHERE tsh.taskId = t.id AND t.semantic =:semantic AND t.endType=1 AND t.endTime is not null ) ORDER BY tsh.timestamp DESC";
        List<TestingScheduleHistory> historyList =
                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"scheduleId", "semantic"}, new String[]{scheduleId, semantic});
        if (Collections3.isNotEmpty(historyList)) {
            TestingTask task = baseDaoSupport.get(TestingTask.class, historyList.get(0).getTaskId());
            if (null != task) {
                return baseDaoSupport.get(TestingTaskResult.class, task.getId());
            }
        }
        return null;
    }

    public List<TestingTaskResult> getLastDataAnalysysTasksResultBySchedule(TestingSchedule schedule, String semantic) {
        List<TestingTaskResult> records = Lists.newArrayList();
        String scheduleId = schedule.getId();
        String hql =
                "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId AND EXISTS( select t.id FROM TestingTask t WHERE tsh.taskId = t.id AND t.semantic =:semantic AND t.endTime is not null ) ORDER BY tsh.timestamp DESC";
        List<TestingScheduleHistory> historyList =
                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"scheduleId", "semantic"}, new String[]{scheduleId, semantic});
        if (Collections3.isNotEmpty(historyList)) {
            TestingTask task = baseDaoSupport.get(TestingTask.class, historyList.get(0).getTaskId());
            if (null != task) {
                records.add(baseDaoSupport.get(TestingTaskResult.class, task.getId()));
            }
        }
        return records;
    }

    public void setCombineCodeByScheduleAndAnalyRecord(TestingSchedule testingSchedule, TestingTechnicalAnalyRecord analyRecord, VerifyData verifyData) {
        String sampleId = testingSchedule.getSampleId();
        TestingSample testingSample = baseDaoSupport.get(TestingSample.class, sampleId);
        if (null != testingSample) {
            ReceivedSample receivedSample = testingSample.getReceivedSample();
            if (null != receivedSample) {
                verifyData.setSampleName(receivedSample.getSampleName());
                verifyData.setSampleCode(receivedSample.getSampleCode());
            }
        }
        String gene = analyRecord.getGeneSymbol();
        String chrLocation = analyRecord.getChrLocation();
        verifyData.setGene(gene);
        verifyData.setChromosomeLocation(chrLocation);
        verifyData.setExonRegion(analyRecord.getExon());
    }

    @Override
    @Transactional
    public String dealReport(TestingReportPagingRequest solveReportRequest) {
        String[] params = solveReportRequest.getWordPath().split("_");
        String orderCode = "";
        String productCode = "";
        String s = "";
        if (params.length == 5) {
            productCode = params[1];
            orderCode = params[2];

            TestingReport testingReport = getReportById(solveReportRequest.getId());

            if (null != testingReport) {
                Order order = testingReport.getOrder();
                Product product = testingReport.getProduct();
                if (null != order && null != product) {
                    if (order.getCode().equals(orderCode) && product.getCode().equals(productCode)) {

                    } else {
                        s = "1";
                    }
                }
            }

        } else {
            s = "2";
        }

        return s;

    }

    @Override
    @Transactional
    public ReturnModel updateTestingReport(TestingReportPagingRequest solveReportRequest) {
        String[] params = solveReportRequest.getWordPath().split("_");
        String orderCode = "";
        String productCode = "";
        String s = "";
        ReturnModel rm = new ReturnModel();
        List<Map<String, String>> orderProductIds = Lists.newArrayList();
        if (params.length == 5) {
            productCode = params[1];
            orderCode = params[2];

            TestingReport testingReport = getReportById(solveReportRequest.getId());

            if (null != testingReport) {
                Order order = testingReport.getOrder();
                Product product = testingReport.getProduct();
                if (null != order && null != product) {
                    if (order.getCode().equals(orderCode) && product.getCode().equals(productCode)) {
                        testingReport.setUpdateDate(new Date());
                        testingReport.setStatus(TestingReport.REPORT_STATUS_COMPLETED);
                        testingReport.setReportName(solveReportRequest.getWordPath());
                        testingReport.setFirstReviewStatus("0");
                        baseDaoSupport.update(testingReport);
                        // 更新orderproduct表
                        OrderProduct orderProduct = getOrderProductByOrderAndProduct(testingReport.getOrder().getId(), testingReport.getProduct().getId());
                        orderProduct.setReportTime(new Date());
                        orderProduct.setReportNo(testingReport.getReportCode());
                        orderProduct.setDataUrl(solveReportRequest.getPath());
                        orderProduct.setReportStatus(1);
                        orderProduct.setProductStatus(4);
                        baseDaoSupport.update(orderProduct);
                    }
                }
                Map<String, String> map = new HashMap<>();
                map.put("orderId", testingReport.getOrder().getId());
                map.put("productId", testingReport.getProduct().getId());
                orderProductIds.add(map);
                rm.setOrderProductIds(orderProductIds);
            }

        }
        return rm;
    }

    public TestingSheetTask getTestingSheetTaskByTaskId(String taskId) {
        String hql = "FROM TestingSheetTask tst WHERE tst.testingTaskId = :taskId";
        List<TestingSheetTask> records = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[]{"taskId"}, new String[]{taskId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public List<TestingTechnicalAnalyRecord> getTTARBySheetId(String sheetId) {
        String hql = "FROM TestingTechnicalAnalyRecord ttar WHERE ttar.sheet.id = :sheetId";
        return baseDaoSupport.findByNamedParam(TestingTechnicalAnalyRecord.class, hql, new String[]{"sheetId"}, new String[]{sheetId});
    }

    private Object getRecordMethodName(String sematic) {
        if ("MULTI-PCR".equals(sematic)) {
            return TestingMethod.MULTI_PCR;
        } else if ("Long-PCR".equals(sematic)) {
            return TestingMethod.LONG_PCR;

        } else if ("FLUO-TEST".equals(sematic)) {
            return "RTPCR";
        } else if ("DT".equals(sematic)) {
            return "DTTB";

        } else if ("SANGER-TEST".equals(sematic)) {
            return "SANGER";

        } else if ("MLPA-TEST".equals(sematic)) {
            return "MLPA";
        } else if ("NGS".equals(sematic)) {
            return "NGS";
        } else if ("CAP-NGS".equals(sematic)) {
            return "CapNGS";
        } else {
            return sematic;
        }

    }

    public ReceivedSample getBySampleCode(String sampleCode) {
        String hql = "FROM ReceivedSample r WHERE r.sampleCode = :sampleCode ";
        List<ReceivedSample> list = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[]{"sampleCode"}, new String[]{sampleCode});
        if (Collections3.isEmpty(list)) {
            return null;
        } else {
            return Collections3.getFirst(list);
        }
    }

    public List<TestingSchedule> getListByProductAndOrder(String orderId, String productId, boolean varify, String methodId) {
        String hql = "";
        List<TestingSchedule> records = Lists.newArrayList();
        if (varify) {
            hql = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId=:productId AND t.verifyKey is not null ";
            records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"orderId", "productId"}, new String[]{orderId, productId});
        } else {
            hql = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId=:productId AND t.methodId=:methodId ";
            records =
                    baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"orderId", "productId", "methodId"}, new String[]{orderId,
                            productId, methodId});
        }
        if (Collections3.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records;
    }

    public TestingMethod getTestMethodBySemantic(String semantic) {
        // 查找验证方法
        String hql = "FROM TestingMethod m WHERE m.semantic = :semantic AND m.enabled = :enabled";
        List<TestingMethod> methods =
                baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[]{"semantic", "enabled"}, new Object[]{semantic, true});

        return Collections3.getFirst(methods);
    }

    public List<TestingSchedule> getMultiOrLongPcr(TestingSchedule schedule) {
        String scheduleId = schedule.getId();
        String hql = "FROM TestingSchedule tsh WHERE tsh.verifyTarget = :scheduleId ";
        List<TestingSchedule> historyList = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"scheduleId"}, new String[]{scheduleId});
        if (Collections3.isEmpty(historyList)) {
            return Lists.newArrayList();
        }
        return historyList;
    }

    public TestingReport wrapForLane(TestingReport entity) {
        // ReceivedSample primarySample = getPrimarySampleByOrderId(entity.getOrder().getId());
        if (null != entity) {
            List<TestingSchedule> testingSchedules = Lists.newArrayList();
            TestingSample testingSample = extraSampleService.getTestingSampleBySampleCode(entity.getSampleCode());
            if (null != testingSample) {
                testingSchedules = getSchedules(entity.getOrder().getId(), entity.getProduct().getId(), testingSample.getId(), testingSchedules);
                if (Collections3.isEmpty(testingSchedules)) {
                    testingSchedules =
                            getScheduleBySampleCode(entity.getOrder().getId(), entity.getProduct().getId(), testingSample.getSampleCode(), testingSchedules);
                }
                // ww 2017.7.31
                if (null != testingSample.getReceivedSample()) {
                    // 重新送样

                    String hql = "FROM OrderPrimarySample  WHERE sampleErrorNewNo = :sampleErrorNewNo";
                    NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleErrorNewNo")).values(Lists.newArrayList(testingSample.getReceivedSample().getSampleCode())).build();
                    List<OrderPrimarySample> orderPrimarySamples = baseDaoSupport.find(queryer, OrderPrimarySample.class);

                    String sampleId = null;
                    if (orderPrimarySamples.size()!=0){
                        String hql1 = "FROM ReceivedSample  WHERE sampleCode = :sampleCode";
                        NamedQueryer queryer1 = NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(orderPrimarySamples.get(0).getSampleCode())).build();
                        List<ReceivedSample> receivedSamples = baseDaoSupport.find(queryer1, ReceivedSample.class);
                        if (Collections3.isNotEmpty(receivedSamples)){
                            sampleId = testingSampleService.getTestingSampleByReceivedSample(receivedSamples.get(0).getSampleId()).getId();
                        }

                    }else{
                        sampleId = testingSample.getId();
                    }

                   testingSchedules =
                            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                    "from TestingSchedule t where t.orderId=:orderId " + "and t.productId=:productId  and t.sampleId=:sampleId ",
                                    new String[] {"orderId", "productId", "sampleId"},
                                    new Object[] {entity.getOrder().getId(), entity.getProduct().getId(), sampleId});



                }

                StringBuffer sequenceCodes = new StringBuffer(256);
                Set<String> set = new HashSet<>();
                for (TestingSchedule ts : testingSchedules) {
                    String sequenceCode = scheduleService.getSequenceCode(ts.getId());
                    set.add(sequenceCode);
                }
                String[] arrayResult = set.toArray(new String[set.size()]);
                if (Collections3.isNotEmpty(arrayResult)) {
                    for (String s : arrayResult) {
                        if (StringUtils.isEmpty(sequenceCodes)) {
                            sequenceCodes.append(s);
                        } else if (StringUtils.isNotEmpty(s)) {
                            sequenceCodes.append(",").append(s);
                        }
                    }
                }
                entity.setSequenceCode(sequenceCodes.toString());
            }
        }
        // 重新计算流程完成百分比
        List<TestingSchedule> schedules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId", new String[]{
                        "orderId", "productId"}, new Object[]{entity.getOrder().getId(), entity.getProduct().getId()});
        entity.setTotalNum(schedules.size());
        int complete = 0;
        for (TestingSchedule schedule : schedules) {
            if (StringUtils.isNotEmpty(schedule.getEndType())) {
                complete++;
            }
        }
        entity.setCompleteNum(complete);
        return null;
    }

    public List<TestingSchedule> getScheduleBySampleCode(String orderId, String productId, String sampleCode, List<TestingSchedule> testingSchedules) {
        String hql = "SELECT rs.sampleId FROM ReceivedSample rs WHERE rs.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<String> sampleIds = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(sampleIds)) {
            testingSchedules = getSchedules(orderId, productId, Collections3.getFirst(sampleIds), testingSchedules);
        }
        return testingSchedules;
    }

    public List<TestingSchedule> getSchedules(String orderId, String productId, String sampleId, List<TestingSchedule> testingSchedules) {
        String hql = "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.sampleId = :sampleId";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("orderId", "productId", "sampleId"))
                        .values(Lists.newArrayList(orderId, productId, sampleId))
                        .build();
        testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
        return testingSchedules;
    }

    // 判断是否重新送样
    public boolean isResampling(String resendSampleId) {
        String hql = "FROM TestingResamplingRecord ts WHERE ts.resendSampleId = :resendSampleId AND ts.resendSampleStatus = '2'";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("resendSampleId")).values(Lists.newArrayList(resendSampleId)).build();
        List<TestingResamplingRecord> list = baseDaoSupport.find(queryer, TestingResamplingRecord.class);
        if (Collections3.isNotEmpty(list)) {
            return true;
        }
        return false;
    }

    public List<TestingSchedule> getScheduleByTestingSampleId(String testingSampleId) {
        List<TestingTask> taskList =
                baseDaoSupport.find(TestingTask.class, "FROM TestingTask t WHERE t.inputSample.id = '" + testingSampleId + "' ORDER BY t.startTime DESC");
        if (Collections3.isNotEmpty(taskList)) {
            String hql = "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id ";
            NamedQueryer queryer =
                    NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskList.get(0).getId())).build();
            List<TestingSchedule> list = baseDaoSupport.find(queryer, TestingSchedule.class);
            if (Collections3.isNotEmpty(list)) {
                return list;
            }
        }

        return null;

    }

    @Override
    public TestingReport get(String id) {
        TestingReport report = baseDaoSupport.get(TestingReport.class, id);
        wrap(report);
        return report;
    }

    @Override
    public List<TestingReport> getReportListByOrderId(String orderId) {
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql("FROM TestingReport t where t.order.id = :orderId AND t.delFlag=false ")
                        .names(Lists.newArrayList("orderId"))
                        .values(Lists.newArrayList(orderId))
                        .build();
        return baseDaoSupport.find(queryer, TestingReport.class);
    }

    public ReceivedSample getPrimarySampleByOrderId(String orderId) {
        ReceivedSample result = null;
        String hql = "FROM ReceivedSample r WHERE r.orderId = :orderId AND r.businessType = 1 ";
        List<ReceivedSample> records = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[]{"orderId"}, new String[]{orderId});
        if (Collections3.isNotEmpty(records)) {
            if (1 == records.size()) {
                result = Collections3.getFirst(records);
            } else {
                for (ReceivedSample receivedSample : records) {
                    String sampleCode = receivedSample.getSampleCode();
                    if (!isSampleNormal(sampleCode)) {
                        continue;
                    }
                    // 重新送样后 的流程sampleID并没有更新 不考虑之前两个主样本都正常的情况了
                    return receivedSample;
                }
            }

        }
        return result;
    }

    private boolean isSampleNormal(String sampleCode) {
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql("SELECT COUNT (*) FROM OrderPrimarySample o WHERE o.sampleCode = :sampleCode AND o.samplePackageStatus = 2 ")
                        .names(Arrays.asList("sampleCode"))
                        .values(Arrays.asList(sampleCode))
                        .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;

    }

    // 验证的模板
    private DataTemplate getVerifyDataTemplateByMethodId(String semantic) {
        String hql = "FROM DataTemplate d WHERE d.delFlag = false AND d.testingMethod.semantic='" + semantic + "'";
        List<DataTemplate> list = baseDaoSupport.find(DataTemplate.class, hql);
        return Collections3.isNotEmpty(list) ? list.get(0) : null;
    }

    // 检测的模板
    private DataTemplate getTestDataTemplateBySchedule(TestingSchedule schedule) {
        String methodId = schedule.getMethodId();
        String productId = schedule.getProductId();
        String hql =
                "FROM DataTemplate t WHERE t.delFlag = false AND EXISTS (select ptm.id FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId AND ptm.dataTemplateId = t.id )";
        List<DataTemplate> records =
                baseDaoSupport.findByNamedParam(DataTemplate.class, hql, new String[]{"methodId", "productId"}, new Object[]{methodId, productId});
        return Collections3.getFirst(records);
    }

    // 检测的模板
    private DataTemplate getTestDataTemplateById(String productId, String methodId) {
        String hql =
                "FROM DataTemplate t WHERE t.delFlag = false AND EXISTS (select ptm.id FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId AND ptm.dataTemplateId = t.id )";
        List<DataTemplate> records =
                baseDaoSupport.findByNamedParam(DataTemplate.class, hql, new String[]{"methodId", "productId"}, new Object[]{methodId, productId});
        return Collections3.getFirst(records);
    }

    @Override
    public ReportHandleModel getHandleModel(String id) {
        TestingReport report = get(id);
        if (null != report && null != report.getOrder()) {
            ReportHandleModel model = new ReportHandleModel();
            List<TestingMethod> testingMethods = Lists.newArrayList();

            OrderProduct op = getOp(report.getOrder().getId(), report.getProduct().getId());
            if (null != op && StringUtils.isNotEmpty(report.getReportName()) && StringUtils.isNotEmpty(op.getDataUrl())) {
                model.setCombineUrl(report.getReportName() + "/" + op.getDataUrl());
            }

            model.setId(report.getId());
            model.setOrderId(report.getOrder().getId());
            model.setOrderCode(report.getOrder().getCode());
            model.setReportStatus(report.getStatus());
            model.setReportTemplateType(report.getReportTemplateType());
            model.setReportType(report.getReportType());
            model.setAnalyType(report.getAnalyResult());
            model.setReportTemplateType(report.getReportTemplateType());
            model.setInstruction(report.getInstruction());
            if (null != report.getProduct()) {
                model.setProductCode(report.getProduct().getCode());
            }
            if (null != report.getProduct()) {
                Set<String> testMethods = new HashSet();
                // 检测方法
                for (ProductTestingMethod ptm : report.getProduct().getProductTestingMethodList()) {
                    testingMethods.add(ptm.getTestingMethod());
                    testMethods.add(ptm.getTestingMethod().getId());
                }
                //验证方法
                /*String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId AND t.verifyKey is not null";
                List<TestingSchedule> yzSchedules =
                    baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId"}, new String[] {report.getOrder().getId(),report.getProduct().getId()});
                if(Collections3.isNotEmpty(yzSchedules))
                {
                    for(TestingSchedule ts : yzSchedules)
                    {
                        testMethods.add(ts.getMethodId());
                    }
                }*/
                // 图片
                List<TestingDataPic> pictures = getPictures(report.getOrder().getId(), report.getProduct().getId(), testMethods);
                if (Collections3.isNotEmpty(pictures)) {
                    TestingMethod pic = new TestingMethod();
                    pic.setName("图片");
                    pic.setSemantic("PICTURE");
                    testingMethods.add(pic);
                }
                // 验证方法
                List<TestingMethod> verifyMethods = getVerifyMethods(report.getOrder().getId(), report.getProduct().getId());
                if (Collections3.isNotEmpty(verifyMethods)) {
                    testingMethods.addAll(verifyMethods);
                }

                sortTestName(testingMethods);

                model.setTestingMethods(testingMethods);
            }
            return model;
        }
        return null;
    }

    @Override
    @Transactional
    public ReportProcessModel getProcessModel(ReportProcessModelArgs args) {
        ReportProcessModel model = new ReportProcessModel();
        if (null != args && StringUtils.isNotEmpty(args.getId())) {
            // ww 2017.7.20 获取勾选的结果ids
            String recordIds = "";
            List<TestingReportTempSave> reportTempSave = getReportTempSaves(args.getId(), args.getSemantic());
            if (Collections3.isNotEmpty(reportTempSave)) {
                for (TestingReportTempSave des : reportTempSave) {
                    recordIds += des.getRecordId() + ",";
                }
            }
            if (StringUtils.isNotEmpty(recordIds)) {
                recordIds = recordIds.substring(0, recordIds.length() - 1);
            }
            model.setCheckRecordIds(recordIds);

            model.setId(args.getId());
            model.setSemantic(args.getSemantic());
            String methodId = getMethodId(args.getSemantic());
            TestingReport report = get(args.getId());
            if (null != report && null != report.getOrder()) {
                model.setReportName(report.getReportName() == null ? "" : report.getReportName());
                // 检测样本
                List<TestSampleModel> testSamples = getTestSample(report, methodId);
                model.setTestSamples(testSamples);

                // 图片
                Set<String> testMethods = new HashSet();
                if (null != report.getProduct() && Collections3.isNotEmpty(report.getProduct().getProductTestingMethodList())) {
                    for (ProductTestingMethod ptm : report.getProduct().getProductTestingMethodList()) {
                        testMethods.add(ptm.getTestingMethod().getId());
                    }
                }
                //验证方法
                /*String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId AND t.verifyKey is not null";
                List<TestingSchedule> yzSchedules =
                    baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId"}, new String[] {report.getOrder().getId(),report.getProduct().getId()});
                if(Collections3.isNotEmpty(yzSchedules))
                {
                    for(TestingSchedule ts : yzSchedules)
                    {
                        testMethods.add(ts.getMethodId());
                    }
                }*/
                List<TestingDataPic> pics = getPictures(report.getOrder().getId(), report.getProduct().getId(), testMethods);
                List<ReportTestingPicture> pictures = Lists.newArrayList();
                for (TestingDataPic tdp : pics) {
                    ReportTestingPicture temp = wrapPic(tdp);
                    // if (temp.isSampleResampling())
                    // {
                    // continue;
                    // }
                    pictures.add(temp);
                }
                pictures.sort(comparing(ReportTestingPicture::getMethodName).thenComparing(ReportTestingPicture::getSort));
                model.setPictures(pictures);
                model.setPictureIds(report.getPictures());

                // 验证样本
                List<VerifySampleModel> verifySamples = getVerifySamples(report, methodId);
                model.setVerifySamples(verifySamples);
                saveReportSample(args, report);
                // 检测流程的提交结果
                if (Collections3.isNotEmpty(testSamples) && null != report.getProduct()) {
                    List<TestingSchedule> schedules = getSchedules(report.getOrder().getId(), report.getProduct().getId(), methodId);
                    if (Collections3.isNotEmpty(schedules)) {
                        // 获取模板表头
                        List<String> columnNames = Lists.newArrayList();
                        for (TestingSchedule schedule : schedules) {
                            TestingSheet ts = getTestingSheet(schedule.getId());
                            if (null != ts) {
                                DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(ts.getId());
                                // dataTemplate.getColumnList().stream().forEach(x ->
                                // columnNames.add(x.getColumnName()));
                                if (null != dataTemplate) {
                                    for (DataTemplateColumn dtc : dataTemplate.getColumnList()) {
                                        columnNames.add(dtc.getColumnName());
                                    }
                                    model.setColumnNames(columnNames);
                                    break;
                                }
                            }
                        }

                        // 获取所有样本下数据分析提交数据
                        List<String> sheetIds = Lists.newArrayList();
                        List<List<String>> valuesLists = Lists.newArrayList();
                        List<String> dataCodes = Lists.newArrayList();

                        List<String> scheduleList = Lists.newArrayList();
                        for (TestSampleModel testSample : testSamples) {
                            scheduleList.add(testSample.getScheduleId());
                        }
                        for (TestingSchedule schedule : schedules) {
                            NamedQueryer testingScheduleHistoryQuery =
                                    NamedQueryer.builder()
                                            .hql("FROM TestingScheduleHistory WHERE scheduleId = :scheduleId and taskRefer=:taskRefer")
                                            .names(Lists.newArrayList("scheduleId", "taskRefer"))
                                            .values(Lists.newArrayList(schedule.getId(), TestingScheduleHistory.TASK_REFER_TECHNICAL_ANALYSIS))
                                            .build();
                            List<TestingScheduleHistory> testingScheduleHistorys =
                                    baseDaoSupport.find(testingScheduleHistoryQuery, TestingScheduleHistory.class);
                            //通过
                            if (Collections3.isNotEmpty(testingScheduleHistorys)) {
                                String technicalAnalysisTaskId = testingScheduleHistorys.get(0).getTaskId();
                                if (StringUtils.isNotBlank(technicalAnalysisTaskId)) {

                                    NamedQueryer technicalAnalysisTaskQuery =
                                            NamedQueryer.builder()
                                                    .hql("FROM TechnicalAnalysisTask WHERE id = :id")
                                                    .names(Lists.newArrayList("id"))
                                                    .values(Lists.newArrayList(technicalAnalysisTaskId))
                                                    .build();
                                    List<TechnicalAnalysisTask> technicalAnalysisTasks =
                                            baseDaoSupport.find(technicalAnalysisTaskQuery, TechnicalAnalysisTask.class);
                                    if (Collections3.isNotEmpty(technicalAnalysisTasks)) {
                                        model.setTechnicalFamilyGroupId(technicalAnalysisTasks.get(0).getFamilyGroupId());
                                        log.info("查找突变位点信息，technicalFamilyGroupId为：" + technicalAnalysisTasks.get(0).getFamilyGroupId());

                                        List<TestingTechnicalAnalyRecord> ttaRecords =
                                                getAnalyRecordByFamilyGroupID(technicalAnalysisTasks.get(0).getFamilyGroupId());
                                        List<MutationSourceUpdateModel> mutationSourceUpdateModels = new ArrayList<>();
                                        UpdateMutationSourceRequest mutationSourceRequest = new UpdateMutationSourceRequest();
                                        mutationSourceRequest.setMutationSourceUpdateModels(mutationSourceUpdateModels);
                                        for (TestingTechnicalAnalyRecord ttar : ttaRecords) {
                                            //获取遗传来源
                                            JSONObject temp = new JSONObject();
                                            temp.put("验证方法", ttar.getVerifyMethod());
                                            temp.put("Sample", ttar.getSample());
                                            temp.put("ID", ttar.getChromosomalLocation());
                                            String mutationSource = getMutationSource(temp, report);
                                            //组装遗传来源
                                            MutationSourceUpdateModel ms = new MutationSourceUpdateModel();
                                            ms.setId(ttar.getMutationObjectId());
                                            ms.setMutationSource(mutationSource);
                                            mutationSourceUpdateModels.add(ms);
                                        }
                                        updateMutationSource(mutationSourceRequest);
                                        DataColAndMutationDataModel dataColAndMutationDataModel =
                                                searchMutationListByAnalsysiSampleId(technicalAnalysisTasks.get(0).getFamilyGroupId());
                                        List<CollectionCnvAnalysisPicResultList> collectionCnvAnalysisPicResultList =
                                                collectionCapcnvData(technicalAnalysisTasks.get(0).getFamilyGroupId());
                                        model.setDataColAndMutationDataModel(dataColAndMutationDataModel);
                                        model.setCnvModel(collectionCnvAnalysisPicResultList);
                                    }
                                }
                            } else {
                                TestingSheet testingSheet = getTestingSheet(schedule.getId());
                                if (null != testingSheet && !sheetIds.contains(testingSheet.getId())) {
                                    sheetIds.add(testingSheet.getId());
                                    NamedQueryer query =
                                            NamedQueryer.builder()
                                                    .hql("FROM TestingTechnicalAnalyRecord ttar WHERE ttar.sheet.id = :sheetId")
                                                    .names(Lists.newArrayList("sheetId"))
                                                    .values(Lists.newArrayList(testingSheet.getId()))
                                                    .build();
                                    List<TestingTechnicalAnalyRecord> subRecordList = baseDaoSupport.find(query, TestingTechnicalAnalyRecord.class);
                                    if (Collections3.isNotEmpty(subRecordList)) {
                                        // 有验证方法的 cap-ngs ngs
                                        for (TestingTechnicalAnalyRecord ttar : subRecordList) {
                                            /*
                                             * if(!dataCodes.contains(ttar.getDataCode())){
                                             * dataCodes.add(ttar.getDataCode());
                                             * }
                                             */
                                            TestingSchedule targetSchedule = getTargetScheduleByAnalyRecord(ttar.getId(), ttar.getVerifyMethod());
                                            // String sId = getScheduleIdByVerifyKey(ttar.getId());
                                            if (null != targetSchedule) {
                                                if (scheduleList.contains(targetSchedule.getId())) {
                                                    List<String> columnValues = Lists.newArrayList();
                                                    JSONObject temp = JSONObject.parseObject(ttar.getOtherFields());
                                                    if (null != temp) {
                                                        columnValues.add(ttar.getId());
                                                        columnValues.add(ttar.getClinicalJudgment());
                                                        String mutationSource = getMutationSource(temp, report);
                                                        if (isPrimarySample(temp.getString("Sample"))) {
                                                            if (StringUtils.isNotEmpty(ttar.getMutationSource())) {
                                                                columnValues.add(ttar.getMutationSource());
                                                            } else {
                                                                columnValues.add(mutationSource);
                                                            }
                                                        } else {
                                                            columnValues.add("");
                                                        }

                                                        for (String name : columnNames) {
                                                            if (!"临床相关性判断".equals(name) && !"突变来源".equals(name)) {
                                                                columnValues.add(temp.getString(name));
                                                            }
                                                        }
                                                    }
                                                    if (Collections3.isNotEmpty(columnValues)) {
                                                        valuesLists.add(columnValues);
                                                    }
                                                }
                                            } else
                                            // 不验证
                                            {
                                                String sample = "";
                                                if (StringUtils.isNotEmpty(ttar.getDataCode())) {
                                                    String[] dataCode = ttar.getDataCode().split("_");
                                                    if (dataCode.length > 0) {
                                                        sample = dataCode[0];
                                                    }
                                                }
                                                if (Collections3.isNotEmpty(testSamples)) {
                                                    for (TestSampleModel sampleModel : testSamples) {
                                                        if (sample.equals(sampleModel.getSampleCode())) {
                                                            List<String> columnValues = Lists.newArrayList();
                                                            JSONObject temp = JSONObject.parseObject(ttar.getOtherFields());
                                                            if (null != temp) {
                                                                columnValues.add(ttar.getId());
                                                                columnValues.add(ttar.getClinicalJudgment());

                                                                String mutationSource = getMutationSource(temp, report);
                                                                if (isPrimarySample(temp.getString("Sample"))) {
                                                                    if (StringUtils.isNotEmpty(ttar.getMutationSource())) {
                                                                        columnValues.add(ttar.getMutationSource());
                                                                    } else {
                                                                        columnValues.add(mutationSource);
                                                                    }
                                                                } else {
                                                                    columnValues.add("");
                                                                }

                                                                for (String name : columnNames) {
                                                                    if (!"临床相关性判断".equals(name) && !"突变来源".equals(name)) {
                                                                        columnValues.add(temp.getString(name));
                                                                    }
                                                                }
                                                            }
                                                            if (Collections3.isNotEmpty(columnValues)) {
                                                                valuesLists.add(columnValues);
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            String methodTaskSemantic = getByTestingMethod(schedule);// 数据分析任务关键字
                            if (!TaskSemantic.SANGER_DATA_ANALYSIS.equals(methodTaskSemantic)) {
                                TestingTaskResult dataAnalysisTaskResult = getLastDataAnalysysTaskResultBySchedule(schedule, methodTaskSemantic);
                                if (null == dataAnalysisTaskResult) {
                                    continue;
                                }
                                String details = dataAnalysisTaskResult.getDetails();
                                if (StringUtils.isNotEmpty(details)) {
                                    if (TaskSemantic.FLUO_ANALYSE.equals(methodTaskSemantic)) {
                                        FluoAnalyseSubmitTaskModel fluoanalysesubmittaskmodel = JsonUtils.asObject(details, FluoAnalyseSubmitTaskModel.class);
                                        if (null != fluoanalysesubmittaskmodel) {
                                            List<FluoDataSubmitTaskSuccessArgs> fargs = fluoanalysesubmittaskmodel.getSuccessArgs();
                                            if (Collections3.isNotEmpty(fargs)) {
                                                for (FluoDataSubmitTaskSuccessArgs data : fargs) {
                                                    Map<String, String> map = data.getMap();
                                                    List<String> columnValues = getColumnValues(dataAnalysisTaskResult, map, columnNames);
                                                    valuesLists.add(columnValues);
                                                }
                                            }
                                        }
                                    } else if (TaskSemantic.DT_DATA_ANALYSIS.equals(methodTaskSemantic)) {
                                        DTDataSubmitTaskArgs taskArgs = JsonUtils.asObject(details, DTDataSubmitTaskArgs.class);
                                        if (null != taskArgs) {
                                            List<DTDataSubmitTaskSuccessArgs> dargs = taskArgs.getSuccessArgs();
                                            if (Collections3.isNotEmpty(dargs)) {
                                                for (DTDataSubmitTaskSuccessArgs data : dargs) {
                                                    Map<String, String> map = data.getMap();
                                                    List<String> columnValues = getColumnValues(dataAnalysisTaskResult, map, columnNames);
                                                    valuesLists.add(columnValues);
                                                }
                                            }
                                        }
                                    } else if (TaskSemantic.MLPA_DATA_ANALYSIS.equals(methodTaskSemantic)) {
                                        MlpaDataSubmitTaskModel taskArgs = JsonUtils.asObject(details, MlpaDataSubmitTaskModel.class);
                                        if (null != taskArgs) {
                                            List<MlpaDataSubmitTaskSuccessArgs> list = taskArgs.getSuccessArgs();
                                            if (Collections3.isNotEmpty(list)) {
                                                for (MlpaDataSubmitTaskSuccessArgs data : list) {
                                                    Map<String, String> map = data.getMap();
                                                    List<String> columnValues = getColumnValues(dataAnalysisTaskResult, map, columnNames);
                                                    valuesLists.add(columnValues);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                List<TestingTaskResult> sangerResults = getLastDataAnalysysTasksResultBySchedule(schedule, methodTaskSemantic);
                                if (Collections3.isEmpty(sangerResults)) {
                                    continue;
                                }
                                List<String> combineCodes = Lists.newArrayList();
                                for (TestingTaskResult result : sangerResults) {
                                    String details = result.getDetails();
                                    if (StringUtils.isNotEmpty(details)) {
                                        DataPcrSangerSubmitTaskArgs dataPcrSangerSubmitTaskArgs =
                                                JsonUtils.asObject(details, DataPcrSangerSubmitTaskArgs.class);
                                        if (null != dataPcrSangerSubmitTaskArgs) {
                                            if (!combineCodes.contains(dataPcrSangerSubmitTaskArgs.getCombineCode())) {
                                                Map<String, String> map = dataPcrSangerSubmitTaskArgs.getMap();
                                                List<String> columnValues = getColumnValues(result, map, columnNames);
                                                valuesLists.add(columnValues);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //验证 不验证排序
                        if (Collections3.isNotEmpty(valuesLists)) {
                            for (List<String> ls : valuesLists) {
                                if (ls.contains("验证")) {
                                    ls.add("1");
                                } else if (ls.contains("不验证")) {
                                    ls.add("2");
                                } else {
                                    ls.add("3");
                                }
                            }
                        }
                        Collections.sort(valuesLists, new Comparator<List<String>>() {
                            @Override
                            public int compare(List<String> o1, List<String> o2) {

                                if (Integer.parseInt(o1.get(o1.size() - 1)) > Integer.parseInt(o2.get(o2.size() - 1))) {
                                    return 1;
                                }
                                if (Integer.parseInt(o1.get(o1.size() - 1)) < Integer.parseInt(o2.get(o2.size() - 1))) {
                                    return -1;
                                }
                                return 0;
                            }
                        });
                        model.setColumnValues(valuesLists);
                    }
                }


                String isFamilySample = "false";
                if ("new".equals(tag))
                {
                    if (report.getOrder() != null) ;
                    {
                        Order od = report.getOrder();
                        if (model.getTestSamples().size() > 1 && "临床遗传".equals(od.getType().getName())) //临床遗传的订单，检测样本（本人、检测、对照）数量大于1，会走家系
                        {
                            isFamilySample = "true";
                        }
                    }
                }
                model.setIsFamilySample(isFamilySample);
            }
        }
        return model;
    }

    @Override
    public void updateMutationSource(UpdateMutationSourceRequest mutationSourceRequest) {
        String url = GatewayService.getServiceUrl("/technicalanaly/reportexport/updateMutationSource");
        template.postForLocation(url, mutationSourceRequest);
    }

    private void saveReportSample(ReportProcessModelArgs args, TestingReport report) {
        try {
            List<TestingMethod> testingMethods = Lists.newArrayList();
            if (null != report && null != report.getOrder() && null != report.getProduct()) {
                Set<String> testMethods = new HashSet();
                // 检测方法
                for (ProductTestingMethod ptm : report.getProduct().getProductTestingMethodList()) {
                    testingMethods.add(ptm.getTestingMethod());
                    testMethods.add(ptm.getTestingMethod().getId());
                }
                // 验证方法
                List<TestingMethod> verifyMethods = getVerifyMethods(report.getOrder().getId(), report.getProduct().getId());
                if (Collections3.isNotEmpty(verifyMethods)) {
                    testingMethods.addAll(verifyMethods);
                }
                sortTestName(testingMethods);
            }
            String semantic = "";
            if (Collections3.isNotEmpty(testingMethods)) {
                semantic = Collections3.getFirst(testingMethods).getSemantic();
            }
            if (semantic.equals(args.getSemantic()))//页面初次加载的时候，即是页面是默认页面
            {
                for (TestingMethod method : testingMethods) {
                    if (method.getSemantic().equals("SANGER")) {
                        List<VerifySampleModel> sangerVerifySamples = getVerifySamples(report, method.getId());
                        ReportProcessModel sangerProcessModel = new ReportProcessModel();
                        sangerProcessModel.setId(args.getId());
                        sangerProcessModel.setSemantic(method.getSemantic());
                        sangerProcessModel.setVerifySamples(sangerVerifySamples);
                        saveTestingReportSample(sangerProcessModel);
                    } else if (method.getSemantic().equals("PCR-NGS")) {
                        List<VerifySampleModel> pcrVerifySamples = getVerifySamples(report, method.getId());
                        ReportProcessModel pcrProcessModel = new ReportProcessModel();
                        pcrProcessModel.setId(args.getId());
                        pcrProcessModel.setSemantic(method.getSemantic());
                        pcrProcessModel.setVerifySamples(pcrVerifySamples);
                        saveTestingReportSample(pcrProcessModel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TestingTechnicalAnalyRecord> getAnalyRecordByFamilyGroupID(String familyGroupId) {
        String hql = "select t FROM TestingTechnicalAnalyRecord t,TechnicalAnalysisTask tat WHERE t.technicalFamilyGroupId=tat.familyGroupId and tat.status=5 and t.technicalFamilyGroupId = :technicalFamilyGroupId and t.sourceRef = '1' ";
        List<TestingTechnicalAnalyRecord> records =
                baseDaoSupport.findByNamedParam(TestingTechnicalAnalyRecord.class, hql, new String[]{"technicalFamilyGroupId"}, new String[]{familyGroupId});
        return records;
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTestingReportSample(ReportProcessModel request) {
        if (Collections3.isNotEmpty(request.getVerifySamples())) {
            for (VerifySampleModel sample : request.getVerifySamples()) {
                String methodId = getMethodId(request.getSemantic());
                TestingReportSample sampleResult = getReportSample(request.getId(), methodId, sample.getId(), sample.getScheduleId());
                if (null == sampleResult) {
                    sampleResult = new TestingReportSample();
                    sampleResult.setPurpose("1");
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setReportId(request.getId());
                    sampleResult.setSampleId(sample.getId());
                    sampleResult.setScheduleId(sample.getScheduleId());
                    sampleResult.setMethodId(getMethodId(request.getSemantic()));
                    sampleResult.setUpdateTime(new Date());
                    sampleResult.setCombineType(sample.getCombineType());
                    sampleResult.setMutationSource(sample.getMutationSource());
                    sampleResult.setCombineCode(sample.getCombineCode());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                    }
                    baseDaoSupport.insert(sampleResult);
                } else {
                    // 已经保存过的记录只更新
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setUpdateTime(new Date());
                    sampleResult.setCombineType(sample.getCombineType());
                    sampleResult.setMutationSource(sample.getMutationSource());
                    sampleResult.setCombineCode(sample.getCombineCode());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                    }
                    baseDaoSupport.update(sampleResult);
                }
            }
        }
    }

    private List<TestSampleModel> getTestSample(TestingReport report, String methodId) {
        String hql = "FROM ReceivedSample rs WHERE rs.orderId = :orderId AND (rs.purpose != '1' OR rs.purpose IS NULL)";
        NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("orderId")).values(Lists.newArrayList(report.getOrder().getId())).build();
        List<ReceivedSample> receivedSamples = baseDaoSupport.find(queryer, ReceivedSample.class);
        List<TestSampleModel> testSamples = Lists.newArrayList();
        for (ReceivedSample rs : receivedSamples) {
            TestSampleModel tsm = new TestSampleModel();
            tsm.setId(rs.getSampleId());
            tsm.setSampleCode(rs.getSampleCode());
            tsm.setSampleName(rs.getSampleName());
            tsm.setSampleTypeName(rs.getSampleTypeName());
            tsm.setRelation(getRelation(rs.getSampleCode()));
            String scheduleId = getScheduleId(report.getOrder().getId(), report.getProduct().getId(), methodId, rs.getSampleId());
            if (StringUtils.isEmpty(scheduleId)) {
                scheduleId = getScheduleId(report.getOrder().getId(), report.getProduct().getId(), methodId, getSampleFromTestingSample(rs.getSampleCode()));
            }
            //重新送样(获取原样本schedule) ww 2017.8.7
            if (StringUtils.isEmpty(scheduleId)) {
                scheduleId =
                        getScheduleId(report.getOrder().getId(),
                                report.getProduct().getId(),
                                methodId,
                                getAbnormalSampleIdForTestingSampleOrReceiveSample(rs.getSampleId()));
            }
            boolean cancelFlag = true;
            if (StringUtils.isNotEmpty(scheduleId)) {
                TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
                if (TestingSchedule.END_SUCCESS.equals(schedule.getEndType())) {
                    tsm.setEndFlag(true);
                }
                if (schedule.getActiveTask().contains("取消") || 2 == schedule.getProccessState()) {
                    cancelFlag = false;
                }
            }
            tsm.setScheduleId(scheduleId);

            setSampleResult(tsm, report.getId(), methodId, rs.getSampleId());

            //ww 
            if (isReSample(rs.getSampleId())) {
                tsm.setIsResampling(1);
            }

            if (StringUtils.isNotEmpty(scheduleId)) {
                if (cancelFlag) {
                    testSamples.add(tsm);
                }
            }
        }
        List<TestSampleModel> map = new ArrayList<>();
        for (TestSampleModel testSampleModel : testSamples) {
            String hql2 = "SELECT GROUP_CONCAT(DISTINCT rsam.SAMPLE_CODE ORDER BY tt.START_TIME SEPARATOR '/') AS codes FROM LIMS_TESTING_SCHEDULE_HISTORY tsh , LIMS_TESTING_TASK tt , LIMS_TESTING_SAMPLE tsam ,LIMS_RECEIVED_SAMPLE rsam   WHERE tt.semantic != 'PRIMER-DESIGN'AND tt.semantic != 'PCR-NGS-PRIMER-DESIGN'AND tsh.SCHEDULE_ID =?  AND tt.ID = tsh.TASK_ID  AND tsam.ID = tt.INPUT_SAMPLE_ID AND rsam.SAMPLE_ID = tsam.ORIGINAL_SAMPLE_ID GROUP BY tsh.SCHEDULE_ID";
            List<String> list = (List<String>) baseDaoSupport.findBySql(hql2, testSampleModel.getScheduleId());
            String a = list.get(0);
            List<String> idList = Arrays.asList(a.split("/"));
            if (idList.contains(testSampleModel.getSampleCode())) {
                map.add(testSampleModel);
            }
        }

        Map<String, List<TestSampleModel>> ss = map.stream().collect(Collectors.groupingBy(TestSampleModel::getSampleName));
        MutableObject a = new MutableObject();
        ss.forEach((k, v) -> {
            if (v.size() > 1) {
                a.setValue(k);

            }
        });


        return  map.stream().map(x -> {
            if (!x.getSampleName().equals(a.getValue())) {
                x.setIsResampling(0);
            }
            return x;
        }).collect(toList());
    }

    private String getRelation(String sampleCode) {
        String hql =
                "SELECT d.text FROM OrderSubsidiarySample oss, Dict d WHERE oss.sampleCode = :sampleCode AND d.category = 'FAMILY_RELATION' AND d.value = oss.familyRelation";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<String> relations = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(relations)) {
            return Collections3.getFirst(relations);
        } else {
            return "本人";
        }
    }

    private List<TestingSchedule> getSchedules(String orderId, String productId, String methodId) {
        String hql = "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.proccessState != 2";//取消的不同显示 ww 2017.8.2
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("orderId", "productId", "methodId"))
                        .values(Lists.newArrayList(orderId, productId, methodId))
                        .build();
        List<TestingSchedule> testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
        return testingSchedules;
    }

    private String getMethodId(String semantic) {
        String methodId = "";
        String hql = "FROM TestingMethod tm WHERE tm.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList(semantic)).build();
        List<TestingMethod> methods = baseDaoSupport.find(queryer, TestingMethod.class);
        if (Collections3.isNotEmpty(methods)) {
            TestingMethod method = Collections3.getFirst(methods);
            methodId = method.getId();
        }
        return methodId;
    }

    private TestingSheet getTestingSheet(String scheduleId) {
        String hql =
                "SELECT tst.testingSheet FROM TestingScheduleHistory tsh, TestingSheetTask tst WHERE tsh.scheduleId = :scheduleId AND tst.testingTaskId = tsh.taskId ORDER BY tsh.timestamp";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
        List<TestingSheet> sheetTasks = baseDaoSupport.find(queryer, TestingSheet.class);
        if (Collections3.isNotEmpty(sheetTasks)) {
            return Collections3.getLast(sheetTasks);
        }
        return null;
    }

    private String getScheduleId(String orderId, String productId, String methodId, String sampleId) {
        String hql =
                "SELECT ts.id FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                        .values(Lists.newArrayList(orderId, productId, methodId, sampleId))
                        .build();
        List<String> scheduleIds = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(scheduleIds)) {
            return Collections3.getFirst(scheduleIds);
        }
        return null;
    }

    private String getSampleFromTestingSample(String sampleCode) {
        String hql = "SELECT ts.id FROM TestingSample ts WHERE ts.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<String> sampleIds = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(sampleIds)) {
            return Collections3.getFirst(sampleIds);
        }
        return null;
    }

    @Override
    @Transactional
    public String reportEdit(TestingReport report) {
        TestingReport entity = get(report.getId());
        entity.setInstruction(report.getInstruction());
        baseDaoSupport.update(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void saveTestingResult(ReportProcessModel request) {
        if (Collections3.isNotEmpty(request.getProcessResults()))
        {
            // ww 2017.7.19 先删除明细数据
            baseDaoSupport.executeHql("delete TestingReportTempSave d where d.reportId = ? and d.semantic = ?",
                new Object[] {request.getReportId(), request.getSemantic()});
            
            HashMap<String, ReportProcessResult> idToReportProcessResult=new HashMap<String, ReportProcessResult>();
            for (ReportProcessResult result : request.getProcessResults())
            {
                TestingTechnicalAnalyRecord subRecord = baseDaoSupport.get(TestingTechnicalAnalyRecord.class, result.getId());
                if (null != subRecord && (StringUtils.isNotEmpty(result.getClinicalJudgment()) || StringUtils.isNotEmpty(result.getMutationSource())))
                {
                    subRecord.setClinicalJudgment(result.getClinicalJudgment());
                    subRecord.setMutationSource(result.getMutationSource());
                    baseDaoSupport.update(subRecord);
                }
                if (null == subRecord)
                {
                    idToReportProcessResult.put(result.getId(), result);
                }
                
                // 在保存明细数据,防止数据重复 ww
                TestingReportTempSave tempSave = new TestingReportTempSave();
                tempSave.setReportId(request.getReportId());
                tempSave.setSemantic(request.getSemantic());
                tempSave.setRecordId(result.getId());
                tempSave.setClinicalJudgment(result.getClinicalJudgment());
                tempSave.setMutationSource(result.getMutationSource());
                tempSave.setCreateTime(new Date());
                baseDaoSupport.insert(tempSave);
            }
            if (idToReportProcessResult.size()>0)
            {
                    String resutId=request.getProcessResults().get(0).getId().substring(0, request.getProcessResults().get(0).getId().lastIndexOf("_"));
                    TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class,resutId);
                    if (null != taskResult && StringUtils.isNotEmpty(taskResult.getDetails()))
                    {
                        FluoAnalyseSubmitTaskModel fluoanalysesubmittaskmodel = JsonUtils.asObject(taskResult.getDetails(), FluoAnalyseSubmitTaskModel.class);
                        if (null != fluoanalysesubmittaskmodel)
                        {
                            List<FluoDataSubmitTaskSuccessArgs> fargs = fluoanalysesubmittaskmodel.getSuccessArgs();
                            if (Collections3.isNotEmpty(fargs))
                            {
                                List<FluoDataSubmitTaskSuccessArgs> args1 = Lists.newArrayList();
                                
                                for (int i = 0; i < fargs.size(); i++)
                                {
                                    FluoDataSubmitTaskSuccessArgs data = fargs.get(i);
                                    Map<String, String> map = data.getMap();
                                    if(idToReportProcessResult.get(resutId+"_"+i)!=null) {
                                        ReportProcessResult r = idToReportProcessResult.get(resutId+"_"+i);
                                        map.put("临床相关性判断", r.getClinicalJudgment());
                                        map.put("突变来源", r.getMutationSource());
                                        data.setMap(map);
                                    }
                                    args1.add(data);

                                }
                                fluoanalysesubmittaskmodel.setSuccessArgs(args1);
                                String details = JsonUtils.asJson(fluoanalysesubmittaskmodel);
                                taskResult.setDetails(details);
                                baseDaoSupport.update(taskResult);
                            }
                        }
                    }

            }
        }
    }

    @Override
    @Transactional
    public void updataOrderProductForFile(ReportRequest request) {
        TestingReport report = get(request.getReportId());
        OrderProduct orderProduct = getOrderProductByOrderAndProduct(report.getOrder().getId(), report.getProduct().getId());
        // orderProduct.setReportTime(new Date());
        orderProduct.setReportNo(report.getReportCode());
        orderProduct.setDataUrl(request.getFileName());
        // orderProduct.setReportStatus(1);
        // orderProduct.setProductStatus(4);
        baseDaoSupport.update(orderProduct);
    }

    @Override
    @Transactional
    public void updataReportForFile(ReportRequest request) {
        TestingReport testingReport = get(request.getReportId());
        // testingReport.setUpdateDate(new Date());
        // testingReport.setStatus(TestingReport.REPORT_STATUS_COMPLETED);
        if (StringUtils.isNotBlank(request.getFileName()) && request.getFileName().contains("REPORTEXPORT")) {
            String reportName = getReportName(testingReport);
            testingReport.setReportName(reportName);
        } else {
            testingReport.setReportName(request.getFileName());
        }
        // testingReport.setFirstReviewStatus("0");
        baseDaoSupport.update(testingReport);
    }

    private String getReportName(TestingReport testingReport) {
        return (testingReport.getProductCode() == null ? "" : testingReport.getProductCode()) + "_" + (testingReport.getOrderCode() == null ? "" : testingReport.getOrderCode()) +
                "_" + (testingReport.getSampleCode() == null ? "" : testingReport.getSampleCode()) + "_" + (testingReport.getSampleName() == null ? "" : testingReport.getSampleName()) + ".doc";
    }

    @Override
    @Transactional
    public void insertTestingReportUploadRecord(ReportUploadRecordRequest request) {
        TestingReportUploadRecord record = new TestingReportUploadRecord();
        record.setFileName(request.getFileName());
        record.setReportId(request.getReportId());
        record.setUploadTime(new Date());
        record.setUploadType(request.getUploadType());
        baseDaoSupport.insert(record);
    }

    @Override
    @Transactional
    public String getReportByOrderAndProduct(TestingReport request) {
        String orderId = "";
        String productId = "";
        String hqlOrder = "SELECT o.id FROM Order o WHERE o.code = :orderCode";
        List<String> orderIds = baseDaoSupport.findByNamedParam(String.class, hqlOrder, new String[]{"orderCode"}, new Object[]{request.getOrderCode()});
        if (Collections3.isNotEmpty(orderIds)) {
            orderId = Collections3.getFirst(orderIds);
        }

        String hqlProduct = "SELECT p.id FROM Product p WHERE p.code = :productCode AND p.delFlag = 0 ";
        List<String> productIds =
                baseDaoSupport.findByNamedParam(String.class, hqlProduct, new String[]{"productCode"}, new Object[]{request.getProductCode()});
        if (Collections3.isNotEmpty(productIds)) {
            productId = Collections3.getFirst(productIds);
        }

        String hqlReport = "FROM TestingReport r WHERE r.order.id = :orderId AND r.product.id = :productId";
        List<TestingReport> reports =
                baseDaoSupport.findByNamedParam(TestingReport.class, hqlReport, new String[]{"orderId", "productId"}, new Object[]{orderId, productId});
        if (Collections3.isNotEmpty(reports)) {
            TestingReport entity = Collections3.getFirst(reports);
            if (StringUtils.isEmpty(entity.getReportCode())) {
                entity.setReportCode(commonService.getReportNoByName(TestingReport.REPORT_NO));
                baseDaoSupport.update(entity);
            }
            return entity.getId();
        }
        return null;
    }

    private List<TestingDataPic> getPictures(String orderId, String productId, Set<String> testMethods) {
        String hql = "FROM TestingDataPic tdp WHERE tdp.orderId = :orderId AND tdp.productId = :productId AND tdp.methodId IN :testMethods";
        List<TestingDataPic> pictures =
                baseDaoSupport.findByNamedParam(TestingDataPic.class, hql, new String[]{"orderId", "productId", "testMethods"}, new Object[]{orderId, productId,
                        testMethods});
        return pictures;
    }

    //返回样本存在Id ,ww
    private String existSample(String sampleCode) {
        String hql = "FROM OrderSampleView os WHERE os.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<OrderSampleView> list = baseDaoSupport.find(queryer, OrderSampleView.class);
        if (Collections3.isNotEmpty(list)) {
            return list.get(0).getSampleId();
        }
        return null;
    }

    private ReportTestingPicture wrapPic(TestingDataPic tdp) {
        ReportTestingPicture picture = new ReportTestingPicture();
        //TestingSample smaple = baseDaoSupport.get(TestingSample.class, tdp.getSampleId());
        //if (null != smaple)
        //{
        //picture.setSampleCode(smaple.getReceivedSample().getSampleCode());
        String sampleId = "";
        //重新送样的sampleId还是原样本的，根据dataCode分隔得到
        if (StringUtils.isNotEmpty(tdp.getDataCode())) {
            String[] sampleCodes = tdp.getDataCode().split("_");
            if (StringUtils.isNotEmpty(existSample(sampleCodes[0]))) {
                picture.setSampleCode(sampleCodes[0]);
                sampleId = existSample(sampleCodes[0]);
            } else {
                if (StringUtils.isNotEmpty(existSample(sampleCodes[1]))) {
                    picture.setSampleCode(sampleCodes[1]);
                    sampleId = existSample(sampleCodes[1]);
                }
            }
        }
        if (isReSample(sampleId)) {
            picture.setIsReSampling(1);
        }
        String relation = getRelation(picture.getSampleCode());
        if ("本人".equals(relation)) {
            picture.setSort(1);
        } else if ("父亲".equals(relation)) {
            picture.setSort(2);
        } else if ("母亲".equals(relation)) {
            picture.setSort(3);
        } else {
            picture.setSort(4);
        }
        picture.setFamilyRaletion(relation);
        // picture.setSampleResampling(isAbnormalSample(smaple.getReceivedSample().getSampleId()));
        //}
        TestingMethod method = baseDaoSupport.get(TestingMethod.class, tdp.getMethodId());
        if (null != method) {
            picture.setMethodName(method.getName());
        }
        picture.setId(tdp.getId());
        picture.setPictureName(tdp.getPicName());
        picture.setPictureUrl(tdp.getPicUrl());
        return picture;
    }

    // 判断是异常样本 true说明是异常样本 false 正常
    public boolean isAbnormalSample(String id) {
        String hql = "FROM TestingResamplingRecord r WHERE r.abnormalSampleId = :id AND r.strategy = :strategy";
        List<TestingResamplingRecord> records =
                baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[]{"id", "strategy"}, new String[]{id,
                        TestingResamplingRecord.STRATEGY_RESAMPLING});
        log.info("judge sample is abnormal,receivedSampleId is:" + id + "~resampleRecords size is:" + records.size());
        if (Collections3.isNotEmpty(records)) {
            return true;
        }
        return false;

    }

    @Override
    @Transactional
    public String savePictures(TestingReport report) {
        TestingReport entity = get(report.getId());
        if (null != entity) {
            entity.setPictures(report.getPictures());
            baseDaoSupport.update(entity);
        }

        // 先删除明细数据 ww
        baseDaoSupport.executeHql("delete TestingReportTempSave d where d.reportId = ? and d.semantic = ?", new Object[]{report.getId(), "PICTURE"});

        // 保存
        if (StringUtils.isNotEmpty(report.getPictures())) {
            String[] arr = report.getPictures().split(",");
            for (String s : arr) {
                TestingReportTempSave tempSave = new TestingReportTempSave();
                tempSave.setReportId(report.getId());
                tempSave.setSemantic("PICTURE");
                tempSave.setRecordId(s);
                tempSave.setCreateTime(new Date());
                baseDaoSupport.insert(tempSave);
            }
        }

        return entity.getId();
    }

    private List<TestingMethod> getVerifyMethods(String orderId, String productId) {
        String hql =
                "SELECT DISTINCT tm FROM TestingSchedule ts, TestingMethod tm WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = tm.id AND ts.verifyTarget IS NOT NULL";
        List<TestingMethod> methods =
                baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[]{"orderId", "productId"}, new Object[]{orderId, productId});
        return methods;
    }

    @Override
    @Transactional
    public void saveTestingSample(ReportProcessModel request) {
        if (Collections3.isNotEmpty(request.getTestSamples())) {
            for (TestSampleModel sample : request.getTestSamples()) {
                String methodId = getMethodId(request.getSemantic());
                TestingReportSample sampleResult = getReportSample(request.getId(), methodId, sample.getId(), sample.getScheduleId());
                if (null == sampleResult) {
                    sampleResult = new TestingReportSample();
                    sampleResult.setPurpose("2");
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setReportId(request.getId());
                    sampleResult.setSampleId(sample.getId());
                    sampleResult.setScheduleId(sample.getScheduleId());
                    sampleResult.setMethodId(getMethodId(request.getSemantic()));
                    sampleResult.setUpdateTime(new Date());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                        // 样本不合格上报处理
                        reportCancelService.cancel(sample.getScheduleId(), request.getId(), sample.getUnqualifiedRemark());
                    }
                    baseDaoSupport.insert(sampleResult);
                } else {
                    // 已经保存过的记录只更新
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setUpdateTime(new Date());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                        // 样本不合格上报处理
                        reportCancelService.cancel(sample.getScheduleId(), request.getId(), sample.getUnqualifiedRemark());
                    }
                    baseDaoSupport.update(sampleResult);
                }
            }
        }
    }

    private void setSampleResult(TestSampleModel tsm, String reportId, String methodId, String sampleId) {
        String hql =
                "FROM TestingReportSample trs WHERE trs.reportId = :reportId AND trs.methodId = :methodId AND trs.sampleId = :sampleId ORDER BY trs.updateTime";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("reportId", "methodId", "sampleId"))
                        .values(Lists.newArrayList(reportId, methodId, sampleId))
                        .build();
        List<TestingReportSample> sampleResults = baseDaoSupport.find(queryer, TestingReportSample.class);
        if (Collections3.isNotEmpty(sampleResults)) {
            TestingReportSample trs = Collections3.getLast(sampleResults);
            tsm.setQualified(trs.isQualified());
            if (!tsm.isQualified()) {
                tsm.setUnqualifiedRemark(trs.getUnqualifiedRemark());
                tsm.setUnqualifiedStrategy(trs.getUnqualifiedStrategy());
            }
        } else {
            tsm.setQualified(true);
        }
    }

    private List<VerifySampleModel> getVerifySamples(TestingReport report, String methodId) {
        List<VerifySampleModel> verifySamples = Lists.newArrayList();
        if (null != report) {
            String geneType = "";
            List<TestingSchedule> verifySchedules = getSchedules(report.getOrder().getId(), report.getProduct().getId(), methodId);
            for (TestingSchedule ts : verifySchedules) {
                if (!"实验取消".equals(ts.getActiveTask())) {
                    ReceivedSample rSample = baseDaoSupport.get(ReceivedSample.class, ts.getSampleId());
                    if (null == rSample) {
                        TestingSample tSample = baseDaoSupport.get(TestingSample.class, ts.getSampleId());
                        if (null != tSample) {
                            rSample = tSample.getReceivedSample();
                        }
                    }
                    String abTask = getTaskIdByReceivedSampleCode(ts.getId(), rSample.getSampleCode());
                    verifySamples.add(warpVerifySampleModel(report, methodId, rSample, ts, geneType, abTask));
                }
            }

            //验证重新送样后的样本也显示 2017.9.11
            List<VerifySampleModel> resendVerifySamples = Lists.newArrayList();
            if (Collections3.isNotEmpty(verifySamples)) {
                for (VerifySampleModel vm : verifySamples) {
                    if (1 == vm.getIsResampling()) {
                        String resendSampleId = getResendSampleId(vm.getId());
                        ReceivedSample resendReceivedSample = baseDaoSupport.get(ReceivedSample.class, resendSampleId);
                        ReceivedSample rSample = baseDaoSupport.get(ReceivedSample.class, vm.getId());
                        TestingSchedule tts = baseDaoSupport.get(TestingSchedule.class, vm.getScheduleId());
                        String resendTask = getTaskIdByReceivedSampleCode(vm.getScheduleId(), resendReceivedSample.getSampleCode());
                        String abTask = getTaskIdByReceivedSampleCode(vm.getScheduleId(), rSample.getSampleCode());
                        if (StringUtils.isNotEmpty(resendTask) && StringUtils.isNotEmpty(abTask)) {
                            resendVerifySamples.add(warpVerifySampleModel(report, methodId, resendReceivedSample, tts, geneType, resendTask));
                        }
                    }
                }
            }
            verifySamples.addAll(resendVerifySamples);

            for (VerifySampleModel vSample : verifySamples) {
                TestingReportSample trs = getReportSampleByCombineCode(report.getId(), methodId, vSample.getCombineCode(), vSample.getScheduleId());
                if (null == trs && "本人".equals(vSample.getFamilyRelation())) {
                    vSample.setMutationSource(getMutationSourceByVerify(verifySamples, methodId, report, vSample.getCombineCode(), vSample.getChrLocation()));
                }
            }
        }
        verifySamples.sort(comparing(VerifySampleModel::getGene)
                .thenComparing(VerifySampleModel::getChrLocation)
                .thenComparing(VerifySampleModel::getSort));
        return verifySamples;
    }

    //拼装验证样本信息
    private VerifySampleModel warpVerifySampleModel(TestingReport report, String methodId, ReceivedSample rSample, TestingSchedule ts, String geneType, String taskId) {
        VerifySampleModel vSample = new VerifySampleModel();
        //重新送样
        if (isReSample(rSample.getSampleId())) {
            vSample.setIsResampling(1);
        }
        vSample.setId(rSample.getSampleId());
        vSample.setSampleCode(rSample.getSampleCode());
        vSample.setSampleName(rSample.getSampleName());
        vSample.setFamilyRelation(getRelation(rSample.getSampleCode()));
        vSample.setScheduleId(ts.getId());

        if (TestingSchedule.END_SUCCESS.equals(ts.getEndType())) {
            vSample.setEndFlag(true);
        }

        VerifyRecordModel verifyRecord = getVerifyRecordByVerifyKeyForReport(ts.getVerifyKey());
        if (null != verifyRecord) {
            TestingTechnicalAnalyRecord analyRecord = verifyRecord.getVerifyRecord().getAnalyRecord();
            vSample.setCombineCode(verifyRecord.getCombineCode());
            vSample.setChrLocation(analyRecord == null ? "" : analyRecord.getChromosomalLocation());
            vSample.setExon(analyRecord == null ? "" : analyRecord.getExon());
            vSample.setGene(analyRecord == null ? "" : analyRecord.getGeneSymbol());
            vSample.setUpstreamGene(analyRecord == null ? "" : analyRecord.getUpRefGene());
            vSample.setDownstreamGene(analyRecord == null ? "" : analyRecord.getDownRefGene());
            vSample.setRefSample(analyRecord == null ? "" : analyRecord.getRefSample());
            if ("本人".equals(vSample.getFamilyRelation())) {
                vSample.setVerifySample(getVerifySampleRecord(analyRecord == null ? "" : analyRecord.getId()));
                vSample.setSort(1);
            } else if ("父亲".equals(vSample.getFamilyRelation())) {
                vSample.setSort(2);
            } else if ("母亲".equals(vSample.getFamilyRelation())) {
                vSample.setSort(3);
            } else {
                vSample.setSort(4);
            }
            geneType = analyRecord == null ? "" : analyRecord.getGeneType();
        }

        //String taskId = getTaskId(ts.getId());
        TestingTaskResult ttr = null;
        if (StringUtils.isNotEmpty(taskId)) {
            ttr = baseDaoSupport.get(TestingTaskResult.class, taskId);
            if (null != ttr && StringUtils.isNotEmpty(ttr.getDetails())) {
                JSONObject temp = JSONObject.parseObject(ttr.getDetails());
                if (null != temp) {
                    JSONObject mapTemp = JSONObject.parseObject(temp.getString("map"));
                    if (null != mapTemp) {
                        vSample.setCombineType(mapTemp.getString("纯合/杂合"));
                        vSample.setRemark(mapTemp.getString("备注"));
                        vSample.setTestResult(mapTemp.getString("阴/阳性"));
                        vSample.setConclusion(mapTemp.getString("结论"));
                    } else {
                        JSONArray mapArray = JSONObject.parseArray(temp.getString("successArgs"));
                        if (null != mapArray) {
                            Iterator<Object> it = mapArray.iterator();
                            while (it.hasNext()) {
                                JSONObject jo = (JSONObject) it.next();
                                JSONObject jmap = JSONObject.parseObject(jo.getString("map"));

                                if (null != jmap && StringUtils.isNotEmpty(jmap.getString("样本编号")) && StringUtils.isNotEmpty(jmap.getString("突变基因"))
                                        && StringUtils.isNotEmpty(vSample.getCombineCode()) && vSample.getCombineCode().contains(jmap.getString("样本编号"))
                                        && vSample.getCombineCode().contains(jmap.getString("突变基因"))) {
                                    vSample.setCombineType(jmap.getString("纯合/杂合"));
                                    vSample.setRemark(jmap.getString("备注"));
                                    vSample.setExon(jmap.getString("外显子"));
                                    vSample.setNucleotideChanges(jmap.getString("核苷酸变化"));
                                    vSample.setTestResult(jmap.getString("阴/阳性"));
                                    vSample.setConclusion(jmap.getString("结论"));
                                }
                            }
                        }
                    }
                }
            }
        }

        TestingReportSample trs = getReportSampleByCombineCode(report.getId(), methodId, vSample.getCombineCode(), ts.getId());
        if (isReSample(rSample.getSampleId())) {
            vSample.setQualified(true);//重新送样的肯定不能异常上报，所以页面要不显示，只能置为合格 2017.9.14
        } else {
            if (null != trs) {
                if (StringUtils.isNotEmpty(trs.getCombineType())) {
                    vSample.setCombineType(trs.getCombineType());
                }
                if (StringUtils.isNotEmpty(trs.getMutationSource())) {
                    vSample.setMutationSource(trs.getMutationSource());
                }

                vSample.setQualified(trs.isQualified());
                if (!trs.isQualified()) {
                    vSample.setUnqualifiedRemark(trs.getUnqualifiedRemark());
                    vSample.setUnqualifiedStrategy(trs.getUnqualifiedStrategy());
                }
            } else {
                if (null == ttr || !"本人".equals(vSample.getFamilyRelation())) {
                    vSample.setQualified(true);
                } else {
                    // 说明是本人样本，如果流程正常结束则去判断半合子等，如果没结束就不去判断了，默认合格
                    if (vSample.isEndFlag()) {
                        if (("het".equals(geneType) && ("杂合变异".equals(vSample.getCombineType()) || "杂合缺失".equals(vSample.getCombineType())))
                                || ("hom".equals(geneType) && ("纯合变异".equals(vSample.getCombineType()) || "纯合缺失".equals(vSample.getCombineType())))
                                || ("hemi".equals(geneType) && "半合子变异".equals(vSample.getCombineType()))) {
                            vSample.setQualified(true);
                        }
                    } else {
                        vSample.setQualified(true);
                    }
                }
            }
        }

        List<TestingDataPic> pictures = getSamplePictures(report.getOrder().getId(), report.getProduct().getId(), vSample.getCombineCode(), methodId);
        vSample.setPictures(pictures);
        return vSample;
    }

    private TestingScheduleHistory getScheduleHistoryBySemantic(String semantic, String scheduleId) {
        String hql =
                "FROM TestingScheduleHistory tsh WHERE EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = tsh.taskId AND tt.semantic = :semantic) AND tsh.scheduleId = :scheduleId ORDER BY tsh.timestamp DESC";
        NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic", "scheduleId")).values(Lists.newArrayList(semantic, scheduleId)).build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(historys)) {
            return historys.get(0);
        }
        return null;

    }

    private TestingReportSample getReportSample(String reportId, String methodId, String sampleId, String scheduleId) {
        String hql =
                "FROM TestingReportSample trs WHERE trs.reportId = :reportId AND trs.methodId = :methodId AND trs.sampleId = :sampleId AND trs.scheduleId = :scheduleId Order By trs.updateTime DESC";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("reportId", "methodId", "sampleId", "scheduleId"))
                        .values(Lists.newArrayList(reportId, methodId, sampleId, scheduleId))
                        .build();
        List<TestingReportSample> sampleResults = baseDaoSupport.find(queryer, TestingReportSample.class);
        if (Collections3.isNotEmpty(sampleResults)) {
            return Collections3.getFirst(sampleResults);
        }
        return null;
    }

    public VerifyRecordModel getVerifyRecordByVerifyKeyForReport(String verifyKey) {
        VerifyRecordModel model = new VerifyRecordModel();
        TestingVerifyRecord record;
        if (StringUtils.isNotEmpty(verifyKey)) {
            // sanger 和 pcr-ngs 4个验证
            SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
            if (null != sangerverifyrecord) {
                record = sangerverifyrecord.getVerifyRecord();
                model.setCombineCode(sangerverifyrecord.getCode());
                model.setVerifyRecord(record);
                return model;
            }
            MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
            if (null != mlpaverifyrecord) {
                record = mlpaverifyrecord.getVerifyRecord();
                model.setVerifyRecord(record);
                model.setCombineCode(mlpaverifyrecord.getCombineCode());
                return model;
            }
            QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
            if (null != qpcrverifyrecord) {
                record = qpcrverifyrecord.getVerifyRecord();
                model.setCombineCode(qpcrverifyrecord.getCombineCode());
                model.setVerifyRecord(record);
                return model;
            }
        }
        return null;
    }

    private String getVerifySampleRecord(String recordId) {
        String hql = "SELECT vr.inputSampleFamilyRelation FROM TestingVerifyRecord vr WHERE vr.analyRecord.id = :recordId";
        List<String> familys = baseDaoSupport.findByNamedParam(String.class, hql, new String[]{"recordId"}, new Object[]{recordId});
        StringBuffer family = new StringBuffer(128);
        for (String f : familys) {
            if (StringUtils.isEmpty(family)) {
                family.append(f);
            } else {
                family.append(",").append(f);
            }
        }
        return family.toString();
    }

    @Override
    @Transactional
    public void saveVerifySample(ReportProcessModel request) {
        if (Collections3.isNotEmpty(request.getVerifySamples())) {
            for (VerifySampleModel sample : request.getVerifySamples()) {
                String methodId = getMethodId(request.getSemantic());
                TestingReportSample sampleResult = getReportSample(request.getId(), methodId, sample.getId(), sample.getScheduleId());
                if (null == sampleResult) {
                    sampleResult = new TestingReportSample();
                    sampleResult.setPurpose("1");
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setReportId(request.getId());
                    sampleResult.setSampleId(sample.getId());
                    sampleResult.setScheduleId(sample.getScheduleId());
                    sampleResult.setMethodId(getMethodId(request.getSemantic()));
                    sampleResult.setUpdateTime(new Date());
                    sampleResult.setCombineType(sample.getCombineType());
                    sampleResult.setMutationSource(sample.getMutationSource());
                    sampleResult.setCombineCode(sample.getCombineCode());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                        // 样本不合格上报处理
                        reportCancelService.cancel(sample.getScheduleId(), request.getId(), sample.getUnqualifiedRemark());
                    }
                    baseDaoSupport.insert(sampleResult);
                } else {
                    // 已经保存过的记录只更新
                    sampleResult.setQualified(sample.isQualified());
                    sampleResult.setUpdateTime(new Date());
                    sampleResult.setCombineType(sample.getCombineType());
                    sampleResult.setMutationSource(sample.getMutationSource());
                    sampleResult.setCombineCode(sample.getCombineCode());
                    if (!sample.isQualified()) {
                        sampleResult.setUnqualifiedRemark(sample.getUnqualifiedRemark());
                        sampleResult.setUnqualifiedStrategy(sample.getUnqualifiedStrategy());
                        // 样本不合格上报处理
                        reportCancelService.cancel(sample.getScheduleId(), request.getId(), sample.getUnqualifiedRemark());
                    }
                    baseDaoSupport.update(sampleResult);
                }
            }
        }
    }

    @Override
    @Transactional
    public ReturnModel solve(TestingReport request) {
        TestingReport report = get(request.getId());
        ReturnModel rm = new ReturnModel();
        if (null != report) {
            report.setReportType(request.getReportType());
            report.setAnalyResult(request.getAnalyResult());
            report.setReportTemplateType(request.getReportTemplateType());
            report.setUpdateDate(new Date());
            report.setStatus(TestingReport.REPORT_STATUS_COMPLETED);
            report.setFirstReviewStatus("0");
            baseDaoSupport.update(report);

            OrderProduct orderProduct = getOrderProductByOrderAndProduct(report.getOrder().getId(), report.getProduct().getId());
            orderProduct.setReportTime(new Date());
            orderProduct.setReportStatus(1);
            orderProduct.setProductStatus(4);
            baseDaoSupport.update(orderProduct);

            List<Map<String, String>> orderProductIds = Lists.newArrayList();
            Map<String, String> map = new HashMap<>();
            map.put("orderId", report.getOrder().getId());
            map.put("productId", report.getProduct().getId());
            orderProductIds.add(map);
            rm.setOrderProductIds(orderProductIds);
        }
        return rm;
    }

    @Override
    @Transactional
    public ResponseModel generateCallback(ReportCallbackModel request) {
        ResponseModel responseModel = new ResponseModel();
        String message = "";
        String stateCode = "";
        if (null == request) {
            log.info(" request parameters is null ");
            stateCode = ResponseModel.FAILURE_STATE_CODE;
            message = " request parameters is null ";
        } else {
            String reportKey = request.getReportKey();
            String status = request.getGenerateStatus();// 1成功 2失败
            if (StringUtils.isEmpty(reportKey) || StringUtils.isEmpty(status) || StringUtils.isEmpty(request.getReportUrl())) {
                log.info("reportKey or GenerateStatus is null ");
                message = " reportKey or GenerateStatus is null ";
                stateCode = ResponseModel.FAILURE_STATE_CODE;
            } else {
                TestingReport report = get(reportKey);
                if (null == report) {
                    log.info(" do not find this report,reportKey is error?");
                    message = " do not find this report,reportKey is error? ";
                    stateCode = ResponseModel.FAILURE_STATE_CODE;
                } else {
                    if ("1".equals(status)) {
                        String combinePath[] = request.getReportUrl().split(",");// arr[0]是文件名 arr[1]是七牛地址
                        if (null == combinePath || combinePath.length != 2) {
                            log.error("file name contains more douhao , ");
                            throw new IllegalStateException();
                        }
                        String fileName = combinePath[0];
                        String qiniuName = combinePath[1];
                        report.setUpdateDate(new Date());

                        report.setStatus(TestingReport.REPORT_STATUS_COMPLETED);
                        report.setFirstReviewStatus("0");
                        report.setSendStatus(2);// 处理成功
                        report.setDataUrl(qiniuName);
                        report.setReportName(fileName);
                        String reportNo = report.getReportCode();
                        if (StringUtils.isEmpty(reportNo)) {
                            reportNo = commonService.getReportNoByName(TestingReport.REPORT_NO);
                            report.setReportCode(reportNo);
                        }
                        baseDaoSupport.update(report);

                        OrderProduct orderProduct = getOrderProductByOrderAndProduct(report.getOrder().getId(), report.getProduct().getId());
                        if (null != orderProduct) {
                            orderProduct.setReportTime(new Date());
                            orderProduct.setReportStatus(1);
                            orderProduct.setProductStatus(4);
                            orderProduct.setDataUrl(qiniuName);
                            orderProduct.setReportNo(report.getReportCode());
                            baseDaoSupport.update(orderProduct);
                        }

                        // --结束
                        stateCode = ResponseModel.SUCCESS_STATE_CODE;
                        message = "success";
                        log.info(" success ");
                        List<Map<String, String>> orderProductIds = Lists.newArrayList();
                        Map<String, String> map = new HashMap<>();
                        map.put("orderId", report.getOrder().getId());
                        map.put("productId", report.getProduct().getId());
                        orderProductIds.add(map);
                        producer.sendReportGenerateMessage(orderProductIds);
                    } else {//失败情况下 发送状态3 备注追加原因
                        report.setSendStatus(3);// 处理失败
                        if (StringUtils.isNotEmpty(request.getMessage())) {
                            report.setRemark(report.getRemark() + "-系统提示：" + request.getMessage());
                        }
                        baseDaoSupport.update(report);
                    }
                    // 更新提交记录表状态
                    TestingReportSendCallBack record = getByReportSubmitRecord(reportKey);
                    if (null != record) {
                        if ("1".equals(status)) {
                            record.setStatus(TestingReportSendCallBack.SEND_STATUS_SUCCESS);
                        } else {
                            record.setStatus(TestingReportSendCallBack.SEND_STATUS_FAILED);
                        }
                        baseDaoSupport.update(record);
                    }
                }

            }
        }

        responseModel.setStatusCode(stateCode);
        responseModel.setMessage(message);
        return responseModel;

    }

    public TestingReportSendCallBack getByReportSubmitRecord(String reportId) {
        String hql = " FROM TestingReportSendCallBack r WHERE r.reportId=:reportId AND r.status=1 ";
        List<TestingReportSendCallBack> records =
                baseDaoSupport.findByNamedParam(TestingReportSendCallBack.class, hql, new String[]{"reportId"}, new String[]{reportId});
        return Collections3.getFirst(records);

    }

    private OrderProduct getOp(String orderId, String productId) {
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql("FROM OrderProduct op WHERE op.order.id = :orderId AND op.product.id = :productId")
                        .names(Lists.newArrayList("orderId", "productId"))
                        .values(Lists.newArrayList(orderId, productId))
                        .build();
        List<OrderProduct> opList = baseDaoSupport.find(queryer, OrderProduct.class);
        if (Collections3.isNotEmpty(opList)) {
            return Collections3.getFirst(opList);
        }
        return null;
    }

    private List<String> getColumnValues(TestingTaskResult dataAnalysisTaskResult, Map<String, String> map, List<String> columnNames) {
        List<String> columnValues = Lists.newArrayList();
        columnValues.add(dataAnalysisTaskResult.getTaskId());
        if (StringUtils.isEmpty(map.get("临床相关性判断"))) {
            columnValues.add("");
        } else {
            columnValues.add(map.get("临床相关性判断"));
        }
        if (StringUtils.isEmpty(map.get("突变来源"))) {
            columnValues.add("");
        } else {
            columnValues.add(map.get("突变来源"));
        }
        for (String name : columnNames) {
            if (!"临床相关性判断".equals(name) && !"突变来源".equals(name)) {
                if ("结果".equals(name)) {
                    if (isNumeric(map.get(name))) {
                        if (0 == Integer.parseInt(map.get(name))) {
                            columnValues.add("成功");
                        } else {
                            columnValues.add("失败");
                        }
                    } else {
                        columnValues.add(map.get(name));
                    }
                } else {
                    columnValues.add(map.get(name));
                }
            }
        }
        return columnValues;
    }

    private String getTaskIdByReceivedSampleCode(String scheduleId, String sampleCode) {
        String hql =
                "SELECT tsh.taskId FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId "
                        + "AND EXISTS (SELECT tt.id FROM TestingTask tt WHERE tt.id = tsh.taskId AND tt.semantic IN ('DATA-ANALYSIS','MLPA-DATA-ANALYSIS','PCR-NGS-DATA-ANALYSIS','QPCR') AND tt.receivedSampleCode = :receivedSampleCode) ORDER BY tsh.timestamp";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("scheduleId", "receivedSampleCode"))
                        .values(Lists.newArrayList(scheduleId, sampleCode))
                        .build();
        List<String> taskIds = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(taskIds)) {
            return Collections3.getLast(taskIds);
        }
        return null;
    }

    private List<TestingDataPic> getSamplePictures(String orderId, String productId, String combineCode, String methodId) {
        String hql =
                "FROM TestingDataPic tdp WHERE tdp.picName LIKE :combineCode AND tdp.orderId = :orderId AND tdp.productId = :productId AND tdp.methodId = :methodId";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("combineCode", "orderId", "productId", "methodId"))
                        .values(Lists.newArrayList("%" + combineCode + "%", orderId, productId, methodId))
                        .build();
        List<TestingDataPic> pictures = baseDaoSupport.find(queryer, TestingDataPic.class);
        return pictures;
    }

    private TestingSchedule getTargetScheduleByAnalyRecord(String analyRecordId, String verifyMethod) {
        String hql =
                "FROM TestingVerifyRecord tvr WHERE EXISTS(SELECT ttar.id FROM TestingTechnicalAnalyRecord ttar WHERE ttar.id = :analyRecordId AND tvr.analyRecord.id = ttar.id)";
        List<TestingVerifyRecord> verifies =
                baseDaoSupport.findByNamedParam(TestingVerifyRecord.class, hql, new String[]{"analyRecordId"}, new Object[]{analyRecordId});

        TestingSchedule verifySchedule = null;
        if (Collections3.isNotEmpty(verifies)) {
            for (TestingVerifyRecord verify : verifies) {
                if ("Sanger".equals(verifyMethod) || "PCR-NGS".equals(verifyMethod)) {
                    String hqlSanger =
                            "FROM TestingSchedule ts WHERE EXISTS(SELECT svr.id FROM SangerVerifyRecord svr WHERE svr.verifyRecord.id = :verifyId AND ts.verifyKey = svr.id)";
                    List<TestingSchedule> sangers =
                            baseDaoSupport.findByNamedParam(TestingSchedule.class, hqlSanger, new String[]{"verifyId"}, new Object[]{verify.getId()});
                    if (Collections3.isNotEmpty(sangers)) {
                        verifySchedule = Collections3.getFirst(sangers);
                        break;
                    }
                } else if ("MLPA".equals(verifyMethod)) {
                    String hqlMlpa =
                            "FROM TestingSchedule ts WHERE EXISTS(SELECT mvr.id FROM MlpaVerifyRecord mvr WHERE mvr.verifyRecord.id = :verifyId AND ts.verifyKey = mvr.id)";
                    List<TestingSchedule> mlpas =
                            baseDaoSupport.findByNamedParam(TestingSchedule.class, hqlMlpa, new String[]{"verifyId"}, new Object[]{verify.getId()});
                    if (Collections3.isNotEmpty(mlpas)) {
                        verifySchedule = Collections3.getFirst(mlpas);
                        break;
                    }
                } else if ("QPCR".equals(verifyMethod)) {
                    String hqlQpcr =
                            "FROM TestingSchedule ts WHERE EXISTS(SELECT qvr.id FROM QpcrVerifyRecord qvr WHERE qvr.verifyRecord.id = :verifyId AND ts.verifyKey = qvr.id)";
                    List<TestingSchedule> qpcrs =
                            baseDaoSupport.findByNamedParam(TestingSchedule.class, hqlQpcr, new String[]{"verifyId"}, new Object[]{verify.getId()});
                    if (Collections3.isNotEmpty(qpcrs)) {
                        verifySchedule = Collections3.getFirst(qpcrs);
                        break;
                    }
                }
            }
            if (null != verifySchedule) {
                return baseDaoSupport.get(TestingSchedule.class, verifySchedule.getVerifyTarget());
            }
        }
        return null;
    }

    private TestingReportSample getReportSampleByCombineCode(String reportId, String methodId, String combineCode, String scheduleId) {
        String hql =
                "FROM TestingReportSample trs WHERE trs.reportId = :reportId AND trs.methodId = :methodId AND trs.combineCode = :combineCode AND trs.scheduleId = :scheduleId Order By trs.updateTime DESC";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("reportId", "methodId", "combineCode", "scheduleId"))
                        .values(Lists.newArrayList(reportId, methodId, combineCode, scheduleId))
                        .build();
        List<TestingReportSample> sampleResults = baseDaoSupport.find(queryer, TestingReportSample.class);
        if (Collections3.isNotEmpty(sampleResults)) {
            return Collections3.getFirst(sampleResults);
        }
        return null;
    }

    private String getMutationSource(JSONObject temp, TestingReport report) {
        String combineTypeS = "";
        String combineTypeF = "";
        String combineTypeM = "";
        String mSemantic = "";
        if ("Sanger".equals(temp.getString("验证方法"))) {
            mSemantic = "SANGER";
        } else if ("MLPA".equals(temp.getString("验证方法"))) {
            mSemantic = "MLPA";
        } else if ("QPCR".equals(temp.getString("验证方法"))) {
            mSemantic = "QPCR";
        } else if ("PCR-NGS".equals(temp.getString("验证方法"))) {
            mSemantic = "PCR-NGS";
        }
        String mId = getMethodId(mSemantic);
        if (StringUtils.isNotEmpty(mId)) {
            List<VerifySampleModel> testSampleResultVerifies = getVerifySamples(report, mId);
            if (Collections3.isNotEmpty(testSampleResultVerifies)) {
                String chrLocation = "";
                for (VerifySampleModel vsm : testSampleResultVerifies) {
                    if (StringUtils.isNotEmpty(temp.getString("ID"))) {
                        if ("本人".equals(vsm.getFamilyRelation()) && StringUtils.isNotEmpty(temp.getString("Sample"))
                                && StringUtils.isNotEmpty(vsm.getCombineCode()) && vsm.getCombineCode().contains(temp.getString("Sample"))
                                && vsm.getCombineCode().contains(temp.getString("ID"))) {
                            combineTypeS = vsm.getCombineType();
                            chrLocation = vsm.getChrLocation();
                        }
                        if ("父亲".equals(vsm.getFamilyRelation()) && vsm.getChrLocation().equals(chrLocation))//chrLocation.equals(temp.getString("ID")) 2017.8.7
                        {
                            combineTypeF = vsm.getCombineType();
                        }
                        if ("母亲".equals(vsm.getFamilyRelation()) && vsm.getChrLocation().equals(chrLocation))//chrLocation.equals(temp.getString("ID")) 2017.8.7
                        {
                            combineTypeM = vsm.getCombineType();
                        }
                    } else {
                        String gene = "";
                        if ("本人".equals(vsm.getFamilyRelation()) && StringUtils.isNotEmpty(temp.getString("Sample"))
                                && StringUtils.isNotEmpty(vsm.getCombineCode()) && vsm.getCombineCode().contains(temp.getString("Sample"))
                                && StringUtils.isNotEmpty(temp.getString("Gene_Symbol")) && vsm.getCombineCode().contains(temp.getString("Gene_Symbol"))) {
                            combineTypeS = vsm.getCombineType();
                            gene = vsm.getGene();
                        }
                        if ("父亲".equals(vsm.getFamilyRelation()) && gene.equals(vsm.getGene()))//gene.equals(temp.getString("Gene_Symbol")) 2017.8.7
                        {
                            combineTypeF = vsm.getCombineType();
                        }
                        if ("母亲".equals(vsm.getFamilyRelation()) && gene.equals(vsm.getGene()))//gene.equals(temp.getString("Gene_Symbol")) 2017.8.7
                        {
                            combineTypeM = vsm.getCombineType();
                        }
                    }

                }
            }
        }
        String mutationSource = getMutationSourceByCombineType(combineTypeS, combineTypeF, combineTypeM);
        return mutationSource;
    }

    private static void sortTestName(List<TestingMethod> testingMethods) {
        List<String> definedList =
                Lists.newArrayList("NGS",
                        "CapNGS",
                        "多重PCR检测",
                        "Long-PCR检测",
                        "MLPA检测",
                        "动态突变",
                        "荧光检测",
                        "Sanger检测",
                        "图片",
                        "Sanger验证",
                        "PCR-NGS验证",
                        "QPCR验证",
                        "MLPA验证");
        Collections.sort(testingMethods, new Comparator<TestingMethod>() {
            @Override
            public int compare(TestingMethod o1, TestingMethod o2) {
                int io1 = definedList.indexOf(o1.getName());
                int io2 = definedList.indexOf(o2.getName());
                return io1 - io2;
            }
        });
    }

    private String getMutationSourceByVerify(List<VerifySampleModel> testSampleResultVerifies, String methodId, TestingReport report, String combineCode, String chr) {
        String combineTypeS = "";
        String combineTypeF = "";
        String combineTypeM = "";
        if (Collections3.isNotEmpty(testSampleResultVerifies)) {
            String chrLocation = "";
            for (VerifySampleModel vsm : testSampleResultVerifies) {
                if ("本人".equals(vsm.getFamilyRelation()) && StringUtils.isNotEmpty(vsm.getCombineCode()) && combineCode.equals(vsm.getCombineCode())) {
                    combineTypeS = vsm.getCombineType();
                    chrLocation = vsm.getChrLocation();
                    break;
                }
            }
            for (VerifySampleModel vsm : testSampleResultVerifies) {
                if ("父亲".equals(vsm.getFamilyRelation()) && chrLocation.equals(vsm.getChrLocation())) {
                    combineTypeF = vsm.getCombineType();
                }
                if ("母亲".equals(vsm.getFamilyRelation()) && chrLocation.equals(vsm.getChrLocation())) {
                    combineTypeM = vsm.getCombineType();
                }
            }
        }
        String mutationSource = getMutationSourceByCombineType(combineTypeS, combineTypeF, combineTypeM);
        return mutationSource;
    }

    private String getMutationSourceByCombineType(String combineTypeS, String combineTypeF, String combineTypeM) {
        String mutationSource = "";
        if (("杂合变异".equals(combineTypeS) && "杂合变异".equals(combineTypeF) && "无变异".equals(combineTypeM)))//|| ("杂合缺失".equals(combineTypeS) && "杂合缺失".equals(combineTypeF) && "无变异".equals(combineTypeM))
        {
            mutationSource = "父亲";
        } else if (("杂合变异".equals(combineTypeS) && "无变异".equals(combineTypeF) && "杂合变异".equals(combineTypeM)))//|| ("杂合缺失".equals(combineTypeS) && "无变异".equals(combineTypeF) && "杂合缺失".equals(combineTypeM))
        {
            mutationSource = "母亲";
        } else if ("半合子变异".equals(combineTypeS) && "无变异".equals(combineTypeF) && "杂合变异".equals(combineTypeM)) {
            mutationSource = "母亲";
        } else if ("杂合变异".equals(combineTypeS) && "半合子变异".equals(combineTypeF) && "无变异".equals(combineTypeM)) {
            mutationSource = "父亲";
        } else if (("杂合变异".equals(combineTypeS) && "无变异".equals(combineTypeF) && "无变异".equals(combineTypeM)))//|| ("杂合缺失".equals(combineTypeS) && "无变异".equals(combineTypeF) && "无变异".equals(combineTypeM))
        {
            mutationSource = "自发突变";
        } else if (("纯合变异".equals(combineTypeS) && "杂合变异".equals(combineTypeF) && "杂合变异".equals(combineTypeM))
                || ("纯合缺失".equals(combineTypeS) && "杂合缺失".equals(combineTypeF) && "杂合缺失".equals(combineTypeM))) {
            mutationSource = "父母";
        } else if (("杂合变异".equals(combineTypeS) && "".equals(combineTypeF) && "杂合变异".equals(combineTypeM)))//|| ("杂合缺失".equals(combineTypeS) && "".equals(combineTypeF) && "杂合缺失".equals(combineTypeM))
        {
            mutationSource = "母亲";
        } else if (("杂合变异".equals(combineTypeS) && "杂合变异".equals(combineTypeF) && "".equals(combineTypeM)))// || ("杂合缺失".equals(combineTypeS) && "杂合缺失".equals(combineTypeF) && "".equals(combineTypeM))
        {
            mutationSource = "父亲";
        } else {
            mutationSource = "未知";
        }
        return mutationSource;
    }

    /**
     * 判断字符串是否能转数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isNotEmpty(str)) {
            for (int i = str.length(); --i >= 0; ) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public DataTemplate getTemplateByProductAndMethod(String productId, String methodSemantic) {
        String hql =
                "FROM DataTemplate d WHERE d.delFlag=false AND EXISTS (SELECT t.dataTemplateId FROM ProductTestingMethod t WHERE t.product.id=:productId AND t.testingMethod.semantic=:methodSemantic AND t.dataTemplateId=d.id ) ";
        List<DataTemplate> records =
                baseDaoSupport.findByNamedParam(DataTemplate.class, hql, new String[]{"productId", "methodSemantic"}, new String[]{productId, methodSemantic});
        if (Collections3.isNotEmpty(records)) {
            return Collections3.getFirst(records);
        }
        return null;
    }

    public String getMethodMapTemp(String productId) {
        Map<String, String> map = Maps.newHashMap();
        String hql = "FROM ProductTestingMethod d WHERE d.product.id=:productId ";
        List<ProductTestingMethod> records =
                baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[]{"productId"}, new String[]{productId});
        if (Collections3.isNotEmpty(records)) {
            for (ProductTestingMethod temp : records) {
                map.put(temp.getTestingMethod().getSemantic().replace("-", ""), temp.getDataTemplateId());
            }
        }
        return JsonUtils.asJson(map);
    }

    public boolean isPrimarySample(String sampleCode) {
        String hql = "FROM OrderPrimarySample s WHERE s.sampleCode=:sampleCode";
        List<DataTemplate> records = baseDaoSupport.findByNamedParam(DataTemplate.class, hql, new String[]{"sampleCode"}, new String[]{sampleCode});
        if (Collections3.isNotEmpty(records)) {
            return true;
        }
        return false;
    }

    public TestingMethod getByMethodByType(int type) {
        String method = "";
        if (0 == type) {
            method = "NGS";
        } else if (1 == type) {
            method = "CAP-NGS";
        } else if (2 == type) {
            method = "MULTI-PCR";
        } else {
            method = "Long-PCR";
        }
        // 查找验证方法
        String hql = "FROM TestingMethod m WHERE m.type = :type AND m.semantic = :semantic AND m.enabled = :enabled";
        List<TestingMethod> methods =
                baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[]{"type", "semantic", "enabled"}, new Object[]{"1", method.toUpperCase(),
                        true});
        if (Collections3.isNotEmpty(methods)) {
            return Collections3.getFirst(methods);
        }
        return null;
    }

    public TestingTask getLastTechnicalTaskBySchedule(TestingSchedule schedule) {
        String hql =
                "FROM TestingTask t WHERE t.semantic = 'TECHNICAL-ANALY' AND t.endTime is not null AND EXISTS( select his.id FROM TestingScheduleHistory his WHERE his.taskId = t.id AND his.scheduleId=:scheduleId ) ORDER BY t.endTime DESC";
        List<TestingTask> technicalTasks =
                baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[]{"scheduleId"}, new String[]{schedule.getId()});
        return Collections3.getFirst(technicalTasks);
    }

    public List<TestingTechnicalAnalyRecord> getRecordByDataCode(String dataCode) {
        String hql = "FROM TestingTechnicalAnalyRecord t WHERE t.dataCode = :dataCode AND t.verify='不验证'";
        List<TestingTechnicalAnalyRecord> records =
                baseDaoSupport.findByNamedParam(TestingTechnicalAnalyRecord.class, hql, new String[]{"dataCode"}, new String[]{dataCode});
        return records;
    }

    public List<TestingReportTempSave> getReportTempSaves(String reportId, String semantic) {
        StringBuffer hql = new StringBuffer("FROM TestingReportTempSave sd WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(reportId)) {
            hql.append(" AND sd.reportId = :reportId");
            paramNames.add("reportId");
            parameters.add(reportId);
        }
        if (StringUtils.isNotEmpty(semantic)) {
            hql.append(" AND sd.semantic = :semantic");
            paramNames.add("semantic");
            parameters.add(semantic);
        }
        NamedQueryer queryer = NamedQueryer.builder().hql(hql.toString()).names(paramNames).values(parameters).build();
        List<TestingReportTempSave> reportTemps = baseDaoSupport.find(queryer, TestingReportTempSave.class);
        return reportTemps;
    }

    //根据原样本判断是否重新送样
    public boolean isReSample(String sampleId) {
        String hql = "FROM TestingResamplingRecord tr WHERE tr.abnormalSampleId = :sampleId AND tr.resendSampleStatus = :status";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("sampleId", "status"))
                        .values(Lists.newArrayList(sampleId, TestingResamplingRecord.RESEND_SAMPLE_RECEIVED))
                        .build();
        List<TestingResamplingRecord> list = baseDaoSupport.find(queryer, TestingResamplingRecord.class);
        if (Collections3.isNotEmpty(list)) {
            return true;
        }
        return false;

    }

    //获取重新送样后的样本
    public String getResendSampleId(String sampleId) {
        String hql = "FROM TestingResamplingRecord tr WHERE tr.abnormalSampleId = :sampleId AND tr.resendSampleStatus = :status";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("sampleId", "status"))
                        .values(Lists.newArrayList(sampleId, TestingResamplingRecord.RESEND_SAMPLE_RECEIVED))
                        .build();
        List<TestingResamplingRecord> list = baseDaoSupport.find(queryer, TestingResamplingRecord.class);
        if (Collections3.isNotEmpty(list)) {
            return list.get(0).getResendSampleId();
        }
        return null;

    }

    //获取重新送样的原样本
    public String getAbnormalSampleIdForTestingSampleOrReceiveSample(String resendSampleId) {
        String hql = "FROM TestingResamplingRecord tr WHERE tr.resendSampleId = :resendSampleId AND tr.resendSampleStatus = :status";
        NamedQueryer queryer =
                NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("resendSampleId", "status"))
                        .values(Lists.newArrayList(resendSampleId, TestingResamplingRecord.RESEND_SAMPLE_RECEIVED))
                        .build();
        List<TestingResamplingRecord> list = baseDaoSupport.find(queryer, TestingResamplingRecord.class);
        if (Collections3.isNotEmpty(list)) {
            List<TestingSample> records =
                    baseDaoSupport.findByNamedParam(TestingSample.class,
                            "FROM TestingSample t WHERE t.receivedSample.id = :originalSampleId AND t.parentSample.id is NULL ",
                            new String[]{"originalSampleId"},
                            new String[]{list.get(0).getAbnormalSampleId()});
            if (Collections3.isNotEmpty(records)) {
                return records.get(0).getId();
            } else {
                return list.get(0).getAbnormalSampleId();
            }
        }
        return null;

    }

    @Override
    @Transactional
    public void send(SendDataRequest srequest) {
        // 记录
        TestingReportSendCallBack sendCallBack = new TestingReportSendCallBack();
        sendCallBack.setReportId(srequest.getReportId());
        sendCallBack.setStatus(TestingReportSendCallBack.SEND_STATUS_DOING);
        sendCallBack.setCreateTime(new Date());
        sendCallBack.setReportTemplateType(srequest.getReportTemplateType());
        baseDaoSupport.insert(sendCallBack);

        // 记录数据
        List<TestingReportTempSave> tempSaves = getReportTempSaves(srequest.getReportId(), null);
        if (Collections3.isNotEmpty(tempSaves)) {
            for (TestingReportTempSave temp : tempSaves) {
                TestingReportSendDetails sendDetails = new TestingReportSendDetails();
                sendDetails.setSendCallBack(sendCallBack);
                sendDetails.setSemantic(temp.getSemantic());
                sendDetails.setRecordId(temp.getRecordId());
                sendDetails.setClinicalJudgment(temp.getClinicalJudgment());
                sendDetails.setMutationSource(temp.getMutationSource());
                baseDaoSupport.insert(sendDetails);
            }
        }

        // 更改report
        TestingReport report = baseDaoSupport.get(TestingReport.class, srequest.getReportId());
        report.setSendStatus(TestingReportSendCallBack.SEND_STATUS_DOING);
        report.setReportTemplateType(srequest.getReportTemplateType());
        report.setReportTemplateType(srequest.getReportTemplateType());
        report.setReportType(srequest.getReportType());
        report.setAnalyResult(srequest.getAnalyResult());
        baseDaoSupport.update(report);

        // 发送数据
        ReportSendDataModel sendDataModel = getSendData(report, srequest.getReportTemplateType(), srequest.getReportType(), srequest.getAnalyResult());
        String url = productAdapter.getAddressConfigByKey("URL_ADDRESS") + "/report/generate/";
        System.out.println("---url:" + url);
        System.out.println(JSON.toJSONString(sendDataModel));
        log.info(" sendDataModel is： " + JSON.toJSONString(sendDataModel));
        HttpRequest request = new HttpRequest();
        HttpResponse response =
                request.post(url).contentType("application/json; charset=utf-8").charset("utf-8").bodyText(JSON.toJSONString(sendDataModel)).send();
        System.out.println(response.body());
    }

    @Override
    public List<TestingReport> getUpdateList() {
        long time = 30 * 60 * 1000;// 30分钟
        Date now = new Date();
        Date dateTime = new Date(now.getTime() - time);// 30分钟前的时间
        // 1.定时任务去查询麦基诺的地址 报告生成状态 处理中的报告30分钟还没有结果的 就去查询
        String hql =
                "FROM TestingReport t WHERE t.sendStatus=1 AND t.status != 2 AND EXISTS(FROM TestingReportSendCallBack c WHERE t.id=c.reportId AND c.createTime < :dateTime )";
        List<TestingReport> records = baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[]{"dateTime"}, new Object[]{dateTime});
        log.info(" there is " + records.size() + " report wait todo ");
        return records;
    }

    @Override
    @Transactional
    public void assign(TestingReportAssignModel request, String token) {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        String reportIds = request.getReportIds();
        if (StringUtils.isNotEmpty(reportIds)) {
            String[] reportArr = reportIds.split(",");
            for (String reportId : reportArr) {
                TestingReport testingReport = baseDaoSupport.get(TestingReport.class, reportId);
                if (null != testingReport) {
                    testingReport.setAssignStatus(1);//已下达
                    testingReport.setAssignDate(new Date());
                    if (null != loginer) {
                        testingReport.setAssignerId(loginer.getId());
                        testingReport.setAssignerName(loginer.getName());
                    }
                    String receiverId = request.getReceiverId();
                    if (StringUtils.isEmpty(receiverId)) {
                        throw new IllegalStateException();
                    }
                    UserMinimalModel user = smmadapter.getUserByID(receiverId);
                    if (null != user) {
                        testingReport.setReceiverId(user.getId());
                        testingReport.setReceiverName(user.getName());
                    }
                    baseDaoSupport.update(testingReport);
                }
            }
        }
    }

    private ReportSendDataModel getSendData(TestingReport report, String reportTemplateType, String reportType, String analyResult) {
        ReportSendDataModel model = new ReportSendDataModel();
        model.setReportTemplateType(reportTemplateType);
        model.setReportType(reportType);
        model.setAnalyResult(analyResult);
        model.setInstruction(report.getInstruction());
        Order order = report.getOrder();
        setBaseData(report, order, model);
        // examinee
        setExamineeData(order, model);
        // samples
        setSamplesData(order, model);
        // testingDatas
        setTestingDatas(report, model);
        //verifyDatas
        setVerifyDatas(report, model);
        return model;

    }

    /**
     * set基础数据
     *
     * @param report
     * @param model
     */
    public void setBaseData(TestingReport report, Order order, ReportSendDataModel model) {
        model.setReportKey(report.getId());
        model.setReportCode(report.getReportCode());
        model.setOrderCode(order.getCode());
        model.setMarketingCenter(order.getType().getName());
        if (null != order.getContract()) {
            model.setContractCode(order.getContract().getCode());
            model.setContractName(order.getContract().getName());
        }
        Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
        model.setCustomerName(customer.getRealName());
        model.setCustomerCompanyName(customer.getCompany().getName());
        model.setSalesmanName(order.getCreatorName());
        model.setOrderRemark(order.getRemark());
        model.setOrderSubmitTime(DateUtils.formatDateTime(order.getSubmitTime()));
        String tecNames = "";// 技术负责人
        Product product = report.getProduct();
        model.setProductCode(product.getCode());
        model.setProductName(product.getName());
        if (null != product.getProductDuty()) {
            model.setProductPrincipalName(product.getProductDuty().getArchive().getName());
        }
        List<ProductPrincipal> productPrincipalList = product.getProductPrincipalList();
        if (Collections3.isNotEmpty(productPrincipalList)) {
            for (int i = 0; i < productPrincipalList.size(); i++) {
                ProductPrincipal pp = productPrincipalList.get(i);
                if (i == 0) {
                    tecNames = pp.getPrincipal().getArchive().getName();
                } else {
                    tecNames += "," + pp.getPrincipal().getArchive().getName();
                }

            }
        }
        model.setTechnicalPrincipalName(tecNames);
    }

    /**
     * set examinee受检人
     *
     * @param order
     * @param model
     */
    public void setExamineeData(Order order, ReportSendDataModel model) {
        ExamineeModel examineeModel = new ExamineeModel();
        String strDiagnosis = "";
        String strPhenotype = "";
        String strGenes = "";
        List<OrderExaminee> orderExamineeList = order.getOrderExamineeList();
        if (Collections3.isNotEmpty(orderExamineeList)) {
            OrderExaminee orderExaminee = orderExamineeList.get(0);
            examineeModel.setName(orderExaminee.getName());
            if (StringUtils.isNotEmpty(orderExaminee.getSex())) {
                if ("0".equals(orderExaminee.getSex())) {
                    examineeModel.setSex("男");
                }
                if ("1".equals(orderExaminee.getSex())) {
                    examineeModel.setSex("女");
                }
            }
            examineeModel.setAge(orderExaminee.getAgeSnapshot());
            examineeModel.setRecordNo(orderExaminee.getRecordNo());
            examineeModel.setMedicalHistory(orderExaminee.getMedicalHistory());
            examineeModel.setFamilyMedicalHistory(orderExaminee.getFamilyMedicalHistory());
            examineeModel.setClinicalRecommend(orderExaminee.getClinicalRecommend());
            List<OrderExamineeDiagnosis> orderExamineeDiagnosis = orderExaminee.getOrderExamineeDiagnosis();
            if (Collections3.isNotEmpty(orderExamineeDiagnosis)) {
                for (OrderExamineeDiagnosis ods : orderExamineeDiagnosis) {
                    strDiagnosis += ods.getDisease().getName() + ",";
                }
            }
            List<OrderExamineePhenotype> orderExamineePhenotype = orderExaminee.getOrderExamineePhenotype();
            if (Collections3.isNotEmpty(orderExamineePhenotype)) {
                for (OrderExamineePhenotype oep : orderExamineePhenotype) {
                    strPhenotype += oep.getPhenotype().getName() + ",";
                }
            }
            List<OrderExamineeGene> orderExamineeGene = orderExaminee.getOrderExamineeGene();
            if (Collections3.isNotEmpty(orderExamineeGene)) {
                for (OrderExamineeGene oee : orderExamineeGene) {
                    strGenes += oee.getGene().getSymbolName() + ",";
                }
            }
            if (StringUtils.isNotEmpty(strDiagnosis)) {
                strDiagnosis = strDiagnosis.substring(0, strDiagnosis.length() - 1);
            }
            if (StringUtils.isNotEmpty(strPhenotype)) {
                strPhenotype = strPhenotype.substring(0, strPhenotype.length() - 1);
            }
            if (StringUtils.isNotEmpty(strGenes)) {
                strGenes = strGenes.substring(0, strGenes.length() - 1);
            }
            examineeModel.setClinicalDiagnosis(strDiagnosis);
            examineeModel.setClinicalPhenotype(strPhenotype);
            examineeModel.setFoucsedGenes(strGenes);
        }
        model.setExaminee(examineeModel);
    }

    /**
     * set样本
     *
     * @param order
     * @param model
     */
    public void setSamplesData(Order order, ReportSendDataModel model) {
        List<SampleModel> sampleModelList = Lists.newArrayList();
        List<OrderPrimarySample> primarySamples = getPrimarySamples(order.getId());
        List<OrderSubsidiarySample> subsidiarySamples = getSubsidiarySamples(order.getId());
        List<OrderResearchSample> researchSamples = getResearchSamples(order.getId());
        if (Collections3.isNotEmpty(primarySamples)) {
            for (OrderPrimarySample ops : primarySamples) {
                SampleModel sampleModel = new SampleModel();
                sampleModel.setSampleCode(ops.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, ops.getSampleTypeId());
                if (null != sampleType) {
                    sampleModel.setSampleTypeName(sampleType.getName());
                }
                OrderExaminee oe = baseDaoSupport.get(OrderExaminee.class, ops.getExamineeId());
                sampleModel.setSampleName(oe.getName());
                sampleModel.setSampleBusinessType("1");
                sampleModelList.add(sampleModel);
            }
        }

        if (Collections3.isNotEmpty(subsidiarySamples)) {
            for (OrderSubsidiarySample oss : subsidiarySamples) {
                SampleModel sampleModel = new SampleModel();
                sampleModel.setSampleCode(oss.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, oss.getSampleTypeId());
                if (null != sampleType) {
                    sampleModel.setSampleTypeName(sampleType.getName());
                }
                sampleModel.setSampleName(oss.getOwnerName());
                sampleModel.setSampleBusinessType("2");
                sampleModel.setSamplePurpose(String.valueOf(oss.getPurpose()));
                sampleModel.setSampleFamilyRelation(getDictText(oss.getFamilyRelation()));
                sampleModelList.add(sampleModel);
            }
        }
        if (Collections3.isNotEmpty(researchSamples)) {
            for (OrderResearchSample ors : researchSamples) {
                SampleModel sampleModel = new SampleModel();
                sampleModel.setSampleCode(ors.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, ors.getSampleTypeId());
                if (null != sampleType) {
                    sampleModel.setSampleTypeName(sampleType.getName());
                }
                sampleModel.setSampleName(ors.getSampleName());
                sampleModel.setSampleBusinessType("3");
                sampleModel.setSampleFamilyRelation(ors.getFamilyRelation());
                sampleModelList.add(sampleModel);
            }
        }
        model.setSamples(sampleModelList);
    }

    /**
     * set检测数据
     *
     * @param report
     * @param model
     */
    public void setTestingDatas(TestingReport report, ReportSendDataModel model) {
        List<TestingDataModel> testingDataList = Lists.newArrayList();
        List<TestingReportTempSave> tempSaves = getReportTempSaves(report.getId(), null);
        if (Collections3.isNotEmpty(tempSaves)) {
            for (TestingReportTempSave tempSave : tempSaves) {
                if (!"PICTURE".equals(tempSave.getSemantic())) {
                    TestingTechnicalAnalyRecord technical = baseDaoSupport.get(TestingTechnicalAnalyRecord.class, tempSave.getRecordId());
                    if (null != technical) {
                        Map<String, String> map = getColumnNames(technical.getSheet().getId());
                        // 判断是否数据编号一样，一样就直接放details
                        TestingDataModel dataModel = isHasDataCode(testingDataList, technical.getDataCode());
                        if (null != dataModel) {
                            Map<String, String> mapSon = JsonUtils.asObject(technical.getOtherFields(), Map.class);
                            map = setColumnValuesByKey(map, mapSon);
                            map.put("临床相关性判断", tempSave.getClinicalJudgment());
                            map.put("突变来源", tempSave.getMutationSource());
                            dataModel.getDetails().add(map);
                            //重复编号 也要把验证的图片放进去 2017.9.14
                            /*List<TestingVerifyRecord> verifyList = getVerifyRecordByRecordId(technical.getId());
                            if (Collections3.isNotEmpty(verifyList))
                            {
                                for (TestingVerifyRecord verifyRecord : verifyList)
                                {
                                    // 验证
                                    if ("Sanger".equals(technical.getVerifyMethod()) || "PCR-NGS".equals(technical.getVerifyMethod()))
                                    {
                                        SangerVerifyRecord sangerVerifyRecord = getSangerVerify(verifyRecord.getId());
                                        if (null != sangerVerifyRecord)
                                        {
                                            TestingSchedule verifySchedule = getVerifyTestingSchedule(sangerVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), sangerVerifyRecord.getCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        dataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if ("MLPA".equals(technical.getVerifyMethod()))
                                    {
                                        MlpaVerifyRecord mlpaVerifyRecord = getMlpaVerify(verifyRecord.getId());
                                        if (null != mlpaVerifyRecord)
                                        {
                                            TestingSchedule verifySchedule = getVerifyTestingSchedule(mlpaVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), mlpaVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        dataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if ("QPCR".equals(technical.getVerifyMethod()))
                                    {
                                        QpcrVerifyRecord qpcrVerifyRecord = getQpcrVerify(verifyRecord.getId());
                                        if (null != qpcrVerifyRecord)
                                        {
                                            TestingSchedule verifySchedule = getVerifyTestingSchedule(qpcrVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), qpcrVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        dataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }*/
                        } else {
                            TestingDataModel testingDataModel = new TestingDataModel();
                            testingDataModel.setFigures(Lists.newArrayList());
                            testingDataModel.setTestingMethod(tempSave.getSemantic());
                            testingDataModel.setDataCode(technical.getDataCode());
                            List<TestingVerifyRecord> verifyList = getVerifyRecordByRecordId(technical.getId());
                            if (Collections3.isNotEmpty(verifyList)) {
                                TestingSchedule schedule = null;
                                for (TestingVerifyRecord verifyRecord : verifyList) {
                                    // 验证
                                    if ("Sanger".equals(technical.getVerifyMethod()) || "PCR-NGS".equals(technical.getVerifyMethod())) {
                                        SangerVerifyRecord sangerVerifyRecord = getSangerVerify(verifyRecord.getId());
                                        if (null != sangerVerifyRecord) {
                                            schedule = getTestingSchedule(sangerVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(sangerVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), sangerVerifyRecord.getCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else if ("MLPA".equals(technical.getVerifyMethod())) {
                                        MlpaVerifyRecord mlpaVerifyRecord = getMlpaVerify(verifyRecord.getId());
                                        if (null != mlpaVerifyRecord) {
                                            schedule = getTestingSchedule(mlpaVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(mlpaVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), mlpaVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else if ("QPCR".equals(technical.getVerifyMethod())) {
                                        QpcrVerifyRecord qpcrVerifyRecord = getQpcrVerify(verifyRecord.getId());
                                        if (null != qpcrVerifyRecord) {
                                            schedule = getTestingSchedule(qpcrVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(qpcrVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), qpcrVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else
                                    // 不验证
                                    {
                                        if (StringUtils.isNotEmpty(technical.getDataCode())) {
                                            TestingScheduleHistory history = getScheduleHistoryByDataCode(technical.getDataCode());
                                            if (null != history) {
                                                schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                                            }

                                        }

                                    }
                                }
                                getProbesAndCoordinates(schedule, testingDataModel);
                            }
                            TestingSheet sheet = technical.getSheet();
                            if (null != sheet) {
                                PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                                String sequenceCode = variables.getPoolingCode();
                                testingDataModel.setLane(sequenceCode);
                            }
                            Map<String, String> mapSon = JsonUtils.asObject(technical.getOtherFields(), Map.class);
                            map = setColumnValuesByKey(map, mapSon);
                            map.put("临床相关性判断", tempSave.getClinicalJudgment());
                            map.put("突变来源", tempSave.getMutationSource());
                            testingDataModel.setDetails(Lists.newArrayList(map));

                            TestingTask libTask = getTestingTaskByDataCode(technical.getDataCode(), "RQT");
                            getLibraryAttributes(libTask, testingDataModel);
                            testingDataList.add(testingDataModel);
                        }
                    } else {
                        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, tempSave.getRecordId());
                        Map<String, String> map = null;
                        List<TestingSheetTask> sheetTaskList =
                                baseDaoSupport.find(TestingSheetTask.class, "FROM TestingSheetTask t WHERE t.testingTaskId = '" + taskResult.getTaskId() + "'");
                        if (Collections3.isNotEmpty(sheetTaskList)) {
                            map = getColumnNames(sheetTaskList.get(0).getTestingSheet().getId());
                        }
                        TestingDataModel testingDataModel = new TestingDataModel();
                        testingDataModel.setTestingMethod(tempSave.getSemantic());
                        TestingScheduleHistory history = getScheduleHistoryByTaskId(taskResult.getTaskId());
                        if (null != history) {
                            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                            getProbesAndCoordinates(schedule, testingDataModel);
                            TestingScheduleHistory hs = getScheduleHistoryByScheduleId(history.getScheduleId());
                            if (null != hs) {
                                TestingTask rqtTask = baseDaoSupport.get(TestingTask.class, hs.getTaskId());
                                getLibraryAttributes(rqtTask, testingDataModel);
                            }
                        }

                        TestingSheetTask sheetTask = getSheetTask(taskResult.getTaskId());
                        TestingSheet sheet = sheetTask.getTestingSheet();
                        if (null != sheet) {
                            PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                            String sequenceCode = variables.getPoolingCode();
                            testingDataModel.setLane(sequenceCode);
                        }
                        Map<String, String> mapSon = JsonUtils.asObject(taskResult.getDetails(), Map.class);
                        map = setColumnValuesByKey(map, mapSon);
                        testingDataModel.setDetails(Lists.newArrayList(map));
                        testingDataModel.setFigures(Lists.newArrayList());
                        testingDataList.add(testingDataModel);
                    }
                }
            }

            for (TestingReportTempSave tempSave : tempSaves) {
                if ("PICTURE".equals(tempSave.getSemantic())) {
                    TestingDataPic dataPic = baseDaoSupport.get(TestingDataPic.class, tempSave.getRecordId());
                    TestingDataModel dataModel = isHasDataCode(testingDataList, dataPic.getDataCode());
                    if (null != dataModel) {
                        TestingDataPicModel picModel = new TestingDataPicModel();
                        picModel.setPicName(dataPic.getPicName());
                        picModel.setPicUrl(dataPic.getPicUrl());
                        /*if (null == dataModel.getFigures())
                        {
                            dataModel.setFigures(Lists.newArrayList());
                        }*/
                        dataModel.getFigures().add(picModel);
                    }
                }
            }
        }

        model.setTestingDatas(testingDataList);
    }

    /**
     * 所有验证数据
     */
    public void setVerifyDatas(TestingReport report, ReportSendDataModel model) {
        List<VerifyDataModel> verifyDatas = Lists.newArrayList();
        List<TestingMethod> verifyMethods = getVerifyMethods(report.getOrder().getId(), report.getProduct().getId());
        if (Collections3.isNotEmpty(verifyMethods)) {
            for (TestingMethod method : verifyMethods) {
                VerifyDataModel verifyDataModel = new VerifyDataModel();
                verifyDataModel.setVerifyMethod(method.getName());
                List<VerifySampleModel> verifySamples = getVerifySamples(report, method.getId());
                verifyDataModel.setVerifySamples(verifySamples);
                verifyDatas.add(verifyDataModel);
            }
        }
        model.setVerifyDatas(verifyDatas);
    }

    /**
     * 获取模板的列名  （key）
     */
    private Map<String, String> getColumnNames(String sheetId) {
        Map<String, String> map = new HashMap<>();
        TestingSheet ts = getTestingSheet(sheetId);
        if (null != ts) {
            DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(ts.getId());
            if (null != dataTemplate) {
                for (DataTemplateColumn dtc : dataTemplate.getColumnList()) {
                    map.put(dtc.getColumnName(), "");
                }
            }
        }
        return map;

    }

    /**
     * 根据key获取值
     *
     * @param
     * @return
     */
    private Map<String, String> setColumnValuesByKey(Map<String, String> map, Map<String, String> mapSon) {
        if (map.isEmpty()) {
            return mapSon;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = mapSon.get(entry.getKey());
            if (null != value) {
                entry.setValue(value);
            }
        }
        return map;

    }

    /**
     * 文库号、文库接头
     *
     * @param libTask
     * @param testingDataModel
     */
    public void getLibraryAttributes(TestingTask libTask, TestingDataModel testingDataModel) {
        if (null != libTask) {
            String strCode = "";
            String strIndex = "";
            if (StringUtils.isNotEmpty(libTask.getTestingSampleAttributes())) {
                LibraryCaptureSampleAttributes attributes = JsonUtils.asObject(libTask.getTestingSampleAttributes(), LibraryCaptureSampleAttributes.class);
                if (null != attributes) {
                    Set<CaptureLibraryAttributes> libraries = attributes.getLibraries();
                    if (null != libraries) {
                        for (CaptureLibraryAttributes ca : libraries) {
                            strCode += ca.getSampleCode() + ",";
                            strIndex += ca.getIndex() + ",";
                        }
                    }
                }
                if (StringUtils.isNotEmpty(strCode)) {
                    strCode = strCode.substring(0, strCode.length() - 1);
                    strIndex = strIndex.substring(0, strIndex.length() - 1);
                }
                testingDataModel.setLibraryCode(strCode);
                testingDataModel.setLibraryIndex(strIndex);
            }
        }
    }

    /**
     * 探针 分析坐标
     *
     * @param schedule
     * @param testingDataModel
     */
    public void getProbesAndCoordinates(TestingSchedule schedule, TestingDataModel testingDataModel) {
        if (null != schedule) {
            String strProbe = "";
            ProductTestingMethod productTestingMethod = getProductTestingMethod(schedule.getProductId(), schedule.getMethodId());
            List<ProductProbe> probes = productTestingMethod.getProductProbe();
            if (Collections3.isNotEmpty(probes)) {
                for (ProductProbe probe : probes) {
                    strProbe += probe.getProbe().getName();
                }
            }
            if (StringUtils.isNotEmpty(strProbe)) {
                strProbe = strProbe.substring(0, strProbe.length() - 1);
            }
            testingDataModel.setProbes(strProbe);
            testingDataModel.setAnalyCoordinates(productTestingMethod.getCoordinate());
        }
    }

    /**
     * 判断发送数据的model中是否已经有相同的dataCode
     *
     * @param testingDataList
     * @param
     * @return
     */
    public TestingDataModel isHasDataCode(List<TestingDataModel> testingDataList, String dataCode) {
        if (Collections3.isNotEmpty(testingDataList)) {
            for (TestingDataModel datamodel : testingDataList) {
                if (dataCode.equals(datamodel.getDataCode())) {
                    return datamodel;
                }
            }
        }
        return null;

    }

    public List<OrderPrimarySample> getPrimarySamples(String orderId) {
        String hql = "FROM OrderPrimarySample s WHERE s.order.id = :orderId";
        List<OrderPrimarySample> records = baseDaoSupport.findByNamedParam(OrderPrimarySample.class, hql, new String[]{"orderId"}, new String[]{orderId});
        return records;
    }

    public List<OrderSubsidiarySample> getSubsidiarySamples(String orderId) {
        String hql = "FROM OrderSubsidiarySample s WHERE s.order.id = :orderId";
        List<OrderSubsidiarySample> records =
                baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql, new String[]{"orderId"}, new String[]{orderId});
        return records;
    }

    public List<OrderResearchSample> getResearchSamples(String orderId) {
        String hql = "FROM OrderResearchSample s WHERE s.order.id = :orderId";
        List<OrderResearchSample> records = baseDaoSupport.findByNamedParam(OrderResearchSample.class, hql, new String[]{"orderId"}, new String[]{orderId});
        return records;
    }

    public String getDictText(String value) {
        String hql = "FROM Dict d WHERE d.category = 'FAMILY_RELATION' AND d.value = :value";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[]{"value"}, new String[]{value});
        return Collections3.isNotEmpty(records) ? records.get(0).getText() : null;
    }

    public List<TestingVerifyRecord> getVerifyRecordByRecordId(String recordId) {
        String hql = "FROM TestingVerifyRecord t WHERE t.analyRecord.id = :recordId ";
        List<TestingVerifyRecord> records = baseDaoSupport.findByNamedParam(TestingVerifyRecord.class, hql, new String[]{"recordId"}, new String[]{recordId});
        return records;
    }

    public SangerVerifyRecord getSangerVerify(String verifyRecord) {
        String hql = "FROM SangerVerifyRecord t WHERE t.verifyRecord.id = :verifyRecord ";
        List<SangerVerifyRecord> records =
                baseDaoSupport.findByNamedParam(SangerVerifyRecord.class, hql, new String[]{"verifyRecord"}, new String[]{verifyRecord});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public QpcrVerifyRecord getQpcrVerify(String verifyRecord) {
        String hql = "FROM QpcrVerifyRecord t WHERE t.verifyRecord.id = :verifyRecord ";
        List<QpcrVerifyRecord> records =
                baseDaoSupport.findByNamedParam(QpcrVerifyRecord.class, hql, new String[]{"verifyRecord"}, new String[]{verifyRecord});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public MlpaVerifyRecord getMlpaVerify(String verifyRecord) {
        String hql = "FROM MlpaVerifyRecord t WHERE t.verifyRecord.id = :verifyRecord ";
        List<MlpaVerifyRecord> records =
                baseDaoSupport.findByNamedParam(MlpaVerifyRecord.class, hql, new String[]{"verifyRecord"}, new String[]{verifyRecord});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingSchedule getVerifyTestingSchedule(String verifyKey) {
        String hql = "from TestingSchedule tt where tt.verifyKey = :verifyKey";
        List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"verifyKey"}, new String[]{verifyKey});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingSchedule getTestingSchedule(String verifyKey) {
        String hql = "FROM TestingSchedule t WHERE EXISTS (select tt.id from TestingSchedule tt where tt.verifyTarget = t.id and tt.verifyKey = :verifyKey)";
        List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[]{"verifyKey"}, new String[]{verifyKey});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public ProductTestingMethod getProductTestingMethod(String productId, String methodId) {
        String hql = "FROM ProductTestingMethod t WHERE t.product.id = :productId AND t.testingMethod.id = :methodId";
        List<ProductTestingMethod> records =
                baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[]{"productId", "methodId"}, new String[]{productId, methodId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingScheduleHistory getScheduleHistoryByDataCode(String dataCode) {
        String hql =
                "FROM TestingScheduleHistory t WHERE EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId and tt.testingAnalyDataCode = :dataCode ) order by t.timestamp desc";
        List<TestingScheduleHistory> records =
                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"dataCode"}, new String[]{dataCode});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingScheduleHistory getScheduleHistoryByTaskId(String taskId) {
        String hql = "FROM TestingScheduleHistory t WHERE t.taskId = :taskId ";
        List<TestingScheduleHistory> records =
                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"taskId"}, new String[]{taskId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingSheetTask getSheetTask(String taskId) {
        String hql = "FROM TestingSheetTask t WHERE t.testingTaskId = :taskId ";
        List<TestingSheetTask> records = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[]{"taskId"}, new String[]{taskId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    public TestingTask getTestingTaskByDataCode(String dataCode, String semantic) {
        String hql = "FROM TestingTask t WHERE t.testingAnalyDataCode = :dataCode AND t.semantic = :semantic order by t.startTime DESC";
        List<TestingTask> records =
                baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[]{"dataCode", "semantic"}, new String[]{dataCode, semantic});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;

    }

    public TestingScheduleHistory getScheduleHistoryByScheduleId(String scheduleId) {

        String hql =
                "FROM TestingScheduleHistory sh WHERE EXISTS( select t.id from TestingTask t where sh.taskId = t.id and t.semantic = 'RQT')  AND sh.scheduleId = :scheduleId order by sh.timestamp DESC";
        List<TestingScheduleHistory> records =
                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"scheduleId"}, new String[]{scheduleId});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }

    @Override
    @Transactional
    public void remark(TestingReport request) {
        TestingReport report = baseDaoSupport.get(TestingReport.class, request.getId());
        report.setOperateRemark(request.getOperateRemark());
        baseDaoSupport.update(report);

    }

    @Override
    public Pagination<ReportSendCallBackModel> reportSendPaging(ReportSendSearcher searcher) {
        Pagination<ReportSendCallBackModel> paginmodel = new Pagination<ReportSendCallBackModel>();
        Pagination<TestingReportSendCallBack> pagination =
                baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), TestingReportSendCallBack.class);
        BeanUtils.copyProperties(pagination, paginmodel);
        List<ReportSendCallBackModel> list = Lists.newArrayList();
        if (Collections3.isNotEmpty(pagination.getRecords())) {
            for (TestingReportSendCallBack sendCallBack : pagination.getRecords()) {
                list.add(wrap(sendCallBack));
            }
        }
        paginmodel.setRecords(list);
        return paginmodel;
    }

    private ReportSendCallBackModel wrap(TestingReportSendCallBack sendCallBack) {
        ReportSendCallBackModel model = new ReportSendCallBackModel();
        BeanUtils.copyProperties(sendCallBack, model);
        TestingReport report = baseDaoSupport.get(TestingReport.class, sendCallBack.getReportId());
        model.setReportCode(report.getReportCode());
        return model;
    }

    @Override
    public ReportSendDataModel getSendDataForView(String id) {
        TestingReportSendCallBack reportSendCallBack = baseDaoSupport.get(TestingReportSendCallBack.class, id);
        List<TestingReportSendDetails> details = reportSendCallBack.getSendDetails();
        TestingReport report = baseDaoSupport.get(TestingReport.class, reportSendCallBack.getReportId());
        Order order = report.getOrder();
        ReportSendDataModel model = new ReportSendDataModel();
        String text = "";
        if (StringUtils.isNotEmpty(reportSendCallBack.getReportTemplateType())) {
            text = dictadapter.getDictTestByCategoryAndValue("REPORT_TYPE", reportSendCallBack.getReportTemplateType());
        }
        model.setReportTemplateType(text);
        model.setReportType("");
        if (StringUtils.isNotEmpty(report.getReportType())) {
            if ("1".equals(report.getReportType())) {
                model.setReportType("明确阳性");
            }
            if ("2".equals(report.getReportType())) {
                model.setReportType("疑似阳性");
            }
            if ("3".equals(report.getReportType())) {
                model.setReportType("可疑");
            }
            if ("4".equals(report.getReportType())) {
                model.setReportType("阴性");
            }

        }
        model.setAnalyResult("");
        if (StringUtils.isNotEmpty(report.getAnalyResult())) {
            if ("1".equals(report.getAnalyResult())) {
                model.setAnalyResult("发现与疾病表型相关的致病性突变");
            }
            if ("2".equals(report.getAnalyResult())) {
                model.setAnalyResult("发现与疾病表型相关的疑似致病性突变");
            }
            if ("3".equals(report.getAnalyResult())) {
                model.setAnalyResult("发现与疾病表型相关的可疑变异");
            }
            if ("4".equals(report.getAnalyResult())) {
                model.setAnalyResult("未发现与疾病表型相关的明确致病性突变");
            }
        }
        model.setInstruction(report.getInstruction());
        setBaseData(report, order, model);
        // examinee
        setExamineeData(order, model);
        // samples
        setSamplesData(order, model);
        // testingDatas
        getSendDetailsTestingDatas(details, model);
        //verifyDatas
        setVerifyDatas(report, model);
        return model;
    }

    public void getSendDetailsTestingDatas(List<TestingReportSendDetails> details, ReportSendDataModel model) {
        List<TestingDataModel> testingDataList = Lists.newArrayList();
        if (Collections3.isNotEmpty(details)) {
            for (TestingReportSendDetails tempSave : details) {
                if (!"PICTURE".equals(tempSave.getSemantic())) {
                    TestingTechnicalAnalyRecord technical = baseDaoSupport.get(TestingTechnicalAnalyRecord.class, tempSave.getRecordId());
                    if (null != technical) {
                        Map<String, String> map = getColumnNames(technical.getSheet().getId());
                        // 判断是否数据编号一样，一样就直接放details
                        TestingDataModel dataModel = isHasDataCode(testingDataList, technical.getDataCode());
                        if (null != dataModel) {
                            Map<String, String> mapSon = JsonUtils.asObject(technical.getOtherFields(), Map.class);
                            map = setColumnValuesByKey(map, mapSon);
                            map.put("临床相关性判断", tempSave.getClinicalJudgment());
                            map.put("突变来源", tempSave.getMutationSource());
                            dataModel.getDetails().add(map);
                            //重复编号 也要把验证的图片放进去 2017.9.14
                            /*  List<TestingVerifyRecord> verifyList = getVerifyRecordByRecordId(technical.getId());
                              if (Collections3.isNotEmpty(verifyList))
                              {
                                  for (TestingVerifyRecord verifyRecord : verifyList)
                                  {
                                      // 验证
                                      if ("Sanger".equals(technical.getVerifyMethod()) || "PCR-NGS".equals(technical.getVerifyMethod()))
                                      {
                                          SangerVerifyRecord sangerVerifyRecord = getSangerVerify(verifyRecord.getId());
                                          if (null != sangerVerifyRecord)
                                          {
                                              TestingSchedule verifySchedule = getVerifyTestingSchedule(sangerVerifyRecord.getId());
                                              if(null != verifySchedule)
                                              {
                                                //验证图片
                                                  List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), sangerVerifyRecord.getCode(), verifySchedule.getMethodId());
                                                  if(Collections3.isNotEmpty(pictures))
                                                  {
                                                      for(TestingDataPic pic: pictures)
                                                      {
                                                          TestingDataPicModel picModel = new TestingDataPicModel();
                                                          picModel.setPicName(pic.getPicName());
                                                          picModel.setPicUrl(pic.getPicUrl());
                                                          dataModel.getFigures().add(picModel);
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                      else if ("MLPA".equals(technical.getVerifyMethod()))
                                      {
                                          MlpaVerifyRecord mlpaVerifyRecord = getMlpaVerify(verifyRecord.getId());
                                          if (null != mlpaVerifyRecord)
                                          {
                                              TestingSchedule verifySchedule = getVerifyTestingSchedule(mlpaVerifyRecord.getId());
                                              if(null != verifySchedule)
                                              {
                                                //验证图片
                                                  List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), mlpaVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                  if(Collections3.isNotEmpty(pictures))
                                                  {
                                                      for(TestingDataPic pic: pictures)
                                                      {
                                                          TestingDataPicModel picModel = new TestingDataPicModel();
                                                          picModel.setPicName(pic.getPicName());
                                                          picModel.setPicUrl(pic.getPicUrl());
                                                          dataModel.getFigures().add(picModel);
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                      else if ("QPCR".equals(technical.getVerifyMethod()))
                                      {
                                          QpcrVerifyRecord qpcrVerifyRecord = getQpcrVerify(verifyRecord.getId());
                                          if (null != qpcrVerifyRecord)
                                          {
                                              TestingSchedule verifySchedule = getVerifyTestingSchedule(qpcrVerifyRecord.getId());
                                              if(null != verifySchedule)
                                              {
                                                //验证图片
                                                  List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), qpcrVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                  if(Collections3.isNotEmpty(pictures))
                                                  {
                                                      for(TestingDataPic pic: pictures)
                                                      {
                                                          TestingDataPicModel picModel = new TestingDataPicModel();
                                                          picModel.setPicName(pic.getPicName());
                                                          picModel.setPicUrl(pic.getPicUrl());
                                                          dataModel.getFigures().add(picModel);
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                  }
                              }*/
                        } else {
                            TestingDataModel testingDataModel = new TestingDataModel();
                            testingDataModel.setFigures(Lists.newArrayList());
                            testingDataModel.setTestingMethod(tempSave.getSemantic());
                            testingDataModel.setDataCode(technical.getDataCode());
                            List<TestingVerifyRecord> verifyList = getVerifyRecordByRecordId(technical.getId());
                            if (Collections3.isNotEmpty(verifyList)) {
                                TestingSchedule schedule = null;
                                for (TestingVerifyRecord verifyRecord : verifyList) {
                                    // 验证
                                    if ("Sanger".equals(technical.getVerifyMethod()) || "PCR-NGS".equals(technical.getVerifyMethod())) {
                                        SangerVerifyRecord sangerVerifyRecord = getSangerVerify(verifyRecord.getId());
                                        if (null != sangerVerifyRecord) {
                                            schedule = getTestingSchedule(sangerVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(sangerVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                                //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), sangerVerifyRecord.getCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else if ("MLPA".equals(technical.getVerifyMethod())) {
                                        MlpaVerifyRecord mlpaVerifyRecord = getMlpaVerify(verifyRecord.getId());
                                        if (null != mlpaVerifyRecord) {
                                            schedule = getTestingSchedule(mlpaVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(mlpaVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), mlpaVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else if ("QPCR".equals(technical.getVerifyMethod())) {
                                        QpcrVerifyRecord qpcrVerifyRecord = getQpcrVerify(verifyRecord.getId());
                                        if (null != qpcrVerifyRecord) {
                                            schedule = getTestingSchedule(qpcrVerifyRecord.getId());
                                            /*TestingSchedule verifySchedule = getVerifyTestingSchedule(qpcrVerifyRecord.getId());
                                            if(null != verifySchedule)
                                            {
                                              //验证图片
                                                List<TestingDataPic> pictures = getSamplePictures(verifySchedule.getOrderId(), verifySchedule.getProductId(), qpcrVerifyRecord.getCombineCode(), verifySchedule.getMethodId());
                                                if(Collections3.isNotEmpty(pictures))
                                                {
                                                    for(TestingDataPic pic: pictures)
                                                    {
                                                        TestingDataPicModel picModel = new TestingDataPicModel();
                                                        picModel.setPicName(pic.getPicName());
                                                        picModel.setPicUrl(pic.getPicUrl());
                                                        testingDataModel.getFigures().add(picModel);
                                                    }
                                                }
                                            }*/
                                        }
                                    } else
                                    // 不验证
                                    {
                                        if (StringUtils.isNotEmpty(technical.getDataCode())) {
                                            TestingScheduleHistory history = getScheduleHistoryByDataCode(technical.getDataCode());
                                            if (null != history) {
                                                schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                                            }

                                        }

                                    }
                                }
                                getProbesAndCoordinates(schedule, testingDataModel);
                            }
                            TestingSheet sheet = technical.getSheet();
                            if (null != sheet) {
                                PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                                String sequenceCode = variables.getPoolingCode();
                                testingDataModel.setLane(sequenceCode);
                            }
                            Map<String, String> mapSon = JsonUtils.asObject(technical.getOtherFields(), Map.class);
                            map = setColumnValuesByKey(map, mapSon);
                            map.put("临床相关性判断", tempSave.getClinicalJudgment());
                            map.put("突变来源", tempSave.getMutationSource());
                            testingDataModel.setDetails(Lists.newArrayList(map));

                            TestingTask libTask = getTestingTaskByDataCode(technical.getDataCode(), "RQT");
                            getLibraryAttributes(libTask, testingDataModel);
                            testingDataList.add(testingDataModel);
                        }
                    } else {
                        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, tempSave.getRecordId());
                        Map<String, String> map = null;
                        List<TestingSheetTask> sheetTaskList =
                                baseDaoSupport.find(TestingSheetTask.class, "FROM TestingSheetTask t WHERE t.testingTaskId = '" + taskResult.getTaskId() + "'");
                        if (Collections3.isNotEmpty(sheetTaskList)) {
                            map = getColumnNames(sheetTaskList.get(0).getTestingSheet().getId());
                        }
                        TestingDataModel testingDataModel = new TestingDataModel();
                        testingDataModel.setTestingMethod(tempSave.getSemantic());
                        TestingScheduleHistory history = getScheduleHistoryByTaskId(taskResult.getTaskId());
                        if (null != history) {
                            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                            getProbesAndCoordinates(schedule, testingDataModel);
                            TestingScheduleHistory hs = getScheduleHistoryByScheduleId(history.getScheduleId());
                            if (null != hs) {
                                TestingTask rqtTask = baseDaoSupport.get(TestingTask.class, hs.getTaskId());
                                getLibraryAttributes(rqtTask, testingDataModel);
                            }
                        }

                        TestingSheetTask sheetTask = getSheetTask(taskResult.getTaskId());
                        TestingSheet sheet = sheetTask.getTestingSheet();
                        if (null != sheet) {
                            PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                            String sequenceCode = variables.getPoolingCode();
                            testingDataModel.setLane(sequenceCode);
                        }
                        Map<String, String> mapSon = JsonUtils.asObject(taskResult.getDetails(), Map.class);
                        map = setColumnValuesByKey(map, mapSon);
                        testingDataModel.setDetails(Lists.newArrayList(map));
                        testingDataModel.setFigures(Lists.newArrayList());
                        testingDataList.add(testingDataModel);
                    }
                }
            }

            for (TestingReportSendDetails tempSave : details) {
                if ("PICTURE".equals(tempSave.getSemantic())) {
                    TestingDataPic dataPic = baseDaoSupport.get(TestingDataPic.class, tempSave.getRecordId());
                    TestingDataModel dataModel = isHasDataCode(testingDataList, dataPic.getDataCode());
                    if (null != dataModel) {
                        TestingDataPicModel picModel = new TestingDataPicModel();
                        picModel.setPicName(dataPic.getPicName());
                        picModel.setPicUrl(dataPic.getPicUrl());
                        /*if (null == dataModel.getFigures())
                        {
                            dataModel.setFigures(Lists.newArrayList());
                        }*/
                        dataModel.getFigures().add(picModel);
                    }
                }
            }
        }

        model.setTestingDatas(testingDataList);
    }

    public Map<String, String> getTechManMapByProduct(String orderId, String productId, String productCode) {
        Map<String, String> result = Maps.newHashMap();
        List<TestingSheetModel> records = viewMapper.getSheetByOrderIdAndProjectId(orderId, productId);
        List<String> nameList = Lists.newArrayList();
        List<String> IdList = Lists.newArrayList();

        if (Collections3.isNotEmpty(records)) {
            for (TestingSheetModel sheetModel : records) {
                if (!nameList.contains(sheetModel.getTesterName())) {
                    nameList.add(sheetModel.getTesterName());
                }
                if (!IdList.contains(sheetModel.getTesterId())) {
                    IdList.add(sheetModel.getTesterId());
                }
            }
        }//技术分析 --拆分成新表 ---查询方法变更  sj
        else {
            log.info("change new technical method to search technical_name!!!");
            String hql = "SELECT distinct u FROM TechnicalAnalysisTask t ,User u where t.orderId=:orderId and t.productCode=:productCode and t.userId = u.id  ";
            List<User> users = baseDaoSupport.findByNamedParam(User.class, hql, new String[]{"orderId", "productCode",}, new Object[]{orderId, productCode});
            if (Collections3.isNotEmpty(users)) {
                users.forEach(o -> {
                    nameList.add(o.getArchive().getName());
                    IdList.add(o.getId());
                });

            }
        }
        String nameVals = StringUtils.join(nameList, ",");
        String idVals = StringUtils.join(IdList, ",");

        result.put("name", nameVals);
        result.put("id", idVals);
        return result;
    }

    @Override
    public List<User> getReportTaskUsers() {
        //查询任务配置的报告用户组人员
        List<User> results = Lists.newArrayList();
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.REPORT_COMBINE);
        if (null != taskConfig) {
            UserGroup userGroup = taskConfig.getUserGroup();
            if (null != userGroup) {
                results.add(userGroup.getGroupLeader());
                for (UserGroupMember member : userGroup.getUserGroupMembers()) {
                    results.add(member.getUser());
                }
            }
        }
        if (Collections3.isEmpty(results)) {
            results = baseDaoSupport.find(User.class, " FROM User u WHERE u.deleted=false ");
        }
        return results;
    }

    @Override
    public List<ReportAndVerifyModel> getReportAndVerifyModelByOrderId(String orderId) {
        List<ReportAndVerifyModel> modelList = Lists.newArrayList();
        String hql = "FROM TestingReport t WHERE t.order.id = :orderId";
        List<TestingReport> reportList = baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[]{"orderId"}, new String[]{orderId});
        if (Collections3.isNotEmpty(reportList)) {
            for (TestingReport report : reportList) {
                ReportAndVerifyModel model = new ReportAndVerifyModel();
                model.setReportId(report.getId());
                model.setReportCode(report.getReportCode());
                if (StringUtils.isNotEmpty(report.getReportName())) {
                    model.setReportName(report.getReportName().substring(20, report.getReportName().length()));
                }
                model.setSampleCode(report.getSampleCode());
                model.setProductName(report.getProductName());
                model.setReceiverName(report.getReceiverName());
                OrderProduct orderProduct = getOrderProductByOrderAndProduct(orderId, report.getProduct().getId());
                if (null != orderProduct) {
                    model.setReportTime(orderProduct.getReportTime());
                    model.setReportStatus(orderProduct.getReportStatus());
                }
                modelList.add(model);
            }
        }
        return modelList;
    }

    @Override
    public DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId) {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/searchMutationListByAnalsysiSampleId/{analysisSampleId}");
        ResponseEntity<DataColAndMutationDataModel> res = template.getForEntity(url, DataColAndMutationDataModel.class, analysisSampleId);
        return res.getBody();
    }

    @Override
    public List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(String analysisSampleId) {
        String url = GatewayService.getServiceUrl("/technicalanaly/collectionCapcnvData/{analysisSampleId}");
        ResponseEntity<List<CollectionCnvAnalysisPicResultList>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<CollectionCnvAnalysisPicResultList>(new CollectionCnvAnalysisPicResultList()),
                        new ParameterizedTypeReference<List<CollectionCnvAnalysisPicResultList>>() {
                        },
                        analysisSampleId);
        return exchange.getBody();
    }

}
