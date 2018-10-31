package com.todaysoft.lims.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.InvoiceUserSearcher;
import com.todaysoft.lims.smm.entity.InvoiceUser;
import com.todaysoft.lims.smm.entity.InvoiceUserModel;

public interface IInvoiceUserService
{
    Pagination<InvoiceUser> paging(InvoiceUserSearcher searcher);
    
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
