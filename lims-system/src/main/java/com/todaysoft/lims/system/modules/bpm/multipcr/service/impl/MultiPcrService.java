package com.todaysoft.lims.system.modules.bpm.multipcr.service.impl;

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

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.impl.DataPcrDataService;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.multipcr.service.IMultiPcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class MultiPcrService extends RestService implements IMultiPcrService
{
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public List<MultiPcrAssignTask> getMultipcrAssignableList(MultiPcrAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/tasks/assignable");
        ResponseEntity<List<MultiPcrAssignTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<MultiPcrAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<MultiPcrAssignTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<MultipcrAssignModel> getMultipcrAssignModel(MultipcrAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/tasks/assigning");
        ResponseEntity<List<MultipcrAssignModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MultipcrAssignArgs>(args), new ParameterizedTypeReference<List<MultipcrAssignModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignMultipcr(MultipcrAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/tasks/assign");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public MultipcrSubmitModel getMultipcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/sheets/{id}");
        return template.getForObject(url, MultipcrSubmitModel.class, id);
    }
    
    @Override
    public String zipFilesMultipcr(File zipfile, MultipcrSubmitModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadMultipcrTasks(sheet)));
        
        String zipFile = testSheetService.zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    private String downloadMultipcrTasks(MultipcrSubmitModel sheet)
    {
        InputStream inputStream = DataPcrDataService.class.getResourceAsStream("/taskTemplate/multipcr/MULTIPCR_TASK.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(sheet.getCode() + "_任务单_" + time + ".xlsx");
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
            dataMap.put("B5", sheet.getCode());
            dataMap.put("E5", sheet.getAssignerName());
            dataMap.put("H5", testSheetService.getFormatTime("yyyy-MM-dd HH:mm:ss", sheet.getAssignTime()));
            dataMap.put("K5", user.getName());
            dataMap.put("B6", sheet.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<MultipcrAssignModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (MultipcrAssignModel assignModel : taskList)
            {
                for (MultiPcrTask task : assignModel.getTasks())
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, task.getTestingCode());
                    data.put(2, task.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                    data.put(3, task.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                    data.put(4, task.getTestingTask().getInputSample().getSampleCode());
                    data.put(5, task.getPrimer().getForwardPrimerName());
                    data.put(6, task.getDnaConcn());
                    data.put(7, task.getQualityLevel());
                    
                    data.put(8, task.getDnaVolume());
                    data.put(9, task.getConnectNum());
                    data.put(10, task.getProduct().getName());
                    
                    datalist.add(data);
                }
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList(path, heads, datalist, wsheet);
            excel.mergedRegion3(wsheet, 1, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 2, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 3, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion2(wsheet, 4, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            //excel.mergedRegion2(wsheet, 5, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 6, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            //excel.mergedRegion2(wsheet, 7, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 8, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 9, 8, wsheet.getLastRowNum(), wbModule);
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
    public void submitMultipcr(MultipcrSubmitSheet sheet)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/sheets/submit");
        template.postForLocation(url, sheet);
        
    }
    
    @Override
    public String downloadOutput(InputStream is, MultipcrSubmitModel sheet)
    {
        File tempFile = new File(sheet.getCode() + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            
            List<MultipcrAssignModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (MultipcrAssignModel assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTasks().get(0).getTestingCode());
                data.put(2, assignModel.getTasks().get(0).getOutputSampleCode());
                data.put(3, assignModel.getTasks().get(0).getOutputSampleCode());
                data.put(4, assignModel.getTasks().get(0).getTestingTask().getInputSample().getReceivedSample().getSampleName());
                data.put(5, "");
                data.put(6, "");
                data.put(7, "");
                data.put(8, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    
}
