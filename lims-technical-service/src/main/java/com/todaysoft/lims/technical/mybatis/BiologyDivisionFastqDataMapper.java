package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.model.searcher.SampleSearcher;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.CompareSample;
import com.todaysoft.lims.technical.mybatis.entity.SampleSex;
import com.todaysoft.lims.technical.mybatis.parameter.CompareSampleSearch;

public interface BiologyDivisionFastqDataMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(BiologyDivisionFastqData record);
    
    int insertSelective(BiologyDivisionFastqData record);
    
    BiologyDivisionFastqData selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(BiologyDivisionFastqData record);
    
    int updateByPrimaryKey(BiologyDivisionFastqData record);
    
    BiologyDivisionFastqData getBySampleId(String sampleId);
    
    BiologyDivisionFastqData selectByDataCode(String dataCode);
    
    List<CompareSample> selectCompareSample(CompareSampleSearch compareSampleSearch);
    
    SampleSex getPrimarySampleSexByCode(String sampleCode);
    
    SampleSex getSubSampleSexByCode(String sampleCode);
    
    List<BiologyDivisionFastqData> search(SampleSearcher searcher);
}