package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.UserFeedbackSearcher;
import com.todaysoft.lims.reagent.entity.UserFeedback;

public interface IUserFeedbackService
{
    
    Pagination<UserFeedback> paging(UserFeedbackSearcher request);
    
}
