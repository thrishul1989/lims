package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.UserAchive;

public interface UserAchiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserAchive record);

    int insertSelective(UserAchive record);

    UserAchive selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserAchive record);

    int updateByPrimaryKey(UserAchive record);
}