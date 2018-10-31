package com.todaysoft.lims.system.modules.bpm.fluotest.service.impl;

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

import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestTask;
import com.todaysoft.lims.system.modules.bpm.fluotest.service.IFluoTestService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class FluoTestService extends RestService implements IFluoTestService
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Override
    public List<FluoTestAssignTask> getfluoTestAssignableList(FluoTestAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/tasks/assignable");
        ResponseEntity<List<FluoTestAssignTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<FluoTestAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<FluoTestAssignTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public FluoTestAssignModel getFluoTestAssignModel(FluoTestAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/tasks/assigning");
        ResponseEntity<FluoTestAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<FluoTestAssignArgs>(args), new ParameterizedTypeReference<FluoTestAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignFluoTest(FluoTestAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/tasks/assign");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public FluoTestSubmitModel getFluoTestSubmitModel(String id)
    {
        
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/sheets/{id}");
        return template.getForObject(url, FluoTestSubmitModel.class, id);
    }
    
    @Override
    public void submitFluoTest(FluoTestSubmitModel request)
    {
        
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/sheets/submit");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public String downloadFluoTestData(InputStream inputStream, FluoTestSubmitModel request)
    {
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            AuthorizedUser user = userService.getByToken();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            dataMap.put("G5", testSheetService.getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            dataMap.put("E6", user.getName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<FluoTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (FluoTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getTestingCode());
                data.put(2, task.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                data.put(3, task.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                data.put(4, task.getDnaConcn());
                data.put(5, task.getQualityLevel());
                data.put(6, task.getDnaVolume());
                data.put(7, task.getWaterVolume());
                data.put(8, task.getProduct().getName());
                datalist.add(data);
            }
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
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
