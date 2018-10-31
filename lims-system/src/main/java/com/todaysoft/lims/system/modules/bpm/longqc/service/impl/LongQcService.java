package com.todaysoft.lims.system.modules.bpm.longqc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.service.impl.LongPcrService;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitTaskModelExcel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestModel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestSheet;
import com.todaysoft.lims.system.modules.bpm.longqc.service.ILongQcService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.StringUtils;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class LongQcService extends RestService implements ILongQcService
{
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public LongqcTestSheet getLongqcSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longqc/sheets/{id}");
        return template.getForObject(url, LongqcTestSheet.class, id);
    }
    
    @Override
    public void submitSheet(LongQcSubmitSheetModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/longqc/sheets/submit");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public List<LongQcSubmitTaskModelExcel> uploadData(File file)
    {
        try
        {
            
            ImportExcel e = new ImportExcel(file, 6, 0);
            
            List<LongQcSubmitTaskModelExcel> List = e.getDataList(LongQcSubmitTaskModelExcel.class);
            Iterator<LongQcSubmitTaskModelExcel> it = List.iterator();
            
            while (it.hasNext())
            {
                if (StringUtils.isEmpty(it.next().getTestingCode()))
                {
                    it.remove();
                }
            }
            
            return List;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }

    @Override
    public String download(InputStream is, LongqcTestSheet sheet)
    {
        File tempFile = new File("CODE_" + sheet.getCode() + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            AuthorizedUser user = userService.getByToken();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", sheet.getCode());
            dataMap.put("E5", sheet.getAssignerName());
            dataMap.put("H5", testSheetService.getFormatTime("yyyy-MM-dd HH:mm:ss", sheet.getAssignTime()));
            dataMap.put("K5", user.getName());
            dataMap.put("B6", sheet.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<LongqcTestModel> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LongqcTestModel assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTestingCode());
                data.put(2, assignModel.getTestingTask().getInputSample().getReceivedSample().getSampleName());
                data.put(3, assignModel.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                data.put(4, assignModel.getTestingTask().getInputSample().getSampleCode());
                
                datalist.add(data);
                
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
