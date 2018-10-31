package com.todaysoft.lims.system.modules.reportexport.mvc;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.modules.bpm.report.service.impl.ReportService;
import com.todaysoft.lims.system.modules.reportexport.entity.MutationFamilySangerResultInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportCapcnvResult;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResult;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResultPicture;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMethodColumnConfig;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMutationInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportSampleBaseInfo;
import com.todaysoft.lims.system.modules.reportexport.model.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportPreviewRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportUpdateRequest;
import com.todaysoft.lims.system.modules.reportexport.model.response.GenerateWordReportReponse;
import com.todaysoft.lims.system.modules.reportexport.service.IReportExportPreviewService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

import com.todaysoft.lims.system.modules.bpm.report.model.TestingReport;

@Controller
@RequestMapping(value = "/reportExportPreview")
public class ReportExportPreviewController extends BaseController
{
    @Autowired
    private IReportExportPreviewService reportExportPreviewService;
    
    @Autowired
    private ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(ReportExportPreviewController.class);
    
    @RequestMapping(value = "/reportExportPreview.do")
    @FormInputView
    public String reportExportPreview(ReportExportPreviewRequest request, ModelMap model) {
        model.addAttribute("taskId",request.getTaskId());
        GetReportExportSampleBaseInfoRequest getReportExportSampleBaseInfoRequest=new GetReportExportSampleBaseInfoRequest();
        getReportExportSampleBaseInfoRequest.setTaskId(request.getTaskId());
        getReportExportSampleBaseInfoRequest.setAnalysisResult(request.getAnalysisResult());
        logger.info("getReportExportSampleBaseInfo start");
        ReportExportSampleBaseInfo reportExportSampleBaseInfo= reportExportPreviewService.getReportExportSampleBaseInfo(getReportExportSampleBaseInfoRequest);
        model.addAttribute("reportExportSampleBaseInfo",reportExportSampleBaseInfo);
        logger.info("getReportExportSampleBaseInfo end");
        ReportExportMutationInfoRequest reportExportMutationInfoRequest=new ReportExportMutationInfoRequest();
        reportExportMutationInfoRequest.setTaskId(request.getTaskId());
        reportExportMutationInfoRequest.setRecordIds(request.getRecordIds());
        reportExportMutationInfoRequest.setTechnicalFamilyGroupId(request.getTechnicalFamilyGroupId());
        logger.info("getReportExportMutationInfo start");
        Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList=  reportExportPreviewService.getReportExportMutationInfo(reportExportMutationInfoRequest);
        logger.info("getReportExportMutationInfo end");
        model.addAttribute("mutationInfoClinicalPhenotypeHighCorrelation",clinicalJudgmentToReportExportMutationInfoList.get("临床表型高度相关"));
        model.addAttribute("mutationInfoSuspiciousVariation",clinicalJudgmentToReportExportMutationInfoList.get("可疑变异"));
        model.addAttribute("mutationInfoHeterozygousCarrier",clinicalJudgmentToReportExportMutationInfoList.get("杂合携带"));
        
        ReportExportCnvAnalysisPicResultListRequest reportExportCnvAnalysisPicResultListRequest=new ReportExportCnvAnalysisPicResultListRequest();
        reportExportCnvAnalysisPicResultListRequest.setAnalysisSampleId(request.getAnalysisSampleId());
        reportExportCnvAnalysisPicResultListRequest.setCnvAnalysisPicIds(request.getCnvAnalysisPicIds());
        reportExportCnvAnalysisPicResultListRequest.setCnvAnalysisResultIds(request.getCnvAnalysisResultIds());
        reportExportCnvAnalysisPicResultListRequest.setTaskId(request.getTaskId());
        logger.info("getReportExportCnvAnalysisPicResultList start");
        Map<String, List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists= reportExportPreviewService.getReportExportCnvAnalysisPicResultList(reportExportCnvAnalysisPicResultListRequest);
        logger.info("getReportExportCnvAnalysisPicResultList end");
        List<ReportExportCnvAnalysisPicResultList> highCorrelationReportExportCnvAnalysisPicResultLists= clinicalJudgmentToReportExportCnvAnalysisPicResultLists.get("临床表型高度相关");
        String missingType=getMissingTypeCnvResult(highCorrelationReportExportCnvAnalysisPicResultLists);
        model.addAttribute("highCorrelationMissingType",missingType);
        model.addAttribute("clinicalPhenotypeHighCorrelation",highCorrelationReportExportCnvAnalysisPicResultLists);
        
        List<ReportExportCnvAnalysisPicResultList> correlationReportExportCnvAnalysisPicResultLists= clinicalJudgmentToReportExportCnvAnalysisPicResultLists.get("临床表型相关");
        missingType=getMissingTypeCnvResult(correlationReportExportCnvAnalysisPicResultLists);
        model.addAttribute("correlationMissingType",missingType);
        model.addAttribute("clinicalPhenotypeCorrelation", correlationReportExportCnvAnalysisPicResultLists);
        
        ReportExportDetectionResultInfoRequest reportExportDetectionResultInfoRequest=new ReportExportDetectionResultInfoRequest();
        reportExportDetectionResultInfoRequest.setMethodName(ReportExportDetectionResultInfoRequest.METHODNAME_MLPA);
        List<ReportExportMethodColumnConfig> mlpaColumnConfigs = reportExportPreviewService.getReportExportMethodColumnConfig(reportExportDetectionResultInfoRequest);
        model.addAttribute("mlpaColumns",mlpaColumnConfigs);
        reportExportDetectionResultInfoRequest.setMethodName(ReportExportDetectionResultInfoRequest.METHODNAME_DT);
        List<ReportExportMethodColumnConfig> dtColumnConfigs = reportExportPreviewService.getReportExportMethodColumnConfig(reportExportDetectionResultInfoRequest);
        model.addAttribute("dtColumns",dtColumnConfigs);

        reportExportDetectionResultInfoRequest.setDetectionResultIds(request.getMlpaDetectionResultIds());
        reportExportDetectionResultInfoRequest.setMethodName(ReportExportDetectionResultInfoRequest.METHODNAME_MLPA);
        reportExportDetectionResultInfoRequest.setTaskId(request.getTaskId());
        logger.info("mlpa,getReportExportDetectionResultInfo start");
        Map<String,List<ReportExportDetectionResult>> clinicalJudgmentToReportExportDetectionResults = reportExportPreviewService.getReportExportDetectionResultInfo(reportExportDetectionResultInfoRequest);
        logger.info("mlpa,getReportExportDetectionResultInfo end");
        List<ReportExportDetectionResult> highCorrelationReportExportDetectionResults = clinicalJudgmentToReportExportDetectionResults.get("临床表型高度相关");
        model.addAttribute("mlpaHighCorrelationLocusType",getLocusTypeMlpaDetectionResult(highCorrelationReportExportDetectionResults));
        model.addAttribute("mlpaClinicalPhenotypeHighCorrelation",highCorrelationReportExportDetectionResults);
        
        List<ReportExportDetectionResult> correlationReportExportDetectionResults = clinicalJudgmentToReportExportDetectionResults.get("临床表型相关");
        model.addAttribute("mlpaCorrelationLocusType", getLocusTypeMlpaDetectionResult(correlationReportExportDetectionResults));
        model.addAttribute("mlpaClinicalPhenotypeCorrelation", correlationReportExportDetectionResults);
        
        reportExportDetectionResultInfoRequest.setDetectionResultIds(request.getDtDetectionResultIds());
        reportExportDetectionResultInfoRequest.setMethodName(ReportExportDetectionResultInfoRequest.METHODNAME_DT);
        logger.info("dt,getReportExportDetectionResultInfo start");
        clinicalJudgmentToReportExportDetectionResults = reportExportPreviewService.getReportExportDetectionResultInfo(reportExportDetectionResultInfoRequest);
        logger.info("dt,getReportExportDetectionResultInfo end");
        model.addAttribute("dtMutationClinicalPhenotypeHighCorrelation",clinicalJudgmentToReportExportDetectionResults.get("临床表型高度相关"));
        model.addAttribute("dtMutationClinicalPhenotypeCorrelation", clinicalJudgmentToReportExportDetectionResults.get("临床表型相关"));
        logger.info("getMutationFamilySangerResultInfoByTaskId start");
        List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos= reportExportPreviewService.getMutationFamilySangerResultInfoByTaskId(request.getTaskId());
        logger.info("getMutationFamilySangerResultInfoByTaskId end");
        model.addAttribute("mutationFamilySangerResultInfos", mutationFamilySangerResultInfos);
        
        ReportExportDetectionResultPictureRequest reportExportDetectionResultPictureRequest=new ReportExportDetectionResultPictureRequest();
        reportExportDetectionResultPictureRequest.setMethod(ReportExportDetectionResultInfoRequest.METHODNAME_MLPA);
        reportExportDetectionResultPictureRequest.setTaskId(request.getTaskId());
        logger.info("mlpa,getReportExportDetectionResultPictureByTaskIdAndMethod start");
        List<ReportExportDetectionResultPicture> mlpaReportExportDetectionResultPictures=reportExportPreviewService.getReportExportDetectionResultPictureByTaskIdAndMethod(reportExportDetectionResultPictureRequest);
        logger.info("mlpa,getReportExportDetectionResultPictureByTaskIdAndMethod end");
        model.addAttribute("mlpaReportExportDetectionResultPictures", mlpaReportExportDetectionResultPictures);
        reportExportDetectionResultPictureRequest.setMethod(ReportExportDetectionResultInfoRequest.METHODNAME_DT);
        logger.info("dt,getReportExportDetectionResultPictureByTaskIdAndMethod start");
        List<ReportExportDetectionResultPicture> dtReportExportDetectionResultPictures=reportExportPreviewService.getReportExportDetectionResultPictureByTaskIdAndMethod(reportExportDetectionResultPictureRequest);
        logger.info("dt,getReportExportDetectionResultPictureByTaskIdAndMethod end");
        model.addAttribute("dtReportExportDetectionResultPictures", dtReportExportDetectionResultPictures);
        return "/bpm/report/reportExportPreview/preview";
    }
    
