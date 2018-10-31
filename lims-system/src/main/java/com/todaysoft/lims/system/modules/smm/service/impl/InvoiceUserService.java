package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class InvoiceUserService extends RestService implements IInvoiceUserService
{
    
    @Override
    public Pagination<InvoiceUser> paging(InvoiceUser searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/paging");
        ResponseEntity<Pagination<InvoiceUser>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceUser>(searcher), new ParameterizedTypeReference<Pagination<InvoiceUser>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public InvoiceUser get(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/{id}");
        return template.getForObject(url, InvoiceUser.class, id);
    }
    
    @Override
    public void create(InvoiceUser request)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/create");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(InvoiceUser request)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/modify");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/{id}");
        template.delete(url, id);
    }
    
    @Override
    public boolean validateUser(InvoiceUser request)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/validateUser");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public boolean validateInstitution(String testInstitution)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/validateInstitution/{testInstitution}");
        return template.getForObject(url, boolean.class, testInstitution);
    }
    
    @Override
    public InvoiceUser getByInstitution(String testInstitution)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/getByInstitution/{testInstitution}");
        return template.getForObject(url, InvoiceUser.class, testInstitution);
    }
    
    @Override
    public List<InvoiceUserModel> findUserByName(InvoiceUserModel searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/findUserByName");
        ResponseEntity<List<InvoiceUserModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceUserModel>(searcher), new ParameterizedTypeReference<List<InvoiceUserModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public InvoiceUserModel getUser(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/user/{id}");
        return template.getForObject(url, InvoiceUserModel.class, id);
    }
    
    @Override
    public InvoiceUser getInvoiceUserByUserId(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/invoiceUser/getInvoiceUserByUserId/{id}");
        return template.getForObject(url, InvoiceUser.class, id);
    }
}
