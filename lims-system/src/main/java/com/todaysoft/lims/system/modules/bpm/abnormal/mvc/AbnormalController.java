package com.todaysoft.lims.system.modules.bpm.abnormal.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.*;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.SangerVerifyRecordModel;
import com.todaysoft.lims.system.util.Collections3;
import com.todaysoft.lims.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.modules.bpm.abnormal.service.IAbnormalService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.impl.TestingTaskService;

@Controller
@RequestMapping("/bpm/abnormal")
public class AbnormalController extends BaseController
{
    @Autowired
    private IAbnormalService service;
    
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private TestingTaskService testingTaskService;

    @Autowired
    private IBiologyAnalyService biologyAnalyService;
    
    @RequestMapping("/tasks.do")
    public String tasks(AbnormalTaskSearcher searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }
        
        if (null == pageSize)
        {
            pageSize = 10;
        }
        List<Task> taskList = taskService.list(new TaskSearcher());
        Pagination<AbnormalTask> pagination = service.paging(searcher, pageNo, pageSize);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (AbnormalTask abnormal:pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(abnormal.getTestingMethods()) && "PCR-NGS验证".equals(abnormal.getTestingMethods()))
                {
                    if (StringUtils.isNotEmpty(abnormal.getId()))
                    {
                        List<TestingSchedule> schedules = biologyAnalyService.getSchedulesByTaskId(abnormal.getId());//通过任务找到对应的流程
                        if (Collections3.isNotEmpty(schedules))
                        {
                            String allSiteCode = "";
                            for (TestingSchedule schedule:schedules)
                            {
                                if ( schedule.getProccessState()!=2 && StringUtils.isNotEmpty(schedule.getVerifyKey()))//只有这个任务对应的流程，没有取消才显示它的位点
                                {
                                    SangerVerifyRecordModel record = biologyAnalyService.getSangerRecordByVerifyKey(schedule.getVerifyKey());
                                    String siteCode = getSiteCode(record.getCode());
                                    allSiteCode = allSiteCode + siteCode + "|";
                                }
                            }
                            if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(allSiteCode))
                            {
                                allSiteCode = allSiteCode.substring(0,allSiteCode.length() -1);
                                abnormal.setProducts(allSiteCode);
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("taskList", taskList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/abnormal/abnormal_task_list";
    }

    private String getSiteCode(String code) //  S081401_C1808132A_PRDM16-chr1-3329182
    {
        String[] codeArr = code.split("_");
        String geneSite = codeArr[2];
        String[] siteArr = geneSite.split("-");
        String siteCode = siteArr[1] +"-" +siteArr[2];
        return  siteCode;
    }
    @RequestMapping(value = "/solve.do", method = RequestMethod.GET)
    @FormInputView
    public String solve(String id,String semantic, ModelMap model)
    {
        AbnormalTaskDetails details = service.getDetails(id,semantic);
        model.addAttribute("data", details);
        List<AbnormalHistoryModel> abnormals = service.getAbnormalHistoryByTaskId(id,semantic);
        model.addAttribute("abnormals", abnormals);
        return "bpm/abnormal/abnormal_solve";
    }
    
    @RequestMapping(value = "/solve.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String solve(AbnormalTaskSolveRequest request, ModelMap model, HttpSession session)
    {
        service.solve(request);
        return redirectList(model, session);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        model.addAttribute("pageSize", session.getAttribute("s-pageSize"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bpm/abnormal/tasks.do";
    }
    
    @RequestMapping(value = "/history_list.do", method = RequestMethod.GET)
    public String history(AbnormalSolveRecord searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        List<Task> taskList = taskService.list(new TaskSearcher());
        
        model.addAttribute("taskList", taskList);
        
        Pagination<AbnormalSolveRecord> pagination = service.history(searcher, pageNo, 10);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (AbnormalSolveRecord record: pagination.getRecords())
            {
                    String allSiteCode = "";
                    for (AbnormalSolveSheduleRecord sheduleRecord : record.getSheduleList())
                    {
                        if (StringUtils.isNotEmpty(sheduleRecord.getMethodName()) && sheduleRecord.getMethodName().equals("PCR-NGS验证"))
                        {
                            if (StringUtils.isNotEmpty(sheduleRecord.getVerifyKey())&& StringUtils.isNotEmpty(sheduleRecord.getActiveTask()))
                            {
                                SangerVerifyRecordModel recordModel = biologyAnalyService.getSangerRecordByVerifyKey(sheduleRecord.getVerifyKey());
                                String siteCode = getSiteCode(recordModel.getCode());
                                allSiteCode = allSiteCode + siteCode + "|";
                            }
                        }
                    }
                    if (StringUtils.isNotEmpty(allSiteCode))
                    {
                        allSiteCode = allSiteCode.substring(0,allSiteCode.length() -1);
                        record.getTestingTask().setProductName(allSiteCode);
                    }
            }
        }
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/abnormal/abnormal_history_list";
    }
    
    @RequestMapping(value = "/history_show.do", method = RequestMethod.GET)
    public String historyShow(String id, ModelMap model, HttpSession session)
    {
        
        AbnormalSolveRecord detail = service.historyShow(id);
        model.addAttribute("data", detail);
        return "bpm/abnormal/abnormal_history_show";
        
    }
    
    @RequestMapping("/generate_redundant_fields.do")
    public void generateRedundantFields(String semantics, ModelMap model, HttpSession session)
    {
        
       service.generateRedundantFields(semantics);
    }

    @RequestMapping("/generate_redundant_task")
    public void generate_redundant_task(String id, ModelMap model, HttpSession session)
    {

        service.generateRedundantTask(id);
    }
 
    @RequestMapping(value = "/abnormal_single.do", method = RequestMethod.GET)
    public String abnormalHistorySingle(String taskId, ModelMap model, HttpSession session)
    {
        AbnormalHistoryModel abmodel = service.getAbnormalHistorySingle(taskId);
        model.addAttribute("abnormalSingle", abmodel);
        return "order/abnormal_single_show";
        
    }
    
    
}
