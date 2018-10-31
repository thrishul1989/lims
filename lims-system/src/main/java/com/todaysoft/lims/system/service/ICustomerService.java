package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.CustomerRelation;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.customerOrderReportForm.CustomerInfoModel;

public interface ICustomerService
{
    
    Pagination<Customer> paging(Customer searcher, int pageNo, int pageSize);
    
    Pagination<Customer> customerByTestingType(String testingType, String name, int pageNo, int pageSize);
    
    void modify(Customer request);
    
    Customer get(String id);
    
    Customer getByNameAndNum(Customer request);
    
    void delete(String id);
    
    void create(Customer request);
    
    boolean validate(Customer request);
    
    void enableCustomer(Customer request);
    
    Integer countSubCustomer(String id);
    
    Integer removeBusiness(CustomerRelation request);
    
    Integer addCustomerRelation(CustomerRelation request);
    
    List<BusinessInfo> businessList(BusinessInfo request);
    
    List<Customer> subCustomerList(Customer request);
    
    List<Customer> list(Customer searcher);
    
    List<Customer> listActivity(Customer searcher);
    
    List<Customer> getCustormersByids(String ids);
    
    BusinessInfo getBusiByCusAndType(String customerId, String testingType);
    
    BusinessInfo getBusiness(String id);
    
    List<BusinessInfo> getBusinesses(BusinessInfo request);
    
    public List<Customer> listBySome(Customer request);
    
    List<BusinessInfo> getBusinessesByTestingType(BusinessInfo request);

    boolean validateType(Customer request);

    void allUpdate(Customer request);

    void allRemove(Customer request);

    List<CustomerInfoModel> getCustomerForCustomerInfoList(Customer searcher);
}
