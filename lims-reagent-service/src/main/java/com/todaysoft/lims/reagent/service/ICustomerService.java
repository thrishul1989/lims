package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CustomerRelationSearcher;
import com.todaysoft.lims.reagent.dao.searcher.CustomerSearcher;
import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.reagent.model.request.CustomerInfoModel;

public interface ICustomerService
{
    
    Pagination<Customer> paging(CustomerSearcher request);
    
    Pagination<Customer> customerByTestingType(String testingType, String name, Integer pageNo, Integer pageSize);
    
    List<Customer> list(CustomerSearcher request);
    
    List<Customer> subCustomerList(CustomerSearcher request);
    
    Customer get(String id);
    
    Customer getByNameAndNum(CustomerSearcher request);
    
    BusinessInfo getBusiByCusAndType(String customerId, String testingType);
    
    BusinessInfo getBusiness(String id);
    
    String create(CustomerSearcher request);
    
    void modify(CustomerSearcher request);
    
    void delete(String id);
    
    void enableCustomer(CustomerSearcher request);
    
    boolean validate(CustomerSearcher request);
    
    List<Customer> getCustomers(List<Integer> ids);
    
    Integer countSubCustomer(String id);
    
    Integer removeBusiness(CustomerRelationSearcher request);
    
    Integer addCustomerRelation(CustomerRelationSearcher request);
    
    List<BusinessInfo> businessList(BusinessInfo request);
    
    List<Customer> getCustormersByids(String ids);
    
    List<BusinessInfo> getBusinesses(BusinessInfo request);
    
    List<Customer> listBySome(CustomerSearcher request);
    
    List<Customer> listActivity(CustomerSearcher request);
    
    List<BusinessInfo> getBusinessesByTestingType(CustomerSearcher request);

    boolean validateType(CustomerSearcher request);

    void allUpdate(CustomerSearcher request);

    void allRemove(CustomerSearcher request);

    List<CustomerInfoModel> getCustomerForCustomerInfoList(CustomerSearcher request);
}
