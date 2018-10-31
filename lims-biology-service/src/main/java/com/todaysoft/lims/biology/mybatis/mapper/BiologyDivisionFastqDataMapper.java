package com.todaysoft.lims.biology.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.request.MaintainDivisionDataRequest;

public interface BiologyDivisionFastqDataMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(BiologyDivisionFastqData record);
    
    int insertSelective(BiologyDivisionFastqData record);
    
    BiologyDivisionFastqData selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(BiologyDivisionFastqData record);
    
    int updateByPrimaryKey(BiologyDivisionFastqData record);
    
    List<BiologyDivisionFastqData> selectByExample(MaintainDivisionDataRequest request);

    BiologyDivisionFastqData getFastDataByDataCode(String dataCode);

    BiologyDivisionFastqData selectByDataCode(String dataCode);
}