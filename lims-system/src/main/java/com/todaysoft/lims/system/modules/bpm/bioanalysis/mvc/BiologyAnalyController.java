package com.todaysoft.lims.system.modules.bpm.bioanalysis.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.SangerVerifyRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BioDivisionListRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalySubmitRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalyTask;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalyTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotatioListRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotationTask;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionMonitor;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionSheetSample;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionStartModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyDivisionTask;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyFamilyAnnotatioListRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyReAnnotationRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.util.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing")
public class BiologyAnalyController extends BaseController
{
    @Autowired
    private IBiologyAnalyService service;
    
    @Resource(name = "exportSampleDataView")
    private View exportSampleDataView;
    
    @Resource(name = "exportDetailsDataView")
    private View exportDetailsDataView;
    
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @RequestMapping("/biAnalysis_tasks.do")
    public String cdnaExtractTasks(BiologyAnalyTaskSearcher searcher, ModelMap model)
    {
        List<BiologyAnalyTask> tasks = service.getTaskList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/bioanalysis_list";
    }
    
    @RequestMapping(value = "/bioanalysis_submit.do", method = RequestMethod.GET)
    @FormInputView
    public String getBioanalysisSubmitModel(String id, ModelMap model)
    {
        BiologyAnalySubmitModel data = service.getSubmitModel(id);
        model.addAttribute("data", data);
        return "bpm/test/biAnalysis_test_form";
    }
    
    @RequestMapping("/biology-analy/export/details_data.jsp")
    public ModelAndView exportAnalyData(String id, ModelMap model)
    {
        model.addAttribute("id", id);
        return new ModelAndView(exportDetailsDataView, model);
    }
    
    @RequestMapping("/biology-analy/export/sample_data.jsp")
    public ModelAndView exportSampleData(String id, ModelMap model)
    {
        model.addAttribute("id", id);
        return new ModelAndView(exportSampleDataView, model);
    }
    
