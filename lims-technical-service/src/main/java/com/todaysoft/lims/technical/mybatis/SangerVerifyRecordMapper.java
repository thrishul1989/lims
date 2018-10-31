package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.SangerVerifyRecord;

public interface SangerVerifyRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SangerVerifyRecord record);

    int insertSelective(SangerVerifyRecord record);

    SangerVerifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SangerVerifyRecord record);

    int updateByPrimaryKey(SangerVerifyRecord record);

    List<SangerVerifyRecord> selectByRecord(String chromosome, String beginLocus, String method);
}