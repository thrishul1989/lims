package com.todaysoft.lims.system.modules.bpm.samplestock.service.impl;

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

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.TestingMlpaTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockin;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockinDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockout;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockoutDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageOutRecord;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageResponse;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingCaptureSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSampleStorage;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheetSampleStorage;
import com.todaysoft.lims.system.modules.bpm.samplestock.service.ISheetSampleStorageService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class SheetSampleStorageService extends RestService implements ISheetSampleStorageService
{
    @Autowired
    private ITestSheetService testSheetService;
    
    @Override
    public Pagination<SampleStorageOutRecord> sampleOut_list(TestingSheetSampleStorage searcher, int pageNo, int pageSize)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/sampleOut_list");
        ResponseEntity<Pagination<SampleStorageOutRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingSheetSampleStorage>(searcher),
                new ParameterizedTypeReference<Pagination<SampleStorageOutRecord>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<SampleStorageOutRecord> sample_list(TestingSheetSampleStorage searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/sample_list");
        ResponseEntity<Pagination<SampleStorageOutRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingSheetSampleStorage>(searcher),
                new ParameterizedTypeReference<Pagination<SampleStorageOutRecord>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public SampleStockout getOutDetail(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/SampleStockout/{id}");
        return template.getForObject(url, SampleStockout.class, id);
    }
    
    @Override
    public TestingSheetSampleStorage getTestingSheetSampleStorage(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getTestingSheetSampleStorage/{id}");
        return template.getForObject(url, TestingSheetSampleStorage.class, id);
    }
    
    @Override
    public TestingSampleStorage getTestingSampleStorage(String code)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getTestingSampleStorage/{code}");
        return template.getForObject(url, TestingSampleStorage.class, code);
    }
    
    @Override
    public void updateTestingSheetSampleStorage(TestingSheetSampleStorage request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/updateTestingSheetSampleStorage");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public String createStockout(SampleStockout request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createStockout");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public String createStockoutDetail(SampleStockoutDetails request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createStockoutDetail");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public String createStockin(SampleStockin request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createStockin");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public TestingSheetSampleStorage createAndReturnStorageIn(TestingSheet sheet)
    {
        
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createAndReturnStorageIn");
        ResponseEntity<TestingSheetSampleStorage> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSheet>(sheet), new ParameterizedTypeReference<TestingSheetSampleStorage>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public String createStockinDetail(SampleStockinDetails request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createStockinDetail");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public SampleStockin getInDetail(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/SampleStockin/{id}");
        return template.getForObject(url, SampleStockin.class, id);
    }
    
    @Override
    public void updateTestingSampleStorage(TestingSampleStorage request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/updateTestingSampleStorage");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public String getTestingCode(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getTestingCode/{taskId}");
        return template.getForObject(url, String.class, taskId);
    }
    
    @Override
    public void ceateStorageIn(TestingSheet sheet)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/createStorageIn");
        template.postForLocation(url, sheet);
        
    }
    
    @Override
    public String searchFreeLocation(String typeId, String storageType)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/storageDetail?typeId=" + typeId + "&storageType=" + storageType + "");
        return template.getForObject(url, String.class);
    }
    
    @Override
    public StoreContainer getBestDevice(String typeId, String storageType)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/getBestDevice?typeId=" + typeId + "&storageType=" + storageType + "");
        return template.getForObject(url, StoreContainer.class);
    }
    
    @Override
    public void updateLocationState(String code)
    {
        String url = GatewayService.getServiceUrl("/bcm/sampleReceiving/updateLocationState/{code}");
        template.getForObject(url, String.class, code);
    }
    
    @Override
    public TestingSample getTestingSampleByCode(String code)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getTestingSampleByCode/{code}");
        return template.getForObject(url, TestingSample.class, code);
    }
    
    @Override
    public void cteateSampleStorage(TestingSampleStorage request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/cteateSampleStorage");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public List<TestingCaptureSample> getTestingCaptureSampleBytaskId(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getTestingCaptureSampleBytaskId");
        Map m = new HashMap<>();
        m.put("id", taskId);
        ResponseEntity<List<TestingCaptureSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(m), new ParameterizedTypeReference<List<TestingCaptureSample>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public SampleStockout closestOutBySample(String sampleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/closestOutBySample/{sampleId}");
        return template.getForObject(url, SampleStockout.class, sampleId);
        
    }
    
    @Override
    public SampleStockin closestInBySampleId(String sampleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/closestInBySampleId/{sampleId}");
        return template.getForObject(url, SampleStockin.class, sampleId);
        
    }
    
    @Override
    public List<TestingMlpaTask> getMlpaTestingBytaskId(String testingTaskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getMlpaTestingBytaskId?testingTaskId=" + testingTaskId);
        Map m = new HashMap<>();
        m.put("testingTaskId", testingTaskId);
        ResponseEntity<List<TestingMlpaTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(m), new ParameterizedTypeReference<List<TestingMlpaTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<MultiPcrTask> getMultiPcrTaskBytaskId(String testingTaskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getMultiPcrTaskBytaskId?testingTaskId=" + testingTaskId);
        Map m = new HashMap<>();
        m.put("testingTaskId", testingTaskId);
        ResponseEntity<List<MultiPcrTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(m), new ParameterizedTypeReference<List<MultiPcrTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String download(InputStream is, List<SampleStorageResponse> list, TestingSheetSampleStorage sheetSampleStorage)
    {
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = null;
        if ("1".equals(sheetSampleStorage.getStorageType()))
        {
            file = new File("sampleOut_" + time + ".xlsx");
        }
        else
        {
            file = new File("sampleIn_" + time + ".xlsx");
        }
        TestSheetService.inputstreamToFile(is, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", sheetSampleStorage.getTestingSheet().getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (SampleStorageResponse model : list)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getTask().getTestingCode());
                data.put(2, model.getTask().getName());
                data.put(3, model.getSample().getSampleCode());
                data.put(4, model.getSample().getReceivedSample().getSampleName());
                data.put(5, model.getSample().getSampleTypeName());
                if (null != model.getStorage())
                {
                    data.put(6, model.getStorage().getLocationCode());
                }
                else
                {
                    data.put(6, "");
                }
                
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2", "C2", "D2", "E2", "F2"}; // 必须为列表头部所有位置集合，
                                                                                // 输出
                                                                                // 数据单元格样式和头部单元格样式保持一致
            
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
    public List<LongPcrTask> getLongPcrByTaskId(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getLongPcrTaskBytaskId?testingTaskId=" + taskId);
        Map m = new HashMap<>();
        m.put("testingTaskId", taskId);
        ResponseEntity<List<LongPcrTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(m), new ParameterizedTypeReference<List<LongPcrTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getDtTestingCode(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bpm/testingSheetSampleStorage/getDtTestingCode/{taskId}");
        return template.getForObject(url, String.class, id);
    }
    
}
