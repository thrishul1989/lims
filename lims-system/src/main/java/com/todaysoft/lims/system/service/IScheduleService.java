package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ScheduleSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Schedule;

public interface IScheduleService
{
    
    Pagination<Schedule> paging(ScheduleSearcher searcher, int pageNo, int pagesize);
    
    Schedule get(String id);
    
}
