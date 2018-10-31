package com.todaysoft.lims.system.modules.bpm.cycleConfig.mvc;

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

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.GlobalConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.TestingConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.impl.CycleConfigService;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.TestingConfigRequest;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/bpm/cycleConfig")
public class CycleConfigController extends BaseController
{
    @Autowired
    private CycleConfigService service;
    
    @RequestMapping(value = "/tabList.do")
    public String paging(ModelMap model)
    {
        model.addAttribute("flag", "update");
        return "bpm/cycleConfig/cycleConfig_tab";
        
    }
    
    //fixedConfigList.do非实验
    @RequestMapping(value = "/fixedConfigList.do")
    public String fixedConfigList(GlobalConfigSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<WarningGlobalConfigModel> pagination = service.globalPagining(searcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/cycleConfig/cycleConfig_fixed_list";
        
    }
    
    //testingConfigList.do实验
    @RequestMapping(value = "/testingConfigList.do")
    public String testingConfigList(TestingConfigSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<WarningTestingConfigModel> pagination = service.testingPagining(searcher);
        List<TestingMethod> list = service.getTestingMethodList();
        model.addAttribute("testingMethodList", list);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bpm/cycleConfig/cycleConfig_testing_list";
        
    }
    
    //非实验修改
    @ResponseBody
    @RequestMapping(value = "/fixedModify.do", method = RequestMethod.POST)
    public void modify(GlobalConfigRequest request, ModelMap model, HttpSession session)
    {
        service.modify(request);
    }
    
    //实验新增
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String sendForward(ModelMap model)
    {
        List<TestingMethod> list = service.getTestingMethodList();
        model.addAttribute("testingMethodList", list);
        return "bpm/cycleConfig/cycleConfig_testing_form";
    }
    
    //检测方法节点获取
    @ResponseBody
    @RequestMapping(value = "/getTaskConfigList.do", method = RequestMethod.POST)
    public List<TaskConfig> getTaskConfigList(TestingConfigRequest request, ModelMap model, HttpSession session)
    {
        return service.getTaskConfigBySemantic(request);
        
    }
    
    //新增保存
    @RequestMapping(value = "/saveCreate.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(TestingConfigRequest request, ModelMap model, HttpSession session)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        service.create(request);
        map.put("result", true);
        return map;
        
    }
    
    //同一检测方法周期名称校验
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(TestingConfigSearcher request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/modify.do")
    public String modify(TestingConfigSearcher searcher, ModelMap model)
    {
        List<TestingMethod> list = service.getTestingMethodListIncludeVerity();
        model.addAttribute("testingMethodList", list);
        WarningTestingConfigModel testingConfig = service.getById(searcher.getId());
        model.addAttribute("testingConfig", testingConfig);
        return "bpm/cycleConfig/cycleConfig_testing_form";
        
    }
    
    @RequestMapping("/delete.do")
    @ResponseBody
    public Integer delete(String id, ModelMap model, HttpSession session)
    {
        Integer a = service.delete(id);
        return a;
    }
    
    //根据testingMethid  id  获取检测配置
    @ResponseBody
    @RequestMapping(value = "/getScheduleTestingConfigList.do", method = RequestMethod.POST)
    public List<WarningTestingConfigModel> getScheduleTestingConfigList(TestingConfigRequest request, ModelMap model, HttpSession session)
    {
        return service.getScheduleTestingConfigList(request);
        
    }
}
