package com.todaysoft.lims.technical.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.ContractMapper;
import com.todaysoft.lims.technical.mybatis.CustomerMapper;
import com.todaysoft.lims.technical.mybatis.MarketingCenterMapper;
import com.todaysoft.lims.technical.mybatis.OrderMapper;
import com.todaysoft.lims.technical.mybatis.OrderSampleDetailMapper;
import com.todaysoft.lims.technical.mybatis.ProductMapper;
import com.todaysoft.lims.technical.mybatis.ProductPrincipalMapper;
import com.todaysoft.lims.technical.mybatis.ReceivedSampleMapper;
import com.todaysoft.lims.technical.mybatis.SchedulePlanTaskMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalyTestingTaskMapper;
import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskMapper;
import com.todaysoft.lims.technical.mybatis.TestingMethodMapper;
import com.todaysoft.lims.technical.mybatis.TestingSampleMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleActiveMapper;
import com.todaysoft.lims.technical.mybatis.TestingScheduleMapper;
import com.todaysoft.lims.technical.mybatis.TestingTaskMapper;
import com.todaysoft.lims.technical.mybatis.UserMapper;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.Contract;
import com.todaysoft.lims.technical.mybatis.entity.Customer;
import com.todaysoft.lims.technical.mybatis.entity.MarketingCenter;
import com.todaysoft.lims.technical.mybatis.entity.Order;
import com.todaysoft.lims.technical.mybatis.entity.OrderSampleDetail;
import com.todaysoft.lims.technical.mybatis.entity.Product;
import com.todaysoft.lims.technical.mybatis.entity.ProductPrincipal;
import com.todaysoft.lims.technical.mybatis.entity.ReceivedSample;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.mybatis.entity.TestingMethod;
import com.todaysoft.lims.technical.mybatis.entity.TestingSample;
import com.todaysoft.lims.technical.mybatis.entity.TestingSchedule;
import com.todaysoft.lims.technical.mybatis.entity.TestingScheduleActive;
import com.todaysoft.lims.technical.mybatis.entity.TestingTask;
import com.todaysoft.lims.technical.mybatis.entity.User;
import com.todaysoft.lims.technical.mybatis.entity.UserAchive;
import com.todaysoft.lims.technical.service.ITechnicalService;
import com.todaysoft.lims.technical.service.core.event.TechnicalCreateEvent;
import com.todaysoft.lims.technical.util.DateUtils;
import com.todaysoft.lims.technical.utils.Collections3;
import com.todaysoft.lims.technical.utils.StringUtils;

@Service
public class TechnicalService implements ITechnicalService
{
    
    @Autowired
    private TestingScheduleActiveMapper testingScheduleActiveMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private TestingScheduleMapper testingScheduleMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ContractMapper contractMapper;
    
    @Autowired
    private CustomerMapper customerMapper;
    
    @Autowired
    private MarketingCenterMapper marketingCenterMapper;
    
    @Autowired
    private SchedulePlanTaskMapper schedulePlanTaskMapper;
    
    @Autowired
    private ProductPrincipalMapper productPrincipalMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TestingMethodMapper testingMethodMapper;
    
    @Autowired
    private ReceivedSampleMapper receivedSampleMapper;
    
    @Autowired
    private TestingSampleMapper testingSampleMappr;
    
    @Autowired
    private TechnicalAnalysisTaskMapper technicalAnalysisTaskMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper biologyDivisionFastqDataMapper;
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private OrderSampleDetailMapper orderSampleDetailMapper;
    
    @Autowired
    private TestingTaskMapper testingTaskMapper;
    
    @Autowired
    private TechnicalAnalyTestingTaskMapper technicalAnalyTestingTaskMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(TechnicalService.class);
    
