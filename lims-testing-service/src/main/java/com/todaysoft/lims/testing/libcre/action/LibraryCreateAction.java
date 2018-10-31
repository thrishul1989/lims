package com.todaysoft.lims.testing.libcre.action;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.libcre.dao.LibraryCreateTaskSearcher;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.testing.libcre.service.ILibraryCreateService;

@RestController
@RequestMapping("/bpm/testing/libcre")
public class LibraryCreateAction
{
    @Autowired
    private ILibraryCreateService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    //文库创建待办列表
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<LibraryCreateTask> libCreTasks(@RequestBody LibraryCreateTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    //文库创建下达页面
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public LibCreAssignModel dnaQcAssignList(@RequestBody LibCreAssignArgs args)
    {
        return service.getAssignModel(args);
    }
    
    //文库创建下达提交
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void libCreAssign(@RequestBody LibraryCreateAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    //文库创建实验任务页面
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public LibraryCreateSheet getSheet(@PathVariable String id)
    {
        return service.getSheet(id);
    }
    
    //文库创建实验任务提交
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void libCreSubmit(@RequestBody Map<String, String> variables, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        String id = variables.get("id");
        service.submitSheet(id, token);
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
