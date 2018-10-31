package com.todaysoft.lims.testing.abnormal.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.impl.TestingSampleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.abnormal.dao.AbnormalTaskSearcher;
import com.todaysoft.lims.testing.abnormal.model.AbnormalHistoryModel;
import com.todaysoft.lims.testing.abnormal.model.AbnormalSolveSheduleRecord;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTask;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskDetails;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskRelatedSchedule;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveStrategy;
import com.todaysoft.lims.testing.abnormal.model.request.AbnormalSolveRecordSearcher;
import com.todaysoft.lims.testing.abnormal.service.IAbnormalService;
import com.todaysoft.lims.testing.abnormal.service.core.AbnormalSolverFactory;
import com.todaysoft.lims.testing.abnormal.service.core.IAbnormalSolver;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingScheduleRequest;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.request.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerTaskVariables;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class AbnormalService implements IAbnormalService, IEntityWrapper<TestingAbnormalTaskView,TestingAbnormalTaskView>
{
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private AbnormalSolverFactory solverFactory;
    
    @Autowired
    private ITestingScheduleService scheduleService;

    @Autowired
    private ITestingSampleService testingSampleService;

    
    @Override
    public Pagination<AbnormalTask> paging(AbnormalTaskSearcher searcher, int pageNo, int pageSize)
    {
        Pagination<TestingAbnormalTaskView> pagination = baseDaoSupport.find(searcher.toViewQuery(), pageNo, pageSize,TestingAbnormalTaskView.class);
        Pagination<AbnormalTask> pagination2 =  new Pagination<>();
        wrapPagination(pagination,pagination2);
        return pagination2;
    }
    
    @Override
    public AbnormalTaskDetails getDetails(String id,String semantic)
    {
        AbnormalTaskDetails details = new AbnormalTaskDetails();
        details.setId(id);
        List<AbnormalTaskRelatedSchedule> schedules = new ArrayList<AbnormalTaskRelatedSchedule>();
        details.setSchedules(schedules);
        List<AbnormalTaskSolveStrategy> strategies = new ArrayList<AbnormalTaskSolveStrategy>();
        details.setStrategies(strategies);
        if(StringUtils.isNotEmpty(semantic) && TaskSemantic.BIOLOGY_ANNOTATION.equals(semantic))
        {
            //生信注释异常处理显示
            details.setName("生信注释");
            details.setSemantic(TaskSemantic.BIOLOGY_ANNOTATION);
            TestingAbnormalTaskView view = baseDaoSupport.get(TestingAbnormalTaskView.class,id);
            if(null != view)
            {
                details.setSubmitTime(view.getEndTime());
                details.setReceivedSampleName(view.getSampleName());
                details.setReceivedSampleCode(view.getSampleCode());
                details.setRemark(view.getRemark());
                TestingSample testingSample = testingSampleService.getTestingSampleByCode(view.getSampleCode());
                if(null != testingSample)
                {
                    details.setReceivedSampleType(testingSample.getSampleTypeName());
                }
            }

            String hql = " FROM BiologyAnnotationSheet sheet WHERE sheet.taskId='"+id+"' order by sheet.createTime desc ";
            List<BiologyAnnotationSheet> sheetList = baseDaoSupport.find(BiologyAnnotationSheet.class,hql);
            if(Collections3.isNotEmpty(sheetList))
            {
                details.setSheetCode(Collections3.getFirst(sheetList).getCode());
//              details.setTesterName(Collections3.getFirst(sheetList).getTesterName());
                List<BiologyAnnotationFailOperate> resultList = getBiologyAnnotationFailOperateByTaskId(Collections3.getFirst(sheetList).getTaskId());
                if(Collections3.isNotEmpty(resultList))
                {
                    details.setTesterName(resultList.get(0).getOperatorName());//异常任务处理页面 实验员显示为异常上报处理人
                }
            }
        } else if (StringUtils.isNotEmpty(semantic) && TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(semantic)) {
            //新技术分析异常处理显示
            details.setName("新技术分析");
            details.setSemantic(TaskSemantic.TECHNICAL_ANALYSIS_TASK);
            TestingAbnormalTaskView view = baseDaoSupport.get(TestingAbnormalTaskView.class, id);
            if (null != view)
            {
                details.setSubmitTime(view.getEndTime());
                details.setReceivedSampleName(view.getSampleName());
                details.setReceivedSampleCode(view.getSampleCode());
                details.setRemark(view.getRemark());
                TestingSample testingSample = testingSampleService.getTestingSampleByCode(view.getSampleCode());
                if (null != testingSample)
                {
                    details.setReceivedSampleType(testingSample.getSampleTypeName());
                }
                TechnicalAnalysisTask analysisTask = baseDaoSupport.get(TechnicalAnalysisTask.class, id);

                String hql = " FROM TechnicalSheet sheet WHERE sheet.taskId='"+analysisTask.getFamilyGroupId()+"' order by sheet.createTime desc ";
                List<TechnicalSheet> sheetList = baseDaoSupport.find(TechnicalSheet.class,hql);
                if(Collections3.isNotEmpty(sheetList))
                {
                    details.setSheetCode(Collections3.getFirst(sheetList).getCode());
                    details.setTesterName(Collections3.getFirst(sheetList).getTesterName());
                }
            }
            else
            {
                TechnicalAnalysisTask analysisTask = baseDaoSupport.get(TechnicalAnalysisTask.class, id);
                if(StringUtils.isNotEmpty(analysisTask.getReceivedSampleCode()))
                {
                    details.setReceivedSampleCode(analysisTask.getReceivedSampleCode());
                    details.setReceivedSampleType(analysisTask.getReceivedSampleType());
                    details.setReceivedSampleName(analysisTask.getReceivedSampleName());
                }

                String hql = " FROM TechnicalSheet sheet WHERE sheet.taskId='"+analysisTask.getFamilyGroupId()+"' order by sheet.createTime desc ";
                List<TechnicalSheet> sheetList = baseDaoSupport.find(TechnicalSheet.class,hql);
                if(Collections3.isNotEmpty(sheetList))
                {
                    details.setSheetCode(Collections3.getFirst(sheetList).getCode());
                    details.setTesterName(Collections3.getFirst(sheetList).getTesterName());
                }
            }
        }else{
            wrapTaskInfo(details,id);
        }
        wrapRelatedSchedule(schedules,strategies,id,semantic);

        List<String> cancerTaskList = Lists.newArrayList();
        cancerTaskList.add(TaskSemantic.DNA_QC);
        cancerTaskList.add(TaskSemantic.CDNA_QC);
        cancerTaskList.add(TaskSemantic.LIBRARY_QC);
        cancerTaskList.add(TaskSemantic.LIBRARY_CAP);
        cancerTaskList.add(TaskSemantic.TECHNICAL_ANALY);
        cancerTaskList.add(TaskSemantic.PCR_NGS);
        cancerTaskList.add(TaskSemantic.PCR_ONE);
        cancerTaskList.add(TaskSemantic.SANGER_DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.MLPA_DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.PRIMER_DESIGN);
        cancerTaskList.add(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
        cancerTaskList.add(TaskSemantic.SANGER_PCR_ONE);
        cancerTaskList.add(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.SANGER_DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.DT_DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.QPCR);
        cancerTaskList.add(TaskSemantic.LONG_QC);
        cancerTaskList.add(TaskSemantic.MULTIPCR_QC);
        cancerTaskList.add(TaskSemantic.FLUO_ANALYSE);
        cancerTaskList.add(TaskSemantic.DATA_ANALYSIS);
        cancerTaskList.add(TaskSemantic.BIOLOGY_ANNOTATION);
        cancerTaskList.add(TaskSemantic.TECHNICAL_ANALYSIS_TASK);
        if (cancerTaskList.contains(semantic))
        {
            // 添加重新送样
            AbnormalTaskSolveStrategy strategy = new AbnormalTaskSolveStrategy();
            strategy.setName("实验取消");
            strategy.setSemantic(TaskSemantic.EXPERIMENT_CANCER);
            strategies.add(strategy);
        }
        List<String> noReSampleTaskList = Lists.newArrayList();
        noReSampleTaskList.add(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
        noReSampleTaskList.add(TaskSemantic.PRIMER_DESIGN);
        // 添加重新送样
        if (!noReSampleTaskList.contains(semantic))
        {
            AbnormalTaskSolveStrategy strategy = new AbnormalTaskSolveStrategy();
            strategy.setName("重新送样");
            strategy.setSemantic(TaskSemantic.RESAMPLING);
            strategies.add(strategy);
        }
        
        return details;
    }

    private void wrapRelatedSchedule(List<AbnormalTaskRelatedSchedule> schedules, List<AbnormalTaskSolveStrategy> strategies,String id,String semantic) {

        List<TestingSchedule> records = testingScheduleService.getScheduleHistorys(id);
        if (!CollectionUtils.isEmpty(records))
        {
            Order order;
            Product product;
            TestingMethod testingMethod;
            AbnormalTaskRelatedSchedule schedule;
            List<String> scheduleRollbackNodes;
            List<String> schedulesRollbackNodes = new ArrayList<String>();
            Set<String> existsNodes = new HashSet<String>();

            for (TestingSchedule record : records)
            {
                order = baseDaoSupport.get(Order.class, record.getOrderId());
                product = baseDaoSupport.get(Product.class, record.getProductId());
                testingMethod = baseDaoSupport.get(TestingMethod.class, record.getMethodId());

                schedule = new AbnormalTaskRelatedSchedule();
                schedule.setId(record.getId());
                schedule.setOrderCode(order.getCode());
                schedule.setMarketingCenter(null == order.getType() ? null : order.getType().getName());
                schedule.setProductName(product.getName());
                schedule.setTestingMethod(testingMethod.getName());
                //引物设计合成特殊处理，根据投入样本找到原始样本
                TestingSample testingSample = baseDaoSupport.get(TestingSample.class, record.getSampleId());
                if (null != testingSample)
                {
                    schedule.setReceivedSample(testingSample.getReceivedSample());
                }

                TestingtechnicalanalyrecordTempory sangerverifyrecord;
                if ("Sanger验证".equals(testingMethod.getName()) || "PCR-NGS验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = scheduleService.getSangerVerifyRecordById(record.getVerifyKey());
                    if (null != sangerverifyrecord)
                    {
                        schedule.setChrLocation(sangerverifyrecord.getChromosomalLocation());
                        schedule.setGeneSymbol(sangerverifyrecord.getGeneSymbol());
                    }
                }
                if ("QPCR验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = scheduleService.getQPCRVerifyRecordById(record.getVerifyKey());
                    if (null != sangerverifyrecord)
                    {
                        schedule.setChrLocation(sangerverifyrecord.getChromosomalLocation());
                        schedule.setGeneSymbol(sangerverifyrecord.getGeneSymbol());
                    }
                }
                if ("MLPA验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = scheduleService.getMLPAVerifyRecordById(record.getVerifyKey());
                    if (null != sangerverifyrecord)
                    {
                        schedule.setChrLocation(sangerverifyrecord.getChromosomalLocation());
                        schedule.setGeneSymbol(sangerverifyrecord.getGeneSymbol());
                    }
                }
                schedules.add(schedule);

                scheduleRollbackNodes = getRollbackNodes(record.getScheduleNodes(), semantic);

                if (CollectionUtils.isEmpty(scheduleRollbackNodes))
                {
                    continue;
                }

                for (String node : scheduleRollbackNodes)
                {
                    if (existsNodes.contains(node))
                    {
                        continue;
                    }

                    schedulesRollbackNodes.add(node);
                    existsNodes.add(node);
                }
            }

            TaskConfig config;
            AbnormalTaskSolveStrategy strategy;

            for (String node : schedulesRollbackNodes)
            {
                config = bcmadapter.getTaskConfigBySemantic(node);

                if (null == config)
                {
                    continue;
                }

                strategy = new AbnormalTaskSolveStrategy();
                strategy.setName(config.getName());
                strategy.setSemantic(config.getSemantic());
                strategies.add(strategy);
            }
        }
    }

    private void wrapTaskInfo(AbnormalTaskDetails details, String id) {

        TestingTask task = baseDaoSupport.get(TestingTask.class, id);
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, id);

        if (null == task || null == result)
        {
            return;
        }

        details.setName(task.getName());
        details.setSubmitTime(task.getEndTime());
        details.setRemark(result.getRemark());
        details.setSemantic(task.getSemantic());

        if (StringUtils.isNotEmpty(task.getInputSample()))
        {
            TestingSample testingSample = task.getInputSample();
            details.setTestingSampleType(testingSample.getSampleTypeName());
            details.setTestingSampleCode(testingSample.getSampleCode());
            details.setInputSampleId(testingSample.getId());

            if (StringUtils.isNotEmpty(testingSample.getReceivedSample()))
            {
                ReceivedSample receivedSample = testingSample.getReceivedSample();
                details.setReceivedSampleType(receivedSample.getSampleTypeName());
                details.setReceivedSampleName(receivedSample.getSampleName());
                details.setReceivedSampleCode(receivedSample.getSampleCode());
            }
        }
        if ("TECHNICAL-ANALY".equals(task.getSemantic()))//技术分析没有投入样本
            {
                if(StringUtils.isNotEmpty(task.getReceivedSampleCode()))
                {
                    details.setReceivedSampleCode(task.getReceivedSampleCode());
                    details.setReceivedSampleType(task.getReceivedSampleType());
                    details.setReceivedSampleName(task.getReceivedSampleName());
//                    details.setTestingSampleType(null);
//                    details.setTestingSampleCode(null);
                }
            }

        TestingSheet sheet = testingSheetService.getSheetByTestingTask(id);

        if (null != sheet)
        {
            details.setSheetCode(sheet.getCode());
            details.setTesterName(sheet.getTesterName());
        }
    }

    @Override
    @Transactional
    public String solve(AbnormalTaskSolveRequest request, String token, VariableModel model)
    {
        String orderId = "";
        List<String> scheduleIds = Lists.newArrayList();
        String strategy = request.getStrategy();
        
        String taskId = request.getId();
        
        List<TestingTask> list = Lists.newArrayList();
        List<TestingTask> activeTaskList = Lists.newArrayList();
        List<TestingSchedule> scheduleList = testingScheduleService.getScheduleHistorys(taskId);
        if (Collections3.isEmpty(scheduleList))
        {
            throw new IllegalStateException();
        }
        TestingSchedule testingSchedule = scheduleList.get(0);
        
        TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, testingSchedule.getMethodId());
        if (null == testingMethod)
        {
            throw new IllegalStateException();
        }
        List<String> sangerStr = Arrays.asList(TaskSemantic.DNA_EXTRACT, TaskSemantic.EXPERIMENT_CANCER, TaskSemantic.RESAMPLING);
        // sanger检测查出当前流程所有相关的异常任务 （处理策略是 DNA提取 实验取消 重新送样的 然后全部一起解决）
        if (TestingMethod.SANGER_TEST.equals(testingMethod.getSemantic()) && sangerStr.contains(strategy))
        {
            list = testingScheduleService.getSangerTestAbnormalTaskByScheduleId(testingSchedule.getId());
            if (TaskSemantic.RESAMPLING.equals(strategy))
            {
                List<TestingScheduleActive> testingScheduleActiveList = testingScheduleService.getScheduleActives(testingSchedule.getId());
                if (Collections3.isNotEmpty(testingScheduleActiveList))
                {
                    List<TestingTask> tasks = Lists.newArrayList();
                    testingScheduleActiveList.stream().forEach(x -> tasks.add(baseDaoSupport.get(TestingTask.class, x.getTaskId())));
                    list.addAll(tasks);
                }
            }
            
            for (TestingTask task : list)
            {
                if (StringUtils.isEmpty(strategy))
                {
                    throw new IllegalStateException();
                }
                
                IAbnormalSolver solver = solverFactory.getSolver(strategy);
                
                if (null == solver)
                {
                    throw new IllegalStateException();
                }
                
                request.setId(task.getId());
                orderId = solver.solve(request, token, scheduleIds);
                
            }
        }
        else
        {
            if (StringUtils.isEmpty(strategy))
            {
                throw new IllegalStateException();
            }
            
            IAbnormalSolver solver = solverFactory.getSolver(strategy);
            
            if (null == solver)
            {
                throw new IllegalStateException();
            }
            
            orderId = solver.solve(request, token, scheduleIds);
        }
        model.setScheduleIds(StringUtils.join(scheduleIds, ","));
        return orderId;
        
    }
    
    private List<String> getRollbackNodes(String scheduleNodes, String taskSemantic)
    {
        if (StringUtils.isEmpty(scheduleNodes))
        {
            return Collections.emptyList();
        }
        
        List<String> nodes = new ArrayList<String>();
        
        List<String> nodeList = getNodeListBySemantic(scheduleNodes, taskSemantic);
        
        if (Collections3.isNotEmpty(nodeList))
        {
            nodes.addAll(nodeList);
        }
        else
        {
            
            String[] array = scheduleNodes.split("\\/");
            
            Set<String> ignores = new HashSet<String>();
            ignores.add(TaskSemantic.DNA_QC);
            ignores.add(TaskSemantic.CDNA_QC);
            ignores.add(TaskSemantic.LIBRARY_QC);
            ignores.add(TaskSemantic.RQT);
            ignores.add(TaskSemantic.POOLING);
            ignores.add(TaskSemantic.QT);
            ignores.add(TaskSemantic.NGS_SEQ);
            //            ignores.add(TaskSemantic.TECHNICAL_ANALY);
            ignores.add(TaskSemantic.MULTIPCR_QC);
            ignores.add(TaskSemantic.LONG_CRE);
            ignores.add(TaskSemantic.LONG_QC);
            ignores.add(TaskSemantic.FLUO_ANALYSE);
            ignores.add(TaskSemantic.BIOLOGY_DIVISION);
            
            for (String node : array)
            {
                if (node.equals(taskSemantic))
                {
                    if (!ignores.contains(node))
                    {
                        nodes.add(node);
                    }
                    
                    break;
                }
                else
                {
                    if (!ignores.contains(node))
                    {
                        nodes.add(node);
                    }
                }
            }
        }
        if (TaskSemantic.TECHNICAL_ANALY.equals(taskSemantic) || TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(taskSemantic))
        {
            if (nodes.contains(TaskSemantic.BIOLOGY_ANNOTATION)) {
                nodes.remove(TaskSemantic.BIOLOGY_ANNOTATION);
            }
            if (nodes.contains(TaskSemantic.BIOLOGY_ANALY))
            {
                nodes.remove(TaskSemantic.BIOLOGY_ANALY);
            }
        }

        if(TaskSemantic.BIOLOGY_ANNOTATION.equals(taskSemantic)){
            if (nodes.contains(TaskSemantic.TECHNICAL_ANALY)) {
                nodes.remove(TaskSemantic.TECHNICAL_ANALY);
            }
            if (nodes.contains(TaskSemantic.BIOLOGY_ANALY))
            {
                nodes.remove(TaskSemantic.BIOLOGY_ANALY);
            }
            if (nodes.contains(TaskSemantic.PCR_NGS_DATA_ANALYSIS))
            {
                nodes.remove(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
            }
            nodes.add(TaskSemantic.BIOLOGY_ANNOTATION);
        }
        if(TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(taskSemantic)){
            if (nodes.contains(TaskSemantic.TECHNICAL_ANALY)) {
                nodes.remove(TaskSemantic.TECHNICAL_ANALY);
            }
            if (nodes.contains(TaskSemantic.BIOLOGY_ANALY))
            {
                nodes.remove(TaskSemantic.BIOLOGY_ANALY);
            }
        }

        
        return nodes;
    }
    
    public void wrapPagination(Pagination<TestingAbnormalTaskView> pagination,Pagination<AbnormalTask> pagination2)
    {
        BeanUtils.copyProperties(pagination,pagination2);
        List<AbnormalTask> resultList = Lists.newArrayList();
        List<TestingAbnormalTaskView> viewList = pagination.getRecords();
        for(TestingAbnormalTaskView view:viewList)
        {
            AbnormalTask model = new AbnormalTask();
            model.setId(view.getId());
            model.setName(view.getName());
            model.setReceivedSampleName(view.getSampleName());
            model.setReceivedSampleCode(view.getSampleCode());
            model.setRemark(view.getRemark());
            model.setSubmitTime(view.getEndTime());
            model.setOrderCode(view.getOrderCode());
            model.setProducts(view.getProductName());
            model.setTestingMethods(view.getMethodName());
            model.setMarketingCenter(view.getOrderType());
            model.setSemantic(view.getSemantic());
            if (TaskSemantic.BIOLOGY_ANNOTATION.equals(view.getSemantic()) || TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(view.getSemantic()))//生信注释的直接去
            {
                if (0 == view.getResubmitCount().intValue()) {
                    model.setResubmitCount(1);
                } else {
                    model.setResubmitCount(view.getResubmitCount());
                }

            }else{
                int count = 0;
                //异常次数 ww
                List<TestingScheduleHistory> shedulesHistorys =
                        baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + view.getId() + "'");
                if(Collections3.isNotEmpty(shedulesHistorys))
                {
                    NamedQueryer queryer =
                            NamedQueryer.builder()
                                    .hql("SELECT COUNT (*) FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId "
                                            + " AND EXISTS(select tt.id from TestingTask tt where tt.id = tsh.taskId and tt.semantic = :semantic)")
                                    .names(Arrays.asList("scheduleId", "semantic"))
                                    .values(Arrays.asList(shedulesHistorys.get(0).getScheduleId(), view.getSemantic()))
                                    .build();
                    count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
                }
                model.setResubmitCount(count);
            }
            resultList.add(model);
        }
        pagination2.setRecords(resultList);
    }
    
    @Override
    public Pagination<AbnormalSolveRecordSearcher> history(AbnormalSolveRecordSearcher searcher)
    {
        Pagination<AbnormalSolveRecordSearcher> paging = new Pagination<AbnormalSolveRecordSearcher>();
        List<AbnormalSolveRecordSearcher> list = new ArrayList<AbnormalSolveRecordSearcher>();
        paging.setRecords(list);
        if (searcher.getTestingTask()!=null){
            searcher.setSampleCode(searcher.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
        }
        Pagination<AbnormalRecord> res = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), AbnormalRecord.class);
        paging.setPageNo(res.getPageNo());
        paging.setPageSize(res.getPageSize());
        paging.setTotalCount(res.getTotalCount());
        for (AbnormalRecord ar : res.getRecords())
        {
            AbnormalSolveRecordSearcher record = new AbnormalSolveRecordSearcher();
            BeanUtils.copyProperties(ar, record);
            paging.getRecords().add(record);
            if (!ar.getStrategy().equals("BIOLOGY-ANNOTATION")&&!ar.getStrategy().equals("TECHNICAL-ANALYSIS")){
                TestingTask testingTask = testingTaskService.get(record.getTaskId());
                if ("1".equals(testingTask.getReceivedSamplePurpose()))
                {
                    testingTask.setProductName(testingTask.getVerifyChromosomePosition());
                }
                record.setTestingTask(testingTask);
            }

            if(ar.getStrategy().equals("BIOLOGY-ANNOTATION")){
                BiologyAnnotationTask biologyAnnotationTask = testingTaskService.getBio(record.getTaskId());
                TestingTask testingTask = new TestingTask();
                testingTask.setName("生信注释");
                testingTask.setProductName(biologyAnnotationTask.getProductName());
                TestingSample testingSample = new TestingSample();
                ReceivedSample receivedSample = new ReceivedSample();
                receivedSample.setSampleCode(biologyAnnotationTask.getSampleCode());
                receivedSample.setSampleName(testingTaskService.getSample(biologyAnnotationTask.getSampleCode()).getSampleName());
                testingSample.setReceivedSample(receivedSample);
                testingTask.setInputSample(testingSample);
                record.setTestingTask(testingTask);
            }

            if(ar.getStrategy().equals("TECHNICAL-ANALYSIS")){
                TechnicalAnalysisTask technicalAnalysisTask = testingTaskService.getTech(record.getTaskId());
                TestingTask testingTask = new TestingTask();
                testingTask.setName("新技术分析");
                testingTask.setProductName(testingTaskService.getPro(technicalAnalysisTask.getProductCode()).getName());
                TestingSample testingSample = new TestingSample();
                ReceivedSample receivedSample = new ReceivedSample();
                receivedSample.setSampleCode(technicalAnalysisTask.getReceivedSampleCode());
                receivedSample.setSampleName(testingTaskService.getSample(technicalAnalysisTask.getReceivedSampleCode()).getSampleName());
                testingSample.setReceivedSample(receivedSample);
                testingTask.setInputSample(testingSample);
                record.setTestingTask(testingTask);
            }
            List<TestingScheduleHistory> shedulesHistorys =
                baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + record.getTaskId() + "'");
            List<AbnormalSolveSheduleRecord> sheduleList = new ArrayList<>();
            for (TestingScheduleHistory sheduleHistory : shedulesHistorys)
            {
                TestingScheduleRequest shedule = testingScheduleService.getScheduleById(sheduleHistory.getScheduleId());
                AbnormalSolveSheduleRecord solveShedule = new AbnormalSolveSheduleRecord();
                Order order = baseDaoSupport.get(Order.class, shedule.getOrderId());
                solveShedule.setOrderCode(order.getCode());
                Product product = baseDaoSupport.get(Product.class, shedule.getProductId());
                solveShedule.setProductName(product.getName());
                TestingMethod method = baseDaoSupport.get(TestingMethod.class, shedule.getMethodId());
                solveShedule.setMethodName(method.getName());
                solveShedule.setVerifyKey(shedule.getVerifyKey());
                solveShedule.setActiveTask(shedule.getActiveTask());
                solveShedule.setProccessState(shedule.getProccessState());
                sheduleList.add(solveShedule);
            }
            record.setSheduleList(sheduleList);
        }
        return paging;
    }
    
    @Override
    public AbnormalSolveRecordSearcher history(String id)
    {
        AbnormalSolveRecord bean = baseDaoSupport.get(AbnormalSolveRecord.class, id);
        
        AbnormalSolveRecordSearcher record = new AbnormalSolveRecordSearcher();
        BeanUtils.copyProperties(bean, record);

        List<AbnormalRecord> abnormalRecord = baseDaoSupport.find(AbnormalRecord.class, "from AbnormalRecord t where t.taskId='"+bean.getTaskId() + "'");


        if(!abnormalRecord.get(0).getStrategy().equals("BIOLOGY-ANNOTATION")&&!abnormalRecord.get(0).getStrategy().equals("TECHNICAL-ANALYSIS")){
            TestingSheet sheet = testingSheetService.getSheetByTestingTask(bean.getTaskId());
            if (sheet!=null){
                record.setSheetCode(sheet.getCode());
            }
            TestingTask testingTask = testingTaskService.get(record.getTaskId());
            record.setTestingTask(testingTask);
        }
        else if (abnormalRecord.get(0).getStrategy().equals("BIOLOGY-ANNOTATION")){
            BiologyAnnotationTask biologyAnnotationTask = testingTaskService.getBio(record.getTaskId());
            String hql = " FROM BiologyAnnotationSheet sheet WHERE sheet.taskId='"+biologyAnnotationTask.getId()+"' order by sheet.createTime desc ";
            List<BiologyAnnotationSheet> sheetList = baseDaoSupport.find(BiologyAnnotationSheet.class,hql);
            if(Collections3.isNotEmpty(sheetList)){
                record.setSheetCode(Collections3.getFirst(sheetList).getCode());
            }

            TestingTask testingTask = new TestingTask();
            testingTask.setName("生信注释");
            List<TestingScheduleHistory> testingScheduleHistorys =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + record.getTaskId() + "'");

            TestingScheduleHistory testingScheduleHistory = testingScheduleHistorys.get(0);

            List<TestingScheduleHistory> testingScheduleHistorys1 =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + testingScheduleHistory.getScheduleId() + "'"+"order by t.timestamp asc");

            int i = 0;
            for (TestingScheduleHistory testingScheduleHistory1:testingScheduleHistorys1){

                if (testingScheduleHistory1.getTaskRefer()!=null&&testingScheduleHistory1.getTaskRefer().equals("NGS_SEQUECING_TASK")){
                    break;
                }
                i++;
            }
            TestingScheduleHistory te = testingScheduleHistorys1.get(i-1);
            TestingTask test = baseDaoSupport.get(TestingTask.class, te.getTaskId());

            TestingSample testingSample = test.getInputSample();

            ReceivedSample receivedSample = new ReceivedSample();
            receivedSample.setSampleCode(biologyAnnotationTask.getSampleCode());
            receivedSample.setSampleName(testingTaskService.getSample(biologyAnnotationTask.getSampleCode()).getSampleName());
            receivedSample.setSampleTypeName(testingTaskService.getSample(biologyAnnotationTask.getSampleCode()).getSampleTypeName());
            testingSample.setReceivedSample(receivedSample);
            testingTask.setInputSample(testingSample);
            record.setTestingTask(testingTask);
