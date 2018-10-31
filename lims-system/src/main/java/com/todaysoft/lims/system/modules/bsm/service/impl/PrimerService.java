package com.todaysoft.lims.system.modules.bsm.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.form.PrimerFormRequest;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Primer;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.CheckPrimerForDesignRequest;
import com.todaysoft.lims.system.modules.bsm.model.PrimerExcelListRequest;
import com.todaysoft.lims.system.modules.bsm.service.IPrimerService;
import com.todaysoft.lims.system.modules.bsm.service.request.PrimerPagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrimerService extends RestService implements IPrimerService
{
    
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
    
    @Autowired
    private ITestingMethodService testingMethodservice;
    
    @Override
    public String create(PrimerFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/create");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(PrimerFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/modify");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<Primer> paging(PrimerPagingRequest searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/primer/paging");
        ResponseEntity<Pagination<Primer>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PrimerPagingRequest>(searcher),
                new ParameterizedTypeReference<Pagination<Primer>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public Primer getPrimersById(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/{id}");
        return template.getForObject(url, Primer.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean checkedPrimerNo(String primerNo)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/checkedPrimerNo/{primerNo}");
        return template.getForObject(url, boolean.class, Collections.singletonMap("primerNo", primerNo));
    }
    
    @Override
    public List<List<String>> analysisFile(MultipartFile file)
    {
        List<List<String>> dataList = Lists.newArrayList();
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
        List<String> jstr = Lists.newArrayList();
        //判断表内正向引物名和反向引物名唯一性，满足重复性，不做判断
        List<String> forwordPrimerNames = new ArrayList<String>();
        List<String> revesePrimerNames = new ArrayList<String>();
        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++)
        {
            
            List<String> joinStr = Lists.newArrayList();
            List<String> joinAll = Lists.newArrayList();
            List<String> colList = new ArrayList<String>();
            Integer type = 0;
            String jost = "";
            String jostAll = "";
            String useType = "";
            Row row = sheet.getRow(rowIndex);
            Cell content;
            if (row == null)
                continue;
            boolean flag = false;
            Boolean nameFlag = false;
            String forwordPrimerName = "";
            String revesePrimerName = "";
            for (int col = 0; col < sheet.getRow(0).getLastCellNum(); col++)
            {
                String str = "";
                content = row.getCell(col);
                /*  if (content == null)//如果为空跳过异常，继续解析下一行
                  {
                      type = 2;
                      colList.add("");
                  }*/
                if (null != content)
                {
                    str = (String)getCellValue(content);
                    //除了产品编号列，有空报必填项缺失错误
                }
                colList.add(str);
                
                if (col == 10)//获取检测平台id，存入检测平台属性
                {
                    
                    TestingMethod t = new TestingMethod();
                    if (StringUtils.isNotEmpty(str))
                    {
                        t.setName(str);
                        TestingMethod testingMethod = testingMethodservice.getByName(t);
                        if (testingMethod != null)
                        {
                            colList.add(testingMethod.getId());
                        }
                        
                    }
                    else
                    {
                        colList.add("");
                    }
                }
                
                if (StringUtils.isEmpty(str) && col != 9 && col != 1)
                {
                    type = 2;
                    continue;
                }
                
                if ("".equals(str) && col == 9)
                {
                    flag = true;
                }
                joinAll.add(str);
                jostAll = StringUtils.join(joinAll);
                //pcr起始点和pcr终止点必须为数字
                if (col == 3 && !isNumeric(str))
                {
                    type = 6;
                }
                if (col == 4 && !isNumeric(str))
                {
                    type = 6;
                }
                //一行所有列为空忽略
                if (col == 10 && "".endsWith(jostAll))
                {
                    type = 4;
                }
                //判断提交表内是否重复
                if (col == 0 || col == 2 || col == 3 || col == 4 || col == 5 || col == 9 || col == 10)
                {
                    joinStr.add(str);//把判定重复的三个字段组成数组
                    if (joinStr.size() == 3)
                    {
                        jost = StringUtils.join(joinStr);//把三个字段的集合拼接成字符串
                        //字符串放到集合中
                        if (jstr.size() == 0)
                        {
                            jstr.add(jost);
                        }
                        else
                        {
                            if (!jstr.contains(jost))
                            {
                                jstr.add(jost);
                            }
                            else
                            {
                                type = 1;
                            }
                        }
                    }
                }
                //判断表内正向引物名名唯一性，满足重复性，不做判断
                if (type != 1 && !StringUtils.isEmpty(str) && col == 5)
                {
                    forwordPrimerName = str;
                    if (forwordPrimerNames.contains(str))
                    {
                        nameFlag = true;//正向引物名重复
                    }
                    else
                    {
                        forwordPrimerNames.add(str);
                    }
                }
                //判断表内反向引物名名唯一性，满足重复性，不做判断
                if (type != 1 && !StringUtils.isEmpty(str) && col == 7)
                {
                    revesePrimerName = str;
                    if (revesePrimerNames.contains(str))
                    {
                        nameFlag = true;//反向引物名重复
                    }
                    else
                    {
                        revesePrimerNames.add(str);
                    }
                }
                //判断应用类型错误
                if (col == 10 && type != 2 && flag)
                {
                    if ("动态突变".equals(str) || "Long-PCR检测".equals(str) || "多重PCR检测".equals(str)
                        || "Sanger检测".equals(str))
                    {
                        type = 3;
                    }
                }
                if (col == 10 && type != 2 && nameFlag)
                {
                    nameFlag = false;
                    if ("QPCR验证".equals(str) || "Sanger验证".equals(str) || "MLPA验证".equals(str)
                        || "PCR-NGS验证".equals(str))
                    {
                        type = 7;
                    }
                }
                //库里验证引物名唯一性
                if ("QPCR验证".equals(str) || "Sanger验证".equals(str) || "MLPA验证".equals(str) || "PCR-NGS验证".equals(str))
                {
                    if (type != 7)
                    {
                        if (!StringUtils.isEmpty(forwordPrimerName) || !StringUtils.isEmpty(revesePrimerName))
                        {
                            PrimerPagingRequest searcher = new PrimerPagingRequest();
                            searcher.setForwardPrimerName(forwordPrimerName);
                            searcher.setReversePrimerName(revesePrimerName);
                            forwordPrimerName = "";
                            revesePrimerName = "";
                            List<Primer> primers = getByName(searcher, 1, 10);
                            if (!Collections3.isEmpty(primers))
                            {
                                type = 8;
                            }
                        }
                    }
                }
                
            }
            if (type == 2)
            {
                colList.add("必填项缺失");
            }
            if (type == 1)
            {
                colList.add("表内存在重复引物");
            }
            if (type == 3)
            {
                colList.add("应用类型错误");
            }
            if (type == 6)
            {
                colList.add("PCR起始点/PCR终止点必须为数字");
            }
            if (type == 7)
            {
                colList.add("表内引物名重复");
            }
            if (type == 8)
            {
                colList.add("引物名已存在");
            }
            if (type != 4 && type != 5)
            {
                dataList.add(colList);
            }
        }
        
        return dataList;
    }
    
    public Workbook getWorkbook(InputStream inStr, String fileName)
        throws Exception
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
                    //value = cell.getNumericCellValue() + "";
                    value = df.format(cell.getNumericCellValue());
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
    
    @Override
    public List<Primer> getPrimerList(PrimerPagingRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/list");
        ResponseEntity<List<Primer>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PrimerPagingRequest>(request),
                new ParameterizedTypeReference<List<Primer>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public String checkPrimerForDesign(CheckPrimerForDesignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/checkPrimerForDesign");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public String excelDataInsert(PrimerExcelListRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/excelDataInsert");
        return template.postForObject(url, request, String.class);
    }
    
    public static boolean isNumeric(String str)
    {
        str = str.replace(",", "");
        for (int i = 0; i < str.length(); i++)
        {
            
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String download(InputStream is,List<Primer> primers)
    {
        
        File tempFile = new File("primer" + ".xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();

            for (Primer p : primers)
            {
                Map<Integer, Object> data = new HashMap<Integer, Object>();
                data.put(1, p.getGene());
                data.put(2, p.getCodingExon());
                data.put(3, p.getChromosomeNumber());
                data.put(4, String.valueOf(p.getPcrStartPoint()));
                data.put(5, String.valueOf(p.getPcrEndPoint()));
                data.put(6, p.getForwardPrimerName());
                data.put(7, p.getForwardPrimerSequence());
                data.put(8, p.getReversePrimerName());
                data.put(9, p.getReversePrimerSequence());
                data.put(10, p.getProductCode());
                data.put(11, p.getApplicationType());
                datalist.add(data);
            }

            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1"}; // 必须为列表头部所有位置集合， 输出
            
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
    public List<Primer> getByName(PrimerPagingRequest searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/primer/paging");
        ResponseEntity<Pagination<Primer>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PrimerPagingRequest>(searcher),
                new ParameterizedTypeReference<Pagination<Primer>>()
                {
                });
        
        return exchange.getBody().getRecords();
    }
    
    @Override
    public List<Primer> selectByProperties(Primer p)
    {
        String url = GatewayService.getServiceUrl("/bsm/primer/listByProperties");
        ResponseEntity<List<Primer>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<Primer>(p),
                new ParameterizedTypeReference<List<Primer>>()
                {
                });
        return exchange.getBody();
    }
}
