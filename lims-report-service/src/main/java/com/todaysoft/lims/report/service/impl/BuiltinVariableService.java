package com.todaysoft.lims.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.BuiltinVariableSearcher;
import com.todaysoft.lims.report.entity.BuiltinVariable;
import com.todaysoft.lims.report.service.IBuiltinVariableService;

@Service
public class BuiltinVariableService implements IBuiltinVariableService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<BuiltinVariable> builtinVariableList(BuiltinVariableSearcher searcher)
    {
        Pagination<BuiltinVariable> pagination = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), BuiltinVariable.class);
        if (null != pagination)
        {
            return pagination.getRecords();
        }
        return null;
    }
    
    @Override
    public BuiltinVariable get(String id)
    {
        return baseDaoSupport.get(BuiltinVariable.class, id);
    }
    
}
