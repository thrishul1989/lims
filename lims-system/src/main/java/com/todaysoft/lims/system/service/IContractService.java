package com.todaysoft.lims.system.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.searcher.ContractSearcher;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractChangeSignUser;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.contract.ContractCreateRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceInfo;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractOrg;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyA;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyB;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.contract.ContractProduct;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBill;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBillRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractUser;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ContractBaseInfoModel;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.TemporaryRequest;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;

public interface IContractService
{
    
    void create(ContractCreateRequest request);
    
    void modify(ContractCreateRequest request);
    
    void delete(ContractSearcher searcher);
    
    Pagination<Contract> paging(ContractSearcher searcher, int pageNo, int pageSize);
    
    Contract getContractById(String id);
    
    boolean validate(ContractSearcher connect);
    
    List<Contract> getContractList(ContractSearcher searcher);
    
    List<ContractOrg> getContractOrgs();
    
    ContractPartyA getContractPAByContractId(String contractId);
    
    ContractPartyB getContractPBByContractId(String contractId);
    
    ContractContent getContractContentByContractId(String contractId);
    
    void addCode(Contract contract);
    
    Integer confirmContract(ContractUser cUser);
    
    ContractOrg getContractOrgByName(ContractSearcher searcher);
    
    void update(Contract contract);
    
    List<Customer> getContractUsers(String contractId);
    
    void downloadData(HttpServletResponse response, String path);
    
    String buildContract(ContractCreateRequest request);
    
    List<ContractProduct> getContractProducts(String contractId);
    
    List<Contract> getContractByUserId(ContractSearcher searcher);
    
    public Pagination<Contract> unRegularlyList(ContractSearcher searcher, int pageNo, int defaultpagesize);
    
    Pagination<ContractPaymentRecord> getRecordByContractId(ContractSearcher searcher, int pageNo, int i);
    
    public Pagination<ContractSettleBill> settlementList(ContractSearcher searcher, int pageNo, int defaultpagesize);
    
    Pagination<Contract> contractSettleManager(ContractSearcher searcher, int pageNo, int defaultpagesize);
    
    Boolean createSettleBill(ContractSettleBillRequest request);
    
    List<Order> getContractBillDetailById(String id);
    
    String downloadSettleBill(InputStream is, List<Order> order, String createTime);
    
    Pagination<InvoiceApply> contractInvoiceList(ContractSearcher searcher);
    
    InvoiceApply getInvoiceApplyInfo(String id);
    
    void handelInvoice(ContractInvoiceRequest data);
    
    void addContracrProduct(Contract cotract);
    
    void deleteContractProduct(String contractId, String productId);
    
    List<InvoiceApply> getInvoiceApplyByContractId(String id);
    
    List<ContractInvoiceInfo> getContractInvoiceInfo(String id);
    
    List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(String invoiceId);
    
    int getContractInvoiceAmount(String id);
    
    List<FinanceInvoiceTask> getTasksByOrderId(String orderId);
    
    List<FinanceInvoiceTask> getTasksByOrders(TemporaryRequest orders);
    
    List<FinanceInvoiceTask> getFinanceInvoiceTaskByOrder(Contract contract, List<Order> orderResults);
    
    BigDecimal getCountAmount(List<ContractInvoiceInfo> infos, List<FinanceInvoiceTask> financeinvoicetasks);
    
    void saveSignUser(ContractSearcher searcher);
    
    List<ContractChangeSignUser> getChangeSignUserList(String contractId);
    
    String exportContractFinancialFile(ContractSearcher searcher);
    
    List<ContractBaseInfoModel> contractReportFormListForWkcreate(ContractSearcher searcher);
    
    String writerContract(String fileName, String fileType, List<ContractBaseInfoModel> contracts, Map<String,List<String>> mapTitle)
        throws Exception;
}
