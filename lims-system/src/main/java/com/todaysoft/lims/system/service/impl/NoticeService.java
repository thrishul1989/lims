package com.todaysoft.lims.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.NoticeSearcher;
import com.todaysoft.lims.system.model.vo.Notice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.INoticeService;

@Service
public class NoticeService extends RestService implements INoticeService
{
    
    @Override
    public Pagination<Notice> paging(NoticeSearcher searcher, int pageNo, int pagesize)
    {
        List<Notice> list = new ArrayList<Notice>();
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        list.add(new Notice("1", "1", "1", "1", new Date()));
        Pagination<Notice> pag = new Pagination<Notice>();
        pag.setRecords(list);
        return pag;
    }
    
    @Override
    public Notice get(String id)
    {
        Notice data = new Notice("2", "2", "2", "2", new Date());
        return data;
    }
    
}
