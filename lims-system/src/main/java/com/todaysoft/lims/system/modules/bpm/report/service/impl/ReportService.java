package com.todaysoft.lims.system.modules.bpm.report.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.order.OrderPaymentModel;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bpm.report.model.*;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.DataTemplateColumn;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.excel.ExportExcel;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AutoConfigurationReportEndpoint.Report;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.config.ConfigManage;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PrimerSqlModel;
import com.todaysoft.lims.system.modules.bpm.report.service.IReportService;
import com.todaysoft.lims.system.modules.bpm.report.service.request.GenerateReportRequest;
import com.todaysoft.lims.system.modules.bpm.report.service.request.ReportSendSearcher;
import com.todaysoft.lims.system.modules.bpm.report.service.request.ReportTaskPagingRequest;
import com.todaysoft.lims.system.modules.bpm.report.service.request.ReportUploadRequest;
import com.todaysoft.lims.system.modules.bpm.report.service.request.SendDataRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.util.Constant;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportService extends RestService implements IReportService
{
    
    @Autowired
    private IConfigService configService;

    @Autowired
    private IUserService userService;
    
    @Override
    public Pagination<TestingReport> paging(ReportTaskSearch searcher, int pageNo, int pageSize)
    {
        ReportTaskPagingRequest request = new ReportTaskPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setMarketingCenter(searcher.getMarketingCenter());
        request.setOrderCode(searcher.getOrderCode());
        request.setStatus(searcher.getStatus());
        request.setAnalType(searcher.getAnalType());
        request.setContractNumber(searcher.getContractNumber());
        request.setCustomer(searcher.getCustomer());
        request.setProductCode(searcher.getProductCode());
        request.setProductName(searcher.getProductName());
        request.setTestUnit(searcher.getTestUnit());
        request.setTechnicalMan(searcher.getTechnicalMan());
        request.setSampleCode(searcher.getSampleCode());
        request.setInspectMan(searcher.getInspectMan());
        request.setStartDate(searcher.getStartDate());
        request.setEndDate(searcher.getEndDate());
        request.setResubmit(searcher.getResubmit());
        request.setProductDutyMan(searcher.getProductDutyMan());
        String url = GatewayService.getServiceUrl("/bpm/testing/report/tasks");
        ResponseEntity<Pagination<TestingReport>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportTaskPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<TestingReport>>()
                {
                });
        return exchange.getBody();
    }

    //待下达列表


    @Override
    public Pagination<TestingReport> assignPaging(ReportTaskSearch searcher, Integer pageNo, Integer pageSize) {
        ReportTaskPagingRequest request = new ReportTaskPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setMarketingCenter(searcher.getMarketingCenter());
        request.setOrderCode(searcher.getOrderCode());
        request.setAnalType(searcher.getAnalType());
        request.setContractNumber(searcher.getContractNumber());
        request.setCustomer(searcher.getCustomer());
        request.setProductCode(searcher.getProductCode());
        request.setProductName(searcher.getProductName());
        request.setTestUnit(searcher.getTestUnit());
        request.setTechnicalMan(searcher.getTechnicalMan());
        request.setSampleCode(searcher.getSampleCode());
        request.setInspectMan(searcher.getInspectMan());
        request.setStartDate(searcher.getStartDate());
        request.setEndDate(searcher.getEndDate());
        request.setResubmit(searcher.getResubmit());
        request.setReportState(searcher.getReportState());
        request.setProductDutyMan(searcher.getProductDutyMan());
        String url = GatewayService.getServiceUrl("/bpm/testing/report/assign/tasks");
        ResponseEntity<Pagination<TestingReport>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<ReportTaskPagingRequest>(request),
                        new ParameterizedTypeReference<Pagination<TestingReport>>()
                        {
                        });
        return exchange.getBody();
    }

    @Override
    public Pagination<TestingReport> assignEdPaging(ReportTaskSearch searcher, Integer pageNo, Integer pageSize) {
        ReportTaskPagingRequest request = new ReportTaskPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setMarketingCenter(searcher.getMarketingCenter());
        request.setOrderCode(searcher.getOrderCode());
        request.setAnalType(searcher.getAnalType());
        request.setContractNumber(searcher.getContractNumber());
        request.setCustomer(searcher.getCustomer());
        request.setProductCode(searcher.getProductCode());
        request.setProductName(searcher.getProductName());
        request.setTestUnit(searcher.getTestUnit());
        request.setTechnicalMan(searcher.getTechnicalMan());
        request.setSampleCode(searcher.getSampleCode());
        request.setInspectMan(searcher.getInspectMan());
        request.setStartDate(searcher.getStartDate());
        request.setEndDate(searcher.getEndDate());
        request.setResubmit(searcher.getResubmit());
        request.setReportState(searcher.getReportState());
        request.setProductDutyMan(searcher.getProductDutyMan());
        String url = GatewayService.getServiceUrl("/bpm/testing/report/assigned/tasks");
        ResponseEntity<Pagination<TestingReport>> exchange =
                template.exchange(url,
                        HttpMethod.POST,
                        new HttpEntity<ReportTaskPagingRequest>(request),
                        new ParameterizedTypeReference<Pagination<TestingReport>>()
                        {
                        });
        return exchange.getBody();
    }

    @Override
    public ReportTaskDetail getDetails(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/tasks/{id}");
        return template.getForObject(url, ReportTaskDetail.class, id);
    }
    
    @Override
    public ReturnModel unZip(MultipartFile uploadData)
    {
        File filePath = new File(ConfigManage.getkey("uploadPath"));
        List<String> fileNameList = Lists.newArrayList();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String dateStr = dateFormater.format(date);
        try
        {
            InputStream in = uploadData.getInputStream();
            String fileName = filePath + "\\" + dateStr + uploadData.getOriginalFilename();//zip file
            File file = new File(fileName);
            inputstreamToFile(in, file);
            unZipFiles(fileName, filePath.getPath(), fileNameList);
            file.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ReturnModel rm = new ReturnModel();
        //保存报告名称到对应的记录，并修改状态为已出报告
        if (Collections3.isNotEmpty(fileNameList))
        {
            ReportUploadRequest reportUploadRequest = new ReportUploadRequest();
            reportUploadRequest.setList(fileNameList);
            reportUploadRequest.setStatus("1");
            String url = GatewayService.getServiceUrl("/bpm/testing/report/batchUpload");
            ResponseEntity<ReturnModel> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ReportUploadRequest>(reportUploadRequest), new ParameterizedTypeReference<ReturnModel>()
                {
                });
            rm = exchange.getBody();
        }
        return rm;
    }
    
    @Override
    public ReturnModel unZip0(MultipartFile uploadData)
    {
        File filePath = new File(ConfigManage.getkey("uploadPath"));
        List<String> fileNameList = Lists.newArrayList();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String dateStr = dateFormater.format(date);
        try
        {
            InputStream in = uploadData.getInputStream();
            String fileName = filePath + "\\" + dateStr + uploadData.getOriginalFilename();//zip file
            File file = new File(fileName);
            inputstreamToFile(in, file);
            unZipFiles(fileName, filePath.getPath(), fileNameList);
            file.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ReturnModel rm = new ReturnModel();
        //保存报告名称到对应的记录，并修改状态为已出报告
        if (Collections3.isNotEmpty(fileNameList))
        {
            ReportUploadRequest reportUploadRequest = new ReportUploadRequest();
            reportUploadRequest.setList(fileNameList);
            reportUploadRequest.setStatus("0");
            String url = GatewayService.getServiceUrl("/bpm/testing/report/batchUpload0");
            ResponseEntity<ReturnModel> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ReportUploadRequest>(reportUploadRequest), new ParameterizedTypeReference<ReturnModel>()
                {
                });
            rm = exchange.getBody();
        }
        return rm;
    }
    
    @Override
    public ReportDataModel getReportDataByIds(String reportId)
    {
        TestingReport report = new TestingReport();
        report.setId(reportId);
        String url = GatewayService.getServiceUrl("/bpm/testing/report/reportData");
        ResponseEntity<ReportDataModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingReport>(report), new ParameterizedTypeReference<ReportDataModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String downloadData(InputStream is, ReportDataModel model, String reportId)
    {
        List<BaseInfo> baseInfoList = model.getBaseInfoList();//基本信息 sheet1

        List<String> ids = Arrays.asList(reportId.split(","));
        File tempFile = null;
        if (ids.size() == 1)
        {
            TestingReport entity = get(reportId);
            String fileName = entity.getProductCode() + "_" + entity.getOrderCode() + "_" + entity.getSampleName() + "_" + entity.getSampleCode();
            String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
            tempFile = new File(fileName + "-" + date + ".xlsx");
        }
        else
        {
            tempFile = new File(new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + ".xlsx");
        }
        
        inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        ExcelUtil excel = new ExcelUtil();
        Workbook wbModule = null;
        Sheet wsheet;
        OutputStream os = null;
        try
        {
            wbModule = excel.getTempWorkbook(path);
            
            os = new FileOutputStream(tempFile);
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        
        if (Collections3.isNotEmpty(baseInfoList))
        {
            //创建临时文件
            try
            {
                wsheet = wbModule.getSheet("基本信息");
                List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
                int i = 0;
                for (BaseInfo baseInfo : baseInfoList)
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, baseInfo.getDataRemark());
                    data.put(2, baseInfo.getAnalysisState());
                    data.put(3, baseInfo.getAnalysisForm());
                    data.put(4, baseInfo.getProductCode());
                    data.put(5, baseInfo.getTestType());
                    data.put(6, baseInfo.getFileName());
                    data.put(7, baseInfo.getName());
                    data.put(8, baseInfo.getSex());
                    data.put(9, baseInfo.getCaseNum());
                    data.put(10, baseInfo.getProductName());
                    data.put(11, baseInfo.getSubmissionDate());
                    data.put(12, baseInfo.getSampleCode());
                    data.put(13, baseInfo.getAge());
                    data.put(14, baseInfo.getTestUnit());
                    data.put(15, baseInfo.getTestMethod());
                    data.put(16, baseInfo.getReportDate());
                    data.put(17, baseInfo.getProbe());
                    data.put(18, baseInfo.getClinicalTest());
                    data.put(19, baseInfo.getFocusGene());
                    data.put(20, baseInfo.getHistorySummary());
                    data.put(21, baseInfo.getSampleForm());
                    data.put(22, baseInfo.getCustomerName());
                    data.put(23, baseInfo.getBusinessMan());
                    data.put(24, baseInfo.getTechnicalMan());
                    data.put(25, baseInfo.getReportMan());
                    data.put(26, baseInfo.getReportCompleteDate());
                    data.put(27, baseInfo.getReportAuditor());
                    data.put(28, baseInfo.getRemark());
                    data.put(29, baseInfo.getFamilyHistory());
                    data.put(30, baseInfo.getFamilyHistoryAbs());
                    data.put(31, baseInfo.getOrderCode());
                    data.put(32, baseInfo.getSequenceCode());
                    datalist.add(data);
                    
                    i++;
                }
                String[] heads =
                    new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1", "U1",
                        "V1", "W1", "X1", "Y1", "Z1", "[1", "\\1", "]1", "^1", "_1", "`1"}; // 必须为列表头部所有位置集合， 输出
                // 数据单元格样式和头部单元格样式保持一致
                excel.writeDateList(path, heads, datalist, wsheet);
                
            }
            catch (FileNotFoundException e)
            {
                
                e.printStackTrace();
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
            
        }

        String arr[]={"NGS测序数据","CAPNGS测序数据","MLPA验证","SANGER验证","QPCR验证","PCRNGS验证","多重PCR","Long-PCR","MLPA结果明细","动态突变结果明细","荧光检测结果明细","Sanger检测"};

        for(String sheetName:arr)
        {
            dynamicWriteExcel(model,wbModule,path,sheetName);
        }
        try {
            wbModule.write(new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile.getAbsolutePath();
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
    public String dealReport(String path, String wordPath, String id)
    {
        
        TestingReport testingReport = new TestingReport();
        testingReport.setPath(path);
        testingReport.setWordPath(wordPath);
        testingReport.setId(id);
        return post("/bpm/testing/report/dealReport", testingReport, String.class);
    }
    
    public void dosome(List<NgsInfo> list, ExcelUtil excel, Workbook wbModule, String path, String sheerName)
    {
        //创建临时文件
        try
        {
            Sheet wsheet = wbModule.getSheet(sheerName);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            int i = 0;
            for (NgsInfo ngsinfo : list)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, ngsinfo.getVerify());
                data.put(2, ngsinfo.getLocusType());
                data.put(3, ngsinfo.getVerifyMethod());
                data.put(4, ngsinfo.getVerifyScheme());
                data.put(5, ngsinfo.getDataCode());
                data.put(6, ngsinfo.getSample());
                data.put(7, ngsinfo.getGeneSymbol());
                data.put(8, ngsinfo.getChrLocation());
                data.put(9, ngsinfo.getRefTranscript());
                data.put(10, ngsinfo.getExon());
                data.put(11, ngsinfo.getNucleotideChanges());
                data.put(12, ngsinfo.getAminoAcidChanges());
                data.put(13, ngsinfo.getGeneType());
                data.put(14, ngsinfo.getInhert());
                data.put(15, ngsinfo.getChromosome());
                data.put(16, ngsinfo.getBeginLocus());
                data.put(17, ngsinfo.getEndLocus());
                data.put(18, ngsinfo.getGeneRegion());
                data.put(19, ngsinfo.getCytoBand());
                data.put(20, ngsinfo.getDbsnp147());
                data.put(21, ngsinfo.getUpRefGene());
                data.put(22, ngsinfo.getDownRefGene());
                data.put(23, ngsinfo.getRefSample());
                datalist.add(data);
                i++;
            }
            String[] heads =
                new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1", "U1",
                    "V1", "W1"}; // 必须为列表头部所有位置集合， 输出
            // 数据单元格样式和头部单元格样式保持一致
            excel.writeDateList(path, heads, datalist, wsheet);
            
        }
        catch (FileNotFoundException e)
        {
            
            e.printStackTrace();
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        
    }
    
    @Override
    public Integer validateData()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/validateData");
        ResponseEntity<Integer> entity = template.getForEntity(url, Integer.class);
        return entity.getBody();
    }
    
    @Override
    public void updateTestingReport(TestingReport request)
    {
        post("/bpm/testing/report/submitRedirectList", request, String.class);
        
    }
    
    @Override
    public void handleSangerVerify()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/handleSangerVerify");
        PrimerSqlModel model = template.getForObject(url, PrimerSqlModel.class);
        String sql = model.getSql();
        if (StringUtils.isNotEmpty(sql))
        {
            try
            {
                File filePath = new File("/mnt/software/logs");
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                String dateStr = dateFormater.format(new Date());
                String fileName = filePath + "\\" + dateStr + "_" + "todo.sql";//zip file
                File file = new File(fileName);
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sql.getBytes());
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void handleReportName()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/handleReportName");
        template.getForObject(url, Object.class);
    }
    
    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     * @param zipFileName 需要解压的ZIP文件
     * @param descFileName 目标文件
     */
    public static boolean unZipFiles(String zipFileName, String descFileName, List<String> list)
    {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String dateStr = dateFormater.format(date);
        String descFileNames = descFileName;
        String mulu = "";
        if (!descFileNames.endsWith(File.separator))
        {
            descFileNames = descFileNames + File.separator;
        }
        try
        {
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(zipFileName, "gbk");
            ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            // 获取ZIP文件里所有的entry
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            // 遍历所有entry
            while (enums.hasMoreElements())
            {
                entry = (ZipEntry)enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                String fileResult = "";
                String fileResult2 = Constant.REPORT_NAME_PREFIX + IdGen.uuid();
                String sufffixname = "";
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory())
                {
                    mulu = descFileDir;
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                }
                else
                {
                    String arr[] = entryName.split("/");
                    if (1 < arr.length)
                    {
                        fileResult = dateStr + "_" + arr[1];
                    }
                    else
                    {
                        fileResult = dateStr + "_" + entryName;
                    }
                    //取文件后缀名
                    
                    int fileIndex = entryName.lastIndexOf(".");
                    if (fileIndex != -1)
                    {
                        sufffixname = entryName.substring(fileIndex, entryName.length());
                    }
                    //七牛文件中文名称有问题 所有生成一个uuid的文件名保存 但是文件名称还是保存在testingreport表，实际数据的url存在orderproduct表
                    String combinePath = fileResult + "," + fileResult2 + sufffixname;
                    if (!list.contains(combinePath))
                    {
                        list.add(combinePath);
                    }
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                File file = new File(descFileDir);
                // 打开文件输出流
                OutputStream os = new FileOutputStream(file);
                // 从ZipFile对象中打开entry的输入流
                InputStream is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1)
                {
                    os.write(buf, 0, readByte);
                }
                FileUtils.uploadQiniu(file, fileResult2 + sufffixname);
                os.close();
                is.close();
                file.delete();
            }
            if (StringUtils.isNotEmpty(mulu))
            {
                deleteDir(new File(mulu));
            }
            zipFile.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    private static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    @Override
    public TestingReport get(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/{id}");
        return template.getForObject(url, TestingReport.class, id);
    }
    
    @Override
    public List<com.todaysoft.lims.system.modules.report.model.TestingReport> getReportListByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getReportListByOrderId/{orderId}");
        ResponseEntity<List<com.todaysoft.lims.system.modules.report.model.TestingReport>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<com.todaysoft.lims.system.modules.report.model.TestingReport>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public String findTestingReports()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/findTestingReports");
        ResponseEntity<String> entity = template.getForEntity(url, String.class);
        return entity.getBody();
    }

    public void dynamicWriteExcel(ReportDataModel model, Workbook wbModule, String path, String sheetName)
    {
        ExcelUtil excel= new ExcelUtil();
        List<List<String>> datalist = Lists.newArrayList();
        DataTemplate dataTemplate = null;
        List<Map<String,String>> dataMapList =Lists.newArrayList();
        switch (sheetName)
        {
            case "NGS测序数据":
                List<NgsInfo> ngsInfoList = model.getNgsInfoList();
                if(Collections3.isEmpty(ngsInfoList)) break;
                dataTemplate = Collections3.getFirst(ngsInfoList).getDataTemplate();
                ngsInfoList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "CAPNGS测序数据":
                List<NgsInfo> capNgsList = model.getCapNgsList();
                if(Collections3.isEmpty(capNgsList)) break;
                dataTemplate = Collections3.getFirst(capNgsList).getDataTemplate();
                capNgsList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "MLPA验证":
                List<VerifyData> mlpaList = model.getMlpaVerifyDataList();
                if(Collections3.isEmpty(mlpaList)) break;
                dataTemplate = Collections3.getFirst(mlpaList).getDataTemplate();
                mlpaList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "SANGER验证":
                List<VerifyData> sangerList = model.getSangerVerifyDataList();
                if(Collections3.isEmpty(sangerList)) break;
                dataTemplate = Collections3.getFirst(sangerList).getDataTemplate();
                sangerList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "QPCR验证":
                List<VerifyData> qpcrList = model.getQpcrVerifyDataList();
                if(Collections3.isEmpty(qpcrList)) break;
                dataTemplate = Collections3.getFirst(qpcrList).getDataTemplate();
                qpcrList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "PCRNGS验证":
                List<VerifyData> pcrngsList = model.getPcrNgsVerifyDataList();
                if(Collections3.isEmpty(pcrngsList)) break;
                dataTemplate = Collections3.getFirst(pcrngsList).getDataTemplate();
                pcrngsList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "多重PCR":
                List<NgsInfo> mpcrList = model.getMultiplePcrList();
                if(Collections3.isEmpty(mpcrList)) break;
                dataTemplate = Collections3.getFirst(mpcrList).getDataTemplate();
                mpcrList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "Long-PCR":
                List<NgsInfo> lpcrList = model.getLongPcrList();
                if(Collections3.isEmpty(lpcrList)) break;
                dataTemplate = Collections3.getFirst(lpcrList).getDataTemplate();
                lpcrList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "MLPA结果明细":
                List<MlpaDataInfo> mlpaTestList = model.getMlpaDataInfoList();
                if(Collections3.isEmpty(mlpaTestList)) break;
                dataTemplate = Collections3.getFirst(mlpaTestList).getDataTemplate();
                mlpaTestList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "动态突变结果明细":
                List<DtDataInfo> dtInfoList = model.getDtDataInfoList();
                if(Collections3.isEmpty(dtInfoList)) break;
                dataTemplate = Collections3.getFirst(dtInfoList).getDataTemplate();
                dtInfoList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "荧光检测结果明细":
                List<FlouDataInfo> fluoInfoList = model.getFlouDataInfoList();
                if(Collections3.isEmpty(fluoInfoList)) break;
                dataTemplate = Collections3.getFirst(fluoInfoList).getDataTemplate();
                fluoInfoList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            case "Sanger检测":
                List<SangerDataInfo> sangerInfoList = model.getSangerDataInfoList();
                if(Collections3.isEmpty(sangerInfoList)) break;
                dataTemplate = Collections3.getFirst(sangerInfoList).getDataTemplate();
                sangerInfoList.stream().forEach(x->dataMapList.add(x.getMap()));
                break;
            default:
                break;
        }
        if(null == dataTemplate)
        {
            return ;
        }
        List<DataTemplateColumn> columnList = dataTemplate.getColumnList();
        //标题行
        List<String> results = Lists.newArrayList();
        List<String> heads = Lists.newArrayList();
        for(int i=0;i<columnList.size();i++)
        {
            heads.add(columnList.get(i).getColumnName());
        }
        //如果是验证数据分析提交的数据则按照固定顺序排序输出
        List<String> verifyMethod = Arrays.asList("MLPA验证","SANGER验证","QPCR验证","PCRNGS验证");
        if(verifyMethod.contains(sheetName))
        {
            results = sortVerifyHeads(heads);
        }else{
            results = heads;
        }
        //数据行
        for(Map<String,String> map:dataMapList)
        {
            if(map.keySet().size()==0)continue;
            List<String> data = Lists.newArrayList();
            for(int i=0;i<results.size();i++)
            {
                String result = map.get(results.get(i));
                if("结果".equals(results.get(i)))
                {
                    result = map.get(results.get(i));
                    if((!"成功".equals(result)) && (!"失败".equals(result)) )
                    {
                        if("0".equals(result))
                        {
                            result="成功";
                        }else{
                            result="失败";
                        }
                    }
                }
                data.add(result);
            }
            datalist.add(data);
        }
        try {
            excel.exportObjects2Excel(datalist,results,wbModule,sheetName,true,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> sortVerifyHeads(List<String> heads) {
        List<String> results = Lists.newArrayList();
        if(Collections3.isNotEmpty(heads))
        {
            String arrs[]={"订单编号","合并编号","家系关系","样本名称","样本编号","基因","外显子区域","染色体位置","纯合/杂合","突变来源","备注"};
            for(int i=0;i<arrs.length;i++)
            {
                results.add(arrs[i]);
                if(heads.contains(arrs[i]))
                {
                    heads.remove(arrs[i]);
                }
            }
            results.addAll(heads);
        }
        return results;
    }

    @Override
    public ReportHandleModel getHandleModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getHandleModel/{id}");
        return template.getForObject(url, ReportHandleModel.class, id);
    }
    
    @Override
    public ReportProcessModel getProcessModel(ReportProcessModelArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getProcessModel");
        return template.postForObject(url, args, ReportProcessModel.class);
    }
    
    @Override
    public String reportEdit(TestingReport report)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/reportEdit");
        return template.postForObject(url, report, String.class);
    }
    
    @Override
    public void saveTestingResult(ReportProcessModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/saveTestingResult");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public String uploadSingleFile(MultipartFile uploadFile)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        int fileIndex = uploadFile.getOriginalFilename().lastIndexOf(".");
        String sufffixname = "";
        if (fileIndex != -1)
        {
            sufffixname = uploadFile.getOriginalFilename().substring(fileIndex, uploadFile.getOriginalFilename().length());
        }
        String fileName = Constant.REPORT_NAME_PREFIX + UUID.randomUUID().toString().replace("-", "") + sufffixname;
        String wordName = dateFormater2.format(date) + "_" + uploadFile.getOriginalFilename();
        if (!file.exists())
        {
            file.mkdir();
        }
        String path = file.getAbsolutePath().toString() + "\\" + fileName;
        localFile = new File(path);
        try
        {
            uploadFile.transferTo(localFile);
            FileUtils.uploadQiniu(localFile, fileName);
            localFile.delete();
        }
        catch (IllegalStateException | IOException e)
        {
            e.printStackTrace();
        }
        
        List<String> fileStr = Arrays.asList(uploadFile.getOriginalFilename().split("_"));
        String productCode = fileStr.get(0);
        String orderCode = fileStr.get(1);
        TestingReport reportRequest = new TestingReport();
        reportRequest.setOrderCode(orderCode);
        reportRequest.setProductCode(productCode);
        String reportId = getReportByOrderAndProduct(reportRequest);
        //更新订单产品表
        ReportRequest request = new ReportRequest(reportId, fileName);
        updataOrderProductForFile(request);
        //更新报告表
        request.setFileName(wordName);
        updataReportForFile(request);
        
        //上传记录表
        ReportUploadRecordRequest recordRequest=new ReportUploadRecordRequest();
        recordRequest.setFileName(fileName);
        recordRequest.setReportId(reportId);
        recordRequest.setUploadType(ReportUploadRecordRequest.UPLOADTYPE_MANUAL);
        insertTestingReportUploadRecord(recordRequest);
        
        return wordName + "/" + fileName;
    }
    
    @Override
    public void updataOrderProductForFile(ReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/updataOrderProductForFile");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void insertTestingReportUploadRecord(ReportUploadRecordRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/insertTestingReportUploadRecord");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void updataReportForFile(ReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/updataReportForFile");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public String getReportByOrderAndProduct(TestingReport request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getReportByOrderAndProduct");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public String savePictures(TestingReport request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/savePictures");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public void saveTestingSample(ReportProcessModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/saveTestingSample");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void saveVerifySample(ReportProcessModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/saveVerifySample");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void solve(TestingReport request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/solve");
        template.postForObject(url, request, String.class);
    }

    @Override
    public void updateTestingReportRedundant()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateTestingReportRedundant");
        template.getForObject(url, Object.class);
    }

    @Override
    public void updateTestingReportTechnicalMan()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateTestingReportTechnicalMan");
        template.getForObject(url, Object.class);
    }

    @Override
    public void updateTestingReportProductDutyMan()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateTestingReportProductDutyMan");
        template.getForObject(url, Object.class);
    }

    @Override
    public List<UserDetailsModel> getTechnicalManByReport(String reportIds) {
        List<UserDetailsModel> result = Lists.newArrayList();
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getReportTaskUsers");
        ResponseEntity<List<UserDetailsModel>> exchange =
                template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDetailsModel>>() {});
        List<UserDetailsModel> records =  exchange.getBody();
        for(UserDetailsModel model:records)
        {
            if(null != model.getArchive())
            {
                model.setUserName_name(model.getArchive().getName());
            }
            result.add(model);
        }
        return result;
    }

    @Override
    public void assign(TestingReportAssignModel model) {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/assign");
        template.postForLocation(url, model);
    }

    @Override
    public void updateTechnicalRecordToMap() {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateTechnicalRecordToMap");
        template.getForObject(url, Object.class);
    }

    @Override
    public void updateDataAnalysisRecordToMap() {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateDataAnalysisRecordToMap");
        template.getForObject(url, Object.class);
    }

    @Override
    public ResponseModel reportCallbackModel(ReportCallbackModel model) {
        System.out.println("callback success:"+model.getGenerateStatus()+"~~"+model.getMessage()+"~~"+model.getReportUrl());
        //处理报告传到骑牛 并处理文件名称
        if("1".equals(model.getGenerateStatus()))//成功
        {
            if(StringUtils.isNotEmpty(model.getReportUrl()) && StringUtils.isNotEmpty(model.getFileName()))
            {
                String url = getQiNiuUrlByUrl(model.getReportUrl(),model.getFileName());
                model.setReportUrl(url);
            }
        }
        String url = GatewayService.getServiceUrl("/bpm/testing/report/generate/callback");

        ResponseEntity<ResponseModel> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ReportCallbackModel>(model), new ParameterizedTypeReference<ResponseModel>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public void send(SendDataRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/sendReport");
        template.postForObject(url, request, String.class);

    }

    @Override
    public void remark(TestingReport request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/remark");
        template.postForObject(url, request, String.class);
    }

    @Override
    public Pagination<ReportSendCallBackModel> reportSendPaging(ReportSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/reportSendList");

        ResponseEntity<Pagination<ReportSendCallBackModel>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ReportSendSearcher>(searcher), new ParameterizedTypeReference<Pagination<ReportSendCallBackModel>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public ReportSendDataModel getSendData(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getSendData/{id}");
        ResponseEntity<ReportSendDataModel> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ReportSendDataModel>()
            {
            }, Collections.singletonMap("id", id));
        return exchange.getBody();
    }

    @Override
    public void updateReportStateJob() {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getUpdateList");
        ResponseEntity<List<TestingReport>> exchange =
                template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingReport>>() {});
        List<TestingReport> records =  exchange.getBody();
        //查询出来待处理的记录 去查询接口
        System.out.println("查出来"+records.size()+"个报告超过30分钟没得到处理结果，需要处理。");
        for(TestingReport report :records)
        {
            String id = report.getId();
            ResponseModel model = requestByReportKey(id);
            if(model != null && null != model.getData() && (!"0".equals(model.getData().getGenerateStatus())))
            {
                reportCallbackModel(model.getData());//处理结果
            }
        }
    }

    public ResponseModel requestByReportKey(String id)
    {
        List<String> list = configService.getConfigValues("URL_ADDRESS");
        if(Collections3.isEmpty(list))
        {
            System.err.println("request url is null ");
            return null;
        }
        else
        {
            String url = list.get(0)+"/report/status/?reportKey="+id;
            System.out.println("request url is:"+url);
            ResponseModel model = null;
            HttpRequest httpRequest = HttpRequest.get(url);
            HttpResponse response = httpRequest.send();
            System.out.println(response.body());
            if(StringUtils.isNotEmpty(response.body()))
            {
                model = JSON.parseObject(response.body(),ResponseModel.class);
                System.out.println("=============search result model message:"+model.getMessage());
                if(ResponseModel.SUCCESS_STATE_CODE.equals(model.getStatusCode()))
                {
                    ReportCallbackModel data = model.getData();
                    System.out.println("=============search result fileName:"+data.getFileName());
                    data.setReportKey(id);
                }else{
                    System.err.println(model.getMessage());
                }
            }else{
                System.err.println("search result model is null ");
            }
            return model;
        }
        
    }

    private String getQiNiuUrlByUrl(String url,String fileName)
    {
        String sufffixname="";
        String fileResultQiniu = Constant.REPORT_NAME_PREFIX + IdGen.uuid();
        if(StringUtils.isNotEmpty(fileName))
        {
            int fileIndex = fileName.lastIndexOf(".");
            if (fileIndex != -1)
            {
                sufffixname = fileName.substring(fileIndex, fileName.length());
            }
        }
        try {
            URL urlDown = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlDown.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();


            File tempFile = new File(IdGen.uuid() + sufffixname);
            inputstreamToFile(inputStream, tempFile);

            System.err.println(tempFile.getAbsolutePath()+"~~"+tempFile.getPath());

            FileUtils.uploadQiniu(tempFile, fileResultQiniu + sufffixname);

            tempFile.delete();

        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileName+","+fileResultQiniu+sufffixname;
    }

    @Override
    public List<ReportAndVerifyModel> getReportAndVerifyModelByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/report/getReportAndVerifyModelByOrderId/{orderId}");
        ResponseEntity<List<ReportAndVerifyModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReportAndVerifyModel>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }

    @Override
    public void generateReport(GenerateReportRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalexport/generate");
        template.postForObject(url, request, String.class);
    }

}
