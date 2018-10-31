package com.todaysoft.lims.system.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jacob.com.Dispatch;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.system.model.searcher.ContractSearcher;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractChangeSignUser;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.contract.ContractCreateRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractFinancialModel;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceInfo;
import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractOrg;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyA;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyB;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.contract.ContractProduct;
import com.todaysoft.lims.system.model.vo.contract.ContractSampleRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBill;
import com.todaysoft.lims.system.model.vo.contract.ContractSettleBillRequest;
import com.todaysoft.lims.system.model.vo.contract.ContractUser;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ContractBaseInfoModel;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ContractReportFormOneHeadModel;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.TemporaryRequest;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceInfo;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.service.IContractService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.system.util.JacobWordDocument;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ContractService extends RestService implements IContractService
{
    @Autowired
    private IMetadataSampleService sampleService;
    
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    private static Logger log = LoggerFactory.getLogger(ContractService.class);
    
    @Override
    public void create(ContractCreateRequest request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/create.do"), request, Integer.class);
    }
    
    @Override
    @SystemServiceLog(description = "合同管理待办理-修改", type = 3)
    public void modify(ContractCreateRequest request)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/modify.do"), request, Boolean.class);
    }
    
    @Override
    @SystemServiceLog(description = "合同管理待办理-删除", type = 3)
    public void delete(ContractSearcher searcher)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/delete"), searcher, Integer.class);
    }
    
    @Override
    public Pagination<Contract> paging(ContractSearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/contract/list.do");
        ResponseEntity<Pagination<Contract>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(searcher), new ParameterizedTypeReference<Pagination<Contract>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public Contract getContractById(String id)
    {
        
        return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContract/{id}"), Contract.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean validate(ContractSearcher connect)
    {
        
        return template.postForObject(GatewayService.getServiceUrl("/bcm/contract/validate.do"), connect, boolean.class);
    }
    
    @Override
    public List<Contract> getContractList(ContractSearcher searcher)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getContracts.do");
        ResponseEntity<List<Contract>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(searcher), new ParameterizedTypeReference<List<Contract>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<ContractOrg> getContractOrgs()
    {
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractOrgs.do");
        ResponseEntity<List<ContractOrg>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractOrg>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public ContractPartyA getContractPAByContractId(String contractId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContractPAByContractId/{contractId}"),
            ContractPartyA.class,
            Collections.singletonMap("contractId", contractId));
    }
    
    @Override
    public ContractPartyB getContractPBByContractId(String contractId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContractPBByContractId/{contractId}"),
            ContractPartyB.class,
            Collections.singletonMap("contractId", contractId));
    }
    
    @Override
    public ContractContent getContractContentByContractId(String contractId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContractContentByContractId/{contractId}"),
            ContractContent.class,
            Collections.singletonMap("contractId", contractId));
    }
    
    @Override
    @SystemServiceLog(description = "合同管理待办理-上传", type = 3)
    public void addCode(Contract contract)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/addCode"), contract, Boolean.class);
    }
    
    @Override
    @SystemServiceLog(description = "合同管理待办理-确认", type = 3)
    public Integer confirmContract(ContractUser cUser)
    {
        return template.postForObject(GatewayService.getServiceUrl("/bcm/contract/confirmContract"), cUser, Integer.class);
    }
    
    @Override
    public ContractOrg getContractOrgByName(ContractSearcher searcher)
    {
        /*return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContractOrgByName/{name}"),
            ContractOrg.class,
            Collections.singletonMap("name", name));*/
        
        return template.postForObject(GatewayService.getServiceUrl("/bcm/contract/getContractOrgByName"), searcher, ContractOrg.class);
    }
    
    @Override
    @SystemServiceLog(description = "合同管理待办理-上传", type = 3)
    public void update(Contract contract)
    {
        
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/update.do"), contract, Boolean.class);
    }
    
    @Override
    public List<Customer> getContractUsers(String contractId)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractUsers/{contractId}");
        ResponseEntity<List<Customer>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>()
        {
        }, Collections.singletonMap("contractId", contractId));
        return exchange.getBody();
    }
    
    @Override
    public void downloadData(HttpServletResponse response, String path)
    {
        
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = null;
        try
        {
            // path是指欲下载的文件的路径。
            file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
                if (file.exists())
                {
                    file.delete();
                }
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        
    }
    
    @Override
    public String buildContract(ContractCreateRequest request) throws ServiceException
    {
        File tempFile = new File("contract.doc");
        if (null != request)
        {
            InputStream is = ContractService.class.getResourceAsStream("/taskTemplate/conractTemplate.doc");
            inputstreamToFile(is, tempFile);
            JacobWordDocument jwd = null;
            try
            {
                jwd = new JacobWordDocument(tempFile.getAbsolutePath());
                if (StringUtils.isNotEmpty(request.getContractSampleJson()))
                {
                    request.setConSamples(JSON.parseArray(request.getContractSampleJson() + "", ContractSampleRequest.class));
                }
                if (StringUtils.isNotEmpty(request.getContractProductJson()))
                {
                    request.setConProducts(JSON.parseArray(request.getContractProductJson() + "", ContractProduct.class));
                }
                
                ContractPartyA contractpa = request.getContractpa();
                if (null != contractpa)
                {
                    jwd.insertMarkerText("contractpa_companyName", contractpa.getCompanyName());
                    jwd.insertMarkerText("contractpa_companyName_1", contractpa.getCompanyName());
                    jwd.insertMarkerText("contractpa_invoiceTitle", contractpa.getInvoiceTitle());
                    jwd.insertMarkerText("contractpa_address", contractpa.getAddress());
                    jwd.insertMarkerText("contractpa_zipcode", contractpa.getZipcode());
                    jwd.insertMarkerText("contractpa_contactPhone", contractpa.getContactPhone());
                    jwd.insertMarkerText("contractpa_contactEmai", contractpa.getContactEmai());
                    jwd.insertMarkerText("contractpa_contactName", contractpa.getContactName());
                }
                
                ContractPartyB contractpb = request.getContractpb();
                if (null != contractpb)
                {
                    jwd.insertMarkerText("contractpb_contactName", contractpb.getContactName());
                    jwd.insertMarkerText("contractpb_contactPhone", contractpb.getContactPhone());
                }
                Contract contract = request.getContract();
                if (null != contract)
                {
                    Calendar start = Calendar.getInstance();
                    start.setTime(contract.getEffectiveStart());
                    String startYear = start.get(Calendar.YEAR) + "";
                    String startMonth = start.get(Calendar.MONTH) + 1 + "";
                    
                    Calendar end = Calendar.getInstance();
                    end.setTime(contract.getEffectiveStart());
                    String endYear = end.get(Calendar.YEAR) + "";
                    String endMonth = end.get(Calendar.MONTH) + 1 + "";
                    jwd.insertMarkerText("contract_code", contract.getCode());
                    jwd.insertMarkerText("contract_name", contract.getName());
                    jwd.insertMarkerText("start_year", startYear);
                    jwd.insertMarkerText("start_month", startMonth);
                    jwd.insertMarkerText("end_year", endYear);
                    jwd.insertMarkerText("end_month", endMonth);
                    jwd.insertMarkerText("contract_remark", contract.getRemark());
                }
                
                ContractContent contractContent = request.getContractcontent();
                if (null != contractContent)
                {
                    String deliveryMode = "";
                    if (StringUtils.isNotEmpty(contractContent.getDeliveryMode()))
                    {
                        for (String str : contractContent.getDeliveryMode().split(","))
                        {
                            deliveryMode += dictService.getEntry("DELIVER_FORM", str).getText() + ",";
                        }
                    }
                    String deliveryResult = null;
                    if (StringUtils.isNotEmpty(contractContent.getDeliveryResult()))
                    {
                        deliveryResult = dictService.getEntry("DELIVER_TYPE", contractContent.getDeliveryResult()).getText();
                    }
                    jwd.insertMarkerText("contractcontent_deliveryMode", deliveryMode);
                    jwd.insertMarkerText("contractcontent_deliveryResult", deliveryResult);
                    if (StringUtils.isNotEmpty(contractContent.getSettlementMode()))
                    {
                        jwd.insertMarkerText("settlement_method", dictService.getEntry("SETTLEMENT_METHOD", contractContent.getDeliveryResult()).getText());
                    }
                    jwd.insertMarkerText("contractcontent_testingPeriod", contractContent.getTestingPeriod());
                    jwd.insertMarkerText("contractcontent_settlementRemark", contractContent.getSettlementRemark());
                }
                
                List<ContractProduct> cProducts = request.getConProducts();
                if (Collections3.isNotEmpty(cProducts))
                {
                    Dispatch table = jwd.getTable(1);
                    for (int i = 0; i < cProducts.size(); i++)
                    {
                        ContractProduct cProduct = cProducts.get(i);
                        
                        if (i != 0)
                        {
                            jwd.insertRow(table);
                        }
                        
                        int rowIndex = i + 2;
                        jwd.insertCellText(table, rowIndex, 1, cProduct.getProductName());
                        jwd.insertCellText(table, rowIndex, 2, cProduct.getRequirement());
                        jwd.insertCellText(table, rowIndex, 3, cProduct.getRealContractPrice());
                        jwd.insertCellText(table, rowIndex, 4, cProduct.getSignCount() + "");
                        jwd.insertCellText(table, rowIndex, 5, cProduct.getRealSignAmount());
                    }
                }
                
                List<ContractSampleRequest> cSamples = request.getConSamples();
                if (Collections3.isNotEmpty(cProducts))
                {
                    Dispatch table = jwd.getTable(2);
                    for (int i = 0; i < cSamples.size(); i++)
                    {
                        ContractSampleRequest cSample = cSamples.get(i);
                        
                        if (i != 0)
                        {
                            jwd.insertRow(table);
                        }
                        
                        int rowIndex = i + 2;
                        String sampleCategory = null;
                        if (StringUtils.isNotEmpty(cSample.getSampleCategory()))
                        {
                            sampleCategory = dictService.getEntry("SAMPLE_SPECIES", cSample.getSampleCategory()).getText();
                        }
                        String SampleTypeKeys = "";
                        if (Collections3.isNotEmpty(cSample.getSampleTypeKeys()))
                        {
                            for (String str : cSample.getSampleTypeKeys())
                            {
                                SampleTypeKeys += sampleService.get(str).getName() + ",";
                                
                            }
                        }
                        jwd.insertCellText(table, rowIndex, 1, sampleCategory);
                        jwd.insertCellText(table, rowIndex, 2, SampleTypeKeys);
                    }
                }
                jwd.saveAs(tempFile.getAbsolutePath());
            }
            catch (Exception e)
            {
                throw new ServiceException("生成合同报错啦！！");
            }
            finally
            {
                if (null != jwd)
                {
                    jwd.destory();
                }
            }
        }
        return tempFile.getAbsolutePath();
        
    }
    
    public static void inputstreamToFile(InputStream ins, File file)
    {
        try
        {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<ContractProduct> getContractProducts(String contractId)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractProducts/{contractId}");
        ResponseEntity<List<ContractProduct>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractProduct>>()
        {
        }, Collections.singletonMap("contractId", contractId));
        return exchange.getBody();
    }
    
    @Override
    public List<Contract> getContractByUserId(ContractSearcher request)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractByUserId");
        ResponseEntity<List<Contract>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(request), new ParameterizedTypeReference<List<Contract>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<Contract> unRegularlyList(ContractSearcher request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/contract/unRegularlyList");
        ResponseEntity<Pagination<Contract>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(request), new ParameterizedTypeReference<Pagination<Contract>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<ContractPaymentRecord> getRecordByContractId(ContractSearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getRecordByContractId");
        ResponseEntity<Pagination<ContractPaymentRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ContractSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ContractPaymentRecord>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public Pagination<ContractSettleBill> settlementList(ContractSearcher request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/contract/settlementList");
        ResponseEntity<Pagination<ContractSettleBill>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(request), new ParameterizedTypeReference<Pagination<ContractSettleBill>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<Contract> contractSettleManager(ContractSearcher request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/contract/contractSettleManager");
        ResponseEntity<Pagination<Contract>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(request), new ParameterizedTypeReference<Pagination<Contract>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Boolean createSettleBill(ContractSettleBillRequest request)
    {
        return template.postForObject(GatewayService.getServiceUrl("/bcm/contract/createSettleBill.do"), request, Boolean.class);
    }
    
    @Override
    public List<Order> getContractBillDetailById(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractBillDetailById/{id}");
        ResponseEntity<List<Order>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>()
        {
        }, Collections.singletonMap("id", id));
        return exchange.getBody();
    }
    
    @Override
    public String downloadSettleBill(InputStream is, List<Order> order, String createTime)
    {
        File tempFile = new File("SetttleBill_" + createTime + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("E2", createTime);
            excel.writeData(path, dataMap, wsheet);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (Order o : order)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, o.getCode());
                data.put(2, o.getOwner() != null ? o.getOwner().getRealName() : "");
                data.put(3, o.getCreatorName());
                data.put(4, DateUtil.format(o.getSubmitTime(), "yyyy-MM-dd HH:mm:ss"));
                data.put(5, o.getAmount() / 100);
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A4", "B4", "C4", "D4", "E4"}; // 必须为列表头部所有位置集合， 输出
            
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public Pagination<InvoiceApply> contractInvoiceList(ContractSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/contractInvoiceList");
        ResponseEntity<Pagination<InvoiceApply>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(searcher), new ParameterizedTypeReference<Pagination<InvoiceApply>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public InvoiceApply getInvoiceApplyInfo(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getInvoiceApplyInfo/{id}");
        return template.getForObject(url, InvoiceApply.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void handelInvoice(ContractInvoiceRequest data)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/handelInvoice");
        template.postForObject(url, data, String.class);
    }
    
    @Override
    public void addContracrProduct(Contract cotract)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/contract/addContracrProduct.do"), cotract, Integer.class);
        
    }
    
    @Override
    public void deleteContractProduct(String contractId, String productId)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/{contractId}/deleteContractProduct/{productId}");
        template.getForObject(url, Integer.class, contractId, productId);
        
    }
    
    @Override
    public List<InvoiceApply> getInvoiceApplyByContractId(String contractId)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/contract/getInvoiceApplyByContractId/{contractId}");
        ResponseEntity<List<InvoiceApply>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InvoiceApply>>()
        {
        }, Collections.singletonMap("contractId", contractId));
        return exchange.getBody();
        
    }
    
    @Override
    public List<ContractInvoiceInfo> getContractInvoiceInfo(String contractId)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractInvoiceInfo/{contractId}");
        ResponseEntity<List<ContractInvoiceInfo>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractInvoiceInfo>>()
            {
            }, Collections.singletonMap("contractId", contractId));
        return exchange.getBody();
    }
    
    @Override
    public List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(String invoiceId)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractInvoiceInfoByApplyId/{invoiceId}");
        ResponseEntity<List<ContractInvoiceInfo>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractInvoiceInfo>>()
            {
            }, Collections.singletonMap("invoiceId", invoiceId));
        return exchange.getBody();
    }
    
    @Override
    public int getContractInvoiceAmount(String contractId)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/contract/getContractInvoiceAmount/{contractId}"),
        
        Integer.class, contractId);
    }
    
    @Override
    public List<FinanceInvoiceTask> getTasksByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getTaskByOrderId/{orderId}");
        ResponseEntity<List<FinanceInvoiceTask>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FinanceInvoiceTask>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public List<FinanceInvoiceTask> getTasksByOrders(TemporaryRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getTaskByOrders");
        ResponseEntity<List<FinanceInvoiceTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TemporaryRequest>(request), new ParameterizedTypeReference<List<FinanceInvoiceTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<FinanceInvoiceTask> getFinanceInvoiceTaskByOrder(Contract contract, List<Order> orderResults)
    {
        List<FinanceInvoiceTask> financeinvoicetasks = Lists.newArrayList();
        if (null != contract)
        {
            ContractContent cc = contract.getContractContent();
            if (null != cc)
            {
                if ("0".equals(cc.getInvoiceMethod()))//一单一票
                {
                    List<FinanceInvoiceTask> singlefinanceinvoicetask = getFinanceInvoiceTasksByOrders(orderResults);
                    financeinvoicetasks.addAll(singlefinanceinvoicetask);
                }
                else
                //集中开票
                {
                    //提前开票
                    TemporaryRequest request = new TemporaryRequest(orderResults);
                    List<FinanceInvoiceTask> advancefinanceinvoicetask = getTasksByOrders(request);
                    if (Collections3.isNotEmpty(advancefinanceinvoicetask))
                        financeinvoicetasks.addAll(advancefinanceinvoicetask);
                }
            }
        }
        return financeinvoicetasks;
    }
    
    @Override
    public BigDecimal getCountAmount(List<ContractInvoiceInfo> infos, List<FinanceInvoiceTask> financeinvoicetasks)
    {
        BigDecimal amount = new BigDecimal(0);
        
        BigDecimal amount1 = new BigDecimal(0);
        if (Collections3.isNotEmpty(infos))
        {
            for (ContractInvoiceInfo info : infos)
            {
                amount = amount.add(new BigDecimal(info.getInvoiceAccount()));
            }
        }
        if (Collections3.isNotEmpty(financeinvoicetasks))
        {
            for (FinanceInvoiceTask task : financeinvoicetasks)
            {
                List<InvoiceInfo> invoiceinfos = task.getInfoList();
                if (Collections3.isNotEmpty(invoiceinfos))
                {
                    for (InvoiceInfo info : invoiceinfos)
                    {
                        amount1 = amount1.add(info.getInvoiceAmount());
                    }
                }
            }
        }
        return amount.add(amount1.multiply(new BigDecimal(100)));
    }
    
    private List<FinanceInvoiceTask> getFinanceInvoiceTasksByOrders(List<Order> orderResults)
    {
        List<FinanceInvoiceTask> tasks = Lists.newArrayList();
        if (Collections3.isNotEmpty(orderResults))
        {
            for (Order order : orderResults)
            {
                List<FinanceInvoiceTask> financeinvoicetasks = getTasksByOrderId(order.getId());
                if (Collections3.isNotEmpty(financeinvoicetasks))
                {
                    financeinvoicetasks.forEach(task -> {
                        if (Collections3.isNotEmpty(task.getInfoList()))
                        {
                            task.getInfoList().forEach(info -> {
                                if (StringUtils.isNotEmpty(info.getDrawerId()))
                                {
                                    InvoiceUserModel user = invoiceUserService.getUser(info.getDrawerId());
                                    if(null != user)
                                    {
                                        info.setDrawerName(user.getName());
                                    }
                                }
                            });
                        }
                    });
                }
                tasks.addAll(financeinvoicetasks);
            }
        }
        return tasks;
    }
    
    @Override
    public void saveSignUser(ContractSearcher searcher)
    {
        post("/bcm/contract/saveSignUser.do", searcher, String.class);
    }
    
    @Override
    public List<ContractChangeSignUser> getChangeSignUserList(String contractId)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getChangeSignUserList/{contractId}");
        ResponseEntity<List<ContractChangeSignUser>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractChangeSignUser>>()
            {
            }, Collections.singletonMap("contractId", contractId));
        return exchange.getBody();
    }
    
    @Override
    public String exportContractFinancialFile(ContractSearcher searcher)
    {
        InputStream in = null;
        OutputStream os = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File("CONTRACT_FINANCIAL_FILE" + time + ".xlsx");
        
        try
        {
            in = ContractService.class.getResourceAsStream("/taskTemplate/order/CONTRACT_FINANCIAL.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            List<ContractFinancialModel> list = getExportRecords(searcher);
            Map<Integer, Object> data = null;
            for (ContractFinancialModel model : list)
            {
                data = new HashMap<Integer, Object>();
                data.put(1, model.getCode());
                data.put(2, model.getName());
                data.put(3, model.getCreatorName());
                data.put(4, model.getMarketingCenter());
                data.put(5, model.getCompanyName());
                data.put(6, model.getStatus());
                data.put(7, new BigDecimal(model.getAmount()).divide(new BigDecimal(100)));
                data.put(8, new BigDecimal(model.getOrdersAmount()).divide(new BigDecimal(100)));
                data.put(9, new BigDecimal(model.getInvoiceAmount()).divide(new BigDecimal(100)));
                data.put(10, new BigDecimal(model.getIncomingAmount()).divide(new BigDecimal(100)));
                data.put(11,
                    (model.getOrdersAmount() - model.getInvoiceAmount()) >= 0 ? new BigDecimal(model.getOrdersAmount() - model.getInvoiceAmount()).divide(new BigDecimal(
                        100)) : "-");
                data.put(12,
                    (model.getOrdersAmount() - model.getIncomingAmount()) >= 0 ? new BigDecimal(model.getOrdersAmount() - model.getIncomingAmount()).divide(new BigDecimal(
                        100)) : "-");
                datalist.add(data);
                
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    log.error(e.getMessage(), e);
                }
            }
        }
        
        return file.getAbsolutePath();
    }
    
    private List<ContractFinancialModel> getExportRecords(ContractSearcher searcher)
    {
        int pageNo = 1;
        int pageSize = 1000;
        List<ContractFinancialModel> records = new ArrayList<ContractFinancialModel>();
        
        Pagination<ContractFinancialModel> pagination;
        
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            pagination = exportPaging(searcher);
            
            if (!CollectionUtils.isEmpty(pagination.getRecords()))
            {
                records.addAll(pagination.getRecords());
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                    pageNo,
                    pagination.getTotalCount(),
                    pagination.getTotalPage());
            }
            
        } while (!pagination.isLastPage());
        
        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        
        return records;
    }
    
    private Pagination<ContractFinancialModel> exportPaging(ContractSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/contractExportPaging");
        ResponseEntity<Pagination<ContractFinancialModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ContractSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ContractFinancialModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public List<ContractBaseInfoModel> contractReportFormListForWkcreate(ContractSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/contract/getContractListForContractBaseInfo");
        ResponseEntity<List<ContractBaseInfoModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractSearcher>(searcher), new ParameterizedTypeReference<List<ContractBaseInfoModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String writerContract(String fileName, String fileType, List<ContractBaseInfoModel> contracts, Map<String, List<String>> mapTitle) throws Exception
    {
        Workbook wb = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File(fileName + "_" + time + "." + fileType);
        Sheet sheet = null;
        // 创建工作文档对象
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                
                wb = new XSSFWorkbook();
            }
            // 创建sheet对象
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();
                
            }
        }
        // 创建sheet对象
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }
        
        Row row = null;
        Cell cell = null;
        // --------一级列头设置----------
        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)260);
        style.setFont(font);
        // ----------二级列头设置---------
        CellStyle style2 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style2.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeight((short)240);
        // font2.setColor(HSSFColor.RED.index);
        style2.setFont(font2);
        // -----------内容设置----------------
        CellStyle style3 = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style3.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style3.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style3.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        Font font3 = wb.createFont();
        font3.setFontName("宋体");
        // font3.setFontHeight((short)240);
        style3.setFont(font3);
        
        List<ContractReportFormOneHeadModel> oneHeadList = Lists.newArrayList();// 一级列头(不重复)
        List<String> oneHeadForTwoList = Lists.newArrayList();// 一级列头(根据二级标题重复生成一级标题)
        List<String> baseInfoList = Lists.newArrayList();
        List<String> partyAInfoList = Lists.newArrayList();
        List<String> partyBInfoList = Lists.newArrayList();
        List<String> successDeliveryList = Lists.newArrayList();
        List<String> settlementInfoList = Lists.newArrayList();
        List<String> productInfoList = Lists.newArrayList();
        List<String> testInfoList = Lists.newArrayList();
        List<String> contractOriginalList = Lists.newArrayList();
        List<String> contractUserInfoList = Lists.newArrayList();
        List<String> changeUserInfoList = Lists.newArrayList();
        List<String> twoHeadList = Lists.newArrayList();// 二级列头
        for (Map.Entry<String, List<String>> entry : mapTitle.entrySet())
        {
            ContractReportFormOneHeadModel oneHeadModel = new ContractReportFormOneHeadModel();
            if ("baseInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同信息");
                oneHeadModel.setIndex(1);
                baseInfoList = entry.getValue();
            }
            else if ("partyAInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("甲方信息");
                oneHeadModel.setIndex(2);
                partyAInfoList = entry.getValue();
            }
            else if ("partyBInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("乙方信息");
                oneHeadModel.setIndex(3);
                partyBInfoList = entry.getValue();
            }
            else if ("successDelivery".equals(entry.getKey()))
            {
                oneHeadModel.setName("成果交付");
                oneHeadModel.setIndex(4);
                successDeliveryList = entry.getValue();
            }
            else if ("settlementInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("结算方式");
                oneHeadModel.setIndex(5);
                settlementInfoList = entry.getValue();
            }
            else if ("productInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("产品列表");
                oneHeadModel.setIndex(6);
                productInfoList = entry.getValue();
            }
            else if ("testInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("实验信息");
                oneHeadModel.setIndex(7);
                testInfoList = entry.getValue();
            }
            else if ("contractOriginal".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同原件");
                oneHeadModel.setIndex(8);
                contractOriginalList = entry.getValue();
            }
            else if ("contractUserInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("合同对象信息");
                oneHeadModel.setIndex(9);
                contractUserInfoList = entry.getValue();
            }
            else if ("changeUserInfo".equals(entry.getKey()))
            {
                oneHeadModel.setName("变更业务员信息");
                oneHeadModel.setIndex(10);
                changeUserInfoList = entry.getValue();
            }
            oneHeadList.add(oneHeadModel);
        }
        // 排序
        oneHeadList.sort(Comparator.comparing(ContractReportFormOneHeadModel::getIndex));
        String[] headnum0 = new String[oneHeadList.size()];// 对应excel中的行和列，下标从0开始{"开始行,结束行,开始列,结束列"}
        int startIndex = 0;
        int endIndex = 0;
        int i = 0;
        if (Collections3.isNotEmpty(baseInfoList))
        {
            endIndex += baseInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += baseInfoList.size();
            for (int j = 0; j < baseInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(baseInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(partyAInfoList))
        {
            endIndex += partyAInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += partyAInfoList.size();
            for (int j = 0; j < partyAInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(partyAInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(partyBInfoList))
        {
            endIndex += partyBInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += partyBInfoList.size();
            for (int j = 0; j < partyBInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(partyBInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(successDeliveryList))
        {
            endIndex += successDeliveryList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += successDeliveryList.size();
            for (int j = 0; j < successDeliveryList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(successDeliveryList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(settlementInfoList))
        {
            endIndex += settlementInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += settlementInfoList.size();
            for (int j = 0; j < settlementInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(settlementInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(productInfoList))
        {
            endIndex += productInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += productInfoList.size();
            for (int j = 0; j < productInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(productInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(testInfoList))
        {
            endIndex += testInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += testInfoList.size();
            for (int j = 0; j < testInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(testInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(contractOriginalList))
        {
            endIndex += contractOriginalList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += contractOriginalList.size();
            for (int j = 0; j < contractOriginalList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(contractOriginalList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(contractUserInfoList))
        {
            endIndex += contractUserInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += contractUserInfoList.size();
            for (int j = 0; j < contractUserInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(contractUserInfoList.get(j));
            }
            i++;
        }
        if (Collections3.isNotEmpty(changeUserInfoList))
        {
            endIndex += changeUserInfoList.size();
            headnum0[i] = "0,0," + startIndex + "," + endIndex;
            startIndex += changeUserInfoList.size();
            for (int j = 0; j < changeUserInfoList.size(); j++)
            {
                oneHeadForTwoList.add(oneHeadList.get(i).getName());
                twoHeadList.add(changeUserInfoList.get(j));
            }
            i++;
        }
        for (int j = 0; j < headnum0.length; j++)
        {
            String str = headnum0[j];
            String[] indexArr = str.split(",");
            headnum0[j] = indexArr[0] + "," + indexArr[1] + "," + indexArr[2] + "," + String.valueOf(Integer.parseInt(indexArr[3]) - 1);
        }
        
        row = sheet.createRow(0); // 创建第1行
        for (int j = 0; j < endIndex; j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(oneHeadForTwoList.get(j));
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(j, 20 * 256);
        }
        row.setHeight((short)450);
        // 动态合并单元格
        for (int j = 0; j < headnum0.length; j++)
        {
            String[] temp = headnum0[j].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        row = sheet.createRow(1); // 创建第2行
        for (int j = 0; j < twoHeadList.size(); j++)
        {
            cell = row.createCell(j);
            cell.setCellValue(twoHeadList.get(j));
            cell.setCellStyle(style2); // 样式，居中
        }
        List<String[]> datalist = Lists.newArrayList();
        for (ContractBaseInfoModel model : contracts)
        {
            datalist.addAll(wrapObjectToListString(model,
                endIndex,
                baseInfoList,
                partyAInfoList,
                partyBInfoList,
                successDeliveryList,
                settlementInfoList,
                productInfoList,
                testInfoList,
                contractOriginalList,
                contractUserInfoList,
                changeUserInfoList));
        }
        ExcelUtil excel = new ExcelUtil();
        String path2 = file.getPath();
        excel.exportObjects2ModuleExcel(datalist, 2, wb, "sheet1", true, path2);
        wb.write(new FileOutputStream(path2));
        return file.getAbsolutePath();
    }
    
    private List<String[]> wrapObjectToListString(ContractBaseInfoModel model, int endIndex, List<String> baseInfoList, List<String> partyAInfoList, List<String> partyBInfoList, List<String> successDeliveryList, List<String> settlementInfoList, List<String> productInfoList, List<String> testInfoList, List<String> contractOriginalList, List<String> contractUserInfoList, List<String> changeUserInfoList)
    {
        List<String[]> results = Lists.newArrayList();
        if (null != model)
        {
            int maxSize =
                maxSize(model,
                    partyAInfoList,
                    partyBInfoList,
                    successDeliveryList,
                    settlementInfoList,
                    productInfoList,
                    testInfoList,
                    contractOriginalList,
                    contractUserInfoList,
                    changeUserInfoList);
            for (int i = 0; i < maxSize; i++)
            {
                int z = 0;
                String[] modelArr = new String[endIndex];
                if (i < 1)
                {
                    for (int k = 0; k < baseInfoList.size(); k++)
                    {
                        if ("合同编号".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getCode()) ? model.getCode() : "";
                        }
                        if ("合同名称".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getName()) ? model.getName() : "";
                        }
                        if ("合同状态".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStatus()) ? model.getStatus() : "";
                        }
                        if ("合同有效期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStartTime()) ? model.getStartTime() : "";
                        }
                        if ("合同截止日期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getEndTime()) ? model.getEndTime() : "";
                        }
                        if ("营销中心".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getMarketingCenter()) ? model.getMarketingCenter() : "";
                        }
                        if ("业务员".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getSignUser()) ? model.getSignUser() : "";
                        }
                        if ("签订日期".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getSignDate()) ? model.getSignDate() : "";
                        }
                        if ("是否入院".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getHospitalAdmited()) ? model.getHospitalAdmited() : "";
                        }
                        if ("开票形式".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getInvoiceMethod()) ? model.getInvoiceMethod() : "";
                        }
                        if ("启动方式".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getStartMode()) ? model.getStartMode() : "";
                        }
                        if ("备注说明".equals(baseInfoList.get(k)))
                        {
                            modelArr[k] = StringUtils.isNotEmpty(model.getRemark()) ? model.getRemark() : "";
                        }
                    }
                }
                z = baseInfoList.size();
                if (Collections3.isNotEmpty(partyAInfoList))
                {
                    if (Collections3.isNotEmpty(model.getPartyAInfoList()))
                    {
                        if (i < model.getPartyAInfoList().size())
                        {
                            int z_pa = z;
                            for (int k = 0; k < partyAInfoList.size(); k++)
                            {
                                if ("甲方".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getCompanyName()) ? model.getPartyAInfoList()
                                            .get(i)
                                            .getCompanyName() : "";
                                }
                                if ("联系人".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getContactName()) ? model.getPartyAInfoList()
                                            .get(i)
                                            .getContactName() : "";
                                }
                                if ("电话".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getContactPhone()) ? model.getPartyAInfoList()
                                            .get(i)
                                            .getContactPhone() : "";
                                }
                                if ("邮箱".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getContactEmail()) ? model.getPartyAInfoList()
                                            .get(i)
                                            .getContactEmail() : "";
                                }
                                if ("邮编".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getZipCode()) ? model.getPartyAInfoList().get(i).getZipCode()
                                            : "";
                                }
                                if ("发票抬头".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getInvoiceTitle()) ? model.getPartyAInfoList()
                                            .get(i)
                                            .getInvoiceTitle() : "";
                                }
                                if ("地址".equals(partyAInfoList.get(k)))
                                {
                                    modelArr[z_pa + k] =
                                        StringUtils.isNotEmpty(model.getPartyAInfoList().get(i).getAddress()) ? model.getPartyAInfoList().get(i).getAddress()
                                            : "";
                                }
                            }
                        }
                    }
                    z += partyAInfoList.size();
                }
                if (Collections3.isNotEmpty(partyBInfoList))
                {
                    if (Collections3.isNotEmpty(model.getPartyBInfoList()))
                    {
                        if (i < model.getPartyBInfoList().size())
                        {
                            int z_pb = z;
                            for (int k = 0; k < partyBInfoList.size(); k++)
                            {
                                if ("乙方".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getCompanyName()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getCompanyName() : "";
                                }
                                if ("联系人".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getContactName()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getContactName() : "";
                                }
                                if ("电话".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getContactPhone()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getContactPhone() : "";
                                }
                                if ("开户银行".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getDepositBank()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getDepositBank() : "";
                                }
                                if ("账号".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getAccountNo()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getAccountNo() : "";
                                }
                                if ("开户名称".equals(partyBInfoList.get(k)))
                                {
                                    modelArr[z_pb + k] =
                                        StringUtils.isNotEmpty(model.getPartyBInfoList().get(i).getAccountName()) ? model.getPartyBInfoList()
                                            .get(i)
                                            .getAccountName() : "";
                                }
                            }
                        }
                    }
                    z += partyBInfoList.size();
                }
                if (Collections3.isNotEmpty(successDeliveryList))
                {
                    if (Collections3.isNotEmpty(model.getSuccessDeliveryList()))
                    {
                        if (i < model.getSuccessDeliveryList().size())
                        {
                            int z_s = z;
                            for (int k = 0; k < successDeliveryList.size(); k++)
                            {
                                if ("交付形式".equals(successDeliveryList.get(k)))
                                {
                                    modelArr[z_s + k] =
                                        StringUtils.isNotEmpty(model.getSuccessDeliveryList().get(i).getDeliveryMode()) ? model.getSuccessDeliveryList()
                                            .get(i)
                                            .getDeliveryMode() : "";
                                }
                                if ("交付方式".equals(successDeliveryList.get(k)))
                                {
                                    modelArr[z_s + k] =
                                        StringUtils.isNotEmpty(model.getSuccessDeliveryList().get(i).getDeliveryResult()) ? model.getSuccessDeliveryList()
                                            .get(i)
                                            .getDeliveryResult() : "";
                                }
                            }
                        }
                    }
                    z += successDeliveryList.size();
                }
                if (Collections3.isNotEmpty(settlementInfoList))
                {
                    if (Collections3.isNotEmpty(model.getSettlementInfoList()))
                    {
                        if (i < model.getSettlementInfoList().size())
                        {
                            int z_se = z;
                            for (int k = 0; k < settlementInfoList.size(); k++)
                            {
                                if ("结算方式".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getSettlementInfoList().get(i).getSettlementMode()) ? model.getSettlementInfoList()
                                            .get(i)
                                            .getSettlementMode() : "";
                                }
                                if ("付款明细".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getSettlementInfoList().get(i).getSettlementRemark()) ? model.getSettlementInfoList()
                                            .get(i)
                                            .getSettlementRemark() : "";
                                }
                                if ("合同总额".equals(settlementInfoList.get(k)))
                                {
                                    modelArr[z_se + k] =
                                        StringUtils.isNotEmpty(model.getSettlementInfoList().get(i).getAmount()) ? model.getSettlementInfoList()
                                            .get(i)
                                            .getAmount() : "";
                                }
                            }
                        }
                    }
                    z += settlementInfoList.size();
                }
                if (Collections3.isNotEmpty(productInfoList))
                {
                    if (Collections3.isNotEmpty(model.getProductInfoList()))
                    {
                        if (i < model.getProductInfoList().size())
                        {
                            int z_pr = z;
                            for (int k = 0; k < productInfoList.size(); k++)
                            {
                                if ("产品名称".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getProductName()) ? model.getProductInfoList()
                                            .get(i)
                                            .getProductName() : "";
                                }
                                if ("单价".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getPrice()) ? model.getProductInfoList().get(i).getPrice()
                                            : "";
                                }
                                if ("数量".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getCount()) ? model.getProductInfoList().get(i).getCount()
                                            : "";
                                }
                                if ("价格".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getAmount()) ? model.getProductInfoList().get(i).getAmount()
                                            : "";
                                }
                                if ("服务要求".equals(productInfoList.get(k)))
                                {
                                    modelArr[z_pr + k] =
                                        StringUtils.isNotEmpty(model.getProductInfoList().get(i).getRequirement()) ? model.getProductInfoList()
                                            .get(i)
                                            .getRequirement() : "";
                                }
                            }
                        }
                    }
                    z += productInfoList.size();
                }
                if (Collections3.isNotEmpty(testInfoList))
                {
                    if (Collections3.isNotEmpty(model.getTestInfoList()))
                    {
                        if (i < model.getTestInfoList().size())
                        {
                            int z_t = z;
                            for (int k = 0; k < testInfoList.size(); k++)
                            {
                                if ("样本种属".equals(testInfoList.get(k)))
                                {
                                    modelArr[z_t + k] =
                                        StringUtils.isNotEmpty(model.getTestInfoList().get(i).getSampleKind()) ? model.getTestInfoList().get(i).getSampleKind()
                                            : "";
                                }
                                if ("样本类型".equals(testInfoList.get(k)))
                                {
                                    modelArr[z_t + k] =
                                        StringUtils.isNotEmpty(model.getTestInfoList().get(i).getSampleType()) ? model.getTestInfoList().get(i).getSampleType()
                                            : "";
                                }
                            }
                        }
                    }
                    z += testInfoList.size();
                }
                if (Collections3.isNotEmpty(contractOriginalList))
                {
                    if (Collections3.isNotEmpty(model.getContractOriginalList()))
                    {
                        if (i < model.getContractOriginalList().size())
                        {
                            int z_co = z;
                            for (int k = 0; k < contractOriginalList.size(); k++)
                            {
                                if ("合同文件名".equals(contractOriginalList.get(k)))
                                {
                                    modelArr[z_co + k] =
                                        StringUtils.isNotEmpty(model.getContractOriginalList().get(i).getOriginalName()) ? model.getContractOriginalList()
                                            .get(i)
                                            .getOriginalName() : "";
                                }
                                if ("更新时间".equals(contractOriginalList.get(k)))
                                {
                                    modelArr[z_co + k] =
                                        StringUtils.isNotEmpty(model.getContractOriginalList().get(i).getUpdateTime()) ? model.getContractOriginalList()
                                            .get(i)
                                            .getUpdateTime() : "";
                                }
                            }
                        }
                    }
                    z += contractOriginalList.size();
                }
                if (Collections3.isNotEmpty(contractUserInfoList))
                {
                    if (Collections3.isNotEmpty(model.getContractUserInfoList()))
                    {
                        if (i < model.getContractUserInfoList().size())
                        {
                            int z_cu = z;
                            for (int k = 0; k < contractUserInfoList.size(); k++)
                            {
                                if ("姓名".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getName()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getName() : "";
                                }
                                if ("手机号".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getPhone()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getPhone() : "";
                                }
                                if ("性别".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getSex()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getSex() : "";
                                }
                                if ("科室".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getDept()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getDept() : "";
                                }
                                if ("单位".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getInstitutionName()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getInstitutionName() : "";
                                }
                                if ("职称".equals(contractUserInfoList.get(k)))
                                {
                                    modelArr[z_cu + k] =
                                        StringUtils.isNotEmpty(model.getContractUserInfoList().get(i).getPosition()) ? model.getContractUserInfoList()
                                            .get(i)
                                            .getPosition() : "";
                                }
                            }
                        }
                    }
                    z += contractUserInfoList.size();
                }
                if (Collections3.isNotEmpty(changeUserInfoList))
                {
                    if (Collections3.isNotEmpty(model.getChangeUserInfoList()))
                    {
                        if (i < model.getChangeUserInfoList().size())
                        {
                            int z_c = z;
                            for (int k = 0; k < changeUserInfoList.size(); k++)
                            {
                                if ("变更前业务员".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeUserInfoList().get(i).getBeforeSignUser()) ? model.getChangeUserInfoList()
                                            .get(i)
                                            .getBeforeSignUser() : "";
                                }
                                if ("变更后业务员".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeUserInfoList().get(i).getAfterSignUser()) ? model.getChangeUserInfoList()
                                            .get(i)
                                            .getAfterSignUser() : "";
                                }
                                if ("操作人".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeUserInfoList().get(i).getOperateName()) ? model.getChangeUserInfoList()
                                            .get(i)
                                            .getOperateName() : "";
                                }
                                if ("操作时间".equals(changeUserInfoList.get(k)))
                                {
                                    modelArr[z_c + k] =
                                        StringUtils.isNotEmpty(model.getChangeUserInfoList().get(i).getOperateTime()) ? model.getChangeUserInfoList()
                                            .get(i)
                                            .getOperateTime() : "";
                                }
                            }
                        }
                    }
                    z += changeUserInfoList.size();
                }
                results.add(modelArr);
            }
        }
        return results;
        
    }
    
    private int maxSize(ContractBaseInfoModel model, List<String> partyAInfoList, List<String> partyBInfoList, List<String> successDeliveryList, List<String> settlementInfoList, List<String> productInfoList, List<String> testInfoList, List<String> contractOriginalList, List<String> contractUserInfoList, List<String> changeUserInfoList)
    {
        int a = 1;
        if (Collections3.isNotEmpty(partyAInfoList))
        {
            if (Collections3.isNotEmpty(model.getPartyAInfoList()))
            {
                a = Math.max(a, model.getPartyAInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(partyBInfoList))
        {
            if (Collections3.isNotEmpty(model.getPartyBInfoList()))
            {
                a = Math.max(a, model.getPartyBInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(successDeliveryList))
        {
            if (Collections3.isNotEmpty(model.getSuccessDeliveryList()))
            {
                a = Math.max(a, model.getSuccessDeliveryList().size());
            }
        }
        if (Collections3.isNotEmpty(settlementInfoList))
        {
            if (Collections3.isNotEmpty(model.getSettlementInfoList()))
            {
                a = Math.max(a, model.getSettlementInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(productInfoList))
        {
            if (Collections3.isNotEmpty(model.getProductInfoList()))
            {
                a = Math.max(a, model.getProductInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(testInfoList))
        {
            if (Collections3.isNotEmpty(model.getTestInfoList()))
            {
                a = Math.max(a, model.getTestInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(contractOriginalList))
        {
            if (Collections3.isNotEmpty(model.getContractOriginalList()))
            {
                a = Math.max(a, model.getContractOriginalList().size());
            }
        }
        if (Collections3.isNotEmpty(contractUserInfoList))
        {
            if (Collections3.isNotEmpty(model.getContractUserInfoList()))
            {
                a = Math.max(a, model.getContractUserInfoList().size());
            }
        }
        if (Collections3.isNotEmpty(changeUserInfoList))
        {
            if (Collections3.isNotEmpty(model.getChangeUserInfoList()))
            {
                a = Math.max(a, model.getChangeUserInfoList().size());
            }
        }
        return a;
    }
    
}
