package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.system.modules.report.model.BuiltinVariable;

public interface IBuiltinVariableService
{
    List<BuiltinVariable> builtinVariableList(BuiltinVariable searcher);
    
    BuiltinVariable get(String id);
}
