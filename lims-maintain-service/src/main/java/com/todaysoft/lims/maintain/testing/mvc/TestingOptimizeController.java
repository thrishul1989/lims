package com.todaysoft.lims.maintain.testing.mvc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.maintain.indexes.model.PrimerSqlModel;
import com.todaysoft.lims.maintain.model.OrderExportInfomation;
import com.todaysoft.lims.maintain.model.OrderSearchRequest;
import com.todaysoft.lims.maintain.testing.service.ITestingOptimizeService;
import com.todaysoft.lims.persist.Pagination;

@RestController
@RequestMapping("/maintain/testing/optimize")
public class TestingOptimizeController
{
    @Autowired
    private ITestingOptimizeService service;
    
    @RequestMapping("/generate_redundant_fields")
    public void generateRedundantFields(String semantics)
    {
        String semantic[] = semantics.split("\\,");
        
        for (String s : semantic)
        {
            Thread t = new Thread()
            {
                @Override
                public void run()
                {
                    service.generateRedundantFields(s);
                }
            };
            
            t.start();
        }
        
    }
    
    @RequestMapping(value = "/validateData", method = RequestMethod.GET)
    public Integer validateData()
    {
        return service.validateData();
    }
    
    //处理sanger验证 引物设计一场结束的问题
    @RequestMapping(value = "/handleSangerVerify", method = RequestMethod.GET)
    public PrimerSqlModel handleSangerVerify()
    {
        PrimerSqlModel testArgs = service.handleSangerVerify();
        return testArgs;
    }
    
    //处理七牛上传的报告没有后缀名问题
    //1.先找到所有的报告，然后取七牛上查找报告，如果是有追命的文件名取到的，不做处理，如果是无后缀名取到的，重新命名再上传上去，删掉之前上传的
    @RequestMapping(value = "/handleReportName", method = RequestMethod.GET)
    public String handleReportName()
    {
        return service.handleReportName();
    }
    
    @RequestMapping("/generate_redundant_task")
    public void generateTask(String id)
    {
        service.generateTask(id);
        
    }
    
    @RequestMapping(value = "/findTestingReports", method = RequestMethod.GET)
    public String findTestingReports()
    {
        return service.findTestingReports();
    }
    
    @RequestMapping(value = "/updateTestingReportRedundant", method = RequestMethod.GET)
    public void updateTestingReportRedundant()
    {
        service.updateTestingReportRedundant();
    }
    
    @RequestMapping(value = "/updateTestingReportTechnicalMan", method = RequestMethod.GET)
    public void updateTestingReportTechnicalMan()
    {
        service.updateTestingReportTechnicalMan();
    }

    @RequestMapping(value = "/updateTestingReportProductDutyMan", method = RequestMethod.GET)
    public void updateTestingReportProductDutyMan()
    {
        service.updateTestingReportProductDutyMan();
    }


    @RequestMapping(value = "/updateTechnicalRecordToMap", method = RequestMethod.GET)
    public void updateTechnicalRecordToMap()
    {
        service.updateTechnicalRecordToMap();
    }
    
    @RequestMapping(value = "/updateDataAnalysisRecordToMap", method = RequestMethod.GET)
    public void updateDataAnalysisRecordToMap()
    {
        service.updateDataAnalysisRecordToMap();
    }
    
    /**
     * 修改产品的检测方法模板
     * */
    @RequestMapping(value = "/update_testingMethod_template", method = RequestMethod.POST)
    public void updateMethodTemplate()
    {
        service.updateMethodTemplate();
        System.out.println("产品模板更新完毕");
        
    }
    
    /**
     * 修改老订单走到生信就技术没有继续走到技术分析
     * */
    @RequestMapping(value = "/update_schedule_notgoin_tech", method = RequestMethod.POST)
    public void updateScheduleNotgoinTech(String orderCodes)
    {
        service.updateScheduleNotgoinTech(orderCodes);
        System.out.println("订单重新生成生信分析完成");
        
    }
    
    /**
     * 分页查询订单信息
     * @param request
     */
    @RequestMapping(value = "/exportAllOrderInfomation")
    public Pagination<OrderExportInfomation> exportAllOrderInfomation(@RequestBody OrderSearchRequest request)
    {
        
        return service.exportAllOrderInfomation(request);
        
    }
    
    /**
     * 处理产品状态不正确
     */
    @RequestMapping(value = "/updateOrderProductStatu")
    public void updateOrderProductStatu()
    {
        service.updateOrderProductStatu();
    }
    
    /**
     * 处理任务冗余应完成时间
     */
    @RequestMapping(value = "/updateTestingTaskPlanFinishDate")
    public void updateTestingTaskPlanFinishDate()
    {
        service.updateTestingTaskPlanFinishDate();
    }
    
    /**
     * 处理MLPA验证图片遗漏存orderId等些信息
     */
    @RequestMapping(value = "/updateDataPicForMlpaVerify")
    public void updateDataPicForMlpaVerify()
    {
        service.updateDataPicForMlpaVerify();
    }

    /**
     * 处理报告寄送已完成，产品状态未完成，订单状态未完成的单子
     */
    @RequestMapping(value = "/updateOrderStatusForSchedulePlanTask")
    public void updateOrderStatusForSchedulePlanTask()
    {
        service.updateOrderStatusForSchedulePlanTask();
    }
    
    /**
     * 处理旧的ngs任务
     */
    @RequestMapping(value = "/updateOldNgsSequecingTask")
    public void updateOldNgsSequecingTask()
    {
        service.updateOldNgsSequecingTask();
    }
    
    /**
     * 补数据
     */
    @RequestMapping(value = "/updateNgsAndBioTask/{sequecingCode}/{tag}")
    public void updateNgsAndBioTask(@PathVariable String sequecingCode,@PathVariable Integer tag) throws IOException
    {
        service.updateNgsAndBioTask(sequecingCode,tag);
    }
    
    
    
}
