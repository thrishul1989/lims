package com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.impl;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataTask;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.IPcrNgsDataDataService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PcrNgsDataDataService extends RestService implements IPcrNgsDataDataService
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;

    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Override
    public String zipFilesDataPcr(File zipfile, PcrNgsDataSheetModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadDataPcrTasks(sheet)));
        srcfile.add(new File(downloadDataPcrResult(sheet)));
        
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
    private String downloadDataPcrTasks(PcrNgsDataSheetModel request)
    {
        InputStream inputStream = PcrNgsDataDataService.class.getResourceAsStream("/taskTemplate/pcrngs/data/PCR_NGS_DATA_TASK.xlsx");
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
            
            List<PcrNgsDataTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsDataTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getBioTestCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getSampleCode());
                data.put(4, task.getCombineCode());
                data.put(5, task.getGene());
                data.put(6, task.getChromosomeLocation());
                data.put(7, task.getSequencingCode());
                data.put(8, task.getProductName());
                data.put(9, task.getOrderCode());
                data.put(10, task.getFamilyRelation());
                data.put(11, "");
                data.put(12, "");
                data.put(13, "");
                data.put(14, "");
                data.put(15, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "K8", "L8", "M8", "N8", "O8"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    private String downloadDataPcrResult(PcrNgsDataSheetModel request)
    {
        InputStream inputStream = PcrNgsDataDataService.class.getResourceAsStream("/taskTemplate/pcrngs/data/PCR_NGS_DATA_RESULT.xlsx");
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
            
            List<PcrNgsDataTask> taskList = request.getTasks();
            String code = request.getCode();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsDataTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, code);
                data.put(2, task.getBioTestCode());
                data.put(3, task.getCombineCode());
                data.put(4, "");
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
        return file.getAbsolutePath();
    }

    @Override
    public DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile)
    {

        DataAnalysisParseModel result = new DataAnalysisParseModel();

        List<String> columns = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(sheetId);
        dataTemplate.getColumnList().stream().forEach(x->columns.add(x.getColumnName()));

        //上传解压
        ZipFileUploadModel zipFileUploadModel = mlpaDataDataService.unZipFiles(zipFile, TestingMethod.PCRNGS);

        //解析xls
        PcrNgsDataAnalRecordResolver resolver = new PcrNgsDataAnalRecordResolver(zipFileUploadModel,dataTemplate);
        List<PcrNgsDataSubmitTaskArgs> records = resolver.resolve();
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
