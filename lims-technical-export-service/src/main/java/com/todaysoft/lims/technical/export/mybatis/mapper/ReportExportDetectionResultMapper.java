package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;

public interface ReportExportDetectionResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReportExportDetectionResult record);

    int insertSelective(ReportExportDetectionResult record);

    ReportExportDetectionResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExportDetectionResult record);

    int updateByPrimaryKey(ReportExportDetectionResult record);
    
    List<ReportExportDetectionResult> selectByTaskIdAndClinicalJudgmentAndMethodName(ReportExportDetectionResult record);

    List<ReportExportDetectionResultPicture> selectDetectionResultPictureByTestingTaskId(@Param("id")String id);

}