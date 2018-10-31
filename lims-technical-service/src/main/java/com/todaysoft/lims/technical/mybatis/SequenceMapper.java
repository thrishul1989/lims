package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.Sequence;

public interface SequenceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);

    Sequence selectByName(String string);
}