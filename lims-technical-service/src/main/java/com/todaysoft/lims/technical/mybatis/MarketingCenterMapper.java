package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.MarketingCenter;

public interface MarketingCenterMapper {
    int deleteByPrimaryKey(String id);

    int insert(MarketingCenter record);

    int insertSelective(MarketingCenter record);

    MarketingCenter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MarketingCenter record);

    int updateByPrimaryKey(MarketingCenter record);
}