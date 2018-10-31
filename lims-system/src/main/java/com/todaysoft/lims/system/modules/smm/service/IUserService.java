package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.request.UserCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserPasswordResetRequest;

public interface IUserService
{
    Pagination<UserSimpleModel> paging(UserSearcher searcher, int pageNo, int pageSize);
    
    List<UserSimpleModel> list(UserSearcher searcher);
    
    List<UserSimpleModel> noSalesManList(UserSearcher searcher);
    
    List<UserSimpleModel> userSelect(UserListRequest request);
    
    boolean unique(String username);
    
    UserDetailsModel getUserByID(String id);
    
    void create(UserCreateRequest request);
    
    void modify(UserModifyRequest request);
    
    void resetPassword(UserPasswordResetRequest request);
    
    Integer delete(String id);
    
    void setEnable(String id);
    
    void setDisable(String id);
    
    AuthorizedUser getByToken();
    
    boolean validate(UserSearcher searcher);
    
    boolean validate(String id);
    
    List<UserSimpleModel> selectList(UserSearcher searcher);
    
    boolean uniqueNum(String employeeNo, String id);
}
