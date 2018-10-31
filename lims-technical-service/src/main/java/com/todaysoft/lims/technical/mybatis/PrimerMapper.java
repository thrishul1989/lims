package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.Primer;

public interface PrimerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Primer record);

    int insertSelective(Primer record);

    Primer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Primer record);

    int updateByPrimaryKey(Primer record);
}