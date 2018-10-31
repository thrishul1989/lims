package com.todaysoft.lims.system.modules.bmm.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.MeetingApply;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplyResponseRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.MeetingApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.IMeetingApplyService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MeetingApplyService extends RestService implements IMeetingApplyService
{
    
    @Override
    public Pagination<MeetingApply> paging(MeetingApplySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/meetingApply/meetingList");
        ResponseEntity<Pagination<MeetingApply>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<MeetingApplySearcher>(searcher),
                new ParameterizedTypeReference<Pagination<MeetingApply>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public MeetingApply getMeetingApply(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/meetingApply/getMeetingApply/{id}");
        return template.getForObject(url, MeetingApply.class, id);
    }
    
    @Override
    @SystemServiceLog(description="会议申请管理-会议响应",type=6)
    public void updateResponse(MeetingApplyResponseRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/meetingApply/updateResponse");
        template.postForObject(url, request, Boolean.class);
    }
}
