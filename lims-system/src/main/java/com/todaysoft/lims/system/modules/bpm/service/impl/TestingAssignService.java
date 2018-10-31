package com.todaysoft.lims.system.modules.bpm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.modules.bpm.model.assign.args.BiAnalysisAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.args.OnTestingAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.args.TechnicalAnalysisAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.BiAnalysisAssignModel;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.TechnicalAnalysisAssignModel;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.task.BiAnalysisAssignTask;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.task.OnTestingAssignTask;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.task.TechnicalAnalysisAssignTask;
import com.todaysoft.lims.system.modules.bpm.model.internal.QualityControlMethod;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.modules.bpm.model.internal.Tester;
import com.todaysoft.lims.system.modules.bpm.service.ITestingAssignService;

@Service
public class TestingAssignService implements ITestingAssignService
{
    @Autowired
    private RestTemplate template;
    

    @Override
    public BiAnalysisAssignModel getBiAnalysisTaskAssignModel(BiAnalysisAssignArgs args)
    {
        BiAnalysisAssignModel model = new BiAnalysisAssignModel();
        model.setTimestamp(new Date());
        model.setLoginUsername("文库质检王二");
        model.setTaskCode("WKQC00000001");
        
        List<Tester> testers = new ArrayList<Tester>();
        Tester tester = new Tester();
        tester.setId("1");
        tester.setName("文库质检张三");
        testers.add(tester);
        
        tester = new Tester();
        tester.setId("2");
        tester.setName("文库质检李四");
        testers.add(tester);
        
        model.setExtractTesters(testers);
        model.setQualityControlTesters(testers);
        
        List<QualityControlMethod> methods = new ArrayList<QualityControlMethod>();
        QualityControlMethod method = new QualityControlMethod();
        method.setId("1");
        method.setName("质检方法-1");
        methods.add(method);
        
        method = new QualityControlMethod();
        method.setId("2");
        method.setName("质检方法-2");
        methods.add(method);
        model.setQualityControlMethods(methods);
        
        List<ReagentKits> rkits = new ArrayList<ReagentKits>();
        ReagentKits kit = new ReagentKits();
        kit.setId("1");
        kit.setName("试剂盒-1");
        kit.setSampleInputQuantity(500D);
        rkits.add(kit);
        
        kit = new ReagentKits();
        kit.setId("2");
        kit.setName("试剂盒-2");
        kit.setSampleInputQuantity(300D);
        rkits.add(kit);
        model.setReagentKits(rkits);
        
        BiAnalysisAssignTask task;
        List<BiAnalysisAssignTask> tasks = new ArrayList<BiAnalysisAssignTask>();
        
        for (int i = 0; i < 50; i++)
        {
            task = new BiAnalysisAssignTask();
            task.setId(String.valueOf(i));
            task.setConnectorId("connectorId" + i);
            task.setCoordinate("coordinate");
            task.setInstitution("institution");
            task.setLibraryCode("libraryCode" + i);
            task.setNotes("notes" + i);
            task.setProductName("productName" + i);
            task.setReceiveType("全血");
            task.setSex("男");
            task.setSourceSampleCode("sourceSampleCode" + i);
            task.setSourceSampleType("type" + i);
            task.setTemporaryStorageLocation("位置" + i);
            task.setTestCode("testCode" + i);
            task.setTestedName("testedName" + i);
            task.setWkbhCode("wkbhCode" + i);
            tasks.add(task);
        }
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    public TechnicalAnalysisAssignModel getTechnicalAnalysisTaskAssignModel(TechnicalAnalysisAssignArgs args)
    {
        TechnicalAnalysisAssignModel model = new TechnicalAnalysisAssignModel();
        model.setTimestamp(new Date());
        model.setLoginUsername("文库质检王二");
        model.setTaskCode("WKQC00000001");
        
        List<Tester> testers = new ArrayList<Tester>();
        Tester tester = new Tester();
        tester.setId("1");
        tester.setName("文库质检张三");
        testers.add(tester);
        
        tester = new Tester();
        tester.setId("2");
        tester.setName("文库质检李四");
        testers.add(tester);
        
        model.setExtractTesters(testers);
        model.setQualityControlTesters(testers);
        
        List<QualityControlMethod> methods = new ArrayList<QualityControlMethod>();
        QualityControlMethod method = new QualityControlMethod();
        method.setId("1");
        method.setName("质检方法-1");
        methods.add(method);
        
        method = new QualityControlMethod();
        method.setId("2");
        method.setName("质检方法-2");
        methods.add(method);
        model.setQualityControlMethods(methods);
        
        List<ReagentKits> rkits = new ArrayList<ReagentKits>();
        ReagentKits kit = new ReagentKits();
        kit.setId("1");
        kit.setName("试剂盒-1");
        kit.setSampleInputQuantity(500D);
        rkits.add(kit);
        
        kit = new ReagentKits();
        kit.setId("2");
        kit.setName("试剂盒-2");
        kit.setSampleInputQuantity(300D);
        rkits.add(kit);
        model.setReagentKits(rkits);
        
        TechnicalAnalysisAssignTask task;
        List<TechnicalAnalysisAssignTask> tasks = new ArrayList<TechnicalAnalysisAssignTask>();
        
        for (int i = 0; i < 50; i++)
        {
            task = new TechnicalAnalysisAssignTask();
            task.setId(String.valueOf(i));
            task.setExaminee("葡萄");
            task.setHistoryStatus(1);
            task.setOrderType("1");
            task.setSampleCode("10101010" + i);
            task.setSampleTypeKey("2");
            task.setSampleTypeName("全血");
            task.setProducts("临检");
            task.setSamplingDate(new Date());
            tasks.add(task);
        }
        model.setTasks(tasks);
        return model;
    }
}