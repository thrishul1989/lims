package com.todaysoft.lims.system.modules.bpm.dnaext.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTask;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;

@Service
public class DNAExtractService extends RestService implements IDNAExtractService
{
    @Autowired
    private IUserService userService;
    
    @Override
    public List<DNAExtractTask> getAssignableList(DNAExtractTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/tasks/assignable");
        ResponseEntity<List<DNAExtractTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DNAExtractTaskSearcher>(searcher), new ParameterizedTypeReference<List<DNAExtractTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DNAExtractAssignModel getAssignModel(DNAExtractAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/tasks/assigning");
        ResponseEntity<DNAExtractAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DNAExtractAssignArgs>(args), new ParameterizedTypeReference<DNAExtractAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assign(DNAExtractAssignSheet data)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/tasks/assign");
        template.postForLocation(url, data);
    }
    
    @Override
    public DNAExtractSheet getSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/sheets/{id}");
        return template.getForObject(url, DNAExtractSheet.class, id);
    }
    
    @Override
    public void submitSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaext/sheets/submit");
        template.postForLocation(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public String downloadCode(InputStream is, DNAExtractSheet sheet)
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
            
            List<DNAExtractTask> tasks = sheet.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (DNAExtractTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getOutputSampleCode());
                data.put(3, tst.getOutputSampleCode());
                data.put(4, tst.getSampleName());
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
    public String downloadDnaExtractData(InputStream inputStream, DNAExtractSheet request)
    {
        //创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            dataMap.put("G5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            AuthorizedUser user = userService.getByToken();
            dataMap.put("E6", user.getName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<DNAExtractTask> tasks = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (DNAExtractTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getProducts());
                data.put(3, tst.getSampleTypeName());
                data.put(4, tst.getSampleCode());
                data.put(5, tst.getSampleName());
                data.put(6, tst.getSampleInputSize());
                data.put(7, tst.getOutputSampleCode());
                data.put(8, tst.getOutputSampleSize());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8"}; // 必须为列表头部所有位置集合， 输出
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
        return tempFile.getAbsolutePath();
    }
}
