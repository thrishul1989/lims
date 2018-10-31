package com.todaysoft.lims.technical.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.technical.model.request.MutationCollectionRequest;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecord;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecordWithBLOBs;

public interface TechnicalAnalyRecordMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TechnicalAnalyRecordWithBLOBs record);
    
    int insertSelective(TechnicalAnalyRecordWithBLOBs record);
    
    TechnicalAnalyRecordWithBLOBs selectByPrimaryKey(String id);
    
    TechnicalAnalyRecord selectRecordByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(TechnicalAnalyRecordWithBLOBs record);
    
    int updateByPrimaryKeyWithBLOBs(TechnicalAnalyRecordWithBLOBs record);
    
    int updateByPrimaryKey(TechnicalAnalyRecord record);
    
    List<TechnicalAnalyRecordWithBLOBs> getByObjectId(String id);
    
    List<TechnicalAnalyRecordWithBLOBs> getByCnvResultId(String id);
    
    List<TechnicalAnalyRecord> selectByFamilyId(String analysisFamilyId);
    
    List<String> getIDsByCnvResultId(String id);
    
    List<String> myCollectionCount(String analysisSampleId, @Param("technicalTaskId") String technicalTaskId);
    
    void delByMutationObjId(String mutationId);
    
    List<TechnicalAnalyRecord> selectByMutationId(String objectId);
    
    List<TechnicalAnalyRecord> selectByFamilyIdAndIds(Map<String, Object> params);
    
    List<TechnicalAnalyRecord> selectByCondition(MutationCollectionRequest request);
}