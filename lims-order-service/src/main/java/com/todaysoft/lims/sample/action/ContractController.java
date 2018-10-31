package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.entity.contract.Contract;
import com.todaysoft.lims.sample.entity.contract.ContractChangeSignUser;
import com.todaysoft.lims.sample.entity.contract.ContractContent;
import com.todaysoft.lims.sample.entity.contract.ContractCreateRequest;
import com.todaysoft.lims.sample.entity.contract.ContractInvoiceInfo;
import com.todaysoft.lims.sample.entity.contract.ContractOrg;
import com.todaysoft.lims.sample.entity.contract.ContractPartyA;
import com.todaysoft.lims.sample.entity.contract.ContractPartyB;
import com.todaysoft.lims.sample.entity.contract.ContractPaymentRecord;
import com.todaysoft.lims.sample.entity.contract.ContractProduct;
import com.todaysoft.lims.sample.entity.contract.ContractQueryRequest;
import com.todaysoft.lims.sample.entity.contract.ContractRequest;
import com.todaysoft.lims.sample.entity.contract.ContractSettleBill;
import com.todaysoft.lims.sample.entity.contract.ContractUpdateRequest;
import com.todaysoft.lims.sample.entity.contract.ContractUser;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.model.contract.ContractBaseInfoModel;
import com.todaysoft.lims.sample.model.contract.ContractFinancial;
import com.todaysoft.lims.sample.model.contract.ContractInvoiceRequest;
import com.todaysoft.lims.sample.model.contract.ContractSettleBillRequest;
import com.todaysoft.lims.sample.service.IContractService;

@RestController
@RequestMapping("/bcm/contract")
public class ContractController
{
    
    @Autowired
    private IContractService service;
    
    @RequestMapping(value = "/list.do")
    public Pagination<Contract> paging(@RequestBody ContractQueryRequest request)
    {
        Pagination<Contract> result=null;

        try{
            result = service.paging(request);
        }catch (Exception e)
        {
           e.printStackTrace();
        }
        return  result;
    }
    
    @RequestMapping(value = "/getContracts.do")
    public List<Contract> getContracts(@RequestBody ContractQueryRequest searcher)
    {
        
        return service.getContractList(searcher);
    }
    
    @RequestMapping(value = "/getContract/{id}")
    public ContractUpdateRequest getContractById(@PathVariable String id)
    {
        ContractUpdateRequest request = new ContractUpdateRequest();
        
        Contract c = service.getContractById(id);
        c.getConProducts().clear();
        c.setConProducts(service.getContractProducts(id));
        if (null != c)
        {
            BeanUtils.copyProperties(c, request);
            request.setContractContent(getContractContentByContractId(c.getId()));
        }
        return request;
    }
    
