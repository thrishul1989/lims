package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.MlpaVerifyRecord;

public interface MlpaVerifyRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(MlpaVerifyRecord record);

    int insertSelective(MlpaVerifyRecord record);

    MlpaVerifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MlpaVerifyRecord record);

    int updateByPrimaryKey(MlpaVerifyRecord record);
}