    @Override
    public String createTechnicalTask(TechnicalCreateEvent event)
    {
        TestingSchedule testingSchedule;
        Order order;
        Product product;
        Contract contract = null;
        Customer customer;
        MarketingCenter marketingCenter = null;
        List<UserAchive> userAchives = new ArrayList<UserAchive>();
        List<User> productTechPrincipals = new ArrayList<User>();
        String shouldReportTime;
        String productPrincipalName = "";
        String productPrincipalId = "";
        TestingMethod method;
        ReceivedSample receivedSample;
        TestingSample testingSample;
        OrderSampleDetail sampleDetail;
        TestingScheduleActive searcher = new TestingScheduleActive();
        searcher.setTaskId(event.getBiologyTaskId());
        logger.info("taskId:::" + event.getBiologyTaskId());
        List<TestingScheduleActive> actives = testingScheduleActiveMapper.selectBySearcher(searcher);
        if (Collections3.isNotEmpty(actives) && actives.size() == 1)
        {
            TestingScheduleActive scheduleActive = Collections3.getFirst(actives);
            testingSchedule = testingScheduleMapper.selectByPrimaryKey(scheduleActive.getScheduleId());
            order = orderMapper.selectByPrimaryKey(testingSchedule.getOrderId());
            product = productMapper.selectByPrimaryKey(testingSchedule.getProductId());
            contract = contractMapper.selectByPrimaryKey(order.getContractId());
            customer = customerMapper.selectByPrimaryKey(order.getOwnerId());
            if (null != contract)
            {
                marketingCenter = marketingCenterMapper.selectByPrimaryKey(contract.getMarketingCenter().toString());
            }
            
            Date date = DateUtils.addDays(order.getSubmitTime(), product.getTestingDuration());
            shouldReportTime = formatter.format(date);
            List<ProductPrincipal> principals = productPrincipalMapper.getByProduct(product.getId());
            for (ProductPrincipal principal : principals)
            {
                User user = userMapper.selectByPrimaryKey(principal.getPrincipalId());
                productTechPrincipals.add(user);
                userAchives.add(user.getUserAchive());
            }
            User user = userMapper.selectByPrimaryKey(product.getProductDuty());
            if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(user.getUserAchive()))
            {
                productPrincipalName = user.getUserAchive().getName();
                productPrincipalId = user.getId();
            }
            
            method = testingMethodMapper.selectByPrimaryKey(testingSchedule.getMethodId());
            testingSample = testingSampleMappr.selectByPrimaryKey(testingSchedule.getSampleId());
            receivedSample = receivedSampleMapper.selectByPrimaryKey(testingSample.getOriginalSampleId());
            sampleDetail = orderSampleDetailMapper.selectBySampleCode(receivedSample.getSampleCode());
            String lastNode = testingSchedule.getScheduleNodes().split("\\/")[testingSchedule.getScheduleNodes().split("\\/").length - 1];
            if (1 == order.getOrderType() && ("TECHNICAL-ANALYSIS".equals(lastNode) || "TECHNICAL-ANALY".equals(lastNode)))
            {
                logger.info("goto new technical task create.");
                // 如果是遗传，到新的数据分析
                // 处理旧数据的实验节点，到新的数据分析，处理项目监控的节点
                updateOldSchedule(testingSchedule);
                TechnicalAnalysisTask technicalAnalysiTask = new TechnicalAnalysisTask();
                technicalAnalysiTask.setName("新技术分析");
                technicalAnalysiTask.setSemantic("TECHNICAL-ANALYSIS");
                technicalAnalysiTask.setStartTime(new Date());
                technicalAnalysiTask.setStatus(1);
                technicalAnalysiTask.setResubmit(0);
                technicalAnalysiTask.setResubmitCount(0);
                technicalAnalysiTask.setOrderMarketingCenter("临床遗传");
                
                technicalAnalysiTask.setOrderCustomerName(customer.getRealName());
                technicalAnalysiTask.setOrderCustomerAssist(order.getDoctorAssist());
                technicalAnalysiTask.setOrderCustomerCompany(customer.getCompany().getName());
                technicalAnalysiTask.setOrderSalesmanName(order.getCreatorName());
                technicalAnalysiTask.setProductName(product.getName());
                technicalAnalysiTask.setOrderCode(order.getCode());
                technicalAnalysiTask.setOrderId(order.getId());
                technicalAnalysiTask.setOrderSubmitTime(order.getSubmitTime());
                if (null != contract)
                {
                    technicalAnalysiTask.setContractMarketingCenter(marketingCenter.getName());
                    technicalAnalysiTask.setOrderContractCode(contract.getCode());
                }
                
                technicalAnalysiTask.setProductCode(product.getCode());
                technicalAnalysiTask.setFamilyRelation(sampleDetail.getFamilyRelation());
                technicalAnalysiTask.setPlannedFinishDate(setTaskPlanFinishDate(testingSchedule));
                technicalAnalysiTask.setProductTechPrincipals(Collections3.extractToString(userAchives, "name", "、"));
                technicalAnalysiTask.setProductTechPrincipalsIds(Collections3.extractToString(productTechPrincipals, "id", ","));
                technicalAnalysiTask.setProductPrincipal(productPrincipalName);
                technicalAnalysiTask.setProductPrincipalId(productPrincipalId);
                technicalAnalysiTask.setProductReportDeadline(shouldReportTime);
                technicalAnalysiTask.setTestingMethodName(method.getName());
                technicalAnalysiTask.setReceivedSampleType(receivedSample.getTypeName());
                technicalAnalysiTask.setReceivedSampleCode(receivedSample.getSampleCode());
                technicalAnalysiTask.setReceivedSampleName(receivedSample.getSampleName());
                technicalAnalysiTask.setReceivedSampleTypeId(receivedSample.getTypeId());
                technicalAnalysiTask.setReceivedSamplePurpose(null == receivedSample.getPurpose() ? "" : receivedSample.getPurpose().toString());
                technicalAnalysiTask.setReceivedSampleSamplingTime(formatter.format(receivedSample.getSamplingDate()));
                technicalAnalysiTask.setReceivedSampleSamplingBtype(receivedSample.getBusinessType());
                technicalAnalysiTask.setTestingAnalyDataCode(event.getDataCode());
                technicalAnalysiTask.setTestingLaneCode(event.getLanCode());
                technicalAnalysiTask.setTestingSequencingCode(event.getSequencingCode());
                technicalAnalysiTask.setFamilyGroupId(event.getFamilyId());
                if (StringUtils.isEmpty(event.getFamilyId()))
                {
                    // 如果是空，说明是单利样本任务，familygroupid就是divisonid
                    BiologyDivisionFastqData division = biologyDivisionFastqDataMapper.selectByDataCode(event.getDataCode());
                    technicalAnalysiTask.setIfFamilyTask(0);
                    technicalAnalysiTask.setFamilyGroupId(division.getId());
                    
                }
                else
                {
                    technicalAnalysiTask.setIfFamilyTask(1);
                }
                technicalAnalysisTaskMapper.insertSelective(technicalAnalysiTask);
                
                return technicalAnalysiTask.getId();
                
            }
            else
            {
                // 走老的技术分析
                logger.info("goto old technical task create.");
                TestingTask testingTask = new TestingTask();
                testingTask.setName("技术分析");
                testingTask.setSemantic("TECHNICAL-ANALY");
                testingTask.setStartTime(new Date());
                testingTask.setStatus(1);
                testingTask.setResubmit(false);
                testingTask.setResubmitCount(0);
                if (2 == order.getOrderType())
                {
                    testingTask.setOrderMarketingCenter("临床肿瘤");
                }
                else if (3 == order.getOrderType())
                {
                    testingTask.setOrderMarketingCenter("健康筛查");
                }
                
                testingTask.setOrderCustomerName(customer.getRealName());
                testingTask.setOrderCustomerAssist(order.getDoctorAssist());
                testingTask.setOrderCustomerCompany(customer.getCompany().getName());
                testingTask.setOrderSalesmanName(order.getCreatorName());
                testingTask.setProductName(product.getName());
                testingTask.setOrderCode(order.getCode());
                testingTask.setOrderSubmitTime(order.getSubmitTime());
                if (null != contract)
                {
                    testingTask.setContractMarketingCenter(marketingCenter.getName());
                    testingTask.setOrderContractCode(contract.getCode());
                }
                
                testingTask.setProductCode(product.getCode());
                testingTask.setFamilyRelation(sampleDetail.getFamilyRelation());
                testingTask.setPlannedFinishDate(setTaskPlanFinishDate(testingSchedule));
                testingTask.setProductTechPrincipals(Collections3.extractToString(userAchives, "name", "、"));
                
                testingTask.setProductReportDeadline(shouldReportTime);
                testingTask.setTestingMethodName(method.getName());
                testingTask.setReceivedSampleType(receivedSample.getTypeName());
                testingTask.setReceivedSampleCode(receivedSample.getSampleCode());
                testingTask.setReceivedSampleName(receivedSample.getSampleName());
                testingTask.setReceivedSampleTypeId(receivedSample.getTypeId());
                testingTask.setInputSampleId(testingSample.getId());//投入样本编号--这里是为了通过最开始的投入样本获取原始样本
                testingTask.setReceivedSamplePurpose(null == receivedSample.getPurpose() ? "" : receivedSample.getPurpose().toString());
                testingTask.setReceivedSampleSamplingTime(formatter.format(receivedSample.getSamplingDate()));
                testingTask.setTestingAnalyDataCode(event.getDataCode());
                testingTask.setTestingLaneCode(event.getSequencingCode());
                
                // 设置加急
                testingTask.setIfUrgent(order.getIfUrgent());
                testingTask.setUrgentUpdateTime(order.getUpdateTime());
                
                testingTaskMapper.insertSelective(testingTask);
                
                TechnicalAnalyTestingTask tatt = new TechnicalAnalyTestingTask();
                tatt.setTaskId(testingTask.getId());
                tatt.setSequencingCode(event.getSequencingCode());
                technicalAnalyTestingTaskMapper.insertSelective(tatt);
                return testingTask.getId();
            }
            
        }
        else
        {
            throw new IllegalStateException();
        }
        
    }
    
    /** 处理旧数据的实验节点，到新的数据分析，处理项目监控的节点 */
    private void updateOldSchedule(TestingSchedule testingSchedule)
    {
        // TODO Auto-generated method stub
        
    }
    
    private Date setTaskPlanFinishDate(TestingSchedule testingSchedule)
    {
        //        SchedulePlanTask searcher = new SchedulePlanTask();
        //        searcher.setOrderId(testingSchedule.getOrderId());
        //        searcher.setProductId(testingSchedule.getProductId());
        //        searcher.setTestingMethodId(testingSchedule.getMethodId());
        //        searcher.setSampleId(testingSchedule.getSampleId());
        //        searcher.setTaskSemantic("TECHNICAL-ANALY");
        //        
        //        List<SchedulePlanTask> planTasks = schedulePlanTaskMapper.selectBySearcher(searcher);
        //        
        //        Date date = planTasks.get(0).getPlannedFinishDate();
        return new Date();
        
    }
    
}
