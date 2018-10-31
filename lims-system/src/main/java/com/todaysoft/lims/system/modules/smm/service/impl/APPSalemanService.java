package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.APPSaleman;
import com.todaysoft.lims.system.modules.smm.model.APPSalemanSearcher;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class APPSalemanService extends RestService implements IAPPSalemanService
{
    
    @Override
    public Pagination<BusinessInfo> paging(APPSalemanSearcher searcher, int pageNo, int pageSize)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/appsaleman/paging");
        ResponseEntity<Pagination<BusinessInfo>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<UserListRequest>(request), new ParameterizedTypeReference<Pagination<BusinessInfo>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<String> list(APPSalemanSearcher searcher)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/smm/appsaleman/list");
        ResponseEntity<List<String>> exchange = template.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<String>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public List<BusinessInfo> listAll(APPSalemanSearcher searcher)
    {
        UserListRequest request = new UserListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/smm/appsaleman/listAll");
        ResponseEntity<List<BusinessInfo>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<UserListRequest>(request), new ParameterizedTypeReference<List<BusinessInfo>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public APPSaleman get(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/{id}");
        return template.getForObject(url, APPSaleman.class, Collections.singletonMap("id", id));
    }

    @Override
    public List<APPSaleman> getAppSaleByids(String ids) {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/getAppSaleByids/{ids}");
        ResponseEntity<List<APPSaleman>> exchange =
                template.exchange(url, HttpMethod.GET, new HttpEntity<String>(ids), new ParameterizedTypeReference<List<APPSaleman>>()
                {
                }, Collections.singletonMap("ids", ids));
        return exchange.getBody();
    }


    @Override
    @SystemServiceLog(description="APP业务员管理-修改",type=9)
    public void modify(APPSaleman data)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/modify");
        template.postForLocation(url, data);
    }
    
    @Override
    public void create(APPSaleman data)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/create");
        template.postForLocation(url, data);
    }
    
    @Override
    public BusinessInfo getBusinessInfo(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/getBusinessInfo/{id}");
        return template.getForObject(url, BusinessInfo.class, Collections.singletonMap("id", id));
    }
    
    @Override
    @SystemServiceLog(description="APP业务员管理-启用",type=9)
    public void setEnable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/enable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
    }
    
    @Override
    @SystemServiceLog(description="APP业务员管理-禁用",type=9)
    public void setDisable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/user/disable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean getDataById(String userId)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/getDataById/{userId}");
        return template.getForObject(url, boolean.class, Collections.singletonMap("userId", userId));
    }
    
    @Override
    public boolean getCustomerByAppId(String appId)
    {
        String url = GatewayService.getServiceUrl("/smm/appsaleman/getCustomerByAppId/{appId}");
        return template.getForObject(url, boolean.class, Collections.singletonMap("appId", appId));
    }
}