    @RequestMapping(value = "/bioanalysis_submit.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String submitBioanalysis(BiologyAnalySubmitRequest request)
    {
        service.submitBioanalysis(request);
        return "redirect:/testing/biAnalysis_tasks.do";
    }
    
    // 数据拆分列表
    @RequestMapping(value = "/biologyDivisionList.do")
    public String getBiologyDivision(BioDivisionListRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        PageInfo<BiologyDivisionTask> pagination = service.getBiologyDivisionList(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "bpm/biology/division_task_list";
    }
    
    @RequestMapping(value = "/biologyDivisionOperate.do")
    @FormInputView
    public String biologyOperate(String id, ModelMap model)
    {
        BiologyDivisionStartModel data = service.getSamplesByTaskId(id);
        
        if (null != data && Collections3.isNotEmpty(data.getSheetSampleList()))
        {
            // 原有的
            List<BiologyDivisionSheetSample> originalList = data.getSheetSampleList().stream().filter(x -> !x.getIsAdd()).collect(Collectors.toList());
            // 后增加的
            List<BiologyDivisionSheetSample> laterList = data.getSheetSampleList().stream().filter(x -> x.getIsAdd()).collect(Collectors.toList());
            
            model.addAttribute("originalList", originalList);
            model.addAttribute("laterList", laterList);
        }
        
        model.addAttribute("squencingCode", data.getSquencingCode());
        model.addAttribute("taskId", id);
        return "bpm/biology/task_division_operate";
    }
    
    @RequestMapping(value = "/getSampleListBySquencingCode.do")
    public String getSampleListBySquencingCode(String code, ModelMap model)
    {
        BiologyDivisionStartModel data = service.getSampleListBySquencingCode(code);
        
        model.addAttribute("originalList", data.getSheetSampleList());
        model.addAttribute("squencingCode", data.getSquencingCode());
        return "bpm/biology/task_division_sample_info";
    }
    
    @RequestMapping(value = "/biologyDivisionInfo.do")
    public String biologyDivisionInfo(String id, ModelMap model)
    {
        BiologyDivisionStartModel data = service.getSamplesByTaskId(id);
        if (null != data && Collections3.isNotEmpty(data.getSheetSampleList()))
        {
            // 原有的
            List<BiologyDivisionSheetSample> originalList = data.getSheetSampleList().stream().filter(x -> !x.getIsAdd()).collect(Collectors.toList());
            // 后增加的
            List<BiologyDivisionSheetSample> laterList = data.getSheetSampleList().stream().filter(x -> x.getIsAdd()).collect(Collectors.toList());
            
            model.addAttribute("originalList", originalList);
            model.addAttribute("laterList", laterList);
        }
        
        model.addAttribute("squencingCode", data.getSquencingCode());
        return "bpm/biology/task_division_info";
    }
    
    @RequestMapping(value = "/biologyStartDivision.do", method = RequestMethod.POST)
    @FormInputView
    public String getBioanalysisSubmitModel(BiologyDivisionStartModel data, ModelMap model)
    {
        service.startBiologyDivision(data);
        return "redirect:/testing/biologyDivisionList.do";
    }
    
    @RequestMapping(value = "/getBiologyDivisionFailRecords.do")
    public String biologyDivisionFailRecords(String id, ModelMap model)
    {
        List<BiologyDivisionMonitor> records = service.getBiologyDivisionFailRecords(id);
        model.addAttribute("records", records);
        return "bpm/biology/task_division_fail_info";
    }
    
    @RequestMapping(value = "/biologyDivision/callBack")
    @ResponseBody
    public void biologyDivisionCallBack(@RequestBody String info, ModelMap model)
    {
        if (StringUtils.isNotEmpty(info))
        {
            BiologyDivisionCallBackModel data = JsonUtils.asObject(info, BiologyDivisionCallBackModel.class);
            if (null != data)
            {
                service.biologyDivisionCallBack(data);
            }
        }
    }
    
    @RequestMapping(value = "/biologyAnnotation/callBack")
    @ResponseBody
    public void biologyAnnotationCallBack(@RequestBody BiologyReAnalysisDataResponse data, ModelMap model)
    {
        if (null != data)
        {
            service.biologyAnnotationCallBack(data);
        }
    }
    
    @RequestMapping("/bioAnnotationTab.do")
    public String tabList(BiologyAnnotatioListRequest searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        return "bpm/biology/annotation_tab";
    }
    
    // 单例
    @RequestMapping(value = "/biologyAnnotationList.do")
    public String getBiologyAnnotationList(BiologyAnnotatioListRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        PageInfo<BiologyAnnotationTask> pagination = service.getBiologyAnnotationList(searcher, pageNo, 10);
        if(Collections3.isNotEmpty(pagination.getList()))
        {
            for (BiologyAnnotationTask annotationTask:pagination.getList())
            {
                if (StringUtils.isNotEmpty(annotationTask.getMethodId()) && "PCR-NGS".equals(annotationTask.getMethodId()))
                {
                    if (StringUtils.isNotEmpty(annotationTask.getId()))
                    {
                      List<TestingSchedule> schedules = service.getSchedulesByTaskId(annotationTask.getId());
                      if (Collections3.isNotEmpty(schedules))
                      {
                          String allSiteCode = "";
                          for (TestingSchedule schedule:schedules)
                          {
                              if (schedule.getProccessState()!=2)//只有这个任务对应的流程，没有取消才显示它的位点
                              {
                                  SangerVerifyRecordModel record = service.getSangerRecordByVerifyKey(schedule.getVerifyKey());
                                  String siteCode = getSiteCode(record.getCode());
                                  allSiteCode = allSiteCode + siteCode + "|";
                              }
                          }
                          if (StringUtils.isNotEmpty(allSiteCode))
                          {
                              allSiteCode = allSiteCode.substring(0,allSiteCode.length() -1);
                              annotationTask.setProductName(allSiteCode);
                          }
                      }
                    }
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "bpm/biology/annotation_task_list";
    }

    private String getSiteCode(String code) //  S081401_C1808132A_PRDM16-chr1-3329182
    {
        String[] codeArr = code.split("_");
        String geneSite = codeArr[2];
        String[] siteArr = geneSite.split("-");
        String siteCode = siteArr[1] +"-" +siteArr[2];
        return  siteCode;
    }

    @RequestMapping(value = "/solveBiologyAnnotation.do")
    @ResponseBody
    public Map<String, Object> biologyAnnotationOperate(BiologyReAnnotationRequest request, ModelMap model)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        service.biologyAnnotationOperate(request);
        result.put("success", true);
        return result;
    }
    
    //重新单例分析文件
    @RequestMapping(value = "/reAnalysisAnnotaionFile.do")
    public String reAnalysisAnnotaionFile(String familyId, String taskId, Integer type, ModelMap model)
    {
        //1.单例分析页面  2.家系分析列表
        
        String returnUrl = "";
        if (1 == type.intValue())
        {
            returnUrl = "redirect:/testing/biologyAnnotationList.do";
        }
        else
        {
            returnUrl = "redirect:/testing/biologyFamilyAnnotationInfo.do?id=" + familyId;
        }
        //解析异步做 先更新状态为解析中
        service.updateTaskState(taskId);
        
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                service.reAnalysisAnnotaionFile(taskId);
            }
        });
        return returnUrl;
    }
    
    //重新家系分析文件
    @RequestMapping(value = "/reFamilyAnalysisAnnotaionFile.do")
    public String reFamilyAnalysisAnnotaionFile(String taskId, ModelMap model)
    {
        //解析异步做 先更新状态为解析中
        service.updateFamilyTaskState(taskId);
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                service.reFamilyAnalysisAnnotaionFile(taskId);
            }
        });
        return "redirect:/testing/biologyAnnotationFamilyList.do";
    }
    
    // 家系注释列表
    @RequestMapping(value = "/biologyAnnotationFamilyList.do")
    public String getBiologyAnnotationFamilyList(BiologyFamilyAnnotatioListRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        PageInfo<BiologyAnnotationFamilyTask> pagination = service.getBiologyAnnotationFamilyList(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "bpm/biology/annotation_family_task_list";
    }
    
    //查看家系情况
    @RequestMapping(value = "/biologyFamilyAnnotationInfo.do")
    public String getBiologyFamilyAnnotationInfo(String id, Integer status, ModelMap model)
    {
        List<BiologyAnnotationTask> taskList = service.getTaskInfoByFamily(id);
        model.addAttribute("taskList", taskList);
        model.addAttribute("familyAnnotationId", id);
        model.addAttribute("status", status);
        return "bpm/biology/annotation_family_task_info";
    }
    
    //启动家系分析
    @RequestMapping(value = "/startFamilyAnnotation.do")
    @ResponseBody
    public void startFamilyAnnotation(String ids, String familyAnnotationId, ModelMap model)
    {
        service.startFamilyAnnotation(ids, familyAnnotationId);
    }
    
}
