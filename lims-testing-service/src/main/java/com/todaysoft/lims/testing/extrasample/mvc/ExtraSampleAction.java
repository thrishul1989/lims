package com.todaysoft.lims.testing.extrasample.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.ConcludingReportModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.extrasample.dao.ExtraSampleTaskSearcher;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTask;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTaskDetails;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTaskHandleRequest;
import com.todaysoft.lims.testing.extrasample.model.request.ExtraSampleTaskPagingRequest;
import com.todaysoft.lims.testing.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/extraSample")
public class ExtraSampleAction
{
    @Autowired
    private IExtraSampleService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingTaskService taskService;
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public Pagination<ExtraSampleTask> paging(@RequestBody(required = false) ExtraSampleTaskPagingRequest request) throws InvocationTargetException
    {
        if (null == request)
        {
            request = new ExtraSampleTaskPagingRequest();
            request.setPageNo(1);
            request.setPageSize(10);
        }
        
        ExtraSampleTaskSearcher searcher = new ExtraSampleTaskSearcher();
        BeanUtils.copyProperties(request, searcher);
        return service.paging(searcher, null == request.getPageNo() ? 1 : request.getPageNo(), null == request.getPageSize() ? 10 : request.getPageSize());
    }
    
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ExtraSampleTaskDetails getDetails(@PathVariable String id)
    {
        return service.getDetails(id);
    }
    
    @RequestMapping(value = "/tasks/handle", method = RequestMethod.POST)
    public void solve(@RequestBody ExtraSampleTaskHandleRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        VariableModel model = new VariableModel();
        List<TestingTask> lists = Lists.newArrayList();
        service.handle(request, token, model, lists);
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            testingScheduleService.sendReportMessage(model);
        }
        if (Collections3.isNotEmpty(lists))
        {
            taskService.updateTaskRedundantColumn(lists, 0);
        }
        if (Collections3.isNotEmpty(model.getStartRecors()))
        {//追加样本需要数据发送或者结题报告的
            ConcludingReportModel reportModel = new ConcludingReportModel();
            reportModel.setStartRecors(model.getStartRecors());
            testingScheduleService.extraSendReport(reportModel);
        }
    }
    
    //    @RequestMapping("/history_list.do")
    //    public Pagination<AbnormalSolveRecordSearcher> history(@RequestBody AbnormalSolveRecordSearcher searcher)
    //    {
    //        return  service.history(searcher);
    //
    //    }
    //
    //    @RequestMapping(value = "/history_show.do/{id}", method = RequestMethod.GET)
    //    public AbnormalSolveRecordSearcher historyShow(@PathVariable String id)
    //    {
    //        return  service.history(id);
    //    }
    
}
