package com.todaysoft.lims.reagent.action;

import java.util.List;

import com.todaysoft.lims.reagent.model.request.CustomerInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CustomerRelationSearcher;
import com.todaysoft.lims.reagent.dao.searcher.CustomerSearcher;
import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.reagent.service.ICustomerService;

@RestController
@RequestMapping("/bsm/customer")
public class CustomerController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Customer> paging(@RequestBody CustomerSearcher request)
    {
        return customerService.paging(request);
    }
    
    @RequestMapping(value = "/customerByTestingType", method = RequestMethod.POST)
    public Pagination<Customer> customerByTestingType(@RequestBody CustomerSearcher request)
    {
        return customerService.customerByTestingType(request.getTestingType(), request.getRealName(), request.getPageNo(), request.getPageSize());
    }
    
    @RequestMapping(value = "/businessList", method = RequestMethod.POST)
    public List<BusinessInfo> businessList(@RequestBody BusinessInfo request)
    {
        return customerService.businessList(request);
    }
    
    @RequestMapping(value = "/subCustomerList", method = RequestMethod.POST)
    public List<Customer> subCustomerList(@RequestBody CustomerSearcher request)
    {
        return customerService.subCustomerList(request);
    }
    
    @RequestMapping(value = "/countSubCustomer", method = RequestMethod.POST)
    public Integer countSubCustomer(@RequestBody CustomerSearcher request)
    {
        return customerService.countSubCustomer(request.getId());
    }
    
    @RequestMapping(value = "/removeBusiness", method = RequestMethod.POST)
    public Integer removeBusiness(@RequestBody CustomerRelationSearcher request)
    {
        return customerService.removeBusiness(request);
    }
    
    @RequestMapping(value = "/addCustomerRelation", method = RequestMethod.POST)
    public Integer addCustomerRelation(@RequestBody CustomerRelationSearcher request)
    {
        return customerService.addCustomerRelation(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Customer> list(@RequestBody CustomerSearcher request)
    {
        return customerService.list(request);
    }
    
    @RequestMapping(value = "/listActivity", method = RequestMethod.POST)
    public List<Customer> listActivity(@RequestBody CustomerSearcher request)
    {
        return customerService.listActivity(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer get(@PathVariable String id)
    {
        return customerService.get(id);
    }
    
    @RequestMapping(value = "/getByNameAndNum", method = RequestMethod.POST)
    public Customer getByNameAndNum(@RequestBody CustomerSearcher request)
    {
        return customerService.getByNameAndNum(request);
    }
    
    @RequestMapping(value = "/getBusiByCusAndType", method = RequestMethod.GET)
    public BusinessInfo getBusiByCusAndType(String customerId, String testingType)
    {
        return customerService.getBusiByCusAndType(customerId, testingType);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody CustomerSearcher request)
    {
        return customerService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody CustomerSearcher request)
    {
        customerService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        customerService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody CustomerSearcher request)
    {
        return customerService.validate(request);
    }
    
    @RequestMapping(value = "/enableCustomer", method = RequestMethod.POST)
    public void enableCustomer(@RequestBody CustomerSearcher request)
    {
        customerService.enableCustomer(request);
    }
    
    @RequestMapping("/getCustomers")
    public List<Customer> getCustomers(@RequestBody List<Integer> ids)
    {
        return customerService.getCustomers(ids);
    }
    
    @RequestMapping("/getCustormersByids/{ids}")
    public List<Customer> getCustormersByids(@PathVariable String ids)
    {
        return customerService.getCustormersByids(ids);
    }
    
    @RequestMapping(value = "/getBusinesses", method = RequestMethod.POST)
    public List<BusinessInfo> getBusinesses(@RequestBody BusinessInfo request)
    {
        return customerService.getBusinesses(request);
    }
    
    @RequestMapping(value = "/getBusinessesByTestingType", method = RequestMethod.POST)
    public List<BusinessInfo> getBusinessesByTestingType(@RequestBody CustomerSearcher request)
    {
        return customerService.getBusinessesByTestingType(request);
    }
    
    @RequestMapping(value = "/getBusiness/{id}", method = RequestMethod.GET)
    public BusinessInfo getBusiness(@PathVariable String id)
    {
        return customerService.getBusiness(id);
    }
    
    @RequestMapping(value = "/listBySome", method = RequestMethod.POST)
    public List<Customer> listBySome(@RequestBody CustomerSearcher request)
    {
        return customerService.listBySome(request);
    }

    @RequestMapping(value = "/validateType", method = RequestMethod.POST)
    public boolean validateType(@RequestBody CustomerSearcher request)
    {
            return customerService.validateType(request);
    }

    @RequestMapping(value = "/allUpdate", method = RequestMethod.POST)
    public void allUpdate(@RequestBody CustomerSearcher request){
        customerService.allUpdate(request);
    }

    @RequestMapping(value = "/allRemove", method = RequestMethod.POST)
    public void allRemove(@RequestBody CustomerSearcher request){
        customerService.allRemove(request);
    }

    @RequestMapping(value = "/getCustomerForCustomerInfoList")
    private List<CustomerInfoModel> getCustomerForCustomerInfoList(@RequestBody CustomerSearcher request)
    {
        return customerService.getCustomerForCustomerInfoList(request);
    }

}