//           BiologyAnnotationSheet sheet = testingSheetService.getBioSheet(bean.getTaskId());
        }
        else if (abnormalRecord.get(0).getStrategy().equals("TECHNICAL-ANALYSIS")){
            TechnicalAnalysisTask technicalAnalysisTask = testingTaskService.getTech(record.getTaskId());
            TechnicalSheet technicalSheet = testingTaskService.getSheetByTask(technicalAnalysisTask.getFamilyGroupId());
            if (technicalSheet!=null){
                record.setSheetCode(technicalSheet.getCode());
            }
            TestingTask testingTask = new TestingTask();
            testingTask.setName("新技术分析");
            List<TestingScheduleHistory> testingScheduleHistorys =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + record.getTaskId() + "'");

            TestingScheduleHistory testingScheduleHistory = testingScheduleHistorys.get(0);

            List<TestingScheduleHistory> testingScheduleHistorys1 =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + testingScheduleHistory.getScheduleId() + "'"+"order by t.timestamp asc");

            int i = 0;
            for (TestingScheduleHistory testingScheduleHistory1:testingScheduleHistorys1){

                if (testingScheduleHistory1.getTaskRefer()!=null&&testingScheduleHistory1.getTaskRefer().equals("NGS_SEQUECING_TASK")){
                    break;
                }
                i++;
            }
            TestingScheduleHistory te = testingScheduleHistorys1.get(i-1);
            TestingTask test = baseDaoSupport.get(TestingTask.class, te.getTaskId());

           TestingSample testingSample = test.getInputSample();


            ReceivedSample receivedSample = new ReceivedSample();
            receivedSample.setSampleCode(technicalAnalysisTask.getReceivedSampleCode());
            receivedSample.setSampleName(testingTaskService.getSample(technicalAnalysisTask.getReceivedSampleCode()).getSampleName());
            receivedSample.setSampleTypeName(testingTaskService.getSample(technicalAnalysisTask.getReceivedSampleCode()).getSampleTypeName());
            testingSample.setReceivedSample(receivedSample);
            testingTask.setInputSample(testingSample);
            record.setTestingTask(testingTask);



        }



        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(bean.getStrategy());
        if (null == taskConfig)
        {
            if (TaskSemantic.EXPERIMENT_CANCER.equals(bean.getStrategy()))
            {
                record.setStrategyName("实验取消");
            }
            else if (TaskSemantic.RESAMPLING.equals(bean.getStrategy()))
            {
                record.setStrategyName("重新送样");
            }
        }
        else
        {
            record.setStrategyName(taskConfig.getName());
        }


        List<TestingScheduleHistory> shedulesHistorys =
            baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.taskId='" + record.getTaskId() + "'");
        List<AbnormalSolveSheduleRecord> sheduleList = new ArrayList<>();
        for (TestingScheduleHistory sheduleHistory : shedulesHistorys)
        {
            TestingScheduleRequest shedule = testingScheduleService.getScheduleById(sheduleHistory.getScheduleId());
            AbnormalSolveSheduleRecord solveShedule = new AbnormalSolveSheduleRecord();
            Order order = baseDaoSupport.get(Order.class, shedule.getOrderId());
            solveShedule.setOrderCode(order.getCode());
            Product product = baseDaoSupport.get(Product.class, shedule.getProductId());
            solveShedule.setProductName(product.getName());
            TestingMethod method = baseDaoSupport.get(TestingMethod.class, shedule.getMethodId());
            solveShedule.setMethodName(method.getName());
            sheduleList.add(solveShedule);
        }
        record.setSheduleList(sheduleList);
        
        return record;
    }
    
    private List<String> getNodeListBySemantic(String scheduleNodes, String semantic)
    {
        //验证流程上报现已经加上重新提取DNA或CNDA，需根据第一个节点判断
        List<String> firstNodes = Arrays.asList(TaskSemantic.DNA_EXTRACT, TaskSemantic.CDNA_EXTRACT);
        List<String> list = Lists.newArrayList();
        if (TaskSemantic.PRIMER_DESIGN.equals(semantic))
        {
            list.add(semantic);
        }
        else if (TaskSemantic.PCR_NGS_PRIMER_DESIGN.equals(semantic))
        {
            list.add(semantic);
        }
        else if (TaskSemantic.PCR_NGS_DATA_ANALYSIS.equals(semantic))
        {
            if (firstNodes.contains(scheduleNodes.split("\\/")[0]))
            {
                list.add(scheduleNodes.split("\\/")[0]);
            }
            list.add(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
            list.add(TaskSemantic.PCR_NGS);
            list.add(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        }
        else if (TaskSemantic.SANGER_PCR_ONE.equals(semantic))
        {
            list.add(TaskSemantic.DNA_EXTRACT);
            list.add(TaskSemantic.SANGER_PCR_ONE);
        }
        else if (TaskSemantic.SANGER_DATA_ANALYSIS.equals(semantic))
        {
            list.add(TaskSemantic.DNA_EXTRACT);
            list.add(TaskSemantic.SANGER_PCR_ONE);
            list.add(TaskSemantic.SANGER_PCR_TWO);
            list.add(TaskSemantic.SANGER_PCR_TWO_REVERSE);
        }
        
        else if (TaskSemantic.DNA_QC.equals(semantic))
        {
            if (firstNodes.contains(scheduleNodes.split("\\/")[0]))
            {
                list.add(scheduleNodes.split("\\/")[0]);
            }
            list.add(TaskSemantic.DNA_QC);

            
        }
        
        return list;
    }
    
    public SecondPcrSangerTaskVariables getPcrTwoTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrSangerTaskVariables.class);
    }
    
    @Override
    public AbnormalSolveRecord getByTaskId(String taskId)
    {
        List<AbnormalSolveRecord> abnormalSolveRecords =
            baseDaoSupport.find(AbnormalSolveRecord.class, "from AbnormalSolveRecord t where t.taskId='" + taskId + "'");
        return Collections3.getFirst(abnormalSolveRecords);
    }

    @Override
    public List<AbnormalHistoryModel> getAbnormalHistoryByTaskId(String taskId,String semantic)
    {
        List<AbnormalHistoryModel> modelList = Lists.newArrayList();
        if(TaskSemantic.BIOLOGY_ANNOTATION.equals(semantic))
        {
            String hql = "FROM TestingScheduleHistory t WHERE t.taskId = :taskId order by t.timestamp desc";
            List<TestingScheduleHistory> records = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"taskId"}, new String[]{taskId});
            List<BiologyAnnotationFailOperate> resultList = getBiologyAnnotationFailOperateByTaskId(taskId);
            if(Collections3.isNotEmpty(resultList))
            {
                for(BiologyAnnotationFailOperate bio:resultList)
                {
                    AbnormalHistoryModel model = new AbnormalHistoryModel();
                    model.setTaskName("生信注释");
                    model.setResultRemark(bio.getRemark());
                   /* if(1==bio.getOperatorType().intValue())
                    {
                        model.setStrategy("手动改为合格");
                        model.setSolverName(bio.getOperatorName());
                        model.setSolveTime(bio.getCreateTime());
                    }else */
                    if(2==bio.getOperatorType().intValue())
                    {
                        AbnormalSolveRecord abSolverRecord = getByTaskId(taskId);
                        if(null != abSolverRecord)
                        {
                            if("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy()))
                            {
                                model.setStrategy("实验取消");
                            }
                            else if("RESAMPLING".equals(abSolverRecord.getStrategy()))
                            {
                                model.setStrategy("重新送样");
                            }
                            else if("RE_EXPERIMENT".equals(abSolverRecord.getStrategy()))
                            {
                                model.setStrategy("重新实验");
                            }
                            else
                            {
                                TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                                if(null != config)
                                {
                                    model.setStrategy(config.getName());
                                }
                            }
                            model.setSolverName(abSolverRecord.getSolverName());
                            model.setSolveTime(abSolverRecord.getSolveTime());
                        }
                    }
                  /*  else if(3==bio.getOperatorType().intValue())
                    {
                        model.setStrategy("重新生信注释");
                        model.setSolverName(bio.getOperatorName());
                        model.setSolveTime(bio.getCreateTime());
                    }*/
                    modelList.add(model);
                }
            }
        } else if (TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(semantic)) {
            String hql = "FROM TestingScheduleHistory t WHERE t.taskId = :taskId order by t.timestamp desc";
            List<TestingScheduleHistory> records = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"taskId"}, new String[]{taskId});
            if (Collections3.isNotEmpty(records)) {
                List<TestingScheduleHistory> scheduleHistories = getTaskIds(records.get(0).getScheduleId());
                if (Collections3.isNotEmpty(scheduleHistories)) {
                    for (TestingScheduleHistory his : scheduleHistories) {
                        String hisTaskId = his.getTaskId();
                        String taskRef = his.getTaskRefer();
                        String taskName = "";
                        if (StringUtils.isEmpty(taskRef)) {
                            TestingTask task = baseDaoSupport.get(TestingTask.class, hisTaskId);
                            if (null != task) {
                                taskName = task.getName();
                            }
                        } else {
                            taskName = getNameByTaskRefer(taskRef);
                        }
                        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, hisTaskId);
                        if (null != taskResult && StringUtils.isNotEmpty(taskResult.getResult()) && !"0".equals(taskResult.getResult())) {
                            AbnormalHistoryModel model = new AbnormalHistoryModel();
                            model.setTaskName(taskName);
                            if (StringUtils.isNotEmpty(taskResult.getRemark())) {
                                model.setResultRemark(taskResult.getRemark());
                            } else {
                                if (StringUtils.isNotEmpty(taskResult.getDetails())) {
                                    if (taskResult.getDetails().contains("{")) {
                                        JSONObject temp = JSONObject.parseObject(taskResult.getDetails());
                                        Object obj = temp.get("map");
                                        if (null == obj) {
                                            Object obre = temp.get("unqualifiedRemark");
                                            if (null != obre) {
                                                model.setResultRemark(obre.toString());
                                            }
                                        } else {
                                            Map map = (Map) obj;
                                            if (null != map.get("备注")) {
                                                model.setResultRemark(map.get("备注").toString());
                                            }
                                        }
                                    }
                                }
                            }
                            if (TestingTaskResult.RESULT_FAILURE_REPORT.equals(taskResult.getResult()))//上报
                            {
                                AbnormalSolveRecord abSolverRecord = getByTaskId(hisTaskId);
                                if (null != abSolverRecord) {
                                    if ("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy())) {
                                        model.setStrategy("实验取消");
                                    } else if ("RESAMPLING".equals(abSolverRecord.getStrategy())) {
                                        model.setStrategy("重新送样");
                                    } else if ("RE_EXPERIMENT".equals(abSolverRecord.getStrategy())) {
                                        model.setStrategy("重新实验");
                                    } else {
                                        TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                                        if (null != config) {
                                            model.setStrategy(config.getName());
                                        }
                                    }
                                    model.setSolverName(abSolverRecord.getSolverName());
                                    model.setSolveTime(abSolverRecord.getSolveTime());
                                }
                            }
                            modelList.add(model);
                        }
                    }
                }
            }
        }else{
            String hql = "FROM TestingScheduleHistory t WHERE t.taskId = :taskId order by t.timestamp desc";
            List<TestingScheduleHistory> records = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"taskId"}, new String[] {taskId});
            if(Collections3.isNotEmpty(records))
            {
                List<TestingScheduleHistory> scheduleHistories = getTaskIds(records.get(0).getScheduleId());
                if (Collections3.isNotEmpty(scheduleHistories))
                {
                    for (TestingScheduleHistory his : scheduleHistories)
                    {
                        String hisTaskId = his.getTaskId();
                        String taskRef = his.getTaskRefer();
                        String taskName = "";
                        TestingTask task = null;
                        if (StringUtils.isEmpty(taskRef)) {
                            task = baseDaoSupport.get(TestingTask.class, hisTaskId);
                            if (null != task) {
                                taskName = task.getName();
                            }
                        } else {
                            taskName = getNameByTaskRefer(taskRef);
                        }
                        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, hisTaskId);
                        if(null != taskResult && StringUtils.isNotEmpty(taskResult.getResult()) && !"0".equals(taskResult.getResult()))
                        {
                            AbnormalHistoryModel model = new AbnormalHistoryModel();
                            model.setTaskName(taskName);
                            if(StringUtils.isNotEmpty(taskResult.getRemark()))
                            {
                                model.setResultRemark(taskResult.getRemark());
                            }
                            else
                            {
                                if(StringUtils.isNotEmpty(taskResult.getDetails()))
                                {
                                    if(taskResult.getDetails().contains("{"))
                                    {
                                        JSONObject temp = JSONObject.parseObject(taskResult.getDetails());
                                        Object obj = temp.get("map");
                                        if(null == obj)
                                        {
                                            Object obre = temp.get("unqualifiedRemark");
                                            if(null != obre)
                                            {
                                                model.setResultRemark(obre.toString());
                                            }
                                        }
                                        else
                                        {
                                            Map map = (Map)obj;
                                            if(null != map.get("备注"))
                                            {
                                                model.setResultRemark(map.get("备注").toString());
                                            }
                                        }
                                    }
                                }
                            }

                            if(TestingTaskResult.RESULT_FAILURE_SOLVE.equals(taskResult.getResult()))//直接解决了
                            {
                                if (null != task)
                                {
                                    String str2 = testingScheduleService.getStrategyByTaskSemantic(task, taskResult);
                                    if (StringUtils.isNotEmpty(str2))
                                    {
                                        model.setStrategy(str2);
                                        model.setAnnormalRemark(model.getResultRemark());
                                        model.setSolveTime(task.getEndTime());
                                        String hql2 = "FROM TestingSheetTask t WHERE t.testingTaskId = :testingTaskId";
                                        List<TestingSheetTask> sheetTasks = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql2, new String[]{"testingTaskId"}, new String[]{hisTaskId});
                                        if (Collections3.isNotEmpty(sheetTasks))
                                        {
                                            if (null != sheetTasks.get(0).getTestingSheet()) {
                                                model.setSolverName(sheetTasks.get(0).getTestingSheet().getTesterName());
                                            }
                                        }
                                    }
                                }
                            }else if(TestingTaskResult.RESULT_FAILURE_REPORT.equals(taskResult.getResult()))//上报
                            {
                                AbnormalSolveRecord abSolverRecord = getByTaskId(hisTaskId);
                                if(null != abSolverRecord)
                                {
                                    if("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy()))
                                    {
                                        model.setStrategy("实验取消");
                                    }
                                    else if("RESAMPLING".equals(abSolverRecord.getStrategy()))
                                    {
                                        model.setStrategy("重新送样");
                                    }
                                    else if("RE_EXPERIMENT".equals(abSolverRecord.getStrategy()))
                                    {
                                        model.setStrategy("重新实验");
                                    }
                                    else
                                    {
                                        TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                                        if(null != config)
                                        {
                                            model.setStrategy(config.getName());
                                        }
                                    }
                                    model.setSolverName(abSolverRecord.getSolverName());
                                    model.setSolveTime(abSolverRecord.getSolveTime());
                                }
                            }
                            modelList.add(model);
                        }
                    }
                }
            }
        }

        //modelList.sort(Comparator.comparing(AbnormalHistoryModel::getSolveTime));
        return modelList;
    }

    private List<TestingScheduleHistory> getTaskIds(String scheduleId)
    {
        String hql = "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId order by t.timestamp";
        List<TestingScheduleHistory> records = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[]{"scheduleId"}, new String[]{scheduleId});
        if(Collections3.isNotEmpty(records))
        {
            return records;
        }
        return null;
    }

    @Override
    public AbnormalHistoryModel getAbnormalHistorySingle(String taskId)
    {
        AbnormalHistoryModel model = new AbnormalHistoryModel();
        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, taskId);
        if(null != taskResult && StringUtils.isNotEmpty(taskResult.getResult()) && !"0".equals(taskResult.getResult()))
        {
            if (StringUtils.isEmpty(taskResult.getTaskRefer())){
                TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
                model.setTaskName(task.getName());
                if(StringUtils.isNotEmpty(taskResult.getRemark()))
                {
                    model.setResultRemark(taskResult.getRemark());
                }
                else
                {
                    if(StringUtils.isNotEmpty(taskResult.getDetails()))
                    {
                        JSONObject temp = JSONObject.parseObject(taskResult.getDetails());
                        Object obj = temp.get("map");
                        if(null == obj)
                        {
                            Object obre = temp.get("unqualifiedRemark");
                            if(null != obre)
                            {
                                model.setResultRemark(obre.toString());
                            }
                        }
                        else
                        {
                            Map map = (Map)obj;
                            if(null != map.get("备注"))
                            {
                                model.setResultRemark(map.get("备注").toString());
                            }
                        }
                    }
                }
                model.setEndTime(task.getEndTime());
                if(TestingTaskResult.RESULT_FAILURE_SOLVE.equals(taskResult.getResult()))//直接解决了
                {
                    String str = testingScheduleService.getStrategyByTaskSemantic(task, taskResult);
                    if(StringUtils.isNotEmpty(str))
                    {
                        model.setStrategy(str);
                        model.setAnnormalRemark(taskResult.getRemark());
                        model.setSolveTime(task.getEndTime());
                    }
                }
                else if(TestingTaskResult.RESULT_FAILURE_REPORT.equals(taskResult.getResult()))//上报
                {
                    AbnormalSolveRecord abSolverRecord = getByTaskId(taskId);
                    if(null != abSolverRecord)
                    {
                        if("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy()))
                        {
                            model.setStrategy("实验取消");
                        }
                        else if("RESAMPLING".equals(abSolverRecord.getStrategy()))
                        {
                            model.setStrategy("重新送样");
                        }
                        else if("RE_EXPERIMENT".equals(abSolverRecord.getStrategy()))
                        {
                            model.setStrategy("重新实验");
                        }
                        else
                        {
                            TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                            if(null != config)
                            {
                                model.setStrategy(config.getName());
                            }
                        }
                        model.setAnnormalRemark(abSolverRecord.getRemark());
                        model.setSolverName(abSolverRecord.getSolverName());
                        model.setSolveTime(abSolverRecord.getSolveTime());
                    }
                }
            }
            else if (taskResult.getTaskRefer().equals("TECHNICAL-ANALYSIS")){
                TechnicalAnalysisTask technicalAnalysisTask = baseDaoSupport.get(TechnicalAnalysisTask.class, taskId);
                model.setTaskName(technicalAnalysisTask.getName());
                if(StringUtils.isNotEmpty(taskResult.getRemark()))
                {
                    model.setResultRemark(taskResult.getRemark());
                }
                model.setEndTime(technicalAnalysisTask.getEndTime());
                AbnormalSolveRecord abSolverRecord = getByTaskId(taskId);
                if (StringUtils.isNotEmpty(abSolverRecord))
                {
                    if("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("实验取消");
                    }
                    else if("RESAMPLING".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("重新送样");
                    }
                    else if("RE_EXPERIMENT".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("重新实验");
                    }
                    else
                    {
                        TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                        if(null != config)
                        {
                            model.setStrategy(config.getName());
                        }
                    }
                    model.setAnnormalRemark(abSolverRecord.getRemark());
                    model.setSolverName(abSolverRecord.getSolverName());
                    model.setSolveTime(abSolverRecord.getSolveTime());
                }
            }
            else if (taskResult.getTaskRefer().equals("BIOLOGY-ANNOTATION")){
                BiologyAnnotationTask biologyAnnotationTask = baseDaoSupport.get(BiologyAnnotationTask.class, taskId);
                model.setTaskName("生信注释");
                if(StringUtils.isNotEmpty(taskResult.getRemark()))
                {
                    model.setResultRemark(taskResult.getRemark());
                }
                model.setEndTime(biologyAnnotationTask.getEndTime());
                AbnormalSolveRecord abSolverRecord = getByTaskId(taskId);
                if (StringUtils.isNotEmpty(abSolverRecord))
                {

                    if("EXPERIMENT-CANCER".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("实验取消");
                    }
                    else if("RESAMPLING".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("重新送样");
                    }
                    else if("RE_EXPERIMENT".equals(abSolverRecord.getStrategy()))
                    {
                        model.setStrategy("重新实验");
                    }
                    else
                    {
                        TaskConfig config = bcmadapter.getTaskConfigBySemantic(abSolverRecord.getStrategy());
                        if(null != config)
                        {
                            model.setStrategy(config.getName());
                        }
                    }
                    model.setAnnormalRemark(abSolverRecord.getRemark());
                    model.setSolverName(abSolverRecord.getSolverName());
                    model.setSolveTime(abSolverRecord.getSolveTime());
                }

            }
        }
        return model;
    }

    private List<BiologyAnnotationFailOperate> getBiologyAnnotationFailOperateByTaskId(String taskId)
    {
        String hql = " FROM BiologyAnnotationFailOperate o WHERE o.taskId = '"+taskId+"' order by o.createTime desc ";
        return baseDaoSupport.find(BiologyAnnotationFailOperate.class,hql);
    }

    @Override
    public TestingAbnormalTaskView wrap(TestingAbnormalTaskView entity) {
        return null;
    }

    static String getNameByTaskRefer(String taskRefer) {
        switch (taskRefer) {
            case "BIOLOGY-ANNOTATION":
                return "生信注释";
            case "TECHNICAL-ANALYSIS":
                return "新技术分析";
        }
        return null;
    }

    @Override
    public AbnormalSolveRecord getReSampleRecord(String scheduleId) {
        List<AbnormalSolveRecord> abnormalSolveRecords =
                baseDaoSupport.find(AbnormalSolveRecord.class, "from AbnormalSolveRecord r where r.strategy = 'RESAMPLING' AND r.taskId in (SELECT h.taskId FROM TestingScheduleHistory h WHERE h.scheduleId = '" + scheduleId + "')");
        return Collections3.getFirst(abnormalSolveRecords);
    }

    @Override
    public AbnormalSolveRecord getReSampleRecordByTask(String taskId) {
        List<AbnormalSolveRecord> abnormalSolveRecords =
                baseDaoSupport.find(AbnormalSolveRecord.class, "from AbnormalSolveRecord r where r.strategy = 'RESAMPLING' AND r.taskId = '"+taskId+"'");
        return Collections3.getFirst(abnormalSolveRecords);
    }
}