    private String getMissingTypeCnvResult(List<ReportExportCnvAnalysisPicResultList> reportExportCnvAnalysisPicResultLists) {
        if(reportExportCnvAnalysisPicResultLists!=null&&reportExportCnvAnalysisPicResultLists.size()>0) {
            Set<String> cnvResult=new HashSet<String>();
            for(ReportExportCnvAnalysisPicResultList reportExportCnvAnalysisPicResultList:reportExportCnvAnalysisPicResultLists) {
                List<ReportExportCapcnvResult> reportExportCapcnvResults = reportExportCnvAnalysisPicResultList.getReportExportCapcnvResultList();
                if(reportExportCapcnvResults!=null&&reportExportCapcnvResults.size()>0) {
                    for(ReportExportCapcnvResult reportExportCapcnvResult:reportExportCapcnvResults) {
                        cnvResult.add(reportExportCapcnvResult.getMissingType());
                    }
                }
            }
            return StringUtils.join(cnvResult,"/");
        }
        return "";
    }
    
    private String getLocusTypeMlpaDetectionResult(List<ReportExportDetectionResult> reportExportDetectionResults) {
        if(reportExportDetectionResults!=null&&reportExportDetectionResults.size()>0) {
            Set<String> locusTypes=new HashSet<String>();
            for(ReportExportDetectionResult reportExportDetectionResult:reportExportDetectionResults) {
                locusTypes.add(reportExportDetectionResult.getResult().get("locusType"));
            }
            return StringUtils.join(locusTypes,"/");
        }
        return "";
    }

