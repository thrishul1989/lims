package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.model.TechnicalAnalysisTaskFailOperate;

public interface TechnicalAnalysisTaskFailOperateMapper {
    int deleteByPrimaryKey(String id);

    int insert(TechnicalAnalysisTaskFailOperate record);

    int insertSelective(TechnicalAnalysisTaskFailOperate record);

    TechnicalAnalysisTaskFailOperate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TechnicalAnalysisTaskFailOperate record);

    int updateByPrimaryKey(TechnicalAnalysisTaskFailOperate record);
}