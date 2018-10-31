package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongqcSheet;

public interface ILongpcrCompleteService
{
    LongpcrSheet getLongpcrSheet(String id);
    
    LongqcSheet getLongqcSheet(String id);
}
