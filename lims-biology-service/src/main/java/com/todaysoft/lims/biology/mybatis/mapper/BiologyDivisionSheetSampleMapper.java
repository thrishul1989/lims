package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyDivisionSheetSample;

import java.util.List;

public interface BiologyDivisionSheetSampleMapper {

    int deleteByPrimaryKey(String id);

    int insert(BiologyDivisionSheetSample record);

    int insertSelective(BiologyDivisionSheetSample record);

    BiologyDivisionSheetSample selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyDivisionSheetSample record);

    int updateByPrimaryKey(BiologyDivisionSheetSample record);

    List<BiologyDivisionSheetSample> getTaskOperateInfoById(String id);

    List<BiologyDivisionSheetSample> getSamplesBySheetId(String id);
}