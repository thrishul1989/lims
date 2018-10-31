package com.todaysoft.lims.system.modules.bpm.extrasample.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderSampleDetails;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalTaskDetails;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTask;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskDetails;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskHandleRequest;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.impl.TestingTaskService;

@Controller
@RequestMapping("/bpm/extraSample")
public class ExtraSampleTaskController extends BaseController
{
    @Autowired
    private IExtraSampleService service;
    
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private TestingTaskService testingTaskService;

    @Autowired
    private IUserService userService;
    
    @RequestMapping("/tasks.do")
    public String tasks(ExtraSampleTaskSearcher searcher, Integer pageNo, Integer pageSize, ModelMap model, HttpSession session)
    {
        if (null == pageNo)
        {
            pageNo = 1;
        }
        
        if (null == pageSize)
        {
            pageSize = 10;
        }
        Pagination<ExtraSampleTask> pagination = service.paging(searcher, pageNo, pageSize);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ExtraSampleTask est : pagination.getRecords())
            {
                String orderId = orderService.getIdByCode(est.getOrderCode());
                if (StringUtils.isNotEmpty(orderId))
                {

                    String projectManager = orderService.getById(orderId).getProjectManager();
                    if (StringUtils.isNotEmpty(projectManager))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(projectManager);
                        est.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }

        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-pageSize", pageSize);
        session.setAttribute("s-searcher", searcher);
        return "bpm/extraSample/extraSample_task_list";
    }
    
    @RequestMapping(value = "/handle.do", method = RequestMethod.GET)
    @FormInputView
    public String handle(String id, ModelMap model)
    {
        ExtraSampleTaskDetails details = service.getDetails(id);
        model.addAttribute("data", details);
        return "bpm/extraSample/extraSample_handle";
    }
    
    @RequestMapping(value = "/handle.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String solve(ExtraSampleTaskHandleRequest request, ModelMap model, HttpSession session)
    {
        service.handle(request);
        return redirectList(model, session);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        model.addAttribute("pageSize", session.getAttribute("s-pageSize"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bpm/extraSample/tasks.do";
    }

    @RequestMapping(value = "/history_show.do", method = RequestMethod.GET)
    public String historyShow(String id, ModelMap model, HttpSession session)
    {
        ExtraSampleTaskDetails details = service.getDetails(id);
        model.addAttribute("data", details);
        return "bpm/extraSample/extraSample_handle_show";
    }
    
}
