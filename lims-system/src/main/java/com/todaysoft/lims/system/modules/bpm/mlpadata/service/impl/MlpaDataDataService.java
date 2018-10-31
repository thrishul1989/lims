package com.todaysoft.lims.system.modules.bpm.mlpadata.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.collect.Maps;
import com.qiniu.common.QiniuException;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.*;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.ZipFileUploadModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.impl.DataPcrSangerDataService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ITestSheetService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MlpaDataDataService extends RestService implements IMlpaDataDataService
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IMlpaDataService service;

    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Override
    public String zipFilesMlpaData(File zipfile, MlpaDataSheetModel sheet)
    {
        List<File> srcfile = Lists.newArrayList();
        srcfile.add(new File(downloadMlpaDataTasks(sheet)));
        srcfile.add(new File(downloadMlpaDataResult(sheet)));
        srcfile.add(new File(downloadAnalysTask(sheet)));
        return testSheetService.zipFiles(zipfile, srcfile);
    }
    
    //任务单
    private String downloadMlpaDataTasks(MlpaDataSheetModel request)
    {
        InputStream inputStream = MlpaDataDataService.class.getResourceAsStream("/taskTemplate/mlpa/mlpa_data_task.xlsx");
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
            dataMap.put("H5", user.getName());
            dataMap.put("B6", request.getDescription());
            excel.writeData(path, dataMap, wsheet);
            
            List<MlpaDataTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            String typeName = "";
            for (MlpaDataTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getSheetCode());
                data.put(2, task.getSampleName());
                data.put(3, task.getSampleCode());
                if ("1".equals(task.getType()))
                {
                    typeName = "检测";
                }
                else
                {
                    typeName = "验证";
                }
                data.put(4, typeName);
                data.put(5, task.getProductName());
                data.put(6, task.getGene());
                data.put(7, task.getChromosomeLocation());
                data.put(8, task.getProbe());
                data.put(9, "");
                data.put(10, task.getSequencingCode());
                data.put(11, task.getOrderCode());
                data.put(12, task.getFamilyRelation());
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    private String downloadMlpaDataResult(MlpaDataSheetModel request)
    {
        InputStream inputStream = MlpaDataDataService.class.getResourceAsStream("/taskTemplate/mlpa/mlpa_data_result.xlsx");
        String time = testSheetService.getFormatTime("yyyyMMdd", new Date());
        File file = new File(request.getCode() + "_数据分析结果表_" + time + ".xlsx");
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            
            List<MlpaDataTask> taskList = request.getTasks();
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            for (MlpaDataTask task : taskList)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, task.getTestingTask().getInputSample().getReceivedSample().getSampleCode());
                data.put(2, task.getGene());
                data.put(3, task.getChromosomeLocation());
                data.put(4, "");
                data.put(5, "");
                data.put(6, "");
                data.put(7, "");
                data.put(8, "");
                data.put(9, "");
                data.put(10, "");
                datalist.add(data);
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            
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
    
    private String downloadAnalysTask(MlpaDataSheetModel request)
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
    public DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile)
    {
        DataAnalysisParseModel result = new DataAnalysisParseModel();

        List<String> columns = Lists.newArrayList();
        DataTemplate dataTemplate = dataTemplateService.getDataTemplateMapBySheetId(sheetId);
        dataTemplate.getColumnList().stream().forEach(x->columns.add(x.getColumnName()));

        //上传解压
        ZipFileUploadModel zipFileUploadModel = unZipFiles(zipFile,TestingMethod.MLPA);

        //解析xls
        MlpaDataAnalRecordResolver resolver = new MlpaDataAnalRecordResolver(zipFileUploadModel,dataTemplate);
        List<MlpaDataSubmitTaskSuccessArgs> records = resolver.resolve();
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

    @Override
    public ZipFileUploadModel unZipFiles(MultipartFile zipFileUpload,String semantic)
    {
        ZipFileUploadModel model = new ZipFileUploadModel();
        List<String> localFilePath = Lists.newArrayList();
        Map<String,String> picNameMap = Maps.newHashMap();
        File xlsFile=null;
        int xlsFileCount = 0;
        //在该目录下创建一个子目录(随机名称)存放解压的文件夹或文件
        String tempDir = ConfigManage.getkey("uploadPath")+File.separator+IdGen.uuid();
        File filePath = new File(tempDir);
        String picParentPath="";
        if(!filePath.exists())
        {
            filePath.mkdirs();
        }
//        String dateStr = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(new Date());
        String descFileNames = filePath.getPath();
//        String mulu = "";
        if (!descFileNames.endsWith(File.separator))
        {
            descFileNames = descFileNames + File.separator;
        }
        String fileName = descFileNames + zipFileUpload.getOriginalFilename();//zip file
        File fileZipLocal = new File(fileName);//随机生成的目录下面存放上传的zip
        try
        {
            InputStream in = zipFileUpload.getInputStream();
            FileUtils.inputstreamToFile(in, fileZipLocal);
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(fileName, "gbk");
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
                String dataCodeStr = "";
                boolean xlsFileBool = false;
                entry = (ZipEntry)enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                String fileResult = "";
                String sufffixname = "";
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory())
                {
//                    mulu = descFileDir;
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                }
                else
                {
                    String arr[] = entryName.split("/");
                    if (1 < arr.length)
                    {
                        fileResult = arr[1];
                        dataCodeStr = arr[1];
                    }
                    else
                    {
                        fileResult = entryName;
                        dataCodeStr = entryName;
                    }
                    //取文件后缀名

                    int fileIndex = entryName.lastIndexOf(".");
                    if (fileIndex != -1)
                    {
                        sufffixname =entryName.substring(entryName.lastIndexOf(".") + 1);
                    }
                    if("xlsx".equalsIgnoreCase(sufffixname) || "xls".equalsIgnoreCase(sufffixname))
                    {
                        xlsFileCount++;
                        xlsFileBool = true;
                    }else if("jpg".equalsIgnoreCase(sufffixname) || "png".equalsIgnoreCase(sufffixname) || "gif".equalsIgnoreCase(sufffixname)
                            || "jpeg".equalsIgnoreCase(sufffixname) || "bmp".equalsIgnoreCase(sufffixname)){
                        String dataCode = "";
                        if(TestingMethod.SANGER.equals(semantic))
                        {
                            //sanger验证的图片命名规则是二次PCR孔号_合并编号_引物名
                            //C01_S062105_176210400_PEX13-chr1-6655_PRI_R-F.jpg  引物名肯定只有一个"_"
                            String tempArr[] = dataCodeStr.split("_");
                            List<String> dataCodeResut = Lists.newArrayList();
                            if(null != tempArr && tempArr.length>3)
                            {
                                for(int i=1;i< tempArr.length-2;i++)
                                {
                                    dataCodeResut.add(tempArr[i]);
                                }
                                dataCode = StringUtils.join(dataCodeResut,"_");
                            }
                        }else if(TestingMethod.SANGERTEST.equals(semantic))//C01_O17041404_smp41405_NPHP4_S16_A2_F 后面已经有引物名了，所以不加了直接就是二次PCR孔号_合并编号
                        {
                            int index = dataCodeStr.indexOf("_");
                            int last = dataCodeStr.lastIndexOf(".");
                            dataCode = dataCodeStr.substring(index+1,last);
                        } else{
                            dataCode = dataCodeStr.split("#")[0];
                        }
                        if (picNameMap.containsKey(dataCode))
                        {
                            picNameMap.put(dataCode,picNameMap.get(dataCode)+","+fileResult);
                        }else{
                            picNameMap.put(dataCode,fileResult);
                        }
                    }
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }

                if(!xlsFileBool)
                {
                    File filePic = new File(descFileDir);
                    picParentPath = filePic.getParent();
                    // 打开文件输出流
                    OutputStream os = new FileOutputStream(filePic);
                    // 从ZipFile对象中打开entry的输入流
                    InputStream is = zipFile.getInputStream(entry);
                    while ((readByte = is.read(buf)) != -1)
                    {
                        os.write(buf, 0, readByte);
                    }
                    os.close();
                    is.close();
//                    上传的时候不上传七牛，等用户点击确认提交的时候上传到服务器 先把文件放到本地 存下来地址 用完删除掉
//                    FileUtils.uploadQiniu(filePic, fileResult);
//                    filePic.delete();
                    if(!localFilePath.contains(filePic.getPath()))
                    {
                        localFilePath.add(filePic.getPath());
                    }
                }else{
                    xlsFile = new File(descFileDir);
                    OutputStream os = new FileOutputStream(xlsFile);
                    // 从ZipFile对象中打开entry的输入流
                    InputStream is = zipFile.getInputStream(entry);
                    while ((readByte = is.read(buf)) != -1)
                    {
                        os.write(buf, 0, readByte);
                    }
                    os.close();
                    is.close();
                }
            }
            if(1 < xlsFileCount)
            {
                throw new IllegalStateException();
            }
            model.setFile(xlsFile);
            model.setZipFile(zipFile);
            model.setUploadDir(tempDir);
            model.setLocalZipFile(fileZipLocal);
            model.setLocalFilePath(localFilePath);
            model.setPicList(wrap(picNameMap));
            model.setPicParentPath(picParentPath);
            return model;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public List<TestingDataPic> wrap(Map<String,String> picNameMap)
    {
        List<TestingDataPic> list = Lists.newArrayList();
        if(null != picNameMap)
        {
            TestingDataPic model;
            for(String key:picNameMap.keySet())
            {
                model = new TestingDataPic();
                model.setDataCode(key);
                model.setPicUrl(picNameMap.get(key));
                list.add(model);
            }
        }
        return list;
    }

    @Override
    public void deleteLocalFileDir(DataAnalysisParseModel model)
    {
        if(null != model )
        {
            String dir = model.getUploadDir();
            File file = new File(dir);
            deleteDir(file);
        }
    }

    @Override
    public boolean deleteDir(File dir)
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
    public void uploadFileQNAndDeleteLocal(List<String> fileList, String dir)
    {
       if(Collections3.isNotEmpty(fileList))
       {
           File file;
           for(String filePath:fileList)
           {
               file = new File(filePath);
               if(file.exists())
               {
                   try {
                       FileUtils.uploadQiniu(file, filePath);
                   } catch (QiniuException e) {
                       e.printStackTrace();
                   }

               }
           }
       }


    }

    @Override
    public List<TestingDataPic> uploadFileAndWrapData(DataAnalysisParseModel parseResult,String sheetId)
    {
        List<TestingDataPic> result = Lists.newArrayList();
        List<TestingDataPic> uploadResult = parseResult.getPicList();
        if(Collections3.isNotEmpty(parseResult.getLocalFilePath()))
        {
            TestingDataPic testingDataPic;
            for(String temp:parseResult.getLocalFilePath())
            {
                if(!StringUtils.isEmpty(temp))
                {
                    String qiniuSuffix = "DATA_PIC_" + IdGen.uuid();
                    String sufffixname="";
                    File fileLocal = new File(temp);
                    String picName = fileLocal.getName();
                    boolean picValid=true;
                    String dataCode="";
                    for(TestingDataPic data:uploadResult)
                    {
                        if(data.getPicUrl().equals(picName))
                        {
                            picValid = data.isValid();
                            dataCode = data.getDataCode();
                        }

                    }
                    if(!picValid) continue;
                    int fileIndex = picName.lastIndexOf(".");
                    if (fileIndex != -1)
                    {
                        sufffixname = picName.substring(fileIndex);
                    }
                    String qiniuUrl = qiniuSuffix+sufffixname;
                    try {
                        FileUtils.uploadQiniu(fileLocal,qiniuUrl);
                    } catch (QiniuException e) {
                        e.printStackTrace();
                    }
                    testingDataPic  = new TestingDataPic();
                    testingDataPic.setSheetId(sheetId);
                    testingDataPic.setPicUrl(qiniuUrl);
                    testingDataPic.setPicName(picName);
                    testingDataPic.setDataCode(StringUtils.isEmpty(dataCode)?picName.split("#")[0]:dataCode);
                    result.add(testingDataPic);
                }
            }
        }
        //执行完删除掉本地临时目录
        File dir = new File(parseResult.getUploadDir());
        if(dir.exists())
        {
            deleteDir(dir);
        }
        return result;
    }
}
