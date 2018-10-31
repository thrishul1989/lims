package com.todaysoft.lims.system.modules.invoice.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApplyModel;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendModel;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendSearcher;
import com.todaysoft.lims.system.modules.invoice.service.IInvoiceSendService;
import com.todaysoft.lims.system.modules.invoice.service.request.InvoiceSendRequest;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class InvoiceSendService extends RestService implements IInvoiceSendService
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Override
    public Pagination<InvoiceSendModel> paging(InvoiceSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/invoiceSend/paging");
        ResponseEntity<Pagination<InvoiceSendModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceSendSearcher>(searcher), new ParameterizedTypeReference<Pagination<InvoiceSendModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<InvoiceSendModel> listByIds(InvoiceSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/invoiceSend/listByIds");
        ResponseEntity<List<InvoiceSendModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceSendSearcher>(searcher), new ParameterizedTypeReference<List<InvoiceSendModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void create(InvoiceSendRequest invoiceSendRequest)
    {
        post("/invoiceSend/create", invoiceSendRequest, String.class);
        
    }
    
    @Override
    public Pagination<InvoiceSendModel> dealPaging(InvoiceSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/invoiceSend/dealPaging");
        ResponseEntity<Pagination<InvoiceSendModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceSendSearcher>(searcher), new ParameterizedTypeReference<Pagination<InvoiceSendModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<InvoiceSendModel> waitingView(InvoiceSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/invoiceSend/waitingView");
        ResponseEntity<List<InvoiceSendModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceSendSearcher>(searcher), new ParameterizedTypeReference<List<InvoiceSendModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<InvoiceSendModel> doneView(InvoiceSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/invoiceSend/doneView");
        ResponseEntity<List<InvoiceSendModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceSendSearcher>(searcher), new ParameterizedTypeReference<List<InvoiceSendModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String download(InputStream is, InvoiceSendSearcher searcher)
    {
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File("invoice_send_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(is, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            
            Map<Integer, Object> data = new HashMap<Integer, Object>();
            data.put(1, searcher.getRecipientName());
            data.put(2, searcher.getRecipientPhone());
            data.put(3, searcher.getRecipientAddress());
            datalist.add(data);
            
            String[] heads = new String[] {"A1", "B1", "C1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
        return file.getAbsolutePath();
    }

    @Override
    public void startInvoiceScheduleTask()
    {
        InvoiceSendRequest invoiceSendRequest = new InvoiceSendRequest();
        post("/invoice/startInvoiceScheduleTask", invoiceSendRequest, String.class);
    }
    
}
