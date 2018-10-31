package com.todaysoft.lims.biology.service;

import com.todaysoft.lims.biology.model.*;
import com.todaysoft.lims.biology.model.entity.ReceivedSample;

public interface IBioInfoAnnotateService
{
    Integer afterAnnotateForSave(BiologyAnnotationTask annotationTask, String... filePath);

    void familyAnnotateForSave(BiologyAnnotationFamilyTask familyTask, BiologyFamilyRelationAnnotate familyRelationAnnotate);

    OrderSampleView getOrderSampleViewBySampleId(String sampleld);

    ReceivedSample getReceivedSampleBySampleCode(String sampleCode);

    AbnormalSolveRecord getReSampleRecord(String scheduleId);

}
