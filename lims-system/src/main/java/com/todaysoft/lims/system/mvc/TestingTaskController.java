package com.todaysoft.lims.system.mvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.model.vo.TaskInputReagentKit;
import com.todaysoft.lims.system.model.vo.TestingSheetJddlModel;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskAbsolute;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskAbsoluteData;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.testingtask.TaskSemantic;
import com.todaysoft.lims.system.model.vo.testingtask.TestingSheetCreateRequest;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTaskAssign;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bsm.service.IReagentKitService;
import com.todaysoft.lims.system.service.IKitStorageService;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/testingTask")
public class TestingTaskController extends BaseController
{
    @Autowired
    private ITestingTaskService service;
    
    @Autowired
    private IMetadataSampleService sampleservice;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private ITaskService tasktService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IReagentKitService reagentKitService;
    
    @Autowired
    private IKitStorageService kitStorageService;
    
    /**
     * 展现页
     * @param searcher
     * @param
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/list.do")
    public String page(TestingTaskDetailRequest searcher, ModelMap model, HttpSession session, String inputType)
    {
        long startTime = System.currentTimeMillis();
        User user = userService.getUserByToken();
        String semantic = searcher.getSemantic();
        String page = "testingTask/DnaQc_list"; // 默认质检页面
        TestingTaskAssign testingTaskAssign = new TestingTaskAssign();
        List<TestingSheetJddlModel> lists = Lists.newArrayList();
        TaskSearcher s = new TaskSearcher();
        s.setSemantic(semantic);
        Task task = tasktService.getBySemantic(semantic);
        // 获取试剂盒名
        List<TaskInput> taskInputlist = task.getInputConfigs();
        /*for (TaskInput taskInput : taskInputlist)
        {
            taskInput.getReagentKitId();
        }*/
        //根据task输出配置获取试剂盒
        TaskInput taskInput = new TaskInput();
        /* taskInput.setInputId(Integer.parseInt(searcher.getInputId()));
         
         taskInput.setInputType(searcher.getInputType());
         taskInput.setTaskId(task.getId());*/
        List<TaskInputReagentKit> rlist = Collections.emptyList();//tasktService.getByTaskInput(taskInput);
        model.addAttribute("rlist", rlist);
        if (StringUtils.isNotBlank(semantic))
        {
            if (TaskSemantic.DNA_EXTRACT.equals(semantic) || TaskSemantic.CDNA_EXTRACT.equals(semantic))
            {
                page = "testingTask/testingTask_list";
                testingTaskAssign = service.paging(searcher);
                getRegentKit(task,
                    testingTaskAssign.getInputTypeId(),
                    testingTaskAssign.getInputSampleId(),
                    testingTaskAssign.getTaskId(),
                    model,
                    taskInputlist);
            }
            if (TaskSemantic.DNA_QC.equals(semantic) || TaskSemantic.CDNA_QC.equals(semantic))
            {
                testingTaskAssign = service.paging(searcher);
                getRegentKit(task,
                    testingTaskAssign.getInputTypeId(),
                    testingTaskAssign.getInputSampleId(),
                    testingTaskAssign.getTaskId(),
                    model,
                    taskInputlist);
            }
            if (TaskSemantic.LIBRARY_CRE.equals(semantic))
            {
                page = "testingTask/libraryCreation_list";
                testingTaskAssign = service.pagingLibraryCreate(searcher);
                getRegentKit(task,
                    testingTaskAssign.getInputTypeId(),
                    testingTaskAssign.getInputSampleId(),
                    testingTaskAssign.getTaskId(),
                    model,
                    taskInputlist);
            }
            if (TaskSemantic.LIBRARY_QC.equals(semantic))
            {
                page = "testingTask/WKQC_list";
                testingTaskAssign = service.pagingWKQC(searcher);
                getRegentKit(task,
                    testingTaskAssign.getInputTypeId(),
                    testingTaskAssign.getInputSampleId(),
                    testingTaskAssign.getTaskId(),
                    model,
                    taskInputlist);
            }
            if (TaskSemantic.LIBRARY_CAP.equals(semantic))
            {
                page = "testingTask/librarycatch_list";
                testingTaskAssign = service.pagingLibraryCatch(searcher);
            }
            if (TaskSemantic.POOLING.equals(semantic))
            {
                page = "testingTask/XDDL_list";
                testingTaskAssign = service.pagingXddl(searcher);
            }
            if (TaskSemantic.QT.equals(semantic))
            {
                page = "testingTask/JDDL_list";
                testingTaskAssign = service.pagingJddl(searcher);
                Pagination<TestingSheetTaskAbsolute> absoluteList = testSheetService.absolutePaging();
                List<String> codeList = new ArrayList<String>();
                for (TestingSheetTaskAbsolute t : absoluteList.getRecords())
                {
                    codeList.add(t.getSourceSampleCode());
                }
                model.addAttribute("absoluteList", absoluteList).addAttribute("codeList", JSONObject.toJSON(codeList).toString());
            }
            if (TaskSemantic.ON_TEST.equals(semantic))
            {
                page = "testingTask/onTest_list";
                testingTaskAssign = service.pagingOnTest(searcher);
            }
            if (TaskSemantic.BIOINFORMATICS_ANALYZE.equals(semantic))
            {
                page = "testingTask/bioinformaticAnalysis_list";
                testingTaskAssign = service.pagingBioInfo(searcher);
            }
            if (TaskSemantic.ANALYSIS.equals(semantic))
            {
                page = "testingTask/analysis_list";
                testingTaskAssign = service.pagingTecAnalysis(searcher);
            }
            if (TaskSemantic.REPORT_CREATE.equals(semantic))
            {
                page = "testingTask/reportCreate_list";
                testingTaskAssign = service.pagingReportCreate(searcher);
            }
            if (TaskSemantic.REPORT_CHECK.equals(semantic))
            {
                page = "testingTask/reportCheck_list";
                testingTaskAssign = service.pagingReportCheck(searcher);
            }
            if (TaskSemantic.REPORT_MAIL.equals(semantic))
            {
                page = "testingTask/reportMail_list";
                testingTaskAssign = service.pagingReportMail(searcher);
            }
        }
        // 获取所用试剂盒
        List<TaskInput> taskList = task.getInputConfigs();
        /*	if (null != task.getTaskInputList() && !task.getTaskInputList().isEmpty()) {
        		// 过滤同一种任务类型
        		for (int i = taskList.size() - 1; i >= 0; i--) {
        			if (!(searcher.getInputId().equals(taskList.get(i).getInputId()))
        					|| !taskList.get(i).getInputType().equals(searcher.getSampleType())) {
        				task.getTaskInputList().remove(i);
        			}
        			} 
        		
        		KitStorage storage = new KitStorage();
        		ReagentKit kit = new ReagentKit();
        		if(Collections3.isNotEmpty(taskList)){
        		kit.setId(task.getTaskInputList().get(0).getReagentKitId());
        		storage.setReagentKit(kit);
        		}
        		Integer totalCount = kitStorageService.countByKitId(storage);
        		if (null == totalCount) {
        			totalCount = 0;
        		}
        		if (totalCount.intValue() < testingTaskAssign.getTestingTaskDetailList().size()) {// 试剂盒所剩反应次数不够
        			model.addAttribute("reactionNumInfo", false);//反应次数不够
        		} else {
        			int count = 0;// 计算反应次数需要需要到的试剂盒
        			List<KitStorage> userList = new ArrayList<KitStorage>();
        			List<KitStorage> storageList = kitStorageService.list(storage);
        			for (KitStorage st : storageList) {
        				if (count < testingTaskAssign.getTestingTaskDetailList().size()
        						&& (count + st.getReactionNum()) < testingTaskAssign.getTestingTaskDetailList().size()) {
        					count += st.getReactionNum();
        					userList.add(st);
        				} else if (count < testingTaskAssign.getTestingTaskDetailList().size()
        						&& (count + st.getReactionNum()) >= testingTaskAssign.getTestingTaskDetailList().size()) {
        					st.setReactionNum(testingTaskAssign.getTestingTaskDetailList().size() - count);
        					userList.add(st);
        					break;
        				} else {
        					break;
        				}
        			}
        			testingTaskAssign.setKitStorageList(userList);
        		}
        	}*/
        model.addAttribute("pagination", testingTaskAssign).addAttribute("user", user);
        model.addAttribute("searcher", searcher);
        long endTime = System.currentTimeMillis();
        float excTime = (float)(endTime - startTime) / 1000;
        System.out.println("执行时间：" + excTime + "s");
        return page;
    }
    
    //获取试剂盒，和试剂默认输出量，输入量
    private void getRegentKit(Task task, String taskInputId, Integer taskInputType, Integer taskId, ModelMap model, List<TaskInput> taskInputlist)
    {
        if (taskInputlist.size() > 0)
        {
            for (TaskInput taskInput : taskInputlist)
            {
                /*if (((taskInput.getInputType()).equals(taskInputId)) && (taskInput.getInputId() == taskInputType))
                {
                    Double InputQuantity = Double.parseDouble(taskInput.getInputQuantity());
                    Double outputQuantity = task.getOutputQuantity();
                    String unit = taskInput.getUnit();
                    //            	 ReagentKit reagentKit= reagentKitService.getReagentKit(taskInput.getReagentKitId());
                    model.addAttribute("reagentKit", null)
                        .addAttribute("InputQuantity", InputQuantity)
                        .addAttribute("outputQuantity", outputQuantity)
                        .addAttribute("unit", unit)
                        .addAttribute("sendDate", dateToStrLong());
                }
                else
                {
                    model.addAttribute("sendDate", dateToStrLong());
                }*/
            }
        }
        else
        {
            model.addAttribute("sendDate", dateToStrLong());
        }
    }
    
    /**
     * 下达任务
     * @param
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/createTestTask")
    public String createTestTask(TestingSheetCreateRequest tscr, ModelMap model, HttpSession session)
    {
        TestingSheetCreateRequest request = service.doSome(tscr);
        String semantic = request.getSemantic();
        String page = "";
        if (StringUtils.isNotBlank(semantic))
        {
            if (TaskSemantic.DNA_EXTRACT.equals(semantic))
            {
                service.create(request);
                page = "redirect:/process/DNA_list.do?type=DNA_EXTRACT";
            }
            if (TaskSemantic.CDNA_EXTRACT.equals(semantic))
            {
                service.create(request);
                page = "redirect:/process/DNA_list.do?type=RNA_EXTRACT";
            }
            if (TaskSemantic.DNA_QC.equals(semantic) || TaskSemantic.CDNA_QC.equals(semantic))
            {
                service.createDNAQC(request);
                page = "redirect:/process/QCList.do?type=DNA_QC";
            }
            if (TaskSemantic.LIBRARY_CRE.equals(semantic))
            {
                service.createLibrary(request);
                page = "redirect:/process/wkList.do?type=WK_CREATE";
            }
            if (TaskSemantic.LIBRARY_QC.equals(semantic))
            {
                service.createWKQC(request);
                page = "redirect:/process/wkQCList.do?type=WK_QC";
            }
            if (TaskSemantic.LIBRARY_CAP.equals(semantic))
            {
                service.createLibraryCatch(request);
                page = "redirect:/process/libaryCatchList.do?type=LIBRARY_CATCH";
            }
            if (TaskSemantic.POOLING.equals(semantic))
            {
                service.createXddl(request);
                page = "redirect:/process/xddlList.do?type=XDDL";
            }
            if (TaskSemantic.QT.equals(semantic))
            {
                service.createJddl(request);
                page = "redirect:/process/jddlList.do?type=JDDL";
            }
            if (TaskSemantic.ON_TEST.equals(semantic))
            {
                service.createOnTest(request);
                page = "redirect:/process/onTestList.do?type=ON_TEST";
            }
            if (TaskSemantic.BIOINFORMATICS_ANALYZE.equals(semantic))
            {
                service.createBioInfo(request);
                page = "redirect:/process/bioinformaticAnalysis.do?type=BIOINFORMATICS_ANALYZE";
            }
            if (TaskSemantic.ANALYSIS.equals(semantic))
            {
                service.createTecAnalysis(request);
                page = "redirect:/process/tecAnalysisList.do?type=ANALYSIS";
            }
            if (TaskSemantic.REPORT_CREATE.equals(semantic))
            {
                service.createReportCreate(request);
                page = "redirect:/process/reportCreateList.do?type=REPORT_CREATE";
            }
            if (TaskSemantic.REPORT_CHECK.equals(semantic))
            {
                service.createReportCheck(request);
                page = "redirect:/process/reportCheckList.do?type=REPORT_CHECK";
            }
            if (TaskSemantic.REPORT_MAIL.equals(semantic))
            {
                service.createReportMail(request);
                page = "redirect:/process/reportMailList.do?type=REPORT_MAIL";
            }
        }
        model.addAttribute("result", "下达DNA任务成功,请执行后续操作！");
        session.setAttribute("result", "下达DNA任务成功,请执行后续操作！");
        return page;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getSampleLocation")
    public String getSampleLocation(String sampleId)
    {
        String containerType = sampleservice.get(sampleId).getStorageType();
        return service.getSampleLocation(containerType);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getTestingSheetJddlModel.do")
    public TestingSheetTaskAbsolute getTestingSheetJddlModel(String testCode)
    {
        if (testCode != null)
        {
            Pagination<TestingSheetTaskAbsolute> absoluteList1 = testSheetService.absolutePaging();
            List<TestingSheetTaskAbsolute> absoluteList = absoluteList1.getRecords();
            List<Double> wkSizeList = new ArrayList<Double>();
            List<String> unitConversionList = new ArrayList<String>();
            TestingSheetTaskAbsolute tsjModel = new TestingSheetTaskAbsolute();
            // List<TestingSheetJddlModel> tsjmList =
            // testingTaskService.getJddlList();
            for (TestingSheetTaskAbsolute t : absoluteList)
            {
                if (t.getSourceSampleCode().endsWith(testCode))
                {
                    tsjModel = t;
                    List<TestingSheetTaskAbsoluteData> absoluteDataList = tsjModel.getAbsoluteDataList();
                    for (TestingSheetTaskAbsoluteData ts : absoluteDataList)
                    {
                        wkSizeList.add(ts.getWkSize());
                        unitConversionList.add(ts.getUnitConversion());
                    }
                    tsjModel.setWkSizeList(wkSizeList);
                    tsjModel.setUnitConversionList(unitConversionList);
                    break;
                }
                else
                {
                    tsjModel = null;
                }
            }
            return tsjModel;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 捕获号唯一性教研
     */
    @ResponseBody
    @RequestMapping("/validateCatchCode.do")
    public boolean validateCatchCode(TestingSheetCreateRequest request)
    {
        return service.validateCatchCode(request);
    }
    
    /**
       * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
       * 
       * @param
       * @return
       */
    public static String dateToStrLong()
    {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(now);
        return dateString;
    }
}
