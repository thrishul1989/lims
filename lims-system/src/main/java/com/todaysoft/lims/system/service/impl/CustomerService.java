package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.model.vo.order.customerOrderReportForm.CustomerInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.CustomerRelation;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.SystemServiceLog;

@Service
public class CustomerService extends RestService implements ICustomerService
{
    
    @Override
    public Pagination<Customer> paging(Customer searcher, int pageNo, int pageSize)
    {
        Customer request = new Customer();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/customer/paging");
        ResponseEntity<Pagination<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(request), new ParameterizedTypeReference<Pagination<Customer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    @SystemServiceLog(description="客户管理-修改",type=4)
    public void modify(Customer request)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/customer/modify");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Customer get(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/customer/{id}");
        return template.getForObject(url, Customer.class, Collections.singletonMap("id", id));
    }
    
    @Override
    @SystemServiceLog(description="客户管理-删除",type=4)
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public void create(Customer request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(Customer request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public void enableCustomer(Customer request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/enableCustomer");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Integer countSubCustomer(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/countSubCustomer");
        return template.postForObject(url, Collections.singletonMap("id", id), Integer.class);
    }
    
    @Override
    @SystemServiceLog(description="客户管理-业务员解绑",type=4)
    public Integer removeBusiness(CustomerRelation request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/removeBusiness");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<BusinessInfo> businessList(BusinessInfo request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/businessList");
        ResponseEntity<List<BusinessInfo>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<BusinessInfo>(request), new ParameterizedTypeReference<List<BusinessInfo>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    @SystemServiceLog(description="客户管理-业务员绑定",type=4)
    public Integer addCustomerRelation(CustomerRelation request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/addCustomerRelation");
        return template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<Customer> subCustomerList(Customer request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/subCustomerList");
        ResponseEntity<List<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(request), new ParameterizedTypeReference<List<Customer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Customer> list(Customer searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/list");
        ResponseEntity<List<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(searcher), new ParameterizedTypeReference<List<Customer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Customer> listActivity(Customer searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/listActivity");
        ResponseEntity<List<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(searcher), new ParameterizedTypeReference<List<Customer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Customer> getCustormersByids(String ids)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getCustormersByids/{ids}");
        ResponseEntity<List<Customer>> exchange =
            template.exchange(url, HttpMethod.GET, new HttpEntity<String>(ids), new ParameterizedTypeReference<List<Customer>>()
            {
            }, Collections.singletonMap("ids", ids));
        return exchange.getBody();
    }
    
    @Override
    public List<BusinessInfo> getBusinesses(BusinessInfo request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getBusinesses");
        ResponseEntity<List<BusinessInfo>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<BusinessInfo>(request), new ParameterizedTypeReference<List<BusinessInfo>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<BusinessInfo> getBusinessesByTestingType(BusinessInfo request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getBusinessesByTestingType");
        ResponseEntity<List<BusinessInfo>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<BusinessInfo>(request), new ParameterizedTypeReference<List<BusinessInfo>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public boolean validateType(Customer request) {
        String url = GatewayService.getServiceUrl("/bsm/customer/validateType");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }

    @Override
    public void allUpdate(Customer request) {
        String url = GatewayService.getServiceUrl("/bsm/customer/allUpdate");
        template.postForObject(url, request, String.class);
    }

    @Override
    public void allRemove(Customer request) {
        String url = GatewayService.getServiceUrl("/bsm/customer/allRemove");
        template.postForObject(url, request, String.class);
    }

    @Override
    public List<CustomerInfoModel> getCustomerForCustomerInfoList(Customer searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getCustomerForCustomerInfoList");
        ResponseEntity<List<CustomerInfoModel>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(searcher), new ParameterizedTypeReference<List<CustomerInfoModel>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public BusinessInfo getBusiByCusAndType(String customerId, String testingType)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getBusiByCusAndType?customerId=" + customerId + "&testingType=" + testingType + "");
        
        return template.getForObject(url, BusinessInfo.class, "");
    }
    
    @Override
    public Customer getByNameAndNum(Customer request)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getByNameAndNum");
        return template.postForObject(url, request, Customer.class);
    }
    
    @Override
    public BusinessInfo getBusiness(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/getBusiness/{id}");
        return template.getForObject(url, BusinessInfo.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<Customer> customerByTestingType(String testingType, String name, int pageNo, int pageSize)
    {
        
        Map req = new HashMap<>();
        req.put("testingType", testingType);
        req.put("realName", name);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        String url =
            GatewayService.getServiceUrl("/bsm/customer/customerByTestingType");
        ResponseEntity<Pagination<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(req), new ParameterizedTypeReference<Pagination<Customer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Customer> listBySome(Customer searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/customer/listBySome");
        ResponseEntity<List<Customer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(searcher), new ParameterizedTypeReference<List<Customer>>()
            {
            });
        return exchange.getBody();
    }
}
