package com.todaysoft.lims.testing.cycleConfig.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ScheduleTestingConfig;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.cycleConfig.dao.GlobalConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.OtherReportFormSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.TestingConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.model.OtherReportFormModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.testing.cycleConfig.service.ICycleConfigService;
import com.todaysoft.lims.testing.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.testing.cycleConfig.service.request.TaskConfigRequest;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/cycleConfig")
public class CycleConfigController
{
    @Autowired
    private ICycleConfigService service;
    
    @RequestMapping(value = "/globalPaging", method = RequestMethod.POST)
    public Pagination<WarningGlobalConfigModel> paging(@RequestBody GlobalConfigSearcher searcher)
    {
        return service.globalPagining(searcher);
    }
    
    @RequestMapping(value = "/testingPaging", method = RequestMethod.POST)
    public Pagination<WarningTestingConfigModel> paging(@RequestBody TestingConfigSearcher searcher)
    {
        return service.testingPagining(searcher);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody GlobalConfigRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/testingMethodList", method = RequestMethod.GET)
    public List<TestingMethod> getTestingMethodList()
    {
        return service.getTestingMethodList();
    }
    
    @RequestMapping(value = "/getTestingMethodListIncludeVerity", method = RequestMethod.GET)
    public List<TestingMethod> getTestingMethodListIncludeVerity()
    {
        return service.getTestingMethodListIncludeVerity();
    }
    
    @RequestMapping(value = "/taskConfig", method = RequestMethod.POST)
    public List<TaskConfig> getTaskConfig(@RequestBody TaskConfigRequest request)
    {
        TestingMethod tm = service.getTestingMethodListById(request.getTestingMethodId());
        String process = tm.getTestingProcess();
        if (StringUtils.isNotEmpty(tm.getAnalyseProcess()))
        {
            process += "/" + tm.getAnalyseProcess();
        }
        String[] semantics = process.split("/");
        List<TaskConfig> list = Lists.newArrayList();
        
        TaskConfig dnaex = service.getTaskConfigBySemantic(TaskSemantic.DNA_EXTRACT);//DNA提取
        TaskConfig dnaqc = service.getTaskConfigBySemantic(TaskSemantic.DNA_QC);////DNA质检
        TaskConfig cdnaex = service.getTaskConfigBySemantic(TaskSemantic.CDNA_EXTRACT);
        TaskConfig cdnaqc = service.getTaskConfigBySemantic(TaskSemantic.CDNA_QC);
        list.add(dnaex);
        list.add(dnaqc);
        if ("1".equals(tm.getType()))//检测
        {
            list.add(cdnaex);
            list.add(cdnaqc);
        }
        //引物设计合成
        /*TaskConfig primerDesign = service.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
        if("Sanger验证".equals(tm.getName()))
        {
            list.add(primerDesign);
        }*/
        //PCR-NGS引物设计合成
        /*TaskConfig pcrNgsPrimerDesign = service.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
        if("PCR-NGS验证".equals(tm.getName()))
        {
            list.add(pcrNgsPrimerDesign);
        }*/
        
        if (semantics.length > 0)
        {
            for (int i = 0; i < semantics.length; i++)
            {
                if (StringUtils.isNotEmpty(semantics[i]))
                {
                    TaskConfig tc = service.getTaskConfigBySemantic(semantics[i]);
                    list.add(tc);
                }
                
            }
        }
        return list;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody TaskConfigRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody TestingConfigSearcher request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/getTestingConfigById/{id}", method = RequestMethod.GET)
    public ScheduleTestingConfig getTestingConfig(@PathVariable String id)
    {
        return service.getTestingConfigById(id);
    }
    
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public Integer deleteById(@PathVariable String id)
    {
        return service.delete(id);
    }
    
    @RequestMapping(value = "/scheduleTestingConfigList", method = RequestMethod.POST)
    public List<WarningTestingConfigModel> getScheduleTestingConfigList(@RequestBody TestingConfigSearcher request)
    {
        return service.getScheduleTestingConfigList(request);
    }
    
    @RequestMapping(value = "/getCycleReportFormList", method = RequestMethod.POST)
    public List<OtherReportFormModel> getCycleReportFormList(@RequestBody OtherReportFormSearcher request)
    {
        return service.getCycleReportFormList(request);
    }
    
}
