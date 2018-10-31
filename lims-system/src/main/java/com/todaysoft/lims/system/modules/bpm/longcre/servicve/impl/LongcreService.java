package com.todaysoft.lims.system.modules.bpm.longcre.servicve.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.impl.DataPcrDataService;
import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitModel;
import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.longcre.servicve.ILongcreService;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestModel;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class LongcreService extends RestService implements ILongcreService
{
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public LongcreSubmitSheet getLongcreSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longcre/sheets/{id}");
        return template.getForObject(url, LongcreSubmitSheet.class, id);
    }
    
    @Override
    public void submitLongcre(LongcreSubmitSheet sheet)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longcre/sheets/submit");
        template.postForLocation(url, sheet);
        
    }
    
    @Override
    public String zipFilesLongcre(File file, LongcreSubmitSheet sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadLongcreTasks(sheet)));
        
        String zipFile = testSheetService.zipFiles(file, srcfile);
        return zipFile;
    }
    
    private String downloadLongcreTasks(LongcreSubmitSheet sheet)
    {
        InputStream inputStream = LongcreService.class.getResourceAsStream("/taskTemplate/longpcr/LONGCRE_TASK.xlsx");
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
            
            List<LongcreSubmitModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LongcreSubmitModel assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTestingCode());
                data.put(2, assignModel.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                data.put(3, assignModel.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                data.put(4, assignModel.getTestingTask().getInputSample().getSampleCode());
                data.put(5, assignModel.getOutputSampleCode());
                data.put(6, assignModel.getOutputSampleConcn());
                
                data.put(7, assignModel.getQualityLevel());
                data.put(8, assignModel.getProduct().getName());
                data.put(9, assignModel.getInputVolumn().setScale(2, RoundingMode.DOWN));
                data.put(10, assignModel.getWaterVolumn().setScale(2, RoundingMode.DOWN));
                data.put(11, assignModel.getConnectNum());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    public String downloadOutput(InputStream is, LongcreSubmitSheet sheet)
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
            
            List<LongcreSubmitModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LongcreSubmitModel assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTestingCode());
                data.put(2, assignModel.getOutputSampleCode());
                data.put(3, assignModel.getOutputSampleCode());
                data.put(4, assignModel.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                data.put(5,"");
                data.put(6,"");
                data.put(7,"");
                data.put(8,"");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1","E1","F1","G1","H1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
