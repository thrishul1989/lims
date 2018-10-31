package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;

public interface BioInfoAnnotateMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(BioInfoAnnotate record);
    
    BioInfoAnnotate selectByPrimaryKey(String id);
    
    int update(BioInfoAnnotate record);
    
    BioInfoAnnotate getAnnotateBySampleCode(String dataCode);

    BioInfoAnnotate getByAnalaysisSampleId(String sampleId);
    
}