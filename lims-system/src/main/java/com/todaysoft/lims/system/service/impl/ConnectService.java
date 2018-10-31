package com.todaysoft.lims.system.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.ConnectSearcher;
import com.todaysoft.lims.system.model.vo.Connect;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IConnectService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class ConnectService extends RestService implements IConnectService
{
    
    @Override
    public void create(Connect request)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/create");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(Connect request)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/modify");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<Connect> paging(ConnectSearcher request, int pageNo, int pageSize)
    {
        
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/connect/paging");
        ResponseEntity<Pagination<Connect>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ConnectSearcher>(request),
                new ParameterizedTypeReference<Pagination<Connect>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public Connect getConnectById(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/{id}");
        return template.getForObject(url, Connect.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean checkedconnectNum(ConnectSearcher connect)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/chechedConnectNum");
        return template.postForObject(url, connect, boolean.class);
    }
    
    @Override
    public List<Connect> getConnectListById(String ids)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/getConnectListById/{ids}");
        ResponseEntity<List<Connect>> exchange =
            template.exchange(url,
                HttpMethod.GET,
                new HttpEntity<String>(ids),
                new ParameterizedTypeReference<List<Connect>>()
                {
                },
                Collections.singletonMap("ids", ids));
        return exchange.getBody();
    }
    
    @Override
    public List<Connect> getConnectList(ConnectSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/getConnectList");
        ResponseEntity<List<Connect>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ConnectSearcher>(searcher),
                new ParameterizedTypeReference<List<Connect>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Integer upload(MultipartFile uploadData)
    {
        Integer flag = 0;//默认正常
        List<Connect> list = new ArrayList<Connect>();
        try
        {
            ImportExcel ei = new ImportExcel(uploadData, 0, 0);
            List<Connect> connects1 = ei.getDataList(Connect.class);
            List<Connect> connects = deleteRepeatData(ei.getDataList(Connect.class));
            if (connects.size() != connects1.size())
            {
                flag = 1;//重复
            }
            List<Connect> conns = getConnectList(new ConnectSearcher());
            for (Connect connect : connects)
            {
                if (!conns.contains(connect))
                {
                    list.add(connect);
                }
            }
            if (list.size() != connects.size())
            {
                flag = 1;//数据库重复
            }
            for (Connect connect : list)
            {
                create(connect);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return flag;
        
    }
    
    @Override
    public List<Connect> ConnectListForWkcreate(ConnectSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/connect/ConnectListForWkcreate");
        ResponseEntity<List<Connect>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ConnectSearcher>(searcher),
                new ParameterizedTypeReference<List<Connect>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void downloadData(HttpServletResponse response, InputStream inputStream)
    {
        
        OutputStream outputStream = null;
        try
        {
            // 以流的形式下载文件。
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("connect.xlsx"));
            response.setContentType("application/octet-stream");
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
    }
    
    public List<Connect> deleteRepeatData(List<Connect> connects)
    {
        Map<Integer, Connect> map = new HashMap<Integer, Connect>();
        List<Connect> list = Lists.newArrayList();
        if (Collections3.isNotEmpty(connects))
        {
            for (Connect connect : connects)
            {
                map.put(connect.getConnectNum(), connect);
            }
        }
        Set<Entry<Integer, Connect>> set = map.entrySet();
        for (Entry<Integer, Connect> entry : set)
        {
            list.add(entry.getValue());
        }
        return list;
    }
    
    @Override
    public void writer(String path, String fileName, String fileType, List<Connect> connects, String[] titles)
        throws Exception
    {
        Workbook wb = null;
        String excelPath = path + File.separator + fileName + "." + fileType;
        File file = new File(excelPath);
        Sheet sheet = null;
        //创建工作文档对象   
        if (!file.exists())
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                
                wb = new XSSFWorkbook();
            }
            //创建sheet对象   
            sheet = wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(excelPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
        }
        else
        {
            if (fileType.equals("xls"))
            {
                wb = new HSSFWorkbook();
                
            }
            else if (fileType.equals("xlsx"))
            {
                wb = new XSSFWorkbook();
                
            }
        }
        //创建sheet对象   
        if (sheet == null)
        {
            sheet = wb.createSheet("sheet1");
        }
        
        Row row = null;
        Cell cell = null;
        /* //添加表头  
         row.setHeight((short)540);
         cell.setCellValue("流程单据"); //创建第一行    
        */
        CellStyle style = wb.createCellStyle(); // 样式对象      
        // 设置单元格的背景颜色为淡蓝色  
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直      
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平   
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
        
        //        cell.setCellStyle(style); // 样式，居中
        
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)280);
        style.setFont(font);
        // 单元格合并      
        // 四个参数分别是：起始行，起始列，结束行，结束列      
        /*  sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
          sheet.autoSizeColumn(5200);*/
        
        row = sheet.createRow(0); //创建第1行    
        for (int i = 0; i < titles.length; i++)
        {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(i, 20 * 256);
        }
        row.setHeight((short)540);
        
        /* //循环写入行数据   
         for (int i = 0; i < connects.size(); i++)
         {
             row = sheet.createRow(i + 2);
             row.setHeight((short)500);
             row.createCell(0).setCellValue((connects.get(i)).getConnectNum());
             row.createCell(1).setCellValue((connects.get(i)).getConnectSequence());
         }*/
        
        //创建文件流   
        OutputStream stream = new FileOutputStream(excelPath);
        //写入数据   
        wb.write(stream);
        //关闭文件流   
        stream.close();
    }
    
    @Override
    public void renderMergedOutputModel(HttpServletRequest request, HttpServletResponse response, String rootPath)
        throws Exception
    {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = new File(rootPath + "connect.xls");
        if (null == file)
        {
            return;
        }
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddhhmmss");
        String datFormate = formate.format(new Date());
        String txtName = datFormate + ".xls";
        inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        response.reset();//清空response的buffer
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + txtName);
        response.setCharacterEncoding("utf-8");
        outputStream = new BufferedOutputStream(response.getOutputStream());
        try
        {
            outputStream.write(buffer);
            outputStream.flush();
        }
        finally
        {
            if (null != inputStream)
            {
                inputStream.close();
            }
            
            if (null != outputStream)
            {
                outputStream.close();
            }
        }
        if (null != file)
        {
            file.delete();
        }
    }
    
}
