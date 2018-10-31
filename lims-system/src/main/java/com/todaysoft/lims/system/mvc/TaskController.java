package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.InputOutputModel;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ReagentInfo;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bsm.service.IReagentKitService;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentListRequest;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.adapter.request.ExperimentProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.system.service.adapter.request.TaskConfigRequest;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController
{
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IReagentKitService reagentKitService;
    
    @Autowired
    private IMetadataSampleService sampleService;
    
    @RequestMapping(value = "/list.do")
    public String list(TaskSearcher searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Task> pagination = taskService.paging(searcher, pageNo, 10);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        return "task/task_list";
    }
    
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Task> list(TaskSearcher request)
    {
        return taskService.list(request);
    }
    
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Task get(String id)
    {
        return taskService.get(id);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(TaskConfigRequest request)
    {
        String content = request.getInputContent();
        List<TaskInput> list = Lists.newArrayList();
        if (StringUtils.isNotEmpty(content))
        {
            list = JSON.parseArray(content, TaskInput.class);
        }
        request.setInputConfigs(list);
        taskService.modify(request);
        return "redirect:list.do";
    }
    
    @RequestMapping(value = "/form.do")
    public String form(String id, ModelMap model, String semantic)
    {
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        Task task = taskService.get(id);
        if (task.getUserGroup() != null)
        {
            UserGroup u = task.getUserGroup();
            u.setName(u.getGroupName());
            userGroups.add(u);
        }
        model.addAttribute("userGroups", JSONObject.toJSON(userGroups).toString());
        task.getInputConfigs().clear();
        ReagentKit reagentKit = new ReagentKit();
        reagentKit.setId(id);
        List<ReagentKit> reagentKits = reagentKitService.listByTaskId(reagentKit);
        List<TaskInput> taskInputs = taskService.getTaskInputs(id);
        List<TaskInput> taskInputResult = Lists.newArrayList();
        
        Map<String, List<ReagentInfo>> map = Maps.newHashMap();
        ReagentInfo reagentInfo = null;
        if (Collections3.isNotEmpty(taskInputs))
        {
            for (TaskInput taskInput : taskInputs)
            {
                if (map.containsKey(taskInput.getInputSampleId()))
                {
                    List<ReagentInfo> list = map.get(taskInput.getInputSampleId());
                    reagentInfo = new ReagentInfo();
                    reagentInfo.setInputSize(taskInput.getInputSize());
                    reagentInfo.setInputVolume(taskInput.getInputVolume());
                    reagentInfo.setReagentKitId(taskInput.getReagentKitId());
                    list.add(reagentInfo);
                }
                else
                {
                    List<ReagentInfo> list = Lists.newArrayList();
                    reagentInfo = new ReagentInfo();
                    reagentInfo.setInputSize(taskInput.getInputSize());
                    reagentInfo.setInputVolume(taskInput.getInputVolume());
                    reagentInfo.setReagentKitId(taskInput.getReagentKitId());
                    list.add(reagentInfo);
                    map.put(taskInput.getInputSampleId(), list);
                }
            }
            for (String sampleId : map.keySet())
            {
                TaskInput taskInputTemp = new TaskInput();
                taskInputTemp.setInputSampleId(sampleId);
                taskInputTemp.setReagentInfoList(map.get(sampleId));
                taskInputResult.add(taskInputTemp);
            }
            task.setInputConfigs(taskInputResult);
        }
        model.addAttribute("task", task);
        model.addAttribute("reagentKits", reagentKits);
        //根据semantic进入对应页面
        String url = "task/task_form";
        switch (semantic)
        {
            case "DNA-EXT":
            case "CDNA-EXT":
                url = "task/dna_extract_form";
                break;
            case "DNA-QC":
            case "CDNA-QC":
            case "LIBRARY-QC":
                url = "task/dna_qc_form";
                break;
            case "LIBRARY-CRE":
                url = "task/lib_create_form";
                break;
            case "LIBRARY-CAP":
                url = "task/lib_cap_form";
                break;
            case "POOLING":
                url = "task/pooling_form";
                break;
            case "RQT":
                url = "task/rqt_form";
                break;
            case "QT":
                url = "task/qt_form";
                break;
            case "NGS-SEQ":
                url = "task/ngs_seq_form";
                break;
            case "BIOLOGY-ANALY":
            case "TECHNICAL-ANALY":
                url = "task/analy_form";
                break;
            case "DATA-ANALYSIS":
            case "DT-DATA-ANALYSIS":
            case "MLPA-DATA-ANALYSIS":
            case "PRIMER-DESIGN":
            case "REPORT-COMBINE":
                url = "task/da_pr_re_form";
            default:
                break;
        }
        return url;
    }
    
    @RequestMapping(value = "/show.do")
    public String get(ModelMap model, SamplePagingRequest request, ExperimentProductListRequest request2,
        ReagentListRequest request3, UserListRequest request4, ReagentKit reagentKit)
    {
        //获取不到UUID假数据
        String id = "200545574de546ea8843ad679c79bc73";
        
        Task task = new Task();
        if (id != null)
        {
            task = taskService.get(id);
        }
        // 任务类型 到时候取的字典项
        List<Dict> taskTypeList = dictService.getEntries("TASK_TYPE");
        // 任务输出输入类型 到时候改成读取字典
        List<Dict> inputTypeList = dictService.getEntries("TASK_ELEMENT");
        List<TaskInput> taskInputs = task.getInputConfigs();
        List<Dict> companys = dictService.getEntries("COMPANY");
        //投入试剂盒
        List<ReagentKit> reagentKits = reagentKitService.list(reagentKit);
        // 查处所有的样本试验数据报告放到map，联动使用
        Map<String, List<InputOutputModel>> map = Collections.emptyMap();// taskService.getInputOutput(request, request2, "");
        model.addAttribute("mapType", JSONObject.toJSON(map).toString())
            .addAttribute("task", task)
            .addAttribute("taskTypeDict", taskTypeList)
            .addAttribute("reagentList", reagentKits)
            .addAttribute("reagentLists", JSONObject.toJSON(reagentKits).toString())
            .addAttribute("inputTypeList", JSONObject.toJSON(inputTypeList).toString())
            .addAttribute("taskInputs", JSONObject.toJSON(taskInputs).toString())
            .addAttribute("companys", JSONObject.toJSON(companys).toString());
        return "task/task_show";
    }
    
    @RequestMapping(value = "/view.do")
    public String viewTask(String id, ModelMap model)
    {
        Task task = taskService.get(id);
        if (StringUtils.isNotEmpty(task.getOutputSampleId()))
        {
            MetadataSample sample = sampleService.get(task.getOutputSampleId());
            task.setSample(sample);
        }
        task.getInputConfigs().clear();
        List<TaskInput> taskInputs = taskService.getTaskInputs(id);
        List<TaskInput> taskInputResult = Lists.newArrayList();
        
        Map<String, List<ReagentInfo>> map = Maps.newHashMap();
        ReagentInfo reagentInfo = null;
        if (Collections3.isNotEmpty(taskInputs))
        {
            for (TaskInput taskInput : taskInputs)
            {
                if (map.containsKey(taskInput.getInputSampleId()))
                {
                    List<ReagentInfo> list = map.get(taskInput.getInputSampleId());
                    reagentInfo = new ReagentInfo();
                    reagentInfo.setInputSize(taskInput.getInputSize());
                    reagentInfo.setInputVolume(taskInput.getInputVolume());
                    if (StringUtils.isNotEmpty(taskInput.getReagentKitId()))
                    {
                        ReagentKit kit = reagentKitService.getReagentKit(taskInput.getReagentKitId());
                        reagentInfo.setReagenKit(kit);
                    }
                    list.add(reagentInfo);
                }
                else
                {
                    List<ReagentInfo> list = Lists.newArrayList();
                    reagentInfo = new ReagentInfo();
                    reagentInfo.setInputSize(taskInput.getInputSize());
                    reagentInfo.setInputVolume(taskInput.getInputVolume());
                    if (StringUtils.isNotEmpty(taskInput.getReagentKitId()))
                    {
                        ReagentKit kit = reagentKitService.getReagentKit(taskInput.getReagentKitId());
                        reagentInfo.setReagenKit(kit);
                    }
                    list.add(reagentInfo);
                    map.put(taskInput.getInputSampleId(), list);
                }
            }
            for (String sampleId : map.keySet())
            {
                TaskInput taskInputTemp = new TaskInput();
                if (StringUtils.isNotEmpty(sampleId))
                {
                    MetadataSample sample = sampleService.get(sampleId);
                    taskInputTemp.setSample(sample);
                }
                taskInputTemp.setReagentInfoList(map.get(sampleId));
                taskInputResult.add(taskInputTemp);
            }
            task.setInputConfigs(taskInputResult);
        }
        model.addAttribute("task", task);
        return "task/task_view";
    }
}
