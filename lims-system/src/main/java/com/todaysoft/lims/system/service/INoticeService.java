package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.NoticeSearcher;
import com.todaysoft.lims.system.model.vo.Notice;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface INoticeService
{
    
    Pagination<Notice> paging(NoticeSearcher searcher, int pageNo, int pagesize);
    
    Notice get(String id);
    
}
