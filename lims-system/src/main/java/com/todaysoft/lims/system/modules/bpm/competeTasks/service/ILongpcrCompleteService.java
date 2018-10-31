package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongqcSheet;

public interface ILongpcrCompleteService
{
    LongpcrSheet getLongpcrSheet(String id);
    
    LongqcSheet getLongqcSheet(String id);
}
