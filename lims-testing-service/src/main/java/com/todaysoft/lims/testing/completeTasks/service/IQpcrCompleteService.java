package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.qpcr.QpcrSheet;

public interface IQpcrCompleteService
{
    QpcrSheet getQpcrSheet(String id);
}
