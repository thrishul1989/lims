package com.todaysoft.lims.system.modules.bpm.pooling.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingTask;
import com.todaysoft.lims.system.modules.bpm.pooling.service.IPoolingService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/process")
public class PoolingTaskController extends BaseController
{
    @Autowired
    private IPoolingService service;
    
    @RequestMapping("/poolingList.do")
    public String testTasks(PoolingAssignableTaskSearcher searcher, ModelMap model)
    {
        List<PoolingTask> tasks = service.getPoolingAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/pooling_list";
    }
}
