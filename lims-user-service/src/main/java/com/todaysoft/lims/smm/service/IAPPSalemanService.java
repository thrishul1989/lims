package com.todaysoft.lims.smm.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.APPSaleman;
import com.todaysoft.lims.smm.entity.BusinessInfo;
import com.todaysoft.lims.smm.request.APPSalemanRequest;

public interface IAPPSalemanService
{
    Pagination<BusinessInfo> paging(APPSalemanRequest request);
    
    List<String> list(APPSalemanRequest request);
    
    List<BusinessInfo> listAll(APPSalemanRequest request);
    
    APPSaleman get(String id);
    
    void modify(APPSaleman data);
    
    void create(APPSaleman data);
    
    BusinessInfo getBusinessInfo(String id);
    
    boolean getDataById(@PathVariable String userId);
    
    boolean getCustomerByAppId(String appId);
    List<APPSaleman> getAppSaleByids(String ids);
}
