package com.todaysoft.lims.system.modules.bpm.longpcr.service.impl;

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
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitContent;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitRequestExcel;
import com.todaysoft.lims.system.modules.bpm.longpcr.service.ILongPcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class LongPcrService extends RestService implements ILongPcrService
{
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public List<LongPcrAssignTask> getLongpcrAssignableList(LongPcrAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longpcr/tasks/assignable");
        ResponseEntity<List<LongPcrAssignTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<LongPcrAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<LongPcrAssignTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<LongpcrAssignModel> getLongpcrAssignModel(LongpcrAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longpcr/tasks/assigning");
        ResponseEntity<List<LongpcrAssignModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<LongpcrAssignArgs>(args),
                new ParameterizedTypeReference<List<LongpcrAssignModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void assignLongpcr(LongpcrAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longpcr/tasks/assign");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public LongpcrSubmitModel getLongpcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longpcr/sheets/{id}");
        return template.getForObject(url, LongpcrSubmitModel.class, id);
    }
    
    @Override
    public void submitLongpcr(LongpcrSubmitContent request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longpcr/sheets/submit");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public List<LongpcrSubmitRequestExcel> uploadData(File file)
    {
        
        try
        {
            
            ImportExcel e = new ImportExcel(file, 0, 0);
            
            List<LongpcrSubmitRequestExcel> List = e.getDataList(LongpcrSubmitRequestExcel.class);
            return List;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    @Override
    public String zipFilesLongpcr(File zipfile, LongpcrSubmitModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadLongpcrTasks(sheet)));
        srcfile.add(new File(downloadLongpcrResult(sheet)));
        String zipFile = testSheetService.zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    private String downloadLongpcrResult(LongpcrSubmitModel sheet)
    {
        InputStream inputStream = LongPcrService.class.getResourceAsStream("/taskTemplate/longpcr/longpcr_result.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(sheet.getCode() + "_结果表_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<LongpcrAssignModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LongpcrAssignModel assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTasks().get(0).getPcrCode());
                
                datalist.add(data);
                
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1",}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList(path, heads, datalist, wsheet);
            /*excel.mergedRegion(wsheet, 1, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 2, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 3, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);*/
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
    
    private String downloadLongpcrTasks(LongpcrSubmitModel sheet)
    {
        InputStream inputStream = LongPcrService.class.getResourceAsStream("/taskTemplate/longpcr/LONGPCR_TASK.xlsx");
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
            
            List<LongpcrAssignModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LongpcrAssignModel assignModel : taskList)
            {
                for (LongPcrTask task : assignModel.getTasks())
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, task.getTestingCode());
                    data.put(2, task.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                    data.put(3, task.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                    data.put(4, task.getTestingTask().getInputSample().getSampleCode());
                    data.put(5, task.getPrimer().getForwardPrimerName());
                    data.put(6, task.getDnaConcn());
                    data.put(7, task.getQualityLevel());
                    data.put(8, task.getProduct().getName());
                    
                    data.put(9, task.getPcrCode());
                    
                    datalist.add(data);
                }
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList(path, heads, datalist, wsheet);
            excel.mergedRegion(wsheet, 1, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 2, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 3, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
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
