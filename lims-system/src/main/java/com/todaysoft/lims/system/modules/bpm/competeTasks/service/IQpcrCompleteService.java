package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.qpcr.QpcrSheet;

public interface IQpcrCompleteService
{
    QpcrSheet getQpcrSheet(String id);
}
