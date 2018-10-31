package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.service.IFirmService;
import com.todaysoft.lims.gateway.service.adapter.FirmAdapter;

@Service
public class FirmService implements IFirmService
{
    
    @Autowired
    private FirmAdapter firmAdapter;
    
    @Override
    public Pagination<Firm> paging(Firm request)
    {
        return firmAdapter.paging(request);
    }
    
    @Override
    public List<Firm> list(Firm request)
    {
        return firmAdapter.list(request);
    }
    
    @Override
    public Firm get(Integer id)
    {
        return firmAdapter.get(id);
    }
    
    @Override
    public Integer create(Firm request)
    {
        return firmAdapter.create(request);
    }
    
    @Override
    public void modify(Firm request)
    {
        firmAdapter.modify(request);
    }
    
    @Override
    public void delete(Integer id)
    {
        firmAdapter.delete(id);
    }
    
    @Override
    public boolean validate(Firm request)
    {
        // TODO Auto-generated method stub
        return firmAdapter.validate(request);
    }
    
    @Override
    public void enableFirm(Firm request)
    {
        firmAdapter.enableFirm(request);
    }
    
    @Override
    public Pagination<Firm> selectFirm(Firm request)
    {
        return firmAdapter.selectFirm(request);
    }
    
}
