package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.TodoModel;
import com.todaysoft.lims.gateway.model.request.TestingNodeTodoRequest;
import com.todaysoft.lims.gateway.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetSubmitRequest;
import com.todaysoft.lims.gateway.service.ITestingScheduleService;

@RestController
@RequestMapping("/1")
public class TestingSheduleController
{
    
    @Autowired
    private ITestingScheduleService service;
    
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public void start(@RequestBody TestingScheduleStartRequest request)
    {
        service.start(request);
    }
    
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public List<TodoModel> todo(@RequestBody TestingNodeTodoRequest request)
    {
        return service.todo(request);
    }
    
    @RequestMapping(value = "/executeTodo", method = RequestMethod.POST)
    public List<TodoModel> executeTodo(@RequestBody TestingNodeTodoRequest request)
    {
        return service.executeTodo(request);
    }
    
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public void assign(@RequestBody TestingSheetCreateRequest request)
    {
        service.assign(request);
    }
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public void submit(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submit(request);
    }
    
    //参与流程
    @RequestMapping(value = "/processJoin", method = RequestMethod.POST)
    public Pagination<TodoModel> processJoin(@RequestBody TestingNodeTodoRequest request)
    {
        return service.processJoin(request);
    }
    
    //dna质检下达
    @RequestMapping(value = "/assignDnaQt", method = RequestMethod.POST)
    public void assignDnaQt(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignDnaQt(request);
    }
    
    //dna质检实验任务提交
    @RequestMapping(value = "/submitDnaQt", method = RequestMethod.POST)
    public void submitDnaQt(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitDnaQt(request);
    }
    
    //文库创建下达
    @RequestMapping(value = "/assignWkCreate", method = RequestMethod.POST)
    public void assignWkCreate(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignWkCreate(request);
    }
    
    //文库创建实验提交
    @RequestMapping(value = "/submitWkCreate", method = RequestMethod.POST)
    public void submitWk(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitWk(request);
    }
    
    //文库质检
    @RequestMapping(value = "/assignWkQt", method = RequestMethod.POST)
    public void assignWkQt(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignWkQt(request);
    }
    
    //文库质检实验提交
    @RequestMapping(value = "/submitWkQt", method = RequestMethod.POST)
    public void submitWkQt(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitWkQt(request);
    }
    
    //文库捕获下达
    @RequestMapping(value = "/assignWKCatch", method = RequestMethod.POST)
    public void assignWKCatch(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignWKCatch(request);
    }
    
    //文库捕获实验提交
    @RequestMapping(value = "/submitWKCatch", method = RequestMethod.POST)
    public void submitWKCatch(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitWKCatch(request);
    }
    
    //相对定量&混样下达
    @RequestMapping(value = "/assignXddl", method = RequestMethod.POST)
    public void assignXddl(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignXddl(request);
    }
    
    //通过CT获取体积和产物相对量数据
    @RequestMapping(value = "/xddl/dataProcess", method = RequestMethod.POST)
    public TestingSheetSubmitRequest dataProcess(@RequestBody TestingSheetSubmitRequest request)
    {
        return service.dataProcess(request);
    }
    
    //相对定量&混样实验提交
    @RequestMapping(value = "/submitXddl", method = RequestMethod.POST)
    public void submitXddl(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitXddl(request);
    }
    
    //绝对定量下达
    @RequestMapping(value = "/assignAbsoluteQ", method = RequestMethod.POST)
    public void assignAbsoluteQ(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignAbsoluteQ(request);
    }
    
    //绝对定量实验提交
    @RequestMapping(value = "/submitAbsoluteQ", method = RequestMethod.POST)
    public void submitAbsoluteQ(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitAbsoluteQ(request);
    }
    
    //上机下达
    @RequestMapping(value = "/assignOntest", method = RequestMethod.POST)
    public void assignOntest(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignOntest(request);
    }
    
    //上机实验提交
    @RequestMapping(value = "/submitOntest", method = RequestMethod.POST)
    public void submitOntest(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitOntest(request);
    }
    
    //上机下达
    @RequestMapping(value = "/assignBioInfo", method = RequestMethod.POST)
    public void assignBioInfo(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignBioInfo(request);
    }
    
    //上机实验提交
    @RequestMapping(value = "/submitBioInfo", method = RequestMethod.POST)
    public void submitBioInfo(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitBioInfo(request);
    }
    
    //技术分析下达
    @RequestMapping(value = "/assignTecAnalysis", method = RequestMethod.POST)
    public void assignTecAnalysis(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignTecAnalysis(request);
    }
    
    //技术分析提交
    @RequestMapping(value = "/submitTecAnalysis", method = RequestMethod.POST)
    public void submitTecAnalysis(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitTecAnalysis(request);
    }
    
    //报告生成下达
    @RequestMapping(value = "/assignReportCreate", method = RequestMethod.POST)
    public void assignReportCreate(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignReportCreate(request);
    }
    
    //报告生成提交
    @RequestMapping(value = "/submitReportCreate", method = RequestMethod.POST)
    public void submitReportCreate(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitReportCreate(request);
    }
    
    //报告审核下达
    @RequestMapping(value = "/assignReportCheck", method = RequestMethod.POST)
    public void assignReportCheck(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignReportCheck(request);
    }
    
    //报告审核提交
    @RequestMapping(value = "/submitReportCheck", method = RequestMethod.POST)
    public void submitReportCheck(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitReportCheck(request);
    }
    
    //报告邮寄下达
    @RequestMapping(value = "/assignReportMail", method = RequestMethod.POST)
    public void assignReportMail(@RequestBody TestingSheetCreateRequest request)
    {
        service.assignReportMail(request);
    }
    
    //报告邮寄提交
    @RequestMapping(value = "/submitReportMail", method = RequestMethod.POST)
    public void submitReportMail(@RequestBody TestingSheetSubmitRequest request)
    {
        service.submitReportMail(request);
    }
    
    @RequestMapping(value = "/validateCatchCode", method = RequestMethod.POST)
    public boolean validateCatchCode(@RequestBody TestingSheetCreateRequest request)
    {
        return service.validateCatchCode(request);
    }
    
}
