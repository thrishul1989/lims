package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.ReceivedSample;

public interface ReceivedSampleMapper {
    int deleteByPrimaryKey(String sampleId);

    int insert(ReceivedSample record);

    int insertSelective(ReceivedSample record);

    ReceivedSample selectByPrimaryKey(String sampleId);

    int updateByPrimaryKeySelective(ReceivedSample record);

    int updateByPrimaryKey(ReceivedSample record);

    ReceivedSample selectBySampleCode(String sampleCode);

}