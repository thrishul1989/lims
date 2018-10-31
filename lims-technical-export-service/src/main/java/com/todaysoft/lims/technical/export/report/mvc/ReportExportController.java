package com.todaysoft.lims.technical.export.report.mvc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;
import com.todaysoft.lims.technical.export.report.dto.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportUpdateRequest;
import com.todaysoft.lims.technical.export.report.model.response.GenerateWordReportReponse;
import com.todaysoft.lims.technical.export.report.service.IReportExportService;

@RestController
@RequestMapping("/technicalexport/reportexport")
public class ReportExportController
{
    @Autowired
    private IReportExportService service;
    
    @RequestMapping(value = "/updateReportExport", method = RequestMethod.POST)
    public void updateReportExport(@RequestBody ReportExportUpdateRequest request)
    {
        service.updateReportExport(request);
    }
    
    @RequestMapping(value = "/getReportExportSampleBaseInfoByTaskId/{taskId}", method = RequestMethod.POST)
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfoByTaskId(@PathVariable(value="taskId") String taskId)
    {
        ReportExportSampleBaseInfo reportExportSampleBaseInfo= service.getReportExportSampleBaseInfoByTaskId(taskId);
        return reportExportSampleBaseInfo;
    }
    
    @RequestMapping(value = "/getReportExportMutationInfo/{taskId}", method = RequestMethod.POST)
    public Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(@PathVariable(value="taskId") String taskId)
    {
        Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList= service.getReportExportMutationInfo(taskId);
        return clinicalJudgmentToReportExportMutationInfoList;
    }
    
    @RequestMapping(value = "/getSangerResultInfoByTaskId/{taskId}", method = RequestMethod.GET)
    public List<MutationFamilySangerResultInfo> getMutationFamilySangerResultInfoByTaskId(@PathVariable(value="taskId") String taskId){
       return service.getMutationFamilySangerResultInfoByTaskId(taskId);
    }
    
    @RequestMapping(value = "/getReportExportDetectionResultPictureByTaskIdAndMethod", method = RequestMethod.POST)
    public List<ReportExportDetectionResultPicture> getReportExportDetectionResultPictureByTaskIdAndMethod(@RequestBody ReportExportDetectionResultPictureRequest reportExportDetectionResultPictureRequest){
       return service.getReportExportDetectionResultPictureByTaskIdAndMethod(reportExportDetectionResultPictureRequest);
    }
    
    @RequestMapping(value = "/getReportExportCnvAnalysisPicResultList/{taskId}", method = RequestMethod.POST)
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(@PathVariable(value="taskId") String taskId)
    {
        Map<String, List<ReportExportCnvAnalysisPicResultList>>  clinicalJudgmentToReportExportCnvAnalysisPicResultLists= service.getReportExportCnvAnalysisPicResultList(taskId);
        return clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    }
    
    @RequestMapping(value = "/generateWordReport/{taskId}", method =RequestMethod.GET)
    public GenerateWordReportReponse generateWordReport(@PathVariable(value="taskId") String taskId,HttpServletRequest reqeust) {
        //System.out.println(reqeust.getSession().getServletContext().getRealPath("/")+"reportdir");
        GenerateWordReportReponse generateWordReportReponse=new GenerateWordReportReponse();
        generateWordReportReponse.setTaskId(taskId);
        generateWordReportReponse.setReportUrl(service.generateReport(taskId));
        return generateWordReportReponse;
    }
    
    @RequestMapping(value = "/downloadReport/{fileName}", method = RequestMethod.GET)
    public void downloadReport(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
      //  ClassLoader classLoader = ReportExportController.class.getClassLoader();
        //File file = ResourceUtils.getFile("classpath:document1.doc");
        InputStream fileInput = ReportExportController.class.getResourceAsStream("/test.doc");
        if(fileInput!=null){
            try {
                
                OutputStream out = response.getOutputStream();
                response.addHeader("Content-Disposition", "attachment; filename="+fileName);
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("ISO-8859-1");
                byte[] buffer = new byte[1024];
                int n = 0;
                while (-1 != (n = fileInput.read(buffer)))
                    out.write(buffer, 0, n);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Exception exit");
                return ;
            }
        } else {
            System.err.println("not found");
            return ;
        }
        return ;
    }

}
