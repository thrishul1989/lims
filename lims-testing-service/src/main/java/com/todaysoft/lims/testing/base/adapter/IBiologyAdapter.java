package com.todaysoft.lims.testing.base.adapter;

import com.todaysoft.lims.testing.base.entity.BiologyAnnotationTask;

public interface IBiologyAdapter
{
    void reTodoBiologyAnnotation(BiologyAnnotationTask request);

    void reTodoTechnicalAnalysis(String id);
}
