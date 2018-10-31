package com.todaysoft.lims.sample.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.MeetingApplySearcher;
import com.todaysoft.lims.sample.entity.meeting.MeetingApply;
import com.todaysoft.lims.sample.model.meeting.MeetingApplyResponseRequest;
import com.todaysoft.lims.sample.service.IMeetingApplyService;

@RestController
@RequestMapping("/bmm/meetingApply")
public class MeetingApplyController
{
    @Autowired
    private IMeetingApplyService meetingApplyService;
    
    @RequestMapping(value = "/meetingList")
    public Pagination<MeetingApply> paging(@RequestBody MeetingApplySearcher request)
    {
        Pagination<MeetingApply> p = meetingApplyService.paging(request);
        return p;
    }
    
    @RequestMapping(value = "/getMeetingApply/{id}")
    public MeetingApply getMeetingApply(@PathVariable String id)
    {
        return meetingApplyService.getMeetingApply(id);
    }
    
    /**
     * 新增会议响应
     * 
     */
    @RequestMapping(value = "/updateResponse", method = RequestMethod.POST)
    public void update(@RequestBody MeetingApplyResponseRequest request)
    {
        meetingApplyService.updateResponse(request);
    }
}
