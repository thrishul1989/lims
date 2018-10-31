package com.todaysoft.lims.system.modules.bpm.mvc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.todaysoft.lims.system.model.searcher.SampleExperimentRequest;
import com.todaysoft.lims.system.model.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.model.TestTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.service.ITestingTestTasksService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class TestingTestTasksController extends BaseController
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestingTestTasksService testTasksService;

    @Autowired
    private ITaskService taskService;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    
    //实验列表
    @RequestMapping("/test_tasks.do")
    public String testTasks(TestTaskSearcher searcher, ModelMap model)
    {
        TestTaskSearcher u = new TestTaskSearcher();
        AuthorizedUser user = userService.getByToken();
        searcher.setTesterId(user.getId());
        List<TestTask> tasks = testTasksService.testTasks(searcher);
        model.addAttribute("tasks", tasks).addAttribute("searcher", searcher);
        return "bpm/process/test_list";
    }

    @RequestMapping("/testingReportForm/main.do")
    public String orderFinancialReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception
    {
        List<Task> taskList = taskService.list(new TaskSearcher());
        model.addAttribute("taskList", taskList);
        return "bpm/testing_report_form/testing_report_main";
    }

    //样本实验统计表
    @RequestMapping("/exportSampleTesting.do")
    @ResponseBody
    public String exportSampleTesting(SampleExperimentRequest searcher, HttpSession session) throws Exception
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setReportTaskId(sessionId);
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl =  testTasksService.exportSampleTesting(searcher);
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }

    //工作任务统计表
    @RequestMapping("/exportTaskSheet.do")
    @ResponseBody
    public String exportTaskSheet(SampleExperimentRequest searcher, HttpSession session) throws Exception
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setReportTaskId(sessionId);

        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = testTasksService.exportTaskSheet(searcher);
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }

    //样本成功率统计表
    @RequestMapping("/exportTaskSucessRate.do")
    @ResponseBody
    public String exportTaskSucessRate(SampleExperimentRequest searcher, HttpSession session) throws Exception
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setReportTaskId(sessionId);

        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = testTasksService.exportTaskSucessRate(searcher);
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }

    //异常任务处理统计表
    @RequestMapping("/exportFailTasks.do")
    @ResponseBody
    public String exportFailTasks(SampleExperimentRequest searcher, HttpSession session) throws Exception
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setReportTaskId(sessionId);

        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = testTasksService.exportFailTasks(searcher);
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }

    //报表导出异步查询结果
    @RequestMapping("/searchExportResult.do")
    @ResponseBody
    public String searchExportResult(String sessionId, HttpSession session) throws Exception
    {
        String result = "";
       if(StringUtils.isNotEmpty(sessionId) && StringUtils.isNotEmpty(session.getAttribute(sessionId)))
       {
           result = session.getAttribute(sessionId).toString();
           session.removeAttribute(sessionId);
       }
        return result;
    }
}
