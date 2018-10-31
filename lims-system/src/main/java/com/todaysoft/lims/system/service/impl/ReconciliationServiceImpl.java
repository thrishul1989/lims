package com.todaysoft.lims.system.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentConfirm;
import com.todaysoft.lims.system.model.vo.reconciliation.AccountCheckMistake;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountCheckTask;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountStateRecord;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.system.service.IReconciliationService;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class ReconciliationServiceImpl extends RestService implements IReconciliationService
{
    
    @Override
    public Pagination<OrderAccountCheckTask> paging(OrderAccountCheckTaskSearcher searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        String url = GatewayService.getServiceUrl("/reconciliation/task_list");
        ResponseEntity<Pagination<OrderAccountCheckTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderAccountCheckTaskSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<OrderAccountCheckTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<OrderPaymentConfirm> getPaymentRecordByDate(OrderPaymentConfirmSearcher searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        String url = GatewayService.getServiceUrl("/reconciliation/getPaymentRecordByDate");
        ResponseEntity<Pagination<OrderPaymentConfirm>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderPaymentConfirmSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<OrderPaymentConfirm>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void handle(OrderAccountCheckTask request)
    {
        String url = GatewayService.getServiceUrl("/reconciliation/handle");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public OrderAccountStateRecord accountStateRecordDetail(OrderPaymentConfirmSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/reconciliation/accountStateRecordDetail");
        return template.postForObject(url, searcher, OrderAccountStateRecord.class);
    }
    
    @Override
    public Pagination<AccountCheckMistake> mistakePaging(OrderPaymentConfirmSearcher searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        String url = GatewayService.getServiceUrl("/reconciliation/mistakePaging");
        ResponseEntity<Pagination<AccountCheckMistake>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderPaymentConfirmSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<AccountCheckMistake>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<OrderAccountStateRecord> searchOrderAccountStateByDate(OrderPaymentConfirmSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/reconciliation/searchOrderAccountStateByDate");
        ResponseEntity<List<OrderAccountStateRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderPaymentConfirmSearcher>(searcher),
                new ParameterizedTypeReference<List<OrderAccountStateRecord>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public String download(InputStream is, List<OrderAccountStateRecord> list)
    {
        
        String time = DateUtil.format(new Date(), "yyyyMMdd");
        File file = new File("bank_account_record" + time + ".xlsx");
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
            
            for (OrderAccountStateRecord model : list)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getSeqNo());
                data.put(2, model.getMerNum());
                data.put(3, model.getMerName());
                data.put(4, model.getTermId());
                data.put(5, model.getSettlementDate());
                data.put(6, model.getTransactionDate());
                data.put(7, model.getTradingTime());
                data.put(8, model.getOrderId());
                data.put(9, model.getTradingType());
                data.put(10, model.getTradingAmount().divide(new BigDecimal(100)));
                data.put(11, model.getServiceCharge().divide(new BigDecimal(100)));
                data.put(12, model.getEnterAmount().divide(new BigDecimal(100)));
                data.put(13, model.getCardNumber());
                data.put(14, model.getCardDomain());
                data.put(15, model.getCardSpecies());
                data.put(16, model.getReferNo());
                data.put(17, model.getoReferNo());
                data.put(18, model.getoTradingTime());
                data.put(19, model.getPayType());
                data.put(20, model.getBankCode());
                
                datalist.add(data);
            }
            
            String[] heads =
                new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    
}
