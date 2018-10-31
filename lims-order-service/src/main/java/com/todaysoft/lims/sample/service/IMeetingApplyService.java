package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MeetingApplySearcher;
import com.todaysoft.lims.sample.entity.meeting.MeetingApply;
import com.todaysoft.lims.sample.model.meeting.MeetingApplyResponseRequest;

public interface IMeetingApplyService
{
    
    Pagination<MeetingApply> paging(MeetingApplySearcher request);
    
    MeetingApply getMeetingApply(String id);
    
    void updateResponse(MeetingApplyResponseRequest request);
}
