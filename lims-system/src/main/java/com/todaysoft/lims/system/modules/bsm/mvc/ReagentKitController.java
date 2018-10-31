package com.todaysoft.lims.system.modules.bsm.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Reagent;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.ReagentKitReagent;
import com.todaysoft.lims.system.model.vo.ReagentKitTask;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.modules.bsm.service.IReagentKitService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/reagentKit")
public class ReagentKitController extends BaseController
{
    
    @Autowired
    private IReagentKitService reagentService;
    
    @Autowired
    private ITaskService taskService;
    
    /**
     * 试剂盒主数据
     * */
    @RequestMapping(value = "/list.do")
    public String getReagentKitList(ReagentKit searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<ReagentKit> pagination = reagentService.paging(searcher, pageNo, 10);
        for (ReagentKit rk : pagination.getRecords())
        {
            for (ReagentKitTask task : rk.getKitTaskList())
            {
                Task t = taskService.get(task.getTaskConfigId());
                if (null != t)
                {
                    task.setTask(t);
                }
            }
        }
        TaskSearcher taskSearcher = new TaskSearcher();
        List<Task> taskList = taskService.list(taskSearcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("taskList", taskList);
        return "reagentkit/reagentkit_list";
    }
    
    /**
     * 新增试剂盒page
     * */
    @RequestMapping(value = "/createReagentKit.do", method = RequestMethod.GET)
    public String createPage(ModelMap model)
    {
        try
        {
            List<Task> data = taskService.list(new TaskSearcher());
            model.addAttribute("task", data);
        }
        catch (Exception e)
        {
            model.addAttribute("task", Collections.emptyList());
        }
        ReagentKit reagentKit = new ReagentKit();
        model.addAttribute("reagentKit", reagentKit);
        return "reagentkit/reagentkit_form";
    }
    
    /**
     * 编辑试剂盒page
     * */
    @RequestMapping(value = "/reagentKitModify.do", method = RequestMethod.GET)
    public String modifyPage(String id, ModelMap model)
    {
        ReagentKit data = reagentService.getReagentKit(id);
        model.addAttribute("reagentKit", data);
        
        try
        {
            List<Task> task = taskService.list(new TaskSearcher());
            model.addAttribute("task", task);
        }
        catch (Exception e)
        {
            model.addAttribute("task", Collections.emptyList());
        }
        
        return "reagentkit/reagentkit_form";
    }
    
    /**
     * 新增修改试剂盒
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/reagentKitCreat.do")
    public String reagentKitCreat(ReagentKit request, ModelMap model, HttpSession session)
    {
        // 试剂盒默认单位盒
        request.setUnit("9");
        List<ReagentKitTask> ReagentKitTaskList = new ArrayList<ReagentKitTask>();
        for (String taskId : request.getTaskIds())
        {
            ReagentKitTask reagentTask = new ReagentKitTask();
            /* Task task = new Task();
             task.setId(taskId);*/
            
            reagentTask.setTaskConfigId(taskId);
            
            ReagentKitTaskList.add(reagentTask);
        }
        //如果是自配试剂
        List<ReagentKitReagent> reagentKitReagentList = new ArrayList<ReagentKitReagent>();
        if ("2".equals(request.getType()))
        {
            
            for (String reagentId : request.getReagentIds())
            {
                ReagentKitReagent reagentKitReagent = new ReagentKitReagent();
                Reagent reagent = new Reagent();
                reagent.setId(reagentId + "");
                
                reagentKitReagent.setReagent(reagent);
                
                reagentKitReagentList.add(reagentKitReagent);
            }
            
        }
        
        request.setKitTaskList(ReagentKitTaskList);
        request.setReagentKitReagentList(reagentKitReagentList);
        if (StringUtils.isEmpty(request.getId()))
        {
            
            reagentService.createReagentKit(request);
        }
        else
        {
            reagentService.modify(request);
        }
        
        return redirectList(model, session, "redirect:/reagentKit/list.do");
    }
    
    /**
     * 删除试剂盒
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delete.do")
    public String deleteReagentKit(String id, ModelMap model, HttpSession session)
    {
        reagentService.deleteReagentKit(id);
        return redirectList(model, session, "redirect:/reagentKit/list.do");
    }
    
    /**
     * 查看试剂盒
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view.do")
    public String viewReagentKit(String id, ModelMap model)
    {
        ReagentKit entity = reagentService.getReagentKit(id);
        List<ReagentKitTask> list = entity.getKitTaskList();
        for (ReagentKitTask reagentKitTask : list)
        {
            String str = reagentKitTask.getTaskConfigId();
            if (StringUtils.isNotEmpty(str))
            {
                Task task = taskService.get(str);
                reagentKitTask.setTask(task);
            }
        }
        model.addAttribute("entity", entity);
        return "reagentkit/reagentkit_show";
    }
    
    /**
     * 模糊匹配试剂盒
     * */
    @RequestMapping(value = "/reagentKitSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> reagentKitSelect(ReagentKit key)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        Pagination<ReagentKit> reagentKit = reagentService.selectReagentKit(key, 1, 10);
        map.put("value", reagentKit.getRecords());
        return map;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(ReagentKit search)
    {
        return reagentService.validate(search);
    }
    
    /**
     * 判断该试剂盒是否有关联的任务
     */
    @ResponseBody
    @RequestMapping("/validateTask.do")
    public Map<String, String> validateTask(String id)
    {
        ReagentKit entity = reagentService.getReagentKit(id);
        List<ReagentKitTask> kitTaskList = entity.getKitTaskList();
        Map<String, String> map = Maps.newHashMap();
        
        List<String> tasks = Lists.newArrayList();
        String taskName = "";
        for (ReagentKitTask rkt : kitTaskList)
        {
            Task task = taskService.get(rkt.getTaskConfigId());
            List<TaskInput> taskInputList = task.getInputConfigs();
            for (TaskInput taskInput : taskInputList)
            {
                if (id.equals(taskInput.getReagentKitId()) && !tasks.contains(task.getName()))
                {
                    tasks.add(task.getName());
                }
            }
        }
        for (int i = 0; i < tasks.size(); i++)
        {
            if (i == 0)
            {
                taskName = tasks.get(0);
            }
            else
            {
                taskName = taskName + "," + tasks.get(i);
            }
        }
        
        if (Collections3.isEmpty(tasks))
        {
            map.put("flag", "true");
        }
        else
        {
            map.put("flag", "false");
        }
        map.put("name", taskName);
        return map;
    }
    
    @RequestMapping("/enable.do")
    public String enable(String id, ModelMap model, HttpSession session)
    {
        ReagentKit reagentKit = reagentService.getReagentKit(id);
        reagentKit.setDeleted(false);
        reagentService.modify(reagentKit);
        return redirectList(model, session, "redirect:/reagentKit/list.do");
    }
}
