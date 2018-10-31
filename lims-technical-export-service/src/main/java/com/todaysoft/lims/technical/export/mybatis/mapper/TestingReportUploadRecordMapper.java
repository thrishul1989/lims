package com.todaysoft.lims.technical.export.mybatis.mapper;

import com.todaysoft.lims.technical.export.mybatis.model.TestingReportUploadRecord;

public interface TestingReportUploadRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestingReportUploadRecord record);

    int insertSelective(TestingReportUploadRecord record);

    TestingReportUploadRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestingReportUploadRecord record);

    int updateByPrimaryKey(TestingReportUploadRecord record);
    
    int selectByReportIdAndUploadType(TestingReportUploadRecord record);
}