package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;

public interface IBiologyDivisionDataService
{
    
    BiologyDivisionFastqData getByDataCode(String dataCode);
    
}
