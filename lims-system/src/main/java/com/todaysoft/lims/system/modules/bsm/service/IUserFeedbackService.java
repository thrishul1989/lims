package com.todaysoft.lims.system.modules.bsm.service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.UserFeedback;
import com.todaysoft.lims.system.modules.bsm.model.UserFeedbackSearcher;

public interface IUserFeedbackService
{
    Pagination<UserFeedback> paging(UserFeedbackSearcher seacher, int pageNo, int pageSize);
}
