package com.todaysoft.lims.sample.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.sample.model.order.OrderSearchRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.ContractSearcher;
import com.todaysoft.lims.sample.entity.Dict;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.entity.InvoiceInfo;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.contract.BusinessInfo;
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
import com.todaysoft.lims.sample.entity.contract.ContractSample;
import com.todaysoft.lims.sample.entity.contract.ContractSampleRequest;
import com.todaysoft.lims.sample.entity.contract.ContractSettleBill;
import com.todaysoft.lims.sample.entity.contract.ContractSettleBillDetail;
import com.todaysoft.lims.sample.entity.contract.ContractUser;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.contract.ChangeUserInfo;
import com.todaysoft.lims.sample.model.contract.ContractBaseInfoModel;
import com.todaysoft.lims.sample.model.contract.ContractFinancial;
import com.todaysoft.lims.sample.model.contract.ContractInvoiceDetail;
import com.todaysoft.lims.sample.model.contract.ContractInvoiceRequest;
import com.todaysoft.lims.sample.model.contract.ContractOriginal;
import com.todaysoft.lims.sample.model.contract.ContractSettleBillRequest;
import com.todaysoft.lims.sample.model.contract.ContractUserInfo;
import com.todaysoft.lims.sample.model.contract.PartyAInfo;
import com.todaysoft.lims.sample.model.contract.PartyBInfo;
import com.todaysoft.lims.sample.model.contract.ProductInfo;
import com.todaysoft.lims.sample.model.contract.SettlementInfo;
import com.todaysoft.lims.sample.model.contract.SuccessDelivery;
import com.todaysoft.lims.sample.model.contract.TestInfo;
import com.todaysoft.lims.sample.service.IContractService;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.util.CodeUtils;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.sample.util.OrderTypeMenu;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ContractService implements IContractService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private UserAdapter userAdapter;
    
    private Logger log = Logger.getLogger(ContractService.class);
    
    @Override
    public Pagination<Contract> paging(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);

        Pagination<Contract> resultTemp = baseDaoSupport.find(searcher.toAuthQuery(), request.getPageNo(), request.getPageSize(), Contract.class);
        if(Collections3.isNotEmpty(resultTemp.getRecords()))
        {
            for(Contract contract:resultTemp.getRecords())
            {
                if(null != contract)
                {
                    ContractContent contractContent = getContractContentByContractId(contract.getId());
                    if(null != contractContent)
                    {
                        contract.setContractContent(contractContent);
                    }

                    List<ContractInvoiceInfo> contractInvoiceInfoList = getContractInvoiceInfo(contract.getId());
                    if(Collections3.isNotEmpty(contractInvoiceInfoList))
                    {
                        contract.setContractInvoiceInfoList(contractInvoiceInfoList);
                    }
                    if(null != contract.getSignUser())
                    {
                        BusinessInfo businessInfo = baseDaoSupport.get(BusinessInfo.class,contract.getSignUser());
                        contract.setSignUsername(null != businessInfo?businessInfo.getRealName():"");
                    }
                }
//                OrderSearchRequest orderSearch = new OrderSearchRequest();
//                orderSearch.setIfResolveOrderInfo(false);
//                orderSearch.setContractId(contract.getId());
//                List<Order> orderList = orderService.orderList(orderSearch);
//                if(Collections3.isNotEmpty(orderList))
//                {
//                    contract.setOrderList(orderList);
//                }
            }
        }
        return resultTemp;
    }
    
    @Override
    public List<Contract> getContractList(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public Contract getContractById(String id)
    {
        
        return baseDaoSupport.get(Contract.class, id);
    }
    
    @Override
    @Transactional
    public void create(ContractCreateRequest request)
    {
        //业务库-合同主表
        Contract contract = request.getContract();
        if (null != contract)
        {
            if (StringUtils.isEmpty(contract.getStatus()))
            {
                contract.setStatus("0");
            }
            contract.setCreateTime(new Date());
            contract.setUpdateTime(new Date());
            contract.setUnReconciledAmount(0); //默认未对账0
            baseDaoSupport.insert(contract);
            
            //业务库-合同内容
            ContractContent contractcontent = request.getContractcontent();
            if (null != contractcontent)
            {
                contractcontent.setContractId(contract.getId());
                baseDaoSupport.insert(contractcontent);
            }
            //业务库-合同甲方资料
            ContractPartyA contractpa = request.getContractpa();
            if (null != contractpa)
            {
                contractpa.setContract(contract);
                baseDaoSupport.insert(contractpa);
            }
            //业务库-合同乙方资料
            ContractPartyB contractpb = request.getContractpb();
            if (null != contractpb)
            {
                contractpb.setContract(contract);
                baseDaoSupport.insert(contractpb);
            }
            //业务库-合同产品设置
            List<ContractProduct> contractproducts = request.getConProducts();
            if (Collections3.isNotEmpty(contractproducts))
            {
                for (ContractProduct contractProduct : contractproducts)
                {
                    contractProduct.setContract(contract);
                    baseDaoSupport.insert(contractProduct);
                }
            }
            //业务库-合同样本设置
            List<ContractSampleRequest> contractsamples = request.getConSamples();
            if (Collections3.isNotEmpty(contractsamples))
            {
                for (ContractSampleRequest contractSample : contractsamples)
                {
                    ContractSample cs = new ContractSample();
                    List<String> sapmleIds = contractSample.getSampleTypeKeys();
                    cs.setContract(contract);
                    cs.setSampleCategory(contractSample.getSampleCategory());
                    cs.setSampleTypeKeys(StringUtils.join(sapmleIds.toArray(), ","));
                    baseDaoSupport.insert(cs);
                }
            }
            
            //业务库-合同客户设置
            List<ContractUser> contractusers = request.getConUsers();
            if (Collections3.isNotEmpty(contractusers))
            {
                for (ContractUser contractUser : contractusers)
                {
                    contractUser.setContract(contract);
                    baseDaoSupport.insert(contractUser);
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void modify(ContractCreateRequest request)
    {
        
        //主表修改数据
        Contract contract = request.getContract();
        contract.setUpdateTime(new Date());
        if (null != contract)
        {
            baseDaoSupport.executeHql("delete ContractContent c where c.contractId = ?", new Object[] {contract.getId()});
            ContractContent cc = request.getContractcontent();
            if (null != cc)
            {
                cc.setContractId(contract.getId());
                baseDaoSupport.insert(cc);
            }
            baseDaoSupport.executeHql("delete ContractPartyA c where c.contract.id = ?", new Object[] {contract.getId()});
            ContractPartyA cpa = request.getContractpa();
            if (null != cpa)
            {
                cpa.setContract(contract);
                baseDaoSupport.insert(cpa);
            }
            baseDaoSupport.executeHql("delete ContractPartyB c where c.contract.id = ?", new Object[] {contract.getId()});
            ContractPartyB cpb = request.getContractpb();
            if (null != cpb)
            {
                ContractQueryRequest searcher = new ContractQueryRequest();
                searcher.setSignUser(cpb.getCompanyName());
                cpb.setTestInstitution(StringUtils.isNotEmpty(getContractOrgByName(searcher)) ? getContractOrgByName(searcher).getTestInstitution() : "0");
                cpb.setContract(contract);
                baseDaoSupport.insert(cpb);
            }
            baseDaoSupport.executeHql("delete ContractSample c where c.contract.id = ?", new Object[] {contract.getId()});
            List<ContractSampleRequest> css = request.getConSamples();
            if (Collections3.isNotEmpty(css))
            {
                for (ContractSampleRequest contractSampleRequest : css)
                {
                    ContractSample cs = new ContractSample();
                    List<String> sapmleIds = contractSampleRequest.getSampleTypeKeys();
                    cs.setContract(contract);
                    cs.setSampleCategory(contractSampleRequest.getSampleCategory());
                    cs.setSampleTypeKeys(StringUtils.join(sapmleIds.toArray(), ","));
                    baseDaoSupport.insert(cs);
                }
            }
            baseDaoSupport.executeHql("delete ContractProduct c where c.contract.id = ?", new Object[] {contract.getId()});
            List<ContractProduct> cs = request.getConProducts();
            if (Collections3.isNotEmpty(cs))
            {
                for (ContractProduct contractProduct : cs)
                {
                    contractProduct.setContract(contract);
                    baseDaoSupport.insert(contractProduct);
                }
            }
            baseDaoSupport.executeHql("delete ContractUser c where c.contract.id = ?", new Object[] {contract.getId()});
            List<ContractUser> contractusers = request.getConUsers();
            if (Collections3.isNotEmpty(contractusers))
            {
                for (ContractUser contractUser : contractusers)
                {
                    contractUser.setContract(contract);
                    baseDaoSupport.insert(contractUser);
                }
            }
            
            baseDaoSupport.update(contract);
        }
    }
    
    @Override
    @Transactional
    public void delete(ContractQueryRequest request)
    {
        if ("0".equals(request.getStatus()))
        {
            baseDaoSupport.delete(getContractById(request.getId()));
        }
        else
        {
            Contract c = getContractById(request.getId());
            c.setDeleted(true);
            c.setDeleteTime(new Date());
            baseDaoSupport.update(c);
        }
        
        baseDaoSupport.executeHql("delete ContractPartyB c where c.contract.id = ?", new Object[] {request.getId()});
        baseDaoSupport.executeHql("delete ContractPartyA c where c.contract.id = ?", new Object[] {request.getId()});
        baseDaoSupport.executeHql("delete ContractContent c where c.contractId = ?", new Object[] {request.getId()});
        baseDaoSupport.executeHql("delete ContractSample c where c.contract.id = ?", new Object[] {request.getId()});
        baseDaoSupport.executeHql("delete ContractProduct c where c.contract.id = ?", new Object[] {request.getId()});
        baseDaoSupport.executeHql("delete ContractUser c where c.contract.id = ?", new Object[] {request.getId()});
    }
    
    @Override
    public boolean validate(ContractQueryRequest request)
    {
        
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Contract.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<ContractOrg> getContractOrgs()
    {
        return baseDaoSupport.find(ContractOrg.class);
    }
    
    @Override
    public ContractPartyA getContractPAByContractId(String contractId)
    {
        String hql = "FROM ContractPartyA c WHERE c.contract.id = :contractId";
        List<ContractPartyA> records = baseDaoSupport.findByNamedParam(ContractPartyA.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public ContractPartyB getContractPBByContractId(String contractId)
    {
        String hql = "FROM ContractPartyB c WHERE c.contract.id = :contractId";
        List<ContractPartyB> records = baseDaoSupport.findByNamedParam(ContractPartyB.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public ContractContent getContractContentByContractId(String contractId)
    {
        String hql = "FROM ContractContent c WHERE c.contractId = :contractId";
        List<ContractContent> records = baseDaoSupport.findByNamedParam(ContractContent.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    @Transactional
    public void addCode(Contract contract)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String formateDate = format.format(new Date());
        String flag = null;
        if (null != contract.getMarketingCenter())
        {
            flag = getStr(contract.getMarketingCenter());
        }
        else
        {
            log.error("获取流水号有误！请检查数据库流水号表是否新建默认数据！");
            
        }
        contract.setCode("MGN" + formateDate + flag + String.format("%04d", selectContractCodeSeqNextVal()));
        contract.setUpdateTime(new Date());
        baseDaoSupport.update(contract);
    }
    
    @Override
    @Transactional
    public Integer confirmContract(ContractUser cUser)
    {
        Contract c = cUser.getContract();
        c.setUpdateTime(new Date());
        baseDaoSupport.executeHql("delete ContractUser cu where cu.contract.id = ?", new Object[] {c.getId()});
        if (StringUtils.isNotEmpty(cUser.getUserId()))
        {
            for (String userId : cUser.getUserId().split(","))
            {
                ContractUser cu = new ContractUser();
                cu.setContract(c);
                cu.setUserId(userId);
                baseDaoSupport.insert(cu);
            }
        }
        c.setStatus("2");
        baseDaoSupport.update(c);
        return 0;
    }
    
    @Override
    public ContractOrg getContractOrgByName(ContractQueryRequest searcher)
    {
        String hql = "FROM ContractOrg c WHERE c.companyName = :companyName";
        List<ContractOrg> records =
            baseDaoSupport.findByNamedParam(ContractOrg.class, hql, new String[] {"companyName"}, new Object[] {searcher.getSignUser()});
        return Collections3.isNotEmpty(records) ? records.get(0) : null;
    }
    
    @Override
    @Transactional
    public void update(Contract contract)
    {
        contract.setUpdateTime(new Date());
        baseDaoSupport.update(contract);
    }
    
    public Integer selectContractCodeSeqNextVal()
    {
        List<?> result = baseDaoSupport.findBySql("SELECT NEXTVAL('contractCode')");
        if (result != null)
        {
            return (Integer)result.get(0);
        }
        else
        {
            log.error("获取流水号有误！请检查数据库流水号表是否新建默认数据！");
            return 1;
        }
    }
    
    public String getStr(Integer m)
    {
        if (m == 1)
        {
            return "C";
        }
        else if (m == 2)
        {
            return "O";
        }
        else if (m == 3)
        {
            return "H";
        }
        return "R";
    }
    
    @Override
    public List<Customer> getContractUsers(String contractId)
    {
        String hql = "select c FROM Customer c, ContractUser u WHERE u.contract.id = :contractId " + "and u.userId = c.id and  c.disableStatus =:disable ";
        List<Customer> result = baseDaoSupport.findByNamedParam(Customer.class, hql, new String[] {"contractId", "disable"}, new Object[] {contractId, 0});
        return result;
    }
    
    @Override
    public List<ContractProduct> getContractProducts(String contractId)
    {
        List<ContractProduct> result = new ArrayList<ContractProduct>();
        String hql = "select c,p.enabled FROM ContractProduct c,Product p  WHERE p.id = c.productId and c.contract.id = :contractId ";
        List<Object[]> list = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"contractId"}, new Object[] {contractId});
        if (Collections3.isNotEmpty(list))
        {
            ContractProduct cp = null;
            for (Object[] record : list)
            {
                cp = (ContractProduct)record[0];
                cp.setProductStatus((Integer)record[1]);
                result.add(cp);
            }
            
        }
        return result;
    }
    
    @Override
    public List<Contract> getContractByUserId(ContractQueryRequest request)
    {
        String hql = "select c FROM Contract c ,ContractUser u WHERE u.userId = :userId and c.id = u.contract.id and c.marketingCenter =:center ";
        List<Contract> result =
            baseDaoSupport.findByNamedParam(Contract.class,
                hql,
                new String[] {"userId", "center"},
                new Object[] {request.getUserId(), request.getMarketingCenter()});
        return result;
    }
    
    @Override
    public Pagination<ContractPaymentRecord> getRecordByContractId(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ContractPaymentRecord> c =
            baseDaoSupport.find(searcher.toPayQuery(), request.getPageNo(), request.getPageSize(), ContractPaymentRecord.class);
        return c;
    }
    
    @Override
    public Pagination<Contract> unRegularlyList(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Contract> c = baseDaoSupport.find(searcher.toPaymentQuery(), request.getPageNo(), request.getPageSize(), Contract.class);
        return c;
    }
    
    @Override
    public Pagination<ContractSettleBill> settlementList(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<ContractSettleBill> c =
            baseDaoSupport.find(searcher.toSettlementQuery(), request.getPageNo(), request.getPageSize(), ContractSettleBill.class);
        
        for (ContractSettleBill b : c.getRecords())
        {
            b.setContractId(b.getContract().getId());
            b.setContract(null);
        }
        
        return c;
    }
    
    @Override
    public Pagination<Contract> contractSettleManager(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Contract> c = baseDaoSupport.find(searcher.toSettleManagerQuery(), request.getPageNo(), request.getPageSize(), Contract.class);
        return c;
    }
    
    /**
     * 创建合同出账单
     */
    @Override
    @Transactional
    public Boolean createSettleBill(ContractSettleBillRequest request)
    {
        ContractSettleBill entity = null;
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            String[] ids = request.getOrderId().split(",");
            entity = new ContractSettleBill();
            BeanUtils.copyProperties(request, entity);
            entity.setCode(CodeUtils.getContractSettleBillCode());
            entity.setStatus(0); //默认值
            entity.setContract(baseDaoSupport.get(Contract.class, request.getContractId()));
            entity.setCreateTime(new Date());
            entity.setOrderCount(ids.length);
            baseDaoSupport.insert(entity);
            
            ContractSettleBillDetail detail = null;
            for (String id : ids)
            {
                detail = new ContractSettleBillDetail();
                detail.setOrderId(id);
                detail.setSettleBill(entity);
                Order o = baseDaoSupport.get(Order.class, id);
                detail.setOrderAmount(StringUtils.isNotEmpty(o) ? o.getAmount() + o.getSubsidiarySampleAmount() - o.getDiscountAmount() : 0);
                baseDaoSupport.insert(detail);
            }
        }
        
        return true;
    }
    
    @Override
    public List<Order> getContractBillDetailById(String id)
    {
        String hql = "select o FROM ContractSettleBillDetail c ,Order o WHERE c.settleBill.id = :id and c.orderId=o.id  ";
        List<Order> result = baseDaoSupport.findByNamedParam(Order.class, hql, new String[] {"id"}, new Object[] {id});
        return result;
    }
    
    /**
     * 合同发票 待处理列表
     */
    @Override
    public Pagination<InvoiceApply> contractInvoiceList(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<InvoiceApply> c = baseDaoSupport.find(searcher.toInvoiceQuery(), request.getPageNo(), request.getPageSize(), InvoiceApply.class);
        return c;
    }
    
    @Override
    public InvoiceApply getInvoiceApplyInfo(String id)
    {
        return baseDaoSupport.get(InvoiceApply.class, id);
    }
    
    @Override
    @Transactional
    public void handelInvoice(ContractInvoiceRequest data)
    {
        
        if (StringUtils.isNotEmpty(data.getInvoiceApplyId()))
        {
            InvoiceApply apply = baseDaoSupport.get(InvoiceApply.class, data.getInvoiceApplyId());
            if (StringUtils.isNotEmpty(apply))
            {
                apply.setStatus(data.getStatus()); //设置状态 ，2-未通过，3-已开票
                apply.setAuthIdea(data.getRemark()); //审核意见
                baseDaoSupport.update(apply);//更新
                
                if (3 == data.getStatus()) //通过才会开票
                {
                    ContractInvoiceInfo entity = null;
                    if (Collections3.isNotEmpty(data.getInvoiceDetail()))
                    {
                        for (ContractInvoiceDetail d : data.getInvoiceDetail())
                        {
                            entity = new ContractInvoiceInfo();
                            BeanUtils.copyProperties(d, entity);
                            entity.setInvoiceAccount(d.getInvoiceAccount());
                            entity.setCreatorId(data.getCreatorId());
                            entity.setCreatorName(data.getCreatorName());
                            entity.setContractId(data.getContractId());
                            entity.setInvoiceApplyId(data.getInvoiceApplyId());
                            entity.setCreateTime(new Date());
                            baseDaoSupport.insert(entity);
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    @Override
    @Transactional
    public void addContracrProduct(ContractRequest request)
    {
        List<ContractProduct> ps = request.getConProducts();
        Contract c = new Contract();
        BeanUtils.copyProperties(request, c);
        if (Collections3.isNotEmpty(ps))
        {
            for (ContractProduct p : ps)
            {
                p.setContract(c);
                baseDaoSupport.insert(p);
            }
        }
    }
    
    @Override
    public void deleteContractProduct(String contractId, String productId)
    {
        baseDaoSupport.executeHql("delete ContractProduct c where c.contract.id = ? and c.productId = ?", new Object[] {contractId, productId});
    }
    
    @Override
    public List<InvoiceApply> getInvoiceApplyByContractId(String contractId)
    {
        String hql = "FROM InvoiceApply c WHERE c.contractId = :contractId and c.status !=:status";
        List<InvoiceApply> records =
            baseDaoSupport.findByNamedParam(InvoiceApply.class, hql, new String[] {"contractId", "status"}, new Object[] {contractId, 1});
        return records;
    }
    
    @Override
    public List<ContractInvoiceInfo> getContractInvoiceInfo(String contractId)
    {
        String hql = "select c FROM ContractInvoiceInfo c  WHERE c.contractId = :id  ";
        List<ContractInvoiceInfo> results = baseDaoSupport.findByNamedParam(ContractInvoiceInfo.class, hql, new String[] {"id"}, new Object[] {contractId});
        if (Collections3.isNotEmpty(results))
        {
            results.forEach(result -> {
                InvoiceApply invoiceapply = baseDaoSupport.get(InvoiceApply.class, result.getInvoiceApplyId());
                if (null != invoiceapply)
                {
                    result.setInvoiceTitle(invoiceapply.getInvoiceTitle());
                }
            });
        }
        return results;
    }
    
    @Override
    public List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(String invoiceId)
    {
        String hql = "select c FROM ContractInvoiceInfo c  WHERE c.invoiceApplyId = :id  ";
        List<ContractInvoiceInfo> result = baseDaoSupport.findByNamedParam(ContractInvoiceInfo.class, hql, new String[] {"id"}, new Object[] {invoiceId});
        return result;
    }
    
    @Override
    public int getContractInvoiceAmount(String contractId)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(c.invoiceAccount) FROM ContractInvoiceInfo c  WHERE c.contractId = :id  ");
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        int resultcount = 0;
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    public int getContractOrdersAmount(String contractId, Integer settleType)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(s.amount+s.subsidiarySampleAmount-s.discountAmount) FROM Order s  WHERE s.deleted=false and s.testingStatus !='0' and  s.contract.id = :id  ");
        if (settleType.equals(1))
        {
            hql.append(" and s.testingStatus !='4' "); //集中的过滤
        }
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    public int getContractOrdersReduceAmount(String contractId, Integer settleType)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append(" select sum(r.checkAmount) from Order o ,OrderReduce r,Contract c where o.deleted=false and o.testingStatus !='0' and o.id = r.orderId.id and o.contract = c.id and c.id =:id  ");
        if (settleType.equals(1))
        {
            hql.append(" and o.testingStatus !='4' "); //集中的过滤 ,一单一结 2
        }
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    @Override
    @Transactional
    public void saveChangeSignUser(ContractQueryRequest request)
    {
        ContractChangeSignUser contractChangeSignUser = new ContractChangeSignUser();
        Contract contract = baseDaoSupport.get(Contract.class, request.getId());
        contractChangeSignUser.setContractId(request.getId());
        contractChangeSignUser.setBeforeSignUser(contract.getSignUser());
        contractChangeSignUser.setAfterSignUser(request.getEditSignUser());
        contractChangeSignUser.setOperateId(request.getOperateId());
        contractChangeSignUser.setOperateName(request.getOperateName());
        contractChangeSignUser.setOperateTime(new Date());
        baseDaoSupport.insert(contractChangeSignUser);
        contract.setSignUser(request.getEditSignUser());
        contract.setProjectManager(request.getEditPrjManager());
        baseDaoSupport.update(contract);
    }
    
    @Override
    public List<ContractChangeSignUser> getChangeSignUserList(String contractId)
    {


        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ContractChangeSignUser ccs WHERE ccs.contractId = :contractId ORDER BY ccs.operateTime DESC")
                .names(Arrays.asList("contractId"))
                .values(Arrays.asList(contractId))
                .build();
        
        List<ContractChangeSignUser> records = baseDaoSupport.find(queryer, ContractChangeSignUser.class);
        return records;
    }
    
    @Override
    public List<ContractProduct> getFilterCPByContractId(String contractId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("select cp From ContractProduct cp,Product p where cp.productId = p.id  and cp.contract.id = :contractId")
                .names(Arrays.asList("contractId"))
                .values(Arrays.asList(contractId))
                .build();
        return baseDaoSupport.find(queryer, ContractProduct.class);
    }
    
    @Override
    public Pagination<ContractFinancial> exportPaging(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Contract> paging = baseDaoSupport.find(createQuery(searcher), request.getPageNo(), request.getPageSize(), Contract.class);
        
        Pagination<ContractFinancial> pager = new Pagination<ContractFinancial>();
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
        
        List<ContractFinancial> orderFinancials = new ArrayList<ContractFinancial>();
        for (Contract o : paging.getRecords())
        {
            ContractFinancial financial = new ContractFinancial();
            financial.setCode(o.getCode());
            financial.setAmount(o.getAmount().intValue());
            ContractPartyA partA = getContractPAByContractId(o.getId());
            financial.setCompanyName(StringUtils.isNotEmpty(partA) ? partA.getCompanyName() : "");
            
            financial.setMarketingCenter(OrderTypeMenu.hintOfValue(o.getMarketingCenter()));
            financial.setName(o.getName());
            
            ContractContent contractContent = getContractContentByContractId(o.getId());
            if (StringUtils.isNotEmpty(contractContent) && !contractContent.getSettlementMode().equals("4"))
            { //集中
                financial.setOrdersAmount(getContractOrdersAmount(o.getId(), 1) - getContractOrdersReduceAmount(o.getId(), 1));
                financial.setIncomingAmount(o.getIncomingAmount());
            }
            else
            {//一单一结
                financial.setOrdersAmount(getContractOrdersAmount(o.getId(), 0) - getContractOrdersReduceAmount(o.getId(), 0));
                financial.setIncomingAmount(searchOrderSumIncomingAmount(o.getId())); //非一单一结
            }
            if (StringUtils.isNotEmpty(contractContent) && StringUtils.isNotEmpty(contractContent.getInvoiceMethod())
                && contractContent.getInvoiceMethod().equals("1"))
            {
                financial.setInvoiceAmount(getContractInvoiceAmount(o.getId()));//集中---查询合同开票申请已开金额
            }
            else
            {
                //根据合同找所有订单
                List<String> orders = searcherOrderIdsByContractId(o.getId());
                BigDecimal invoiceamount = new BigDecimal(0);
                for (String orderId : orders)
                {
                    List<FinanceInvoiceTask> financeinvoicetasks = orderService.getInvoiceInfoByOrderId(orderId);
                    if (Collections3.isNotEmpty(financeinvoicetasks))
                    {
                        for (FinanceInvoiceTask task : financeinvoicetasks)
                        {
                            List<InvoiceInfo> invoiceinfos = task.getInfoList();
                            if (Collections3.isNotEmpty(invoiceinfos))
                            {
                                for (InvoiceInfo info : invoiceinfos)
                                {
                                    if (orders.contains(info.getOrderIds()) || StringUtils.isEmpty(info.getOrderIds()))
                                    {
                                        invoiceamount = invoiceamount.add(info.getInvoiceAmount());
                                    }
                                    
                                }
                            }
                        }
                    }
                    
                }
                financial.setInvoiceAmount(invoiceamount.multiply(new BigDecimal(100)).intValue());//一单一结---订单开票总和
                
            }
            String statusText = "已确认";
            
            if (DateUtil.toEndDate(o.getEffectiveEnd()).before(new Date()))
            {
                statusText = "合同期满";
            }
            if (o.getStatus().equals("3"))
            {
                statusText = "已结项";
            }
            financial.setStatus(statusText);
            
            financial.setCreatorName(getContractSignUserName(o.getSignUser()));
            orderFinancials.add(financial);
            
        }
        pager.setRecords(orderFinancials);
        return pager;
    }
    
    private String getContractSignUserName(String id)
    {
        BusinessInfo b = baseDaoSupport.get(BusinessInfo.class, id);
        if (StringUtils.isNotEmpty(b))
        {
            return b.getRealName();
        }
        return "";
    }
    
    private List<String> searcherOrderIdsByContractId(String id)
    {
        String hql = "select s.id from Order s where s.contract.id =:contractid";
        return baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"contractid"}, new Object[] {id});
        
    }
    
    private Integer searchOrderSumIncomingAmount(String contractId)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(s.incomingAmount) FROM Order s  WHERE s.deleted=false and s.testingStatus !='0' and  s.contract.id = :id  ");
        
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    private NamedQueryer createQuery(ContractSearcher searcher)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM Contract s where s.status >= '2' and s.deleted = false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters, searcher);
        hql.append(" order by s.createTime asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, ContractSearcher searcher)
    {
        if (StringUtils.isNotEmpty(searcher.getMarketingCenter()))
        {
            hql.append(" and s.marketingCenter =:marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(searcher.getMarketingCenter());
        }
        
        if (StringUtils.isNotEmpty(searcher.getCode()))
        {
            hql.append(" and s.code LIKE:code");
            paramNames.add("code");
            parameters.add("%" + searcher.getCode() + "%");
        }
    }
    
    @Override
    public List<ContractBaseInfoModel> getContractListForContractBaseInfo(ContractQueryRequest request)
    {
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Contract> contractList = baseDaoSupport.find(searcher.toReportFormQuery(), Contract.class);
        List<ContractBaseInfoModel> baseInfoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(contractList))
        {
            for (Contract contract : contractList)
            {
                if (StringUtils.isNotEmpty(contract.getCode()))
                    if ("0".equals(contract.getStatus()))
                    {
                        if (StringUtils.isNotEmpty(contract.getCode()))
                        {
                            ContractBaseInfoModel baseInfoModel = new ContractBaseInfoModel();
                            baseInfoModel.setCode(contract.getCode());
                            baseInfoModel.setName(contract.getName());
                            if ("1".equals(contract.getStatus()))
                            {
                                baseInfoModel.setStatus("待办理");
                            }
                            else if ("2".equals(contract.getStatus()))
                            {
                                if (contract.getEffectiveEnd().after(new Date()))
                                {
                                    baseInfoModel.setStatus("已确认");
                                }
                                else
                                {
                                    baseInfoModel.setStatus("合同期满");
                                }
                            }
                            else if ("3".equals(contract.getStatus()))
                            {
                                baseInfoModel.setStatus("已结项");
                            }
                            else
                            {
                                baseInfoModel.setStatus("草稿");
                            }
                            baseInfoModel.setStartTime(DateUtil.format(contract.getEffectiveStart(), "yyyy-MM-dd"));
                            baseInfoModel.setEndTime(DateUtil.format(contract.getEffectiveEnd(), "yyyy-MM-dd"));
                            if (null != contract.getMarketingCenter())
                            {
                                if (1 == contract.getMarketingCenter())
                                {
                                    baseInfoModel.setMarketingCenter("临床遗传");
                                }
                                else if (2 == contract.getMarketingCenter())
                                {
                                    baseInfoModel.setMarketingCenter("临床肿瘤");
                                }
                                else if (3 == contract.getMarketingCenter())
                                {
                                    baseInfoModel.setMarketingCenter("健康筛查");
                                }
                                else
                                {
                                    baseInfoModel.setMarketingCenter("科技服务");
                                }
                            }
                            BusinessInfo businessInfo = baseDaoSupport.get(BusinessInfo.class, contract.getSignUser());
                            if (null != businessInfo)
                            {
                                baseInfoModel.setSignUser(businessInfo.getRealName());
                            }
                            if (null != contract.getSignDate())
                            {
                                baseInfoModel.setSignDate(DateUtil.format(contract.getSignDate(), "yyyy-MM-dd"));
                            }
                            if ("0".equals(contract.getHospitalAdmited()))
                            {
                                baseInfoModel.setHospitalAdmited("否");
                            }
                            if ("1".equals(contract.getHospitalAdmited()))
                            {
                                baseInfoModel.setHospitalAdmited("是");
                            }
                            ContractContent cc = getContractContentByContractId(contract.getId());
                            if (null != cc)
                            {
                                baseInfoModel.setInvoiceMethod(getText("INVOICE_METHOD", cc.getInvoiceMethod()));
                                baseInfoModel.setRemark(cc.getSettlementRemark());
                            }
                            if (null != contract.getStartMode())
                            {
                                baseInfoModel.setStartMode(getText("START_MODE", contract.getStartMode().toString()));
                            }
                            //-----------------甲方信息-------------------
                            warpPartyAInfo(contract, baseInfoModel);
                            //-----------------乙方信息-------------------
                            warpPartyBInfo(contract, baseInfoModel);
                            //----------------成果交付--------------------
                            warpSuccessDelivery(contract, baseInfoModel);
                            //----------------结算方式--------------------
                            warpSettlementInfo(contract, baseInfoModel);
                            //----------------产品列表--------------------
                            warpProductInfo(contract, baseInfoModel);
                            //----------------实验信息--------------------
                            warpTestInfo(contract, baseInfoModel);
                            //----------------合同原件--------------------
                            warpContractOriginal(contract, baseInfoModel);
                            //----------------合同对象信息--------------------
                            warpContractUserInfo(contract, baseInfoModel);
                            //----------------变更业务员信息--------------------
                            warpChangeUserInfo(contract, baseInfoModel);
                            baseInfoList.add(baseInfoModel);
                        }
                    }
                    else
                    {
                        ContractBaseInfoModel baseInfoModel = new ContractBaseInfoModel();
                        baseInfoModel.setCode(contract.getCode());
                        baseInfoModel.setName(contract.getName());
                        if ("1".equals(contract.getStatus()))
                        {
                            baseInfoModel.setStatus("待办理");
                        }
                        else if ("2".equals(contract.getStatus()))
                        {
                            if (contract.getEffectiveEnd().after(new Date()))
                            {
                                baseInfoModel.setStatus("已确认");
                            }
                            else
                            {
                                baseInfoModel.setStatus("合同期满");
                            }
                        }
                        else if ("3".equals(contract.getStatus()))
                        {
                            baseInfoModel.setStatus("已结项");
                        }
                        else
                        {
                            baseInfoModel.setStatus("草稿");
                        }
                        baseInfoModel.setStartTime(DateUtil.format(contract.getEffectiveStart(), "yyyy-MM-dd"));
                        baseInfoModel.setEndTime(DateUtil.format(contract.getEffectiveEnd(), "yyyy-MM-dd"));
                        if (null != contract.getMarketingCenter())
                        {
                            if (1 == contract.getMarketingCenter())
                            {
                                baseInfoModel.setMarketingCenter("临床遗传");
                            }
                            else if (2 == contract.getMarketingCenter())
                            {
                                baseInfoModel.setMarketingCenter("临床肿瘤");
                            }
                            else if (3 == contract.getMarketingCenter())
                            {
                                baseInfoModel.setMarketingCenter("健康筛查");
                            }
                            else
                            {
                                baseInfoModel.setMarketingCenter("科技服务");
                            }
                        }
                        BusinessInfo businessInfo = baseDaoSupport.get(BusinessInfo.class, contract.getSignUser());
                        if (null != businessInfo)
                        {
                            baseInfoModel.setSignUser(businessInfo.getRealName());
                        }
                        if (null != contract.getSignDate())
                        {
                            baseInfoModel.setSignDate(DateUtil.format(contract.getSignDate(), "yyyy-MM-dd"));
                        }
                        if ("0".equals(contract.getHospitalAdmited()))
                        {
                            baseInfoModel.setHospitalAdmited("否");
                        }
                        if ("1".equals(contract.getHospitalAdmited()))
                        {
                            baseInfoModel.setHospitalAdmited("是");
                        }
                        ContractContent cc = getContractContentByContractId(contract.getId());
                        if (null != cc)
                        {
                            baseInfoModel.setInvoiceMethod(getText("INVOICE_METHOD", cc.getInvoiceMethod()));
                            baseInfoModel.setRemark(cc.getSettlementRemark());
                        }
                        if (null != contract.getStartMode())
                        {
                            baseInfoModel.setStartMode(getText("START_MODE", contract.getStartMode().toString()));
                        }
                        //-----------------甲方信息-------------------
                        warpPartyAInfo(contract, baseInfoModel);
                        //-----------------乙方信息-------------------
                        warpPartyBInfo(contract, baseInfoModel);
                        //----------------成果交付--------------------
                        warpSuccessDelivery(contract, baseInfoModel);
                        //----------------结算方式--------------------
                        warpSettlementInfo(contract, baseInfoModel);
                        //----------------产品列表--------------------
                        warpProductInfo(contract, baseInfoModel);
                        //----------------实验信息--------------------
                        warpTestInfo(contract, baseInfoModel);
                        //----------------合同原件--------------------
                        warpContractOriginal(contract, baseInfoModel);
                        //----------------合同对象信息--------------------
                        warpContractUserInfo(contract, baseInfoModel);
                        //----------------变更业务员信息--------------------
                        warpChangeUserInfo(contract, baseInfoModel);
                        baseInfoList.add(baseInfoModel);
                    }
            }
        }
        return baseInfoList;
    }
    
    private void warpPartyAInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<PartyAInfo> partyAInfoList = Lists.newArrayList();
        ContractPartyA a = getContractPAByContractId(contract.getId());
        PartyAInfo partyAInfo = new PartyAInfo();
        if (null != a)
        {
            partyAInfo.setCompanyName(a.getCompanyName());
            partyAInfo.setContactName(a.getContactName());
            partyAInfo.setContactPhone(a.getContactPhone());
            partyAInfo.setContactEmail(a.getContactEmai());
            partyAInfo.setZipCode(a.getZipcode());
            partyAInfo.setAddress(a.getAddress());
            partyAInfo.setInvoiceTitle(a.getInvoiceTitle());
        }
        partyAInfoList.add(partyAInfo);
        baseInfoModel.setPartyAInfoList(partyAInfoList);
    }
    
    private void warpPartyBInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<PartyBInfo> partyBInfoList = Lists.newArrayList();
        ContractPartyB b = getContractPBByContractId(contract.getId());
        PartyBInfo partyBInfo = new PartyBInfo();
        if (null != b)
        {
            partyBInfo.setCompanyName(b.getCompanyName());
            partyBInfo.setContactName(b.getContactName());
            partyBInfo.setContactPhone(b.getContactPhone());
            partyBInfo.setDepositBank(b.getDepositBank());
            partyBInfo.setAccountNo(b.getBankAccountNo());
            partyBInfo.setAccountName(b.getBankAccountName());
        }
        partyBInfoList.add(partyBInfo);
        baseInfoModel.setPartyBInfoList(partyBInfoList);
    }
    
    private void warpSuccessDelivery(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<SuccessDelivery> successDeliveryList = Lists.newArrayList();
        ContractContent cc = getContractContentByContractId(contract.getId());
        SuccessDelivery successDelivery = new SuccessDelivery();
        if (null != cc)
        {
            String deliveryModes = "";
            if (StringUtils.isNotEmpty(cc.getDeliveryMode()))
            {
                String[] ss = cc.getDeliveryMode().split(",");
                for (int i = 0; i < ss.length; i++)
                {
                    if (0 == i)
                    {
                        deliveryModes = getText("DELIVER_FORM", ss[i]);
                    }
                    else
                    {
                        deliveryModes += "," + getText("DELIVER_FORM", ss[i]);
                    }
                }
            }
            successDelivery.setDeliveryMode(deliveryModes);
            successDelivery.setDeliveryResult(getText("DELIVER_TYPE", cc.getDeliveryResult()));
        }
        successDeliveryList.add(successDelivery);
        baseInfoModel.setSuccessDeliveryList(successDeliveryList);
    }
    
    private void warpSettlementInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<SettlementInfo> settlementInfoList = Lists.newArrayList();
        ContractContent cc = getContractContentByContractId(contract.getId());
        SettlementInfo settlementInfo = new SettlementInfo();
        if (null != cc)
        {
            settlementInfo.setSettlementMode(getText("SETTLEMENT_METHOD", cc.getSettlementMode()));
            settlementInfo.setSettlementRemark(cc.getSettlementRemark());
            settlementInfo.setAmount(contract.getAmount().divide(new BigDecimal(100), 2).toString());
        }
        settlementInfoList.add(settlementInfo);
        baseInfoModel.setSettlementInfoList(settlementInfoList);
    }
    
    private void warpProductInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<ProductInfo> productInfoList = Lists.newArrayList();
        List<ContractProduct> conProducts = contract.getConProducts();
        if (Collections3.isNotEmpty(conProducts))
        {
            for (ContractProduct product : conProducts)
            {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductName(product.getProductName());
                if (null != product.getContractPrice())
                {
                    productInfo.setPrice(new BigDecimal(product.getContractPrice()).divide(new BigDecimal(100)).toEngineeringString());
                }
                if (null != product.getSignCount())
                {
                    productInfo.setCount(String.valueOf(product.getSignCount()));
                }
                if (null != product.getSignAmount())
                {
                    productInfo.setAmount(product.getSignAmount().divide(new BigDecimal(100)).toString());
                }
                productInfo.setRequirement(product.getRequirement());
                productInfoList.add(productInfo);
            }
        }
        baseInfoModel.setProductInfoList(productInfoList);
    }
    
    //
    private void warpTestInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<TestInfo> testInfoList = Lists.newArrayList();
        List<ContractSample> samples = contract.getConSamples();
        if (Collections3.isNotEmpty(samples))
        {
            for (ContractSample cs : samples)
            {
                TestInfo testInfo = new TestInfo();
                testInfo.setSampleKind(getText("SAMPLE_SPECIES", cs.getSampleCategory()));
                String sampleTypes = "";
                if (StringUtils.isNotEmpty(cs.getSampleTypeKeys()))
                {
                    String[] ss = cs.getSampleTypeKeys().split(",");
                    for (int i = 0; i < ss.length; i++)
                    {
                        Sample sampleType = baseDaoSupport.get(Sample.class, ss[i]);
                        if (null != sampleType)
                        {
                            if (0 == i)
                            {
                                sampleTypes = sampleType.getName();
                            }
                            else
                            {
                                sampleTypes += "," + sampleType.getName();
                            }
                        }
                    }
                }
                testInfo.setSampleType(sampleTypes);
                testInfoList.add(testInfo);
            }
        }
        baseInfoModel.setTestInfoList(testInfoList);
    }
    
    private void warpContractOriginal(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<ContractOriginal> contractOriginalList = Lists.newArrayList();
        ContractOriginal contractOriginal = new ContractOriginal();
        contractOriginal.setOriginalName(contract.getOriginal());
        if (null != contract.getUpdateTime())
        {
            contractOriginal.setUpdateTime(DateUtil.format(contract.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        contractOriginalList.add(contractOriginal);
        baseInfoModel.setContractOriginalList(contractOriginalList);
    }
    
    private void warpContractUserInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<ContractUserInfo> contractUserInfoList = Lists.newArrayList();
        List<ContractUser> users = contract.getConUsers();
        if (Collections3.isNotEmpty(users))
        {
            for (ContractUser cu : users)
            {
                ContractUserInfo contractUserInfo = new ContractUserInfo();
                Customer customer = baseDaoSupport.get(Customer.class, cu.getUserId());
                if (null != customer)
                {
                    contractUserInfo.setName(customer.getRealName());
                    contractUserInfo.setPhone(customer.getPhoneNum());
                    if (null != customer.getSex())
                    {
                        if (0 == customer.getSex())
                        {
                            contractUserInfo.setSex("男");
                        }
                        if (1 == customer.getSex())
                        {
                            contractUserInfo.setSex("女");
                        }
                    }
                    contractUserInfo.setDept(getText("BASE_DEPT", customer.getDept()));//BASE_DEPT
                    if (null != customer.getCompany())
                    {
                        contractUserInfo.setInstitutionName(customer.getCompany().getName());
                    }
                    contractUserInfo.setPosition(getText("POSITION", customer.getPosition()));//POSITION
                }
                contractUserInfoList.add(contractUserInfo);
            }
        }
        baseInfoModel.setContractUserInfoList(contractUserInfoList);
    }
    
    private void warpChangeUserInfo(Contract contract, ContractBaseInfoModel baseInfoModel)
    {
        List<ChangeUserInfo> changeUserInfoList = Lists.newArrayList();
        List<ContractChangeSignUser> ccsuList = getChangeSignUserList(contract.getId());
        if (Collections3.isNotEmpty(ccsuList))
        {
            for (ContractChangeSignUser ccsu : ccsuList)
            {
                ChangeUserInfo changeUserInfo = new ChangeUserInfo();
                BusinessInfo businessInfo = baseDaoSupport.get(BusinessInfo.class, ccsu.getBeforeSignUser());
                changeUserInfo.setBeforeSignUser(null != businessInfo ? businessInfo.getRealName() : "");
                BusinessInfo businessInfo2 = baseDaoSupport.get(BusinessInfo.class, ccsu.getAfterSignUser());
                changeUserInfo.setAfterSignUser(null != businessInfo2 ? businessInfo2.getRealName() : "");
                UserDetailsModel user = userAdapter.getUser(ccsu.getOperateId());
                if (StringUtils.isNotEmpty(user))
                {
                    changeUserInfo.setOperateName(user.getArchive().getName());
                }
                changeUserInfo.setOperateTime(DateUtil.format(ccsu.getOperateTime(), "yyyy-MM-dd HH:mm:ss"));
                changeUserInfoList.add(changeUserInfo);
            }
        }
        baseInfoModel.setChangeUserInfoList(changeUserInfoList);
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
    
}
