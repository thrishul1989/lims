package com.todaysoft.lims.system.modules.bpm.qpcr.service.impl;

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

import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrDataAnalRecordResolver;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.impl.DataPcrDataService;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;
import com.todaysoft.lims.system.modules.bpm.qpcr.service.IQpcrDataService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class QpcrDataService implements IQpcrDataService
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;

    @Autowired
    private IDataTemplateService dataTemplateService;

    @Autowired
    private IMlpaDataDataService mlpaDataDataService;
    
    @Override
    public String zipFilesQpcr(File zipfile, QpcrSubmitModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadQpcrTasks(sheet)));
        srcfile.add(new File(downloadQpcrResult(sheet)));
        String zipFile = testSheetService.zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    //任务单
    private String downloadQpcrTasks(QpcrSubmitModel request)
    {
        InputStream inputStream = DataPcrDataService.class.getResourceAsStream("/taskTemplate/qpcr/QPCR_TASK.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_任务单_" + time + ".xlsx");
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
            dataMap.put("H5", testSheetService.getFormatTime("yyyy-MM-dd HH:mm:ss", request.getAssignTime()));
            dataMap.put("K5", user.getName());
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<QpcrTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (QpcrTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, task.getSampleCode());
                data.put(3, task.getGeneSymbol());
                data.put(4, task.getChrLocation());
                data.put(5, task.getChromosome());
                data.put(6, task.getBeginLocus());
                data.put(7, task.getEndLocus());
                data.put(8, task.getExon());
                data.put(9, task.getUpRefGene());
                data.put(10, task.getDownRefGene());
                data.put(11, task.getRefSample());
                data.put(12, task.getSequencingCode());
                data.put(13, task.getProductName());
                data.put(14, task.getOrderCode());
                data.put(15, task.getSampleName());
                data.put(16, task.getFamilyRelation());
                data.put(17, "");
                data.put(18, "");
                data.put(19, "");
                data.put(20, "");
                data.put(21, "");
                data.put(22, "");
                data.put(23, "");
                datalist.add(data);
            }
            
            String[] heads =
                new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7", "N7", "O7", "P7", "Q7", "R7", "S7", "T7", "U7",
                    "V7", "W7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    private String downloadQpcrResult(QpcrSubmitModel request)
    {
        InputStream inputStream = DataPcrDataService.class.getResourceAsStream("/taskTemplate/qpcr/QPCR_RESULT.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_分析结果_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<QpcrTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (QpcrTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, "");
                data.put(3, "");
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
    public DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile) {
        DataAnalysisParseModel result = new DataAnalysisParseModel();
        List<String> columns = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(sheetId);
        dataTemplate.getColumnList().stream().forEach(x->columns.add(x.getColumnName()));

        //上传解压
        ZipFileUploadModel zipFileUploadModel = mlpaDataDataService.unZipFiles(zipFile, TestingMethod.QPCR);

        //解析xls
        QpcrDataAnalRecordResolver resolver = new QpcrDataAnalRecordResolver(zipFileUploadModel,dataTemplate);
        List<QpcrSubmitTask> records = resolver.resolve();
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
