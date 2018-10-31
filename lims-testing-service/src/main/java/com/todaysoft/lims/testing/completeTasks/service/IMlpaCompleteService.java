package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.mlpa.MlpaDataSheet;

public interface IMlpaCompleteService
{
    MlpaDataSheet getMlpaDataSheet(String id);
}
