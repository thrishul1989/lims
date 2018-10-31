package com.todaysoft.lims.testing.base.service;

import com.todaysoft.lims.testing.base.entity.Sequence;

public interface ICommonService
{
    String getDNAExtractCode(int i);

    String getTestSampleCode(String sampleCode,String semantic);

    String getTaskCodeBySemantic(String semantic);

    String getFirstPcrSampleTempLocation(int i);

    String getPrimerTempLocation(int i);

    String getSecondPrimerLocation(int i);

    String getTechnicalAnalBatchNo();

    String getMlpaCode(int start,int i);
    
    Sequence getSequenceListByNameAndDate(String name);

    String getReportNoByName(String name);

}
