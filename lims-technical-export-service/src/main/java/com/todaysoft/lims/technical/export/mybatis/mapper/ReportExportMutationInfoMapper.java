package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportPicture;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerReportSample;
import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;

public interface ReportExportMutationInfoMapper {
    int insert(ReportExportMutationInfo record);

    int insertSelective(ReportExportMutationInfo record);
    
    ReportExportMutationInfo selectByPrimaryKey(String id);
    
    List<ReportExportMutationInfo> selectByTaskId(String taskId);
    
    void deleteByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ReportExportMutationInfo record);

    int updateByPrimaryKey(ReportExportMutationInfo record);
    
    List<MutationFamilySangerResultInfo> selectMutationFamilySangerResultInfoByTaskId(@Param("taskId")String taskId);
    
    List<MutationFamilySangerReportSample> selectMutationFamilySangerReportSampleByMutationObjectId(@Param("mutationObjectId")String mutationObjectId);
   
    List<MutationFamilySangerReportPicture> selectMutationFamilySangerPictureByCombineCode(@Param("combineCode")String combineCode);
    
}