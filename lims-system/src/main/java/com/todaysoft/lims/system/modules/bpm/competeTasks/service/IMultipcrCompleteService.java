package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.multipcr.MultipcrQcSheet;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;

public interface IMultipcrCompleteService
{
    MultipcrSubmitModel getMultipcrSubmitModel(String id);
    
    MultipcrQcSheet getMultipcrQcSheet(String id);
}
