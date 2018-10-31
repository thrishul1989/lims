package com.todaysoft.lims.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.report.dao.searcher.BuiltinVariableSearcher;
import com.todaysoft.lims.report.entity.BuiltinVariable;
import com.todaysoft.lims.report.service.IBuiltinVariableService;

@RestController
@RequestMapping("/report/builtinVariable")
public class BuiltinVariableController
{
    
    @Autowired
    private IBuiltinVariableService service;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BuiltinVariable get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/selectBuiltinVariable", method = RequestMethod.POST)
    public List<BuiltinVariable> builtinVariableList(@RequestBody BuiltinVariableSearcher searcher)
    {
        return service.builtinVariableList(searcher);
    }
}
