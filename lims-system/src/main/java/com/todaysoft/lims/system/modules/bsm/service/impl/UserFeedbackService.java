package com.todaysoft.lims.system.modules.bsm.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.UserFeedback;
import com.todaysoft.lims.system.modules.bsm.model.UserFeedbackSearcher;
import com.todaysoft.lims.system.modules.bsm.service.IUserFeedbackService;
import com.todaysoft.lims.system.modules.bsm.service.request.UserFeedbackListRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class UserFeedbackService extends RestService implements IUserFeedbackService
{
    @Override
    public Pagination<UserFeedback> paging(UserFeedbackSearcher seacher, int pageNo, int pageSize)
    {
        UserFeedbackListRequest request = new UserFeedbackListRequest();
        BeanUtils.copyProperties(seacher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bsm/userFeedback/paging");
        ResponseEntity<Pagination<UserFeedback>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<UserFeedbackListRequest>(request),
                new ParameterizedTypeReference<Pagination<UserFeedback>>()
                {
                });
        return exchange.getBody();
    }
    
}
