package com.todaysoft.lims.system.modules.bpm.libcre.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
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

import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.system.modules.bpm.libcre.service.ILibCreService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class LibCreService extends RestService implements ILibCreService
{
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Override
    public List<LibraryCreateTask> libCreTasks(LibCreTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcre/tasks/assignable");
        ResponseEntity<List<LibraryCreateTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<LibCreTaskSearcher>(searcher), new ParameterizedTypeReference<List<LibraryCreateTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public LibCreAssignModel getLibCreAssignModel(LibCreAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcre/tasks/assigning");
        ResponseEntity<LibCreAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<LibCreAssignArgs>(args), new ParameterizedTypeReference<LibCreAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void libCreAssign(LibraryCreateAssignRequest data)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcre/tasks/assign");
        template.postForObject(url, data, Integer.class);
    }
    
    @Override
    public LibraryCreateSheet getSheet(TestArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcre/sheets/{id}");
        return template.getForObject(url, LibraryCreateSheet.class, args.getId());
    }
    
    @Override
    public void submitSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcre/sheets/submit");
        template.postForLocation(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public String downloadCode(InputStream is, LibraryCreateSheet sheet)
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
            
            List<LibraryCreateTask> tasks = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LibraryCreateTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getOutputSampleCode());
                data.put(3, tst.getOutputSampleCode());
                data.put(4, tst.getOriginalSampleName());
                data.put(5, "");
                data.put(6, "");
                data.put(7, "");
                data.put(8, "");
                datalist.add(data);
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
    public String download(InputStream is, LibraryCreateSheet sheet)
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
            
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", sheet.getCode());
            dataMap.put("E5", sheet.getAssignerName());
            dataMap.put("H5", testSheetService.getFormatTime("yyyy-MM-dd HH:mm:ss", sheet.getAssignTime()));
            dataMap.put("K5", sheet.getReagentKitName());
            dataMap.put("B6", sheet.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<LibraryCreateTask> taskList = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LibraryCreateTask assignModel : taskList)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, assignModel.getTestingCode());
                data.put(2, assignModel.getOriginalSampleTypeName());
                data.put(3, assignModel.getSampleCode());
                data.put(4, assignModel.getConcn());
                data.put(5, assignModel.getSampleInputVolume());
                data.put(6, assignModel.getTeInputVolume().setScale(2, RoundingMode.CEILING));
                data.put(7, assignModel.getOutputSampleCode());
                data.put(8, assignModel.getLibraryIndex());
                datalist.add(data);
                
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
