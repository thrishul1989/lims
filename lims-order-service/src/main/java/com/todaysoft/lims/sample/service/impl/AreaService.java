package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.sample.entity.DataArea;
import com.todaysoft.lims.sample.service.IAreaService;

@Service
public class AreaService implements IAreaService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public DataArea get(Integer id)
    {
        DataArea d = baseDaoSupport.get(DataArea.class, id.toString());
        return d;
    }
    
    @Override
    public List<DataArea> findRoots()
    {
        String jpql = "select area from  DataArea area where area.parentId is null order by area.order asc";
        List<DataArea> re = (List<DataArea>)baseDaoSupport.find(jpql);
        return re;
    }
    
    @Override
    public List<DataArea> findByParentId(DataArea request)
    {
        String jpql = "select area from  DataArea area where area.parentId = ?";
        List<DataArea> re = (List<DataArea>)baseDaoSupport.find(jpql, request.getParentId());
        return re;
    }
    
}
