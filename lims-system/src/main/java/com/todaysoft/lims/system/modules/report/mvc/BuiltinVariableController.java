package com.todaysoft.lims.system.modules.report.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.modules.report.model.BuiltinVariable;
import com.todaysoft.lims.system.modules.report.service.IBuiltinVariableService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/report/builtinVariable")
public class BuiltinVariableController extends BaseController
{
    @Autowired
    private IBuiltinVariableService service;
    
    @RequestMapping("/selectBuiltinVariable.do")
    @ResponseBody
    public List<BuiltinVariable> builtinVariableList(String key)
    {
        BuiltinVariable searcher = new BuiltinVariable();
        searcher.setName(key);
        searcher.setPageNo(1);
        searcher.setPageSize(10);
        service.builtinVariableList(searcher);
        return service.builtinVariableList(searcher);
    }
    
    @RequestMapping(value = "/getBuiltinVariable.do")
    @ResponseBody
    public BuiltinVariable get(String id)
    {
        return service.get(id);
    }
}
