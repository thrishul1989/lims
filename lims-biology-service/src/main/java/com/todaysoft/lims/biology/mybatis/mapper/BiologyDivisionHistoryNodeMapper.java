package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyDivisionHistoryNode;

public interface BiologyDivisionHistoryNodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyDivisionHistoryNode record);

    int insertSelective(BiologyDivisionHistoryNode record);

    BiologyDivisionHistoryNode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyDivisionHistoryNode record);

    int updateByPrimaryKey(BiologyDivisionHistoryNode record);
}