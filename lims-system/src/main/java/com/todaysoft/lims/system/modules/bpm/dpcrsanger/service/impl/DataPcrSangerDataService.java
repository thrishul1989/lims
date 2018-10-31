package com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerDataService;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataPcrSangerDataService extends RestService implements IDataPcrSangerDataService
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IDataPcrSangerService service;

    @Autowired
    private IDataTemplateService dataTemplateService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @Override
    public String zipFilesDataPcr(File zipfile, DataPcrSangerSheetModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadDataPcrTasks(sheet)));
        srcfile.add(new File(downloadDataPcrResult(sheet)));
        srcfile.add(new File(downloadAnalysTask(sheet)));
        
        byte[] buf = new byte[1024];
        try
        {
            // Create the ZIP file  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files  
            for (int i = 0; i < srcfile.size(); i++)
            {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.  
                out.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file  
                int len;
                while ((len = in.read(buf)) > 0)
                {
                    out.write(buf, 0, len);
                }
                // Complete the entry  
                out.closeEntry();
                in.close();
                file.delete();
            }
            // Complete the ZIP file  
            out.close();
        }
        catch (IOException e)
        {
            return "";
        }
        return zipfile.getAbsolutePath();
    }
    
    //任务单
    private String downloadDataPcrTasks(DataPcrSangerSheetModel request)
    {
        InputStream inputStream = DataPcrSangerDataService.class.getResourceAsStream("/taskTemplate/sangerTest/datapcr/DATA_PCR_TASKS.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_数据分析任务表_" + time + ".xlsx");
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
            dataMap.put("D5", request.getAssignerName());
            dataMap.put("F5", testSheetService.getFormatTime("yyyy-MM-dd HH:mm:ss", request.getAssignTime()));
            dataMap.put("B6", user.getName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<DataPcrSangerTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (DataPcrSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, task.getPcrTaskCode());
                data.put(3, task.getPcrTestCode());
                data.put(4, task.getSampleName());
                data.put(5, task.getSampleCode());
                data.put(6, task.getGene());
                data.put(7, task.getClinicalRecommend());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    
    //结果上传
    private String downloadDataPcrResult(DataPcrSangerSheetModel request)
    {
        InputStream inputStream = DataPcrSangerDataService.class.getResourceAsStream("/taskTemplate/sangerTest/datapcr/DATA_PCR_RESULT.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_测序数据结果上传_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<DataPcrSangerTask> taskList = request.getTasks();
            String code = request.getCode();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (DataPcrSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, code);
                data.put(2, task.getPcrTestCode());
                data.put(3, task.getCombineCode());
                data.put(4, "");
                data.put(5, "");
                data.put(6, "");
                data.put(7, "");
                data.put(8, "");
                data.put(9, task.getSampleName());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    
    //任务单
    private String downloadAnalysTask(DataPcrSangerSheetModel request)
    {
        request = service.exportAnalySheet(request.getId());
        InputStream inputStream = DataPcrSangerDataService.class.getResourceAsStream("/taskTemplate/ANALYS_TASK.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_分析任务单_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            AuthorizedUser u = userService.getByToken();
            dataMap.put("H5", u.getName());
            dataMap.put("K5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<TechnicalAnalyTask> taskList = request.getAnalysTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (TechnicalAnalyTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String sendDate = "";
                String reportDate = "";
                if (null != task.getSubmitTime())
                {
                    sendDate = dateFormater.format(task.getSubmitTime());
                }
                if (null != task.getReportDate())
                {
                    reportDate = dateFormater.format(task.getReportDate());
                }
                data.put(1, task.getProductCode());
                data.put(2, task.getMarketingCenter());
                data.put(3, task.getDataCode());
                data.put(4, task.getReceivedSampleName());
                if ("0".equals(task.getSex()))
                {
                    data.put(5, "男");
                }
                else if ("1".equals(task.getSex()))
                {
                    data.put(5, "女");
                }
                else
                {
                    data.put(5, task.getSex());
                }
                
                data.put(6, task.getCaseNum());
                data.put(7, task.getProductName());
                data.put(8, sendDate);
                data.put(9, task.getReceivedSampleCode());
                data.put(10, task.getAge());
                data.put(11, task.getCustomerCompanyName());
                data.put(12, task.getMethodName());
                data.put(13, reportDate);
                data.put(14, task.getClinicalDiagnosis());
                data.put(15, task.getFocusGenes());
                data.put(16, task.getCaseSummary());
                data.put(17, task.getFamilyHistorySummary());
                data.put(18, task.getSampleTypeName());
                data.put(19, task.getCustomerName());
                data.put(20, task.getBusinessLeader());
                data.put(21, task.getTechnicalPrincipal());
                data.put(22, task.getOrderCode());
                data.put(23, task.getContractType());
                data.put(24, task.getRemark());
                data.put(25, task.getFamilyRelation());
                datalist.add(data);
            }
            
            String[] heads =
                new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7", "N7", "O7", "P7", "Q7", "R7", "S7", "T7", "U7",
                    "V7", "W7", "X7", "Y7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    public DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile) {

        DataAnalysisParseModel result = new DataAnalysisParseModel();
        List<String> columns = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(sheetId);
        dataTemplate.getColumnList().stream().forEach(x->columns.add(x.getColumnName()));

        //上传解压
        ZipFileUploadModel zipFileUploadModel = mlpaDataDataService.unZipFiles(zipFile, TestingMethod.SANGERTEST);

        //解析xls
        DataPcrSangerDataAnalRecordResolver resolver = new DataPcrSangerDataAnalRecordResolver(zipFileUploadModel,dataTemplate);
        List<DataPcrSangerSubmitTaskArgs> records = resolver.resolve();
        DefaultTableModel modelMap = new DefaultTableModel(columns,records);
        result.setModelMap(modelMap);
        result.setPicList(zipFileUploadModel.getPicList());
        result.setLocalFilePath(zipFileUploadModel.getLocalFilePath());
        result.setUploadDir(zipFileUploadModel.getUploadDir());
        result.setPicParentPath(zipFileUploadModel.getPicParentPath());
        //关闭压缩所文件
        try {
            zipFileUploadModel.getZipFile().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