    @RequestMapping(value = "/create.do")
    public void create(@RequestBody ContractCreateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify.do")
    public void modify(@RequestBody ContractCreateRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/delete")
    public void delete(@RequestBody ContractQueryRequest request)
    {
        service.delete(request);
    }
    
    @RequestMapping(value = "/validate.do")
    public boolean validate(@RequestBody ContractQueryRequest searcher)
    {
        
        return service.validate(searcher);
    }
    
    @RequestMapping(value = "/getContractOrgs.do")
    public List<ContractOrg> getContractOrg()
    {
        List<ContractOrg> lsit = service.getContractOrgs();
        
        return lsit;
    }
    
    @RequestMapping(value = "/getContractPAByContractId/{contractId}")
    public ContractPartyA getContractPAByContractId(@PathVariable String contractId)
    {
        return service.getContractPAByContractId(contractId);
    }
    
    @RequestMapping(value = "/getContractPBByContractId/{contractId}")
    public ContractPartyB getContractPBByContractId(@PathVariable String contractId)
    {
        return service.getContractPBByContractId(contractId);
    }
    
    @RequestMapping(value = "/getContractContentByContractId/{contractId}")
    public ContractContent getContractContentByContractId(@PathVariable String contractId)
    {
        return service.getContractContentByContractId(contractId);
    }
    
    @RequestMapping(value = "/addCode")
    public void addCode(@RequestBody Contract request)
    {
        service.addCode(request);
    }
    
    @RequestMapping(value = "/confirmContract")
    public Integer confirmContract(@RequestBody ContractUser cUser)
    {
        return service.confirmContract(cUser);
    }
    
    @RequestMapping(value = "/getContractOrgByName")
    public ContractOrg getContractOrgByName(@RequestBody ContractQueryRequest searcher)
    {
        return service.getContractOrgByName(searcher);
    }
    
    @RequestMapping(value = "/update.do")
    public void modify(@RequestBody Contract contract)
    {
        service.update(contract);
    }
    
    @RequestMapping(value = "/getContractUsers/{contractId}")
    public List<Customer> getContractUsers(@PathVariable String contractId)
    {
        return service.getContractUsers(contractId);
    }
    
    @RequestMapping(value = "/getContractByUserId")
    public List<Contract> getContractByUserId(@RequestBody ContractQueryRequest request)
    {
        return service.getContractByUserId(request);
    }
    
    @RequestMapping(value = "/getContractProducts/{contractId}")
    public List<ContractProduct> getContractProducts(@PathVariable String contractId)
    {
        return service.getContractProducts(contractId);
    }
    
    @RequestMapping(value = "/unRegularlyList")
    public Pagination<Contract> unRegularlyList(@RequestBody ContractQueryRequest request)
    {
        return service.unRegularlyList(request);
    }
    
    @RequestMapping(value = "/getRecordByContractId")
    public Pagination<ContractPaymentRecord> getRecordByContractId(@RequestBody ContractQueryRequest request)
    {
        
        return service.getRecordByContractId(request);
    }
    
    @RequestMapping(value = "/settlementList")
    public Pagination<ContractSettleBill> settlementList(@RequestBody ContractQueryRequest request)
    {
        return service.settlementList(request);
    }
    
    @RequestMapping(value = "/getContractBillDetailById/{id}")
    public List<Order> getContractBillDetailById(@PathVariable String id)
    {
        return service.getContractBillDetailById(id);
    }
    
    /**
     * 合同结算
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/contractSettleManager")
    public Pagination<Contract> contractSettleManager(@RequestBody ContractQueryRequest request)
    {
        return service.contractSettleManager(request);
    }
    
    @RequestMapping("/createSettleBill.do")
    public Boolean createSettleBill(@RequestBody ContractSettleBillRequest request)
    {
        return service.createSettleBill(request);
    }
    
    @RequestMapping("/contractInvoiceList")
    public Pagination<InvoiceApply> contractInvoiceList(@RequestBody ContractQueryRequest request)
    {
        return service.contractInvoiceList(request);
    }
    
    @RequestMapping(value = "/getInvoiceApplyByContractId/{contractId}")
    public List<InvoiceApply> getInvoiceApplyByContractId(@PathVariable String contractId)
    {
        return service.getInvoiceApplyByContractId(contractId);
    }
    
    @RequestMapping(value = "/getInvoiceApplyInfo/{id}", method = RequestMethod.GET)
    public InvoiceApply getInvoiceApplyInfo(@PathVariable String id)
    {
        return service.getInvoiceApplyInfo(id);
    }
    
    @RequestMapping("/handelInvoice")
    public void handelInvoice(@RequestBody ContractInvoiceRequest data)
    {
        service.handelInvoice(data);
    }
    
    @RequestMapping(value = "/getContractInvoiceInfo/{contractId}")
    public List<ContractInvoiceInfo> getContractInvoiceInfo(@PathVariable String contractId)
    {
        return service.getContractInvoiceInfo(contractId);
    }
    
    @RequestMapping(value = "/getContractInvoiceInfoByApplyId/{invoiceId}")
    public List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(@PathVariable String invoiceId)
    {
        return service.getContractInvoiceInfoByApplyId(invoiceId);
    }
    
    @RequestMapping(value = "/getContractInvoiceAmount/{contractId}")
    public int getContractInvoiceAmount(@PathVariable String contractId)
    {
        return service.getContractInvoiceAmount(contractId);
    }
    
    @RequestMapping(value = "/addContracrProduct.do")
    public void addContracrProduct(@RequestBody ContractRequest request)
    {
        service.addContracrProduct(request);
    }
    
    @RequestMapping(value = "/{contractId}/deleteContractProduct/{productId}")
    public void deleteContractProduct(@PathVariable String contractId, @PathVariable String productId)
    {
        service.deleteContractProduct(contractId, productId);
    }
    
    @RequestMapping(value = "/saveSignUser.do", method = RequestMethod.POST)
    public void saveSignUser(@RequestBody ContractQueryRequest request)
    {
        service.saveChangeSignUser(request);
    }
    
    @RequestMapping(value = "/getChangeSignUserList/{contractId}")
    public List<ContractChangeSignUser> getChangeSignUserList(@PathVariable String contractId)
    {
        return service.getChangeSignUserList(contractId);
    }
    
    @RequestMapping("/exportPaging")
    public Pagination<ContractFinancial> exportPaging(@RequestBody ContractQueryRequest request)
    {
        return service.exportPaging(request);
    }
    
    @RequestMapping("/getContractListForContractBaseInfo")
    public List<ContractBaseInfoModel> getContractListForContractBaseInfo(@RequestBody ContractQueryRequest request)
    {
        return service.getContractListForContractBaseInfo(request);
    }
    
}
