package com.todaysoft.lims.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ScheduleSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Schedule;
import com.todaysoft.lims.system.service.IScheduleService;

@Service
public class Scheduleservice extends RestService implements IScheduleService
{
    
    @Override
    public Pagination<Schedule> paging(ScheduleSearcher searcher, int pageNo, int pagesize)
    {
        
        List<Schedule> list = new ArrayList<Schedule>();
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        list.add(new Schedule("1", "1", new Date()));
        Pagination<Schedule> pag = new Pagination<Schedule>();
        pag.setRecords(list);
        return pag;
    }
    
    @Override
    public Schedule get(String id)
    {
        Schedule data = new Schedule("2", "2", new Date());
        return data;
    }
}
