package com.todaysoft.lims.technical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.technical.mybatis.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.service.IBiologyDivisionDataService;

@Service
public class BiologyDivisionDataServiceImpl implements IBiologyDivisionDataService
{
    @Autowired
    private BiologyDivisionFastqDataMapper mapper;
    
    @Override
    public BiologyDivisionFastqData getByDataCode(String dataCode)
    {
        return mapper.selectByDataCode(dataCode);
    }
    
}
