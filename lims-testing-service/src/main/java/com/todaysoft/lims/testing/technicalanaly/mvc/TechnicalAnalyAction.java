package com.todaysoft.lims.testing.technicalanaly.mvc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.technicalanaly.dao.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.testing.technicalanaly.model.AssignVerifyProcessResult;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySheetSamplesModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/testing/technical-analy")
public class TechnicalAnalyAction
{
    private static Logger log = LoggerFactory.getLogger(TechnicalAnalyAction.class);

    @Autowired
    private ITechnicalAnalyService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public List<TechnicalAnalyTask> list(@RequestBody TechnicalAnalyAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks_detail", method = RequestMethod.POST)
    public List<TechnicalAnalyTask> listDetail(@RequestBody TechnicalAnalyAssignableTaskSearcher searcher)
    {
        return service.getAssignableListDetail(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public TechnicalAnalyAssignModel getAssignModel(@RequestBody TechnicalAnalyAssignArgs args)
    {
        return service.getAssignModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody TechnicalAnalyAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public TechnicalAnalySubmitModel getSubmitModel(@PathVariable String id)
    {
        return service.getSubmitModel(id);
    }
    
    @RequestMapping(value = "/sheets/{id}/export", method = RequestMethod.GET)
    public TechnicalAnalySubmitModel getExportModel(@PathVariable String id)
    {
        return service.getExportModel(id);
    }
    
    @RequestMapping(value = "/sheets/{id}/samples", method = RequestMethod.GET)
    public TechnicalAnalySheetSamplesModel getSamplesModel(@PathVariable String id)
    {
        return service.getSamplesModel(id);
    }
    
    @RequestMapping(value = "/sheets/verify", method = RequestMethod.POST)
    public void verify(@RequestBody TechnicalAnalySubmitVerifyRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.verify(request, token);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody TechnicalAnalySubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        VariableModel model = new VariableModel();
        List<TestingTask> tasks = Lists.newArrayList();
        List<AssignVerifyProcessResult> assignVerifyProcessResults= service.submit(request, token, model, tasks);
        if(assignVerifyProcessResults!=null&&assignVerifyProcessResults.size()>0) {
            for(AssignVerifyProcessResult assignVerifyProcessResult:assignVerifyProcessResults) {
                if(assignVerifyProcessResult!=null) {
                    if(assignVerifyProcessResult.getSchduleIds()!=null&&assignVerifyProcessResult.getSchduleIds().size()>0) {
                        testingScheduleService.sendOrderVerifyTestingStartMessage(assignVerifyProcessResult.getSchduleIds(), assignVerifyProcessResult.getOrderIds());
                    }else {
                        log.error("数据编号："+assignVerifyProcessResult.getDataCode()+"为空，未发送验证流程重排计划消息");
                    }
                }
            }
        }
        testingTaskService.updateTaskRedundantColumn(tasks, 0);
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            testingScheduleService.sendReportMessage(model);
        }
        String id = request.getId();
        if (StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
    
    @RequestMapping(value = "/exportSummary", method = RequestMethod.POST)
    public List<TechnicalAnalyTask> getListBySequencingCode(@RequestBody TechnicalAnalyAssignableTaskSearcher searcher)
    {
        return service.getListBySequencingCode(searcher);
    }
}
