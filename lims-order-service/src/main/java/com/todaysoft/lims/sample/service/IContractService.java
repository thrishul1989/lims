package com.todaysoft.lims.sample.service;

import java.util.List;

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
import com.todaysoft.lims.sample.entity.contract.ContractUser;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.model.contract.ContractBaseInfoModel;
import com.todaysoft.lims.sample.model.contract.ContractFinancial;
import com.todaysoft.lims.sample.model.contract.ContractInvoiceRequest;
import com.todaysoft.lims.sample.model.contract.ContractSettleBillRequest;

public interface IContractService
{
    
    void create(ContractCreateRequest request);
    
    void modify(ContractCreateRequest request);
    
    void delete(ContractQueryRequest connect);
    
    boolean validate(ContractQueryRequest connect);
    
    Pagination<Contract> paging(ContractQueryRequest searcher);
    
    Contract getContractById(String id);
    
    List<Contract> getContractList(ContractQueryRequest searcher);
    
    List<ContractOrg> getContractOrgs();
    
    ContractPartyA getContractPAByContractId(String contractId);
    
    ContractPartyB getContractPBByContractId(String contractId);
    
    ContractContent getContractContentByContractId(String contractId);
    
    void addCode(Contract contract);
    
    Integer confirmContract(ContractUser cUser);
    
    ContractOrg getContractOrgByName(ContractQueryRequest searcher);
    
    void update(Contract contract);
    
    List<Customer> getContractUsers(String contractId);
    
    List<ContractProduct> getContractProducts(String contractId);
    
    List<Contract> getContractByUserId(ContractQueryRequest userId);
    
    Pagination<ContractPaymentRecord> getRecordByContractId(ContractQueryRequest request);
    
    Pagination<Contract> unRegularlyList(ContractQueryRequest request);
    
    Pagination<ContractSettleBill> settlementList(ContractQueryRequest request);
    
    Pagination<Contract> contractSettleManager(ContractQueryRequest request);
    
    Boolean createSettleBill(ContractSettleBillRequest request);
    
    List<Order> getContractBillDetailById(String id);
    
    Pagination<InvoiceApply> contractInvoiceList(ContractQueryRequest request);
    
    InvoiceApply getInvoiceApplyInfo(String id);
    
    void handelInvoice(ContractInvoiceRequest data);
    
    void addContracrProduct(ContractRequest request);
    
    void deleteContractProduct(String contractId, String productId);
    
    List<InvoiceApply> getInvoiceApplyByContractId(String contractId);
    
    List<ContractInvoiceInfo> getContractInvoiceInfo(String contractId);
    
    List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(String invoiceId);
    
    int getContractInvoiceAmount(String contractId);
    
    void saveChangeSignUser(ContractQueryRequest request);
    
    List<ContractChangeSignUser> getChangeSignUserList(String contractId);
    
    List<ContractProduct> getFilterCPByContractId(String contractId);
    
    Pagination<ContractFinancial> exportPaging(ContractQueryRequest request);
    
    List<ContractBaseInfoModel> getContractListForContractBaseInfo(ContractQueryRequest searcher);
}
