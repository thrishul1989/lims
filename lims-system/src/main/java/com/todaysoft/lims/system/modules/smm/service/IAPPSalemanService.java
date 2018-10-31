package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.APPSaleman;
import com.todaysoft.lims.system.modules.smm.model.APPSalemanSearcher;

public interface IAPPSalemanService
{
    Pagination<BusinessInfo> paging(APPSalemanSearcher searcher, int pageNo, int pageSize);
    
    List<String> list(APPSalemanSearcher searcher);
    
    APPSaleman get(String id);

    List<APPSaleman> getAppSaleByids(String ids);
    void modify(APPSaleman data);
    
    void create(APPSaleman data);
    
    BusinessInfo getBusinessInfo(String id);
    
    void setEnable(String id);
    
    void setDisable(String id);
    
    List<BusinessInfo> listAll(APPSalemanSearcher searcher);
    
    boolean getDataById(String userId);
    
    boolean getCustomerByAppId(String appId);
    
}
