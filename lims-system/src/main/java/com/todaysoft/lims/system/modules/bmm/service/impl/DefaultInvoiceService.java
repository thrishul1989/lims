package com.todaysoft.lims.system.modules.bmm.service.impl;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceImportModel;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceImportModelResolver;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.request.*;
import com.todaysoft.lims.system.modules.bmm.model.response.OrderInvoiceImportDetails;
import com.todaysoft.lims.system.modules.bmm.model.response.VerifiedOrderInvoiceImportRecord;
import com.todaysoft.lims.system.modules.bmm.model.response.VerifyOrderInvoiceImportRecordsResponse;
import com.todaysoft.lims.system.modules.bmm.service.IDefaultInvoiceService;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class DefaultInvoiceService extends RestService implements IDefaultInvoiceService
{
    private static Logger log = LoggerFactory.getLogger(DefaultInvoiceService.class);
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    @Override
    public Pagination<DefaultInvoiceModel> paging(DefaultInvoiceSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/paging");
        ResponseEntity<Pagination<DefaultInvoiceModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DefaultInvoiceSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<DefaultInvoiceModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public DefaultInvoiceModel get(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/{id}");
        return template.getForObject(url, DefaultInvoiceModel.class, Collections.singletonMap("id", id));
    }

    @Override
    public void delayAdvanceInvoice(String id)
    {
        DefaultInvoiceModel request = new DefaultInvoiceModel();
        request.setId(id);
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/delayAdvanceInvoice");
        template.postForLocation(url, request);
    }

    @Override
    public void solve(DefaultInvoiceModel request)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/solve");
        template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public List<DefaultInvoiceModel> list(DefaultInvoiceSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/list");
        ResponseEntity<List<DefaultInvoiceModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DefaultInvoiceSearcher>(searcher),
                new ParameterizedTypeReference<List<DefaultInvoiceModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public String export(DefaultInvoiceSearcher searcher)
    {
        InputStream in = null;
        OutputStream os = null;
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File("default_" + time + ".xlsx");
        
        try
        {
            in = DefaultInvoiceService.class.getResourceAsStream("/taskTemplate/invoiceApply/default_invoice.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            List<DefaultInvoiceModel> list = getExportRecords(searcher);
            String invoiceTypeContent = "";
            for (DefaultInvoiceModel model : list)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getCode());
                data.put(2, null == model.getContract() ? "" : model.getContract().getCode());
                data.put(3, model.getOrderType());
                data.put(4, model.getExaminee());
                data.put(5, model.getOwnerName());
                data.put(6, model.getOwnerCompany());
                data.put(7, model.getCreatorId());
                String institution = "";
                if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(model.getInstitution()))
                {
                    institution ="北京迈基诺";
                }
                else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(model.getInstitution()))
                {
                    institution ="北京检验所";
                }
                else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(model.getInstitution()))
                {
                    institution ="重庆迈基诺";
                }
                else
                {
                    institution ="重庆检验所";
                }
                data.put(8,institution);
                BigDecimal amount = model.getCurrentActualPay().setScale(2, RoundingMode.HALF_UP);
                data.put(9, amount);
                data.put(10, testSheetService.getFormatTime("yyyy/MM/dd", model.getUpdateTime()));
                data.put(11, model.getInvoiceTitle());
                if (model.getBillingType() == 0 || model.getBillingType() == 1)
                {
                    invoiceTypeContent = "电票";
                }
                else
                {
                    invoiceTypeContent = "普票";
                }
                data.put(12, invoiceTypeContent);
                data.put(13, "");
                data.put(14, "");
                data.put(15, "");
                data.put(16, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (FileNotFoundException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IOException e)
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
    
    public List<DefaultInvoiceModel> getExportRecords(DefaultInvoiceSearcher searcher)
    {
        int pageNo = 1;
        int pageSize = 20;
        List<DefaultInvoiceModel> records = new ArrayList<DefaultInvoiceModel>();
        
        Pagination<DefaultInvoiceModel> pagination;
        
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            pagination = paging(searcher);
            
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
    
    @Override
    public List<VerifiedOrderInvoiceImportRecord> parse(MultipartFile file)
    {
        DefaultInvoiceImportModelResolver resolver = new DefaultInvoiceImportModelResolver(file);
        List<DefaultInvoiceImportModel> records = resolver.resolve();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        OrderInvoiceImportRecord importRecord;
        List<OrderInvoiceImportRecord> importRecords = new ArrayList<OrderInvoiceImportRecord>();
        
        for (DefaultInvoiceImportModel record : records)
        {
            importRecord = new OrderInvoiceImportRecord();
            BeanUtils.copyProperties(record, importRecord);
            importRecords.add(importRecord);
        }
        
        // 上传数据验证
        VerifyOrderInvoiceImportRecordsRequest request = new VerifyOrderInvoiceImportRecordsRequest();
        request.setRecords(importRecords);
        
        String url = GatewayService.getServiceUrl("/invoice/import/orders/verify");
        ResponseEntity<VerifyOrderInvoiceImportRecordsResponse> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<VerifyOrderInvoiceImportRecordsRequest>(request),
                new ParameterizedTypeReference<VerifyOrderInvoiceImportRecordsResponse>()
                {
                });
        
        VerifyOrderInvoiceImportRecordsResponse response = exchange.getBody();
        return response.getRecords();
    }
    
    @Override
    public void importRecords(List<VerifiedOrderInvoiceImportRecord> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return;
        }
        
        List<OrderInvoiceImportDetails> importRecords = new ArrayList<OrderInvoiceImportDetails>();
        
        for (VerifiedOrderInvoiceImportRecord record : records)
        {
            if (record.isValid())
            {
                importRecords.add(record.getDetails());
            }
        }
        
        ImportOrderInvoiceRecordsRequest request = new ImportOrderInvoiceRecordsRequest();
        request.setRecords(importRecords);
        String url = GatewayService.getServiceUrl("/invoice/import/orders");
        template.postForLocation(url, request);
    }
    
    @Override
    public DefaultInvoiceModel getByOrderId(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/getByOrderId/{orderId}");
        return template.getForObject(url, DefaultInvoiceModel.class, Collections.singletonMap("orderId", id));
    }
    
    @Override
    public DefaultInvoiceModel getInfoByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/getInfoByOrderId/{orderId}");
        return template.getForObject(url, DefaultInvoiceModel.class, Collections.singletonMap("orderId", orderId));
    }
    
    @Override
    public FinanceInvoiceTask getFinanceInvoiceByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/defaultInvoice/getFinanceInvoiceByOrderId/{orderId}");
        return template.getForObject(url, FinanceInvoiceTask.class, Collections.singletonMap("orderId", orderId));
    }
    
    @Override
    public void updateOrderById(DefaultInvoiceRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/updateOrderById");
        template.postForObject(url, request, boolean.class);
    }
}
