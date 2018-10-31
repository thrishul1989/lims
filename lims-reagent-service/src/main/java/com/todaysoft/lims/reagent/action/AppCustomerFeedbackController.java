package com.todaysoft.lims.reagent.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.UserFeedbackSearcher;
import com.todaysoft.lims.reagent.entity.UserFeedback;
import com.todaysoft.lims.reagent.service.IUserFeedbackService;

@RestController
@RequestMapping("/bsm/userFeedback")
public class AppCustomerFeedbackController
{
    @Autowired
    private IUserFeedbackService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<UserFeedback> paging(@RequestBody UserFeedbackSearcher request)
    {
        return service.paging(request);
    }
    
}
