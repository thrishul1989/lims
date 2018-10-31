package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.UserInform;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;

public interface IUserService
{
    UserDetailsModel getUserByID(String id);
    
    Pagination<UserDetailsModel> selectUser(UserListRequest req, int pageNo, int pageSize);
    
    List<UserDetailsModel> getUserDetailsModelList(UserListRequest request);
    
    Pagination<UserInform> informPaging(UserSearcher searcher, int pageNo, int defaultpagesize);
    
    User getUserByToken();
}
