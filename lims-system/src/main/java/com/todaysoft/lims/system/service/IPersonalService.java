package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.NoticeSearcher;
import com.todaysoft.lims.system.model.vo.Schedule;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IPersonalService
{
    
    Pagination<Schedule> paging(NoticeSearcher searcher, int pageNo, int pagesize);
    
    Schedule getNoticeById(String id);
    
    List<Schedule> list(Schedule request);
    
    Integer modify(Schedule request);
    
    Integer create(Schedule request);
    
    void delete(String id);
    
}