    @RequestMapping(value = "/updateReportExport", method = RequestMethod.POST)
//    @FormSubmitHandler
    @ResponseBody
    public String updateReportExport(ReportExportUpdateRequest request) {
        reportExportPreviewService.updateReportExport(request);
        return "success";
    }
    
    @RequestMapping(value = "/generateWordReport/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public GenerateWordReportReponse generateWordReport(@PathVariable(value="taskId") String taskId) {
        
        GenerateWordReportReponse generateWordReportReponse = reportExportPreviewService.generateReport(taskId);
        TestingReport testingReport= reportService.get(taskId);
        String reportName=getReportName(testingReport);
        generateWordReportReponse.setReportName(reportName);
        return generateWordReportReponse;
    }
    
    private String getReportName(TestingReport testingReport) {
        Calendar calendar=Calendar.getInstance();
        return DateUtil.format(calendar.getTime(), "yyyyMMdd")+"_"+(testingReport.getProductCode()==null?"":testingReport.getProductCode())+"_"+(testingReport.getOrderCode()==null?"":testingReport.getOrderCode())+
            "_"+(testingReport.getSampleCode()==null?"":testingReport.getSampleCode())+"_"+(testingReport.getSampleName()==null?"":testingReport.getSampleName())+".doc";
    }
    
    @RequestMapping(value = "/downloadReport/{fileName}/{wordName}", method = RequestMethod.GET)
    public void downloadReport(@PathVariable("fileName") String fileName,@PathVariable("wordName") String wordName, HttpServletRequest request, HttpServletResponse response) throws Exception{
        FileUtils.download(response, fileName, wordName+".doc");
    }
}
