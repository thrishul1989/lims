package com.todaysoft.lims.reagent.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.reagent.entity.Dict;
import com.todaysoft.lims.reagent.model.request.CustomerInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CustomerRelationSearcher;
import com.todaysoft.lims.reagent.dao.searcher.CustomerSearcher;
import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.reagent.entity.CustomerRelation;
import com.todaysoft.lims.reagent.service.ICustomerService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class CustomerService implements ICustomerService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Customer> paging(CustomerSearcher request)
    {
        
        Pagination<Customer> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Customer.class);
        return paging;
    }
    
    @Override
    public List<Customer> list(CustomerSearcher request)
    {
        
        return baseDaoSupport.find(Customer.class, " SELECT c FROM Customer as c WHERE  c.delFlag = 0 and c.parentId is null and "
            + " c.disableStatus=0 and c.activateStatus=1");
    }
    
    @Override
    public Customer get(String id)
    {
        Customer c = baseDaoSupport.get(Customer.class, id);
        return c;
    }
    
    @Override
    @Transactional
    public String create(CustomerSearcher request)
    {
        
        //        Customer entity = new Customer();
        //        BeanUtils.copyProperties(request, entity);
        //        baseDaoSupport.insert(entity);
        //        return entity.getId();
        return "";
    }
    
    @Override
    @Transactional
    public void modify(CustomerSearcher request)
    {
        Customer entity = get(request.getId());
        BeanUtils.copyProperties(request,
            entity,
            "roleType",
            "password",
            "salt",
            "headImg",
            "address",
            "parentId",
            "registDate",
            "delFlag",
            "disableStatus",
            "activateStatus",
            "createId",
            "createDate",
            "updateDate");
        //        entity.builder().realName(request.getRealName()).sex(request.getSex()).birthday(request.getBirthday()).
        //        phoneNum(request.getPhoneNum()).company(request.getCompany()).roomNum(request.getRoomNum()).
        //        landLine(request.getLandLine()).email(request.getEmail()).dept(request.getDept()).position(request.getPosition()).
        //        cooperate(request.getCooperate()).analyseType(request.getAnalyseType()).build();
        
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Customer entity = get(id);
        entity.setDelFlag(1);
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validate(CustomerSearcher request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Customer.class, request, "phoneNum")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    @Transactional
    public void enableCustomer(CustomerSearcher request)
    {
        Customer entity = get(request.getId());
        if (1 == request.getDisableStatus())
        {
            entity.setDisableStatus(1);
        }
        else if (0 == request.getDisableStatus())
        {
            entity.setDisableStatus(0);
            
        }
        
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    public List<Customer> getCustomers(List<Integer> ids)
    {
        String hql = "FROM Customer u WHERE u.id IN :ids";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("ids")).values(Arrays.asList((Object)ids)).build();
        return baseDaoSupport.find(queryer, Customer.class);
        
    }
    
    @Override
    public Integer countSubCustomer(String id)
    {
        // TODO Auto-generated method stub
        List<Long> list = baseDaoSupport.find(Long.class, "select count(*) from Customer u where u.parentId='" + id + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0).intValue();
            
        }
        return 0;
    }
    
    @Override
    public Integer removeBusiness(CustomerRelationSearcher request)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.execute("delete from CustomerRelation cr where cr.customer.id='" + request.getCustomer().getId() + "' " + "and cr.business.id='"
            + request.getBusiness().getId() + "'");
    }
    
    @Override
    public List<BusinessInfo> businessList(BusinessInfo request)
    {
        // TODO Auto-generated method stub
        
        return baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b where b.testingType='" + request.getTestingType() + "' and  b.realName like '%"
            + request.getRealName() + "%' " + " and b.delFlag=0 and b.disableStatus=0");
        
    }
    
    @Override
    @Transactional
    public Integer addCustomerRelation(CustomerRelationSearcher request)
    {
        //先判断当前是否已经绑定过该类型的业务员
        BusinessInfo business = baseDaoSupport.get(BusinessInfo.class, request.getBusiness().getId());
        
        List<CustomerRelation> list =
            baseDaoSupport.find(CustomerRelation.class, "from CustomerRelation cr where cr.business.testingType='" + business.getTestingType() + "' "
                + " and cr.customer.id='" + request.getCustomer().getId() + "' and cr.business.delFlag = 0");
        if (Collections3.isNotEmpty(list))
        {
            return 0;
            
        }
        else
        {
            try
            {
                CustomerRelation customerRelation = new CustomerRelation();
                BusinessInfo bs = new BusinessInfo();
                bs.setId(request.getBusiness().getId());
                customerRelation.setBusiness(bs);
                Customer c = new Customer();
                c.setId(request.getCustomer().getId());
                customerRelation.setCustomer(c);
                customerRelation.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                baseDaoSupport.insert(customerRelation);
                return 1;
            }
            catch (Exception e)
            {
                return 3;
            }
            
        }
        
    }
    
    @Override
    public List<Customer> subCustomerList(CustomerSearcher request)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(Customer.class, "from Customer c where parentId='" + request.getId() + "'");
    }
    
    @Override
    public List<Customer> getCustormersByids(String ids)
    {
        List<Customer> list = Lists.newArrayList();
        for (String id : ids.split(","))
        {
            list.add(get(id));
        }
        return list;
    }
    
    @Override
    public List<BusinessInfo> getBusinesses(BusinessInfo request)
    {
        List<BusinessInfo> s = 
            baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b where b.realName like '%" + request.getRealName() + "%'"
        +" AND b.delFlag=0 and b.disableStatus=0");
        return s;
    }
    
    @Override
    public List<BusinessInfo> getBusinessesByTestingType(CustomerSearcher request)
    {
        List<BusinessInfo> s = 
            baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b where b.realName like '%" + request.getRealName() + "%'"
        +" AND b.delFlag=0 and b.disableStatus=0 AND b.testingType= '"+request.getTestingType()+"'");
        return s;
    }

    @Override
    public boolean validateType(CustomerSearcher request) {
        List<BusinessInfo> list = baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b WHERE b.realName = '"+ request.getCurrentName()+"'");
        if (Collections3.isNotEmpty(list)) {
            BusinessInfo businessInfo = Collections3.getFirst(list);
            if (businessInfo.getTestingType().equals(request.getTestingType())){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void allUpdate(CustomerSearcher request) {
        if(StringUtils.isNotEmpty(request.getKeys()))
        {
            for (String id : request.getKeys().split(","))
            {
                Customer cu = get(id);
                List<CustomerRelation> relationList = cu.getCustomerRelationList();
                if (Collections3.isNotEmpty(relationList)){
                    for (CustomerRelation cr : relationList){
                        if (cr.getBusiness().getRealName().equals(request.getCurrentName()))
                        {
                            BusinessInfo bb =baseDaoSupport.get(BusinessInfo.class,request.getBusiness());
                            cr.setBusiness(bb);
                            baseDaoSupport.update(cr);
                        }
                    }
                }
            }
        }

    }

    @Override
    @Transactional
    public void allRemove(CustomerSearcher request) {
        if(StringUtils.isNotEmpty(request.getKeys())) {
            for (String id : request.getKeys().split(",")) {
                Customer cu = get(id);
                List<CustomerRelation> relationList = cu.getCustomerRelationList();
                for (CustomerRelation cr : relationList) {
                    if (cr.getBusiness().getRealName().equals(request.getCurrentName())) {
                       baseDaoSupport.delete(cr);
                    }
                }
            }
        }

    }

    @Override
    public List<CustomerInfoModel> getCustomerForCustomerInfoList(CustomerSearcher request)
    {
        CustomerSearcher searcher = new CustomerSearcher();
        BeanUtils.copyProperties(request,searcher);
        List<CustomerInfoModel> customerInfoModelList = Lists.newArrayList();
        List<Customer> customerList = baseDaoSupport.find(searcher);
        if (Collections3.isNotEmpty(customerList))
        {
            for (Customer customer : customerList)
            {
                CustomerInfoModel customerInfo = new CustomerInfoModel();

                customerInfo.setCustomerName(customer.getRealName());
                if ("0".equals(String.valueOf(customer.getSex())))
                {
                    customerInfo.setSex("男");
                }
                if ("1".equals(String.valueOf(customer.getSex())))
                {
                    customerInfo.setSex("女");
                }
                if ("0".equals(String.valueOf(customer.getActivateStatus()))) {
                    customerInfo.setActivateStatus("未激活");
                }
                if ("1".equals(String.valueOf(customer.getActivateStatus()))) {
                    customerInfo.setActivateStatus("已激活");
                }
                if (null != customer.getCompany())
                {
                    customerInfo.setCompanyName(customer.getCompany().getName());
                }
                if (null != customer.getDept()) {
                    customerInfo.setDept(getText("BASE_DEPT",customer.getDept()));
                }
                if (null != customer.getEmail()) {
                    customerInfo.setEmail(customer.getEmail());
                }
                if (null != customer.getPhoneNum()) {
                    customerInfo.setPhoneNum(customer.getPhoneNum());
                }
                if (null != customer.getRoomNum()) {
                    customerInfo.setRoomNum(customer.getRoomNum());
                }
                if (null != customer.getLandLine()) {
                    customerInfo.setLandLine(customer.getLandLine());
                }
                if (null != customer.getBirthday()) {
                    customerInfo.setBirthday(customer.getBirthday());
                }
                if (null != customer.getPosition()) {
                    customerInfo.setPosition(getText("POSITION",customer.getPosition()));
                }
                if (null != customer.getCooperate()) {
                    customerInfo.setCooperate(getText("COOPERATE",customer.getCooperate()));
                }
                if (null != customer.getAnalyseType()) {
                    customerInfo.setAnalyseType(getText("ANALYSIS_TYPE",customer.getAnalyseType()));
                }
                if (null != customer.getCompany())
                {
                    if (null != customer.getCompany().getAddress()) {
                        customerInfo.setAddress(customer.getCompany().getAddress());
                    }
                }
                if (null != customer.getCharacteristic()) {
                    customerInfo.setCharacteristic(customer.getCharacteristic());
                }
                if (null != customer.getResearchFiled()) {
                    customerInfo.setResearchFiled(customer.getResearchFiled());
                }
                if (null != customer.getIntroduction()) {
                    customerInfo.setIntroduction(customer.getIntroduction());
                }
                if(null != customer.getId()) {
                    customerInfo.setCountSubCustomer(countSubCustomer(customer.getId()));
                    List<Customer> subCustomerList = baseDaoSupport.find(Customer.class, "from Customer c where parentId='" + customer.getId() + "'");

                    String sub = "";
                    if (Collections3.isNotEmpty(subCustomerList))
                    {
                        for (Customer subCustomer : subCustomerList)
                        {
                            sub = sub + subCustomer.getRealName()+"_"+subCustomer.getPhoneNum()+"、";
                        }
                        if (sub.length()>=1) {
                            customerInfo.setSubCustomerList(sub.substring(0, sub.length() - 1));  //附属账号信息
                        }
                    }
                }
                if (null != customer.getCreateId())
                {
                    customerInfo.setCreatorName(customer.getCreateId().getRealName());
                }
                List<CustomerRelation> relationList = customer.getCustomerRelationList();
                String currentName = "";
                if (Collections3.isNotEmpty(relationList)) {
                    for (CustomerRelation cr : relationList) {
                        currentName = currentName + cr.getBusiness().getRealName() + "、";
                    }
                    if (currentName.length()>=1) {
                        customerInfo.setCurrentName(currentName.substring(0, currentName.length() - 1));
                    }
                }

                customerInfoModelList.add(customerInfo);
            }
        }

        return customerInfoModelList;
    }

    private String getText(String category, String value)
    {
        String hql = "FROM Dict d WHERE d.category = :category and d.value = :value";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category", "value"}, new Object[] {category, value});
        if (Collections3.isNotEmpty(records))
        {
            return records.get(0).getText();
        }
        return null;
    }

    @Override
    public BusinessInfo getBusiByCusAndType(String customerId, String testingType)
    {
        List<BusinessInfo> list =
            baseDaoSupport.find(BusinessInfo.class, "select b from BusinessInfo b where b.id in (select cr.business.id from "
                + "CustomerRelation cr where cr.customer.id='" + customerId + "') and b.testingType='" + testingType + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Customer getByNameAndNum(CustomerSearcher request)
    {
        List<Customer> list =
            baseDaoSupport.find(Customer.class,
                "from Customer c where c.realName = '" + request.getRealName() + "' " + "and c.phoneNum = '" + request.getPhoneNum() + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public BusinessInfo getBusiness(String id)
    {
        // TODO Auto-generated method stub
        List<BusinessInfo> list = baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b where b.id='" + id + "'");
        if (null != list && Collections3.isNotEmpty(list))
        {
            return list.get(0);
            
        }
        return null;
    }
    
    @Override
    public Pagination<Customer> customerByTestingType(String testingType, String name, Integer pageNo, Integer pageSize)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql =
            new StringBuffer("select c from Customer c where c.delFlag=0 and c.disableStatus=0  and  c.id in ("
                + " select cr.customer.id from CustomerRelation cr where cr.business.id in (select b.id from BusinessInfo b where b.testingType ='"
                + testingType + "'))");
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" and c.realName LIKE '%" + name + "%'");
        }
        queryer.setHql(hql.toString());
        
        Pagination<Customer> paging = baseDaoSupport.find(queryer, pageNo, pageSize, Customer.class);
        return paging;
    }
    
    @Override
    public List<Customer> listBySome(CustomerSearcher request)
    {
        
        return baseDaoSupport.find(request);
    }
    
    @Override
    public List<Customer> listActivity(CustomerSearcher request)
    {
        return baseDaoSupport.find(Customer.class, " SELECT c FROM Customer as c WHERE  c.delFlag = 0 and c.parentId is null and " + " c.disableStatus=0 ");
    }

}
