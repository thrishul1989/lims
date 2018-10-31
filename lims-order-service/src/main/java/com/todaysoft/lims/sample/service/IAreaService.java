package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.DataArea;

public interface IAreaService
{
    
    DataArea get(Integer parentId);
    
    List<DataArea> findRoots();
    
    List<DataArea> findByParentId(DataArea request);
    
}
