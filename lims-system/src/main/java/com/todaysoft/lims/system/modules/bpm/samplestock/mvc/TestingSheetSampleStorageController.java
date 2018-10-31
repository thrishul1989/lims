package com.todaysoft.lims.system.modules.bpm.samplestock.mvc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.bpm.dt.service.IDTService;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.TestingMlpaTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockin;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockinDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockout;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockoutDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageOutRecord;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageResponse;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingCaptureSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSampleStorage;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheetSampleStorage;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheetTask;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import com.todaysoft.lims.system.modules.bpm.samplestock.service.ISheetSampleStorageService;
import com.todaysoft.lims.system.modules.bpm.samplestock.service.TestingCodeComparator;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.util.AppToken;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testingSheetSampleStorage")
public class TestingSheetSampleStorageController extends BaseController
{
    
    @Autowired
    private ISheetSampleStorageService sheetSampleStorageService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDTService dtService;
    
    @Autowired
    private ITaskService taskService;
    
    private static Logger log = LoggerFactory.getLogger(TestingSheetSampleStorageController.class);
    
    @RequestMapping("/tab.do")
    public String list(TestingMethodSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        return "testingSampleStorage/tab";
    }
    
    @RequestMapping("/sampleOut_list.do")
    public String sampleOut_list(TestingSheetSampleStorage searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        searcher.setStorageType("1");
        Pagination<SampleStorageOutRecord> pagination = sheetSampleStorageService.sampleOut_list(searcher, pageNo, 10);
        List<Task> taskList = Lists.newArrayList();
        if (null == session.getAttribute("taskList"))
        {
            taskList = taskService.list(new TaskSearcher());
            session.setAttribute("taskList", taskList);
        }
        else
        {
            taskList = (List<Task>)session.getAttribute("taskList");
        }
        
        model.addAttribute("tasks", taskList);
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "testingSampleStorage/sampleOut_list";
    }
    
    @RequestMapping("/sampleIn_list.do")
    public String sampleIn_list(TestingSheetSampleStorage searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        searcher.setStorageType("2");
        Pagination<SampleStorageOutRecord> pagination = sheetSampleStorageService.sampleOut_list(searcher, pageNo, 10);
        List<Task> taskList = Lists.newArrayList();
        if (null == session.getAttribute("taskList"))
        {
            taskList = taskService.list(new TaskSearcher());
            session.setAttribute("taskList", taskList);
        }
        else
        {
            taskList = (List<Task>)session.getAttribute("taskList");
        }
        model.addAttribute("tasks", taskList);
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "testingSampleStorage/sampleIn_list";
    }
    
    @RequestMapping("/sampleOut_show.do")
    public String sampleOut_show(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        // 查询操作记录
        SampleStockout sampleOut = sheetSampleStorageService.getOutDetail(sheetSampleStorage.getStorageRecordId());
        
        model.addAttribute("sampleOut", sampleOut);
        model.addAttribute("sheetSampleStorage", sheetSampleStorage);
        
        return "testingSampleStorage/sampleOut_show";
    }
    
    @RequestMapping("/sampleIn_show.do")
    public String sampleIn_show(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        // 查询操作记录
        SampleStockin sampleIn = sheetSampleStorageService.getInDetail(sheetSampleStorage.getStorageRecordId());
        
        model.addAttribute("sampleIn", sampleIn);
        model.addAttribute("sheetSampleStorage", sheetSampleStorage);
        
        return "testingSampleStorage/sampleIn_show";
    }
    
