package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.QpcrVerifyRecord;

public interface QpcrVerifyRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(QpcrVerifyRecord record);

    int insertSelective(QpcrVerifyRecord record);

    QpcrVerifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QpcrVerifyRecord record);

    int updateByPrimaryKey(QpcrVerifyRecord record);
}