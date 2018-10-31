package com.todaysoft.lims.system.mvc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.modules.smm.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.searcher.ContractSearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.OrderContrctSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractChangeSignUser;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.contract.ContractCreateRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceDetail;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceInfo;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractOrg;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyA;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyB;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.contract.ContractProduct;
import com.todaysoft.lims.system.model.vo.contract.ContractSample;
import com.todaysoft.lims.system.model.vo.contract.ContractSampleRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBill;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBillRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractUser;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import com.todaysoft.lims.system.model.vo.order.OrderResearchProduct;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;
import com.todaysoft.lims.system.modules.bmm.service.IInvoiceApplyService;
import com.todaysoft.lims.system.modules.bpm.dnaext.mvc.DNAExtractController;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.service.IContractService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.system.util.Arith;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/contract")
public class ContractController extends BaseController
{
    
    @Autowired
    private IContractService service;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMetadataSampleService sampleService;
    
    @Autowired
    private IAPPSalemanService appService;
    
    @Autowired
    private IProductService pService;
    
    @Autowired
    private ICustomerService cService;
    
    @Autowired
    private ICompanyService companyService;
    
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IDepartmentService deptService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IInvoiceApplyService invoiceApplyService;
    
    @RequestMapping(value = "/list.do")
    public String tab(ContractSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "update");
        return "contract/contract_tab";
    }
    
    @RequestMapping(value = "/selectContract.do")
    public String paging(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
            String returnUrl = "";
            Pagination<Contract> pagination = null;
            try{

            /**数据权限开始  。。。。。。。。。。。。。。。。。*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            AccountDetails account = (AccountDetails) principal;
            Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
            if (dataAuthorityMap.containsKey("CONTRACT_LIST")) {
                searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_LIST"));
            }
            /**数据权限结束  。。。。。。。。。。。。。。。。。*/
            if (StringUtils.isEmpty(searcher.getStatus())) {
                searcher.setStatus(Contract.DRAFT);
            }
            if (StringUtils.isNotEmpty(searcher.getSignUser())) {
                BusinessInfo b = appService.getBusinessInfo(searcher.getSignUser());
                if (null != b) {
                    model.addAttribute("businessInfo", JSONObject.toJSON(b).toString());
                }
            }
            pagination = service.paging(searcher, pageNo, 10);
                if (Collections3.isNotEmpty(pagination.getRecords())) {
                    for (Contract c : pagination.getRecords()) {
                        if (StringUtils.isNotEmpty(c.getProjectManager())) {
                            UserDetailsModel prjManagerUser = userService.getUserByID(c.getProjectManager());
                            c.setPrjManagerName(prjManagerUser.getArchive().getName());
                        }
                    }
                }
            switch (searcher.getStatus())
            {
                //草稿
                case Contract.DRAFT:
                    returnUrl = "contract/contract_list_draft";
                    break;
                //待确认
                case Contract.CONFIRMING:
                    returnUrl =  "contract/contract_list_confirming";
                    break;
                case Contract.CONFIRMED:
                    if ("inDate".equals(searcher.getFlag()))
                    {
                        //已确认
                        returnUrl =  "contract/contract_list_confirmed";
                    }
                    else
                    {
                        for (Contract c : pagination.getRecords()) {
                            List<FinanceInvoiceTask> financeinvoicetasks = Lists.newArrayList();
                            List<ContractInvoiceInfo> contractinvoiceinfos = Lists.newArrayList();
                            List<Order> orders = getOrdersByConrtractId(c.getId());
                            if (null != c.getContractContent() && "1".equals(c.getContractContent().getInvoiceMethod())) { //集中
                                contractinvoiceinfos = c.getContractInvoiceInfoList();
                            } else {
                                //一单一票  查订单
                                if (Collections3.isNotEmpty(orders)) {
                                    financeinvoicetasks = service.getFinanceInvoiceTaskByOrder(c, orders);
                                }
                            }
                            c.setCountInvoiceAmount(service.getCountAmount(contractinvoiceinfos, financeinvoicetasks).intValue());
                            c.setOrders(orders);
                            InsertOrderAmount(c);//这一步有问题，查询太慢
                        }
                        //合同期满
                        returnUrl =  "contract/contract_list_expiration";
                    }
                    break;
                default:
                    //结项
                    returnUrl =  "contract/contract_list_knot";
                    break;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        List<TestingType> testingTypes = Lists.newArrayList();
        if(null != session.getAttribute("testingType"))
        {
            testingTypes = (List<TestingType>)session.getAttribute("testingType");
        }else{
            testingTypes = testingTypeService.testingTypeList();
            session.setAttribute("testingTypes",testingTypes);
        }
        model.addAttribute("testingTypes", testingTypes);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return returnUrl;
    }
    
    @RequestMapping(value = "/create.do")
    @ResponseBody
    public String create(ContractCreateRequest request, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(request.getContractSampleJson()))
        {
            request.setConSamples(JSON.parseArray(request.getContractSampleJson() + "", ContractSampleRequest.class));
        }
        if (StringUtils.isNotEmpty(request.getContractProductJson()))
        {
            request.setConProducts(JSON.parseArray(request.getContractProductJson() + "", ContractProduct.class));
        }
        Contract contract = request.getContract();
        if (null == contract.getAmount())
        {
            contract.setAmount(new BigDecimal(0));
        }
        else
        {
            contract.setAmount(contract.getAmount().multiply(new BigDecimal(100)));
        }
        if (null == contract.getIncomingAmount())
        {
            contract.setIncomingAmount(0);
        }
        if (StringUtils.isEmpty(contract.getHospitalAdmited()))
        {
            contract.setHospitalAdmited(null);
        }
        AuthorizedUser u = userService.getByToken();
        contract.setCreatorId(u.getId());
        contract.setCreatorName(u.getName());
        service.create(request);
        return "OK";
    }
    
    @RequestMapping(value = "/modify.do")
    @ResponseBody
    public String modify(ContractCreateRequest request)
    {
        if (StringUtils.isNotEmpty(request.getContractSampleJson()))
        {
            request.setConSamples(JSON.parseArray(request.getContractSampleJson() + "", ContractSampleRequest.class));
        }
        if (StringUtils.isNotEmpty(request.getContractProductJson()))
        {
            request.setConProducts(JSON.parseArray(request.getContractProductJson() + "", ContractProduct.class));
        }
        Contract contract = request.getContract();
        contract.setAmount(contract.getAmount().multiply(new BigDecimal(100)));
        if (StringUtils.isEmpty(contract.getHospitalAdmited()))
        {
            contract.setHospitalAdmited(null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try
        {
            date = sdf.parse(contract.getCreateDate());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (date != null)
        {
            contract.setCreateTime(date);
        }
        AuthorizedUser u = userService.getByToken();
        contract.setUpdateId(u.getId());
        contract.setUpdateName(u.getName());

        service.modify(request);
        //    return "redirect:/contract/selectContract.do?status=" + request.getContract().getStatus();

        return "OK";
    }
    
    @RequestMapping(value = "/getContractById")
    @ResponseBody
    public Contract getContractById(String id)
    {
        Contract contract = service.getContractById(id);
        return contract;
    }
    
    @RequestMapping(value = "/form.do")
    public String form(String id, ModelMap model)
    {
        Contract contract = null;
        ContractPartyA cpa = null;
        ContractPartyB cpb = null;
        ContractContent cc = null;
        if (id != null)
        {
            contract = service.getContractById(id);
            cpa = service.getContractPAByContractId(contract.getId());
            cpb = service.getContractPBByContractId(contract.getId());
            cc = service.getContractContentByContractId(contract.getId());
            List<String> list = Lists.newArrayList();
            if (StringUtils.isNotEmpty(cc.getDeliveryMode()))
            {
                for (String str : cc.getDeliveryMode().split(","))
                {
                    list.add(str);
                }
            }
            model.addAttribute("deliveryModes", JSONObject.toJSON(list).toString());
            List<ContractProduct> products = contract.getConProducts();
            if (Collections3.isNotEmpty(products))
            {
                for (ContractProduct contractProduct : products)
                {
                    contractProduct.setP(pService.getProduct(contractProduct.getProductId()));
                }
            }
            model.addAttribute("conProducts", JSONObject.toJSON(products).toString());
            
            List<ContractSample> samples = contract.getConSamples();
            List<ContractSampleRequest> samplerequest = Lists.newArrayList();
            if (Collections3.isNotEmpty(samples))
            {
                for (ContractSample contractSample : samples)
                {
                    List<String> strs = Lists.newArrayList();
                    String[] keys = contractSample.getSampleTypeKeys().split(",");
                    strs = Arrays.asList(keys);
                    ContractSampleRequest request = new ContractSampleRequest();
                    BeanUtils.copyProperties(contractSample, request);
                    request.setSampleTypeKeys(strs);
                    samplerequest.add(request);
                }
            }
            
            model.addAttribute("contractsample", JSONObject.toJSON(samplerequest).toString());
            if (StringUtils.isNotEmpty(contract.getSignUser()))
            {
                if (StringUtils.isNotEmpty(contract.getSignUser()))
                {
                    BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
                    if (null != b)
                    {
                        model.addAttribute("signUser", JSONObject.toJSON(b).toString());
                    }
                }
            }
            //修改页面回显项目管理人
            if (contract.getProjectManager() !=null && contract.getProjectManager()!="")
            {
               UserDetailsModel udm = userService.getUserByID(contract.getProjectManager());
               UserSimpleModel usm = new UserSimpleModel();
               usm.setId(udm.getId());
               usm.setName(udm.getArchive().getName());
               usm.setPhone(udm.getArchive().getPhone());
               model.addAttribute("prjManagerUser", JSONObject.toJSON(usm).toString());
            }
        }

        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("testingTypes", testingTypes);
        List<ContractOrg> contractpbs = service.getContractOrgs();
        model.addAttribute("contractpbs", contractpbs);
        model.addAttribute("samples", sampleService.list(null));
        
        model.addAttribute("contract", contract);
        model.addAttribute("cpa", cpa);
        model.addAttribute("cpb", cpb);
        model.addAttribute("cc", cc);
        return "contract/contract_form";
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(ContractSearcher searcher)
    {
        service.delete(searcher);
        return "redirect:/contract/selectContract.do?status=" + searcher.getStatus();
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        Contract contract = null;
        if (id != null)
        {
            contract = service.getContractById(id);
            ContractPartyA cpa = service.getContractPAByContractId(contract.getId());
            ContractPartyB cpb = service.getContractPBByContractId(contract.getId());
            //cpb.setCompanyName(service.getContractOrgById(cpb.getCompanyName()).getCompanyName());
            ContractContent cc = service.getContractContentByContractId(contract.getId());
            List<ContractUser> cus = contract.getConUsers();
            for (ContractUser contractUser : cus)
            {
                Customer c = cService.get(contractUser.getUserId());
                contractUser.setCustormer(c);
            }
            List<ContractSample> samples = contract.getConSamples();
            updateData(samples);
            cc.setDeliveryModes(getListBysplit(cc.getDeliveryMode()));
            model.addAttribute("cus", cus);
            model.addAttribute("cpa", cpa);
            model.addAttribute("cpb", cpb);
            model.addAttribute("cc", cc);
            List<ContractChangeSignUser> changeSignUserList = service.getChangeSignUserList(contract.getId());
            if (Collections3.isNotEmpty(changeSignUserList))
            {
                for (ContractChangeSignUser ccs : changeSignUserList)
                {
                    BusinessInfo bi = appService.getBusinessInfo(ccs.getBeforeSignUser());
                    if (null != bi)
                    {
                        ccs.setBeforeSignUserName(bi.getRealName());
                    }
                    BusinessInfo bi2 = appService.getBusinessInfo(ccs.getAfterSignUser());
                    if (null != bi2)
                    {
                        ccs.setAfterSignUserName(bi2.getRealName());
                    }
                }
            }
            model.addAttribute("changeSignUserList", changeSignUserList);
        }
        if (StringUtils.isNotEmpty(contract.getSignUser()))
        {
            BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
            if (null != b)
            {
                contract.setSignUsername(b.getRealName());
            }
        }
        if (StringUtils.isNotEmpty(contract.getProjectManager()))
        {
            UserDetailsModel udm = userService.getUserByID(contract.getProjectManager());
            if (null != udm)
            {
              contract.setPrjManagerName(udm.getArchive().getName());
            }
        }
        model.addAttribute("contract", contract);
        return "contract/contract_view";
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do")
    public boolean checkedName(ContractSearcher searcher)
    {
        
        return service.validate(searcher);
    }
    
    @RequestMapping(value = "/getContracts.do")
    @ResponseBody
    public List<Contract> getContracts(ContractSearcher searcher)
    {
        
        List<Contract> result = service.getContractList(searcher);
        return result;
    }
    
    @RequestMapping("/upload.do")
    public String upload(HttpServletRequest request, HttpServletResponse response, String contractId)
    {
        String path = FileUtils.uploadContract(request, response).get(0);
        Contract c = service.getContractById(contractId);
        AuthorizedUser u = userService.getByToken();
        c.setUpdateId(u.getId());
        c.setUpdateName(u.getName());
        c.setUpdateTime(new Date());
        c.setOriginal(path);
        if (StringUtils.isEmpty(c.getCode()))
        {
            service.addCode(c);
        }
        else
        {
            service.update(c);
        }
        return "redirect:/contract/selectContract.do?status=1";
    }
    
    @RequestMapping("/download.do")
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        FileUtils.download(resp, fileName);
    }
    
    @RequestMapping(value = "/getContractOrgByName")
    @ResponseBody
    public ContractOrg getContractOrgById(String name)
    {
        ContractSearcher searcher = new ContractSearcher();
        searcher.setSignUser(name);
        return service.getContractOrgByName(searcher);
    }
    
    @RequestMapping(value = "/confirmContract")
    @ResponseBody
    public Integer confirmContract(ContractUser cUser)
    {
        if (StringUtils.isNotEmpty(cUser.getId()))
        {
            Contract c = service.getContractById(cUser.getId());
            AuthorizedUser u = userService.getByToken();
            c.setConfirmerId(u.getId());
            c.setConfirmerName(u.getName());
            c.setUpdateTime(new Date());
            c.setConfirmTime(new Date());
            cUser.setContract(c);
        }
        return service.confirmContract(cUser);
    }
    
    @RequestMapping(value = "/confirm.do")
    public String confirm(String id, ModelMap model)
    {
        Contract contract = null;
        if (id != null)
        {
            contract = service.getContractById(id);
            if (StringUtils.isNotEmpty(contract.getOriginal()))
            {
                contract.setOriginalRealName((contract.getOriginal().substring(19)));
            }
            ContractPartyA cpa = service.getContractPAByContractId(contract.getId());
            ContractPartyB cpb = service.getContractPBByContractId(contract.getId());
            //cpb.setCompanyName(service.getContractOrgById(cpb.getCompanyName()).getCompanyName());
            ContractContent cc = service.getContractContentByContractId(contract.getId());
            cc.setDeliveryModes(getListBysplit(cc.getDeliveryMode()));
            List<ContractUser> cus = contract.getConUsers();
            for (ContractUser contractUser : cus)
            {
                Customer c = cService.get(contractUser.getUserId());
                if (null != c)
                {
                    contractUser.setCustormer(c);
                }
            }
            model.addAttribute("cus", cus);
            model.addAttribute("cpa", cpa);
            model.addAttribute("cpb", cpb);
            model.addAttribute("cc", cc);
            
        }
        List<Company> companyList = companyService.getCompanys(new CompanySearcher());
        model.addAttribute("companyList", companyList);
        
        List<ContractSample> samples = contract.getConSamples();
        updateData(samples);
        if (StringUtils.isNotEmpty(contract.getSignUser()))
        {
            
            if (StringUtils.isNotEmpty(contract.getSignUser()))
            {
                BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
                if (null != b)
                {
                    contract.setSignUsername(b.getRealName());
                }
            }
        }
        if (null != contract.getSignDate())
        {
            model.addAttribute("contractSignDate", JSON.toJSON(contract.getSignDate()).toString());
        }
        if (StringUtils.isNotEmpty(contract.getProjectManager()))
        {
            UserDetailsModel udm = userService.getUserByID(contract.getProjectManager());
            if (null != udm)
            {
                contract.setPrjManagerName(udm.getArchive().getName());
                model.addAttribute("prjMUser", JSON.toJSON(contract.getProjectManager()).toString());
            }
        }
        model.addAttribute("contract", contract);
        return "contract/contract_confirm";
    }
    
    @RequestMapping(value = "/getCustormers")
    @ResponseBody
    public List<Customer> getCustormers(Customer searcher)
    {
        List<Customer> customer = cService.listBySome(searcher);
        for (Customer c : customer)
        {
            if (StringUtils.isNotEmpty(c.getSex()))
            {
                c.setSexName(dictService.getEntry("SEX", c.getSex() + "").getText());
            }
            if (StringUtils.isNotEmpty(c.getPosition()))
            {
                c.setPositionName(dictService.getEntry("POSITION", c.getPosition()).getText());
            }
            if (StringUtils.isNotEmpty(c.getDept()))
            {
                c.setDeptName(dictService.getEntry("BASE_DEPT", c.getDept()).getText());
            }
        }
        return customer;
    }
    
    @RequestMapping(value = "/selectTal.do")
    public String selectTal(String isCreate, ModelMap model)
    {
        model.addAttribute("flag", isCreate);
        return "contract/contract_tab";
    }
    
    @RequestMapping(value = "/getContractUsers.do")
    @ResponseBody
    public List<Customer> getContractUsers(String contractId)
    {
        return service.getContractUsers(contractId);
    }
    
    @RequestMapping(value = "/getContractByUserId.do")
    @ResponseBody
    public List<Contract> getContractByUserId(ContractSearcher searcher)
    {
        return service.getContractByUserId(searcher);
    }
    
    @RequestMapping(value = "/buildContract.do")
    @ResponseBody
    public String buildContract(ContractCreateRequest request)
    {
        return service.buildContract(request);
    }
    
    @RequestMapping(value = "/downloadData.do")
    public void downloadData(String formValue, HttpServletResponse response)
    {
        service.downloadData(response, formValue);
    }
    
    public List<ContractSample> updateData(List<ContractSample> samples)
    {
        if (Collections3.isNotEmpty(samples))
        {
            for (ContractSample contractSample : samples)
            {
                List<String> sampleNames = Lists.newArrayList();
                if (StringUtils.isNotEmpty(contractSample.getSampleTypeKeys()))
                    for (String str : contractSample.getSampleTypeKeys().split(","))
                    {
                        MetadataSample s = sampleService.get(str);
                        if (null != s)
                        {
                            sampleNames.add(s.getName());
                        }
                    }
                contractSample.setSampleNames(sampleNames);
            }
        }
        return samples;
    }
    
    public List<String> getListBysplit(String deliveryMode)
    {
        List<String> list = Lists.newArrayList();
        if (StringUtils.isNotEmpty(deliveryMode))
        {
            for (String str : deliveryMode.split(","))
            {
                list.add(str);
            }
        }
        return list;
    }
    
    /*
     * *订单 ---根据合同选产品
     */
    @RequestMapping(value = "/getContractProducts.do")
    @ResponseBody
    public List<Product> getContractProducts(String contractId)
    {
        List<ContractProduct> list = service.getContractProducts(contractId);
        List<Product> products = Lists.newArrayList();
        
        if (Collections3.isNotEmpty(list))
        {
            for (ContractProduct s : list)
            {
                Product p = pService.getProduct(s.getProductId());
                if (StringUtils.isNotEmpty(p))
                {
                    if (StringUtils.isNotEmpty(s.getContractPrice()))
                    {
                        p.setPrice(s.getContractPrice().toString());
                        p.setRealPrice(BigDecimal.valueOf(Double.valueOf(s.getContractPrice())).divide(new BigDecimal(100)).toString());
                    }
                    
                    products.add(p);
                }
                
            }
        }
        return Collections3.isNotEmpty(products) ? products : null;
        
    }
    
    /**
     * 合同不定期列表
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/unRegularlyList.do")
    public String unRegularlyList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CONTRACT_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_LIST"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        //searcher.setStatus(Contract.CONFIRMED); // ---- 已确认 
        searcher.setPayStatus(new String[] {Contract.CONFIRMED});
        
        Pagination<Contract> pagination = service.unRegularlyList(searcher, pageNo, DEFAULTPAGESIZE);
        
        for (Contract c : pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(c.getSignUser()))
            {
                BusinessInfo b = appService.getBusinessInfo(c.getSignUser());
                if (null != b)
                {
                    c.setSignUsername(b.getRealName());
                }
                
            }
            
            ContractContent cc = service.getContractContentByContractId(c.getId());
            c.setContractContent(cc);
            BigDecimal amount = searcherOrderAmountByContractId(c.getId());
            c.setOrderAmount(amount);
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "contract/contract_unregularly_list";
    }
    
    /**合同款项查看
     * */
    @RequestMapping(value = "/viewPaymentRecord.do")
    public String getRecordByContractId(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<ContractPaymentRecord> pagination = service.getRecordByContractId(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        Contract contract = service.getContractById(searcher.getId());
        ContractContent cc = service.getContractContentByContractId(contract.getId());
        
        OrderSearchRequest orderSearcher = new OrderSearchRequest();
        orderSearcher.setContractId(contract.getId());
        orderSearcher.setIfResolveOrderInfo(false);
        List<Order> orderResult = orderService.list(orderSearcher);//根据订单获取合同价格
        if (StringUtils.isNotEmpty(orderResult))
        {
            BigDecimal amount = new BigDecimal(0);
            BigDecimal incomingAmount = new BigDecimal(0);
            
            if (StringUtils.isNotEmpty(cc.getSettlementMode()) && !cc.getSettlementMode().equals("4")) //非一单一结
            {
                for (Order o : orderResult)
                {
                    if (!o.getTestingStatus().equals(4))
                    { // 过滤已取消
                        amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                    }
                }
            }
            else
            {
                for (Order o : orderResult)
                {
                    amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                    incomingAmount = incomingAmount.add(new BigDecimal(o.getIncomingAmount()));
                }
                contract.setIncomingAmount(incomingAmount.multiply(new BigDecimal(100)).intValue());
            }
            contract.setOrderAmount(amount);
        }
        contract.setContractContent(cc);
        model.addAttribute("contract", contract);
        
        return "contract/contract_paymentrecord_view";
    }
    
    /**
     * 结算清单付款列表 tab2
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping("/settlementList.do")
    public String settlementList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<ContractSettleBill> pagination = service.settlementList(searcher, pageNo, DEFAULTPAGESIZE);
        
        for (ContractSettleBill c : pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(c.getContractId()))
            {
                Contract contract = service.getContractById(c.getContractId());
                ContractContent cc = service.getContractContentByContractId(c.getContractId());
                c.setContractContent(cc);
                c.setContract(contract);
                
                if (StringUtils.isNotEmpty(contract.getSignUser()))
                {
                    BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
                    if (null != b)
                    {
                        contract.setSignUsername(b.getRealName());
                    }
                }
            }
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "contract/contract_settlement_list";
    }
    
    /**
     * 合同结算管理
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/contractSettleManager.do")
    public String contractSettleManager(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CONTRACT_SETTLE"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_SETTLE"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        Pagination<Contract> pagination = service.contractSettleManager(searcher, pageNo, DEFAULTPAGESIZE);
        
        for (Contract c : pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(c.getSignUser()))
            {
                BusinessInfo b = appService.getBusinessInfo(c.getSignUser());
                if (null != b)
                {
                    c.setSignUsername(b.getRealName());
                }
            }
            
            ContractContent cc = service.getContractContentByContractId(c.getId());
            c.setContractContent(cc);
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "contract/contract_settlement_manager";
    }
    
    //出账单页面
    @RequestMapping("/contractGeneBill.do")
    public String contractGeneBillPage(OrderSearchRequest searcher, ModelMap model, HttpSession session)
    {
        searcher.setIfResolveOrderInfo(false);
        List<Order> data = orderService.geneBilllist(searcher); //通过合同id查询订单
        List<Customer> customerList = customerService.listActivity(new Customer());
        model.addAttribute("customerList", customerList);
        
        for (Order o : data)
        {
            for (Customer c : customerList)
            {
                if (StringUtils.isNotEmpty(o.getOwnerId()) && o.getOwnerId().equals(c.getId()))
                {
                    o.setOwner(c);
                }
                
            }
            
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("data", data);
        session.setAttribute("s-searcher", searcher);
        return "contract/contract_settlement_manager_bill";
    }
    
    @RequestMapping("/viewContractBill.do")
    public String viewContractBill(String contractId, ModelMap model, HttpSession session)
    {
        Contract contract = service.getContractById(contractId);
        BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
        if (null != b)
        {
            contract.setSignUsername(b.getRealName());
        }
        model.addAttribute("data", contract);
        return "contract/contract_bill_view";
    }
    
    @RequestMapping("/viewContractBillDetail.do")
    public String viewContractBillDetail(String id, String createTime, ModelMap model, HttpSession session)
    {
        List<Order> contract = service.getContractBillDetailById(id);
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        model.addAttribute("customerList", customerList);
        
        for (Order o : contract)
        {
            for (Customer c : customerList)
            {
                if (StringUtils.isNotEmpty(o.getOwnerId()) && o.getOwnerId().equals(c.getId()))
                {
                    o.setOwner(c);
                }
                
            }
            
        }
        
        model.addAttribute("createTime", createTime);
        model.addAttribute("contractId", id);
        model.addAttribute("data", contract);
        return "contract/bill_detail_form";
    }
    
    @RequestMapping("/createSettleBill.do")
    @ResponseBody
    public Boolean createSettleBill(ContractSettleBillRequest request, Model model, HttpSession session)
    {
        
        AuthorizedUser user = userService.getByToken();
        if (user != null)
        {
            request.setCreateId(user.getId());
            request.setCreateName(user.getName());
        }
        return service.createSettleBill(request);
    }
    
    @RequestMapping("/downloadSettleBill.do")
    @ResponseBody
    public String downloadSettleBill(String id, String createTime, Model model, HttpSession session)
    {
        List<Order> order = service.getContractBillDetailById(id);
        List<Customer> customerList = customerService.listActivity(new Customer());
        model.addAttribute("customerList", customerList);
        
        for (Order o : order)
        {
            for (Customer c : customerList)
            {
                if (StringUtils.isNotEmpty(o.getOwnerId()) && o.getOwnerId().equals(c.getId()))
                {
                    o.setOwner(c);
                }
                
            }
        }
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/Contract_Settle_Bill.xlsx");
        return service.downloadSettleBill(is, order, createTime);
    }
    
    /**
     * 合同款项列表
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping("/contractPayRecordList.do")
    public String contractPayRecordList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CONTRACT_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_LIST"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        searcher.setPayStatus(new String[] {Contract.CONFIRMED, Contract.KNOT});
        searcher.setSecondOrderBy(true); //二级排序
        Pagination<Contract> pagination = service.unRegularlyList(searcher, pageNo, DEFAULTPAGESIZE);
        
        for (Contract c : pagination.getRecords())
        {
            if (StringUtils.isNotEmpty(c.getSignUser()))
            {
                BusinessInfo b = appService.getBusinessInfo(c.getSignUser());
                if (null != b)
                {
                    c.setSignUsername(b.getRealName());
                }
                
            }
            
            ContractContent cc = service.getContractContentByContractId(c.getId());
            c.setContractContent(cc);
            
            BigDecimal amount = searcherOrderAmountByContractId(c.getId());
            c.setOrderAmount(amount);
            c.setRemainAmount(Arith.sub(amount.doubleValue(), c.getIncomingAmount() / 100));
            
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "contract/contract_payrecord_list";
    }
    
    private BigDecimal searcherOrderAmountByContractId(String cid)
    {
        BigDecimal amount = new BigDecimal(0);
        OrderSearchRequest orderSearcher = new OrderSearchRequest();
        orderSearcher.setContractId(cid);
        orderSearcher.setIfResolveOrderInfo(false);
        List<Order> orderResult = orderService.list(orderSearcher);//根据订单获取合同价格
        if (StringUtils.isNotEmpty(orderResult))
        {
            for (Order o : orderResult)
            {
                if (!o.getTestingStatus().equals(4))
                {
                    amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                }
                
            }
            
        }
        return amount;
    }
    
    @RequestMapping("/contractInvoiceManager.do")
    public String contractInvoiceManager(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        return "contract/invoice/contract_invoice_tab";
    }
    
    /**
     * 合同发票待处理
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/contractInvoiceList.do")
    public String contractInvoiceList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        searcher.setApplyStatus(new Integer[] {1});
        invoiceSearcherList(searcher, pageNo, model, session);
        return "contract/invoice/contract_invoice_list";
        
    }
    
    /**
     * 合同发票已处理
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/contractInvoiceHandleList.do")
    public String contractInvoiceHandleList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        if (null == searcher.getApplyStatus() || searcher.getApplyStatus().length == 0)
        {
            searcher.setApplyStatus(new Integer[] {2, 3, 4});
        }
        
        invoiceSearcherList(searcher, pageNo, model, session);
        return "contract/invoice/contract_invoice_handle_list";
        
    }
    
    private void invoiceSearcherList(ContractSearcher searcher, int pageNo, ModelMap model, HttpSession session)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CONTRACT_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_LIST"));
        }
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(DEFAULTPAGESIZE);
        
        Pagination<InvoiceApply> pagination = service.contractInvoiceList(searcher);
        for (InvoiceApply c : pagination.getRecords())
        {
            ContractContent cc = service.getContractContentByContractId(c.getContractId());
            c.setContractContent(cc);
            ContractPartyB cpb = service.getContractPBByContractId(c.getContractId());
            c.setContractPartyB(cpb);
            Contract contract = service.getContractById(c.getContractId());
            c.setContract(contract);
            UserDetailsModel user = userService.getUserByID(c.getCreatorId());
            c.setCreatorId(user.getArchive().getName());
            
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
    }
    
    /**
     * 业务管理 ---发票申请列表
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/contractInvoiceApplyList.do")
    public String contractInvoiceApplyList(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        searcher.setOrderRule(true); //逆序
        invoiceSearcherList(searcher, pageNo, model, session);
        return "contract/invoice/contract_invoice_apply_list";
        
    }
    
    /**
     * 业务管理 ---发票申请查看
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/contractInvoiceApplyDetail.do")
    public String contractInvoiceApplyDetail(String id, String invoiceId, ModelMap model)
    {
        Contract contract = service.getContractById(id);
        
        InvoiceApply data = invoiceApplyService.get(invoiceId);
        if (StringUtils.isNotEmpty(contract))
        {
            ContractPartyA cpa = service.getContractPAByContractId(id);
            contract.setContractPartyA(cpa);
            ContractPartyB cpb = service.getContractPBByContractId(id);
            contract.setContractPartyB(cpb);
            
            List<ContractInvoiceInfo> info = service.getContractInvoiceInfoByApplyId(invoiceId);
            if (Collections3.isNotEmpty(info))
            {
                for (ContractInvoiceInfo o : info)
                {
                    UserDetailsModel user = userService.getUserByID(o.getInvoicePerson());
                    if (StringUtils.isNotEmpty(user))
                    {
                        o.setInvoicePersonName(user.getArchive().getName());
                    }
                }
                
                model.addAttribute("entity", info);
            }
            
            contract.setOrderAmount(searcherOrderAmountByContractId(contract.getId()));
            contract.setCountInvoiceAmount(service.getContractInvoiceAmount(id));
            model.addAttribute("contract", contract);
            model.addAttribute("record", data);
            
        }
        
        return "contract/invoice/contract_invoice_apply_view";
    }
    
    @RequestMapping("/handleContractInvoice.do")
    public String handleContractInvoice(String id, String invoiceId, ModelMap model)
    {
        Contract contract = service.getContractById(id);
        ContractContent cc = service.getContractContentByContractId(id);
        contract.setContractContent(cc);
        ContractPartyB cpb = service.getContractPBByContractId(id);
        contract.setContractPartyB(cpb);
        ContractPartyA cpa = service.getContractPAByContractId(id);
        contract.setContractPartyA(cpa);
        InvoiceApply data = invoiceApplyService.get(invoiceId);
        if (StringUtils.isNotEmpty(contract))
        {
            OrderSearchRequest orderSearcher = new OrderSearchRequest();
            orderSearcher.setContractId(contract.getId());
            orderSearcher.setIfResolveOrderInfo(false);
            
            List<Order> orderResult = orderService.list(orderSearcher);//根据订单获取合同价格
            setContractAmountInfo(contract, cc, orderResult);
            contract.setCountInvoiceAmount(service.getContractInvoiceAmount(id));
            model.addAttribute("contract", contract);
        }
        
        UserSearcher searcher = new UserSearcher();
        List<UserSimpleModel> userlist = userService.list(searcher);
        model.addAttribute("userlist", userlist);
        model.addAttribute("record", data);
        
        return "contract/invoice/handle_invoice_form";
    }
    
    private void setContractAmountInfo(Contract contract, ContractContent cc, List<Order> orderResult)
    {
        BigDecimal amount = new BigDecimal(0);
        BigDecimal incomingAmount = new BigDecimal(0);
        if (StringUtils.isNotEmpty(cc) && !"4".equals(cc.getSettlementMode()))
        { //集中
            for (Order o : orderResult)
            {
                if (!o.getTestingStatus().equals(4))
                { // 过滤已取消
                    amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice())); //减免的金额
                }
                
            }
        }
        else
        {
            for (Order o : orderResult)
            {
                amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                incomingAmount = incomingAmount.add(new BigDecimal(o.getIncomingAmount()));
            }
            contract.setIncomingAmount(incomingAmount.multiply(new BigDecimal(100)).intValue());
        }
        contract.setOrderAmount(amount);
    }
    
    /**
     * 处理申请信息
     * @param contract
     * @param model
     * @return
     */
    @RequestMapping("/handelInvoice.do")
    @ResponseBody
    public Map<String, Object> handelInvoice(ContractInvoiceRequest contract, ModelMap model)
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        AuthorizedUser u = userService.getByToken();
        contract.setCreatorId(u.getId());
        contract.setCreatorName(u.getName());
        // 组装组样本
        List<ContractInvoiceDetail> invoiceDetail = JSON.parseArray(contract.getDetail() + "", ContractInvoiceDetail.class);
        contract.setInvoiceDetail(invoiceDetail);
        service.handelInvoice(contract);
        map.put("result", true);
        return map;
    }
    
    /**
     * 查看 申请
     * @param id
     * @param model
     * @return
     */
    
    @RequestMapping("/viewContractInvoice.do")
    public String viewContractInvoice(String id, String invoiceId, ModelMap model)
    {
        Contract contract = service.getContractById(id);
        
        InvoiceApply data = invoiceApplyService.get(invoiceId);
        if (StringUtils.isNotEmpty(contract))
        {
            ContractPartyA cpa = service.getContractPAByContractId(id);
            contract.setContractPartyA(cpa);
            ContractPartyB cpb = service.getContractPBByContractId(id);
            contract.setContractPartyB(cpb);
            ContractContent content = service.getContractContentByContractId(id);
            
            OrderSearchRequest orderSearcher = new OrderSearchRequest();
            orderSearcher.setContractId(contract.getId());
            orderSearcher.setIfResolveOrderInfo(false);
            List<Order> orderResult = orderService.list(orderSearcher);//根据订单获取合同价格
            List<ContractInvoiceInfo> info = service.getContractInvoiceInfoByApplyId(invoiceId);
            if (Collections3.isNotEmpty(info))
            {
                for (ContractInvoiceInfo o : info)
                {
                    UserDetailsModel user = userService.getUserByID(o.getInvoicePerson());
                    if (StringUtils.isNotEmpty(user))
                    {
                        o.setInvoicePersonName(user.getArchive().getName());
                    }
                }
                
                model.addAttribute("entity", info);
            }
            setContractAmountInfo(contract, content, orderResult);
            contract.setCountInvoiceAmount(service.getContractInvoiceAmount(id));
            model.addAttribute("contract", contract);
            model.addAttribute("record", data);
            
        }
        
        return "contract/invoice/handle_invoice_view";
    }
    
    @RequestMapping("/exchangeProduct.do")
    public String exchangeProduct(String contractId, ModelMap model)
    {
        List<ContractProduct> products = service.getContractProducts(contractId);
        Contract contract = service.getContractById(contractId);
        model.addAttribute("products", products);
        model.addAttribute("contract", contract);
        String productIds = "";
        if (Collections3.isNotEmpty(products))
        {
            int size = products.size() - 1;
            for (int i = 0; i < products.size(); i++)
            {
                ContractProduct p = products.get(i);
                if (i == size)
                {
                    productIds += p.getProductId();
                    continue;
                }
                productIds += p.getProductId() + ",";
            }
        }
        model.addAttribute("productsString", JSON.toJSON(productIds).toString());
        return "contract/contract_product_change";
    }
    
    @RequestMapping("/insertContractProducts.do")
    @ResponseBody
    public String insertContractProducts(String param, String contractId)
    {
        Contract contract = service.getContractById(contractId);
        if (StringUtils.isNotEmpty(param))
        {
            List<ContractProduct> products = JSON.parseArray(param + "", ContractProduct.class);
            contract.setConProducts(products);
            service.addContracrProduct(contract);
        }
        return "ok";
    }
    
    @RequestMapping("/canDelete.do")
    @ResponseBody
    public boolean canDelete(String contractId, String productId)
    {
        boolean flag = false;
        OrderContrctSearcher searcher = new OrderContrctSearcher();
        searcher.setContractId(contractId);
        List<Order> orders = orderService.getOrderByContract(searcher);
        Order order = Collections3.isNotEmpty(orders) ? orders.get(0) : null;
        //科研订单
        if (null != order)
        {
            List<OrderResearchProduct> orderresearchproducts = order.getOrderResearchProduct();
            if (Collections3.isNotEmpty(orderresearchproducts))
            {
                for (OrderResearchProduct p : orderresearchproducts)
                {
                    String pId = null == p.getProduct() ? null : p.getProduct().getId();
                    if (productId.equals(pId))
                    {
                        flag = true;
                    }
                }
            }
            //非科研订单
            List<OrderProduct> orderproducts = order.getOrderProductList();
            if (Collections3.isNotEmpty(orderproducts))
            {
                for (OrderProduct p : orderproducts)
                {
                    String pId = null == p.getProduct() ? null : p.getProduct().getId();
                    if (productId.equals(pId))
                    {
                        flag = true;
                    }
                }
            }
        }
        if (!flag)
        {
            service.deleteContractProduct(contractId, productId);
        }
        return flag;
    }
    
    @RequestMapping("/showContractInvoice.do")
    public String showContractInvoice(String id, ModelMap model)
    {
        Contract contract = service.getContractById(id);
        InsertOrderAmount(contract);
        List<FinanceInvoiceTask> financeinvoicetasks = Lists.newArrayList();
        List<ContractInvoiceInfo> contractinvoiceinfos = Lists.newArrayList();
        if (contract.getContractContent().getInvoiceMethod().equals("1"))
        { //集中
          //合同发票
            contractinvoiceinfos = service.getContractInvoiceInfo(id);
            if (Collections3.isNotEmpty(contractinvoiceinfos))
            {
                contractinvoiceinfos.forEach(info -> {
                    
                    UserDetailsModel user = userService.getUserByID(info.getInvoicePerson());
                    if (StringUtils.isNotEmpty(user))
                    {
                        info.setInvoicePersonName(user.getArchive().getName());
                    }
                });
            }
            model.addAttribute("entity", contractinvoiceinfos);
        }
        else
        {
            List<Order> orders = getOrdersByConrtractId(id);
            //一单一票  查订单
            if (Collections3.isNotEmpty(orders))
            {
                financeinvoicetasks = service.getFinanceInvoiceTaskByOrder(contract, orders);
            }
            model.addAttribute("tasks", financeinvoicetasks);
        }
        
        BigDecimal amount = service.getCountAmount(contractinvoiceinfos, financeinvoicetasks);
        if (null != amount)
        {
            contract.setCountInvoiceAmount(amount.intValue());
        }
        model.addAttribute("contract", contract);
        
        return "/contract/contracte_invoice_view";
    }
    
    @RequestMapping("/endContract")
    @ResponseBody
    @SystemServiceLog(description = "合同管理待办理-结项确认", type = 3)
    public String endContract(String id)
    {
        Contract contract = service.getContractById(id);
        AuthorizedUser u = userService.getByToken();
        contract.setKnotId(u.getId());
        contract.setKnotName(u.getName());
        contract.setKnotTime(new Date());
        contract.setStatus(Contract.KNOT);
        service.update(contract);
        return "endContract";
    }
    
    public Contract InsertOrderAmount(Contract contract)
    {
        if (null != contract)
        {
            ContractContent cc = contract.getContractContent();
            List<Order> orderResult = contract.getOrders();//根据订单获取合同价格
            if (StringUtils.isNotEmpty(orderResult))
            {
                BigDecimal amount = new BigDecimal(0);
                BigDecimal incomingAmount = new BigDecimal(0);
                if (StringUtils.isEmpty(cc.getSettlementMode()))
                {
                    return contract;
                }
                if (!cc.getSettlementMode().equals("4")) //非一单一结
                {
                    for (Order o : orderResult)
                    {
                        if (!o.getTestingStatus().equals(4))
                        { // 过滤已取消
                            amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice())); //减免的金额
                        }
                        
                    }
                }
                else
                {
                    for (Order o : orderResult)
                    {
                        amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                        incomingAmount = incomingAmount.add(new BigDecimal(o.getIncomingAmount()));
                    }
                    contract.setIncomingAmount(incomingAmount.multiply(new BigDecimal(100)).intValue());
                }
                contract.setOrderAmount(amount);
            }
            contract.setContractContent(cc);
        }
        return contract;
    }
    
    private List<Order> getOrdersByConrtractId(String contractId)
    {
        OrderSearchRequest orderSearcher = new OrderSearchRequest();
        orderSearcher.setContractId(contractId);
        orderSearcher.setIfResolveOrderInfo(false);
        return orderService.list(orderSearcher);
    }
    
    @RequestMapping(value = "/saveSignUser.do", method = RequestMethod.POST)
    public String review(ContractSearcher request, ModelMap model, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        request.setOperateId(user.getId());
        request.setOperateName(user.getUsername());
        service.saveSignUser(request);
        return redirectList(model, session, "redirect:/contract/selectContract.do?flag=" + request.getFlag());
    }
    
    //合同财务报表
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    
    @RequestMapping("/exportContractFinancialFile.do")
    @ResponseBody
    public String exportContractFinancialFile(ContractSearcher searcher, HttpSession session) throws Exception
    {
        
        String sessionId = IdGen.uuid();
        
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = service.exportContractFinancialFile(searcher);
                session.setAttribute(sessionId, fileUrl);
            }
            
        });
        return sessionId;
    }


    //通过合同编号获取项目管理人
    @RequestMapping("/getPrjManager")
    @ResponseBody
    public UserSimpleModel getPrjManager(String id)
    {
        Contract contract = service.getContractById(id);
        UserDetailsModel udm = null;
        UserSimpleModel usm  = new UserSimpleModel();
        if (null!=contract)
        {
            if (null!=contract.getProjectManager())
            {
                udm = userService.getUserByID(contract.getProjectManager());
                usm  = new UserSimpleModel();
                usm.setId(udm.getId());
                usm.setName(udm.getArchive().getName());
                usm.setPhone(udm.getArchive().getPhone());
            }
        }
        return usm;
    }
}