    @RequestMapping("/sampleOut_operate.do")
    public String sampleOut_operate(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        model.addAttribute("sheetSampleStorage", sheetSampleStorage);
        model.addAttribute("result", 1);
        List<SampleStorageResponse> list = new ArrayList<SampleStorageResponse>();
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
        {
            
            if ("MULTI-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 特殊处理(获取实验编号)
            
                List<MultiPcrTask> multipcrList = sheetSampleStorageService.getMultiPcrTaskBytaskId(sheeTask.getTestingTaskId());
                for (MultiPcrTask multipcr : multipcrList)
                {
                    // 去重
                    if (!fileter.containsKey(multipcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage =
                            sheetSampleStorageService.getTestingSampleStorage(multipcr.getTestingTask().getInputSample().getSampleCode());
                        map.setSample(multipcr.getTestingTask().getInputSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(multipcr.getTestingCode());
                        testingTask.setName("多重PCR实验");
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                            model.addAttribute("result", 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                                
                            }
                            
                        }
                        fileter.put(multipcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                    
                }
                
            }
            else if ("MLPA".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// MLPA特殊处理(获取实验编号)
            
                List<TestingMlpaTask> mlpaList = sheetSampleStorageService.getMlpaTestingBytaskId(sheeTask.getTestingTaskId());
                for (TestingMlpaTask mlpa : mlpaList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(mlpa.getTestingSample().getSampleCode());
                        map.setSample(mlpa.getTestingSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(mlpa.getTestCode());
                        testingTask.setName("MLPA实验");
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                            model.addAttribute("result", 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(mlpa.getTestingSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                            }
                            
                        }
                        fileter.put(mlpa.getTestingSample().getSampleCode(), "");
                    }
                    
                }
                
            }
            else if ("RQT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 去重、NGS且是文库的就入库
                if (!fileter.containsKey(testingTask.getInputSample().getSampleCode()) && "文库".equals(testingTask.getTestingSampleType())
                    && "NGS".equals(testingTask.getTestingMethodName()))
                {
                    // 获取实验编号
                    String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                    
                    testingTask.setTestingCode(testingCode);
                    // 获取样本位置
                    TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                    map.setSample(testingTask.getInputSample());
                    map.setStorage(storage);
                    map.setTask(testingTask);
                    list.add(map);
                    
                    if (null == storage)
                    {// 判断出库样本是否已经生成位置或者不在库中
                        model.addAttribute("result", 2);
                        
                    }
                    if (null != storage && 1 != storage.getStatus())
                    {
                        model.addAttribute("result", 3);
                        // 查询该样本最近的出库记录
                        SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                        if (null != closestOut)
                        {
                            map.setClosestOut(closestOut);
                        }
                        
                    }
                    fileter.put(testingTask.getInputSample().getSampleCode(), "");
                }
                
            }
            else if ("Long-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic())
                || "Long-QC".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                List<LongPcrTask> longpcrList = sheetSampleStorageService.getLongPcrByTaskId(sheeTask.getTestingTaskId());
                for (LongPcrTask longpcr : longpcrList)
                {
                    // 去重
                    if (!fileter.containsKey(longpcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage =
                            sheetSampleStorageService.getTestingSampleStorage(longpcr.getTestingTask().getInputSample().getSampleCode());
                        map.setSample(longpcr.getTestingTask().getInputSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(longpcr.getTestingCode());
                        testingTask.setName(sheetSampleStorage.getTestingSheet().getTaskName());
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                            model.addAttribute("result", 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                                
                            }
                            
                        }
                        fileter.put(longpcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else if ("DT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 动态突变特殊处理(获取实验编号)
            
                System.out.println("特殊查询动态突变实验编号");
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取实验编号
                String testingCode = sheetSampleStorageService.getDtTestingCode(testingTask.getId());
                System.out.println("动态突变实验编号:" + testingCode);
                testingTask.setTestingCode(testingCode);
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                map.setSample(testingTask.getInputSample());
                map.setStorage(storage);
                map.setTask(testingTask);
                list.add(map);
                
                if (null == storage)
                {// 判断出库样本是否已经生成位置或者不在库中
                 // model.addAttribute("result", 2);
                    
                }
                if (null != storage && 1 != storage.getStatus())
                {
                    // model.addAttribute("result", 3);
                    // 查询该样本最近的出库记录
                    SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                    if (null != closestOut)
                    {
                        map.setClosestOut(closestOut);
                    }
                    
                }
                
            }
            else
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 去重
                if (!fileter.containsKey(testingTask.getInputSample().getSampleCode()))
                {
                    // 获取实验编号
                    String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                    
                    testingTask.setTestingCode(testingCode);
                    // 获取样本位置
                    TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                    map.setSample(testingTask.getInputSample());
                    map.setStorage(storage);
                    map.setTask(testingTask);
                    list.add(map);
                    
                    if (null == storage)
                    {// 判断出库样本是否已经生成位置或者不在库中
                        model.addAttribute("result", 2);
                        
                    }
                    if (null != storage && 1 != storage.getStatus())
                    {
                        model.addAttribute("result", 3);
                        // 查询该样本最近的出库记录
                        SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                        if (null != closestOut)
                        {
                            map.setClosestOut(closestOut);
                        }
                        
                    }
                    fileter.put(testingTask.getInputSample().getSampleCode(), "");
                }
            }
            
        }
        // 实验编号排序
        
        Collections.sort(list, new Comparator<SampleStorageResponse>()
        {
            @Override
            public int compare(SampleStorageResponse o1, SampleStorageResponse o2)
            {
                return new TestingCodeComparator().compare(o1.getTask().getTestingCode(), o2.getTask().getTestingCode());
            }
        });
        
        model.addAttribute("sampleList", list);
        
        return "testingSampleStorage/sampleOut_operate";
    }
    
    @ResponseBody
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    public String download(TestingSheetSampleStorage searcher)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        List<SampleStorageResponse> list = new ArrayList<SampleStorageResponse>();
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
        {
            
            if ("MULTI-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 特殊处理(获取实验编号)
            
                List<MultiPcrTask> multipcrList = sheetSampleStorageService.getMultiPcrTaskBytaskId(sheeTask.getTestingTaskId());
                for (MultiPcrTask multipcr : multipcrList)
                {
                    // 去重
                    if (!fileter.containsKey(multipcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage =
                            sheetSampleStorageService.getTestingSampleStorage(multipcr.getTestingTask().getInputSample().getSampleCode());
                        map.setSample(multipcr.getTestingTask().getInputSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(multipcr.getTestingCode());
                        testingTask.setName("多重PCR实验");
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                         // model.addAttribute("result",
                         // 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            // model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                                
                            }
                            
                        }
                        fileter.put(multipcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                    
                }
                
            }
            else if ("MLPA".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// MLPA特殊处理(获取实验编号)
            
                List<TestingMlpaTask> mlpaList = sheetSampleStorageService.getMlpaTestingBytaskId(sheeTask.getTestingTaskId());
                for (TestingMlpaTask mlpa : mlpaList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(mlpa.getTestingSample().getSampleCode());
                        map.setSample(mlpa.getTestingSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(mlpa.getTestCode());
                        testingTask.setName("MLPA实验");
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                         // model.addAttribute("result",
                         // 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            // model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(mlpa.getTestingSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                            }
                            
                        }
                        fileter.put(mlpa.getTestingSample().getSampleCode(), "");
                    }
                    
                }
                
            }
            else if ("DT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 动态突变特殊处理(获取实验编号)
            
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取实验编号
                String testingCode = sheetSampleStorageService.getDtTestingCode(testingTask.getId());
                
                testingTask.setTestingCode(testingCode);
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                map.setSample(testingTask.getInputSample());
                map.setStorage(storage);
                map.setTask(testingTask);
                list.add(map);
                
                if (null == storage)
                {// 判断出库样本是否已经生成位置或者不在库中
                 // model.addAttribute("result", 2);
                    
                }
                if (null != storage && 1 != storage.getStatus())
                {
                    // model.addAttribute("result", 3);
                    // 查询该样本最近的出库记录
                    SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                    if (null != closestOut)
                    {
                        map.setClosestOut(closestOut);
                    }
                    
                }
                
            }
            else if ("Long-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic())
                || "Long-QC".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                List<LongPcrTask> longpcrList = sheetSampleStorageService.getLongPcrByTaskId(sheeTask.getTestingTaskId());
                for (LongPcrTask longpcr : longpcrList)
                {
                    // 去重
                    if (!fileter.containsKey(longpcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取样本位置
                        TestingSampleStorage storage =
                            sheetSampleStorageService.getTestingSampleStorage(longpcr.getTestingTask().getInputSample().getSampleCode());
                        map.setSample(longpcr.getTestingTask().getInputSample());
                        map.setStorage(storage);
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(longpcr.getTestingCode());
                        testingTask.setName(sheetSampleStorage.getTestingSheet().getTaskName());
                        map.setTask(testingTask);
                        list.add(map);
                        if (null == storage)
                        {// 判断出库样本是否已经生成位置或者不在库中
                         // model.addAttribute("result",
                         // 2);
                            
                        }
                        if (null != storage && 1 != storage.getStatus())
                        {
                            // model.addAttribute("result", 3);
                            // 查询该样本最近的出库记录
                            SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                            if (null != closestOut)
                            {
                                map.setClosestOut(closestOut);
                                
                            }
                            
                        }
                        fileter.put(longpcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取实验编号
                String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                
                testingTask.setTestingCode(testingCode);
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                map.setSample(testingTask.getInputSample());
                map.setStorage(storage);
                map.setTask(testingTask);
                list.add(map);
                
                if (null == storage)
                {// 判断出库样本是否已经生成位置或者不在库中
                 // model.addAttribute("result", 2);
                    
                }
                if (null != storage && 1 != storage.getStatus())
                {
                    // model.addAttribute("result", 3);
                    // 查询该样本最近的出库记录
                    SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                    if (null != closestOut)
                    {
                        map.setClosestOut(closestOut);
                    }
                    
                }
            }
            
        }
        // 实验编号排序
        
        Collections.sort(list, new Comparator<SampleStorageResponse>()
        {
            @Override
            public int compare(SampleStorageResponse o1, SampleStorageResponse o2)
            {
                return new TestingCodeComparator().compare(o1.getTask().getTestingCode(), o2.getTask().getTestingCode());
            }
        });
        
        InputStream is = TestingSheetSampleStorageController.class.getResourceAsStream("/taskTemplate/sampleInOut/sample_out.xlsx");
        return sheetSampleStorageService.download(is, list, sheetSampleStorage);
    }
    
    @RequestMapping("/sampleIn_operate.do")
    public String sampleIn_operate(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        model.addAttribute("sheetSampleStorage", sheetSampleStorage);
        model.addAttribute("result", 1);
        List<SampleStorageResponse> list = new ArrayList<SampleStorageResponse>();
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
        {
            
            if ("MLPA".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// mlpa特殊处理获取编号
            
                List<TestingMlpaTask> mlpaList = sheetSampleStorageService.getMlpaTestingBytaskId(sheeTask.getTestingTaskId());
                for (TestingMlpaTask mlpa : mlpaList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(mlpa.getTestingSample().getSampleCode());
                        // 获取实验编号
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(mlpa.getTestCode());
                        testingTask.setName("MLPA实验");
                        
                        map.setSample(mlpa.getTestingSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        if (null != storage && 1 == storage.getStatus())
                        {
                            model.addAttribute("result", 2);
                            // 查询该样本最近的入库记录
                            SampleStockin closestIn = sheetSampleStorageService.closestInBySampleId(testingTask.getInputSample().getId());
                            if (null != closestIn)
                            {
                                map.setClosestIn(closestIn);
                            }
                            
                        }
                        fileter.put(mlpa.getTestingSample().getSampleCode(), "");
                    }
                }
            }
            
            else if ("MULTI-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 特殊处理获取编号
            
                List<MultiPcrTask> multipcrList = sheetSampleStorageService.getMultiPcrTaskBytaskId(sheeTask.getTestingTaskId());
                for (MultiPcrTask mlpa : multipcrList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        // 获取样本位置
                        TestingSampleStorage storage =
                            sheetSampleStorageService.getTestingSampleStorage(mlpa.getTestingTask().getInputSample().getSampleCode());
                        // 获取实验编号
                        
                        TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                        testingTask.setTestingCode(mlpa.getTestingCode());
                        testingTask.setName("多重PCR实验");
                        
                        map.setSample(mlpa.getTestingTask().getInputSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        if (null != storage && 1 == storage.getStatus())
                        {
                            model.addAttribute("result", 2);
                            // 查询该样本最近的入库记录
                            SampleStockin closestIn = sheetSampleStorageService.closestInBySampleId(testingTask.getInputSample().getId());
                            if (null != closestIn)
                            {
                                map.setClosestIn(closestIn);
                            }
                            
                        }
                        fileter.put(mlpa.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else if ("RQT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                // 去重、获取实验编号
                if (!fileter.containsKey(testingTask.getInputSample().getSampleCode()) && "文库".equals(testingTask.getTestingSampleType())
                    && "NGS".equals(testingTask.getTestingMethodName()))
                {
                    String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                    
                    testingTask.setTestingCode(testingCode);
                    
                    map.setSample(testingTask.getInputSample());
                    
                    map.setTask(testingTask);
                    list.add(map);
                    if (null != storage && 1 == storage.getStatus())
                    {
                        model.addAttribute("result", 2);
                        // 查询该样本最近的入库记录
                        SampleStockin closestIn = sheetSampleStorageService.closestInBySampleId(testingTask.getInputSample().getId());
                        if (null != closestIn)
                        {
                            map.setClosestIn(closestIn);
                        }
                        
                    }
                    fileter.put(testingTask.getInputSample().getSampleCode(), "");
                }
            }
            else if ("Long-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic())
                || "Long-QC".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                List<LongPcrTask> longpcrList = sheetSampleStorageService.getLongPcrByTaskId(sheeTask.getTestingTaskId());
                for (LongPcrTask longpcr : longpcrList)
                {
                    // 去重
                    if (!fileter.containsKey(longpcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        
                        // 获取实验编号
                        TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                        
                        testingTask.setTestingCode(longpcr.getTestingCode());
                        
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                        testingTask.setName(sheetSampleStorage.getTestingSheet().getTaskName());
                        
                        map.setSample(longpcr.getTestingTask().getInputSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        if (null != storage && 1 == storage.getStatus())
                        {
                            model.addAttribute("result", 2);
                            // 查询该样本最近的入库记录
                            SampleStockin closestIn = sheetSampleStorageService.closestInBySampleId(testingTask.getInputSample().getId());
                            if (null != closestIn)
                            {
                                map.setClosestIn(closestIn);
                            }
                            
                        }
                        fileter.put(longpcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else if ("DT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 动态突变特殊处理(获取实验编号)
            
                System.out.println("特殊查询动态突变实验编号");
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取实验编号
                String testingCode = sheetSampleStorageService.getDtTestingCode(testingTask.getId());
                System.out.println("动态突变实验编号:" + testingCode);
                testingTask.setTestingCode(testingCode);
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                map.setSample(testingTask.getInputSample());
                map.setStorage(storage);
                map.setTask(testingTask);
                list.add(map);
                
                if (null == storage)
                {// 判断出库样本是否已经生成位置或者不在库中
                 // model.addAttribute("result", 2);
                    
                }
                if (null != storage && 1 != storage.getStatus())
                {
                    // model.addAttribute("result", 3);
                    // 查询该样本最近的出库记录
                    /*SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                    if (null != closestOut) {
                    	map.setClosestOut(closestOut);
                    }*/
                    
                }
                
            }
            else
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                // 去重
                if (!fileter.containsKey(testingTask.getInputSample().getSampleCode()))
                {
                    // 获取实验编号
                    String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                    testingTask.setTestingCode(testingCode);
                    map.setSample(testingTask.getInputSample());
                    map.setTask(testingTask);
                    if (null != storage && 1 == storage.getStatus())
                    {
                        model.addAttribute("result", 2);
                        // 查询该样本最近的入库记录
                        SampleStockin closestIn = sheetSampleStorageService.closestInBySampleId(testingTask.getInputSample().getId());
                        if (null != closestIn)
                        {
                            map.setClosestIn(closestIn);
                        }
                        
                    }
                    list.add(map);
                    fileter.put(testingTask.getInputSample().getSampleCode(), "");
                }
            }
            
        }
        // 实验编号排序
        Collections.sort(list, new Comparator<SampleStorageResponse>()
        {
            @Override
            public int compare(SampleStorageResponse o1, SampleStorageResponse o2)
            {
                return new TestingCodeComparator().compare(o1.getTask().getTestingCode(), o2.getTask().getTestingCode());
            }
        });
        model.addAttribute("sampleList", list);
        
        return "testingSampleStorage/sampleIn_operate";
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadSampleIn.do", method = RequestMethod.POST)
    public String downloadSampleIn(TestingSheetSampleStorage searcher)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        List<SampleStorageResponse> list = new ArrayList<SampleStorageResponse>();
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
        {
            
            if ("MLPA".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// mlpa特殊处理获取编号
            
                List<TestingMlpaTask> mlpaList = sheetSampleStorageService.getMlpaTestingBytaskId(sheeTask.getTestingTaskId());
                for (TestingMlpaTask mlpa : mlpaList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        // 获取实验编号
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(mlpa.getTestCode());
                        testingTask.setName("MLPA实验");
                        
                        map.setSample(mlpa.getTestingSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        fileter.put(mlpa.getTestingSample().getSampleCode(), "");
                    }
                }
            }
            
            else if ("MULTI-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 特殊处理获取编号
            
                List<MultiPcrTask> multipcrList = sheetSampleStorageService.getMultiPcrTaskBytaskId(sheeTask.getTestingTaskId());
                for (MultiPcrTask mlpa : multipcrList)
                {
                    // 去重
                    if (!fileter.containsKey(mlpa.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        // 获取实验编号
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(mlpa.getTestingCode());
                        testingTask.setName("多重PCR实验");
                        
                        map.setSample(mlpa.getTestingTask().getInputSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        fileter.put(mlpa.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else if ("Long-PCR".equals(sheetSampleStorage.getTestingSheet().getSemantic())
                || "Long-QC".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {
                List<LongPcrTask> longpcrList = sheetSampleStorageService.getLongPcrByTaskId(sheeTask.getTestingTaskId());
                for (LongPcrTask longpcr : longpcrList)
                {
                    // 去重
                    if (!fileter.containsKey(longpcr.getTestingTask().getInputSample().getSampleCode()))
                    {
                        SampleStorageResponse map = new SampleStorageResponse();
                        // 获取实验编号
                        
                        TestingTask testingTask = new TestingTask();
                        testingTask.setTestingCode(longpcr.getTestingCode());
                        testingTask.setName(sheetSampleStorage.getTestingSheet().getTaskName());
                        
                        map.setSample(longpcr.getTestingTask().getInputSample());
                        
                        map.setTask(testingTask);
                        list.add(map);
                        fileter.put(longpcr.getTestingTask().getInputSample().getSampleCode(), "");
                    }
                }
            }
            else if ("DT".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 动态突变特殊处理(获取实验编号)
                System.out.println("特殊查询动态突变实验编号");
                SampleStorageResponse map = new SampleStorageResponse();
                log.info("start:testingTaskService.get(sheeTask.getTestingTaskId()),sheeTask.getTestingTaskId():" + sheeTask.getTestingTaskId());
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                log.info("end:testingTaskService.get(sheeTask.getTestingTaskId()),sheeTask.getTestingTaskId():" + sheeTask.getTestingTaskId());
                // 获取实验编号
                log.info("start:sheetSampleStorageService.getDtTestingCode(testingTask.getId()),testingTask.getId():" + testingTask.getId());
                String testingCode = sheetSampleStorageService.getDtTestingCode(testingTask.getId());
                log.info("end:sheetSampleStorageService.getDtTestingCode(testingTask.getId()),testingTask.getId():" + testingTask.getId());
                System.out.println("动态突变实验编号:" + testingCode);
                testingTask.setTestingCode(testingCode);
                // 获取样本位置
                log.info("start:sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode()),testingTask.getInputSample().getSampleCode():"
                    + testingTask.getInputSample().getSampleCode());
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                log.info("end:sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode()),testingTask.getInputSample().getSampleCode():"
                    + testingTask.getInputSample().getSampleCode());
                map.setSample(testingTask.getInputSample());
                map.setStorage(storage);
                map.setTask(testingTask);
                list.add(map);
                
                if (null == storage)
                {// 判断出库样本是否已经生成位置或者不在库中
                 // model.addAttribute("result", 2);
                    
                }
                if (null != storage && 1 != storage.getStatus())
                {
                    // model.addAttribute("result", 3);
                    // 查询该样本最近的出库记录
                    log.info("start:sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId()),testingTask.getInputSample().getId():"
                        + testingTask.getInputSample().getId());
                    SampleStockout closestOut = sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId());
                    log.info("end:sheetSampleStorageService.closestOutBySample(testingTask.getInputSample().getId()),testingTask.getInputSample().getId():"
                        + testingTask.getInputSample().getId());
                    if (null != closestOut)
                    {
                        map.setClosestOut(closestOut);
                    }
                    
                }
                
            }
            else
            {
                SampleStorageResponse map = new SampleStorageResponse();
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取实验编号
                String testingCode = sheetSampleStorageService.getTestingCode(testingTask.getId());
                
                testingTask.setTestingCode(testingCode);
                
                map.setSample(testingTask.getInputSample());
                
                map.setTask(testingTask);
                list.add(map);
                
            }
            
        }
        // 实验编号排序
        Collections.sort(list, new Comparator<SampleStorageResponse>()
        {
            @Override
            public int compare(SampleStorageResponse o1, SampleStorageResponse o2)
            {
                return new TestingCodeComparator().compare(o1.getTask().getTestingCode(), o2.getTask().getTestingCode());
            }
        });
        InputStream is = TestingSheetSampleStorageController.class.getResourceAsStream("/taskTemplate/sampleInOut/sample_out.xlsx");
        return sheetSampleStorageService.download(is, list, sheetSampleStorage);
        
    }
    
    @RequestMapping("/sampleOut_submit.do")
    @FormInputView
    public String sampleOut_submit(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        TestingSheet testingSheet = sheetSampleStorage.getTestingSheet();
        User user = userService.getUserByToken();
        UserDetailsModel userModel = userService.getUserByID(user.getId());
        SampleStockout stockout =
            new SampleStockout().builder()
                .operatorId(user.getId())
                .operatorName(userModel.getArchive().getName())
                .operateTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .sheetId(testingSheet)
                .build();
        
        String stockId = sheetSampleStorageService.createStockout(stockout);
        stockout.setId(stockId);
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : testingSheet.getTestingSheetTaskList())
        {
            
            if ("LIBRARY-CAP".equals(testingSheet.getSemantic()))
            {// 文库捕获特殊处理
            
                List<TestingCaptureSample> captureSampleList = sheetSampleStorageService.getTestingCaptureSampleBytaskId(sheeTask.getTestingTaskId());
                for (TestingCaptureSample captureSample : captureSampleList)
                {
                    // 去重
                    if (!fileter.containsKey(captureSample.getSample().getSampleCode()))
                    {
                        // 获取样本位置
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(captureSample.getSample().getSampleCode());
                        if (StringUtils.isNotEmpty(storage))
                        {
                            SampleStockoutDetails details =
                                new SampleStockoutDetails().builder()
                                    .sampleLocation(storage.getLocationCode())
                                    .sampleId(new TestingSample().builder().id(captureSample.getSample().getId()).build())
                                    .record(stockout)
                                    .build();
                            sheetSampleStorageService.createStockoutDetail(details);
                            
                            // 样本状态改为出库
                            storage.setStatus(2);
                            sheetSampleStorageService.updateTestingSampleStorage(storage);
                            fileter.put(captureSample.getSample().getSampleCode(), "");
                        }
                    }
                }
            }
            else
            {
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                if (StringUtils.isNotEmpty(storage))
                {
                    SampleStockoutDetails details =
                        new SampleStockoutDetails().builder()
                            .sampleLocation(storage.getLocationCode())
                            .sampleId(new TestingSample().builder().id(testingTask.getInputSample().getId()).build())
                            .record(stockout)
                            .build();
                    sheetSampleStorageService.createStockoutDetail(details);
                    
                    // 样本状态改为出库
                    storage.setStatus(2);
                    sheetSampleStorageService.updateTestingSampleStorage(storage);
                }
            }
            
        }
        sheetSampleStorage.setStatus(1);
        sheetSampleStorage.setStorageRecordId(stockId);
        sheetSampleStorageService.updateTestingSheetSampleStorage(sheetSampleStorage);
        
        // 生成入库单,DNA质检不需要生成入库单，实验完成会自动生成入库单
        if (!"DNA-QC".equals(testingSheet.getSemantic()))
        {
            sheetSampleStorageService.ceateStorageIn(testingSheet);
        }
        return redirectList(model, session, "redirect:/testingSheetSampleStorage/sampleOut_list.do");
        
    }
    
    @RequestMapping("/sampleIn_submit.do")
    public String sampleIn_submit(TestingSheetSampleStorage searcher, ModelMap model, HttpSession session)
    {
        TestingSheetSampleStorage sheetSampleStorage = sheetSampleStorageService.getTestingSheetSampleStorage(searcher.getId());
        
        User user = userService.getUserByToken();
        UserDetailsModel userModel = userService.getUserByID(user.getId());
        SampleStockin stockin =
            new SampleStockin().builder()
                .operatorId(user.getId())
                .operatorName(userModel.getArchive().getName())
                .operateTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();
        String stockId = sheetSampleStorageService.createStockin(stockin);
        stockin.setId(stockId);
        Map fileter = new HashMap<>();
        for (TestingSheetTask sheeTask : sheetSampleStorage.getTestingSheet().getTestingSheetTaskList())
        {
            
            if ("LIBRARY-CAP".equals(sheetSampleStorage.getTestingSheet().getSemantic()))
            {// 文库捕获特殊处理
            
                List<TestingCaptureSample> captureSampleList = sheetSampleStorageService.getTestingCaptureSampleBytaskId(sheeTask.getTestingTaskId());
                for (TestingCaptureSample captureSample : captureSampleList)
                {
                    // 去重
                    if (!fileter.containsKey(captureSample.getSample().getSampleCode()))
                    {
                        TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(captureSample.getSample().getSampleCode());
                        if (null != storage)
                        {
                            SampleStockinDetails details =
                                new SampleStockinDetails().builder()
                                    .sampleLocation(storage.getLocationCode())
                                    .sampleId(new TestingSample().builder().id(captureSample.getSample().getId()).build())
                                    .record(stockin)
                                    .build();
                            sheetSampleStorageService.createStockinDetail(details);
                            // 样本状态改为入库
                            storage.setStatus(1);
                            sheetSampleStorageService.updateTestingSampleStorage(storage);
                        }
                        fileter.put(captureSample.getSample().getSampleCode(), "");
                    }
                    
                }
                
            }
            else
            {
                TestingTask testingTask = testingTaskService.get(sheeTask.getTestingTaskId());
                // 获取样本位置
                TestingSampleStorage storage = sheetSampleStorageService.getTestingSampleStorage(testingTask.getInputSample().getSampleCode());
                if (null != storage)
                {
                    SampleStockinDetails details =
                        new SampleStockinDetails().builder()
                            .sampleLocation(storage.getLocationCode())
                            .sampleId(new TestingSample().builder().id(testingTask.getInputSample().getId()).build())
                            .record(stockin)
                            .build();
                    sheetSampleStorageService.createStockinDetail(details);
                    // 样本状态改为入库
                    storage.setStatus(1);
                    sheetSampleStorageService.updateTestingSampleStorage(storage);
                }
            }
            
        }
        
        sheetSampleStorage.setStatus(1);
        sheetSampleStorage.setStorageRecordId(stockId);
        sheetSampleStorageService.updateTestingSheetSampleStorage(sheetSampleStorage);
        
        return redirectList(model, session, "redirect:/testingSheetSampleStorage/sampleIn_list.do");
        
    }
    
    @Autowired
    private AppToken appToken;
    
    @RequestMapping("/searchLocation.do")
    @ResponseBody
    public String searchLocation(String sampleCode)
    {
        // 先判断改样本是否已经有存储位置
        String location = "";
        TestingSampleStorage sampleStorage = sheetSampleStorageService.getTestingSampleStorage(sampleCode);
        if (null != sampleStorage)
        {
            return sampleStorage.getLocationCode();
        }
        else
        {
            // 分配新的位置
            TestingSample testingSample = sheetSampleStorageService.getTestingSampleByCode(sampleCode);
            
            StoreContainer s = sheetSampleStorageService.getBestDevice(testingSample.getSampleTypeId(), "2");
            if (StringUtils.isNotEmpty(s))
            {
                
                // sheetSampleStorageService.searchFreeLocation(s.getId(),testingSample.getSampleTypeId(),
                // "2");
                
                location = appToken.lpopLocationRedis(s.getId(), testingSample.getSampleTypeId(), "2");
                
                if (StringUtils.isNotEmpty(location))
                {
                    sheetSampleStorageService.updateLocationState(location);
                    // 保存位置
                    sheetSampleStorageService.cteateSampleStorage(new TestingSampleStorage(sampleCode, location, null, 2));
                }
                
            }
            
            return location;
        }
    }
}
