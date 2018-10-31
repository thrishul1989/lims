package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.report.dao.searcher.BuiltinVariableSearcher;
import com.todaysoft.lims.report.entity.BuiltinVariable;

public interface IBuiltinVariableService
{
    List<BuiltinVariable> builtinVariableList(BuiltinVariableSearcher searcher);
    
    BuiltinVariable get(String id);
}
