package com.todaysoft.lims.system.modules.bmm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.MeetingApply;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplyResponseRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplySearcher;

public interface IMeetingApplyService
{
    Pagination<MeetingApply> paging(MeetingApplySearcher searchers);
    
    MeetingApply getMeetingApply(String id);
    
    void updateResponse(MeetingApplyResponseRequest request);
}
