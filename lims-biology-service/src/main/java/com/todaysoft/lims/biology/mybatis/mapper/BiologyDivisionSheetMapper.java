package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheet;
import com.todaysoft.lims.biology.model.TestingSheetRequest;

import java.util.List;

public interface BiologyDivisionSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyDivisionSheet record);

    int insertSelective(BiologyDivisionSheet record);

    BiologyDivisionSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyDivisionSheet record);

    int updateByPrimaryKey(BiologyDivisionSheet record);

    int getFalilTaskCount(String taskId);

    List<BiologyDivisionSheet> getSheetListByTaskId(String id);

    List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request);

}