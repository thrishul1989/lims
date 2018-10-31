package com.todaysoft.lims.system.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
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

import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.AppSampleTransport;
import com.todaysoft.lims.system.model.vo.order.OrderSampleView;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceiving;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoraging;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoragingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferring;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.system.service.ISampleReceivingService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.system.util.Response;
import com.todaysoft.lims.utils.DateUtils;

@Service
public class SampleReceivingService extends RestService implements ISampleReceivingService
{
    
    @Override
    public void update(SampleReceivingFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/update");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void create(SampleReceivingFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public List<OrderSampleView> getSampleByView(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getSampleByView");
        ResponseEntity<List<OrderSampleView>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<OrderSampleView>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void createTransfer(SampleTransferFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/createTransfer");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public Response<ReceivedSample> getTransferSample(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getTransferSample");
        ResponseEntity<Response<ReceivedSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<Response<ReceivedSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void createStoraging(SampleStoragingFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/createStoraging");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public Pagination<ReceiveSampleOrderResponse> sampleReceivingPaging(SampleReceiveRecordSearcher searcher, int pageNo, int pageSize)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/orders/sample_receive_search");
        ResponseEntity<Pagination<ReceiveSampleOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleReceiveRecordSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ReceiveSampleOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SampleTransferring> sampleTransferringPaging(SampleTransferringDetail searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/sampleTransferringPaging");
        ResponseEntity<Pagination<SampleTransferring>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleTransferringDetail>(searcher),
                new ParameterizedTypeReference<Pagination<SampleTransferring>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SampleStoraging> sampleStoragePaging(SampleStoraging searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/sampleStoragePaging");
        ResponseEntity<Pagination<SampleStoraging>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleStoraging>(searcher), new ParameterizedTypeReference<Pagination<SampleStoraging>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SampleReceivingDetail> sampleReceivingDetail(SampleStoraging request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/sampleReceivingDetail");
        ResponseEntity<Pagination<SampleReceivingDetail>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleStoraging>(request),
                new ParameterizedTypeReference<Pagination<SampleReceivingDetail>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SampleReceivingDetail> sampleDetail(SampleReceiveRecordSearcher request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/sampleDetail");
        ResponseEntity<Pagination<SampleReceivingDetail>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleReceiveRecordSearcher>(request),
                new ParameterizedTypeReference<Pagination<SampleReceivingDetail>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<ReceivedSample> storageDetail(SampleStoraging request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/storageDetail");
        ResponseEntity<Pagination<ReceivedSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleStoraging>(request), new ParameterizedTypeReference<Pagination<ReceivedSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<ReceivedSample> getReceivedSampleByCode(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getReceivedSampleByCode");
        ResponseEntity<List<ReceivedSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<ReceivedSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Response<ReceivedSample> getHasReceivedSample(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getHasReceivedSample");
        ResponseEntity<Response<ReceivedSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<Response<ReceivedSample>>()
            {
            });
        return exchange.getBody();
    }
    
    public Map<String, Object> getReceivedSampleNameByCode(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getReceivedSampleNameByCode");
        ResponseEntity<Map<String, Object>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<Map<String, Object>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<ReceivedSample> getHasInboundSample(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getHasInboundSample");
        ResponseEntity<List<ReceivedSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<ReceivedSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<SampleReceivingDetail> getSampleDetailByCode(SampleReceiveRecordSearcher search)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getSampleDetailByCode");
        ResponseEntity<List<SampleReceivingDetail>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleReceiveRecordSearcher>(search),
                new ParameterizedTypeReference<List<SampleReceivingDetail>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public SampleReceiving getSampleReceivingById(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getSampleReceivingById/{id}");
        return template.getForObject(url, SampleReceiving.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public SampleTransferring getSampleTransferringById(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getSampleTransferringById/{id}");
        return template.getForObject(url, SampleTransferring.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public String downloadCode(InputStream is, SampleTransferring list)
    {
        File tempFile = new File("CODE_" + DateUtils.getDate("yyMMdd") + "_" + list.getCode().replace("转存", "ZC") + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 1;
            OrderSearchRequest request = null;
            ;
            for (SampleTransferringDetail sample : list.getSampleTransferringDetail())
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, i);
                data.put(2, sample.getSampleCode());
                data.put(3, sample.getSampleCode());
                request = new OrderSearchRequest();
                request.setSampleCode(sample.getSampleCode());
                Map<String, Object> result = getReceivedSampleNameByCode(request);
                if (result != null && result.size() > 0)
                {
                    data.put(4, result.get("name"));
                }
                data.put(7, "");
                data.put(8, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"}; // 必须为列表头部所有位置集合， 输出
            
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
    public Pagination<AppSampleTransport> packageSampleList(SampleReceiveRecordSearcher searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/packageSampleList");
        ResponseEntity<Pagination<AppSampleTransport>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleReceiveRecordSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<AppSampleTransport>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<OrderSampleView> seacherBasicSample(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/orders/seacherBasicSample");
        ResponseEntity<List<OrderSampleView>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<OrderSampleView>>()
            {
            });
        return exchange.getBody();
    }
}
