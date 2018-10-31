package com.todaysoft.lims.system.modules.maintain.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.OrderExportInfomation;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.impl.DataPcrDataService;
import com.todaysoft.lims.system.modules.maintain.service.IMaintainService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.service.impl.TestSheetService;

@Service
public class MaintainService extends RestService implements IMaintainService
{
    
    @Autowired
    private IOrderService orderService;
    
    @Override
    public void updateMethodTemplate()
    {
        
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/update_testingMethod_template");
        template.postForObject(url, new HashMap(), Integer.class);
        
    }
    
    @Override
    public void deleteZeroDatas()
    {
        String url = GatewayService.getServiceUrl("/maintain/invoiceSolve/deleteZeroDatas");
        template.getForObject(url, Integer.class);
    }
    
    @Override
    public void updateScheduleNotgoinTech(String orderCode)
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/update_schedule_notgoin_tech?orderCodes=" + orderCode + "");
        Map<String, String> req = new HashMap();
        req.put("orderCodes", orderCode);
        template.postForObject(url, req, Integer.class);
        
    }
    
    @Override
    public String exportAllOrderInfomation() throws IOException
    {
        
        InputStream inputStream = DataPcrDataService.class.getResourceAsStream("/taskTemplate/orderExport.xlsx");
        File file = new File("所有订单.xlsx");
        if (!file.exists())
        {
            file.createNewFile();
        }
        
        TestSheetService.inputstreamToFile(inputStream, file);
        String path = file.getPath();
        
        FileOutputStream output = new FileOutputStream(new File(path)); //读取的文件路径   
        SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数据，以免内存溢出，其余写入 硬盘   
        Sheet sheet = wb.createSheet();
        int pageNo = 0;
        int rownum = 1;
        Pagination<OrderExportInfomation> pagination;
        Row row1 = sheet.createRow(0);
        
        Cell cell0 = row1.createCell(0);
        cell0.setCellValue("订单编号");
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue("检测产品");
        Cell cell22 = row1.createCell(2);
        cell22.setCellValue("检测方法");
        Cell cell33 = row1.createCell(3);
        cell33.setCellValue("主样本编号");
        Cell cell44 = row1.createCell(4);
        cell44.setCellValue("样本接收时间");
        Cell cell55 = row1.createCell(5);
        cell55.setCellValue("实验启动时间");
        Cell cell66 = row1.createCell(6);
        cell66.setCellValue("下机时间");
        Cell cell77 = row1.createCell(7);
        cell77.setCellValue("报告审核完成时间");
        Cell cell88 = row1.createCell(8);
        cell88.setCellValue("报告发送时间");
        
        do
        {
            
            pagination = this.paging(new OrderSearchRequest(), pageNo++, 100);
            System.out.println("正在导出第" + pageNo * 100 + "条数据");
            
            for (OrderExportInfomation infomation : pagination.getRecords())
            {
                
                Row row = sheet.createRow(rownum);
                rownum++;
                
                Cell cell = row.createCell(0);
                cell.setCellValue(infomation.getOrderCode());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(infomation.getProductName());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(infomation.getMethodName());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(infomation.getSampleCode());
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(infomation.getReciveTime());
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(infomation.getScheduleStartTime());
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(infomation.getBioTaskTime());
                Cell cell7 = row.createCell(7);
                cell7.setCellValue(infomation.getReviewTime());
                Cell cell8 = row.createCell(8);
                cell8.setCellValue(infomation.getReportDataSendTime());
                
            }
            
        } while (!pagination.isLastPage());
        
        wb.write(output);
        output.close();
        System.out.println("导出订单完成");
        
        return file.getAbsolutePath();
        
    }
    
    public Pagination<OrderExportInfomation> paging(OrderSearchRequest request, int pageNo, int pageSize)
    {
        
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/exportAllOrderInfomation");
        ResponseEntity<Pagination<OrderExportInfomation>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<OrderExportInfomation>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void updateOrderProduct()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateOrderProductStatu");
        template.getForObject(url, Integer.class);
        
    }
    
    @Override
    public void updateTestingTaskPlanFinishDate()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateTestingTaskPlanFinishDate");
        template.getForObject(url, Integer.class);
        
    }
    
    @Override
    public void updateDataPicForMlpaVerify()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateDataPicForMlpaVerify");
        template.getForObject(url, Integer.class);
        
    }
    
    @Override
    public void updateOrderStatusForSchedulePlanTask()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateOrderStatusForSchedulePlanTask");
        template.getForObject(url, Integer.class);
        
    }
    
    @Override
    public void updateOldNgsSequecingTask()
    {
        String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateOldNgsSequecingTask");
        template.getForObject(url, Integer.class);
        
    }
    
    @Override
    public void updateNgsAndBioTask(String sequecingCode, Integer tag)
    {
        try
        {
            String url = GatewayService.getServiceUrl("/maintain/testing/optimize/updateNgsAndBioTask/{sequecingCode}/{tag}");
            template.getForObject(url, Integer.class, sequecingCode, tag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
