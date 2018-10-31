package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.DataArea;

public interface IAreaService
{
    
    DataArea get(Integer parentId);
    
    List<DataArea> findRoots();
    
    List<DataArea> findByParentId(Integer parentId);
}
