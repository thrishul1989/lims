package com.todaysoft.lims.system.modules.bcm.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.MeasureUnit;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.form.SamplePretestingConfigForm;
import com.todaysoft.lims.system.modules.bcm.form.SampleStartableConfigForm;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.SamplePretestingConfig;
import com.todaysoft.lims.system.modules.bcm.model.TaskConfigSimpleModel;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bcm.service.ITaskConfigService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.utils.Collections3;

@Controller
@RequestMapping("/sample")
public class MetadataSampleController extends BaseController
{
    @Autowired
    private IMetadataSampleService service;
    
    @Autowired
    private ITaskConfigService taskConfigService;
    
    @RequestMapping("/list.do")
    public String list(SamplePagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<MetadataSample> pagination = service.paging(searcher, pageNo, 10);
        List<MeasureUnit> unitList = service.unitList();
        model.addAttribute("unitList", unitList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "sample/sample_list";
    }
    
    @RequestMapping("/unique.do")
    @ResponseBody
    public boolean unique(String code, String ignore)
    {
        if (!StringUtils.isEmpty(ignore) && ignore.equals(code))
        {
            return true;
        }
        
        return service.getAsCode(code) == null;
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String createPage(ModelMap model)
    {
        List<MeasureUnit> unitList = service.unitList();
        model.addAttribute("unitList", unitList);
        return "sample/sample_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(MetadataSample data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyPage(String id, ModelMap model)
    {
        List<MeasureUnit> unitList = service.unitList();
        MetadataSample data = service.get(id);
        model.addAttribute("data", data).addAttribute("unitList", unitList);
        return "sample/sample_form";
    }
    
    @RequestMapping("/getSampleById.do")
    @ResponseBody
    public MetadataSample getSampleById(String id)
    {
        MetadataSample data = service.get(id);
        return data;
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(MetadataSample data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session);
    }
    
    @RequestMapping("/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session);
    }
    
    @RequestMapping("/view.do")
    public String details(String id, ModelMap model)
    {
        MetadataSample data = service.get(id);
        model.addAttribute("data", data);
        return "sample/sample_show";
    }
    
    @RequestMapping("/delete_config.do")
    public String deleteConfig(String id, String sourceSampleId, ModelMap model)
    {
        service.deleteConfig(id);
        model.clear();
        model.addAttribute("id", sourceSampleId);
        return "redirect:/sample/config_form.do";
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/sample/list.do";
    }
    
    /**
     * 模糊匹配样本
     * */
    @RequestMapping(value = "/getSelecteList.do")
    @ResponseBody
    public Map containerSelect(SamplePagingRequest request)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<MetadataSample> sampleList = service.selectSample(request, 1, 100);
        map.put("value", sampleList);
        
        return map;
    }
    
    @RequestMapping(value = "/getUnitName.do")
    @ResponseBody
    public MeasureUnit getUnitName(String id)
    {
        
        MeasureUnit name = service.getUnitName(id);
        
        return name;
    }
    
    @RequestMapping(value = "/getSelecteList")
    @ResponseBody
    public List<MetadataSample> getSelecteList(SamplePagingRequest request)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<MetadataSample> sampleList = service.list(request);
        if (Collections3.isEmpty(sampleList))
        {
            sampleList = Lists.newArrayList();
        }
        return sampleList;
    }
    
    @RequestMapping("/startable_config.do")
    public String startableConfig(SampleStartableConfigForm data, ModelMap model)
    {
        service.startableConfig(data);
        model.clear();
        return "redirect:/sample/handleSample.do";
    }
    
    @RequestMapping(value = "/handleSample.do")
    public String handleSample(SamplePagingRequest searcher, ModelMap model)
    {
        //        List<MetadataSample> samples = service.list(searcher);
        //        for (MetadataSample sample : samples)
        //        {
        //            List<TaskInput> taskInputs = Collections.emptyList();//taskService.getTaskInput(Integer.parseInt(sample.getId()));
        //            List<Task> tasks = new ArrayList<Task>();
        //            for (TaskInput taskInput : taskInputs)
        //            {
        //                //                Task task = taskService.get(taskInput.getTaskId());
        //                //                if (task != null)
        //                //                {
        //                //                    tasks.add(taskService.get(task.getOutputId()));
        //                //                }
        //            }
        //            sample.setTasks(tasks);
        //            
        //            List<MetadataSample> targetMetadataSample = service.getEntityByIsintermediate("2");
        //            sample.setTargetMetadataSamples(targetMetadataSample);
        //            for (MetadataSample metadataSample : targetMetadataSample)
        //            {
        //                SampleExtractConfigRequest request = new SampleExtractConfigRequest();
        //                request.setSourceSampleId(Integer.parseInt(sample.getId()));
        //                request.setTargetSampleId(Integer.parseInt(metadataSample.getId()));
        //                List<SampleExtractConfig> configs = service.getconfigBySourceAndTarget(request);
        //                if (Collections3.isNotEmpty(configs))
        //                {
        //                    SampleExtractConfig config = configs.get(0);
        //                    metadataSample.setConfig(config);
        //                }
        //            }
        //        }
        //        
        //        model.addAttribute("samples", samples);
        Map<String, SamplePretestingConfig> configs = service.getPretestingConfigs();
        model.addAttribute("configs", configs);
        model.addAttribute("searcher", searcher);
        return "sample/sample_handle";
    }
    
    @RequestMapping(value = "/pretesting_config.do", method = RequestMethod.GET)
    public String pretestingConfig(String receivedSampleId, String testingSampleId, ModelMap model)
    {
        model.addAttribute("receivedSampleId", receivedSampleId);
        model.addAttribute("testingSampleId", testingSampleId);
        return "sample/pretesting_config";
    }
    
    @RequestMapping(value = "/pretesting_tasks.do")
    @ResponseBody
    public List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSampleId(String inputSampleId)
    {
        return taskConfigService.getPretestingTaskConfigsByInputSample(inputSampleId);
    }
    
    @RequestMapping(value = "/getUnit.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String testingUnit(String id)
    {
        MetadataSample ms = null;
        if (!StringUtils.isEmpty(id))
        {
            ms = service.get(id);
        }
        if (ms == null)
        {
            return "";
        }
        else
        {
            return ms.getTestingUnit();
        }
    }
    
    @RequestMapping(value = "/pretesting_config.do", method = RequestMethod.POST)
    @ResponseBody
    public void modifyConfig(SamplePretestingConfigForm data)
    {
        
    }
    
    @RequestMapping(value = "/validata.do", method = RequestMethod.POST)
    @ResponseBody
    public boolean validata(MetadataSample sample)
    {
        return service.validata(sample);
    }
}
