package com.todaysoft.lims.system.mvc;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.CustomerRelation;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private ICompanyService companyService;
    
    @Autowired
    private IDictService dictService;
    
    /**
     * 客户数据
     * */
    @RequestMapping(value = "/list.do")
    public String getList(Customer searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Customer> pagination = customerService.paging(searcher, pageNo, 10);
        //查询附属账号数
        for (Customer customer : pagination.getRecords())
        {
            Integer countSubCustomer = customerService.countSubCustomer(customer.getId());
            customer.setCountSubCustomer(countSubCustomer);

            if (Collections3.isNotEmpty(customer.getCustomerRelationList()))
            {//当前业务员业务类型,并去除无效业务员,多个业务员用逗号隔开
                Iterator<CustomerRelation> it = customer.getCustomerRelationList().iterator();
                while (it.hasNext())
                {
                    CustomerRelation relation =it.next();
                    if (customer.getCustomerRelationList().indexOf(relation) != customer.getCustomerRelationList().size()-1) {
                        relation.getBusiness().setRealName(relation.getBusiness().getRealName() + "，");

                        if (relation.getBusiness().getDelFlag() == 1)
                        {
                            it.remove();
                        }
                        else
                        {
                            TestingType testingType = testingTypeService.get(relation.getBusiness().getTestingType());
                            if (null != relation.getBusiness())
                            {
                                relation.getBusiness().setType(testingType);
                            }
                        }
                    }
                }

            }

        }
        //查询所有单位
        List<Company> companyList = companyService.getCompanys(new CompanySearcher());
        model.addAttribute("companyList", companyList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "customer/customer_list";
        
    }
    
    /**
     * 新增客户page
     * */
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String create(ModelMap model)
    {
        Customer data = new Customer();
        
        model.addAttribute("customer", data);
        model.addAttribute("flag", "新增");
        
        return "customer/customer_form";
    }
    
    /**
     * 修改探客户page
     * */
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modify(String id, ModelMap model)
    {
        Customer data = customerService.get(id);
        
        model.addAttribute("customer", data);
        model.addAttribute("flag", "修改");
        return "customer/customer_form";
    }
    
    /**
    * 查看探客户page
    * */
    @RequestMapping(value = "/show.do", method = RequestMethod.GET)
    public String show(String id, ModelMap model)
    {
        Customer data = customerService.get(id);
        if (Collections3.isNotEmpty(data.getCustomerRelationList()))
        {//查询业务员业务类型,去除无效业务员
            Iterator<CustomerRelation> it = data.getCustomerRelationList().iterator();
            while (it.hasNext())
            {
                CustomerRelation relation = it.next();
                if (relation.getBusiness().getDelFlag() == 1)
                {
                    it.remove();
                }
                else
                {
                    TestingType testingType = testingTypeService.get(relation.getBusiness().getTestingType());
                    if (null != relation.getBusiness())
                    {
                        relation.getBusiness().setType(testingType);
                        
                    }
                }
                
            }

        }
        //查询该客户子账号
        List<Customer> subCustomerList = customerService.subCustomerList(data);
        model.addAttribute("subCustomerList", subCustomerList);
        model.addAttribute("customer", data);
        model.addAttribute("flag", "修改");
        return "customer/customer_show";
    }
    
    /**
     * 修改业务配置page
     * */
    @RequestMapping(value = "/modifyBusiness.do", method = RequestMethod.GET)
    public String modifyBusiness(String id, ModelMap model)
    {
        Customer data = customerService.get(id);
        if (Collections3.isNotEmpty(data.getCustomerRelationList()))
        {//查询业务员业务类型,并去除无效业务员
            Iterator<CustomerRelation> it = data.getCustomerRelationList().iterator();
            while (it.hasNext())
            {
                CustomerRelation relation = it.next();
                if (relation.getBusiness().getDelFlag() == 1)
                {
                    it.remove();
                }
                else
                {
                    TestingType testingType = testingTypeService.get(relation.getBusiness().getTestingType());
                    if (null != relation.getBusiness())
                    {
                        relation.getBusiness().setType(testingType);

                    }
                }

            }
            
        }
        //查询所有检测类型
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("records", data);
        model.addAttribute("flag", "修改");
        return "customer/customer_business";
    }
    
    /**
     * 修改客户
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/customerModify.do", method = RequestMethod.POST)
    @ResponseBody
    public boolean modifyCustomer(Customer request, ModelMap model, HttpSession session)
    {
        request.setBirthday("" == request.getBirthday() ? null : request.getBirthday());
        Company company = new Company();
        company.setId(request.getCompanyId());
        request.setCompany(company);
        if (request.getId() == null)
        {
            
            customerService.create(request);
        }
        else
        {
            customerService.modify(request);
        }
        
        return true;
    }
    
    /**
     * 删除客户
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        customerService.delete(id);
        return redirectList(model, session, "redirect:/customer/list.do");
    }
    
    /**
     * 客户解绑业务员
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/removeBusiness.do")
    @ResponseBody
    public Integer removeBusiness(CustomerRelation request, ModelMap model, HttpSession session)
    {
        Integer result = customerService.removeBusiness(request);
        return result;
    }
    
    /**
     * 绑定业务员
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/addCustomerRelation.do")
    @ResponseBody
    public Integer addCustomerRelation(CustomerRelation request, ModelMap model, HttpSession session)
    {
        //        return 1;
        
        Integer result = customerService.addCustomerRelation(request);
        return result;
    }

    /**
     * 批量修改业务员form
     *
     * @param keys
     * @param model
     * @return
     */
    @RequestMapping("/businessEnchange.do")
    @FormInputView
    public String businessEnchange(String keys, ModelMap model){
        List<Customer> customers = customerService.getCustormersByids(keys);

        if (Collections3.isNotEmpty(customers)) {
            for (Customer c : customers) {
                Iterator<CustomerRelation> it = c.getCustomerRelationList().iterator();
                while (it.hasNext())
                {
                    CustomerRelation relation = it.next();
                    if (c.getCustomerRelationList().indexOf(relation) != c.getCustomerRelationList().size()-1) {
                        relation.getBusiness().setRealName(relation.getBusiness().getRealName() + "，");
                        if (relation.getBusiness().getDelFlag() == 1) {
                            it.remove();
                        } else {
                            TestingType testingType = testingTypeService.get(relation.getBusiness().getTestingType());
                            if (null != relation.getBusiness()) {
                                relation.getBusiness().setType(testingType);

                            }
                        }
                    }
                }
            }
        }


        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("keys",keys);
        model.addAttribute("customers", customers);
        model.addAttribute("testingTypeList", testingTypeList);
        return "/customer/customer_business_enchange";
    }

    /**
     * 批量解绑
     *
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping("/businessRemove.do")
    @FormInputView
    public String businessRemove(String ids, ModelMap model){
        List<Customer> customers = customerService.getCustormersByids(ids);

        if (Collections3.isNotEmpty(customers)) {
            for (Customer c : customers) {
                Iterator<CustomerRelation> it = c.getCustomerRelationList().iterator();
                while (it.hasNext())
                {
                    CustomerRelation relation = it.next();
                    if (c.getCustomerRelationList().indexOf(relation) != c.getCustomerRelationList().size()-1) {
                        relation.getBusiness().setRealName(relation.getBusiness().getRealName() + "，");
                        if (relation.getBusiness().getDelFlag() == 1) {
                            it.remove();
                        } else {
                            TestingType testingType = testingTypeService.get(relation.getBusiness().getTestingType());
                            if (null != relation.getBusiness()) {
                                relation.getBusiness().setType(testingType);

                            }
                        }
                    }
                }
            }
        }


        model.addAttribute("keys",ids);
        model.addAttribute("customers", customers);
        return "/customer/customer_business_remove";
    }

    /**
     * 批量改绑
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/allUpdate.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> allUpdate(Customer request)
    {
        Map<String,Object> map = new HashMap<>();

        customerService.allUpdate(request);

        map.put("result",true);
        return map;
    }

    @RequestMapping(value = "/allRemove.do")
    @ResponseBody
    public Map<String,Object> allRemove(Customer request)
    {
        Map<String,Object> map = new HashMap<>();

        customerService.allRemove(request);

        map.put("result",true);
        return map;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(Customer search)
    {
        return customerService.validate(search);

    }

    /**
     * 当前业务员营销中心校验
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validateType.do", method = RequestMethod.POST)
    public boolean validateType (Customer request)
    {
        boolean flag = customerService.validateType(request);
        return flag;
    }
    
    /**
     *启用禁用
     */
    
    @RequestMapping(value = "/enableCustomer.do", method = RequestMethod.GET)
    @SystemServiceLog(description="客户管理-启用禁用",type=4)
    public String enableCustomer(Customer request, ModelMap model, HttpSession session)
    {
        
        customerService.enableCustomer(request);
        //同时启用禁用附属账号
        Customer data = customerService.get(request.getId());
        List<Customer> subCustomerList = customerService.subCustomerList(data);
        for (Customer subCustomer : subCustomerList)
        {
            subCustomer.setDisableStatus(1);
            customerService.enableCustomer(subCustomer);
            
        }
        
        return redirectList(model, session, "redirect:/customer/list.do");
    }
    
    /**
     * 客户数据
     * */
    @RequestMapping(value = "/getSelectList.do")
    @ResponseBody
    public Map<String, Object> getSelectList(Customer searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        Pagination<Customer> containers = customerService.paging(searcher, 1, 1000);
        map.put("value", containers.getRecords());
        
        return map;
        
    }
    
    /**
     * 客户数据
     * */
    @RequestMapping(value = "/getSelectListByConstract.do")
    @ResponseBody
    public List<Customer> getSelectListByConstract(Customer searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Customer> containers = customerService.paging(searcher, 1, 1000);
        return containers.getRecords();
        
    }
    
    /**
     * 客户数据
     * */
    @RequestMapping(value = "/multiSelectList.do")
    @ResponseBody
    public List<Customer> multiSelectList(Customer searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Customer> containers = customerService.paging(searcher, 1, 100);
        
        return containers.getRecords();
        
    }
    
    /**
     *某营销中心下的客户
     * */
    @RequestMapping(value = "/customerByTestingType.do")
    @ResponseBody
    public List<Customer> customerByTestingType(String testingType, String name, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Customer> containers = customerService.customerByTestingType(testingType, name, 1, 1000);
        
        return containers.getRecords();
        
    }
    
    @RequestMapping(value = "/getCustormersByids")
    @ResponseBody
    public List<Customer> getCustormersByids(String ids)
    {
        List<Customer> customers = customerService.getCustormersByids(ids);
        if (Collections3.isNotEmpty(customers))
        {
            for (Customer c : customers)
            {
                if (StringUtils.isNotEmpty(c.getDept()))
                {
                    c.setDeptName(dictService.getEntry("BASE_DEPT", c.getDept()).getText());
                }
            }
        }
        return customers;
    }
    
    @RequestMapping(value = "/getByidAndType")
    @ResponseBody
    public BusinessInfo getBusiByCusAndType(String customerId, String testingType)
    {
        return customerService.getBusiByCusAndType(customerId, testingType);
    }
    
}
