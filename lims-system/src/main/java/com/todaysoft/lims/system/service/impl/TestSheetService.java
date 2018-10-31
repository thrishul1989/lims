package com.todaysoft.lims.system.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.config.ExceptionResolver;
import com.todaysoft.lims.system.model.vo.MlpaPcTestCodeModel;
import com.todaysoft.lims.system.model.vo.TestingSheet;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskAbsolute;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalyDetailsRecord;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnalySampleRecord;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTTask;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRTask;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerTask;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetGroupModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetSampleModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestTask;
import com.todaysoft.lims.system.modules.bpm.ontest.model.SequencingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignTask;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestTask;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitTask;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignTask;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignReference;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTTask;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRTask;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SixNineInfoModel;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerTask;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.adapter.request.TestingSheetSubmitRequest;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestSheetService extends RestService implements ITestSheetService
{
    
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
    
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ExceptionResolver.class);
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IBiologyAnalyService service;
    
    @Override
    public TestingSheet getTestSheet(Integer activitiTaskId)
    {
        String url = GatewayService.getServiceUrl("/testingSheet/activitiTaskId/{activitiTaskId}");
        return template.getForObject(url, TestingSheet.class, activitiTaskId);
    }
    
    @Override
    public Pagination<TestingSheetTaskAbsolute> absolutePaging()
    {
        String url = GatewayService.getServiceUrl("/testingSheet/absolute/paging");
        ResponseEntity<Pagination<TestingSheetTaskAbsolute>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Pagination<TestingSheetTaskAbsolute>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void dnaSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submit");
        template.postForLocation(url, req);
    }
    
    @Override
    public void dnaCheckSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitDnaQt");
        template.postForLocation(url, req);
    }
    
    @Override
    public void wkCreateSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitWkCreate");
        template.postForLocation(url, req);
    }
    
    @Override
    public void wkCheckSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitWkQt");
        template.postForLocation(url, req);
    }
    
    @Override
    public void wkCatchSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitWKCatch");
        template.postForLocation(url, req);
    }
    
    @Override
    public void xddlSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitXddl");
        template.postForLocation(url, req);
    }
    
    @Override
    public void jddlSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitAbsoluteQ");
        template.postForLocation(url, req);
    }
    
    @Override
    public void onTestSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitOntest");
        template.postForLocation(url, req);
    }
    
    @Override
    public void bioInfoSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitBioInfo");
        template.postForLocation(url, req);
        
    }
    
    @Override
    public void tecAnalysisSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitTecAnalysis");
        template.postForLocation(url, req);
    }
    
    @Override
    public void reportCreateSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitReportCreate");
        template.postForLocation(url, req);
    }
    
    @Override
    public void reportCheckSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitReportCheck");
        template.postForLocation(url, req);
    }
    
    @Override
    public void reportMailSubmit(TestingSheet request)
    {
        TestingSheetSubmitRequest req = new TestingSheetSubmitRequest();
        req.setId(request.getId());
        req.setTestingSheet(request);
        req.getTestingSheet().setSemantic(request.getSemantic());
        
        String url = GatewayService.getServiceUrl("/testing/submitReportMail");
        template.postForLocation(url, req);
    }
    
    @Override
    public void downloadData(HttpServletResponse response, String path)
    {
        
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = null;
        try
        {
            // path是指欲下载的文件的路径。
            file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
                outputStream.close();
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        if (null != file)
        {
            
            file.delete();
        }
        
    }
    
    private String isXLSX(String path, String nameCode)
    {
        String fileName = null;
        // 取得根目录路径
        String rootPath = getClass().getResource("/").getFile().toString();
        if (path.endsWith(excel2007U))
        {
            fileName = rootPath + nameCode + ".xlsx";
            
        }
        else
        {
            fileName = rootPath + nameCode + ".xls";
        }
        
        return fileName;
    }
    
    @Override
    public String getFileName(String path, String nameCode, String downloadName)
    {
        String fileName = null;
        // 取得根目录路径
        String rootPath = getClass().getResource("/").getFile().toString();
        
        if (path.endsWith(excel2007U))
        {
            fileName = rootPath + nameCode + downloadName + ".xlsx";
            
        }
        else
        {
            fileName = rootPath + nameCode + downloadName + ".xls";
        }
        
        return fileName;
    }
    
    private String getFileNamePCR2(String path, String nameCode, String downloadName)
    {
        String fileName = null;
        // 取得根目录路径
        String rootPath = getClass().getResource("/").getFile().toString();
        
        if (path.endsWith(excel2007U))
        {
            fileName = rootPath + downloadName + nameCode + ".xlsx";
            
        }
        else
        {
            fileName = rootPath + downloadName + nameCode + ".xls";
        }
        
        return fileName;
    }
    
    private String getUserName(Integer id)
    {
        return userService.getByToken().getName();
    }
    
    @Override
    public String downloadDNAData(String path, TestingSheet request)
    {
        OutputStream os = null;
        File file = null;
        // try
        // {
        // ExcelUtil excel = new ExcelUtil();
        // Workbook wbModule = excel.getTempWorkbook(path);
        // Sheet wsheet = wbModule.getSheetAt(0);
        // String str = path.substring(path.lastIndexOf("/") + 1);
        // String fileName = isXLSX(str, request.getCode());
        // file = new File(fileName);
        // os = new FileOutputStream(file);
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        // Map<String, Object> dataMap = new HashMap<String, Object>();
        // dataMap.put("B5", request.getCode());
        // dataMap.put("D5", request.getSendName());
        // dataMap.put("F5", dateFormater.format(request.getCreateTime()));
        // dataMap.put("H5", "");
        // dataMap.put("J5", getUserName(request.getExecuter()));
        // dataMap.put("B7", request.getDescription());
        // excel.writeData(path, dataMap, wsheet);
        //
        // List<TestingSheetTask> sheetTaskList =
        // request.getTestingSheetTaskList();
        // List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer,
        // Object>>();
        // for (TestingSheetTask tst : sheetTaskList)
        // {
        // // Map<Integer, Object> data = new HashMap<Integer, Object>();
        // // data.put(1, tst.getTestingCode());
        // // data.put(2, tst.getInputSamplecode());
        // // data.put(3, tst.getOutputSamplecode());
        // // data.put(4, tst.getInspectName());
        // // data.put(5, tst.getInputSampleLocation());
        // // data.put(6, tst.getOutputSampleLocation());
        // // data.put(7, tst.getSampleType());
        // //
        // // datalist.add(data);
        // }
        //
        // String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8",
        // "G8", "H8", "I8", "J8"}; // 必须为列表头部所有位置集合， 输出
        // // 数据单元格样式和头部单元格样式保持一致
        //
        // excel.writeDateList(path, heads, datalist, wsheet);
        //
        // // 写到输出流并移除资源
        // excel.writeAndClose(path, os);
        // os.flush();
        //
        // }
        // catch (FileNotFoundException e)
        // {
        //
        // e.printStackTrace();
        // }
        // catch (IOException e)
        // {
        //
        // e.printStackTrace();
        // }
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadDNAQCData(InputStream inputStream, DNAQcSheet request, BigDecimal[] concentration, BigDecimal[] volume, BigDecimal[] A260280, BigDecimal[] A260230, String[] qualityGrade)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~DNA_QC----TEMPFILE path!!!!!:" + path);
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("E6", request.getQualityControlMethods());
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            dataMap.put("I5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            AuthorizedUser user = userService.getByToken();
            dataMap.put("E6", user.getName());
            dataMap.put("I6", request.getQualityControlMethods());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<DNAQcTask> tasks = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (DNAQcTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getProducts());
                data.put(3, tst.getOriginalSampleTypeName());
                data.put(4, tst.getOriginalSampleCode());
                data.put(5, tst.getOriginalSampleName());
                data.put(6, tst.getSampleCode());
                data.put(7, "");
                data.put(8, "");
                data.put(9, "");
                data.put(10, "");
                data.put(11, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "K9", "L9"}; // 必须为列表头部所有位置集合，
                                                                                                                    // 输出
            // 数据单元格样式和头部单元格样式保持一致
            /*
             * Font font = wbModule.createFont();
             * font.setBoldweight(Font.BOLDWEIGHT_NORMAL); CellStyle style =
             * wbModule.createCellStyle(); style.setFont(font);
             * style.setAlignment(CellStyle.ALIGN_CENTER);
             * style.setBorderBottom(CellStyle.BORDER_THIN);
             * style.setBorderRight(CellStyle.BORDER_THIN);
             * wsheet.setDefaultColumnStyle(0, style);
             * wsheet.setDefaultColumnStyle(1, style);
             * wsheet.setDefaultColumnStyle(2, style);
             * wsheet.setDefaultColumnStyle(3, style);
             */
            
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
    public String downloadCDNAQCData(InputStream inputStream, CDNAQcSheet request, String[] concentration, String[] volume, String[] A260280, String[] A260230, String[] qualityGrade)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~CDNA_QC----TEMPFILE path!!!!!:" + path);
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
            dataMap.put("I5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            dataMap.put("B6", request.getReagentKitName());
            AuthorizedUser user = userService.getByToken();
            dataMap.put("E6", user.getName());
            dataMap.put("I6", request.getQualityControlMethods());
            dataMap.put("B7", request.getDescription());
            excel.writeData("", dataMap, wsheet);
            
            List<CDNAQcTask> tasks = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (CDNAQcTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getProducts());
                data.put(3, tst.getOriginalSampleTypeName());
                data.put(4, tst.getOriginalSampleCode());
                data.put(5, tst.getOriginalSampleName());
                data.put(6, tst.getSampleCode());
                data.put(7, "");
                data.put(8, "");
                data.put(9, "");
                data.put(10, "");
                data.put(11, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "K9", "L9"}; // 必须为列表头部所有位置集合，
                                                                                                                    // 输出
            // 数据单元格样式和头部单元格样式保持一致
            
            excel.writeDateList("", heads, datalist, wsheet);
            
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
    public String downloadLibQcData(InputStream inputStream, LibraryQcSubmitModel request, String[] concentration, String[] volume, String[] A260280, String[] A260230, String[] qualityGrade)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~LIB_QC----TEMPFILE path!!!!!:" + path);
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
            dataMap.put("I5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            AuthorizedUser user = userService.getByToken();
            dataMap.put("E6", user.getName());
            dataMap.put("I6", request.getQualityControlMethods());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<LibraryQcTask> tasks = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (LibraryQcTask tst : tasks)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getProducts());
                data.put(3, tst.getOriginalSampleTypeName());
                data.put(4, tst.getOriginalSampleCode());
                data.put(5, tst.getOriginalSampleName());
                data.put(6, tst.getSampleCode());
                data.put(7, "");
                data.put(8, "");
                data.put(9, "");
                data.put(10, "");
                data.put(11, "");
                data.put(12, "");
                data.put(13, tst.getIndex());
                data.put(14, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "K9", "L9", "M9", "N9"}; // 必须为列表头部所有位置集合，
            // 输出
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
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public String downloadWKCreateData(String path, TestingSheet request)
    {
        OutputStream os = null;
        File file = null;
        // try
        // {
        // ExcelUtil excel = new ExcelUtil();
        // Workbook wbModule = excel.getTempWorkbook(path);
        // Sheet wsheet = wbModule.getSheetAt(0);
        // String fileName = isXLSX(path, request.getCode());
        // file = new File(fileName);
        // os = new FileOutputStream(file);
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        // Map<String, Object> dataMap = new HashMap<String, Object>();
        // /* dataMap.put("B6",request.get)*/
        // dataMap.put("B5", request.getCode());
        // dataMap.put("D5", request.getSendName());
        // dataMap.put("F5", dateFormater.format(request.getCreateTime()));
        // dataMap.put("H5", "");
        // dataMap.put("J5", getUserName(request.getExecuter()));
        // // dataMap.put("D6", request.getReagent());
        // // dataMap.put("F6", request.getBatchNum());
        // // dataMap.put("H6", request.getReagentPreInput());
        // dataMap.put("B7", request.getDescription());
        // excel.writeData(path, dataMap, wsheet);
        //
        // List<TestingSheetTask> sheetTaskList =
        // request.getTestingSheetTaskList();
        // List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer,
        // Object>>();
        // for (TestingSheetTask tst : sheetTaskList)
        // {
        // // Map<Integer, Object> data = new HashMap<Integer, Object>();
        // // data.put(1, tst.getConnectorId());
        // // data.put(2, tst.getTestingCode());
        // // data.put(3, tst.getOutputSampleLocation());
        // // data.put(4, tst.getSampleDetail().getInspectMan());
        // // data.put(5, tst.getInputSamplecode());
        // // data.put(6, tst.getInputSampleLocation());
        // // data.put(7, tst.getSampleType());
        // // data.put(8, "");
        // // data.put(9, "");
        // // datalist.add(data);
        // }
        //
        // String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8",
        // "G8", "H8"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
        //
        // excel.writeDateList(path, heads, datalist, wsheet);
        //
        // // 写到输出流并移除资源
        // excel.writeAndClose(path, os);
        // os.flush();
        //
        // }
        // catch (FileNotFoundException e)
        // {
        //
        // e.printStackTrace();
        // }
        // catch (IOException e)
        // {
        //
        // e.printStackTrace();
        // }
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadWKQCData(String path, TestingSheet request)
    {
        OutputStream os = null;
        File file = null;
        // try
        // {
        // ExcelUtil excel = new ExcelUtil();
        // Workbook wbModule = excel.getTempWorkbook(path);
        // Sheet wsheet = wbModule.getSheetAt(0);
        // String fileName = isXLSX(path, request.getCode());
        // file = new File(fileName);
        // os = new FileOutputStream(file);
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        // Map<String, Object> dataMap = new HashMap<String, Object>();
        // dataMap.put("B5", request.getCode());
        // dataMap.put("D5", request.getSendName());
        // dataMap.put("F5", dateFormater.format(request.getCreateTime()));
        // dataMap.put("H5", "");
        // dataMap.put("J5", getUserName(request.getExecuter()));
        // dataMap.put("B7", request.getDescription());
        // excel.writeData(path, dataMap, wsheet);
        //
        // List<TestingSheetTask> sheetTaskList =
        // request.getTestingSheetTaskList();
        // List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer,
        // Object>>();
        // for (TestingSheetTask tst : sheetTaskList)
        // {
        // // Map<Integer, Object> data = new HashMap<Integer, Object>();
        // // data.put(1, tst.getTestingCode());
        // // data.put(2, tst.getOutputSamplecode());
        // // data.put(3, tst.getInputSampleLocation());
        // // data.put(4, "");
        // // data.put(5, "");
        // // data.put(6, "");
        // // data.put(7, "");
        // // data.put(8, "");
        // // data.put(9, "");
        // // datalist.add(data);
        // }
        //
        // String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8",
        // "G8", "H8", "I8", "J8"}; // 必须为列表头部所有位置集合， 输出
        // // 数据单元格样式和头部单元格样式保持一致
        //
        // excel.writeDateList(path, heads, datalist, wsheet);
        //
        // // 写到输出流并移除资源
        // excel.writeAndClose(path, os);
        // os.flush();
        //
        // }
        // catch (FileNotFoundException e)
        // {
        //
        // e.printStackTrace();
        // }
        // catch (IOException e)
        // {
        //
        // e.printStackTrace();
        // }
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadLibrarycatchData(InputStream inputStream, LibraryCaptureSheetModel request, BigDecimal[] concentration, BigDecimal[] volume)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
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
            dataMap.put("D5", request.getAssignerName());
            dataMap.put("F5", dateFormater.format(request.getAssignTime()));
            dataMap.put("J5", dateFormater.format(new Date()));
            AuthorizedUser u = userService.getByToken();
            dataMap.put("J6", u.getName());
            dataMap.put("B6", request.getReagentKitName());
            dataMap.put("D6", request.getBatchCode());
            dataMap.put("F6", "");
            dataMap.put("B7", request.getMethods());
            dataMap.put("B8", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<LibraryCaptureSheetGroupModel> libCapSheet = request.getGroups();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (LibraryCaptureSheetGroupModel tst : libCapSheet)
            {
                List<LibraryCaptureSheetSampleModel> sheetTaskCatchList = tst.getSamples();
                for (LibraryCaptureSheetSampleModel stc : sheetTaskCatchList)
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, tst.getTestingCode());
                    data.put(2, stc.getSampleCode());
                    // data.put(3, stc.getSampleVolume());
                    data.put(3, stc.getInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
                    data.put(4, tst.getProbeName());
                    data.put(5, stc.getConcn());
                    data.put(6, stc.getFragmentLengthStart());
                    data.put(7, stc.getFragmentLengthEnd());
                    data.put(8, stc.getLibraryIndex());
                    data.put(9, stc.getOriginalSampleTypeName());
                    datalist.add(data);
                }
            }
            String[] heads = new String[] {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9"}; // 必须为列表头部所有位置集合，
            // 输出
            excel.writeDateList(path, heads, datalist, wsheet);
            excel.mergedRegion(wsheet, 0, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 3, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion(wsheet, 9, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
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
    public String downloadRQTData(InputStream inputStream, RQTSheetModel request, BigDecimal[] ctv)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
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
            dataMap.put("H5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            AuthorizedUser user = userService.getByToken();
            dataMap.put("E6", user.getName());
            dataMap.put("H6", request.getDesignDatasize());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<RQTTask> tasks = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (RQTTask task : tasks)
            {
                
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getTestingCode());
                data.put(2, task.getSampleCode());
                if (task.isCaptureSample())
                {
                    if (StringUtils.isNotEmpty(task.getProbeName()))
                    {
                        data.put(3, task.getProbeName() + "-" + formatData(task.getProbeDatasize()) + "G");
                    }
                }
                data.put(4, task.getConcn());
                data.put(5, task.getTeInputVolume());
                data.put(6, task.getPoolingConcn());
                data.put(7, task.getTotalDatasize());
                data.put(8, task.getSampleInputVolume());
                data.put(9, task.getTestingMethod());
                data.put(10, "");
                datalist.add(data);
                
                i++;
            }
            String[] heads = new String[] {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9"}; // 必须为列表头部所有位置集合，
                                                                                                        // 输出
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
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public String downloadPoolingData(InputStream inputStream, PoolingSubmitModel request)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
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
            AuthorizedUser user = userService.getByToken();
            dataMap.put("J5", user.getName());
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            List<PoolingSubmitTask> libCapSheet = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PoolingSubmitTask tst : libCapSheet)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, tst.getTestingCode());
                data.put(2, tst.getSampleCode());
                if (tst.isCaptureSample())
                {
                    if (StringUtils.isNotEmpty(tst.getProbeName()))
                    {
                        data.put(3, tst.getProbeName() + "-" + formatData(tst.getProbeDatasize()) + "G");
                    }
                }
                data.put(4, tst.getConcn());
                data.put(5, tst.getCtv());
                data.put(6, tst.getRqtv());
                data.put(7, tst.getTestingDatasize());
                data.put(8, formatData(tst.getSpecifiedRatio()));
                data.put(9, formatData(tst.getMixVolume()));
                data.put(10, tst.getTestingMethod());
                datalist.add(data);
                i++;
            }
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8"}; // 必须为列表头部所有位置集合，
                                                                                                        // 输出
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
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public String downloadJDDLData(InputStream inputStream, QtSubmitModel entity, BigDecimal concentration, BigDecimal libData)
    {
        Iterator it = entity.getReferences().iterator();
        while (it.hasNext())
        {
            QtAssignReference ref = (QtAssignReference)it.next();
            if (ref.getSampleCode().equals(entity.getSampleCode()))
            {
                it.remove();
            }
        }
        
        // 创建临时文件
        File tempFile = new File(entity.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", entity.getCode());
            dataMap.put("E5", entity.getAssignerName());
            dataMap.put("H5", dateFormater.format(entity.getAssignTime()));
            AuthorizedUser u = userService.getByToken();
            dataMap.put("K5", u.getName());
            dataMap.put("B6", entity.getDescription());
            excel.writeData(path, dataMap, wsheet);
            // 定量样本 插入excel
            String[] str = {"Qbit(ng/ul)", "QPCR(nM)", "2100"};
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (int j = 0; j < 3; j++)
            {
                Map<Integer, Object> data2 = new HashMap<Integer, Object>();
                data2.put(1, "定量样本");
                data2.put(2, entity.getSampleCode());
                data2.put(3, str[j]);
                data2.put(4, "");
                data2.put(5, "");
                if (null != libData)
                {
                    data2.put(6, libData);
                }
                if (null != concentration)
                {
                    data2.put(7, concentration.setScale(2, BigDecimal.ROUND_DOWN));
                }
                data2.put(8, "");
                data2.put(9, "");
                data2.put(10, "");
                data2.put(11, "");
                data2.put(12, "");
                datalist.add(data2);
            }
            
            // 参考样本插入excel
            int i = 3;
            List<QtAssignReference> references = entity.getReferences();
            
            for (QtAssignReference qr : references)
            {
                for (int j = 0; j < 3; j++)
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, "参考样本");
                    data.put(2, qr.getSampleCode());
                    data.put(3, str[j]);
                    data.put(4, "");
                    data.put(5, "");
                    data.put(6, qr.getFragmentLength());
                    data.put(7, qr.getConcn().setScale(2, BigDecimal.ROUND_DOWN));
                    data.put(8, qr.getCluster());
                    data.put(9, "");
                    data.put(10, "");
                    data.put(11, "");
                    data.put(12, qr.getTestingTime() == null ? "" : dateFormater.format(qr.getTestingTime()));
                    datalist.add(data);
                }
                i++;
            }
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7"}; // 必须为列表头部所有位置集合，
                                                                                                                    // 输出
            excel.writeDateList(path, heads, datalist, wsheet);
            excel.mergedRegion2(wsheet, 0, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion2(wsheet, 1, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 5, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 6, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 7, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 8, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 9, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            excel.mergedRegion3(wsheet, 10, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
            
            excel.mergedRegion3(wsheet, 11, wsheet.getFirstRowNum(), wsheet.getLastRowNum(), wbModule);
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
    public String downloadOnTestingData(InputStream inputStream, SequencingSubmitModel request, File zipfile, String taskId)
    {
        
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(oneTesting(request, inputStream)));
        srcfile.add(new File(sampleData(taskId)));
        srcfile.add(new File(detailData(taskId)));
        String zipFile = this.zipFiles(zipfile, srcfile);
        return zipFile;
        
    }
    
    private String detailData(String taskId)
    {
        
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/biology-analy_details_data_template.xlsx");
        String time = this.getFormatTime("yyyyMMdd", new Date());
        File file = new File("分析数据" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        
        ExcelUtil excel = new ExcelUtil();
        Workbook workbook;
        
        try
        {
            workbook = excel.getTempWorkbook(path);
            List<BiologyAnalyDetailsRecord> records = service.getDetailsRecords(taskId);
            String poolingCode = "";
            if (!CollectionUtils.isEmpty(records))
            {
                poolingCode = Collections3.getFirst(records).getPoolingCode();
                Sheet template = workbook.getSheetAt(0);
                os = new FileOutputStream(file);
                Sheet sheet = workbook.createSheet("分析数据任务表");
                int index = 0;
                Row row = sheet.createRow(index++);
                copyRow(row, template.getRow(0));
                
                int columns = row.getLastCellNum();
                
                for (int i = 0; i < columns; i++)
                {
                    sheet.setColumnWidth(i, template.getColumnWidth(i));
                }
                
                Row templateRow = template.getRow(1);
                
                for (BiologyAnalyDetailsRecord record : records)
                {
                    setText(cloneCell(sheet, index, 0, templateRow), StringUtils.isEmpty(record.getPoolingCode()) ? "" : record.getPoolingCode());
                    setText(cloneCell(sheet, index, 1, templateRow), StringUtils.isEmpty(record.getOrderCode()) ? "" : record.getOrderCode());
                    setText(cloneCell(sheet, index, 2, templateRow), StringUtils.isEmpty(record.getRecordCode()) ? "" : record.getRecordCode());
                    setText(cloneCell(sheet, index, 3, templateRow), StringUtils.isEmpty(record.getReceivedSampleCode()) ? "" : record.getReceivedSampleCode());
                    setText(cloneCell(sheet, index, 4, templateRow),
                        StringUtils.isEmpty(record.getReferenceSampleCode()) ? "" : record.getReferenceSampleCode());
                    setText(cloneCell(sheet, index, 5, templateRow),
                        StringUtils.isEmpty(record.getReferenceSampleType()) ? "" : record.getReferenceSampleType());
                    setText(cloneCell(sheet, index, 6, templateRow),
                        StringUtils.isEmpty(record.getReferencePoolingCode()) ? "" : record.getReferencePoolingCode());
                    setText(cloneCell(sheet, index, 7, templateRow), StringUtils.isEmpty(record.getLibrarySampleCode()) ? "" : record.getLibrarySampleCode());
                    setText(cloneCell(sheet, index, 8, templateRow), StringUtils.isEmpty(record.getLibraryIndex()) ? "" : record.getLibraryIndex());
                    setText(cloneCell(sheet, index, 9, templateRow), StringUtils.isEmpty(record.getCoordinate()) ? "" : record.getCoordinate());
                    setText(cloneCell(sheet, index, 10, templateRow), StringUtils.isEmpty(record.getExamineeSex()) ? "" : record.getExamineeSex());
                    setText(cloneCell(sheet, index, 11, templateRow), StringUtils.isEmpty(record.getSampleName()) ? "" : record.getSampleName());
                    setText(cloneCell(sheet, index, 12, templateRow), StringUtils.isEmpty(record.getSampleType()) ? "" : record.getSampleType());
                    setText(cloneCell(sheet, index, 13, templateRow), StringUtils.isEmpty(record.getTestingType()) ? "" : record.getTestingType());
                    setText(cloneCell(sheet, index, 14, templateRow), StringUtils.isEmpty(record.getExamineeName()) ? "" : record.getExamineeName());
                    setText(cloneCell(sheet, index, 15, templateRow), StringUtils.isEmpty(record.getContractCode()) ? "" : record.getContractCode());
                    setText(cloneCell(sheet, index, 16, templateRow), StringUtils.isEmpty(record.getRemark()) ? "" : record.getRemark());
                    setText(cloneCell(sheet, index, 17, templateRow), StringUtils.isEmpty(record.getProductCode()) ? "" : record.getProductCode());
                    setText(cloneCell(sheet, index, 18, templateRow), StringUtils.isEmpty(record.getProductName()) ? "" : record.getProductName());
                    setText(cloneCell(sheet, index, 19, templateRow), StringUtils.isEmpty(record.getFamilyRelation()) ? "本人" : record.getFamilyRelation());
                    setText(cloneCell(sheet, index, 20, templateRow), StringUtils.isEmpty(record.getExamineeDiagnosis()) ? "" : record.getExamineeDiagnosis());
                    setText(cloneCell(sheet, index, 21, templateRow), StringUtils.isEmpty(record.getExamineeGenes()) ? "" : record.getExamineeGenes());
                    setText(cloneCell(sheet, index, 22, templateRow), StringUtils.isEmpty(record.getExamineePhenotypes()) ? "" : record.getExamineePhenotypes());
                    setText(cloneCell(sheet, index, 23, templateRow), StringUtils.isEmpty(record.getMedicalHistory()) ? "" : record.getMedicalHistory());
                    setText(cloneCell(sheet, index, 24, templateRow),
                        StringUtils.isEmpty(record.getFamilyMedicalHistory()) ? "" : record.getFamilyMedicalHistory());
                    setText(cloneCell(sheet, index, 25, templateRow), StringUtils.isEmpty(record.getClinicalRecommend()) ? "" : record.getClinicalRecommend());
                    index++;
                }
                
                workbook.removeSheetAt(0);
                excel.writeAndClose(path, os);
                os.flush();
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    
    private String sampleData(String taskId)
    {
        
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/biology-analy_sample_data_template.xlsx");
        String time = this.getFormatTime("yyyyMMdd", new Date());
        File file = new File("拆分数据" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        
        ExcelUtil excel = new ExcelUtil();
        Workbook workbook;
        try
        {
            workbook = excel.getTempWorkbook(path);
            
            List<BiologyAnalySampleRecord> records = service.getSampleRecords(taskId);
            String poolingCode = "";
            if (!CollectionUtils.isEmpty(records))
            {
                poolingCode = Collections3.getFirst(records).getPoolingCode();
                Sheet template = workbook.getSheetAt(0);
                os = new FileOutputStream(file);
                Sheet sheet = workbook.createSheet("拆分数据任务表");
                int index = 0;
                Row row = sheet.createRow(index++);
                copyRow(row, template.getRow(0));
                
                int columns = row.getLastCellNum();
                
                for (int i = 0; i < columns; i++)
                {
                    sheet.setColumnWidth(i, template.getColumnWidth(i));
                }
                
                Row templateRow = template.getRow(1);
                
                for (BiologyAnalySampleRecord record : records)
                {
                    setText(cloneCell(sheet, index, 0, templateRow), StringUtils.isEmpty(record.getPoolingCode()) ? "" : record.getPoolingCode());
                    setText(cloneCell(sheet, index, 1, templateRow), StringUtils.isEmpty(record.getRecordCode()) ? "" : record.getRecordCode());
                    setText(cloneCell(sheet, index, 2, templateRow), StringUtils.isEmpty(record.getReceivedSampleCode()) ? "" : record.getReceivedSampleCode());
                    setText(cloneCell(sheet, index, 3, templateRow), StringUtils.isEmpty(record.getLibraryIndex()) ? "" : record.getLibraryIndex());
                    index++;
                }
                
                workbook.removeSheetAt(0);
                
                excel.writeAndClose(path, os);
                os.flush();
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return file.getAbsolutePath();
        
    }
    
    private String oneTesting(SequencingSubmitModel request, InputStream inputStream)
    {
        // 创建临时文件
        File tempFile = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
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
            dataMap.put("H5", dateFormater.format(request.getAssignTime()));
            AuthorizedUser u = userService.getByToken();
            dataMap.put("K5", u.getName());
            dataMap.put("B6", request.getDescription());
            dataMap.put("C8", request.getFirstDiluteConcn() + "nM 稀释");
            dataMap.put("G8", request.getSecondDiluteConcn() + "pM 稀释");
            dataMap.put("H8", request.getFinalConcn() + "pM 稀释");
            excel.writeData(path, dataMap, wsheet);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            Map<Integer, Object> data = new HashMap<Integer, Object>();
            data.put(1, request.getSampleCode());
            data.put(2, request.getConcn().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(3, request.getFirstDiluteSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(4, request.getFirstDiluteHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(5, request.getSecondDiluteSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(6, request.getSecondDiluteReagentInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(7, request.getSecondDiluteHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(8, request.getFinalSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(9, request.getFinalHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            datalist.add(data);
            String[] heads = new String[] {"A8", "B8", "C9", "D9", "E9", "F9", "G9", "H9", "I9"}; // 必须为列表头部所有位置集合，
                                                                                                  // 输出
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
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public String downloadBioinformaticAnalysis(String path, TestingSheet request)
    {
        OutputStream os = null;
        File file = null;
        // try
        // {
        //
        // ExcelUtil excel = new ExcelUtil();
        // Workbook wbModule = excel.getTempWorkbook(path);
        // Sheet wsheet = wbModule.getSheetAt(0);
        // String fileName = isXLSX(path, request.getCode());
        // file = new File(fileName);
        // os = new FileOutputStream(file);
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        // Map<String, Object> dataMap = new HashMap<String, Object>();
        // dataMap.put("B5", request.getCode());
        // dataMap.put("D5", request.getSendName());
        // dataMap.put("F5", dateFormater.format(request.getCreateTime()));
        // dataMap.put("H5", getUserName(request.getExecuter()));
        // dataMap.put("J5", "");
        // dataMap.put("B6", request.getDescription());
        // excel.writeData(path, dataMap, wsheet);
        //
        // List<TestingSheetTask> sheetTaskList =
        // request.getTestingSheetTaskList();
        // List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer,
        // Object>>();
        // for (TestingSheetTask tst : sheetTaskList)
        // {
        // Map<Integer, Object> data = new HashMap<Integer, Object>();
        // String sex = null;
        // // if ("1".equals(tst.getSex()))
        // // {
        // // sex = "男";
        // // }
        // // else
        // // {
        // // sex = "女";
        // // }
        // // data.put(1, request.getWkbhCode());
        // // data.put(2, tst.getTestingCode());
        // // data.put(3, tst.getOriginalCode());
        // // data.put(4, "");
        // // data.put(5, tst.getLibraryCode());
        // // data.put(6, tst.getConnectorId());
        // // data.put(7, tst.getCoordinate());
        // // data.put(8, tst.getProductName());
        // // data.put(9, tst.getSampleType());
        // // data.put(10, tst.getReceiveType());
        // // data.put(11, sex);
        // // data.put(12, tst.getInstitution());
        // // data.put(13, "");
        // // datalist.add(data);
        // }
        //
        // String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7",
        // "G7", "H7", "I7", "J7", "K7", "L7", "M7"}; // 必须为列表头部所有位置集合，
        // // 输出
        // // 数据单元格样式和头部单元格样式保持一致
        //
        // excel.writeDateList(path, heads, datalist, wsheet);
        //
        // // 写到输出流并移除资源
        // excel.writeAndClose(path, os);
        // os.flush();
        //
        // }
        // catch (FileNotFoundException e)
        // {
        //
        // e.printStackTrace();
        // }
        // catch (IOException e)
        // {
        //
        // e.printStackTrace();
        // }
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadAnalysisData(InputStream inputStream, TechnicalAnalySubmitModel request)
    {
        File file = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("L5", dateFormater.format(request.getAssignTime()));
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<TechnicalAnalyTask> taskList = request.getTasks();
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
                data.put(1, task.getSequencingCode());
                data.put(2, task.getProductCode());
                data.put(3, task.getMarketingCenter());
                data.put(4, task.getDataCode());
                data.put(5, task.getReceivedSampleName());
                if ("0".equals(task.getSex()))
                {
                    data.put(6, "男");
                }
                else if ("1".equals(task.getSex()))
                {
                    data.put(6, "女");
                }
                else
                {
                    data.put(6, task.getSex());
                }
                
                data.put(7, task.getCaseNum());
                data.put(8, task.getProductName());
                data.put(9, sendDate);
                data.put(10, task.getReceivedSampleCode());
                data.put(11, task.getAge());
                data.put(12, task.getCustomerCompanyName());
                data.put(13, task.getMethodName());
                data.put(14, reportDate);
                data.put(15, task.getProbeName());
                data.put(16, task.getClinicalDiagnosis());
                data.put(17, task.getFocusGenes());
                data.put(18, task.getCaseSummary());
                data.put(19, task.getFamilyHistorySummary());
                data.put(20, task.getSampleTypeName());
                data.put(21, task.getCustomerName());
                data.put(22, task.getBusinessLeader());
                data.put(23, task.getTechnicalPrincipal());
                data.put(24, task.getOrderCode());
                data.put(25, task.getContractType());
                data.put(26, task.getRemark());
                data.put(27, task.getFamilyRelation());
                data.put(28, task.getCheckedCount());
                datalist.add(data);
            }
            
            String[] heads =
                new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7", "N7", "O7", "P7", "Q7", "R7", "S7", "T7", "U7",
                    "V7", "W7", "X7", "Y7", "Z7", "[7", "\\7"}; // 必须为列表头部所有位置集合，
                                                                // 输出
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
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadPrimerDesignData(InputStream inputStream, PrimerDesignSheetModel request)
    {
        File file = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            String time = getFormatTime("yyyy/MM/dd", request.getAssignTime());
            AuthorizedUser user = userService.getByToken();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            dataMap.put("H5", user.getName());
            dataMap.put("K5", time);
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<PrimerDesignTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 1;
            for (PrimerDesignTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, i);
                data.put(2, task.getGene());
                data.put(3, task.getCodingExon());
                data.put(4, task.getChromosomeNumber());
                data.put(5, task.getChromosomeLocation());
                data.put(6, task.getPcrStartPoint() == null ? "" : String.valueOf(task.getPcrStartPoint()));
                data.put(7, task.getPcrEndPoint() == null ? "" : String.valueOf(task.getPcrEndPoint()));
                data.put(8, "");
                data.put(9, task.getForwardPrimerSequence());
                data.put(10, "");
                data.put(11, task.getReversePrimerSequence());
                data.put(12, "");
                data.put(13, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7"}; // 必须为列表头部所有位置集合，
                                                                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    @Override
    public String downloadPcrNgsPrimerDesignData(InputStream inputStream, PcrNgsPrimerDesignSheetModel request)
    {
        File file = new File(request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            String time = getFormatTime("yyyy/MM/dd", request.getAssignTime());
            AuthorizedUser user = userService.getByToken();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", request.getCode());
            dataMap.put("E5", request.getAssignerName());
            dataMap.put("H5", user.getName());
            dataMap.put("K5", time);
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsPrimerDesignTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 1;
            for (PcrNgsPrimerDesignTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, i);
                data.put(2, task.getGene());
                data.put(3, task.getCodingExon());
                data.put(4, task.getChromosomeNumber());
                data.put(5, task.getChromosomeLocation());
                data.put(6, task.getPcrStartPoint() == null ? "" : String.valueOf(task.getPcrStartPoint()));
                data.put(7, task.getPcrEndPoint() == null ? "" : String.valueOf(task.getPcrEndPoint()));
                data.put(8, "");
                data.put(9, "");
                data.put(10, "");
                data.put(11, "");
                data.put(12, "");
                data.put(13, "");
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7"}; // 必须为列表头部所有位置集合，
                                                                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR任务表
    public String downloadFirstPCRTasks(FirstPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/firstpcr/FIRST_PCR_TASKS.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_一次PCR任务表_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("H5", getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", user.getName());
            dataMap.put("E6", request.getReagentKitName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (FirstPCRTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getPcrTestCode());
                data.put(4, task.getSampleLocationTemp());
                data.put(5, task.getSampleCode());
                data.put(6, task.getForwardPrimerLocationTemp());
                data.put(7, task.getForwardPrimerCode());
                data.put(8, task.getConcentration());
                data.put(9, formatData(task.getVolume()));
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8"}; // 必须为列表头部所有位置集合，
                                                                                                  // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR任务表
    public String downloadFirstPCRSangerTasks(FirstPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sangerTest/firstpcr/FIRST_PCR_TASKS.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_一次PCR任务表_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("H5", getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", user.getName());
            dataMap.put("E6", request.getReagentKitName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (FirstPCRSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getPcrTestCode());
                data.put(4, task.getSampleLocationTemp());
                data.put(5, task.getSampleCode());
                data.put(6, task.getForwardPrimerLocationTemp());
                data.put(7, task.getForwardPrimerCode());
                data.put(8, task.getConcentration());
                data.put(9, formatData(task.getVolume()));
                data.put(10, task.getClinicalRecommend());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8"}; // 必须为列表头部所有位置集合，
                                                                                                        // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR任务表
    public String downloadPcrNgsTasks(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_TASKS.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR-NGS任务表_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("H5", user.getName());
            dataMap.put("K5", getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", request.getReagentKitName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getCombineCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getPcrTestCode());
                data.put(4, task.getSampleLocationTemp());
                data.put(5, task.getSampleCode());
                data.put(6, task.getForwardPrimerLocationTemp());
                data.put(7, task.getForwardPrimerCode());
                data.put(8, task.getConcentration());
                data.put(9, formatData(task.getVolume()));
                data.put(10, task.getLibraryIndex());
                data.put(11, task.getLibraryIndexLocation());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "K8"}; // 必须为列表头部所有位置集合，
                                                                                                              // 输出
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
        return file.getAbsolutePath();
    }
    
    // mlpa任务表
    public String downloadMlpaTasks(MlpaTestSubmitModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/mlpa/mlpa_test.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File("MLPA任务单" + "_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("H5", getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", user.getName());
            dataMap.put("E6", request.getReagentKitName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<MlpaTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (MlpaTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getTestCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getSampleCode());
                data.put(4, task.getConcentration());
                data.put(5, task.getRatio2628());
                data.put(6, task.getRatio2623());
                data.put(7, task.getAddSampleVolume());
                data.put(8, task.getAddWaterVolume());
                data.put(9, task.getAttrType().intValue() == 1 ? "待测" : "对照");
                data.put(10, task.getProbe());
                data.put(11, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "K8"}; // 必须为列表头部所有位置集合，
                                                                                                              // 输出
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
        return file.getAbsolutePath();
    }
    
    // mlpa上机任务表
    public String downloadMlpaOnTest(MlpaTestSubmitModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/mlpa/mlpa_pc_test.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(time + "_" + request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("A2", time + "_" + request.getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<MlpaTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            List<MlpaPcTestCodeModel> listResult = Lists.newArrayList();
            Map<String, List<MlpaTestTask>> map = Maps.newHashMap();
            for (MlpaTestTask task : taskList)
            {
                String probe = task.getProbe();
                List<MlpaTestTask> ms = Lists.newArrayList();
                if (map.containsKey(probe))
                {
                    ms = map.get(probe);
                    ms.add(task);
                    map.put(probe, ms);
                }
                else
                {
                    ms.add(task);
                    map.put(probe, ms);
                }
            }
            Set<String> keySet = Sets.newHashSet();
            if (Collections3.isNotEmpty(map.keySet()))
            {
                keySet = map.keySet();
            }
            List<String> keyList = keySet.stream().collect(Collectors.toList());
            keyList.sort((h1, h2) -> h1.compareTo(h2));
            
            int start = 0;
            String startStr = "";
            for (String key : keyList)
            {
                List<MlpaTestTask> temp = map.get(key);
                int tempSize = temp.size();
                int addSize = 0;
                if (tempSize > 0)
                {
                    int val = tempSize / 8;
                    int mod = tempSize % 8;
                    if (mod != 0)
                    {
                        addSize = 8 - mod;
                    }
                }
                for (int i = 0; i < addSize; i++)
                {
                    temp.add(new MlpaTestTask(key));
                }
                String testCode = "";
                String comName = "";
                MlpaPcTestCodeModel mlpaPcTestCodeModel;
                for (int i = 0; i < temp.size(); i++)
                {
                    MlpaTestTask mlpaTestTask = temp.get(i);
                    mlpaPcTestCodeModel = new MlpaPcTestCodeModel();
                    Map<String, String> mapCodeStart = getMlpaCode(start, i + 1);
                    testCode = mapCodeStart.get("code");
                    startStr = mapCodeStart.get("start");
                    mlpaPcTestCodeModel.setTestCode(testCode);
                    if (StringUtils.isNotEmpty(mlpaTestTask.getSampleCode()))
                    {
                        comName = request.getCode() + "_" + mlpaTestTask.getSampleCode() + "_" + mlpaTestTask.getProbe();
                    }
                    else
                    {
                        if (StringUtils.isNotEmpty(mlpaTestTask.getTestCode()))
                        {
                            comName = "2";
                        }
                        else
                        {
                            comName = "1";
                        }
                        
                    }
                    mlpaPcTestCodeModel.setCombineCode(comName);
                    listResult.add(mlpaPcTestCodeModel);
                }
                if (StringUtils.isNotEmpty(startStr))
                {
                    start = Integer.valueOf(startStr);
                }
            }
            
            for (MlpaPcTestCodeModel mlpaPcTestCodeModel : listResult)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, mlpaPcTestCodeModel.getTestCode());
                data.put(2, mlpaPcTestCodeModel.getCombineCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A5", "B5"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    // DT任务表
    public String downloadDTTasks(DTSubmitModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/dt/dt_test.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File("动态突变任务单" + "_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("H5", getFormatTime("yyyy/MM/dd", request.getAssignTime()));
            dataMap.put("B6", user.getName());
            dataMap.put("E6", request.getReagentKitName());
            dataMap.put("B7", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<DTTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (DTTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getTestCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getSampleCode());
                data.put(4, task.getPrimers());
                data.put(5, task.getConcentration());
                data.put(6, task.getRatio2628());
                data.put(7, task.getAddSampleVolume());
                data.put(8, task.getAddWaterVolume());
                data.put(9, task.getProducts());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8"}; // 必须为列表头部所有位置集合，
                                                                                                  // 输出
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
        return file.getAbsolutePath();
    }
    
    // DT上机任务表
    public String downloadDTOnTest(DTSubmitModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/dt/dt_pc_test.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(time + "_" + request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
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
            dataMap.put("A2", time + "_" + request.getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<DTTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            List<MlpaPcTestCodeModel> listResult = Lists.newArrayList();
            Map<String, List<DTTask>> map = Maps.newHashMap();
            for (DTTask task : taskList)
            {
                String primer = task.getPrimers();
                List<DTTask> ms = Lists.newArrayList();
                if (map.containsKey(primer))
                {
                    ms = map.get(primer);
                    ms.add(task);
                    map.put(primer, ms);
                }
                else
                {
                    ms.add(task);
                    map.put(primer, ms);
                }
            }
            Set<String> keySet = Sets.newHashSet();
            if (Collections3.isNotEmpty(map.keySet()))
            {
                keySet = map.keySet();
            }
            List<String> keyList = keySet.stream().collect(Collectors.toList());
            keyList.sort((h1, h2) -> h1.compareTo(h2));
            
            int start = 0;
            String startStr = "";
            for (String key : keyList)
            {
                List<DTTask> temp = map.get(key);
                int tempSize = temp.size();
                int addSize = 0;
                if (tempSize > 0)
                {
                    int val = tempSize / 8;
                    int mod = tempSize % 8;
                    if (mod != 0)
                    {
                        addSize = 8 - mod;
                    }
                }
                for (int i = 0; i < addSize; i++)
                {
                    temp.add(new DTTask(key));
                }
                String testCode = "";
                String comName = "";
                MlpaPcTestCodeModel mlpaPcTestCodeModel;
                for (int i = 0; i < temp.size(); i++)
                {
                    mlpaPcTestCodeModel = new MlpaPcTestCodeModel();
                    Map<String, String> mapCodeStart = getMlpaCode(start, i + 1);
                    testCode = mapCodeStart.get("code");
                    startStr = mapCodeStart.get("start");
                    mlpaPcTestCodeModel.setTestCode(testCode);
                    if (StringUtils.isNotEmpty(temp.get(i).getSampleCode()))
                    {
                        comName = request.getCode() + "_" + temp.get(i).getSampleCode() + "_" + temp.get(i).getPrimers();
                    }
                    else
                    {
                        comName = "1";
                    }
                    mlpaPcTestCodeModel.setCombineCode(comName);
                    listResult.add(mlpaPcTestCodeModel);
                }
                if (StringUtils.isNotEmpty(startStr))
                {
                    start = Integer.valueOf(startStr);
                }
            }
            
            for (MlpaPcTestCodeModel mlpaPcTestCodeModel : listResult)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, mlpaPcTestCodeModel.getTestCode());
                data.put(2, mlpaPcTestCodeModel.getCombineCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A5", "B5"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR结果上传
    public String downloadFirstPCRResult(FirstPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/firstpcr/FIRST_PCR_RESULT.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_一次PCR结果上传_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            String code = request.getCode();
            for (FirstPCRTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, code);
                data.put(2, task.getPcrTestCode());
                data.put(3, task.getCombineCode());
                data.put(4, "");
                data.put(5, "");
                data.put(6, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1"}; // 必须为列表头部所有位置集合，
                                                                                // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR结果上传
    public String downloadFirstPCRSangerResult(FirstPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sangerTest/firstpcr/FIRST_PCR_RESULT.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_一次PCR结果上传_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            String code = request.getCode();
            for (FirstPCRSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, code);
                data.put(2, task.getPcrTestCode());
                data.put(3, task.getCombineCode());
                data.put(4, "");
                data.put(5, "");
                data.put(6, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1"}; // 必须为列表头部所有位置集合，
                                                                                // 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadPcrNgsResult(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_RESULT.xlsx");
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR-NGS结果上传_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            String code = request.getCode();
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                // data.put(1, code);
                // data.put(2, task.getPcrTestCode());
                data.put(1, task.getCombineCode());
                data.put(2, "");
                data.put(3, "");
                data.put(4, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1"}; // 必须为列表头部所有位置集合，
                                                                    // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR-Template
    public String downloadFirstPCRTemplate(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR_Template_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append("Source Well")
                .append(",")
                .append("Des Plate")
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRTask task : taskList)
            {
                String location = task.getSampleLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(task.getVolume() == null ? "" : formatData(task.getVolume()))
                    .append(",")
                    .append("\r");
                
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerTemplate(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        
        File file = new File(request.getCode() + "_PCR_Template_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append("Source Well")
                .append(",")
                .append("Des Plate")
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRSangerTask task : taskList)
            {
                String location = task.getSampleLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(task.getVolume() == null ? "" : formatData(task.getVolume()))
                    .append(",")
                    .append("\r");
                
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadPcrNgsTemplate(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_TEMPLATE.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        
        File file = new File(request.getCode() + "_PCR_Template_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getSampleLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "PCR");
                data.put(4, codeList.get(i));
                data.put(5, task.getVolume() == null ? "" : formatData(task.getVolume()));
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR反向引物架
    public String downloadFirstPCRReversePrimer(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR反向引物架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("引物临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("引物编号")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<FirstPCRTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRTask task : taskList)
                {
                    primerStr = task.getReversePrimerLocationTemp() + "-" + task.getReversePrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRTask task : checkTaskList)
            {
                content.append(task.getReversePrimerLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getReversePrimerCode()) ? "" : task.getReversePrimerCode())
                    .append(",")
                    .append("\r");
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerReversePrimer(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR反向引物架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("引物临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("引物编号")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<FirstPCRSangerTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRSangerTask task : taskList)
                {
                    primerStr = task.getReversePrimerLocationTemp() + "-" + task.getReversePrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRSangerTask task : checkTaskList)
            {
                content.append(task.getReversePrimerLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getReversePrimerCode()) ? "" : task.getReversePrimerCode())
                    .append(",")
                    .append("\r");
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadPcrNgsReversePrimer(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_REVERSE_PRIMER.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR反向引物架_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<PcrNgsTestTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (PcrNgsTestTask task : taskList)
                {
                    primerStr = task.getReversePrimerLocationTemp() + "-" + task.getReversePrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsTestTask task : checkTaskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getReversePrimerLocationTemp());
                data.put(2, task.getReversePrimerCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR正向引物架
    public String downloadFirstPCRForwardPrimer(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR正向引物架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("引物临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("引物编号")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<FirstPCRTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRTask task : taskList)
                {
                    primerStr = task.getForwardPrimerLocationTemp() + "-" + task.getForwardPrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRTask task : checkTaskList)
            {
                content.append(task.getForwardPrimerLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getForwardPrimerCode()) ? "" : task.getForwardPrimerCode())
                    .append(",")
                    .append("\r");
            }
            
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    // 一次PCR正向引物架
    public String downloadFirstPCRSangerForwardPrimer(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR正向引物架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("引物临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("引物编号")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<FirstPCRSangerTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRSangerTask task : taskList)
                {
                    primerStr = task.getForwardPrimerLocationTemp() + "-" + task.getForwardPrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRSangerTask task : checkTaskList)
            {
                content.append(task.getForwardPrimerLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getForwardPrimerCode()) ? "" : task.getForwardPrimerCode())
                    .append(",")
                    .append("\r");
            }
            
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadPcrNgsForwardPrimer(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_FORWARD_PRIMER.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR正向引物架_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<PcrNgsTestTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (PcrNgsTestTask task : taskList)
                {
                    primerStr = task.getForwardPrimerLocationTemp() + "-" + task.getForwardPrimerCode();
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsTestTask task : checkTaskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getForwardPrimerLocationTemp());
                data.put(2, task.getForwardPrimerCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR样本架
    public String downloadFirstPCRSample(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR样本架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("样本临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("样本编号")
                .append(",")
                .append("存储位置")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<FirstPCRTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRTask task : taskList)
                {
                    primerStr = task.getSampleLocationTemp() + "-" + task.getSampleCode();
                    
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRTask task : checkTaskList)
            {
                logger.info(task.getId() + "~~~" + task.getDnaLocation());
                content.append(task.getSampleLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getSampleCode()) ? "" : task.getSampleCode())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getDnaLocation()) ? "" : task.getDnaLocation())
                    .append(",")
                    .append("\r");
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerSample(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR样本架_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "GB2312");
            bw = new BufferedWriter(osw);
            
            content.append("样本临时位置")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("\r")
                .append("位置")
                .append(",")
                .append("样本编号")
                .append(",")
                .append("存储位置")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<FirstPCRSangerTask> checkTaskList = Lists.newArrayList();
            List<String> primerList = Lists.newArrayList();
            String primerStr = "";
            if (Collections3.isNotEmpty(taskList))
            {
                for (FirstPCRSangerTask task : taskList)
                {
                    primerStr = task.getSampleLocationTemp() + "-" + task.getSampleCode();
                    
                    if (!primerList.contains(primerStr))
                    {
                        checkTaskList.add(task);
                        primerList.add(primerStr);
                    }
                }
            }
            
            for (FirstPCRSangerTask task : checkTaskList)
            {
                content.append(task.getSampleLocationTemp())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getSampleCode()) ? "" : task.getSampleCode())
                    .append(",")
                    .append(StringUtils.isEmpty(task.getDnaLocation()) ? "" : task.getDnaLocation())
                    .append(",")
                    .append("\r");
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    // 一次PCR样本架
    public String downloadPcrNgsSample(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_SAMPLE.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_PCR样本架_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getSampleLocationTemp());
                data.put(2, task.getSampleCode());
                data.put(3, task.getDnaLocation());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2", "C2"}; // 必须为列表头部所有位置集合，
                                                              // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR_primers_2PCR
    public String downloadFirstPCRPrimers2PCR(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_2PCR_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (FirstPCRTask task : taskList)
            {
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("2X PCR Primer")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(2)
                    .append(",")
                    .append("\r");
                
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerPrimers2PCR(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_2PCR_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRSangerTask task : taskList)
            {
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("2X PCR Primer")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(2)
                    .append(",")
                    .append("\r");
                
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadPcrNgsPrimers2PCR(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_PRIMERS_2PCR.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_2PCR_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "2X PCR Primer");
                data.put(4, codeList.get(i));
                data.put(5, 2);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR_primers_F
    public String downloadFirstPCRPrimersF(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_F_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRTask task : taskList)
            {
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(1)
                    .append(",")
                    .append("\r");
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerPrimersF(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_F_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRSangerTask task : taskList)
            {
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(1)
                    .append(",")
                    .append("\r");
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadPcrNgsPrimersF(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_PRIMERS_F.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_F_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "PCR");
                data.put(4, codeList.get(i));
                data.put(5, 1);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 一次PCR_primers_R
    public String downloadFirstPCRPrimersR(FirstPCRSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_R_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRTask task : taskList)
            {
                String location = task.getReversePrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(1)
                    .append(",")
                    .append("\r");
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    public String downloadFirstPCRSangerPrimersR(FirstPCRSangerSheetModel request)
    {
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_R_" + time + ".csv");
        
        FileOutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        StringBuffer content = new StringBuffer(1024);
        try
        {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            
            content.append("Source Plate")
                .append(",")
                .append(request.getCode())
                .append(",")
                .append(getFormatTime("MM/dd/yyyy", request.getAssignTime()))
                .append(",")
                .append("Des Well")
                .append(",")
                .append("Vol")
                .append(",")
                .append("\r");
            
            List<FirstPCRSangerTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            int i = 0;
            for (FirstPCRSangerTask task : taskList)
            {
                String location = task.getReversePrimerLocationTemp();
                int index = location.lastIndexOf("-");
                
                content.append(location.substring(0, index))
                    .append(",")
                    .append(location.substring(index + 1))
                    .append(",")
                    .append("PCR")
                    .append(",")
                    .append(codeList.get(i))
                    .append(",")
                    .append(1)
                    .append(",")
                    .append("\r");
                i++;
            }
            bw.write(content.toString());
            bw.flush();
            bw.close();
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
    
    // 一次PCR_primers_R
    public String downloadPcrNgsPrimersR(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_PRIMERS_R.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_primers_R_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getReversePrimerLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "PCR");
                data.put(4, codeList.get(i));
                data.put(5, 1);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadPcrNgsIndexSample(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_INDEX_SAMPLE.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_index样本_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getLibraryIndexLocation());
                data.put(2, task.getLibraryIndex());
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadPcrNgsIndex(PcrNgsTestSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/pcrngs/pcrngstest/PCR_NGS_TEST_INDEX.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_index-PCR_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<PcrNgsTestTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (PcrNgsTestTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getLibraryIndexLocation();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "2X PCR Primer");
                data.put(4, codeList.get(i));
                data.put(5, 1);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 压缩成zip文件
    @Override
    public String zipFiles(File zipfile, FirstPCRSheetModel request)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadFirstPCRTasks(request)));
        srcfile.add(new File(downloadFirstPCRResult(request)));
        srcfile.add(new File(downloadFirstPCRTemplate(request)));
        srcfile.add(new File(downloadFirstPCRForwardPrimer(request)));
        srcfile.add(new File(downloadFirstPCRReversePrimer(request)));
        srcfile.add(new File(downloadFirstPCRSample(request)));
        srcfile.add(new File(downloadFirstPCRPrimers2PCR(request)));
        srcfile.add(new File(downloadFirstPCRPrimersF(request)));
        srcfile.add(new File(downloadFirstPCRPrimersR(request)));
        
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
    
    @Override
    public String zipPcrOneSangerFiles(File zipfile, FirstPCRSangerSheetModel request)
    {
        
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadFirstPCRSangerTasks(request)));
        srcfile.add(new File(downloadFirstPCRSangerResult(request)));
        srcfile.add(new File(downloadFirstPCRSangerTemplate(request)));
        srcfile.add(new File(downloadFirstPCRSangerForwardPrimer(request)));
        srcfile.add(new File(downloadFirstPCRSangerReversePrimer(request)));
        srcfile.add(new File(downloadFirstPCRSangerSample(request)));
        srcfile.add(new File(downloadFirstPCRSangerPrimers2PCR(request)));
        srcfile.add(new File(downloadFirstPCRSangerPrimersF(request)));
        srcfile.add(new File(downloadFirstPCRSangerPrimersR(request)));
        
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
    
    @Override
    public String zipPcrNgsFiles(File zipfile, PcrNgsTestSheetModel request)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadPcrNgsTasks(request)));
        srcfile.add(new File(downloadPcrNgsResult(request)));
        srcfile.add(new File(downloadPcrNgsTemplate(request)));
        srcfile.add(new File(downloadPcrNgsForwardPrimer(request)));
        srcfile.add(new File(downloadPcrNgsReversePrimer(request)));
        srcfile.add(new File(downloadPcrNgsSample(request)));
        srcfile.add(new File(downloadPcrNgsPrimersF(request)));
        srcfile.add(new File(downloadPcrNgsPrimersR(request)));
        srcfile.add(new File(downloadPcrNgsIndexSample(request)));
        srcfile.add(new File(downloadPcrNgsIndex(request)));
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
    
    @Override
    public String zipMlpaFiles(File zipfile, MlpaTestSubmitModel request)
    {
        
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadMlpaTasks(request)));
        srcfile.add(new File(downloadMlpaOnTest(request)));
        
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
    
    @Override
    public String zipDTFiles(File zipfile, DTSubmitModel request)
    {
        
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadDTTasks(request)));
        srcfile.add(new File(downloadDTOnTest(request)));
        
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
    
    // 二次PCR测序引物架
    public String downloadSecondPCRSequencePrimers(SecondPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_SEQUENCE_PRIMERS.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_测序引物架_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<SecondPCRTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (SecondPCRTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getForwardPrimerLocationTemp());
                data.put(2, task.getForwardPrimerCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadSecondPCRSangerSequencePrimers(SecondPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_SEQUENCE_PRIMERS.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_测序引物架_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<SecondPCRSangerTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (SecondPCRSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getForwardPrimerLocationTemp());
                data.put(2, task.getForwardPrimerCode());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A2", "B2"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    // 二次PCR Primers-2PCR
    public String downloadSecondPCRPrimers2PCR(SecondPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_PRIMERS-2PCR.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_Primers-2PCR_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<SecondPCRTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (SecondPCRTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "Dwen_PCR1");
                data.put(4, codeList.get(i));
                data.put(5, 0.33);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadSecondPCRSangerPrimers2PCR(SecondPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_PRIMERS-2PCR.xlsx");
        
        String time = getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_Primers-2PCR_" + time + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B1", request.getCode());
            dataMap.put("C1", getFormatTime("MM/dd/yyyy", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<SecondPCRSangerTask> taskList = request.getTasks();
            List<String> codeList = getDesWel();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (SecondPCRSangerTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                String location = task.getForwardPrimerLocationTemp();
                int index = location.lastIndexOf("-");
                data.put(1, location.substring(0, index));
                data.put(2, location.substring(index + 1));
                data.put(3, "Dwen_PCR1");
                data.put(4, codeList.get(i));
                data.put(5, 0.33);
                datalist.add(data);
                i++;
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1"}; // 必须为列表头部所有位置集合，
                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    // 二次PCR实验安排表
    public String downloadSecondPCRTaskSchedule(SecondPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_TASK_SCHEDULE.xlsx");
        File file = new File(request.getCode() + "_二次PCR实验安排表.xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("A2", "实验日期：" + getFormatTime("yyyy-MM-dd", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<SixNineInfoModel> taskList = request.getSixNineModelList();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (SixNineInfoModel model : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getColumeNum());
                data.put(2, model.getLineNum().get(0));
                data.put(3, model.getLineNum().get(1));
                data.put(4, model.getLineNum().get(2));
                data.put(5, model.getLineNum().get(3));
                data.put(6, model.getLineNum().get(4));
                data.put(7, model.getLineNum().get(5));
                data.put(8, model.getLineNum().get(6));
                data.put(9, model.getLineNum().get(7));
                data.put(10, model.getLineNum().get(8));
                data.put(11, model.getLineNum().get(9));
                data.put(12, model.getLineNum().get(10));
                data.put(13, model.getLineNum().get(11));
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "K5", "L5", "M5"}; // 必须为列表头部所有位置集合，
                                                                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadSecondPCRSangerTaskSchedule(SecondPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_TASK_SCHEDULE.xlsx");
        File file = new File(request.getCode() + "_二次PCR实验安排表.xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("A2", "实验日期：" + getFormatTime("yyyy-MM-dd", request.getAssignTime()));
            excel.writeData(path, dataMap, wsheet);
            
            List<com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SixNineInfoModel> taskList = request.getSixNineModelList();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SixNineInfoModel model : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, model.getColumeNum());
                data.put(2, model.getLineNum().get(0));
                data.put(3, model.getLineNum().get(1));
                data.put(4, model.getLineNum().get(2));
                data.put(5, model.getLineNum().get(3));
                data.put(6, model.getLineNum().get(4));
                data.put(7, model.getLineNum().get(5));
                data.put(8, model.getLineNum().get(6));
                data.put(9, model.getLineNum().get(7));
                data.put(10, model.getLineNum().get(8));
                data.put(11, model.getLineNum().get(9));
                data.put(12, model.getLineNum().get(10));
                data.put(13, model.getLineNum().get(11));
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "K5", "L5", "M5"}; // 必须为列表头部所有位置集合，
                                                                                                                          // 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadSecondPCRTasks(SecondPCRSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_TASKS.xlsx");
        
        String time = getFormatTime("yyyy-MM-dd", new Date());
        File file = new File(time + "_二次PCR任务单" + "-" + request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("A2", time + "-" + request.getCode());
            dataMap.put("B2", time + "-" + request.getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<SixNineInfoModel> taskList = request.getSixNineModelList();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (int i = 0; i < 12; i++)
            {
                for (SixNineInfoModel model : taskList)
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, model.getCombineCodeList().get(i));
                    datalist.add(data);
                }
            }
            
            String[] heads = new String[] {"B5"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    public String downloadSecondPCRSangerTasks(SecondPCRSangerSheetModel request)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/sanger/secondpcr/SECOND_PCR_TASKS.xlsx");
        
        String time = getFormatTime("yyyy-MM-dd", new Date());
        File file = new File(time + "_二次PCR任务单" + "-" + request.getCode() + ".xlsx");
        inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("A2", time + "-" + request.getCode());
            dataMap.put("B2", time + "-" + request.getCode());
            excel.writeData(path, dataMap, wsheet);
            
            List<com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SixNineInfoModel> taskList = request.getSixNineModelList();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (int i = 0; i < 12; i++)
            {
                for (com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SixNineInfoModel model : taskList)
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, model.getCombineCodeList().get(i));
                    datalist.add(data);
                }
            }
            
            String[] heads = new String[] {"B5"}; // 必须为列表头部所有位置集合， 输出
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
        return file.getAbsolutePath();
    }
    
    @Override
    public String zipFilesPCR2(File zipfile, SecondPCRSheetModel request)
    {
        List<File> srcfile = Lists.newArrayList();
        List<SecondPCRTask> tasks = request.getTasks();
        Integer flag = 1;
        if (Collections3.isNotEmpty(tasks))
        {
            for (SecondPCRTask task : tasks)
            {
                if (null != task && null != task.getFlag())
                {
                    flag = task.getFlag();
                    break;
                }
            }
        }
        if (flag == 2)
        {
            srcfile.add(new File(downloadSecondPCRSequencePrimers(request)));
            srcfile.add(new File(downloadSecondPCRPrimers2PCR(request)));
        }
        srcfile.add(new File(downloadSecondPCRTaskSchedule(request)));
        srcfile.add(new File(downloadSecondPCRTasks(request)));
        
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
    
    @Override
    public String zipFilesPCRSanger2(File zipfile, SecondPCRSangerSheetModel request)
    {
        List<File> srcfile = Lists.newArrayList();
        List<SecondPCRSangerTask> tasks = request.getTasks();
        Integer flag = 1;
        if (Collections3.isNotEmpty(tasks))
        {
            flag = tasks.get(0).getFlag();
        }
        if (flag == 2)
        {
            srcfile.add(new File(downloadSecondPCRSangerSequencePrimers(request)));
            srcfile.add(new File(downloadSecondPCRSangerPrimers2PCR(request)));
        }
        srcfile.add(new File(downloadSecondPCRSangerTaskSchedule(request)));
        srcfile.add(new File(downloadSecondPCRSangerTasks(request)));
        
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
    
    @Override
    public String downloadReportCreate(String path, TestingSheet request)
    {
        OutputStream os = null;
        File file = null;
        // try
        // {
        //
        // ExcelUtil excel = new ExcelUtil();
        // Workbook wbModule = excel.getTempWorkbook(path);
        // Sheet wsheet = wbModule.getSheetAt(0);
        // String fileName = isXLSX(path, request.getCode());
        // file = new File(fileName);
        // os = new FileOutputStream(file);
        // SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        // Map<String, Object> dataMap = new HashMap<String, Object>();
        // dataMap.put("B5", request.getCode());
        // dataMap.put("D5", request.getSendName());
        // dataMap.put("F5", dateFormater.format(request.getCreateTime()));
        // dataMap.put("H5", getUserName(request.getExecuter()));
        // dataMap.put("J5", "");
        // dataMap.put("B6", request.getDescription());
        // excel.writeData(path, dataMap, wsheet);
        //
        // List<TestingSheetTask> sheetTaskList =
        // request.getTestingSheetTaskList();
        // List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer,
        // Object>>();
        // for (TestingSheetTask tst : sheetTaskList)
        // {
        // Map<Integer, Object> data = new HashMap<Integer, Object>();
        // String sex = null;
        // // if ("1".equals(tst.getSex()))
        // // {
        // // sex = "男";
        // // }
        // // else
        // // {
        // // sex = "女";
        // // }
        // // data.put(1, request.getWkbhCode());
        // // data.put(2, tst.getTestingCode());
        // // data.put(3, tst.getOriginalCode());
        // // data.put(4, "");
        // // data.put(5, tst.getLibraryCode());
        // // data.put(6, tst.getConnectorId());
        // // data.put(7, tst.getCoordinate());
        // // data.put(8, tst.getProductName());
        // // data.put(9, tst.getSampleType());
        // // data.put(10, tst.getReceiveType());
        // // data.put(11, sex);
        // // data.put(12, tst.getInstitution());
        // // data.put(13, "");
        // // datalist.add(data);
        // }
        //
        // String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7",
        // "G7", "H7", "I7", "J7", "K7", "L7", "M7"}; // 必须为列表头部所有位置集合，
        // // 输出
        // // 数据单元格样式和头部单元格样式保持一致
        //
        // excel.writeDateList(path, heads, datalist, wsheet);
        //
        // // 写到输出流并移除资源
        // excel.writeAndClose(path, os);
        // os.flush();
        //
        // }
        // catch (FileNotFoundException e)
        // {
        //
        // e.printStackTrace();
        // }
        // catch (IOException e)
        // {
        //
        // e.printStackTrace();
        // }
        return file.getAbsolutePath();
    }
    
    @Override
    public String getValue(MultipartFile file, int stratRow, int column)
    {
        Workbook wb = null;
        try
        {
            wb = this.getWorkbook(file.getInputStream(), file.getOriginalFilename());
            if (null == wb)
            {
                throw new Exception("创建Excel工作薄为空！");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        return (String)getCellValue(sheet.getRow(stratRow).getCell(column));
    }
    
    @Override
    public List<String> uploadData(MultipartFile file, int stratRow, int column)
    {
        
        List<String> inAmounts = new ArrayList<String>();
        
        Workbook wb = null;
        try
        {
            wb = this.getWorkbook(file.getInputStream(), file.getOriginalFilename());
            if (null == wb)
            {
                throw new Exception("创建Excel工作薄为空！");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        
        for (int rowIndex = stratRow; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++)
        {
            
            Row row = sheet.getRow(rowIndex);
            if (row == null)
                continue;
            Cell content = row.getCell(column);
            if (content == null)
            {
                inAmounts.add("");
                continue;
            }
            String str = String.valueOf(getCellValue(content));
            if (StringUtils.isEmpty(str))
            {
                inAmounts.add("");
            }
            if (StringUtils.isNotEmpty(str))
            {
                inAmounts.add(str.trim());
            }
        }
        
        /*
         * for (int j = stratRow; j < sheet.getLastRowNum(); j++) { Row row =
         * sheet.getRow(j); if(row==null||row.getFirstCellNum()==j){ continue; }
         * Cell cell = row.getCell(column); String str =
         * (String)getCellValue(cell); if(StringUtils.isNotEmpty(str)){
         * 
         * inAmounts.add(str); } }
         */
        return inAmounts;
        
    }
    
    @Override
    public List<List<String>> uploadDataByRow(MultipartFile file, int stratRow, int codeColumn)
    {
        List<String> code = uploadData(file, stratRow, codeColumn);
        
        List<List<String>> data = Lists.newArrayList();
        
        Workbook wb = null;
        try
        {
            wb = this.getWorkbook(file.getInputStream(), file.getOriginalFilename());
            if (null == wb)
            {
                throw new Exception("创建Excel工作薄为空！");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        
        int coloumNum = sheet.getRow(stratRow).getPhysicalNumberOfCells();// 获得总列数
        
        for (int rowIndex = stratRow; rowIndex < (code.size() + stratRow); rowIndex++)
        {
            if (StringUtils.isNotEmpty(code.get(rowIndex - stratRow)))
            {
                List<String> rowData = Lists.newArrayList();
                for (int column = 0; column < coloumNum; column++)
                {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null)
                        continue;
                    Cell content = row.getCell(column);
                    if (content == null)
                    {
                        rowData.add("");
                        continue;
                    }
                    String str = getCellValue(content).toString();
                    if (StringUtils.isNotEmpty(str))
                    {
                        rowData.add(str.trim());
                    }
                    else
                    {
                        rowData.add("");
                    }
                }
                data.add(rowData);
            }
        }
        return data;
    }
    
    @Override
    public Object getCellValue(Cell cell)
    {
        Object value = null;
        DecimalFormat df = new DecimalFormat(); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat(); // 格式化数字
        
        switch (cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString()))
                {
                    value = new BigDecimal(cell.getNumericCellValue());
                }
                else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString()))
                {
                    value = sdf.format(cell.getDateCellValue());
                }
                else
                {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }
    
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception
    {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType))
        {
            wb = new HSSFWorkbook(inStr); // 2003-
        }
        else if (excel2007U.equals(fileType))
        {
            wb = new XSSFWorkbook(inStr); // 2007+
        }
        else
        {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }
    
    @Override
    public String QCValue(double concentration, double one, String flag)
    {
        
        double val = one;
        if ("DNAQC".equals(flag) || "CDNAQC".equals(flag))
        {
            if (concentration < 20)
            {
                return "D";
            }
            else if (concentration >= 20 && concentration < 30)
            {
                if (val >= 1.7 && val <= 2.0)
                {
                    return "B";
                }
                else if (val >= 1.5 && val < 1.7 || val > 2.0 && val <= 2.3)
                {
                    return "C";
                }
                else if (val < 1.5 || val > 2.3)
                {
                    return "D";
                }
            }
            else if (concentration >= 30)
            {
                if (val >= 1.7 && val <= 2.0)
                {
                    return "A";
                }
                else if (val >= 1.5 && val < 1.7 || val > 2.0 && val <= 2.3)
                {
                    return "B";
                }
                else if (val < 1.5 || val > 2.3)
                {
                    return "C";
                }
            }
        }
        else if ("LibQc".equals(flag))
        {
            if (concentration < 30)
            {
                return "D";
            }
            else if (concentration >= 30 && concentration < 50)
            {
                if (val >= 1.7 && val <= 2.0)
                {
                    return "B";
                }
                else if (val >= 1.5 && val < 1.7 || val > 2.0 && val <= 2.3)
                {
                    return "C";
                }
                else if (val < 1.5 || val > 2.3)
                {
                    return "D";
                }
            }
            else if (concentration >= 50)
            {
                if (val >= 1.7 && val <= 2.0)
                {
                    return "A";
                }
                else if (val >= 1.5 && val < 1.7 || val > 2.0 && val <= 2.3)
                {
                    return "B";
                }
                else if (val < 1.5 || val > 2.3)
                {
                    return "C";
                }
            }
        }
        return null;
    }
    
    public static void inputstreamToFile(InputStream ins, File file)
    {
        try
        {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getDownLoadPath(String tempFile)
    {
        ClassLoader classLoader = ConfigManage.class.getClassLoader();
        String resource = classLoader.getResource(tempFile).getPath();
        String path = null;
        if (resource.indexOf("/") == 0)
        {
            path = resource.substring(1);
        }
        return path;
    }
    
    public String formatData(BigDecimal data)
    {
        String str = "";
        if (null != data)
        {
            BigDecimal val = data.setScale(2, BigDecimal.ROUND_CEILING);
            str = String.valueOf(val);
            if (str.indexOf(".") > 0)
            {
                // 正则表达
                str = str.replaceAll("0+?$", "");// 去掉后面无用的零
                str = str.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
            }
        }
        return str;
    }
    
    public List<String> getDesWel()
    {
        List<String> list = Lists.newArrayList();
        String code = "";
        for (int i = 1; i < 13; i++)
        {
            for (char j = 'A'; j < 'I'; j++)
            {
                code = String.valueOf(j) + String.valueOf(i);
                list.add(code);
            }
        }
        return list;
    }
    
    @Override
    public String getFormatTime(String formatDate, Date date)
    {
        SimpleDateFormat fileNameDateFormater = new SimpleDateFormat(formatDate);
        String time = fileNameDateFormater.format(date);
        return time;
    }
    
    public Map<String, String> getMlpaCode(int start, int i)
    {
        Map<String, String> map = Maps.newHashMap();
        String code = "";
        int num = 0;
        String arr[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int val = i / 8;
        int mod = i % 8;
        if (val < 1)
        {
            num = start + 1;
        }
        else if (1 <= val && mod != 0)
        {
            num = start + val + 1;
        }
        else if (1 <= val && mod == 0)
        {
            num = start + val;
        }
        if (mod == 0)
        {
            code = arr[7] + String.format("%02d", num);
        }
        else
        {
            code = arr[mod - 1] + String.format("%02d", num);
        }
        map.put("code", code);
        map.put("start", String.valueOf(num));
        return map;
    }
    
    @Override
    public String zipFiles(File zipfile, List<File> srcfile)
    {
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
    
    protected void copyRow(Row row, Row target)
    {
        int cells = target.getLastCellNum();
        
        Cell cell;
        Cell targetCell;
        
        for (int i = 0; i < cells; i++)
        {
            cell = row.createCell(i);
            targetCell = target.getCell(i);
            cell.setCellType(targetCell.getCellType());
            cell.setCellValue(targetCell.getStringCellValue());
            cell.setCellStyle(targetCell.getCellStyle());
        }
        
        row.setHeight(target.getHeight());
    }
    
    protected Cell cloneCell(Sheet sheet, int row, int col, Row templateRow)
    {
        Row sheetRow = sheet.getRow(row);
        
        if (sheetRow == null)
        {
            sheetRow = sheet.createRow(row);
            
            if (null != templateRow)
            {
                sheetRow.setHeight(templateRow.getHeight());
            }
        }
        
        Cell cell = sheetRow.getCell(col);
        
        if (cell == null)
        {
            cell = sheetRow.createCell(col);
        }
        
        if (null != templateRow)
        {
            cell.setCellStyle(templateRow.getCell(col).getCellStyle());
        }
        
        return cell;
    }
    
    protected void setText(Cell cell, String value)
    {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(value);
    }
    
    @Override
    public String downloadNgsSequecingData(InputStream is, SequencingSubmitModel sheet, File zipfile, String sampleCode)
    {
        List<File> srcfile = Lists.newArrayList();
        if (1 == sheet.getSequecingType())
        {
            srcfile.add(new File(ngsSequecingSelf(sheet, is)));
        }
        else
        {
            srcfile.add(new File(ngsSequecingOther(sheet, is)));
        }
        
        srcfile.add(new File(downloadDetailData(sampleCode)));
        String zipFile = this.zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    private String ngsSequecingOther(SequencingSubmitModel sheet, InputStream is)
    {
        // 创建临时文件
        File tempFile = new File(sheet.getCode() + ".xlsx");
        inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", sheet.getCode());
            dataMap.put("E5", sheet.getAssignerName());
            dataMap.put("H5", dateFormater.format(sheet.getAssignTime()));
            AuthorizedUser u = userService.getByToken();
            dataMap.put("K5", u.getName());
            dataMap.put("B6", sheet.getDescription());
            dataMap.put("A9", sheet.getCode());
            dataMap.put("B9", sheet.getSequecingMachineCode());
            dataMap.put("C9", sheet.getSequecingstrategy());
            dataMap.put("D9", sheet.getRun());
            if (null != sheet.getLanCode())
            {
                dataMap.put("E9", sheet.getLanCode().toString());
            }
            
            excel.writeData(path, dataMap, wsheet);
            
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
    
    private String downloadDetailData(String sampleCode)
    {
        InputStream inputStream = TestSheetService.class.getResourceAsStream("/taskTemplate/biology-analy_details_data_template.xlsx");
        String time = this.getFormatTime("yyyyMMdd", new Date());
        File file = new File("分析数据" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        OutputStream os = null;
        
        ExcelUtil excel = new ExcelUtil();
        Workbook workbook;
        
        try
        {
            workbook = excel.getTempWorkbook(path);
            List<BiologyAnalyDetailsRecord> records = service.getDetailsRecordsBySampleCode(sampleCode);
            //String poolingCode = "";
            if (!CollectionUtils.isEmpty(records))
            {
                //poolingCode = Collections3.getFirst(records).getPoolingCode();
                Sheet template = workbook.getSheetAt(0);
                os = new FileOutputStream(file);
                Sheet sheet = workbook.createSheet("分析数据任务表");
                int index = 0;
                Row row = sheet.createRow(index++);
                copyRow(row, template.getRow(0));
                
                int columns = row.getLastCellNum();
                
                for (int i = 0; i < columns; i++)
                {
                    sheet.setColumnWidth(i, template.getColumnWidth(i));
                }
                
                Row templateRow = template.getRow(1);
                
                for (BiologyAnalyDetailsRecord record : records)
                {
                    setText(cloneCell(sheet, index, 0, templateRow), StringUtils.isEmpty(record.getPoolingCode()) ? "" : record.getPoolingCode());
                    setText(cloneCell(sheet, index, 1, templateRow), StringUtils.isEmpty(record.getOrderCode()) ? "" : record.getOrderCode());
                    setText(cloneCell(sheet, index, 2, templateRow), StringUtils.isEmpty(record.getRecordCode()) ? "" : record.getRecordCode());
                    setText(cloneCell(sheet, index, 3, templateRow), StringUtils.isEmpty(record.getReceivedSampleCode()) ? "" : record.getReceivedSampleCode());
                    setText(cloneCell(sheet, index, 4, templateRow),
                        StringUtils.isEmpty(record.getReferenceSampleCode()) ? "" : record.getReferenceSampleCode());
                    setText(cloneCell(sheet, index, 5, templateRow),
                        StringUtils.isEmpty(record.getReferenceSampleType()) ? "" : record.getReferenceSampleType());
                    setText(cloneCell(sheet, index, 6, templateRow),
                        StringUtils.isEmpty(record.getReferencePoolingCode()) ? "" : record.getReferencePoolingCode());
                    setText(cloneCell(sheet, index, 7, templateRow), StringUtils.isEmpty(record.getLibrarySampleCode()) ? "" : record.getLibrarySampleCode());
                    setText(cloneCell(sheet, index, 8, templateRow), StringUtils.isEmpty(record.getLibraryIndex()) ? "" : record.getLibraryIndex());
                    setText(cloneCell(sheet, index, 9, templateRow), StringUtils.isEmpty(record.getCoordinate()) ? "" : record.getCoordinate()
                        .replace("，", ","));
                    setText(cloneCell(sheet, index, 10, templateRow), StringUtils.isEmpty(record.getExamineeSex()) ? "" : record.getExamineeSex());
                    setText(cloneCell(sheet, index, 11, templateRow), StringUtils.isEmpty(record.getSampleName()) ? "" : record.getSampleName());
                    setText(cloneCell(sheet, index, 12, templateRow), StringUtils.isEmpty(record.getSampleType()) ? "" : record.getSampleType());
                    setText(cloneCell(sheet, index, 13, templateRow), StringUtils.isEmpty(record.getTestingType()) ? "" : record.getTestingType());
                    setText(cloneCell(sheet, index, 14, templateRow), StringUtils.isEmpty(record.getExamineeName()) ? "" : record.getExamineeName());
                    setText(cloneCell(sheet, index, 15, templateRow), StringUtils.isEmpty(record.getContractCode()) ? "" : record.getContractCode());
                    setText(cloneCell(sheet, index, 16, templateRow), StringUtils.isEmpty(record.getRemark()) ? "" : record.getRemark());
                    setText(cloneCell(sheet, index, 17, templateRow), StringUtils.isEmpty(record.getProductCode()) ? "" : record.getProductCode());
                    setText(cloneCell(sheet, index, 18, templateRow), StringUtils.isEmpty(record.getProductName()) ? "" : record.getProductName());
                    setText(cloneCell(sheet, index, 19, templateRow), StringUtils.isEmpty(record.getFamilyRelation()) ? "本人" : record.getFamilyRelation());
                    setText(cloneCell(sheet, index, 20, templateRow), StringUtils.isEmpty(record.getExamineeDiagnosis()) ? "" : record.getExamineeDiagnosis());
                    setText(cloneCell(sheet, index, 21, templateRow), StringUtils.isEmpty(record.getExamineeGenes()) ? "" : record.getExamineeGenes());
                    setText(cloneCell(sheet, index, 22, templateRow), StringUtils.isEmpty(record.getExamineePhenotypes()) ? "" : record.getExamineePhenotypes());
                    setText(cloneCell(sheet, index, 23, templateRow), StringUtils.isEmpty(record.getMedicalHistory()) ? "" : record.getMedicalHistory());
                    setText(cloneCell(sheet, index, 24, templateRow),
                        StringUtils.isEmpty(record.getFamilyMedicalHistory()) ? "" : record.getFamilyMedicalHistory());
                    setText(cloneCell(sheet, index, 25, templateRow), StringUtils.isEmpty(record.getClinicalRecommend()) ? "" : record.getClinicalRecommend());
                    index++;
                }
                
                workbook.removeSheetAt(0);
                excel.writeAndClose(path, os);
                os.flush();
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    
    private String ngsSequecingSelf(SequencingSubmitModel sheet, InputStream is)
    {
        // 创建临时文件
        File tempFile = new File(sheet.getCode() + ".xlsx");
        inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        logger.info(tempFile + "~~~TEMPFILE path!!!!!:" + path);
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("B5", sheet.getCode());
            dataMap.put("E5", sheet.getAssignerName());
            dataMap.put("H5", dateFormater.format(sheet.getAssignTime()));
            AuthorizedUser u = userService.getByToken();
            dataMap.put("K5", u.getName());
            dataMap.put("B6", sheet.getDescription());
            dataMap.put("C8", sheet.getFirstDiluteConcn() + "nM 稀释");
            dataMap.put("G8", sheet.getSecondDiluteConcn() + "pM 稀释");
            dataMap.put("H9", sheet.getSequecingMachineCode());
            dataMap.put("I9", sheet.getRun());
            if (null != sheet.getLanCode())
            {
                dataMap.put("J9", sheet.getLanCode().toString());
            }
            
            excel.writeData(path, dataMap, wsheet);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            Map<Integer, Object> data = new HashMap<Integer, Object>();
            data.put(1, sheet.getSampleCode());
            data.put(2, sheet.getConcn().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(3, sheet.getFirstDiluteSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(4, sheet.getFirstDiluteHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(5, sheet.getSecondDiluteSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(6, sheet.getSecondDiluteReagentInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(7, sheet.getSecondDiluteHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(8, sheet.getFinalSampleInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            data.put(9, sheet.getFinalHTInputVolume().setScale(2, BigDecimal.ROUND_DOWN));
            datalist.add(data);
            String[] heads = new String[] {"A8", "B8", "C9", "D9", "E9", "F9", "G9"}; // 必须为列表头部所有位置集合，
                                                                                      // 输出
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
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
}
