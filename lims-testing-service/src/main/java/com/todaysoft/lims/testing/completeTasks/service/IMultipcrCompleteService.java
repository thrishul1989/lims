package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.multipcr.MultipcrQcSheet;

public interface IMultipcrCompleteService
{
    MultipcrQcSheet getMultipcrQcSheet(String id);
}
