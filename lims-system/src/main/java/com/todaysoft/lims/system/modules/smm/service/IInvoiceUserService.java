package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;

public interface IInvoiceUserService
{
    Pagination<InvoiceUser> paging(InvoiceUser searcher);
    
    InvoiceUser get(String id);
    
    void create(InvoiceUser request);
    
    void modify(InvoiceUser request);
    
    void delete(String id);
    
    boolean validateUser(InvoiceUser request);
    
    boolean validateInstitution(String testInstitution);
    
    InvoiceUser getByInstitution(String testInstitution);
    
    List<InvoiceUserModel> findUserByName(InvoiceUserModel searcher);
    
    InvoiceUserModel getUser(String id);
    
    InvoiceUser getInvoiceUserByUserId(String id);
}
