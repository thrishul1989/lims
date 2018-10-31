package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.model.BiologyAnalysisSearchSheet;
import com.todaysoft.lims.technical.model.TestingSheetRequest;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisSheet;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;

public interface TechnicalAnalysisTaskMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TechnicalAnalysisTask record);
    
    int insertSelective(TechnicalAnalysisTask record);
    
    TechnicalAnalysisTask selectByPrimaryKey(String id);
    
    List<TechnicalAnalysisTask> selectBySearcher(TechnicalAnalysisTask searcher);
    
    List<TechnicalAnalysisTask> selectCurrentTasks(TechnicalAnalysisTask searcher);
    
    int updateByPrimaryKeySelective(TechnicalAnalysisTask record);
    
    int updateByPrimaryKey(TechnicalAnalysisTask record);
    
    List<String> selectDistinctGroupId(TechnicalAnalysisTask searcher);
    
    void submitTechnicalTaskBy(String familyGroupId, String status);
    
    List<TechnicalAnalysisTask> getTaskByFamilyId(String familyAnalysisId);
    
    List<BiologyAnalysisSearchSheet> completeSheetPaging(TestingSheetRequest request);
    
    List<BiologyAnalysisSearchSheet> completeSheetPage(TestingSheetRequest request);
    
    List<String> getNextVal(String name);
    
    String getTesterName(String id);
    
    int insertSheet(TechnicalAnalysisSheet technicalAnalysisSheet);
    
    int updataSheet(TechnicalAnalysisSheet technicalAnalysisSheet);
    
    String getFamily(String id);
    
    TechnicalAnalysisTask getSheet(String id);
    
    List<TechnicalAnalysisTask> searchErrorTechnicalTask(String familyAnalysisId);
    
    List<TechnicalAnalysisTask> getTaskByFamilyIdIncludeError(String familyAnalysisId);
    
}